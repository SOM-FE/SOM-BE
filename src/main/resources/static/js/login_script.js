function login(){
    //login 성공시 로직
    let id = document.getElementById('id').value;
    let pw = document.getElementById('pw').value;

    if(id == "" || pw == ""){
        alert("모든 필드를 채워주세요");
        return;
    }

    if(id == "admin"){
        if(pw == "admin"){
            sessionStorage.clear();
            sessionStorage.setItem("login", "success");
            window.location.href = 'home.html';
        }
        else{
            alert("일치하는 정보가 없습니다.");
        }
    }
    else{
        alert("일치하는 정보가 없습니다.");
    }
}

// input 창에서 enter키 입력시 login() 함수 실행
function pressEnter() {
    if (window.event.keyCode === 13) {
         login();
    }
}
