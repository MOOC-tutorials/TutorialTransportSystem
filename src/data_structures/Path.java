package data_structures;

import java.util.Comparator;


public class Path <K,V>{


	public HashTableLinearProbing<K, Boolean> visitado= new HashTableLinearProbing<>(1000000);
	public HashTableLinearProbing<K, K> DeDondeViene= new HashTableLinearProbing<>(100000);

	public Path(DirectedGraph<K, V> grafo) 
	{
		for (K iterable_element : grafo.getTablaVertcies().keys()) 
		{
			visitado.put(iterable_element, false);

		}
		for(K iterable_element : grafo.getTablaVertcies().keys()) 
		{
			DeDondeViene.put(iterable_element, null);

		}

	}
	public SimpleIndividualLinkedList<K> bfs(K llaveOrigen,DirectedGraph<K, V> grafo)
	{
		SimpleIndividualLinkedList<K> rta = new SimpleIndividualLinkedList<>();
		if (grafo.getTablaVertcies().get(llaveOrigen)!=null)
		{
			Cola<K> cola= new Cola<>();

			cola.enqueue(llaveOrigen);

			visitado.put(llaveOrigen, true);

			while (!cola.estaVacia()) 
			{
				K llave= cola.dequeue();



				rta.add(llave);

				SimpleArrayLIst<Edge<K>> arreglo =grafo.darVecinosVertice(llave);


				for (int i = 0; i < arreglo.size(); i++) 
				{
					K llave2= arreglo.get(i).getLlaveDestino();
					if (!visitado.get(llave2))
					{	
						visitado.put(llave2, true);
						DeDondeViene.put(llave2,llave );

						cola.enqueue(llave2);
					}

				}



			}
		}

		return rta;
	}
	/**
	 * llave origen debe ser igual a la del bfs
	 * @param llaveOrigen
	 * @param llavedestino
	 * @return
	 */

	public SimpleIndividualLinkedList<K> recorridoMnimoRespectoAvertices( K origen, K llavedestino)
	{
		SimpleIndividualLinkedList<K>	rta= new SimpleIndividualLinkedList<>();
		K llaveAux=DeDondeViene.get(llavedestino);
		while(llaveAux!= origen && llaveAux!=null )
		{
			rta.add(llaveAux);
			
			llaveAux=DeDondeViene.get(llaveAux);
		}
		if(llavedestino!=origen)
		{
			rta.add(origen);
			rta.addAtEnd(llavedestino);
		}else
		{
			
			rta.add(llavedestino);
		}

		return rta;
	}
	public  K darDeDondeViene(K llave)
	{
		return DeDondeViene.get(llave);
	}

}
