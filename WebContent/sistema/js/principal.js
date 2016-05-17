IAN.principal = new Object();
IAN.principal = new Object();

$(document).ready(function(){


	IAN.principal.resumoMov = function(){
		IAN.principal.resumo();
	};

	//Método que calcula o saldo total
	IAN.principal.saldoTotal = function() {
		var cfg = {
			type: "POST",
			url: "../rest/movimentacaoRest/saldoMovimentacao",
			success: function(saldoTotal) {
				IAN.principal.incluirValorObjetivo(saldoTotal);
			},
			error: function(rest) {
				IAN.alert.alertError("Não foi possível exibir o Saldo")
			} 
		};
		IAN.ajax.post(cfg);
	};
	
	//Método que soma o valor do objetivo no saldo total
	IAN.principal.incluirValorObjetivo = function (saldoTotal) {
		var cfg = {
			type: "POST",
			url: "../rest/objetivoRest/buscarValorTotal/" +parseFloat(saldoTotal),
			success: function(saldoTotalOb) {
				html = "<h1 class='saldo'>"+ saldoTotalOb +"</h1>";
				$("#saldoTotal").html(html);
			},
			error: function(rest) {
				IAN.alert.alertError("Não foi possível exibir o Saldo")
			} 
		};
		IAN.ajax.post(cfg);
	};
	
	IAN.principal.resumo = function(){
		var receita = 0;
		var despesa = 1;
		IAN.principal.resumoReceita(receita);
		IAN.principal.resumoReceita(despesa);
	}

	//método que exibe as 5 primeiras receitas
	IAN.principal.resumoReceita = function(movimentacao) {
		var cfg = {
			type: "POST",
			url: "../rest/movimentacaoRest/buscarUltimasMovimentacoes/"+movimentacao,
			success: function(listaDemovimentacao) {
				var html = "";
				if(movimentacao == 0){
					html = "<p class='text-success'><strong>Ultimas Receitas</strong></p>";
				} else {
					html = "<p class='text-error'><strong>Ultimas Despesas</strong></p>";
				}
				html +=	"<table class='table table-bordered table-hover'>"+	
					 	"<thead>" +
						"<tr>"	+
						"<th class='span2'>Data</th>" +
						"<th class='span4'>Descrição</th>" +
						"<th class='span2'>Valor</th>" +
						"<th class='span2'>Categoria</th>" +
						"</tr>" +
						"</thead>" +
						"<tbody>";	
				for(var i = 0; listaDemovimentacao.length > i; i++) {
					html +=	IAN.principal.createResumoLine(listaDemovimentacao[i], movimentacao);
				}
				html += "</tbody>" +
						"</table>";
				
				if(movimentacao == 0){
					$("#resumoReceita").html(html);
				} else {
					$("#resumoDespesa").html(html);
				}
			},
			error: function(rest) {
				IAN.alert.alertError("Não foi possível exibir os valores");
			}
		}; //Fim do cfg
		IAN.ajax.post(cfg);
	};


	//Cria a linha do resumo
	IAN.principal.createResumoLine = function(resumo, movimentacao) {
		var html = ""
		console.log("dentro do createResumoLine: "+movimentacao);
		if(movimentacao == 0){
			html = "<tr class='' id='item-geral-receita-"+ resumo.id +"'>";
		} else {
			html = "<tr class='' id='item-geral-despesa-"+ resumo.id +"'>";
		}
		
		html +=	"<td class='span2'>"+ resumo.sDate +"</td>" +
				"<td class='span4'>"+ resumo.descricao +"</td>" +
				"<td class='span2'>"+ resumo.sValor +"</td>" +
				"<td class='span2'>"+ resumo.nomeCategoria+"</td>" +
				"</tr>";
		
		return html;
	};	
		IAN.principal.resumoMov();
		IAN.principal.saldoTotal();
	});


	$(".submitKey").keypress(function(e) {
	    if ((e.keyCode == 13) && (e.target.type != "input")) {
	      e.preventDefault();
	      $("#" + this.id +" .btn-primary").click();
	    }
	});
	
	
	$(".modal").find('input:text')[0].focus();
	
	IAN.principal.clearInput = function() {
		$("input").val('');
	};
