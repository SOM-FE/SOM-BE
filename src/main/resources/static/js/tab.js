//tab 전환
function onDisplay(no){
    if(no == 3){
        alert("단건 조회를 원하시면 전체/카테고리별 조회에서 원하시는 질문을 클릭해주세요");
        return;
    }
    for (var i = 1; i < 5; i++) {
        $('#section'+i).hide();
        document.getElementById('section'+i+'link').classList.remove ("current");
        document.getElementById('section'+i+'link').classList.add("sidebar-link");
    }
    
    document.getElementById('section'+no+'link').classList.add ("current");
    document.getElementById('section'+no+'link').classList.remove("sidebar-link");

    $('#section'+no).show();
}

$(document).ready(function() {
    $('#section1').show();
    $('#section2').hide();
    $('#section3').hide();
    $('#section4').hide();
});
