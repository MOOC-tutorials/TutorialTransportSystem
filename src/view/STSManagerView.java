package view;

import java.util.Scanner;

import controller.Controller;



import data_structures.HashTableLinearProbing;
import data_structures.IList;
import data_structures.SimpleIndividualLinkedList;
import data_structures.SimpleLinkedList;
import data_structures.Stack;
import data_structures.caminoMinimo;
import model.vo.InformeParada;
import model.vo.ParadaVO;
import model.vo.RutaVO;
import model.vo.TiemposParadasVO;
import model.vo.caminoMinimoVo;
import model.vo.caminoPosibleVO;
import model.vo.caminoVo;;

public class STSManagerView {

	/**
	 * Main
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean fin = false;
		while (!fin) {
			printMenu();

			int option = sc.nextInt();

			switch (option) {
			// Cargar
			case 1:

				// Memoria y tiempo
				long memoryBeforeCase1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				long startTime = System.nanoTime();

				// Cargar data
				Controller.ITScargarGTFS();

				// Tiempo en cargar
				long endTime = System.nanoTime();
				long duration = (endTime - startTime) / (1000000);

				// Memoria usada
				long memoryAfterCase1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				System.out.println("Tiempo en cargar: " + duration + " milisegundos \nMemoria utilizada:  "
						+ ((memoryAfterCase1 - memoryBeforeCase1) / 1000000.0) + " MB");

				break;

				// 1
			case 2:
				// Parada
				System.out.println("Ingrese el id de la parada:");
				String stopId = sc.next();
				// Fecha deseada
				System.out.println(
						"Ingrese la fecha deseada Ej: 20170625 (AnoMesDia) \n Esta fecha se utilizara para los otros metodos.");
				String fechaCase2 = sc.next();

				IList<caminoVo> paradasAlcanzables = Controller.darParadasAlcanzables(stopId, fechaCase2);

				for (caminoVo camino : paradasAlcanzables)
				{
					System.out.println("Id de parada "+camino.getParadaID()+ " Transferencias disponibles: "+camino.getTablaDeTrasferencias().darCapacidad()+ " para llegar"+ camino.getParadaIDdeDondeVengo());

				}


				break;
				// 2
			case 3:

				IList<Stack<Integer>> componentes = Controller.darComponentesConexas();
				int i = 1;
				ParadaVO p= new ParadaVO();
				int tamanio=0;
				//TODO Imprimir respuesta de las componentes conexas
				for (Stack<Integer> iList : componentes) 
				{
					if(iList.size()!=tamanio)
					{
						SimpleIndividualLinkedList<Integer> lista=iList.lista;
						lista=lista.ordenar2(lista, p.comparadorPorId);
						System.out.println("Componente " + i++);
						for (Integer voParada : lista) 
						{
							System.out.println("ID parada: "+ voParada);
						}
						tamanio=iList.size();
					}
				}
				break;


				// 3
			case 4:
				System.out.println("Ingrese el id de la parada origen");
				String stopId4 = sc.next();
				System.out.println("Ingrese la hora de Inicio");
				String horaInicio = sc.next();
				System.out.println("Ingrese la hora de fin");
				String horaFin = sc.next();
				Controller.crearSubGrafo(stopId4, horaInicio, horaFin);
				break;

				// 3.1
			case 5:
				SimpleLinkedList<ParadaVO, SimpleIndividualLinkedList<RutaVO>> paradas = Controller.darParadasSubGrafo();
				for (ParadaVO voParada : paradas) 
				{
					System.out.println("ParadaID: "+voParada.darId());
					System.out.println("rutas: ");
					SimpleIndividualLinkedList<RutaVO> nueva=paradas.getElementByKey(voParada);
					for(RutaVO ruta:nueva)
					{
						System.out.println("RouteID: "+ruta.darRouteid());
					}
				}
				break;

				// 3.2
			case 6:
				System.out.println("Ingrese el id de la parada a consultar");
				String stopId6 = sc.next();
				IList<InformeParada> llegadas = Controller.darItinerarioLlegada(stopId6);
				for (InformeParada voStopTime : llegadas) 
				{
					System.out.println("RutaId: "+voStopTime.getIDruta()+"TriId: "+voStopTime.getIDViaje()+"Hora llegada: "+voStopTime.getHoraLlegada());
				}
				break;

				// 3.3
			case 7:
				System.out.println("Ingrese el id de la parada a consultar");
				String stopId7 = sc.next();
				IList<InformeParada> salidas = Controller.darItinerarioSalida(stopId7);
				for (InformeParada voStopTime : salidas) 
				{
					System.out.println("RutaId: "+voStopTime.getIDruta()+"TriId: "+voStopTime.getIDViaje()+"Hora salida: "+voStopTime.getHoraSalida());

				}
				break;

				// 3.4
			case 8:
				IList<InformeParada> paradaCongestionada = Controller.darParadaMasCongestionada();
				System.out.println("La parada mas congestionada es "+paradaCongestionada.getCurrentElement().getIDparada()+ " Viajes en la parada de salida y de llegada : "+paradaCongestionada.getSize());
				break;

				// 3.5
			case 9:
				System.out.println("Ingrese el id de la parada de origen");
				String paradaO = sc.next();
				int paradaOI= Integer.parseInt(paradaO);
				System.out.println("Ingrese el id de la parada de destino");
				String paradaDestino = sc.next();
				int paradaDestinoI=Integer.parseInt(paradaDestino);

				System.out.println("Ingrese la hora");
				String hora = sc.next();


				SimpleIndividualLinkedList<Integer> camino= Controller.darCaminoMasCorto(paradaOI, paradaDestinoI, hora);
				System.out.println("El camino mas corto pasa por las siguientes paradas:");
				HashTableLinearProbing<Integer, ParadaVO> emergencia=Controller.manager.tablaParadasStopID;
				for (Integer voParadaCamino : camino)
				{
					//TODO Imprimir la informacion del camino
					System.out.println("ParadaID: "+voParadaCamino);
					ParadaVO actual=emergencia.get(voParadaCamino);
					for(Integer ruta:actual.getTablaRutasEnHoraio().keys())
					{
						System.out.println("RutaId :"+actual.getTablaRutasEnHoraio().get(ruta).darRouteid());
					}

				}
				break;

				// 3.6
			case 10:
				System.out.println("Ingrese el id de la parada de origen");
				String paradaOrigen = sc.next();

				System.out.println("Ingrese el id de la parada de destino");
				String paradaD = sc.next();

				System.out.println("Ingrese la hora");
				String hora3F = sc.next();

				IList<caminoPosibleVO> camino3F= Controller.darCaminoMenorTiempo(paradaOrigen, paradaD, hora3F);
				System.out.println("El recorrido de mennor costo para llegar de "+paradaOrigen+" a "+paradaD+ " es : \n" );
				for (caminoPosibleVO voParadaCamino : camino3F) 
				{
					System.out.println( "Id de ruta "+voParadaCamino.getRouteId()+" Viaje ID: "+voParadaCamino.getTripId()+ " Parada Id origen : "+voParadaCamino.getParadaIDOrigen()+" Parada Id destino: "+voParadaCamino.getParadaIDDestino() + " Horario: "+voParadaCamino.getTiempo());
				}

				System.out.println("El tiempo total de viaje es: "+camino3F.darPrimero().darElemento().getCosto()+" Segundos");
				break;
				// 3.7
			case 11:

				Stack<Integer> cc = Controller.darMayorComponenteConexa();
				System.out.println("La componente conexa mas grande esta compuesta por las siguientes paradas: ");
				for (Integer voParada : cc) {
					System.out.println("ParadaId: "+voParada);
				}

				break;

				// 3.8 
			case 12:
				System.out.println("Ingrese la hora de inicio para la b�squeda");
				String horaInicio12 = sc.next();

				IList<String> ciclo = Controller.darCiclo(horaInicio12);
				System.out.println("Las paradas que conforman el ciclo son :");
				for(String a: ciclo)
				{
					System.out.println(a);
				}
				break;

				// 3.9
			case 13:
				//								System.out.println("Ingrese la hora de inicio para la b�squeda");
				//								String horaInicio13 = sc.next();
				//								MST arbol = Controller.darArbolRecubrimientoMinimo(horaInicio13);
				//		
				break;
				// SALIR
			case 14:

				fin = true;
				sc.close();
				break;

			}
		}
	}

	/**
	 * Menu
	 */

	private static void printMenu() {
		System.out.println("---------ISIS 1206 - Estructuras de datos----------");
		System.out.println("---------------------Proyecto 3----------------------");
		System.out.println("1. Cargar la informaci�n est�tica necesaria para la operaci�n del sistema");
		System.out.println("2. Dar las paradas alcanzables desde una parada");
		System.out.println(
				"3. Dar componentes fuertemente conexas en el grafo de paradas");
		System.out.println(
				"4. Crear un subgrafo a partir de una fecha, parada origen y rango de hora dadas");
		System.out.println(
				"5. Dar las paradas del subgrafo");
		System.out.println("6. Dar el itinerario de llegada de una parada");
		System.out.println(
				"7.Dar el itinerario de salida de una parada");
		System.out.println(
				"8. Dar la parada m�s congestionada (con m�s viajes de llegada y de salida) del subgrafo");
		System.out.println(
				"9. Dar el camino m�s corto (de menor distancia) entre dos paradas");
		System.out.println(
				"10. Dar el camino de menor tiempo entre dos paradas");
		System.out.println(
				"11. Dar la mayor componente conexa del subgrafo");
		System.out.println("12. Dar un ciclo simple del subgrafo");
		System.out.println(
				"13. Dar el �rbol de recubrimiento m�nimo del grafo simplicado");
		System.out.println("14. Salir.\n");
		System.out.println("Ingrese la opci�n deseada y luego presione enter: (e.g., 1):");

	}
}