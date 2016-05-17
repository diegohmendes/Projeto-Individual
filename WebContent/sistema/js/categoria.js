IAN.categoria = new Object();

$(document).ready(function(){
	
	//função para condição de cadastro ou edição
	IAN.categoria.modal = function(id) {
		console.log(id);
		$("#idCat").val(id);
		if(id == undefined) {
			$("#genericCategoria").modal();
		} else {
			IAN.categoria.buscarPorId();
		};
	};
		
	//método para buscar a categoria para setar no modal
	IAN.categoria.buscarPorId = function() {
		var id;
		var	cfg;
		
		id = $("#idCat").val();
		cfg = {
			url: "../rest/categoriaRest/buscarPorId/"+id,
			//ajustar para retornar somente a descrição;
			success: function(categoria) {
				$("#descricaoCategoria").val(categoria.descricao);
				$("#genericCategoria").modal();
			},
			error: function(msg) {
				//Incluir alerta
				alert("erro");
			}
		};
		IAN.ajax.post(cfg);
	};
		
	//método que cadastra a categoria
	IAN.categoria.incluir = function() {
		var categoria = new Object();
		var	id;
		var	url;
		
		id = $("#idCat").val();
		categoria.descricao = $("#descricaoCategoria").val();
		categoria.id = id;
		
		if(id == ''){
			url = "../rest/categoriaRest/cadastrarCategoria"
		} else {
			url = "../rest/categoriaRest/editarCategoria"
		}

		cfg = {
			url: url,
			data: categoria,
			success: function(categoria) {
				var	html =	"<td class='span10'>"+ categoria.descricao +"</td>" +
							"<th class='span1'><a href='#' onclick='IAN.categoria.modal("+categoria.id+")' role='button' data-toggle='modal'><i class='icon-pencil'></i></a></th>" +
							"<th class='span1'><a href='#' onclick='IAN.categoria.excluirValidacao("+categoria.id+")'><i class='icon-remove'></i></a></th>";
				if(id == ""){
					$("#exibirCategoria tr:last").after(html)
				} else {
					$("#item-categoria-"+categoria.id).html(html);
				}
				$("#genericCategoria").modal('hide');
				$("input").val('');
				
				IAN.alert.alertSuccess("Informações inseridas!")
			},
			error: function(msg) {
				IAN.categoria.alertError("Problema ao inserir informações");
			}
		}; //Fim do cfg
		IAN.ajax.post(cfg);
	};
	
	//método para excluir a categoria
	IAN.categoria.excluir = function(){
		var id = $("#idExcluirCat").val();
		
		cfg = {
			type: "POST",
			url: "../rest/categoriaRest/removerCategoria/"+id,
			success: function(msg) {
				if(msg) {
					$("#item-categoria-"+id).remove();
					$("#removeCat").modal('hide');
					IAN.alert.alertSuccess("Categoria removida");
				} else {
					IAN.alert.alertError("A categoria esta vinculada a uma movimentacao");
				}
			},
			error: function(rest) {
				IAN.categoria.alertError("Problema na remoção");
				$("#removeCat").modal('hide');
			}
		}; //Fim do cfg
		IAN.ajax.post(cfg);
	};
	
	//metodo que busca todas as categorias para o table dinâmico
	IAN.categoria.buscar = function(){
		var cfg = {
			url: "../rest/categoriaRest/exibirCategoria",
			success: function(listaDeCategorias) {
			 var html = "<table class='table table-bordered table-hover'>"+
							"<thead>" +
								"<tr class=''>"	+
									"<th class='span10'>Descrição</th>" +
									"<th class='span1'>Editar</th>" +
									"<th class='span1'>Excluir</th>" +
								"</tr>" +
							"</thead>";
							
				for(var i = 0; i < listaDeCategorias.length; i++) {
					html +=	"<tbody>" +
								"<tr class='' id='item-categoria-"+ listaDeCategorias[i].id +"''>" +
									"<td class='span10'>"+ listaDeCategorias[i].descricao +"</td>" +
										"<th class='span1'><a href='#' onclick='IAN.categoria.modal("+listaDeCategorias[i].id+")' role='button' data-toggle='modal'><i class='icon-pencil'></i></a></th>" +
										"<th class='span1'><a href='#' onclick='IAN.categoria.excluirValidacao("+listaDeCategorias[i].id+")'><i class='icon-remove'></i></a></th>" +
								"</tr>";
				}

				html += 	"</tbody>" +
						"</table>";

			$("#exibirCategoria").html(html);

			},

			error: function(rest) {
				IAN.categoria.alertError("Problema na busca");
			}

		}; //Fim do cfg
		IAN.ajax.post(cfg);
	};
	
	//método para validação da exclusão
	IAN.categoria.excluirValidacao = function(id) {
		$("#idExcluirCat").val(id);
		$("#removeCat").modal();
	};
});