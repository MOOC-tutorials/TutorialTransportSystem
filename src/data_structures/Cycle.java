package data_structures;




public class Cycle <K,V>
{
	private DirectedGraph<K, V> grafo;
	private SimpleIndividualLinkedList<Stack<K>> lista ;
	private HashTableLinearProbing<K, V> marcados;
	private Stack<K> pila;
	public Cycle(DirectedGraph<K, V> pGrafo, K llaveInicial,HashTableLinearProbing<K, V> pMarcados)
	{
		if(pMarcados==null)
			marcados= new HashTableLinearProbing<>(100);
			else
				marcados=pMarcados;
		lista= new SimpleIndividualLinkedList<Stack<K>>();
		pila= new Stack<K>();
		grafo=pGrafo;
		dfs(llaveInicial);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void dfs(K llave)
	{
		lista.reiniciarIterador();
		if(marcados.get(llave)==null)
		{
			V temporal=grafo.getTablaVertcies().get(llave);
			SimpleArrayLIst<Edge<K>> lista2=grafo.darVecinosVertice(llave);
			marcados.put(llave, temporal);
			pila.push(llave);
			for(int i=0;i<lista2.size();i++)
			{
				Edge eje=lista2.get(i);
				K llaveFinal=(K) eje.getLlaveDestino();
				dfs(llaveFinal);		
			}	

			pila.pop();
		}
		else
		{

			Stack<K> anadible=new Stack<K>();

			for(K llaves: pila)
			{
				anadible.push(llaves);

				if(llaves.equals(llave))
				{
					if(pila.size()>1)
						lista.addAtEnd(anadible);	

					break;
				}
			}
		}


	}

	public boolean tieneCiclo( )
	{
		return lista.getSize()>0;
	}
	public SimpleIndividualLinkedList<Stack<K>> darCiclos()
	{
		return lista;
	}
	public HashTableLinearProbing<K, V> darMarcados()
	{
		return marcados;
	}

	


}