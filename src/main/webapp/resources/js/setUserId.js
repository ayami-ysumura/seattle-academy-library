$(function(){
	var userId = $('#userId').val();
	
	if(userId.length !== 0){
	window.sessionStorage.setItem(['userId'],[userId]);
	}
});