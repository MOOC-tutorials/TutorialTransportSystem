package model.vo;

import data_structures.HashTableLinearProbing;

public class ItirenarioParada {
	
	public int paradaId;
	public HashTableLinearProbing<Integer, RutaVO> tablasRutas;
	public HashTableLinearProbing<Integer, ViajeVO> tablaDeviajes;
	public int getParadaId() {
		return paradaId;
	}
	public void setParadaId(int paradaId) {
		this.paradaId = paradaId;
	}
	public HashTableLinearProbing<Integer, RutaVO> getTablasRutas() {
		return tablasRutas;
	}
	public void setTablasRutas(HashTableLinearProbing<Integer, RutaVO> tablasRutas) {
		this.tablasRutas = tablasRutas;
	}
	public HashTableLinearProbing<Integer, ViajeVO> getTablaDeviajes() {
		return tablaDeviajes;
	}
	public void setTablaDeviajes(
			HashTableLinearProbing<Integer, ViajeVO> tablaDeviajes) {
		this.tablaDeviajes = tablaDeviajes;
	}
	

}
