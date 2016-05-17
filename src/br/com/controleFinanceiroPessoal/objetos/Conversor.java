package br.com.controleFinanceiroPessoal.objetos;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Conversor {
	
	private SimpleDateFormat formatToSql = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat formatToFront = new SimpleDateFormat("dd-MM-yyyy");
	
	public String convertFloatToString(float valor) {  
        String formato = "R$ #,##0.00";  
        DecimalFormat d = new DecimalFormat(formato);
        return d.format(valor);
    } 
	
	public Date convertStringToSqlDate(String sDate){
		try {
			java.util.Date utilDate = formatToSql.parse(sDate);
			java.sql.Date date = new java.sql.Date(utilDate.getTime());
			return date;
			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String convertSqlDateToString(Date date){
		String sDate = formatToFront.format(new Date(date.getTime()));
		return sDate;
	}
}


