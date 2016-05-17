IAN.grafico = new Object();
google.load('visualization', '1', {packages: ['corechart', 'bar']});
google.load("visualization", "1", {packages:["corechart"]});
google.load('visualization', '1.1', {packages:["bar"]});

$(document).ready(function(){
    IAN.grafico.gerarGraficoTotal = function() {

            var data = new Object();

                if( $("#dataInicial").val() > $("#dataFinal").val() ){
                    IAN.alert.alertError("A data inicial não pode ser posterior a data final");
                    return false
                } else {
                    data.dataInicial = $("#dataInicial").val();
                    data.dataFinal = $("#dataFinal").val();

                    var cfg = {

                        url: "../rest/graficoRest/gerarGrafico",
                        data: data,

                        success: function(totalMovimentacao) {

                            google.setOnLoadCallback(drawStuff(totalMovimentacao));

                            function drawStuff() {
                                var data = new google.visualization.arrayToDataTable([
                                    ['Movimentacao', 'Valor Total'],
                                    ['Receita', totalMovimentacao[0]],
                                    ['Despesa', totalMovimentacao[1]]
                                    ]);

                            var options = {
                                title: 'Total de movimentações',
                                width: 900,
                                legend: { position: 'none' },
                                chart: { title: 'Movimentações por período',
                                         subtitle: 'Gastos' },
                                bars: 'horizontal', // Required for Material Bar Charts.
                                axes: {
                                  x: {
                                    0: { side: 'top', label: 'Percentage'} // Top x-axis.
                                  }
                                },
                                bar: { groupWidth: "90%" }
                              };

                            var chart = new google.charts.Bar(document.getElementById('chart_div'));
                            chart.draw(data, options);
                        }

                        IAN.grafico.gerarGraficoReceita();
                    },

                    error: function(rest) {
                        IAN.alert.alertError("Erro no grafico");

                    },

                }

                IAN.ajax.post(cfg);
            }

        }

IAN.grafico.gerarGraficoReceita = function () {

    var data = new Object();

    data.dataInicial = $("#dataInicial").val();
    data.dataFinal = $("#dataFinal").val();

        var cfg = {
            url: "../rest/graficoRest/gerarGraficoReceita",
            data: data,

            success: function(listaCategoria) {

                console.dir(listaCategoria);
                google.setOnLoadCallback(drawChart2(listaCategoria));
                function drawChart2() {

                    var data1 = google.visualization.arrayToDataTable(
                        
                            listaCategoria
                    );

                var options1 = {
                  title: 'Receitas por categoria',
                  pieHole: 0.4,
                  is3D: true,
                };

                var chart1 = new google.visualization.PieChart(document.getElementById('donutchart'));
                chart1.draw(data1, options1);
              }

                IAN.grafico.gerarGraficoDespesa();

            },

            error: function(msg) {
                IAN.alert.alertError("Erro no grafico");
            }

        }
        IAN.ajax.post(cfg);       
}//Fim do gerarGraficoDespesa

 IAN.grafico.gerarGraficoDespesa = function () {

    var data = new Object();

    data.dataInicial = $("#dataInicial").val();
    data.dataFinal = $("#dataFinal").val();

        var cfg = {
            url: "../rest/graficoRest/gerarGraficoDespesa",
            data: data,

            success: function(listaCategoria) {

                console.dir(listaCategoria);
                google.setOnLoadCallback(drawChart3(listaCategoria));
                function drawChart3() {

                    var data2 = google.visualization.arrayToDataTable(
                        
                            listaCategoria
                    );

                var options2 = {
                  title: 'Despesas por categoria',
                  pieHole: 0.4,
                  is3D: true,
                };

                var chart2 = new google.visualization.PieChart(document.getElementById('donutchart1'));
                chart2.draw(data2, options2);
              }
            },

            error: function(msg) {
                IAN.alert.alertError("Erro no grafico");
            }

        }
        IAN.ajax.post(cfg);       
 	}
});