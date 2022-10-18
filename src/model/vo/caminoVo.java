package model.vo;

import data_structures.HashTableLinearProbing;

public class caminoVo {

	private	int paradaID;
	private int paradaIDdeDondeVengo;
	private HashTableLinearProbing<Integer, RutaVO> tablaDeTrasferencias;
	public caminoVo() {
		tablaDeTrasferencias= new HashTableLinearProbing<>(1000);	// TODO Auto-generated constructor stub
	}
	public HashTableLinearProbing<Integer, RutaVO> getTablaDeTrasferencias() {
		return tablaDeTrasferencias;
	}
	public void setTablaDeTrasferencias(
			HashTableLinearProbing<Integer, RutaVO> tablaDeTrasferencias) {
		this.tablaDeTrasferencias = tablaDeTrasferencias;
	}
	public int getParadaID() {
		return paradaID;
	}
	public void setParadaID(int paradaID) {
		this.paradaID = paradaID;
	}
	public int getParadaIDdeDondeVengo() {
		return paradaIDdeDondeVengo;
	}
	public void setParadaIDdeDondeVengo(int paradaIDdeDondeVengo) {
		this.paradaIDdeDondeVengo = paradaIDdeDondeVengo;
	}

}
