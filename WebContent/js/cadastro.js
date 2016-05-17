IAN.usuario = new Object();

	IAN.usuario.alertSuccess = function(msg) {
		$.notify({
			title: '<strong>Sucesso!</strong>',
			message: msg
		},{
			type: 'success'
		});
	}
	
	IAN.usuario.alertError = function(msg) {
		$.notify({
			title: '<strong>Erro!</strong>',
			message: msg
		},{
			type: 'danger'
		});
	}

	$(document).ready(function(){

		IAN.usuario.cadastrar = function() {
			var newUser = new Object();
			
			newUser.nome = $("#nome").val();
			newUser.email = $("#email").val();
			newUser.senha = $("#senha").val();
			newUser.confirmarSenha = $("#confirmarSenha").val();

			var	cfg = {
					url: "rest/usuarioRest/cadastrarUsuario",
					data: newUser,
					success: function (usuario) {
						IAN.usuario.login(usuario);
					},
					error: function(rest) {
						IAN.usuario.alertError("Erro no cadastro do Usuário")
					}	
			}
			
			IAN.ajax.post(cfg);
		}
		
		IAN.usuario.login = function(usuario) {
			if(usuario == "email já cadastrado") {
				IAN.usuario.alertError("email já cadastrado")
				return false;
			} else {
				$("#emailUsuario").val(usuario.email);
				$("#senhaUsuario").val(usuario.senha);
				$("#formIndex").submit();
			};
		};
	});