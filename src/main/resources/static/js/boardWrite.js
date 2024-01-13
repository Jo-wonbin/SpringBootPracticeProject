$(document).ready(function (){
   $("#postForm").submit(function (event){

       event.preventDefault();

       // FormData 객체 생성
       const formData = new FormData(this);

       // 서버로 데이터 전송 (폼 액션 속성에 서버 URL을 지정할 수도 있음)
       $.ajax({
           type: "POST",
           url: "/board/save",
           data: formData,
           contentType: false,
           processData: false,
           success: function (response) {
               // 서버 응답에 따라 알림창 표시
               alert(response);

               // 성공 여부에 따라 페이지 이동
               if (response === "게시글 작성 성공") {
                   window.location.href = "/board"; // 성공 페이지로 이동
               }
           },
           error: function (error) {
               console.error("Ajax 요청 실패:", error);
           }
       });
   }) ;
});