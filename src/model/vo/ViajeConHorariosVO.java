package model.vo;

public class ViajeConHorariosVO 
{
	private int rutaID;
	private int tripID;
	private String horaDeLlegada;
	private String HoraDeLlegada2;
	public String getHoraDeLlegada2() {
		return HoraDeLlegada2;
	}
	public void setHoraDeLlegada2(String horaDeLlegada2) {
		HoraDeLlegada2 = horaDeLlegada2;
	}
	public int getRutaID() {
		return rutaID;
	}
	public void setRutaID(int rutaID) {
		this.rutaID = rutaID;
	}
	public int getTripID() {
		return tripID;
	}
	public void setTripID(int tripID) {
		this.tripID = tripID;
	}
	public String getHoraDeLlegada() {
		return horaDeLlegada;
	}
	public void setHoraDeLlegada(String horaDeLlegada) {
		this.horaDeLlegada = horaDeLlegada;
	}
}
