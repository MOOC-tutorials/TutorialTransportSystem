package model.vo;

import data_structures.HashTableLinearProbing;

public class paradaConTasferecnia 
{
public int idParadaorigen;
public int idParadaDestinoConTrasferencia;

public HashTableLinearProbing<Integer, RutaVO> rutasParaLlegar;
public paradaConTasferecnia() {
rutasParaLlegar= new HashTableLinearProbing<>(10);	// TODO Auto-generated constructor stub
}
public int getIdParadaorigen() {
	return idParadaorigen;
}
public void setIdParadaorigen(int idParadaorigen) {
	this.idParadaorigen = idParadaorigen;
}
public int getIdParadaDestinoConTrasferencia() {
	return idParadaDestinoConTrasferencia;
}
public void setIdParadaDestinoConTrasferencia(int idParadaDestinoConTrasferencia) {
	this.idParadaDestinoConTrasferencia = idParadaDestinoConTrasferencia;
}
public HashTableLinearProbing<Integer, RutaVO> getRutasParaLlegar() {
	return rutasParaLlegar;
}
public void setRutasParaLlegar(
		HashTableLinearProbing<Integer, RutaVO> rutasParaLlegar) {
	this.rutasParaLlegar = rutasParaLlegar;
}

}
