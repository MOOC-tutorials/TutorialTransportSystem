
package data_structures;

import java.awt.List;
import java.util.Comparator;


/**
 * Esta clase utiliza el algoritmo de dijkstra para encontrar el  camino mas corto (relacionado con los arcos(
 * 
 * @param <K>
 * @param <V>
 */
public class caminoMinimo <K,V>{

	private HashTableLinearProbing<V, Boolean> marcados;
	private MinPQ<Vertice<V>> colaDePrioridad;
	private HashTableLinearProbing<K, Vertice<V>> manejoDeDistancias;
	private HashTableLinearProbing<V, K> tablaaux;
	private HashTableLinearProbing<K, V> DondeVengo;
	private HashTableLinearProbing<V, K> DondeVengoValores;

	public caminoMinimo(DirectedGraph<K, V> grafo ) 
	{
		marcados= new HashTableLinearProbing<>(grafo.getTablaVertcies().getSize());
		DondeVengo= new HashTableLinearProbing<>(grafo.getTablaVertcies().getSize());
		DondeVengoValores= new HashTableLinearProbing<>(grafo.getTablaVertcies().getSize());

		colaDePrioridad= new MinPQ<>(grafo.getTablaVertcies().getSize(), new Comparator<Vertice<V>>() {

			@Override
			public int compare(Vertice<V> arg0, Vertice<V> arg1) {
				// TODO Auto-generated method stub
				Double a=arg0.getDistancia();

				return a.compareTo(arg1.getDistancia());
			}
		});

		manejoDeDistancias= new HashTableLinearProbing<>(grafo.getTablaVertcies().getSize());

		tablaaux= new HashTableLinearProbing<>(grafo.getTablaVertcies().getSize());

		for (K iterable_element : grafo.getTablaVertcies().keys()) 
		{
			Vertice<V> anadido= new Vertice<>();
			anadido.setDistancia(Double.POSITIVE_INFINITY);
			anadido.setItem(grafo.getTablaVertcies().get(iterable_element));
			anadido.setLlave(iterable_element);
			manejoDeDistancias.put(iterable_element,anadido );

		}
		for (K iterable_element : grafo.getTablaVertcies().keys()) 
		{
			Vertice<V> anadido= new Vertice<>();
			V item= grafo.getTablaVertcies().get(iterable_element);	
			anadido.setItem(item);
			marcados.put(anadido.getItem(), false);
			tablaaux.put(anadido.getItem(), iterable_element);
		}
		for(K iterable_element : grafo.getTablaVertcies().keys())
		{

			DondeVengo.put(iterable_element, null);
			DondeVengoValores.put( grafo.getTablaVertcies().get(iterable_element), null);

		}
	}
	/**
	 * Intento 1.0  de dijkstra
	 * @param llaveOrigen
	 * @param llaveDestino
	 * @param grafo
	 * @return
	 */
	public void encontrarCaminoMasCorto(K llaveOrigen, DirectedGraph<K, V> grafo)

	{



		//Verifico que exista 
		if(grafo.getTablaVertcies().get(llaveOrigen)!=null)
		{
			//busco la llave origen y actualizo su valor a 0 luego lo inserto a una cola de prioridad orientada al minimo por distancia del vertice
			Vertice<V> aux= manejoDeDistancias.get(llaveOrigen);
			aux.setDistancia(0);
			manejoDeDistancias.put(llaveOrigen, aux);	

			colaDePrioridad.insert(aux);
			while (!colaDePrioridad.isEmpty()) 
			{
				//desencolo el elemento menor de la cola de prioridad
				Vertice<V> evaluador= colaDePrioridad.delMin();


				if(marcados.get(evaluador.getItem())==false)
				{ // marco el vertice como visitado
					marcados.put(evaluador.getItem(), true);

					//busco los vecinos de la llave actual
					K llave=tablaaux.get(evaluador.item);
					SimpleArrayLIst<Edge<K>> ejes= grafo.darVecinosVertice(llave);
					if (ejes!=null)
					{
						//Para cada uno de los vecinos.	
						for (int i = 0; i < ejes.size(); i++) 
						{ 
							K llaveAux = ejes.get(i).getLlaveDestino();

							double nuevaDistancia=ejes.get(i).getPeso()+evaluador.distancia;

							//Cambio la distancia de ese nodo si la distancia es menor a la actual 
							if(nuevaDistancia<manejoDeDistancias.get(llaveAux).getDistancia())		 
							{
								Vertice<V> elVertice=  manejoDeDistancias.get(llaveAux);
								elVertice.setDistancia(nuevaDistancia);
								manejoDeDistancias.put(llaveAux, elVertice);
								
								DondeVengo.put(llaveAux, evaluador.item);


							}
							//Inserto en la cola de prioridad.
							colaDePrioridad.insert(manejoDeDistancias.get(llaveAux));


						}
					}


				}

			}
			

		}



	}
	public double darCostoMimoDeRecorrido(K llave ) 
	{
		double  rta= manejoDeDistancias.get(llave).distancia;
		
			return rta;
		
	

	}
	public SimpleIndividualLinkedList<K> darCamino(K llaveOrigen, K llavedestino)
	{
		V predecesor= DondeVengo.get(llavedestino);
		SimpleIndividualLinkedList<K>rta = new SimpleIndividualLinkedList<>();
		while (predecesor!=manejoDeDistancias.get(llaveOrigen)&&predecesor!=null) 
		{
		
			rta.add(tablaaux.get(predecesor));
			
			predecesor= DondeVengo.get(tablaaux.get(predecesor));
		}
		if(llavedestino!=llaveOrigen)
		{
			rta.addAtEnd(llavedestino);
		}
		
		return rta;
	}



	public class Vertice<T>
	{
		public K llave;
		public K getLlave() {
			return llave;
		}
		public void setLlave(K llave) {
			this.llave = llave;
		}
		public V item;
		public double distancia;
		public V getItem() {
			return item;
		}
		public void setItem(V item) {
			this.item = item;
		}
		public double getDistancia() {
			return distancia;
		}
		public void setDistancia(double distancia) {
			this.distancia = distancia;
		}

	}
	public K llavePadre(K valor)
	{
		 V valorObjeto=DondeVengo.get(valor);
		
		return tablaaux.get(valorObjeto);
	}
}
