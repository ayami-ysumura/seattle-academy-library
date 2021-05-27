$(function(){
	const rentStatus = $('.rentalStatus').val();
	if(rentStatus==='貸し出し可'){
		$('.btn_returnBook').prop('disabled',true);
	} else if(rentStatus==='貸し出し中'){
		$('.btn_rentBook').prop('disabled',true);
		$('.btn_deleteBook').prop('disabled',true);
	}
});