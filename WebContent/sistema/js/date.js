var fd = Date.today().clearTime().moveToFirstDayOfMonth();
var firstday = fd.toString("MM/dd/yyyy");

$("#dataInicial").val(firstday);

var ld = Date.today().clearTime().moveToLastDayOfMonth();
var lastday = ld.toString("MM/dd/yyyy");

$("#dataFinal").val(lastday);