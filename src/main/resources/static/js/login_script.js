var domain = "http://localhost:8080/admin";

function login() {
    //login 성공시 로직
    let id = document.getElementById('id').value;
    let pw = document.getElementById('pw').value;

    if (id == "" || pw == "") {
        alert("모든 필드를 채워주세요");
        return;
    }

    adminLogin(id, pw)
}

// input 창에서 enter키 입력시 login() 함수 실행
function pressEnter() {
    if (window.event.keyCode === 13) {
        login();
    }
}

// 로그인 요청
function adminLogin(id, pw) {
    $.ajax({
        type: 'POST',
        url: domain + "/login",
        contentType: 'application/json',
        data: JSON.stringify({
            "adminId": id,
            "password": pw
        }),
        success: function (mydata) {
            if (mydata) {
                sessionStorage.clear();
                sessionStorage.setItem("login", "success");
                window.location.href = 'home.html';
            }

            else {
                alert("일치하는 정보가 없습니다.");
            }
        },
        error: function () {
            alert("Error");
        }
    })
}
