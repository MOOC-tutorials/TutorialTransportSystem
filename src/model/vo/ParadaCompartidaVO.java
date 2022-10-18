package model.vo;


import data_structures.SimpleIndividualLinkedList;

public class ParadaCompartidaVO 
{
private int paradaId;
private boolean esCompartida;
private SimpleIndividualLinkedList<ViajeVO> listaViaje;
private SimpleIndividualLinkedList<RutaVO> listaRutas;
public int getParadaId() {
	return paradaId;
}
public void setParadaId(int paradaId) {
	this.paradaId = paradaId;
}
public ParadaCompartidaVO()
{
	listaRutas=new SimpleIndividualLinkedList<>();
	listaViaje= new SimpleIndividualLinkedList<>();
	esCompartida=false;
}
public SimpleIndividualLinkedList<ViajeVO> getListaViaje() {
	return listaViaje;
}
public void setListaViaje(SimpleIndividualLinkedList<ViajeVO> listaViaje) {
	this.listaViaje = listaViaje;
}
public SimpleIndividualLinkedList<RutaVO> getListaRutas() {
	return listaRutas;
}
public void setListaRutas(SimpleIndividualLinkedList<RutaVO> listaRutas) {
	this.listaRutas = listaRutas;
}
public void siesCompartida()
{
	esCompartida=true;
}

public void anadirRuta(RutaVO ruta)
{
	listaRutas.add(ruta);
}
public void anadirViaje(ViajeVO viaje)
{
	listaViaje.add(viaje);
}
public boolean compartida()
{
	return esCompartida;
}

}
