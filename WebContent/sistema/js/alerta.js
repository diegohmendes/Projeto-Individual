IAN.alert = new Object();

$(document).ready(function(){
		
	IAN.alert.alertSuccess = function(msg) {
		$.notify({
			title: '<strong>Sucesso!</strong>',
			message: msg
		},{
			type: 'success'
		});
	}

	IAN.alert.alertError = function(msg) {
		$.notify({
			title: '<strong>Erro!</strong>',
			message: msg
		},{
			type: 'danger'
		});
	}
});
