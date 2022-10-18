package model.vo;

import data_structures.HashTableLinearProbing;
import data_structures.MaxPQ;


/*
 **Clase usada para las rutas que hacen parte del servicio prestado por una agencia.
 */

public class RutaVO 
{
	private int Routeid;
	private String Agencyid;
	private String nombreCorto;
	private String nombreLargo;
	private HashTableLinearProbing<Integer, ViajeVO> tablaDeViajes;
	private HashTableLinearProbing<Integer, ParadaVO> tablaDeParadas;
	private MaxPQ<DistanciaViajeVO> colaDeViajes;
	private HashTableLinearProbing<BusEstimVO, BusEstimVO> tablaDeEstimaciones;
	private boolean tieneViajes;
	public boolean RutaTieneViajes()
	{
		return tablaDeViajes.darCapacidad()!=0;
	}
	public MaxPQ<DistanciaViajeVO> getColaDeViajes() {
		return colaDeViajes;
	}
	public void setColaDeViajes(MaxPQ<DistanciaViajeVO> colaDeViajes) {
		this.colaDeViajes = colaDeViajes;
	}
	public boolean tieneViajes()
	{
		return tablaDeViajes.darCapacidad()!=0;
	}
	public void setRouteid(int id)
	{
		this.Routeid=id;
	}public int darRouteid()
	{
		return Routeid;
	}
	public HashTableLinearProbing<Integer, ViajeVO> getTablaDeViajes() 
	{
		return tablaDeViajes;
	}
	public HashTableLinearProbing<Integer, ParadaVO> getTablaDeParadas() 
	{
		return tablaDeParadas;
	}

	public RutaVO() {

		tablaDeViajes = new HashTableLinearProbing<>(500);
		tablaDeParadas = new HashTableLinearProbing<>(500);
		tablaDeEstimaciones= new HashTableLinearProbing<>(300);
	}
	public void setAgencyid(String id)
	{
		this.Agencyid=id;
	}
	public String darAgencyid()
	{
		return Agencyid;
	}
	public void setNombreCorto(String nombre)
	{
		this.nombreCorto=nombre;
	}
	public String darNombreCorto()
	{
		return nombreCorto;
	}
	public void setNombreLargo(String nombre)
	{
		this.nombreLargo=nombre;
	}
	public String darNombreLargo()
	{
		return nombreLargo;
	}
	public HashTableLinearProbing<BusEstimVO, BusEstimVO> darEstimaciones()
	{
		return tablaDeEstimaciones;
	}
	public boolean tieneRetrasos()
	{
		return tablaDeEstimaciones.darCapacidad()!=0;
	}

}
