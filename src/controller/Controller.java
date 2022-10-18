package controller;

import data_structures.IList;
import data_structures.SimpleIndividualLinkedList;
import data_structures.SimpleLinkedList;
import data_structures.Stack;
import api.ISTSManager;
import model.logic.ITSManager;
import model.vo.InformeParada;
import model.vo.ParadaVO;
import model.vo.RutaVO;
import model.vo.VOParadaCamino;
import model.vo.caminoMinimoVo;
import model.vo.caminoPosibleVO;
import model.vo.caminoVo;


public class Controller 
{
	/**
	 * modela el manejador de la clase l�gica
	 */
	public static ITSManager manager  = new ITSManager();

	public static void ITSInit() 
	{
		
	}

	 
	public static void ITScargarGTFS() 
	{
		manager.ITScargarGTFS();	
	}

	 	
	public static IList<caminoVo> darParadasAlcanzables(String stopId, String fecha) {
		return manager.darParadasAlcanzables(stopId, fecha);
	}

	
	public static IList<Stack<Integer>> darComponentesConexas() {
		return manager.darComponentesConexas();
	}

	
	public static void crearSubGrafo(String stopId, String horaInicio, String horaFin) {
		manager.crearSubGrafo(stopId, horaInicio, horaFin);
	}

	
	public static SimpleLinkedList<ParadaVO, SimpleIndividualLinkedList<RutaVO>> darParadasSubGrafo() {
		return manager.darParadasSubGrafo();
	}

	
	public static IList<InformeParada> darItinerarioLlegada(String stopId) {
		return manager.darItinerarioLlegada(stopId);
	}

	
	public static IList<InformeParada> darItinerarioSalida(String stopId) {
		return manager.darItinerarioSalida(stopId);
	}

	
	public static IList<InformeParada> darParadaMasCongestionada() {
		return manager.darParadaMasCongestionada();
	}

	
	public static SimpleIndividualLinkedList<Integer> darCaminoMasCorto(int paradaOrigen, int paradaDestino, String hora) {
		return manager.darCaminoMasCorto(paradaOrigen, paradaDestino, hora);
	}

	
	public static IList<caminoPosibleVO> darCaminoMenorTiempo(String paradaOrigen, String paradaDestino, String hora) {
		return manager.darCaminoMenorTiempo(paradaOrigen, paradaDestino, hora);
	}

	
	public static Stack<Integer> darMayorComponenteConexa() {
		return manager.darMayorComponenteConexa();
	}

	
	public static IList<String> darCiclo(String horaInicio) {
		return manager.darCiclo(horaInicio);
	}
	
//	public static MST darArbolRecubrimientoMinimo(String horaInicio) {
//		manager.simplificarGrafo();
//		return manager.darArbolRecubrimientoMinimo(horaInicio);
//	}
//	 
	
}
