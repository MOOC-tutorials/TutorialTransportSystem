package data_structures;
/**
 * Clase que dado un grafo dirigido encuentra los componentes conectados del grafo(Estructura union find)
 *
 */
public class CC<K,V> 
{
		public int cantidad_componentes_conectados ( DirectedGraph<K, V> grafos ) 
		{
			return  DFSComplete(grafos).darCapacidad();
		}
		
		
		public HashTableLinearProbing<K, V> DFSComplete ( DirectedGraph<K,V> grafo ) {

			SimpleIndividualLinkedList<K> listaDeConocidos = new SimpleIndividualLinkedList<>();
		HashTableLinearProbing<K, V> forest = new HashTableLinearProbing<>(100);
			for (K llaves : grafo.getTablaVertcies().keys())
				if (!listaDeConocidos.contiene(llaves))
					DFS(grafo, llaves, listaDeConocidos, forest);
			verificar(forest, grafo);
			return forest;
		}
		public void DFS ( DirectedGraph<K, V> g ,  K src ,  SimpleIndividualLinkedList<K> known  , HashTableLinearProbing<K, V> forest   ) {

			known.addAtEnd( src ); 
			for (int i = 0; i < g.darVecinosVertice( src ).size(); i++) {
				@SuppressWarnings("unchecked")
				K eje=(K) g.darVecinosVertice(src).get(i).getLlaveDestino();
			
				if (!known.contiene(eje)) {					
					forest.put(eje, g.getTablaVertcies().get(eje));                      	
					DFS(g, eje, known, forest);              
				}
			}
		}
		
		
			public  void verificar (HashTableLinearProbing<K, V> forest, DirectedGraph<K, V>g)
			{
				for (K iterable_element : g.getTablaVertcies().keys()) {
					if(forest.get(iterable_element)==null&& g.getTablaEjes().get(iterable_element)!=null&&g.getTablaEjes().get(iterable_element).size()>0)
					{
						forest.put(iterable_element, g.getTablaVertcies().get(iterable_element));
					}
				}
			}
		
	}