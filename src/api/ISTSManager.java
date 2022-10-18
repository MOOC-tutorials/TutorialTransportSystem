package api;

import model.vo.InformeParada;
import model.vo.ParadaVO;
import model.vo.RutaVO;
import model.vo.TiemposParadasVO;
import model.vo.VOParadaCamino;
import model.vo.caminoMinimoVo;
import model.vo.caminoPosibleVO;
import data_structures.DirectedGraph;
import data_structures.GrafoNoDirigido;
import data_structures.IList;
import data_structures.Kruskal;
import data_structures.SimpleIndividualLinkedList;
import data_structures.SimpleLinkedList;
import data_structures.Stack;


public interface ISTSManager {

	public IList darParadasAlcanzables(String stopId, String fecha);

	public IList<Stack<Integer>> darComponentesConexas();

	public void crearSubGrafo(String stopId, String horaInicio, String horaFin);

	public SimpleLinkedList<ParadaVO, SimpleIndividualLinkedList<RutaVO>> darParadasSubGrafo();

	public IList<InformeParada> darItinerarioLlegada(String stopId);

	public IList<InformeParada> darItinerarioSalida(String stopId);

	public IList<InformeParada> darParadaMasCongestionada();

	public IList<caminoPosibleVO> darCaminoMenorTiempo(String paradaOrigen, String paradaDestino, String hora);
	
	public Stack<Integer> darMayorComponenteConexa();

	public IList<String> darCiclo(String horaInicio);
	
	public void darArbolRecubrimientoMinimo(String horaInicio);

	public void ITSInit();

	public void ITScargarGTFS();

	SimpleIndividualLinkedList<Integer> darCaminoMasCorto(int paradaOrigen,
			int paradaDestino, String hora);

	GrafoNoDirigido<Integer, ParadaVO> simplificarGrafo(
			DirectedGraph<Integer, ParadaVO> dirigido);

}
