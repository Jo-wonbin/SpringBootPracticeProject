// 실행 시 지역구 불러오기 작동
$(document).ready(function () {
    loadProvinces();
    // fetchBoardData();
});

function loadBoards(provinceId) {
    /*
        1. 전체 / 광역시별 / 지역구별 분기
        2. 데이터 불러오기
        3. 데이터 화면 출력
     */
    if (provinceId === 0) {

        // 전체 데이터 조회
        $.get('/board/all', function (data) {
            // 서버에서 받은 배열을 순회하면서 옵션을 추가
            console.log("전체 데이터 조회");
            displayBoardData(data);
        });
    } else {
        // 광역시의 데이터 조회
        $.get('/board/province/' + provinceId, function (data) {
            // 서버에서 받은 배열을 순회하면서 옵션을 추가
            console.log("광역시 데이터 조회");
            displayBoardData(data);
        });
    }
}

function loadDistrictBoard() {
    const districtId = $("#district").val();
    if (districtId !== "zero") {
        $.get('/board/district/' + districtId, function (data) {
            // 서버에서 받은 배열을 순회하면서 옵션을 추가
            console.log("지역구 데이터 조회");
            displayBoardData(data);
        });
    }
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
        const row = `<tr onclick="detailBoard(${board.id})">
                            <td>${board.id}</td>
                            <td>${board.boardTitle}</td>
                            <td>${board.boardWriter}</td>
                            <td>${board.boardHits}</td>
                            <td>${board.boardCreatedTime}</td>
                            <td>${board.provinceName}</td>
                            <td>${board.districtName}</td>
                        </tr>`;
        $tbody.append(row);
    }
}

// 광역시 선택 시 동작
function updateDistricts() {
    const province = $('#province').val();
    const districtSelect = $('#district');

    // 기존 옵션 제거
    districtSelect.empty();
    if(province === "zero"){
        districtSelect.append("<option value=" + "zero" + " selected>전체</option>");
        loadBoards(0);
    }else {
        // Ajax를 통해 서버에서 데이터 동적으로 불러오기
        $.get('/area/district/' + province, function (data) {
            // 새로운 옵션 추가
            console.log(data);
            districtSelect.append("<option value=" + "zero" + " selected>전체</option>");
            data.forEach(function (districts) {
                districtSelect.append('<option value="' + districts.id + '">' + districts.name + '</option>');
            });
        });
        loadBoards(province);
    }
}

// 지역구 불러오기
function loadProvinces() {
    const provinceSelect = $('#province');

    const provinceSelectValue = provinceSelect.val();
    if (provinceSelectValue === "zero") {
        $('#district').append("<option value=" + "zero" + " selected>전체</option>");
        loadBoards(0);
    }

    // Ajax를 통해 서버에서 광역시 정보 동적으로 불러오기
    $.get('/area/province', function (data) {
        // 서버에서 받은 배열을 순회하면서 옵션을 추가
        console.log(data);
        data.forEach(function (province) {
            provinceSelect.append('<option  value="' + province.id + '">' + province.name + '</option>');
        });
    });


}

// 게시글 작성 버튼 클릭 이벤트 핸들러
function boardWrite() {
    console.log("게시글 이동 버튼 클릭");
    location.href = "/board/boardWrite";
};