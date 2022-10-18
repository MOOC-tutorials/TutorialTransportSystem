package model.vo;



import java.util.Comparator;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import data_structures.SimpleArrayLIst;

/**
 * Clase VO que permite reconocer cuando Un tripVO(Viaje) esta disponible 
 * txt relacionado =Calendar
 *
 */
public class ServicioVO implements Comparable<ServicioVO>
{
	private int Service_id;
	private boolean Monday;
	private boolean Tuesday;
	private boolean Wensday;
	private boolean Thursday;
	private boolean Friday;
	private boolean Saturday;
	private boolean Sunday;
	private DateTime startDate;
	private DateTime endDate;
	private SimpleArrayLIst<ViajeVO> arreglo; //Arreglo con todos los viajes de un servicio
	private SimpleArrayLIst<CalendarDatesVO> diasLibres;
	public	Comparator<ServicioVO> comparadorServiceID = new Comparator<ServicioVO>()
			{

		@Override
		public int compare(ServicioVO servicio1, ServicioVO servicio2)
		{
			int pId1=servicio1.getService_id();
			int pId2=servicio2.getService_id();
			if(pId1>pId2)
				return 1;
			else if(pId1==pId2)
				return 0;
			else return -1;

		}

			};
	public SimpleArrayLIst<CalendarDatesVO> getDiasLibres() {
		return diasLibres;
	}
	public void setDiasLibres(SimpleArrayLIst<CalendarDatesVO> diasLibres) {
		this.diasLibres = diasLibres;
	}
	/**
	 * Retorna un arreglo con todos los viajes de un servico
	 * @return arreglo de viajes
	 */
	public SimpleArrayLIst<ViajeVO> getArreglo() {
		return arreglo;
	}
	public void setArreglo(SimpleArrayLIst<ViajeVO> arreglo) {
		this.arreglo = arreglo;
	}
	public int getService_id() {
		return Service_id;
	}
	public void setService_id(int service_id) {
		Service_id = service_id;
	}
	public boolean isMonday() {
		return Monday;
	}
	public void setMonday(boolean monday) {
		Monday = monday;
	}
	public boolean isTuesday() {
		return Tuesday;
	}
	public void setTuesday(boolean tuesday) {
		Tuesday = tuesday;
	}
	public boolean isWensday() {
		return Wensday;
	}
	public void setWensday(boolean wensday) {
		Wensday = wensday;
	}
	public boolean isThursday() {
		return Thursday;
	}
	public void setThursday(boolean thursday) {
		Thursday = thursday;
	}
	public boolean isFriday() {
		return Friday;
	}
	public void setFriday(boolean friday) {
		Friday = friday;
	}
	public boolean isSaturday() {
		return Saturday;
	}
	public void setSaturday(boolean saturday) {
		Saturday = saturday;
	}
	public boolean isSunday() {
		return Sunday;
	}
	public void setSunday(boolean sunday) {
		Sunday = sunday;
	}
	public DateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt=fmt.parseDateTime(startDate);
		this.startDate = dt;
	}
	public DateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt=fmt.parseDateTime(endDate);
		this.endDate=dt;
	}
	@Override
	public int compareTo(ServicioVO pServicioVO) {
		int pId=pServicioVO.getService_id();
		if(Service_id>pId)
		return 1;
		else if(Service_id==pId)
			return 0;
		else return -1;
	}
	


}
