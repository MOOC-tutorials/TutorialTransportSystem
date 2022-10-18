package model.logic;

import java.awt.RenderingHints.Key;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.management.GarbageCollectorMXBean;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.script.SimpleScriptContext;
import javax.swing.text.TabableView;

import data_structures.Cola;
import data_structures.Cycle;
import data_structures.DirectedGraph;
import data_structures.DirectedGraphTest;
import data_structures.Edge;
import data_structures.GrafoNoDirigido;
import data_structures.HashTableLinearProbing;
import data_structures.IList;
import data_structures.Kosaraju;
import data_structures.Kruskal;
import data_structures.Kruskal2;
import data_structures.MaxPQ;
import data_structures.MinPQ;
import data_structures.Path;
import data_structures.RedBlackBST;
import data_structures.SimpleArrayLIst;
import data_structures.SimpleIndividualLinkedList;
import data_structures.SimpleLinkedList;
import data_structures.Stack;
import data_structures.caminoMinimo;
import model.exceptions.DateNotFoundExpection;
import model.vo.AgenciaVO;
import model.vo.BusEstimVO;
import model.vo.BusUpdateVO;
import model.vo.CalendarDatesVO;
import model.vo.DistanciaViajeVO;
import model.vo.InformeParada;
import model.vo.ItirenarioParada;
import model.vo.ParadaCompartidaVO;
import model.vo.ParadaVO;
import model.vo.RutaVO;
import model.vo.ServicioVO;
import model.vo.TiemposParadasVO;
import model.vo.VOParadaCamino;
import model.vo.ViajeVO;
import model.vo.BusEstimVO.ItemSchedule;
import model.vo.caminoMinimoVo;
import model.vo.caminoPosibleVO;
import model.vo.caminoVo;
import model.vo.paradaConTasferecnia;

import org.joda.time.DateTime;
import org.omg.CosNaming.IstringHelper;

import api.ISTSManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ITSManager implements ISTSManager
{
	public HashTableLinearProbing<Integer, ParadaVO> tablaParadasStopID;
	private HashTableLinearProbing<Integer, ParadaVO> tablaParadasStopCODE;
	private HashTableLinearProbing<Integer,ServicioVO> tablaServicios;
	private HashTableLinearProbing<CalendarDatesVO,CalendarDatesVO> tablaFechasCalendario;
	private HashTableLinearProbing<String, AgenciaVO> tablaAgencias;
	private HashTableLinearProbing<Integer, RutaVO> tablaRutas;
	private HashTableLinearProbing<Integer, ViajeVO> tablaViajes;
	private HashTableLinearProbing<String, RutaVO> tablaRutaRouteNumber = new HashTableLinearProbing<>(600);
	private MaxPQ<DistanciaViajeVO> prioridadDeViajesPorDistancia;
	private  RedBlackBST<Integer,ParadaCompartidaVO>  arbolcompartido= new RedBlackBST<>();
	private MaxPQ<Integer> heapParadas;
	private DirectedGraph<Integer, ParadaVO> grafoDeParadas;
	private DirectedGraph<Integer, ParadaVO>Subgrafo= new DirectedGraph<>();
	private HashTableLinearProbing<Integer,ItirenarioParada>  tablaDeItirenario= new HashTableLinearProbing<>(10000); 
	private double horaF;
	private double minF;
	public void cargarParadas(String ruta)
	{

		tablaParadasStopID= new HashTableLinearProbing<>(8600);
		tablaParadasStopCODE= new HashTableLinearProbing<>(8600);
		try
		{

			BufferedReader bf=new BufferedReader(new FileReader(ruta));
			bf.readLine();
			String linea = bf.readLine();
			while(linea!=null)
			{
				String[] partes=linea.split(",");
				ParadaVO nuevo=new ParadaVO();
				nuevo.setId(Integer.valueOf(partes[0]));
				if(!(partes[1].equals(" ")))
					nuevo.setStopCode(Integer.parseInt(partes[1]));
				nuevo.setLatitude(Double.parseDouble(partes[4]));
				nuevo.setLongitude((Double.parseDouble(partes[5])));
				nuevo.setNombre(partes[2]);
				nuevo.setRevisada(false);
				tablaParadasStopID.put(Integer.valueOf(partes[0]), nuevo);
				tablaParadasStopCODE.put(nuevo.getStopCode(), nuevo);
				linea = bf.readLine();
			}	
			bf.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void cargarParadasPorRuta()
	{
		for(Integer llave: tablaRutas.keys())
		{
			RutaVO actual= tablaRutas.get(llave);
			HashTableLinearProbing<Integer, ViajeVO> tablaDeViajes=actual.getTablaDeViajes();
			if(((Cola)tablaDeViajes.keys()).size()!=0)
			{
				for(Integer llave2:tablaDeViajes.keys())
				{
					ViajeVO actual2=tablaDeViajes.get(llave2);
					HashTableLinearProbing<Integer, TiemposParadasVO> tablaDeParadas=actual2.getTabalaParadaID();

					if(((Cola)tablaDeParadas.keys()).size()!=0)
					{

						for(Integer actual3:tablaDeParadas.keys())
						{

							TiemposParadasVO tiempo= tablaDeParadas.get(actual3);
							ParadaVO anadible=tiempo.getParada();
							if(anadible!=null)
							{

								actual.getTablaDeParadas().put(anadible.darId(), anadible);
							}

						}
					}
				}
			}
		}

	}

	public void cargarHorasParadas(String ruta)
	{

		try
		{
			BufferedReader bf=new BufferedReader(new FileReader(ruta));
			bf.readLine();
			String linea = bf.readLine();  
			TiemposParadasVO nuevoAux= new TiemposParadasVO();


			while(linea!=null)
			{
				String[] partes=linea.split(",");


				TiemposParadasVO nuevo=new TiemposParadasVO();
				nuevo.setTripId(Integer.valueOf(partes[0]));
				nuevo.setArrival_time(partes[1]);
				nuevo.setDeparture_time(partes[2]);
				nuevo.setStopId(Integer.valueOf(partes[3]));
				nuevo.setSequence(Integer.parseInt(partes[4]));

				if(partes.length==8)
				{
					nuevo.setDistance_traveled(""+0);
					nuevoAux.setStopId(Integer.valueOf(partes[3]));
					nuevoAux.setSequence(Integer.parseInt(partes[4]));
					tablaViajes.get(nuevo.getTripId()).setStartDate(nuevo.getArrival_time());
				}
				if(partes.length==9 && partes[8]!="")
					nuevo.setDistance_traveled(partes[8]);

				if(nuevoAux.getSequence()<nuevo.getSequence())
				{
					ViajeVO viaje= tablaViajes.get(nuevo.getTripId());
					viaje.getTabalaParadaID().put(nuevo.getStopId(), nuevo);
					viaje.getArbolDeParadas().get(nuevoAux.getStopId()).setParadaAlaQueApunta(nuevo.getStopId());

					nuevoAux.setStopId(nuevo.getStopId());

					nuevoAux.setSequence(nuevo.getSequence());


				}
				nuevo.setParada(tablaParadasStopID.get(nuevo.getStopId()));

				ViajeVO viaje= tablaViajes.get(nuevo.getTripId());
				viaje.getArbolDeParadas().put(nuevo.getStopId(), nuevo);
				viaje.getArbolFrecuencia().put(nuevo.getSequence(), nuevo);
				tablaParadasStopID.get(nuevo.getStopId()).getTablaDeFrecuencia().put(nuevo.getTripId(), nuevo.getSequence());

				tablaViajes.get(nuevo.getTripId()).setEndDate(nuevo.getArrival_time());
				linea = bf.readLine();
			}
			bf.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	public void cargarServicioBuses(String ruta) 
	{
		tablaServicios=new HashTableLinearProbing<>(10);
		try
		{
			BufferedReader bf=new BufferedReader(new FileReader(ruta));
			bf.readLine();
			String linea = bf.readLine();
			while(linea!=null)
			{
				String[] partes=linea.split(",");
				ServicioVO nuevo=new ServicioVO();
				nuevo.setService_id(Integer.parseInt(partes[0]));	
				if(Integer.parseInt(partes[1])==1)
					nuevo.setMonday(true);
				if(Integer.parseInt(partes[2])==1)
					nuevo.setTuesday(true);
				if(Integer.parseInt(partes[3])==1)
					nuevo.setWensday(true);
				if(Integer.parseInt(partes[4])==1)
					nuevo.setThursday(true);
				if(Integer.parseInt(partes[5])==1)
					nuevo.setFriday(true);
				if(Integer.parseInt(partes[6])==1)
					nuevo.setSaturday(true);
				if(Integer.parseInt(partes[7])==1)
					nuevo.setSunday(true);
				nuevo.setStartDate(partes[8]);
				nuevo.setEndDate(partes[9]);
				tablaServicios.put(Integer.parseInt(partes[0]), nuevo);
				linea = bf.readLine();
			}	
			bf.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void cargarFechasCalendarios(String ruta) 
	{
		tablaFechasCalendario= new HashTableLinearProbing<>(300);

		try
		{

			BufferedReader bf=new BufferedReader(new FileReader(ruta));
			bf.readLine();
			String linea = bf.readLine();
			while(linea!=null)
			{
				String[] partes=linea.split(",");
				CalendarDatesVO nuevo=new CalendarDatesVO();
				nuevo.setService_id(Integer.parseInt(partes[0]));

				nuevo.setDate(partes[1]);
				tablaFechasCalendario.put(nuevo,nuevo);	
				linea=bf.readLine();
			}
			bf.close();
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void cargarAgencias(String ruta) 
	{
		tablaAgencias=new HashTableLinearProbing<>(3);
		try
		{

			BufferedReader bf=new BufferedReader(new FileReader(ruta));
			bf.readLine();
			String linea = bf.readLine();
			while(linea!=null)
			{
				String[] partes=linea.split(",");
				AgenciaVO nuevo=new AgenciaVO();
				nuevo.setId(partes[0]);
				nuevo.setNombre(partes[1]);
				tablaAgencias.put(partes[0], nuevo);
				linea = bf.readLine();
			}	
			bf.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void cargarRutas(String ruta) 
	{ tablaRutas=new HashTableLinearProbing<>(250);

	try
	{

		BufferedReader bf=new BufferedReader(new FileReader(ruta));
		bf.readLine();
		String linea = bf.readLine();
		while(linea!=null)
		{
			String[] partes=linea.split(",");
			RutaVO nuevo=new RutaVO();
			nuevo.setRouteid(Integer.parseInt(partes[0]));
			nuevo.setAgencyid(partes[1]);
			nuevo.setNombreCorto(partes[2]);
			nuevo.setNombreLargo(partes[3]);
			tablaRutas.put(nuevo.darRouteid(), nuevo);
			tablaRutaRouteNumber.put(nuevo.darNombreCorto(),nuevo);
			linea = bf.readLine();
		}	

		bf.close();
	}

	catch(Exception e)
	{
		e.printStackTrace();
	}

	}
	public void cargarViajes(String ruta) 
	{
		tablaViajes=new HashTableLinearProbing<>(60000);
		try
		{

			BufferedReader bf=new BufferedReader(new FileReader(ruta));
			bf.readLine();
			String linea = bf.readLine();
			while(linea!=null)
			{
				String[] partes=linea.split(",");
				ViajeVO nuevo=new ViajeVO();
				nuevo.setRouteId(Integer.valueOf(partes[0]));
				nuevo.setServiceId(Integer.valueOf(partes[1]));
				nuevo.setTripId(Integer.valueOf(partes[2]));

				tablaViajes.put(nuevo.getTripId(), nuevo);
				RutaVO ruta2= tablaRutas.get(nuevo.getRouteId());
				ruta2.getTablaDeViajes().put(nuevo.getTripId(), nuevo);
				linea = bf.readLine();
			}	
			bf.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	public void  cargarEstimServices(File ruta,int paradaI)
	{
		BufferedReader lector=null;


		try
		{

			lector = new BufferedReader(new FileReader(ruta));
			String linea =lector.readLine();
			if (linea.startsWith("["))	
			{


				JsonParser parser= new JsonParser();

				String RouteNo, RouteName, Direction, RouteMap;




				String Pattern = "", Destination = "", ExpectedLeaveTime = "", ExpectedCountdown = "",
						ScheduleStatus = "",  LastUpdate = "";
				boolean CancelledTrip = false, CancelledStop = false, AddedTrip = false, AddedStop = false;

				JsonObject RouteMapObj = new JsonObject();
				JsonArray schedulesArray= new JsonArray();

				BusEstimVO re;


				try {
					JsonArray array = (JsonArray) parser.parse(new FileReader(ruta)); 
					int tam = array.size();

					for(int j = 0; j < tam; j++ ) {
						SimpleArrayLIst<ItemSchedule> Schedules = new SimpleArrayLIst<>();
						JsonObject obj = (JsonObject)array.get(j); 
						// EXTRAER CADA ELEMENTO
						RouteNo = obj.get("RouteNo").getAsString();
						RouteName = obj.get("RouteName").getAsString();
						Direction = obj.get("Direction").getAsString();

						RouteMapObj = obj.get("RouteMap").getAsJsonObject();
						String href=RouteMapObj.get("Href").getAsString();
						RouteMap = href;

						re= new BusEstimVO(RouteNo, RouteName, Direction, Schedules);
						schedulesArray = obj.get("Schedules").getAsJsonArray();
						int N = schedulesArray.size();
						for (int k = 0; k < N; k++) {

							JsonObject obj2 =  (JsonObject)schedulesArray.get(k); 

							Pattern = obj2.get("Pattern").getAsString();
							Destination = obj2.get("Destination").getAsString();
							ExpectedLeaveTime = obj2.get("ExpectedLeaveTime").getAsString().toString();
							ExpectedCountdown = obj2.get("ExpectedCountdown").getAsString();
							ScheduleStatus = obj2.get("ScheduleStatus").getAsString();
							CancelledTrip = obj2.get("CancelledTrip").getAsBoolean();
							CancelledStop = obj2.get("CancelledStop").getAsBoolean();
							AddedTrip = obj2.get("AddedTrip").getAsBoolean();
							AddedStop = obj2.get("AddedStop").getAsBoolean();
							LastUpdate = obj2.get("LastUpdate").getAsString();

							ItemSchedule schedulea= re.new ItemSchedule(Pattern, Destination, ExpectedLeaveTime, ExpectedCountdown, ScheduleStatus, CancelledTrip, CancelledStop, AddedTrip, AddedStop, LastUpdate);
							Schedules.add(schedulea);
							ParadaVO anadir=tablaParadasStopCODE.get(paradaI);
							anadir.anadirBus(re);
							if(ScheduleStatus.equals("-"))
							{

								ParadaVO laParada=tablaParadasStopCODE.get(paradaI);
								ParadaVO laParadaVO2=tablaParadasStopID.get(laParada.darId());
								laParadaVO2.setNumeroIncidentes();
								RutaVO rutaBuscada=tablaRutas.get(tablaRutaRouteNumber.get(RouteNo).darRouteid());
								rutaBuscada.darEstimaciones().put(re, re);

								System.out.println(laParada.toString());

							}
						}


					}
				} catch( FileNotFoundException e) {
					System.out.println(e.getMessage() + "error");
				} catch (Exception e) {
					e.printStackTrace();    
					System.out.println(e.getMessage() + "ERROR");

				}

			}}
		catch (Exception e) {
			e.printStackTrace();

		}


	}
	public void cargarUpdatesBuses(File ruta)
	{

		JsonParser parser= new JsonParser();
		String VehicleNo, RouteNo, Direction, Destination, Pattern, RouteMap, RecordedTime, Latitude, Longitude, TripId; 
		JsonObject RouteMapObj = new JsonObject();
		try {
			JsonArray array = (JsonArray) parser.parse(new FileReader(ruta));
			int tam = array.size();
			for(int j = 0; j < tam; j++ ) {
				JsonObject obj = (JsonObject)array.get(j); 

				VehicleNo         = obj.get("VehicleNo").getAsString();
				TripId             = obj.get("TripId").getAsString();
				RouteNo         = obj.get("RouteNo").getAsString();
				Direction         = obj.get("Direction").getAsString();
				Destination     = obj.get("Destination").getAsString();
				Pattern         = obj.get("Pattern").getAsString();
				Latitude         = obj.get("Latitude").getAsString();
				Longitude         = obj.get("Longitude").getAsString();
				RecordedTime     = obj.get("RecordedTime").getAsString();

				RouteMapObj     = obj.get("RouteMap").getAsJsonObject();
				String href     = RouteMapObj.get("Href").getAsString();
				RouteMap         = href;




				BusUpdateVO bus = new BusUpdateVO(VehicleNo, TripId, RouteNo, Direction, Destination, Pattern, Latitude, Longitude, RecordedTime, RouteMap);

				ViajeVO viaje =tablaViajes.get(Integer.parseInt(bus.getTripId()));

				//				viaje.darArbol().put(bus.getRecordedTime(), bus);

			}
		} catch( FileNotFoundException e) {
			System.out.println( "file not found :: " + e.getMessage() + "\n" + e.getStackTrace());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage() + "\n" + e.getStackTrace() + "\n" + e.getLocalizedMessage());
		}



	}



	public void ITScargarTR(String fecha) throws DateNotFoundExpection {
		if (fecha.equals("20170821"))
		{
			File f2 = new File("data/RealTime-8-21-BUSES_SERVICE");
			File[] updateFiles2 = f2.listFiles();

			for (int i = 0; i < updateFiles2.length; i++) 
			{
				cargarUpdatesBuses(updateFiles2[i]);
			}
			File f = new File("data/RealTime-8-21-STOPS_ESTIM_SERVICES");
			File[] updateFiles = f.listFiles();

			for (int i = 0; i < updateFiles.length; i++) 
			{
				String nombre=updateFiles[i].getName();
				String paradaID=nombre.replace("STOPS_ESTIM_SERVICE_", "");
				String [] arreglo=paradaID.split("-");
				int parad=Integer.parseInt(arreglo[0]);
				cargarEstimServices(updateFiles[i],parad);

			}

		}else if(fecha.equals("20170822"))
		{
			File f2 = new File("data/RealTime-8-22-BUSES_SERVICE");
			File[] updateFiles2 = f2.listFiles();

			for (int i = 0; i < updateFiles2.length; i++) 
			{
				cargarUpdatesBuses(updateFiles2[i]);
			}
			File f = new File("data/RealTime-8-22-STOPS_ESTIM_SERVICES");
			File[] updateFiles = f.listFiles();

			for (int i = 0; i < updateFiles.length; i++) 
			{
				String nombre=updateFiles[i].getName();
				String paradaID=nombre.replace("STOPS_ESTIM_SERVICE_", "");
				String [] arreglo=paradaID.split("-");
				int parad=Integer.parseInt(arreglo[0]);
				cargarEstimServices(updateFiles[i],parad);
			}
		}
		else {
			throw new DateNotFoundExpection(); 
		}


	}



	public void cargarTransfers(String ruta)
	{
		try
		{

			BufferedReader bf=new BufferedReader(new FileReader(ruta));
			bf.readLine();
			String linea = bf.readLine();
			while(linea!=null)
			{
				String[] partes=linea.split(",");
				Integer paradaOrigen=Integer.parseInt(partes[0]);

				String control=partes[1]+"-" +partes[2];
				tablaParadasStopID.get(paradaOrigen).getTransferencias().add(control);
				linea = bf.readLine();
			}	
			bf.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void crearGrafoParadas()
	{

		grafoDeParadas=new DirectedGraph<>();
		//A�ado los vertices  al grafo
		for (Integer iterable_element : tablaParadasStopID.keys()) 
		{

			grafoDeParadas.addVertex(iterable_element, tablaParadasStopID.get(iterable_element));
		}




		for (Integer integer : tablaViajes.keys()) 
		{
			tablaViajes.get(integer).crearGrafo();	
		}

		for (Integer integer : tablaViajes.keys()) 
		{
			for (Integer a : tablaViajes.get(integer).getArcos().keys()) 
			{
				grafoDeParadas.addEdge(tablaViajes.get(integer).getArcos().get(a).getOrigen(),tablaViajes.get(integer).getArcos().get(a).getDestino() , tablaViajes.get(integer).getArcos().get(a).getArco());
			}
		}



	}










	//Punto 2(Miguel)
	public SimpleIndividualLinkedList<Stack<Integer>> listaComponentesFuertementeConexas2()
	{
		Cycle<Integer, ParadaVO> kosaraju= new Cycle<Integer, ParadaVO>(grafoDeParadas, 1888,null);
		return kosaraju.darCiclos();
	}

	//Punto 3.1(Miguel)
	public SimpleLinkedList<ParadaVO, SimpleIndividualLinkedList<RutaVO>> darParadasSubgrafo31()
	{
		SimpleLinkedList<ParadaVO, SimpleIndividualLinkedList<RutaVO>> lista= new SimpleLinkedList<>();
		HashTableLinearProbing<Integer, ParadaVO> tabla= Subgrafo.getTablaVertcies();
		for(Integer llave:tabla.keys())
		{
			SimpleIndividualLinkedList<RutaVO> anadible=new SimpleIndividualLinkedList<RutaVO>();
			for(Integer llave2:tablaRutas.keys())
			{
				anadible.add(tablaRutas.get(llave2));
			}
			lista.add(tabla.get(llave), anadible);
		}
		return lista;
	}




	// Punto 3.4 (Nicolas)
	public SimpleIndividualLinkedList< InformeParada >darParadaMasCongestionada()
	{
		int mayor=0;
		ParadaVO rta= null;
		HashTableLinearProbing<Integer, ParadaVO> vertices= Subgrafo.getTablaVertcies();
		for (Integer integer : vertices.keys()) 
		{
			int actual=vertices.get(integer).getTabladeviajesEnRango().darCapacidad();
			if (actual>mayor)
			{
				mayor=actual;

				rta= new ParadaVO();
				rta.setId( vertices.get(integer).darId());
			}
		}
		ParadaVO parada=tablaParadasStopID.get(rta.darId());

		SimpleIndividualLinkedList<InformeParada> listarta= new SimpleIndividualLinkedList<>();
		for (Integer id : parada.getTabladeviajesEnRango().keys()) 
		{
			ViajeVO viaje= parada.getTabladeviajesEnRango().get(id);
			String  llegada=viaje.getArbolDeParadas().get(parada.darId()).getArrival_time();
			String salida= viaje.getArbolDeParadas().get(parada.darId()).getDeparture_time();
			InformeParada anadido= new InformeParada(parada.darId(), viaje.getRouteId(), viaje.getTripId(), llegada, salida);
			listarta.add(anadido);	
		}
		return listarta;

	}


	//Punto 3.5(Miguel)
	public SimpleIndividualLinkedList<Integer> caminoMasCorto35( Integer llaveOrigen, Integer llaveDestino,String hora)
	{
		String[] pHora=hora.trim().split(":");
		int HoraH=Integer.valueOf(pHora[0]);
		int HoraM=Integer.valueOf(pHora[1]);
		caminoMinimo<Integer, ParadaVO> camino=new caminoMinimo<>(Subgrafo);
		SimpleIndividualLinkedList<Integer>retornable=new SimpleIndividualLinkedList<Integer>();
		camino.encontrarCaminoMasCorto(llaveDestino, Subgrafo);
		ParadaVO parada=null;
		for(Integer llave:tablaViajes.keys())
		{
			ViajeVO actual= tablaViajes.get(llave);
			if(actual!=null)
			{
				TiemposParadasVO tiempo=actual.getTabalaParadaID().get(llaveOrigen);
				if(tiempo!=null)
				{
					String[] horarios=tiempo.getDeparture_time().split(":");
					int horarioH=Integer.parseInt(horarios[0].trim());
					int horarioM=Integer.parseInt(horarios[1].trim());
					if(horarioH>HoraH||horarioH==HoraH)
					{	
						if(horarioH==HoraH)
						{
							if(horarioM>HoraM||horarioM==HoraM)
								parada=tiempo.getParada();
						}
						else
						{
							parada=tiempo.getParada();
						}

					}
				}
			}
			if(parada!=null)
				break;
		}
		if(parada!=null)
			retornable=camino.darCamino(parada.darId(), llaveDestino);
		else 
		{
			System.out.println("No hay paradas que concuerden con la hora");
		}
		return retornable;
	}


	//Punto 3.7(Miguel)
	public Stack<Integer> darComponenteFuertementeConexaMasGrande37()
	{
		Stack<Integer>retornable=null;
		HashTableLinearProbing<Integer, ParadaVO> tabla=Subgrafo.getTablaVertcies();
		ParadaVO inicial=null;
		for(Integer llave:tabla.keys())
		{
			ParadaVO parada=tabla.get(llave);
			if(parada!=null)
			{
				inicial=parada;
				break;
			}
		}

		Cycle<Integer, ParadaVO> kosaraju=new Cycle<Integer, ParadaVO>(Subgrafo, inicial.darId(),null);
		SimpleIndividualLinkedList<Stack<Integer>>lista=kosaraju.darCiclos();
		int i=0;
		for(Stack<Integer>pila:lista)
		{
			if(pila.size()>i)
			{
				retornable=pila;
				i=pila.size();
			}
		}
		return retornable; 
	}
	//Punto 3.9(Miguel)
	public RedBlackBST<Integer, ParadaVO> simplificarYDarArbol39()
	{
		RedBlackBST<Integer, ParadaVO> retornable= new RedBlackBST<>();
		GrafoNoDirigido<Integer, ParadaVO> nuevo=simplificarGrafo(Subgrafo);
		return retornable;
	}

	//Punto 1
	@Override
	public IList<caminoVo> darParadasAlcanzables(String stopId, String fecha) 
	{		SimpleIndividualLinkedList<caminoVo> rta= new SimpleIndividualLinkedList<>();
	boolean fechaExcepcion=false;
	for (CalendarDatesVO casos : tablaFechasCalendario.keys()) 
	{
		if (casos.getDate().toString().equals(fecha))
		{
			fechaExcepcion=true;
			break;
		}
	}
	if(fechaExcepcion==false)
	{

		Path<Integer, ParadaVO> paradas=  new Path<>(grafoDeParadas);
		SimpleIndividualLinkedList<Integer> aux= paradas.bfs(Integer.parseInt(stopId), grafoDeParadas);

		int contador =0;

		for (Integer id : aux) 
		{

			caminoVo anadido = new caminoVo();
			anadido.setParadaID(id);
			if (contador!=0&&contador<aux.getSize()) {

				if(paradas.darDeDondeViene(id)!=null) {


					int llaveAux= paradas.darDeDondeViene(id);
					for (Integer caminoVo : tablaRutas.keys()) 

					{

						if (tablaRutas.get(caminoVo).getTablaDeParadas().get(id)!=null && tablaRutas.get(caminoVo).getTablaDeParadas().get(llaveAux)!=null)
						{
							anadido.getTablaDeTrasferencias().put(llaveAux, tablaRutas.get(caminoVo));

						}
					}
					tablaParadasStopID.get(id).setParadaDelLaQueLLego(llaveAux);
					anadido.setParadaIDdeDondeVengo(llaveAux);
					rta.add(anadido);
				}
			}	
			contador++;
		}

		return  rta;
	}
	return rta;


	}
	@Override
	public IList<Stack<Integer>> darComponentesConexas() {
		// TODO Auto-generated method stub
		return listaComponentesFuertementeConexas2();
	}
	/**
	 * Metodo para crear subgrafo
	 */
	@Override
	public void crearSubGrafo(String stopId, String horaInicio, String horaFin) {
		Path<Integer, ParadaVO> paradas=  new Path<>(grafoDeParadas);
		SimpleIndividualLinkedList<Integer> PosiblesVertices= paradas.bfs(Integer.parseInt( stopId), grafoDeParadas);
		HashTableLinearProbing<Integer,SimpleArrayLIst<Edge<Integer>>> PosiblesEjes= new HashTableLinearProbing<>(1000);
		HashTableLinearProbing<Integer, RutaVO> ruatasDeParadas= new HashTableLinearProbing<>(150);
		horaF=Integer.parseInt(horaFin.split(":")[0]);
		minF=Integer.parseInt(horaFin.split(":")[1]);
		HashTableLinearProbing<Integer, ParadaVO> vertices= new HashTableLinearProbing<>(1000);


		int horaFinalH=Integer.parseInt(horaFin.split(":")[0]);
		int horaFinalM=Integer.parseInt(horaFin.split(":")[1]);

		int horaIniciaH=Integer.parseInt(horaInicio.split(":")[0]);
		int horaInicialM=Integer.parseInt(horaInicio.split(":")[1]);


		//Busco todas las rutas que contengan  las paradas alcanzables de una parada
		for (Integer IdParada : PosiblesVertices) 
		{
			vertices.put(IdParada, tablaParadasStopID.get(IdParada));

			PosiblesEjes.put(IdParada, grafoDeParadas.getTablaEjes().get(IdParada));
			//Creo los itirenarios de paradas
			tablaDeItirenario.put(IdParada, new ItirenarioParada());
		}

		for (Integer rutaActual : tablaRutas.keys() ) 
		{
			HashTableLinearProbing<Integer, ViajeVO> viajesDeUnaRuta= tablaRutas.get(rutaActual).getTablaDeViajes();

			for (Integer viajeActualDeUnaRuta : viajesDeUnaRuta.keys()) 
			{


				if(Integer.parseInt(tablaViajes.get(viajeActualDeUnaRuta).getStartDate().split(":")[0].trim())>=horaIniciaH&& horaFinalH>=Integer.parseInt(tablaViajes.get(viajeActualDeUnaRuta).getEndDate().split(":")[0].trim()))

				{
					if(Integer.parseInt(tablaViajes.get(viajeActualDeUnaRuta).getStartDate().split(":")[1].trim())>=horaInicialM && horaFinalM>=Integer.parseInt(tablaViajes.get(viajeActualDeUnaRuta).getEndDate().split(":")[1].trim()))
					{

						//Si se encuentra en el rango verifico que dentro de las paradas de un viaje se  encuentre alguna de las paradas del subgrafo
						// de ser asi lo a�ado a la tabla de  viajes de dicha parada

						for (Integer IdParadaSubgrafo :vertices.keys() ) 
						{




							if(tablaViajes.get(viajeActualDeUnaRuta).getArbolDeParadas().get(IdParadaSubgrafo)!=null)
							{

								tablaParadasStopID.get(IdParadaSubgrafo).getTabladeviajesEnRango().put(viajeActualDeUnaRuta, tablaViajes.get(viajeActualDeUnaRuta));
							}
						}


					}
					else if( tablaViajes.get(viajeActualDeUnaRuta).getEndDate().split(":")[1].contains("00"))
					{
						for (Integer IdParadaSubgrafo :vertices.keys() ) 
						{
							if(tablaViajes.get(viajeActualDeUnaRuta).getArbolDeParadas().get(IdParadaSubgrafo)!=null)
							{
								tablaParadasStopID.get(IdParadaSubgrafo).getTabladeviajesEnRango().put(viajeActualDeUnaRuta, tablaViajes.get(viajeActualDeUnaRuta));
							}
						}
					}

				}
			}


		}

		HashTableLinearProbing<paradaConTasferecnia, paradaConTasferecnia> tablaDeLasParadasConTrasferencia= new HashTableLinearProbing<>(1000);
		//Reviso el tema de las trasbordos
		for (Integer idPardaSubgrafo: vertices.keys()) 
		{
			// Si una prada del subgrafo tiene trasferncias y ahi viajes en el rango por parametro
			if(vertices.get(idPardaSubgrafo).getTransferencias().size()!=0 && vertices.get(idPardaSubgrafo).getTabladeviajesEnRango().darCapacidad()!=0 )
			{
				SimpleArrayLIst<String> trasferneciasDeUnaParada= vertices.get(idPardaSubgrafo).getTransferencias();
				for (int i = 0; i <trasferneciasDeUnaParada.size(); i++) 	
				{
					int trasferencia=Integer.parseInt(trasferneciasDeUnaParada.get(i).split("-")[0]);
					//si alguna de las transferncias hace parte del subrgafo y que esa misma tenga viajes en el rango de horas 
					if( vertices.get(trasferencia)!=null &&vertices.get(trasferencia).getTabladeviajesEnRango().darCapacidad()!=0)
					{
						paradaConTasferecnia anadido= new paradaConTasferecnia();
						//parada "padre"
						anadido.setIdParadaorigen(idPardaSubgrafo);
						//parada "Destino"
						anadido.setIdParadaDestinoConTrasferencia(vertices.get(trasferencia).darId());
						tablaDeLasParadasConTrasferencia.put(anadido, anadido);
					}

				}
			}
		}

		//Busco los viajes con tarsferencias

		for (paradaConTasferecnia tarsferencia : tablaDeLasParadasConTrasferencia.keys()) 
		{
			HashTableLinearProbing<Integer, ViajeVO> viajesEnRango=tablaParadasStopID.get(  tarsferencia.getIdParadaorigen() ).getTabladeviajesEnRango();
			for (Integer idViajes : viajesEnRango.keys()) 
			{
				if(viajesEnRango.get(idViajes).getTabalaParadaID().get(tarsferencia.getIdParadaDestinoConTrasferencia())!=null)
				{
					tarsferencia.getRutasParaLlegar().put(viajesEnRango.get(idViajes).getRouteId(), tablaRutas.get(viajesEnRango.get(idViajes).getRouteId()));
				}
			}
		}

		//Rutas en rango de cada parada

		for (Integer id : vertices.keys()) 
		{
			if(vertices.get(id).getTabladeviajesEnRango().darCapacidad()!=0)
			{
				for (Integer integer : vertices.get(id).getTabladeviajesEnRango().keys()) 
				{
					int rutaId= vertices.get(id).getTabladeviajesEnRango().get(integer).getRouteId();
					tablaParadasStopID.get(id).getTablaRutasEnHoraio().put(rutaId, tablaRutas.get(rutaId));

				}	
			}


		}

		//Cosntruyo el subgrafo

		for (Integer VericeAnadido : vertices.keys()) 
		{
			if(vertices.get(VericeAnadido).getTabladeviajesEnRango().darCapacidad()>0)
			{
				Subgrafo.addVertex(VericeAnadido, vertices.get(VericeAnadido));

			}


		}
		//verfificacion de  ejes
		for (Integer ejes  : grafoDeParadas.getTablaEjes().keys()) 
		{
			if(Subgrafo.getTablaVertcies().get(ejes)!=null )
			{
				SimpleArrayLIst<Edge<Integer>>  ejesPorvertice= grafoDeParadas.getTablaEjes().get(ejes);
				for (int i = 0; i < ejesPorvertice.size(); i++) 
				{

					int llaveOrigen=ejesPorvertice.get(i).getLlaveorigen();
					int llaveDestino=ejesPorvertice.get(i).getLlaveDestino();
					double peso= ejesPorvertice.get(i).getPeso();
					if(Subgrafo.getTablaVertcies().get(llaveOrigen)!=null && Subgrafo.getTablaVertcies().get(llaveDestino)!=null)
					{
						Subgrafo.addEdge(llaveOrigen, llaveDestino, peso);
					}
				}
			}
		}




	}
	@Override
	public SimpleLinkedList<ParadaVO, SimpleIndividualLinkedList<RutaVO>> darParadasSubGrafo() {
		// TODO Auto-generated method stub
		return darParadasSubgrafo31();
	}
	@SuppressWarnings("unchecked")
	@Override
	public IList<InformeParada> darItinerarioLlegada(String stopId) {
		SimpleIndividualLinkedList<InformeParada> listarta= new SimpleIndividualLinkedList<>();
		if(Subgrafo.getTablaVertcies().get(Integer.parseInt(stopId)).getTabladeviajesEnRango()!=null){

			HashTableLinearProbing<Integer, ViajeVO> viajesDeUnaParada=Subgrafo.getTablaVertcies().get(Integer.parseInt(stopId)).getTabladeviajesEnRango();
			for (Integer informeParada : viajesDeUnaParada.keys()) 
			{
				ViajeVO viaje= viajesDeUnaParada.get(informeParada);
				String  llegada=viaje.getArbolDeParadas().get(Integer.parseInt(stopId)).getArrival_time();
				String salida= viaje.getArbolDeParadas().get(Integer.parseInt(stopId)).getDeparture_time();
				InformeParada anadido= new InformeParada(Integer.parseInt(stopId), viaje.getRouteId(), viaje.getTripId(), llegada, salida);
				listarta.add(anadido);
			}

			listarta= listarta.ordenar2(listarta, new Comparator<InformeParada>() {

				@Override
				public int compare(final InformeParada o1, final InformeParada o2) {
					// TODO Auto-generated method stub
					return o1.getHoraLlegada().compareTo(o2.getHoraLlegada());
				}
			});
		}
		return listarta;
	}
	@SuppressWarnings("unchecked")
	@Override
	public IList<InformeParada> darItinerarioSalida(String stopId) {
		IList<InformeParada>rta= darItinerarioLlegada(stopId);
		rta=rta.ordenar2(rta, new Comparator<InformeParada>() {

			@Override
			public int compare(InformeParada o1, InformeParada o2) {
				// TODO Auto-generated method stub
				return o1.getHoraSalida().compareTo(o2.getHoraSalida());
			}
		});
		return rta;
	}
	@Override
	public SimpleIndividualLinkedList<Integer> darCaminoMasCorto(int paradaOrigen,
			int paradaDestino, String hora) {
		// TODO Auto-generated method stub
		return caminoMasCorto35(paradaOrigen,paradaDestino,hora);
	}
	@Override
	public IList<caminoPosibleVO> darCaminoMenorTiempo(String paradaOrigen2,
			String paradaDestino2, String hora) 
	{
		int paradaOrigen=Integer.parseInt(paradaOrigen2);
		int paradaDestino= Integer.parseInt(paradaDestino2);

		int horaparamtero=Integer.parseInt(hora.split(":")[0].trim());
		int minparametero=Integer.parseInt(hora.split(":")[1].trim());
		SimpleIndividualLinkedList<caminoPosibleVO>rta= new SimpleIndividualLinkedList<>();

		DirectedGraph<Integer, ParadaVO> grafoTiempos= new DirectedGraph<>();

		if(Subgrafo.getTablaVertcies().get(paradaOrigen)!=null&&Subgrafo.getTablaVertcies().get(paradaDestino)!=null)
		{ 

			boolean sitenemosVIaje=false;
			for (Integer viaje : Subgrafo.getTablaVertcies().get(paradaOrigen).getTabladeviajesEnRango().keys())
			{
				int horaAux=	Integer.parseInt(Subgrafo.getTablaVertcies().get(paradaOrigen).getTabladeviajesEnRango().get(viaje).getStartDate().split(":")[0].trim());
				int minAux=	Integer.parseInt(Subgrafo.getTablaVertcies().get(paradaOrigen).getTabladeviajesEnRango().get(viaje).getStartDate().split(":")[1].trim());

				if(horaparamtero>=horaAux&& minparametero>=minAux)
				{ sitenemosVIaje=true;
				break;

				}
			}
			if(sitenemosVIaje)
			{


				HashTableLinearProbing<Integer, ParadaVO> tablaDeVertices= Subgrafo.getTablaVertcies();
				HashTableLinearProbing<Integer, SimpleArrayLIst<Edge<Integer>>> tablaDeEjes = Subgrafo.getTablaEjes();

				for (Integer vertice : tablaDeVertices.keys())

				{
					grafoTiempos.addVertex(vertice, tablaParadasStopID.get(vertice));
				}
				//reconstruyo los ejes del grafo para que el peso del los arcos sean el tiempo
				for (Integer eje : tablaDeEjes.keys()) 
				{

					SimpleArrayLIst<Edge<Integer>>arregloDeEjes=tablaDeEjes.get(eje);
					for(int i=0; i<arregloDeEjes.size();i++)
					{
						int stopIDorigen= arregloDeEjes.get(i).getLlaveorigen();
						int stopIDdestino= arregloDeEjes.get(i).getLlaveDestino();
						double arco=0;
						if (tablaParadasStopID.get(stopIDorigen).getTransferencias().size()>0)
						{
							for (int j = 0; j < tablaParadasStopID.get(stopIDorigen).getTransferencias().size(); j++) 
							{
								int trasferneciaPosible=Integer.parseInt(tablaParadasStopID.get(stopIDorigen).getTransferencias().get(j).split("-")[0]);
								if(trasferneciaPosible==stopIDdestino)
								{
									arco+=Integer.parseInt(tablaParadasStopID.get(stopIDorigen).getTransferencias().get(j).split("-")[1]);
								}
							}

						}
						ParadaVO parada= tablaParadasStopID.get(stopIDorigen);

						for (Integer idViaje : parada.getTabladeviajesEnRango().keys()) 
						{
							ViajeVO viaje= parada.getTabladeviajesEnRango().get(idViaje);
							if(viaje.getArbolDeParadas().get(stopIDdestino)!=null&& viaje.getArbolDeParadas().get(stopIDorigen)!=null)
							{ 
								long HoraLLegadaDestino=Integer.parseInt(viaje.getArbolDeParadas().get(stopIDdestino).getArrival_time().split(":")[0].trim());
								long MinLLegadaDestino=Integer.parseInt(viaje.getArbolDeParadas().get(stopIDdestino).getArrival_time().split(":")[1].trim());
								long SegLLegandoDestino=Integer.parseInt(viaje.getArbolDeParadas().get(stopIDdestino).getArrival_time().split(":")[2].trim());

								long HoraSalidaOrigen=Integer.parseInt(viaje.getArbolDeParadas().get(stopIDorigen).getDeparture_time().split(":")[0].trim());
								long MinLlegadaOrigen=Integer.parseInt(viaje.getArbolDeParadas().get(stopIDorigen).getDeparture_time().split(":")[1].trim());
								long SegSalidaOrigen= Integer.parseInt(viaje.getArbolDeParadas().get(stopIDorigen).getDeparture_time().split(":")[2].trim());

								double Origen1=TimeUnit.HOURS.toSeconds(HoraSalidaOrigen);
								double Origen2= TimeUnit.MINUTES.toSeconds(MinLlegadaOrigen);
								double Origen3= TimeUnit.SECONDS.toSeconds(SegSalidaOrigen);

								double tiempOrigen= Origen1+Origen2+Origen3;

								double Salida1=TimeUnit.HOURS.toSeconds(HoraLLegadaDestino);
								double Salida2= TimeUnit.MINUTES.toSeconds(MinLLegadaDestino);
								double Salida3= TimeUnit.SECONDS.toSeconds(SegLLegandoDestino);
								double tiempoFinal=Salida1+Salida2+Salida3;							





								arco+= tiempoFinal-tiempOrigen;

								grafoTiempos.addEdge(stopIDorigen, stopIDdestino, arco);
							}
						}


					}
				}

				//Dijkstra para encontrar el camino mas corto
				SimpleIndividualLinkedList<Integer> llaves= new SimpleIndividualLinkedList<>();
				HashTableLinearProbing<Integer, ParadaVO>aux= new HashTableLinearProbing<>(1000);
				double costoRecorrido=0;
				caminoMinimo<Integer, ParadaVO> camino= new caminoMinimo<>(grafoTiempos);
				camino.encontrarCaminoMasCorto(paradaOrigen, grafoTiempos);
				llaves=camino.darCamino(paradaOrigen, paradaDestino);
				caminoMinimoVo auxiliar= new caminoMinimoVo();
				int contador=0;

				for (Integer integer : llaves) 
				{ 
					if(contador!=0)
					{
						Subgrafo.getTablaVertcies().get(integer).setParadaDijkstra(camino.llavePadre(integer));


					}
					else if(contador==0)
					{
						Subgrafo.getTablaVertcies().get(integer).setParadaDijkstra(integer);


					}
					contador++;

				}
				costoRecorrido=camino.darCostoMimoDeRecorrido(paradaDestino);


				SimpleIndividualLinkedList<caminoPosibleVO>llavesss= new SimpleIndividualLinkedList<>();
				for (Integer caminoPosibleVO : llaves)
				{
					HashTableLinearProbing<Integer, ViajeVO> tablita=tablaParadasStopID.get(caminoPosibleVO).getTabladeviajesEnRango();
					for (Integer viaje : tablita.keys()) 
					{
						
						if (Integer.parseInt(tablita.get(viaje).getStartDate().split(":")[0].trim())<=horaparamtero&&Integer.parseInt(tablita.get(viaje).getStartDate().split(":")[1].trim())<=minparametero)
						{
							caminoPosibleVO anadido= new caminoPosibleVO(caminoPosibleVO, tablaParadasStopID.get(caminoPosibleVO).getParadaDijkstra(), viaje, tablaViajes.get(viaje).getRouteId(), tablaViajes.get(viaje).getArbolDeParadas().get(caminoPosibleVO).getArrival_time());
							llavesss.addAtEnd(anadido);

						}
					}
				}

				rta= llavesss;
				rta.darPrimero().darElemento().setCosto(costoRecorrido);
			}
		}
		return rta;

	}
	@Override
	public Stack<Integer> darMayorComponenteConexa() 
	{
		// TODO Auto-generated method stub
		return darComponenteFuertementeConexaMasGrande37();
	}
	@Override
	public IList<String> darCiclo(String horaInicio) 
	{
		int HoraParametro=Integer.parseInt(horaInicio.split(":")[0]);
		int MinParametro= Integer.parseInt(horaInicio.split(":")[1]);

		ParadaVO parada= new ParadaVO();
		for (Integer iterable_element :Subgrafo.getTablaVertcies().keys() ) 
		{
			HashTableLinearProbing<Integer, ViajeVO> tabla=	Subgrafo.getTablaVertcies().get(iterable_element).getTabladeviajesEnRango();
			for (Integer viajeId : tabla.keys()) 
			{
				if (tabla.get(viajeId).getStartDate().contains(horaInicio))
				{
					parada= tablaParadasStopID.get(iterable_element);

				}
			}		
		}

		Cycle<Integer, ParadaVO> ciclo= new Cycle<Integer, ParadaVO>(Subgrafo, parada.darId(), null);
		int aux=0;

		Stack<Integer> aux2= new Stack<>();
		for (Stack<Integer> pila : ciclo.darCiclos()) 
		{
			if(pila.size()>aux)
			{
				aux=pila.size();
				aux2=pila;
			}
		}

		IList<ParadaVO> paradaLista= new SimpleIndividualLinkedList<>();
		IList<String> ret = new SimpleIndividualLinkedList<>();


		while(aux2.iterator().hasNext())	
		{


			ParadaVO actual=tablaParadasStopID.get(aux2.pop());

			paradaLista.add(actual);

		}

		for (ParadaVO actual : paradaLista) 
		{
			//Reviso todos los viajes que componen el ciclo
			if (actual.getTabladeviajesEnRango().darCapacidad()>0 )
			{
				for (Integer a : actual.getTabladeviajesEnRango().keys()) 
				{
					//Si el viaje se encuentra en el rango
					int HoaraActualViajeInicio= Integer.parseInt(actual.getTabladeviajesEnRango().get(a).getStartDate().split(":")[0].trim());
					int MinActualViajeInicio=Integer.parseInt(actual.getTabladeviajesEnRango().get(a).getStartDate().split(":")[1].trim());


					int HoraFinalActual=Integer.parseInt(actual.getTabladeviajesEnRango().get(a).getEndDate().split(":")[0].trim());
					int MinFinalActual= Integer.parseInt(actual.getTabladeviajesEnRango().get(a).getEndDate().split(":")[1].trim());					
					
					if(HoraParametro>=HoaraActualViajeInicio&&HoraParametro<=HoraFinalActual)
						
					{
						if(MinParametro>=MinActualViajeInicio&&MinParametro<=MinFinalActual)
						{
							
						String anadidoRet= "Parada:"+actual.darId()+
								" Ruta:"+actual.getTabladeviajesEnRango().get(a).getRouteId()+
								" Viaje:"+actual.getTabladeviajesEnRango().get(a).getTripId()+
								" Horario:"+actual.getTabladeviajesEnRango().get(a).getArbolDeParadas().get(actual.darId()).getArrival_time();

						ret.add(anadidoRet);
						}
					}
				}
			}
		}


		return ret;
	}
	@Override
	public GrafoNoDirigido<Integer, ParadaVO> simplificarGrafo(DirectedGraph<Integer, ParadaVO> dirigido)
	{
		GrafoNoDirigido<Integer, ParadaVO> retornable= new GrafoNoDirigido<>();
		retornable.setListaVertices(dirigido.getTablaVertcies());
		HashTableLinearProbing<Integer, Edge<Integer>> marcados= new HashTableLinearProbing<>(1000);
		for(Integer llave:dirigido.getTablaEjes().keys())
		{
			SimpleArrayLIst<Edge<Integer>> lista= dirigido.getTablaEjes().get(llave);
			int anadir=0;
			int llaveAuxiliar=0;
			for(int i=0;i<lista.size();i++)
			{
				Edge<Integer> actual=lista.get(i);
				if(marcados.get(actual.getLlaveorigen())==null)
				{
					marcados.put(actual.getLlaveorigen(), actual);
					SimpleArrayLIst<Edge<Integer>> vecinos=dirigido.darVecinosVertice(actual.getLlaveorigen());
					for(int j=0;j<vecinos.size();j++)
					{
						if(vecinos.get(j).getLlaveDestino()!=llave)
						{
							retornable.addEdge(llave, vecinos.get(j).getLlaveorigen(), actual.getPeso());
						}
						else
						{
							llaveAuxiliar=vecinos.get(j).getLlaveorigen();
							anadir+=actual.getPeso();
						}
					}
				}
			}
			retornable.addEdge(llave, llaveAuxiliar, anadir);
		}

		return retornable;

	}
	@Override
	public void darArbolRecubrimientoMinimo(String horaInicio)
	{

		GrafoNoDirigido<Integer, ParadaVO> nuevo= simplificarGrafo(Subgrafo);
		Kruskal2<Integer, ParadaVO> kruskal= new Kruskal2<>(nuevo);
	}
	@Override
	public void ITSInit() {
		// TODO Auto-generated method stub

	}
	@Override
	public void ITScargarGTFS() {
		// TODO Auto-generated method stub
		cargarServicioBuses("data/calendar.txt");

		cargarRutas("data/routes.txt");
		cargarViajes("data/trips.txt");
		cargarParadas("data/stops.txt");
		cargarHorasParadas("data/stop_times.txt"); 
		cargarAgencias("data/agency.txt");
		cargarFechasCalendarios("data/calendar_dates.txt");
		cargarTransfers("data/transfers.txt");
		cargarParadasPorRuta();
		crearGrafoParadas();

	}

}	
