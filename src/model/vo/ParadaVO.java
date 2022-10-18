package model.vo;

import java.util.Comparator;

import data_structures.HashTableLinearProbing;
import data_structures.IList;
import data_structures.SimpleArrayLIst;
import data_structures.SimpleIndividualLinkedList;

/**
 * Clase usada para representar una parada de un viaje (cordenadas).
 * 
 */
public class ParadaVO 
{
	private Double latitude;
	private Double longitude;
	private int id;
	private int stopCode;
	private int sequence;
	private HashTableLinearProbing<Integer, Integer> tablaDeFrecuencia;
	private HashTableLinearProbing<Integer, RutaVO> tablaRutasEnHoraio;
	private HashTableLinearProbing<Integer, ViajeVO> tabladeviajesEnRango;




	private int paradaDelLaQueLLego;
	public int getParadaDijkstra() {
		return paradaDijkstra;
	}

	public void setParadaDijkstra(int paradaDijkstra) {
		this.paradaDijkstra = paradaDijkstra;
	}
	private int paradaDijkstra;

	public int getParadaDelLaQueLLego() {
		return paradaDelLaQueLLego;
	}

	public void setParadaDelLaQueLLego(int paradaDelLaQueLLego) {
		this.paradaDelLaQueLLego = paradaDelLaQueLLego;
	}

	public Comparator<ParadaVO> getComparadorIncidentesMayorAMenor() {
		return comparadorIncidentesMayorAMenor;
	}

	public void setComparadorIncidentesMayorAMenor(
			Comparator<ParadaVO> comparadorIncidentesMayorAMenor) {
		this.comparadorIncidentesMayorAMenor = comparadorIncidentesMayorAMenor;
	}
	//ARREGLO De las posibles trasferencias de una parada a otra 
	private SimpleArrayLIst<String> transferencias;

	public SimpleArrayLIst<String> getTransferencias() {
		return transferencias;
	}

	public void setTransferencias(SimpleArrayLIst<String> transferencias) {
		this.transferencias = transferencias;
	}

	public HashTableLinearProbing<Integer, Integer> getTablaDeFrecuencia() {
		return tablaDeFrecuencia;
	}

	public void setTablaDeFrecuencia(
			HashTableLinearProbing<Integer, Integer> tablaDeFrecuencia) {
		this.tablaDeFrecuencia = tablaDeFrecuencia;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getStopCode() {
		return stopCode;
	}

	public void setStopCode(int stopCode) {
		this.stopCode = stopCode;
	}
	private String nombre;
	private boolean padre;
	private int numeroRetrasos;
	private boolean revisada;
	private SimpleIndividualLinkedList<BusEstimVO> listaBuses=new SimpleIndividualLinkedList<BusEstimVO>();
	public Comparator<ParadaVO> comparadorIncidentesMenorAMayor=new Comparator<ParadaVO>()
			{

		@Override
		public int compare(ParadaVO parada1, ParadaVO parada2) 
		{
			int pId1=parada1.getNumeroIncidentes();
			int pId2=parada2.getNumeroIncidentes();
			if(pId1>pId2)
				return 1;
			else if(pId1==pId2)
				return 0;
			else return -1;
		}

			};
			public Comparator<ParadaVO> comparadorIncidentesMayorAMenor=new Comparator<ParadaVO>()
					{

				@Override
				public int compare(ParadaVO parada1, ParadaVO parada2) 
				{
					int pId1=parada1.getNumeroIncidentes();
					int pId2=parada2.getNumeroIncidentes();
					if(pId1<pId2)
						return 1;
					else if(pId1==pId2)
						return 0;
					else return -1;
				}

					};
					public Comparator<Integer> comparadorPorId=new Comparator<Integer>()
							{

						@Override
						public int compare(Integer parada1, Integer parada2) 
						{
							return parada1.compareTo(parada2);
						}

							};
					public ParadaVO()
					{transferencias= new SimpleArrayLIst<>();
					tablaDeFrecuencia= new HashTableLinearProbing<>(100);
					tablaRutasEnHoraio= new HashTableLinearProbing<>(1000);
					tabladeviajesEnRango= new HashTableLinearProbing<>(1000);
					
					}

					public HashTableLinearProbing<Integer, ViajeVO> getTabladeviajesEnRango() {
						return tabladeviajesEnRango;
					}

					public void setTabladeviajesEnRango(
							HashTableLinearProbing<Integer, ViajeVO> tabladeviajesEnRango) {
						this.tabladeviajesEnRango = tabladeviajesEnRango;
					}

					public HashTableLinearProbing<Integer, RutaVO> getTablaRutasEnHoraio() {
						return tablaRutasEnHoraio;
					}

					public void setTablaRutasEnHoraio(
							HashTableLinearProbing<Integer, RutaVO> tablaRutasEnHoraio) {
						this.tablaRutasEnHoraio = tablaRutasEnHoraio;
					}

					public void setId(int id)
					{
						this.id=id;
					}
					public void setPadre(int p)
					{
						if(p==0)
							padre=false;
						else 
							padre=true;
					}
					public void setNombre(String nombre)
					{
						this.nombre=nombre;
					}
					public int darId()
					{
						return id;
					}
					public String darNombre()
					{
						return nombre;
					}
					public boolean esPadre() {
						// TODO Auto-generated method stub
						return padre;
					}
					public void setNumeroIncidentes()
					{
						numeroRetrasos++;
					}
					public int getNumeroIncidentes() 
					{
						return numeroRetrasos;
					}
					public void setRevisada(boolean revisa)
					{
						revisada=revisa;
					}
					public boolean darRevisada()
					{
						return revisada;
					}

					public Double getLatitude() {
						return latitude;
					}

					public void setLatitude(Double latitude) {
						this.latitude = latitude;
					}

					public Double getLongitude() {
						return longitude;
					}

					public void setLongitude(Double longitude) {
						this.longitude = longitude;
					}
					public void anadirBus(BusEstimVO bus)
					{
						listaBuses.add(bus);
					}
					public SimpleIndividualLinkedList<BusEstimVO> darListaBuses()
					{
						return listaBuses;
					}
}


