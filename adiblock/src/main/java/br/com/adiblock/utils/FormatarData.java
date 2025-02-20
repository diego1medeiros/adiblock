package br.com.adiblock.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FormatarData {

	public static String formatarData(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String dataRetorno = sdf.format(data);
		return dataRetorno;
	}
	
	public static LocalDateTime  converterDateParaLocalDate (Instant date) {
		Instant instant = date;
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		return localDateTime;
	}
	
	public static Date converterLocalDateParaDate  (LocalDateTime localDateTime) {
		Date data = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return data;
	}
}
