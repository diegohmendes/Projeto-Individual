IAN.login = new Object();

	$(document).ready(function() {

		IAN.login.recuperarLogin = function() {

			var cfg = {
				url: "../rest/usuarioRest/recuperarLogin",

				success: function(email) {
					$("#user").html(email);
				},

				error: function(rest) {
					alert(rest);
				}
			};

			IAN.ajax.post(cfg);

		};

		IAN.login.recuperarLogin();

	});