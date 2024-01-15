// 실행 시 지역구 불러오기 작동
$(document).ready(function () {
    loadProvinces();
    fetchBoardData();
});

// Ajax 통신을 사용하여 게시글 데이터를 불러오는 함수
function fetchBoardData() {

    $.ajax({
        url: '/board/all',  // 실제 서버 엔드포인트를 입력해야 합니다.
        method: 'GET',
        success: function (data) {
            // 성공적으로 데이터를 받아왔을 때의 처리
            displayBoardData(data);
        },
        error: function (error) {
            // 에러 발생 시의 처리
            console.error('Error fetching board data:', error);
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

    // Ajax를 통해 서버에서 데이터 동적으로 불러오기
    $.get('/area/district', {provinceName: province}, function (data) {
        // 새로운 옵션 추가
        console.log(data);
        districtSelect.append("<option value='0' selected>전체</option>");
        data.forEach(function (districts) {
            districtSelect.append('<option value="' + districts.id + '">' + districts.name + '</option>');
        });
    });
}

// 지역구 불러오기
function loadProvinces() {
    const provinceSelect = $('#province');

    const provinceSelectValue = provinceSelect.val();
    if (provinceSelectValue === 0) {
        $('#district').append("<option value='0' selected>전체</option>");
        loadBoards(0, 0);
        return;
    }

    // Ajax를 통해 서버에서 광역시 정보 동적으로 불러오기
    $.get('/area/province', function (data) {
        // 서버에서 받은 배열을 순회하면서 옵션을 추가
        console.log(data);
        data.forEach(function (province) {
            provinceSelect.append('<option  value="' + province.name + '">' + province.name + '</option>');
        });
    });
}

function loadBoards(province, district) {
    $.get('/board/all', function (data) {
        // 서버에서 받은 배열을 순회하면서 옵션을 추가
        console.log(data);
        data.forEach(function (board) {
            provinceSelect.append('<option  value="' + province.name + '">' + province.name + '</option>');
        });
    });
}

// 게시글 작성 버튼 클릭 이벤트 핸들러
function boardWrite() {
    console.log("게시글 이동 버튼 클릭");
    location.href = "/board/boardWrite";
};