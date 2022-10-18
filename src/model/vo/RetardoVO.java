package model.vo;

public class RetardoVO
{
private String RangoDeHora;
private int numeroDeRetrasos;

public String getRangoDeHora() {
	return RangoDeHora;
}
public void setRangoDeHora(String rangoDeHora) {
	RangoDeHora = rangoDeHora;
}
public int getNumeroDeRetrasos() {
	return numeroDeRetrasos;
}
public void setNumeroDeRetrasos(int numeroDeRetrasos) {
	this.numeroDeRetrasos = numeroDeRetrasos;
}
public RetardoVO(String rangoDeHora, int numeroDeRetrasos) {
	
	RangoDeHora = rangoDeHora;
	this.numeroDeRetrasos = numeroDeRetrasos;
}



}
