package com.codenation.desafiofinal.util;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import com.codenation.desafiofinal.enums.StatusEnum;

public class MapFoodUtil {

	public static Double calcularDistanciaEntreDoisPontos(Double lat1, Double lon1, Double lat2, Double lon2) {
		Double theta = lon1 - lon2;
		Double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) +
				Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
				Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;

		dist = dist * 1.609344;
		return dist;
	}

	private static Double deg2rad(Double deg) {
		return (deg * Math.PI / 180.0);
	}

	/**
	 * getDataAtual
	 *
	 * @return Data atual
	 */
	public static Date getDataAtual() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	/**
	 * getMinutosFromTimes
	 *
	 * @param timeInicial
	 * @param timeFinal
	 * @return Retorna a quantidade de minutos entre dois timess
	 */
	public static Integer getMinutosFromTimes(Date timeInicial, Date timeFinal){
		DateTime time1 = new DateTime(timeInicial);
		DateTime time2 = new DateTime(timeFinal);
		Minutes minutes = Minutes.minutesBetween(time1, time2);
		return minutes.getMinutes();
	}

	private static Double rad2deg(Double rad) {
		return (rad * 180 / Math.PI);
	}


	public static String statusEnumToString(StatusEnum status) {
		if(status.equals(StatusEnum.AGUARDANDO_RESPOSTA)) {
			return "AGUARDANDO_RESPOSTA";
		}else if(status.equals(StatusEnum.ACEITO)) {
			return "ACEITO";
		}else if(status.equals(StatusEnum.RECUSADO)) {
			return "RECUSADO";
		}else if(status.equals(StatusEnum.EM_ANDAMENTO)) {
			return "EM_ANDAMENTO";
		}else if(status.equals(StatusEnum.CANCELADO)) {
			return "CANCELADO";
		}else if(status.equals(StatusEnum.FINALIZADO)) {
			return "FINALIZADO";
		}
		return null;
	}

	public static StatusEnum stringToStatusEnum( String status) {
		if(status.equals("AGUARDANDO_RESPOSTA")) {
			return StatusEnum.AGUARDANDO_RESPOSTA;
		}else if(status.equals("ACEITO")) {
			return StatusEnum.ACEITO;
		}else if(status.equals("RECUSADO")) {
			return StatusEnum.RECUSADO ;
		}else if(status.equals("EM_ANDAMENTO")) {
			return StatusEnum.EM_ANDAMENTO;
		}else if(status.equals("CANCELADO")) {
			return StatusEnum.CANCELADO;
		}else if(status.equals("FINALIZADO")) {
			return StatusEnum.FINALIZADO;
		}
		return null;
	}
}