var domain = "http://localhost:8080/admin";

//받아온 data 한글 변환
function changeKorean(data){
    for(var i=0; i<data.content.length; i++){
        // 카테고리 변경
        switch(data.content[i].category){
            case "INDIVIDUAL":
                data.content[i].category = "개인";
                break;
            case "AFFECTION":
                data.content[i].category = "애정";
                break;
            case "COMMUNICATION":
                data.content[i].category = "의사소통";
                break;
            case "FINANCE":
                data.content[i].category = "경제";
                break;
            case "RELATIONSHIP":
                data.content[i].category = "관계";
                break;
            case "ROLE":
                data.content[i].category = "역할";
                break;
            case "UPBRINGING":
                data.content[i].category = "양육";
                break;
        }
        // 타겟 변경
        switch(data.content[i].target){
            case "COMMON":
                data.content[i].target = "공통";
                break;
            case "COUPLE":
                data.content[i].target = "커플";
                break;
            case "MARRIED":
                data.content[i].target = "기혼";
                break;
            case "FAMILY":
                data.content[i].target = "가족_공통";
                break;
            case "PARENT":
                data.content[i].target = "부모";
                break;
            case "CHILD":
                data.content[i].target = "자녀";
                break;
        }
    }
}

//전체 조회
function showTableAll(page, sectionNo){
    $.ajax({
        type: 'GET',
        url: domain + "/questions?category=all&size=60&page="+page,
        contentType: 'application/json',
        success: function(mydata){
            changeKorean(mydata);
            document.getElementById("questionList").innerHTML='';
            document.getElementById("section"+sectionNo+"_page").innerHTML='';
             for(var i=0; i<mydata.content.length; i++){
                 $("#questionList").append('<tr><th>'+(page*60+i+1)+'</th><th>'+mydata.content[i].target+'</th><th onclick="singleFind('+mydata.content[i].id+')" style="cursor:pointer;">'+mydata.content[i].question+'</th><th>'+mydata.content[i].category+'</th><th>'+mydata.content[i].isAdult+'</th></tr>');
             }
             for(var i=1; i<mydata.totalPage+1; i++){
                 $("#section"+sectionNo+"_page").append("<li class='page-item'><a href='#' class='page-link py-2 px-3' id='section"+sectionNo+"_page_"+i+"' onclick='showTableAll("+(i-1)+", "+sectionNo+")'>"+i+"</a></li>");
             }
             var currentPageNo = "section"+sectionNo+"_page_"+(mydata.currentPage+1);
             document.getElementById(currentPageNo).classList.add("active");
        },
        error: function(){
            alert("Error");
        }
    })
}

//카테고리별 조회
function showTableBySelect(page, sectionNo){
    var category = $("#select_category option:selected").val();
    $.ajax({
        type: 'GET',
        url: domain + "/questions?category="+category+"&size=60&page="+page,
        contentType: 'application/json',
        success: function(mydata){
            changeKorean(mydata);
            document.getElementById("selectQuestionList").innerHTML='';
            document.getElementById("section"+sectionNo+"_page").innerHTML='';
            for(var i=0; i<mydata.content.length; i++){
                $("#selectQuestionList").append("<tr><th>"+(page*60+i+1)+"</th><th>"+mydata.content[i].target+"</th><th onclick='singleFind("+mydata.content[i].id+")' style='cursor:pointer;'>"+mydata.content[i].question+"</th><th>"+mydata.content[i].category+"</th><th>"+mydata.content[i].isAdult+"</th></tr>");
            }
            for(var i=1; i<mydata.totalPage+1; i++){
                $("#section"+sectionNo+"_page").append("<li class='page-item'><a href='#' class='page-link py-2 px-3' id='section"+sectionNo+"_page_"+i+"' onclick='showTableBySelect("+(i-1)+", "+sectionNo+")'>"+i+"</a></li>");
            }
            var currentPageNo = "section"+sectionNo+"_page_"+(mydata.currentPage+1);
            document.getElementById(currentPageNo).classList.add("active");
        },
        error: function(){
            alert("Error");
        }
    })
}
//단건 조회
function singleFind(id){
    document.getElementById("question").innerHTML = '';
    document.getElementById("buttonGroup").innerHTML = '';
    for (var i = 1; i < 4; i++) {
        $('#section'+i).hide();
        document.getElementById('section'+i+'link').classList.remove ("current");
        document.getElementById('section'+i+'link').classList.add("sidebar-link");
    }
    $('#section3').show();
    document.getElementById('section3link').classList.add ("current");
    document.getElementById('section3link').classList.remove("sidebar-link");
    $.ajax({
        type: 'GET',
        url: domain + "/questions/"+id,
        contentType: 'application/json',
        success: function(mydata){
            $("#question").append("<form style='display: flex;'><textarea id='questionArea' style='width: 80%; margin: 20px auto; padding: 7px;' rows='10' cols='10'>"+mydata.question+"</textarea></form>")
            $("#buttonGroup").append('<button type="button" class="btn btn-info btn-sm" data-bs-toggle="modal" data-bs-target="#modify" onclick="questionModify('+mydata.id+')" style="width: 40%;">수정하기</button><button type="button" onclick="questionDeleteCheck('+mydata.id+')" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#delete" style="width: 40%;">삭제하기</button>');
            $("#modify_category").val(mydata.category).prop("selected", true);
            $("#modify_target").val(mydata.target).prop("selected", true);
            $("#modify_adult").val(mydata.isAdult).prop("selected", true);
        },
        error: function(){
            alert("Error");
        }
    })
}

// 질문 수정
function modify(id){
    $.ajax({
        type: 'PUT',
        url: domain + "/questions/"+id,
        contentType: 'application/json',
        data: JSON.stringify({
            "target": $("#modify_target option:selected").val(),
            "question": $("#questionArea").val(),
            "isAdult": $("#modify_adult option:selected").val(),
            "category": $("#modify_category option:selected").val(),
        }),
        success: function(){
            alert("성공적으로 수정되었습니다.");
            location.reload();
        },
        error: function(){
            alert("Error");
        }
    })
}

//질문 수정 확인
function questionModify(id){
    $("#modal_modify").empty();
    $("#modify-modal-footer").empty();
    $("#modal_modify").append("카테고리: "+$("#modify_category option:selected").text()+"<br>분류: "+$("#modify_target option:selected").text()+"<br>질문: "+$("#questionArea").val()+"<br>성인여부: "+$("#modify_adult option:selected").text());
    $("#modify-modal-footer").append('<button type="button" onclick="modify('+id+')" class="btn btn-success" data-bs-dismiss="modal">수정하기</button>')
    $("#modify-modal-footer").append('<button type="button" class="btn btn-danger close" data-bs-dismiss="modal">취소</button>')
}
//질문 삭제
function questionDelete(id){
    $.ajax({
        type: 'DELETE',
        url: domain + "/questions/"+id,
        contentType: 'application/json',
        success: function(){
            alert("성공적으로 삭제되었습니다.");
            location.reload();
        },
        error: function(){
            alert("Error");
        }
    })
}

//질문 삭제 확인
function questionDeleteCheck(id){
    $("#delete-modal-footer").empty();
    $("#delete-modal-footer").append('<button type="button" onclick="questionDelete('+id+')" class="btn btn-success" data-bs-dismiss="modal">삭제하기</button>')
    $("#delete-modal-footer").append('<button type="button" class="btn btn-danger close" data-bs-dismiss="modal">취소</button>')
}

//질문 추가하기
function addQuestion(){
    $.ajax({
        type: 'POST',
        url: domain + "/questions",
        contentType: 'application/json',
        data: JSON.stringify({
            "target": $("#add_target").val(),
            "question": $("#addQuestionArea").val(),
            "isAdult": $("#add_adult").val(),
            "category": $("#add_category").val()
        }),
        success: function(){
            alert("성공적으로 추가되었습니다.");
            location.reload();
        },
        error: function(){
            alert("Error");
        }
    })
}

// 질문 추가하기 check
function addQuestionCheck(){
    $("#add-modal-footer").empty();
    $("#add-modal-body").empty();
    $("#add-modal-body").append("카테고리: "+$("#add_category option:selected").text()+"<br>분류: "+$("#add_target option:selected").text()+"<br>질문: "+$("#addQuestionArea").val()+"<br>성인여부: "+$("#add_adult option:selected").text());
    $("#add-modal-footer").append('<button type="button" onclick="addQuestion()" class="btn btn-success" data-bs-dismiss="modal">추가하기</button>')
    $("#add-modal-footer").append('<button type="button" class="btn btn-danger close" data-bs-dismiss="modal">취소</button>')
}

// 로그인 여부 검사(비정상적인 접근 차단)
function checkLogin(){
    if(sessionStorage.getItem("login") == "success"){
        return;
    }
    else{
        window.location.href = "index.html";
    }
}

// 로그 아웃
function logout(){
    sessionStorage.clear();
    window.location.href = "index.html";
}

$(document).ready(function() {
    showTableAll(0, 1);
    checkLogin();
});
