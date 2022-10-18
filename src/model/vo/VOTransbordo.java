package model.vo;

import data_structures.IList;

public class VOTransbordo
{
	/**
	 * Modela el tiempo de transbordo
	 */
	private int transferTime;

	/**
	 * Id de la parada de origen.
	 */
	private int idParadaOrigen;

	/**
	 * Lista de paradas que conforman el transbordo
	 */
	private IList<ParadaVO> listadeParadas;

	/**
	 * @return the transferTime
	 */
	public int getTransferTime()
	{
		return transferTime;
	}

	/**
	 * @param transferTime the transferTime to set
	 */
	public void setTransferTime(int transferTime) 
	{
		this.transferTime = transferTime;
	}

	/**
	 * @return the listadeParadas
	 */
	public IList<ParadaVO> getListadeParadas()
	{
		return listadeParadas;
	}

	public int getIdParadaOrigen() {
		return idParadaOrigen;
	}

	public void setIdParadaOrigen(int idParadaOrigen) {
		this.idParadaOrigen = idParadaOrigen;
	}

	/**
	 * @param listadeParadas the listadeParadas to set
	 */
	public void setListadeParadas(IList<ParadaVO> listadeParadas) 
	{
		this.listadeParadas = listadeParadas;
	}


}
