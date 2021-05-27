$(function(){
	const favoStatus = $('.favoStatus').val();
	if(favoStatus==='favo'){
		$('.favo').prop('disabled',true);
		$('.noFavo').css("background-color","skyblue");
	} else if(favoStatus==='noFavo'){
		$('.noFavo').prop('disabled',true);
		$('.favo').css("background-color","skyblue");
	}
});