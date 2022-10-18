package data_structures;

/**
 * 
 *API que sigue un grafo dirigdo  
 */
 public interface IGraph <K,V> {
	

/**
 * Agrega un vertice al grafo
 * @param llave
 * @param informacion
 */
public void addVertex(K llave,V informacion);
/**
 * Añade una conexion al grafo 
 * @param llaveInicio
 * @param llaveFinal
 * @param peso
 */
public void addEdge( K llaveInicio ,K llaveFinal, double peso );

}
