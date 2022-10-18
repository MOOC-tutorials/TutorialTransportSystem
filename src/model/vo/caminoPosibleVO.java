package model.vo;

public class caminoPosibleVO 
{
	private int paradaIDOrigen;
	private int paradaIDDestino;
	private int tripId;
	private int routeId;
	private String tiempo;
	private double costo;
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public int getParadaIDOrigen() {
		return paradaIDOrigen;
	}
	public void setParadaIDOrigen(int paradaIDOrigen) {
		this.paradaIDOrigen = paradaIDOrigen;
	}
	public int getParadaIDDestino() {
		return paradaIDDestino;
	}
	public void setParadaIDDestino(int paradaIDDestino) {
		this.paradaIDDestino = paradaIDDestino;
	}
	public int getTripId() {
		return tripId;
	}
	public void setTripId(int tripId) {
		this.tripId = tripId;
	}
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	public String getTiempo() {
		return tiempo;
	}
	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}
	public caminoPosibleVO(int paradaIDOrigen, int paradaIDDestino, int tripId, int routeId, String tiempo) {
		super();
		this.paradaIDOrigen = paradaIDOrigen;
		this.paradaIDDestino = paradaIDDestino;
		this.tripId = tripId;
		this.routeId = routeId;
		this.tiempo = tiempo;
	}

}
