// 실행 시 지역구 불러오기 작동
$(document).ready(function () {
    loadProvinces();

    $("#signUpForm").submit(function (event) {

        event.preventDefault();

        const check = checkPassword();

        if (check === 2) {
            alert("비밀번호를 확인해주세요.");
            return;
        }

        const province = $("#province").val();
        const district = $("#district").val();

        if (province === "noneProvince" || district === "noneDistrict") {

            alert("광역시와 지역구를 모두 선택하세요.");

        } else {
            // FormData 객체 생성
            const formData = new FormData(this);

            // 서버로 데이터 전송 (폼 액션 속성에 서버 URL을 지정할 수도 있음)
            $.ajax({
                type: "POST",
                url: "/member/save",
                data: formData,
                contentType: false,
                processData: false,
                success: function (response) {
                    // 서버 응답에 따라 알림창 표시
                    alert(response);

                    // 성공 여부에 따라 페이지 이동
                    if (response === "회원가입 성공") {
                        window.location.href = "/member/login"; // 성공 페이지로 이동
                    }
                },
                error: function (error) {
                    console.error("Ajax 요청 실패:", error);
                }
            });
        }
    });
});

function checkPassword() {
    // 비밀번호와 비밀번호 확인 값을 가져옴
    const password = $("#password").val();
    const confirmPassword = $("#confirmPassword").val();

    // 비밀번호와 비밀번호 확인이 일치하는지 확인
    if (password === confirmPassword) {
        // 비밀번호 일치 시 에러 메시지 삭제
        const pwdError = $("#passwordError");
        pwdError.color = "green";
        pwdError.text("비밀번호가 일치합니다.");
        return 1;
    } else {
        // 비밀번호 불일치 시 에러 메시지 표시
        const pwdError = $("#passwordError");
        pwdError.color = "red";
        pwdError.text("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
        return 2;
    }
}

// 지역구 불러오기
function loadProvinces() {
    const provinceSelect = $('#province');

    // Ajax를 통해 서버에서 광역시 정보 동적으로 불러오기
    $.get('/area/province', function (data) {
        // 서버에서 받은 배열을 순회하면서 옵션을 추가
        console.log(data);
        data.forEach(function (province) {
            provinceSelect.append('<option  value="' + province.id + '">' + province.name + '</option>');
        });
    });
}

// 광역시 선택 시 동작
function updateDistricts() {
    const province = $('#province').val();
    const districtSelect = $('#district');

    // 기존 옵션 제거
    districtSelect.empty();

    // Ajax를 통해 서버에서 데이터 동적으로 불러오기
    $.get('/area/district/'+province, function (data) {
        // 새로운 옵션 추가
        console.log(data);
        data.forEach(function (districts) {
            districtSelect.append('<option value="' + districts.id + '">' + districts.name + '</option>');
        });
    });
}

// 이메일 중복 체크
const emailCheck = () => {
    const email = document.getElementById("memberEmail").value;
    const checkResult = document.getElementById("check-result");
    console.log("입력값: ", email);
    $.ajax({
        // 요청방식: post, url: "email-check", 데이터: 이메일
        type: "post",
        url: "/member/email-check",
        data: {
            "memberEmail": email
        },
        success: function (res) {
            console.log("요청성공", res);
            if (res == "ok") {
                console.log("사용가능한 이메일");
                checkResult.style.color = "green";
                checkResult.innerHTML = "사용가능한 이메일입니다.";
            } else {
                console.log("이미 사용중인 이메일");
                checkResult.style.color = "red";
                checkResult.innerHTML = "이미 사용중인 이메일입니다.";
            }
        },
        error: function (err) {
            console.log("에러발생", err);
        }
    });
}