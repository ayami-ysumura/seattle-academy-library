$(function(){
	const favoStatus = $('.favoStatus').val();
	if(favoStatus==='1'){
		$('.favo').prop('disabled',true);
		$('.noFavo').css("background-color","skyblue");
	}else if(favoStatus==='0'){
		$('.noFavo').prop('disabled',true);
		$('.favo').css("background-color","skyblue");
	}
});