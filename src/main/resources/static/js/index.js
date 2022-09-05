/**
 * 
 */
 
function popOpen() {

    var modalPop = $('.modal-wrap');
    var modalBg = $('.modal-bg'); 

    $(modalPop).show();
    $(modalBg).show();

}

function popClose() {
   var modalPop = $('.modal-wrap');
   var modalBg = $('.modal-bg');

   $(modalPop).hide();
   $(modalBg).hide();

}
function kakaoLogin(){
	var url="https://kauth.kakao.com/oauth/authorize?client_id=c6cc4fe0f14cdce36a10b177251de0bb&redirect_uri=http://localhost:8080/login&response_type=code"
	window.location.href = url;
}
function kakaoLogout(){
	
	var url="https://kauth.kakao.com/oauth/logout?client_id=c6cc4fe0f14cdce36a10b177251de0bb&logout_redirect_uri=http://localhost:8080/logout"
	window.location.href = url;
	
	
}