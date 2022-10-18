package model.vo;

import data_structures.SimpleIndividualLinkedList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Clase VO para representar cuando un trip no esta disponible.
 * Txt relacionado= Calendar_dates.
 *
 */
public class CalendarDatesVO
{
	private int Service_id;
	private DateTime Date;
	private SimpleIndividualLinkedList<DateTime> fechas;
	public CalendarDatesVO()
	{
		fechas= new SimpleIndividualLinkedList<>();
	}

	public SimpleIndividualLinkedList<DateTime> getFechas() {
		return fechas;
	}

	public int getService_id() {
		return Service_id;
	}
	public void setService_id(int service_id) {
		Service_id = service_id;
	}
	public DateTime getDate() {
		return Date;
	}
	public void setDate(String date) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt=fmt.parseDateTime(date);
		this.Date = dt;
	}
	
	}


