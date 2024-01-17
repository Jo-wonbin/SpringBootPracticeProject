// 실행 시 지역구 불러오기 작동
$(document).ready(function () {
    loadProvinces();
});
// 페이징 정보를 저장할 변수
let currentPage = 1; // 현재 페이지
let totalPages = 1; // 전체 페이지 수

function loadPageBoard(page, provinceId, districtId) {
    let url = "/board/paging";

    if (provinceId && provinceId !== "zero") {
        url = "/board/province/" + provinceId + "/paging";
    } else if (districtId && districtId !== "zero") {
        url = "/board/district/" + districtId + "/paging";
    }

    $.ajax({
        url: url + "?page=" + page,
        type: "GET",
        success: function (pagingDto) {
            // 받아온 PagingDto를 이용해 게시글 목록 업데이트
            if (pagingDto === "데이터가 없습니다.") {
                const $tbody = $('#boardListBody');
                $tbody.empty();
                console.log("게시글 데이터 없음.");
            } else {
                displayBoardData(pagingDto.boardList.content);
                // 페이징 정보 업데이트
                currentPage = pagingDto.boardList.number + 1;
                totalPages = pagingDto.boardList.totalPages;
                // 페이징 업데이트
                updatePagination();
            }

        },
        error: function () {
            const $tbody = $('#boardListBody');
            $tbody.empty();

            console.log("오류");
        }
    });
}

function detailBoard(boardId) {
    location.href = "/board/" + boardId;
}

// 받아온 데이터를 화면에 표시하는 함수
function displayBoardData(data) {
    const $tbody = $('#boardListBody');
    $tbody.empty();  // 기존 목록을 비우고 새로운 데이터로 채웁니다.

    // 받아온 데이터를 이용해 동적으로 테이블 행을 생성하여 추가
    for (const board of data) {
        const formattedDate = new Date(board.boardCreatedTime).toLocaleString('ko-KR', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit'
        });

        const row = `<tr onclick="detailBoard(${board.id})">
                            <td>${board.id}</td>
                            <td>${board.boardTitle}</td>
                            <td>${board.boardWriter}</td>
                            <td>${board.boardHits}</td>
                            <td>${formattedDate}</td>
                            <td>${board.provinceName}</td>
                            <td>${board.districtName}</td>
                        </tr>`;
        $tbody.append(row);
    }
}

function checkDistrict() {
    const province = $('#province').val();
    const district = $('#district').val();
    if (province === "zero") {
        loadPageBoard(1, null, null);
    } else if (district === "zero") {
        loadPageBoard(1, province, null);
    } else {
        loadPageBoard(1, null, district);
    }
}

// 광역시 선택 시 동작
function updateDistricts() {
    const province = $('#province').val();
    const districtSelect = $('#district');

    // 기존 옵션 제거
    districtSelect.empty();
    if (province === "zero") {
        districtSelect.append("<option value=" + "zero" + " selected>전체</option>");
    } else {
        // Ajax를 통해 서버에서 데이터 동적으로 불러오기
        $.get('/area/district/' + province, function (data) {
            // 새로운 옵션 추가
            console.log(data);
            districtSelect.append("<option value=" + "zero" + " selected>전체</option>");
            data.forEach(function (districts) {
                districtSelect.append('<option value="' + districts.id + '">' + districts.name + '</option>');
            });
        });
    }
    loadPageBoard(1, province, null);
}

// 지역구 불러오기
function loadProvinces() {
    const provinceSelect = $('#province');

    const provinceSelectValue = provinceSelect.val();
    if (provinceSelectValue === "zero") {
        $('#district').append("<option value=" + "zero" + " selected>전체</option>");
    }
    // Ajax를 통해 서버에서 광역시 정보 동적으로 불러오기

    $.get('/area/province', function (data) {
        // 서버에서 받은 배열을 순회하면서 옵션을 추가
        console.log(data);
        data.forEach(function (province) {
            provinceSelect.append('<option  value="' + province.id + '">' + province.name + '</option>');
        });
    });
    loadPageBoard(1, provinceSelectValue, null);


}

// 게시글 작성 버튼 클릭 이벤트 핸들러
function boardWrite() {
    console.log("게시글 이동 버튼 클릭");
    location.href = "/board/boardWrite";
}

// 페이징 업데이트
function updatePagination() {
    const $pagination = $(".pagination");
    $pagination.empty();
    // 맨 첫 페이지로 이동하는 버튼
    $pagination.append(`<li class="page-item ${currentPage === 1 ? 'disabled' : ''}">
                            <a class="page-link" href="#" onclick="changePage(1)" aria-label="Previous">
                                <span aria-hidden="true">&laquo;&laquo;</span>
                            </a>
                        </li>`);

    // 이전 페이지로 이동하는 버튼 추가
    $pagination.append(`<li class="page-item ${currentPage === 1 ? 'disabled' : ''}">
                            <a class="page-link" href="#" onclick="changePage(${currentPage - 1})" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>`);

    let pageStart = Math.max(1, currentPage - 2);
    let pageEnd = Math.min(totalPages, currentPage + 2);

    // 현재 페이지가 1 또는 2일 경우, pageEnd를 5로 제한
    if (currentPage <= 2) {
        pageEnd = Math.min(5, totalPages);
    }

    // 현재 페이지와 totalPages의 차이가 0 또는 1일 경우, pageStart를 조정
    if (totalPages - currentPage <= 1) {
        pageStart = Math.max(1, totalPages - 4);
    }

    for (let i = pageStart; i <= pageEnd; i++) {
        $pagination.append(`<li class="page-item ${currentPage === i ? 'active' : ''}">
                                <a class="page-link" href="#" onclick="changePage(${i})">${i}</a>
                            </li>`);
    }

    // 다음 페이지로 이동하는 버튼 추가
    $pagination.append(`<li class="page-item ${currentPage === totalPages ? 'disabled' : ''}">
                            <a class="page-link" href="#" onclick="changePage(${currentPage + 1})" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>`);
    //맨 끝 페이지로 이동하는 버튼 추가
    $pagination.append(`<li class="page-item ${currentPage === totalPages ? 'disabled' : ''}">
                            <a class="page-link" href="#" onclick="changePage(${totalPages})" aria-label="Next">
                                <span aria-hidden="true">&raquo;&raquo;</span>
                            </a>
                        </li>`);
}

// 페이지 변경 시 동작하는 함수
function changePage(page) {
    if (page >= 1 && page <= totalPages) {
        loadPageBoard(page, $("#province").val(), $("#district").val());
    }
}