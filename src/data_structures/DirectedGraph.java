package data_structures;



public class DirectedGraph<K,V> implements IGraph<K, V> {


	private HashTableLinearProbing<K,  SimpleArrayLIst<Edge<K>>> tablaEjes;
	private HashTableLinearProbing<K, V> tablaVertcies;

	public DirectedGraph() {

		tablaEjes= new HashTableLinearProbing<>(10000);
		tablaVertcies = new HashTableLinearProbing<>(1000);

	}

	@Override
	public void addVertex(K llave, V informacion) {
		tablaVertcies.put(llave, informacion);

	}
	public void addlistaDeEjes(K llave, SimpleArrayLIst<Edge<K>> lista)
	{
		tablaEjes.put(llave, lista);
	}
	@Override
	public void addEdge(K llaveInicio, K llaveFinal, double peso) {
		Edge<K> anadido=new Edge<>(peso, llaveInicio,llaveFinal);
		SimpleArrayLIst< Edge<K>> arreglo= tablaEjes.get(llaveInicio);
		if (arreglo==null)
		{
			arreglo= new SimpleArrayLIst<>();
			arreglo.add(anadido);
			tablaEjes.put(llaveInicio, arreglo);
		}else
		{
			arreglo.add(anadido);
			tablaEjes.put(llaveInicio, arreglo);
		}


	}
	public int darCantidadDeVertices()
	{
		return tablaVertcies.darCapacidad();
	}
	public int darCantidadEjes()
	{ int capacidad=0;
		for (K iterable_element : tablaEjes.keys()) 
		{
			capacidad+=tablaEjes.get(iterable_element).size();
		} 
		return capacidad;
	}
 
	public HashTableLinearProbing<K, SimpleArrayLIst<Edge<K>>> getTablaEjes() {
		return tablaEjes;
	}

	public HashTableLinearProbing<K, V> getTablaVertcies() {
		return tablaVertcies;
	}
/**
 * Busco aquellos vertices vecinos del vertice  k (el grado)
 * @param individuo
 * @return
 */
	public SimpleArrayLIst<Edge<K>> darVecinosVertice( K individuo)
	{
		SimpleArrayLIst<Edge<K>>aux =new SimpleArrayLIst<>();
		SimpleArrayLIst<Edge<K>> t=tablaEjes.get(individuo);
		if(t!=null)
		for (int i = 0; i <tablaEjes.get(individuo).size() ; i++) {
			aux.add(tablaEjes.get(individuo).get(i));
		}
		
		
		return aux;
	}
	



}