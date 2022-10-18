package model.vo;

import data_structures.HashTableLinearProbing;
import data_structures.SimpleIndividualLinkedList;

public class caminoMinimoVo 
{
	private int paradaOrigen;
	private int paradaDestino;
	private HashTableLinearProbing<Integer, ParadaVO> rutas;
	private SimpleIndividualLinkedList<RutaVO> rutalista;
	private SimpleIndividualLinkedList< ParadaVO> paradas;
	private HashTableLinearProbing<Integer, ParadaVO> viajes;
	private SimpleIndividualLinkedList<ViajeVO>listaViajes;
	private HashTableLinearProbing<Integer, Boolean> tablaDeParadasVisitadas;
	private SimpleIndividualLinkedList<TiemposParadasVO>listaDeHOrarios;
	
	public HashTableLinearProbing<Integer, Boolean> getTablaDeParadasVisitadas() {
		return tablaDeParadasVisitadas;
	}
	public void setTablaDeParadasVisitadas(HashTableLinearProbing<Integer, Boolean> tablaDeParadasVisitadas) {
		this.tablaDeParadasVisitadas = tablaDeParadasVisitadas;
	}
	public caminoMinimoVo() {
		rutalista= new SimpleIndividualLinkedList<>();
	paradas= new SimpleIndividualLinkedList<>();
	rutas= new HashTableLinearProbing<>(100);
	viajes= new HashTableLinearProbing<>(100);
	listaViajes= new SimpleIndividualLinkedList<>();
	tablaDeParadasVisitadas= new HashTableLinearProbing<>(1000);
	listaDeHOrarios= new SimpleIndividualLinkedList<>();
	
	}
	public SimpleIndividualLinkedList<TiemposParadasVO> getListaDeHOrarios() {
		return listaDeHOrarios;
	}
	public void setListaDeHOrarios(SimpleIndividualLinkedList<TiemposParadasVO> listaDeHOrarios) {
		this.listaDeHOrarios = listaDeHOrarios;
	}
	public SimpleIndividualLinkedList<RutaVO> getRutalista() {
		return rutalista;
	}
	public void setRutalista(SimpleIndividualLinkedList<RutaVO> rutalista) {
		this.rutalista = rutalista;
	}
	public SimpleIndividualLinkedList<ViajeVO> getListaViajes() {
		return listaViajes;
	}
	public void setListaViajes(SimpleIndividualLinkedList<ViajeVO> listaViajes) {
		this.listaViajes = listaViajes;
	}
	public void setParadas(SimpleIndividualLinkedList<ParadaVO> paradas) {
		this.paradas = paradas;
	}
	public int getParadaOrigen() {
		return paradaOrigen;
	}
	public void setParadaOrigen(int paradaOrigen) {
		this.paradaOrigen = paradaOrigen;
	}
	public int getParadaDestino() {
		return paradaDestino;
	}
	public void setParadaDestino(int paradaDestino) {
		this.paradaDestino = paradaDestino;
	}
	public HashTableLinearProbing<Integer, ParadaVO> getRutas() {
		return rutas;
	}
	public void setRutas(HashTableLinearProbing<Integer, ParadaVO> rutas) {
		this.rutas = rutas;
	}

	public SimpleIndividualLinkedList<ParadaVO> getParadas() {
		return paradas;
	}
	public HashTableLinearProbing<Integer, ParadaVO> getViajes() {
		return viajes;
	}
	public void setViajes(HashTableLinearProbing<Integer, ParadaVO> viajes) {
		this.viajes = viajes;
	}
	
}
