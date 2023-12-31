//각 반별로 게시판 권한 주기
$(document).ready(function() {
  var member_class = document.body.getAttribute('data-member-class');
  var member_type = document.body.getAttribute('data-member-type');
  console.log(member_class);

  // 클릭 이벤트 캔슬 함수
  function cancelClickEvent(links) {
    links.forEach(function(link) {
      link.addEventListener("click", function(event) {
        event.preventDefault(); // 클릭 이벤트를 캔슬합니다.
      });
    });
  }

  var sideClass1 = document.querySelectorAll(".sideBoard1");
  var sideClass2 = document.querySelectorAll(".sideBoard2");
  var sideClass3 = document.querySelectorAll(".sideBoard3");
  var sideClass4 = document.querySelectorAll(".sideBoard4");

  if (member_type === "강사님" || member_type === "관리자") {

  } else if (member_class === "1반") {
    cancelClickEvent(sideClass2);
    cancelClickEvent(sideClass3);
    cancelClickEvent(sideClass4);
  } else if (member_class === "2반") {
    cancelClickEvent(sideClass1);
    cancelClickEvent(sideClass3);
    cancelClickEvent(sideClass4);
  } else if (member_class === "3반") {
    cancelClickEvent(sideClass1);
    cancelClickEvent(sideClass2);
    cancelClickEvent(sideClass4);
   } else if (member_class === "4반") {
     cancelClickEvent(sideClass1);
     cancelClickEvent(sideClass2);
     cancelClickEvent(sideClass3);
   }

   // 메뉴 공통 기능
   let menu = $('.menu');
   let sidebar = $('.sidebar');
   let mainContent = $('.main--content');

   menu.click(function() {
     sidebar.toggleClass('active');
     mainContent.toggleClass('active');
   });
});