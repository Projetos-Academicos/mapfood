package com.codenation.desafiofinal.util;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

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

	public static Double converterMetrosEmQuilometros(Double metros) {
		Double quilometros = (double) (metros / 1000);
		return quilometros;
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

}
