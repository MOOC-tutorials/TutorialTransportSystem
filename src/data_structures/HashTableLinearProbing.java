package data_structures;

import java.util.Iterator;



public class HashTableLinearProbing<K,V> 
{
	public EstructuraDeDato<K,V> [] tabla ;
	private int tamanio;
	private int cantidadElementosDentroDeLaTabla;
	@SuppressWarnings("unchecked")
	public HashTableLinearProbing( int  tamanioArreglo ) 
	{


		tabla=new EstructuraDeDato[tamanioArreglo];
		tamanio=tamanioArreglo;
		cantidadElementosDentroDeLaTabla=0;
	}



	public void put(K key, V item) 
	{
		EstructuraDeDato<K, V> nuevo= new EstructuraDeDato<>(key,item);
		if(key == null)		throw new NullPointerException("La llave no debe ser nula"); 
		

		int i;
		for (i = darIndice(key); tabla[i] != null; i = (i + 1) % tamanio) {
			if (tabla[i].key.equals(key)) 
			{
				tabla[i] = nuevo;
				return;
			}
		}
		tabla[i]=nuevo;
		cantidadElementosDentroDeLaTabla++;
		if(factorDeCarga())		rehash();
	}



	private void rehash(){

		HashTableLinearProbing<K, V> temp;

		if(cantidadElementosDentroDeLaTabla > tamanio/2)
			temp = new HashTableLinearProbing<>(tamanio*2);
			else if(cantidadElementosDentroDeLaTabla < tamanio/4)
				temp = new HashTableLinearProbing<>(tamanio/2);
				else
					temp = new HashTableLinearProbing<>(tamanio);

					for (int i = 0; i < tamanio; i++) {
						if (tabla[i] != null) {
							temp.put(tabla[i].key, tabla[i].value);
						}
					}
					tamanio = temp.tamanio;
					tabla = temp.tabla;
	}


	private boolean factorDeCarga() {

		return (cantidadElementosDentroDeLaTabla/tamanio)>=0.5;
	}



	public V get(K key) {
		for (int i = darIndice(key); tabla[i] != null; i = (i + 1) % tamanio) 
		{
			if (tabla[i].key.equals(key))
				return tabla[i].value;


		}

		return null;


	}


	public void delete2(K key)
	{
		int i = darIndice(key);

		while (!key.equals(tabla[i].key)) {
			i = (i + 1) % tamanio;
		}


		tabla[i] = null;


		--cantidadElementosDentroDeLaTabla; 


		rehash();
	}

	public V delete(K key) {

		return null;
	}
	public int getSize() {
		return tamanio;
	}
	/**
	 * Da cuantos pares <Llave,Valor> ahi dentro de la tabla
	 * @return cuantos elementos ahi dentro de la tabla
	 */
	public int darCapacidad()
	{
		return cantidadElementosDentroDeLaTabla;
	}
	public boolean isEmpty() {

		return tamanio!=0;
	}
	//funcion hash
	public int darIndice(K key) 
	{
		
		int  key2=(key.hashCode()&0x7fffffff)%tamanio;
		
		return key2;
	}


	public Iterable<K> keys() {

		  Cola<K> queue = new Cola<>();
	        for (int i = 0; i < tamanio; i++)
	        	if (tabla[i]!= null) 
	        		queue.enqueue(tabla[i].key);
	        		
	        return queue;
	        	
	        
	        
	        		
	        	
	        
	}
	@SuppressWarnings("hiding")
	public class  EstructuraDeDato<K,V>{
		public K key;
		public V value;

		EstructuraDeDato(){
			key = null;
			value = null;
		}
		EstructuraDeDato(K k, V v){
			key = k;
			value = v;
		}
	}
	public Iterator<K> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
