IAN.movimentacao = new Object();

$(document).ready(function(){
	
	//Função para chamada do modal
	IAN.movimentacao.modal = function(id){
		$("#idMov").val(id);
		if(id == undefined) {
			IAN.movimentacao.combo();
			$("#genericCadastro").modal();
		} else {
			IAN.movimentacao.buscarPorId(id);
			$("#genericCadastro").modal();
		}
	};
	
	//Função para condição (novo ou existente)
	IAN.movimentacao.cadastro = function() {
		debugger;
		var id;
		id = $("#idMov").val();
		IAN.movimentacao.Inserir(id);
	};
	
	//Método que busca as categorias para montar o combobox
	IAN.movimentacao.combo = function(movimentacao) {
		var html = "";
		cfg = {
			url: "../rest/categoriaRest/exibirCategoria",
			success: function(lista) {
				for (var i = 0; i < lista.length; i++) {
					if(movimentacao == undefined) {
						html += "<option value='"+lista[i].id+"'>"+lista[i].descricao+"</option>";
					} else if(movimentacao != "" && lista[i].id == movimentacao.idCategoria) {
							html += "<option selected value='"+lista[i].id+"'>"+lista[i].descricao+"</option>";
						} else {
							html += "<option value='"+lista[i].id+"'>"+lista[i].descricao+"</option>";
						}
				};
				if(movimentacao != undefined) {
					$("#valTipo").val(movimentacao.tipoMov);
					$("#idMov").val(movimentacao.id);
					if(movimentacao.tipoMov == 0){
						$('#receita').prop('checked','checked');
					} else {
						$('#despesa').prop('checked','checked');
					}
					//$("#genericCadastro input[name='tipoMovimentacao']:checked").val(movimentacao.tipo);
					$("#movDescricao").val(movimentacao.descricao);
					$("#movData").val(movimentacao.date);
					$("#valorMov").val(movimentacao.sValor); 
					$("#categoriaMov").html(html);
				}
				$("#categoriaMov").html(html);
			},
			error: function(msg) {
				alert(msg);
			}
		};
		IAN.ajax.post(cfg);
	};
	
	//Método que busca os valores da edição
	IAN.movimentacao.buscarPorId = function(id) {
		var cfg = {
			url: "../rest/movimentacaoRest/buscarMovimentacaoPorId/"+id,
			success: function(movimentacao) {
				IAN.movimentacao.combo(movimentacao);
			},
			error: function(msg) {
				alert("erro");
			}
		};
		IAN.ajax.post(cfg);
	};
	
	//Método para inserir nova movimentacão
	IAN.movimentacao.Inserir = function(id) {
		var newMov = new Object();
			cfg = "";
			url = "";
		if(id == "") {
			url = "../rest/movimentacaoRest/cadastrarMovimentacao"
		} else {
			url = "../rest/movimentacaoRest/editarMovimentacao"
		};
		
		newMov.id = $("#idMov").val();
		//aqui o problema, na segunda vez que pega o valor na 2 vez
		if($("input[name='tipoMovimentacao']:checked") == 0){
			newMov.tipoMov = 0;
		} else {
			newMov.tipoMov = 1;
		}
		//newMov.tipo = $("input[name='tipoMovimentacao']:checked").val();
		console.log(newMov.tipoMov);
		newMov.descricao = $("#movDescricao").val();
		newMov.sDate = $("#movData").val();
		newMov.fValor = $("#valorMov").maskMoney('unmasked')[0];	
		newMov.idCategoria = $("#categoriaMov").val();
		cfg = {
			url: url,
			data: newMov,
			success: function(movimentacao) {
				IAN.movimentacao.addTabela(movimentacao);
				IAN.principal.saldoTotal();
				IAN.alert.alertSuccess('sucesso');
				$('#genericCadastro').modal('hide');
				$("#genericCadastro").val('');
				$("input").val('');
				
				if(newMov.tipoMov != $("#valTipo").val()) {
					$("#item-movimentacao-"+id).remove();
				}	
			},
			error: function(rest) {
				IAN.alert.alertError("Erro");
			}
		};
		IAN.ajax.post(cfg);
	};
	
	//Método para adicionar na tabela
	IAN.movimentacao.addTabela = function(movimentacao){
		//Arrumar o tr da edição
		var	html = "";
			html = "<tr class='' id='item-movimentacao-"+ movimentacao.id +"'>";
			if($("#item-movimentacao-"+ movimentacao.id).val() == undefined) {
				html = IAN.movimentacao.criarLinha(movimentacao);
				if(movimentacao.tipoMov == 0) {
					$("#receitas tr:last").after(html);
				} else {
					$("#despesas tr:last").after(html);
				};
				IAN.principal.saldoTotal();
			} else {
				html = IAN.movimentacao.criarLinha(movimentacao);
				$("#item-movimentacao-"+movimentacao.id).html(html);
			}
			
			html += "</tr>";
	};
	
	//Método para criar a linha que será inserida na tabela
	IAN.movimentacao.criarLinha = function(movimentacao){
		//return  "<tr class='' id='item-movimentacao-"+ movimentacao.id +"'>" +
			return "<td class='span2'>"+ movimentacao.sDate +"</td>" +
					"<td class='span4'>"+ movimentacao.descricao +"</td>" +
					"<td class='span2'>"+ movimentacao.sValor +"</td>" +
					"<td class='span2'>"+ movimentacao.nomeCategoria+"</td>" +
					"<td class='span1'><a href='#' onclick='IAN.movimentacao.modal("+movimentacao.id+")' role='button' data-toggle='modal'><i class='icon-pencil'></i></a></td>" +
					"<td class='span1'><a href='#' onclick='IAN.movimentacao.excluirValidacao("+movimentacao.id+")'><i class='icon-remove'></i></a></td>";// +
				//"</tr>";
	};
	
	//Método que atribui o tipo de movimentação
	IAN.movimentacao.tipoReceita = function() {
		IAN.movimentacao.buscar(0);
	};
	
	IAN.movimentacao.tipoDespesa = function () {
		IAN.movimentacao.buscar(1);
	};
	
	//Método que busca as movimentações
	IAN.movimentacao.buscar = function(tipo){
		var cfg = "";
		
		cfg = {
			type: "POST",
			url: "../rest/movimentacaoRest/exibirMovimentacao/"+tipo,
			success: function(movimentacoes) {
			 var html = "<table class='table table-bordered table-hover'>"+
							"<thead>" +
								"<tr>"	+
									"<th class='span2'>Data</th>" +
									"<th class='span4'>Descrição</th>" +
									"<th class='span2'>Valor</th>" +
									"<th class='span2'>Categoria</th>" +
									"<th class='span1'>Editar</th>" +
									"<th class='span1'>Excluir</th>" +
								"</tr>" +
							"</thead>";
				html +=	"<tbody>";
				
				for(var i = 0; i < movimentacoes.length; i++) {
					html += "<tr class='' id='item-movimentacao-"+ movimentacoes[i].id +"'>";
					html +=	IAN.movimentacao.criarLinha(movimentacoes[i]);
					html += "</tr>";
				}
				
				html += 	"</tbody>" +
						"</table>";
				
				IAN.movimentacao.exibir(html, tipo);
				IAN.principal.saldoTotal();
			},
			error: function(rest) {
				alert("Problema na busca");
			}
		};
		IAN.ajax.post(cfg);
	};
	
	//Método para exibir na tabela
	IAN.movimentacao.exibir = function(html, tipo) {
		if(tipoMov == 0) {
			$("#exibirReceita").html(html);
		} else {
			$("#exibirDespesa").html(html);
		};
	};
	
	//Aciona modal para validação da exclusão da Movimentação
	IAN.movimentacao.excluirValidacao = function(id) {
		$("#idExcluir").val(id);
		$("#deleteQuestion").modal();
	};
	
	//método que exclui a movimentacao
	IAN.movimentacao.remover = function(){
		var id = "";
			cfg = "";
		
		id = $("#idExcluir").val();
		
		cfg = {
			type: "POST",
			url: "../rest/movimentacaoRest/removerMovimentacao/"+id,
			success: function(msg) {
				IAN.principal.saldoTotal();
				$("#item-movimentacao-"+id).remove();
				$('#deleteQuestion').modal('hide');
				IAN.alert.alertSuccess("Sucesso");
			},
			error: function(rest) {
				IAN.alert.alertError("Erro");
			}
		}; //Fim do cfg
		IAN.ajax.post(cfg);
	};
	
	$("#valorMov").maskMoney({prefix:'R$ ', allowNegative: true, thousands:'.', decimal:',', affixesStay: false});
});