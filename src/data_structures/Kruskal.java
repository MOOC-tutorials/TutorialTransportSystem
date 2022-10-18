package data_structures;

import java.util.Comparator;
import java.util.Iterator;




public class Kruskal<K,V>
{
	//La llave me representa a todoslos contenidos relacionados con esta
	private  HashTableLinearProbing<K,SimpleArrayLIst<K>> Representantes;

	private SimpleArrayLIst<Edge<K>> ejes;
	public Kruskal (DirectedGraph<K, V> grafo) 
	{
		ejes=new SimpleArrayLIst<>();
		Representantes= new HashTableLinearProbing<>(grafo.getTablaEjes().getSize());

		for (K iterable_element : grafo.getTablaVertcies().keys()) 
		{
			SimpleArrayLIst<K>listaAnadida= new SimpleArrayLIst<>();



			listaAnadida.add(iterable_element);

			Representantes.put(iterable_element, listaAnadida);	 
		}
		for (K iterable_element : grafo.getTablaEjes().keys()) 
		{
			for(int i=0;i<grafo.getTablaEjes().get(iterable_element).size();i++)
				ejes.add(grafo.getTablaEjes().get(iterable_element).get(i));
		}

		ejes=ejes.ordenar(ejes, new Comparator<Edge<K>>() {

			@Override
			public int compare(Edge arg0, Edge arg1) {
				Double helper=arg0.getPeso();
				return helper.compareTo(arg1.getPeso());
			}
		});

	}
	/**
	 * Union-Find
	 */
	public void ConstruirUnionFind()
	{

		for (int i = 0; i < ejes.size(); i++) 
		{
			K llave1=  ejes.get(i).getLlaveorigen();
			K llave2=ejes.get(i).getLlaveDestino();

			if (!findSeEncuentra(llave1, llave2)) 
			{
				Union(llave1, llave2);	
			}
		}
	
		
	}

	/**
	 * retorna el representante
	 * @param llaveBuscada
	 * @return la llave representante
	 */
	public K find(K llaveBuscada )
	{
		K llave=null;
		for (K buscado : Representantes.keys()) 
		{
			for (int i = 0; i < Representantes.get(buscado).size(); i++) 
			{
				if ( Representantes.get(buscado).get(i)==llaveBuscada)
				{
					return buscado;
				}
			}
		}
		return llave;
	}
	public boolean findSeEncuentra(K llaveBuscada,K llaveDestino)
	{

		SimpleArrayLIst<K>arreglo= Representantes.get(llaveDestino);	

		for (int i = 0; i < arreglo.size(); i++) {
			if(arreglo.get(i)==llaveBuscada)
				return true;
		}



		return false;	
	}
	/**
	 * Une los coponentes 
	 */
	public void Union(K llaveRepresenntanteOrigen ,K llaveRepresentanteDestino )

	{

		if(!findSeEncuentra(llaveRepresenntanteOrigen,llaveRepresentanteDestino))
		{SimpleArrayLIst<K> arregloUnionOrigen= Representantes.get(llaveRepresenntanteOrigen);
		SimpleArrayLIst<K> arrigleDestino = Representantes.get(llaveRepresentanteDestino);
		for(int i=0;i<arregloUnionOrigen.size();i++)
		{
			arrigleDestino.add(arregloUnionOrigen.get(i));
		}

		Representantes.put(llaveRepresentanteDestino, arrigleDestino);
		}

	}
}
