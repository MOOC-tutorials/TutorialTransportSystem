package model.vo;

public class InformeParada {
	private int IDparada;
	private int IDruta;
	private int IDViaje;
	private String horaLlegada;
    private String horaSalida;
	public InformeParada(int iDparada, int iDruta, int iDViaje, String horaLlegada, String horaSalida) {
		
		IDparada = iDparada;
		IDruta = iDruta;
		IDViaje = iDViaje;
		this.horaLlegada = horaLlegada;
		this.horaSalida = horaSalida;
	}
	public int getIDparada() {
		return IDparada;
	}
	public void setIDparada(int iDparada) {
		IDparada = iDparada;
	}
	public int getIDruta() {
		return IDruta;
	}
	public void setIDruta(int iDruta) {
		IDruta = iDruta;
	}
	public int getIDViaje() {
		return IDViaje;
	}
	public void setIDViaje(int iDViaje) {
		IDViaje = iDViaje;
	}
	public String getHoraLlegada() {
		return horaLlegada;
	}
	public void setHoraLlegada(String horaLlegada) {
		this.horaLlegada = horaLlegada;
	}
	public String getHoraSalida() {
		return horaSalida;
	}
	public void setHoraSalida(String horaSalida) {
		this.horaSalida = horaSalida;
	}
    
}
