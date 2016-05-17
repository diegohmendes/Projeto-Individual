IAN.objetivo = new Object();

$(document).ready(function(){

	//Método para inserir valor do objetivo
	IAN.objetivo.inserir = function() {

		var objetivo = new Object();

		objetivo.descricao = $("#descricaoObjetivo").val();
		objetivo.fValor = $("#valorObjetivo").maskMoney('unmasked')[0];
		
		var cfg = {
			url: "../rest/objetivoRest/cadastrarObjetivo",
			data: objetivo,
			success: function(objetivo) {
				var html =  "<tr class='' id='item-objetivo-"+ objetivo.id +"'>" +
								"<td class='span3'>"+ objetivo.descricao +"</td>" + 
								"<td class='span1'>"+ objetivo.sValor +"</td>" + 
								"<td class='span6'>" + 
								"<dsiv class='progress progress-striped active'>" +
									"<div class='bar' style='width: "+ objetivo.fValorPercent+"%;'>"+ objetivo.fValorPercent +"%</div>" +
									"</div>" +
								"</td>" +
								"<td class='span1'><a href='#' onclick='IAN.objetivo.addValorCorrente("+ objetivo.id +")' role='button' data-toggle='modal'><i class='icon-plus'></i></a></td>" +
								"<td class='span1'><a href='#' onclick='IAN.objetivo.removerObjetivo("+ objetivo.id +")' role='button' data-toggle='modal'><i class='icon-remove'></i></a></td>" +				
							"</tr>";
				$('#exibirObjetivos tr:last').after(html);
				$("#modalObjetivo").modal('hide');
				$("input").val('');
				IAN.alert.alertSuccess("Objetivo cadastrado");
			},
			error: function(msg) {
				IAN.alert.alertError("Problema na inclusão do objetivo");
			}
		};
		IAN.ajax.post(cfg);
	};
		
	//Método para inserir valor do objetivo
	IAN.objetivo.buscar = function() {
		var cfg = {
			url: "../rest/objetivoRest/buscarObjetivo",
			type: "POST",
			success: function(listaDeObjetivos) {
				var html = "<table class='table table-bordered table-hover info'>" +
							"<thead>" +
									"<tr>" +
											"<th class='span3'>Descrição</th>" +
											"<th class='span2'>Valor</th>" +
											"<th class='span5'>Progresso</th>" +
											"<th class='span1'>Adicionar</th>" +
											"<th class='span1'>Excluir</th>" +
									"</tr>" +
							"</thead>";
					html += "<tbody>";
					for (var i = 0; i < listaDeObjetivos.length; i++) {
						if(listaDeObjetivos[i].status == 1 ) {
						} else {
						html +=	"<tr class='' id='item-objetivo-"+ listaDeObjetivos[i].id +"'>" +
									"<td class='span3'>"+ listaDeObjetivos[i].descricao +"</td>" + 
									"<td class='span1'>"+ listaDeObjetivos[i].sValor +"</td>" + 
									"<td class='span6'>" + 
										"<div class='progress progress-striped active' id='id-div-objetivo'>" +
											"<div class='bar' style='width: "+listaDeObjetivos[i].fValorPercent+"%;'>"+listaDeObjetivos[i].fValorPercent+"%</div>" +
 										"</div>" +
									"</td>" +
									"<td class='span1'><a href='#' onclick='IAN.objetivo.addValorCorrente("+ listaDeObjetivos[i].id +")' role='button' data-toggle='modal'><i class='icon-plus'></i></a></td>" +
								"<td class='span1'><a href='#' onclick='IAN.objetivo.removerObjetivo("+ listaDeObjetivos[i].id +")' role='button' data-toggle='modal'><i class='icon-remove'></i></a></td>" +
								"</tr>";
						}
					};
					html += "</tbody>" +
								"</table>";
					IAN.principal.saldoTotal();
					$("#exibirObjetivos").html(html);
				},
				error: function(msg) {
					IAN.alert.alertError("Problema na exibição");
				}
			};
			IAN.ajax.post(cfg);
		};

	//Chama o modal para adicionar o valor corrente
	IAN.objetivo.addValorCorrente = function(id) {
		$("#idValorCorrente").val(id);
		$("#modalCorrente").modal();
	}
    
	//método para adicionar o valor corrente
	IAN.objetivo.adicionarValor = function() {
		var objetivo = new Object();
		objetivo.id = $("#idValorCorrente").val();
		objetivo.fValorCorrente = $("#valorCorrente").maskMoney('unmasked')[0];
		var cfg = {
			url: "../rest/objetivoRest/addValorObjetivo",
			data: objetivo,
			success: function(objetivo) {		
				var html =  	"<td class='span3'>"+ objetivo.descricao +"</td>" + 
								"<td class='span1'>"+ objetivo.sValor +"</td>" + 
								"<td class='span6'>" + 
									"<div class='progress progress-striped active'>" +
										"<div class='bar' style='width: "+objetivo.fValorPercent+"%;'>"+ objetivo.fValorPercent +"%</div>" +
 									"</div>" +
								"</td>" +
								"<td class='span1'><a href='#' onclick='IAN.objetivo.addValorCorrente("+ objetivo.id +")' role='button' data-toggle='modal'><i class='icon-plus'></i></a></td>" +
								"<td class='span1'><a href='#' onclick='IAN.objetivo.removerObjetivo("+ objetivo.id +")' role='button' data-toggle='modal'><i class='icon-remove'></i></a></td>";


				$("#item-objetivo-"+objetivo.id).html(html);
				IAN.alert.alertSuccess("Valor cadastrado");
				IAN.principal.saldoTotal();
				$("#modalCorrente").modal('hide');
			},
			error: function(msg) {
				IAN.alert.alertError("Problema no cadastro");
			}
		};
		IAN.ajax.post(cfg);
	};

	//método para remover o objetivo sem valor adicionado
	IAN.objetivo.removerObjetivo = function(idObjetivo) {		
		var id = idObjetivo;
		var cfg = {
				type: "POST",
				url: "../rest/objetivoRest/verificarObjetivo/"+id,
				success: function(booleano) {
					console.log(booleano);
					if(booleano) {
						$("#item-objetivo-"+id).remove();
						IAN.alert.alertSuccess("Removido com sucesso");
					} else {
						$("#idInvisivel").val(idObjetivo);
						$('#statusObjetivo').modal();
					};
				},
				error: function(msg) {
					IAN.alert.alertError(msg);
				}
		}
		IAN.ajax.post(cfg);
	};

	//método para remover o objetivo e marcar como concluido ou voltar o valor para o saldo
	IAN.objetivo.removerObjetivoAdv = function() {
		debugger;
		var idObjetivo = $("#idInvisivel").val();
		if($("#statusObjetivo input[name='optObjetivo']:checked") == 0){
			optObjetivo = 0;
		} else {
			optObjetivo = 1;
		}
		//var optObjetivo = $("#statusObjetivo input[name='optObjetivo']:checked").val();
		var url;
		
		if(optObjetivo == 0) {
			url = "../rest/objetivoRest/removerObjetivoOk/"+idObjetivo;
		} else {
			url = "../rest/objetivoRest/concluirObjetivo/"+idObjetivo;
		}		
		var cfg = {
			type: "POST",
			url: url,
			success: function(msg) {
				$("#item-objetivo-"+idObjetivo).remove();
				$("#statusObjetivo").modal('hide');
				IAN.alert.alertSuccess("Seu objetivo foi removido");
			},
			error: function(msg) {
				IAN.alert.alertError(msg);
			}
		}
		IAN.ajax.post(cfg);
	};

	$("#valorObjetivo, #valorCorrente").maskMoney({prefix:'R$ ', allowNegative: true, thousands:'.', decimal:',', affixesStay: false});
});
