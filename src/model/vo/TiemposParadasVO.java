package model.vo;

import java.util.Comparator;

/**
 * 
 * Clase usada para representar el tiempo especifico en el que para un bus.
 *
 */
public class TiemposParadasVO implements Comparable<TiemposParadasVO>
{
	private int StopId;
	private int TripId;
	private String arrival_time;
	private String departure_time;
	private Double distance_traveled;
	private int cantidadDeRetrasos;
	private ParadaVO parada;
	private int sequence;
	private int paradaAlaQueApunta;
	
	public int getParadaAlaQueApunta() {
		return paradaAlaQueApunta;
	}
	public void setParadaAlaQueApunta(int paradaAlaQueApunta) {
		this.paradaAlaQueApunta = paradaAlaQueApunta;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public TiemposParadasVO()
	{
		cantidadDeRetrasos=0;
	}
	public void setParada(ParadaVO pParada)
	{
		parada=pParada;
	}
	public ParadaVO getParada()
	{
		return parada;
	}
	public int getStopId() {
		return StopId;
	}
	public void setStopId(int stopId) {
		StopId = stopId;
	}
	public int getTripId() {
		return TripId;
	}
	public void setTripId(int tripId) {
		TripId = tripId;
	}
	public String getArrival_time() {
		return arrival_time;
	}
	public void setArrival_time(String arrival_time) {
		this.arrival_time = arrival_time;
	}
	public String getDeparture_time() {
		return departure_time;
	}
	public void setDeparture_time(String departure_time) {
		this.departure_time = departure_time;
	}
	public Double getDistance_traveled() {
		return distance_traveled;
	}
	public void setDistance_traveled(String partes) {
		
		this.distance_traveled = Double.parseDouble(partes);
	}
	@Override
	public int compareTo(TiemposParadasVO pParadas) 
	{
		int pId=pParadas.getTripId();
		if(TripId>pId)
			return 1;
		else if(TripId==pId)
			return 0;
		else return -1;
	}
	public void setCantidadDeRetrasos(int retrasos) {
		cantidadDeRetrasos=retrasos;
		
	}
public int getCantidadDeRetrasos()
{
	return cantidadDeRetrasos;
}

}

