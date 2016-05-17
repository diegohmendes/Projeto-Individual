IAN.logout = new Object();

	$(document).ready(function() {
		
		IAN.logout.sair = function() {
			
			var cfg = {
					url: "../Logout",
					type: "POST",
					
					success: function (msg) {
						window.location.assign("/controleFinanceiroPessoal/index.html");
					},
					error: function (msg) {
						alert("Erro");
					}
			};
			
			IAN.ajax.post(cfg);
		};
	});
