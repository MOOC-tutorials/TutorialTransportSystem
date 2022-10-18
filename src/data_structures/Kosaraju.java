package data_structures;



public class Kosaraju<K,V>
{
	private DirectedGraph<K, V> grafo;
	private SimpleIndividualLinkedList<Stack<K>> lista ;
	private HashTableLinearProbing<K, V> marcados= new HashTableLinearProbing<>(100);
	private HashTableLinearProbing<K, V> marcados2= new HashTableLinearProbing<>(100);
	private Stack<K> pila;
	public Kosaraju(DirectedGraph<K, V> pGrafo, K llaveInicial)
	{
		lista= new SimpleIndividualLinkedList<Stack<K>>();
		pila= new Stack<K>();
		grafo= pGrafo;
		dfsTrasversal(llaveInicial,llaveInicial);
		DirectedGraph<K, V>  transpuesto=transponerGrafo();
		sacarDeLaPila(transpuesto);
		verificar(llaveInicial);
	}
	public void verificar(K llave)
	{
		Cycle<K, V> ciclo=new Cycle<K,V>(grafo, llave,null);
		if(!ciclo.tieneCiclo())
			lista= new SimpleIndividualLinkedList<Stack<K>>();
	}
	public void dfsTrasversal(K llave,K llaveInicial)
	{		
		if(marcados.get(llave)==null)
		{
			V temporal=grafo.getTablaVertcies().get(llave);
			SimpleArrayLIst<Edge<K>> lista2=grafo.darVecinosVertice(llave);
			if(lista2.isEmpty())
			{
				Stack<K> individual= new Stack<>();
				individual.push(llave);
				lista.add(individual);
			}

			marcados.put(llave, temporal);
			for(int i=0;i<lista2.size();i++)
			{
				Edge<K> eje=lista2.get(i);
				K llaveFinal=eje.getLlaveDestino();
				dfsTrasversal(llaveFinal,llaveInicial);		
			}	
			pila.push(llave);
		}
		if(llave.equals(llaveInicial))
		{
			K usar=noEstaMarcado();
			if(usar!=null)
			{
				dfsTrasversal(usar, usar);
			}
		}
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void sacarDeLaPila(DirectedGraph<K, V> pGrafoTranspuesto)
	{
		while(pila.size()>0)
		{
			lista.reiniciarIterador();
			K llave=pila.pop();
			if(marcados.get(llave)!=null)
			{
				SimpleArrayLIst<Edge<K>> lista2=pGrafoTranspuesto.darVecinosVertice(llave);
				int cantidad=lista2.size();
			
				Cycle<K, V> ciclo=new Cycle<K,V>(pGrafoTranspuesto, llave,marcados2);
				marcados2=ciclo.darMarcados();
				marcados.delete2(llave);
				
				while(cantidad>0)
				{		
					if(pila.size()>0)
					marcados.delete2(pila.pop());
					cantidad--;
				}
				SimpleIndividualLinkedList<Stack<K>> anadibles=ciclo.darCiclos();
				for(Stack<K> laPila:anadibles)
				{
					lista.add(laPila);
				}

			}
		}
	}
	public K noEstaMarcado()
	{
		HashTableLinearProbing<K, V> temporal= grafo.getTablaVertcies();
		Iterable<K> nueva=(temporal.keys());
		if( ((Cola)nueva).size()>0)
			for(K llave: nueva)
			{
				if(marcados.get(llave)==null)
					return llave;
			}
		return null;
	}

	public DirectedGraph<K,V> transponerGrafo()
	{
		DirectedGraph<K,V> retornable= new DirectedGraph<>();
		HashTableLinearProbing<K, V> vertices= grafo.getTablaVertcies();
		HashTableLinearProbing<K, SimpleArrayLIst<Edge<K>>> ejes= grafo.getTablaEjes();
		Iterable<K> nueva=(vertices.keys());
		Iterable<K> nueva2=(ejes.keys());
		if( ((Cola)nueva).size()>0)
			for(K llave: nueva)
			{
				V anadible=vertices.get(llave);
				retornable.addVertex(llave, anadible);
			}

		if(((Cola)nueva2).size()>0)
			for(K llave: nueva2)
			{
				SimpleArrayLIst<Edge<K>> listaEjes=ejes.get(llave);
				for(int i=0;i< listaEjes.size();i++)
				{
					Edge<K> actual=listaEjes.get(i);
					K inicial=actual.getLlaveDestino();	
					double peso= actual.getPeso();
					retornable.addEdge(inicial, llave, peso);
				}

			}

		return retornable;
	}
	public boolean tieneCiclo( )
	{
		return lista.getSize()>0;
	}
	public SimpleIndividualLinkedList<Stack<K>> darCiclos()
	{
		return lista;
	}

}
