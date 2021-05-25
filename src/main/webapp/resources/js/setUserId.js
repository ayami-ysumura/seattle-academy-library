$(function(){
	var userId = $('#userId').val();
	
	if(userId.length !== 0){
	window.sessionStorage.setItem(['userId'],[userId]);
	}
	//getUserId = window.sessionStorage.getItem(['userId']);
//console.log(getUserId);
	//$('#giveEmail').prop('value',getUserId);
});