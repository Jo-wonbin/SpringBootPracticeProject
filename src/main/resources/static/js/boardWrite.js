function saveBoardFormSubmit() {
    // 입력된 데이터 가져오기

    const files = $("#file").files;

    for(let i=0; i<files.length; i++){
        console.log(files[i]);
    }

    alert("버튼 작동 됌.");
    return false;

}