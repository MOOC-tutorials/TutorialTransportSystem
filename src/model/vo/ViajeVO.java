package model.vo;


import java.util.concurrent.ArrayBlockingQueue;

import org.joda.time.DateTime;

import data_structures.DirectedGraph;
import data_structures.HashTableLinearProbing;
import data_structures.MaxPQ;
import data_structures.RedBlackBST;
public class ViajeVO 
{
	private int RouteId;
	private int ServiceId;
	private int TripId;
	private String ShapeId;
	private String startDate;
	private String endDate;
	private HashTableLinearProbing<Integer, TiemposParadasVO>tabalaParadaID;
	private RedBlackBST<Integer, TiemposParadasVO> arbolDeParadas;
	private RedBlackBST<Integer, TiemposParadasVO> arbolFrecuencia;
	public HashTableLinearProbing<Integer, Eje> arcos;

	public RedBlackBST<Integer, TiemposParadasVO> getArbolFrecuencia()
	{
		return arbolFrecuencia;
	}
	public void setArbolFrecuencia(RedBlackBST<Integer, TiemposParadasVO> arbolFrecuencia) {
		this.arbolFrecuencia = arbolFrecuencia;
	}

	private DirectedGraph<Integer, TiemposParadasVO> grafoAux;



	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {

		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate=endDate;
	}
	public void setRouteId(int routeId) {
		RouteId = routeId;
	}
	public void setServiceId(int serviceId) {
		ServiceId = serviceId;
	}
	public void setTripId(int tripId) {
		TripId = tripId;
	}
	public int getRouteId() {
		return RouteId;
	}
	public int getServiceId() {
		return ServiceId;
	}
	public int getTripId() {
		return TripId;
	}
	public String getShapeId() {
		return ShapeId;
	}
	public void setShapeId(String shapeId) {
		ShapeId = shapeId;
	}


	public ViajeVO() {
		tabalaParadaID= new HashTableLinearProbing<>(100);
		arbolFrecuencia= new RedBlackBST<>();
		arcos= new HashTableLinearProbing<>(100);
		arbolDeParadas= new RedBlackBST<>();
	}
	public HashTableLinearProbing<Integer, TiemposParadasVO> getTabalaParadaID() {
		return tabalaParadaID;
	}
	public void setTabalaParadaID(
			HashTableLinearProbing<Integer, TiemposParadasVO> tabalaParadaID) {
		this.tabalaParadaID = tabalaParadaID;
	}
	public RedBlackBST<Integer, TiemposParadasVO> getArbolDeParadas() {
		return arbolDeParadas;
	}
	public void setArbolDeParadas(RedBlackBST<Integer, TiemposParadasVO> arbolDeParadas) {
		this.arbolDeParadas = arbolDeParadas;
	}

	public void crearGrafo()
	{



		for (Integer integer : arbolDeParadas) 
		{
			if(getArbolDeParadas().get(integer).getParadaAlaQueApunta()!=0)
			{
				double arco=getArbolDeParadas().get(getArbolDeParadas().get(integer).getParadaAlaQueApunta()).getDistance_traveled()-getArbolDeParadas().get(integer).getDistance_traveled();
				Eje anadido= new Eje();
				anadido.setArco(arco);
				anadido.setOrigen(getArbolDeParadas().get(integer).getStopId());
				anadido.setDestino(getArbolDeParadas().get(getArbolDeParadas().get(integer).getParadaAlaQueApunta()).getStopId());
				arcos.put(anadido.getOrigen(), anadido);
			}

		}
		


	}
	public HashTableLinearProbing<Integer, Eje> getArcos() {
		return arcos;
	}
	public void setArcos(HashTableLinearProbing<Integer, Eje> arcos) {
		this.arcos = arcos;
	}
	public class Eje
	{

		private Integer origen;
		private Integer destino;
		private double arco;
		public Integer getOrigen() {
			return origen;
		}
		public void setOrigen(Integer origen) {
			this.origen = origen;
		}
		public Integer getDestino() {
			return destino;
		}
		public void setDestino(Integer destino) {
			this.destino = destino;
		}
		public double getArco() {
			return arco;
		}
		public void setArco(double arco) {
			this.arco = arco;
		}

	}
}


