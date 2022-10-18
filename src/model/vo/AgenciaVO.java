package model.vo;

import data_structures.HashTableLinearProbing;

/**
 * Clase usada para representar a una agencia.
 * 
 */
public class AgenciaVO 
{
	private String id;
	private String nombre;
	
	
	

	public void setId(String id)
	{
		this.id=id;
	}
	public void setNombre(String nombre)
	{
		this.nombre=nombre;
	}
	public String darId()
	{
		return id;
	}
	public String darNombre()
	{
		return nombre;
	}

}
