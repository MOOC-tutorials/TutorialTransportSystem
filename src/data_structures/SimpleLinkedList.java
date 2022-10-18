package data_structures;


import java.util.Iterator;



public class SimpleLinkedList<K,V> implements Iterable<K>
{
	private Node<K,V> primero;
	private Node<K,V> ultimo;
	private Iterator<K> iterador;
	private Integer tamanio;

	public SimpleLinkedList ()
	{
		tamanio=0;
		primero=null;
		ultimo=primero;

	}
	public static class Node<K,V>
	{
		K key;
		V Item ;
		Node<K,V> siguiente;
		public Node()
		{
			Item=null;
			siguiente=null;
		}	
		public void cambiarElemento(V item)
		{
			Item=item;
		}

		public Node<K,V> darSiguiente()
		{
			return siguiente;
		}
		public V darElemento()
		{
			return Item;
		}
		public K darLlave()
		{
			return key;
		}
		public void cambiarLlave(K llave)
		{
			key=llave;
		}

		public void cambiarSiguiente(Node<K,V> nuevo)
		{
			siguiente=nuevo;
		}
	}
	public class iterador<K> implements Iterator<K>
	{
		private Node<K, V> actual;
		public iterador() 
		{
			actual= (Node<K, V>) primero;
		}

		public boolean hasNext()
		{
			return actual!=null;

		}


		public K next()
		{
			Node<K,V> ahora=actual;
			K llave = ahora.darLlave();
			actual=actual.darSiguiente();
			return llave;
		}

		public Node<K,V> darActual()
		{
			return actual;
		}


		public void remove()
		{
			throw new UnsupportedOperationException();	

		}


	};



	public Iterator<K> iterator()
	{
		return new iterador<K>();
	}

	public Integer getSize() 
	{
		return tamanio;
	}


	public void add(K key,V item)
	{
		Node<K,V> nuevo=new Node<K,V>(); 
		nuevo.cambiarElemento(item);
		nuevo.cambiarLlave(key);
		if(primero==null)
		{
			primero=nuevo;
			ultimo=nuevo;
			iterador=iterator();
		}
		else
		{
			nuevo.cambiarSiguiente(primero);
			primero=nuevo;
			iterador=iterator();
		}	
		tamanio++;
	}


	public void addAtEnd(K key,V item)
	{
		Node<K,V> nuevo=new Node<K,V>(); 
		nuevo.cambiarElemento(item);
		nuevo.cambiarLlave(key);
		if(ultimo==null)
		{
			ultimo=nuevo;
			primero=nuevo;
			iterador=iterator();
		}
		else
		{
			ultimo.cambiarSiguiente(nuevo);
			ultimo=nuevo;
		}	
		tamanio++;
	}


	public void addAtK(Integer indice, V item, K key)
	{
		if(indice==0)
		{
			add(key, item);
		}
		else if(indice==(getSize()))
		{
			addAtEnd(key,item);
		}
		else
		{
			Integer contador=0;
			Node<K,V> nuevo=new Node<K,V>(); 
			nuevo.cambiarElemento(item);
			nuevo.cambiarLlave(key);
			while(contador<indice-1 && iterador.hasNext())
			{
				contador++;
				iterador.next();
			}	
			Node<K,V> actual=((iterador<K>) iterador).darActual();
			nuevo.cambiarSiguiente(actual.darSiguiente());
			actual.cambiarSiguiente(nuevo);
		}
		iterador=iterator();
		tamanio++;
	}


	public V getElement(Integer indice) 
	{

		if(indice==tamanio-1)
		{
			return ultimo.darElemento();
		}
		else{
			Integer contador=0;
			while(contador<indice && iterador.hasNext())
			{
				contador++;
				iterador.next();
			}		
		
			V elemento=((iterador<K>) iterador).darActual().darElemento();
			iterador=iterator();
			return elemento;
		}
	}
	public V getElementByKey(K key) 
	{	
		if(primero.darLlave().equals(key))
		{
			return primero.darElemento();
		}
		else if(ultimo.darLlave().equals(key))
		{
			return ultimo.darElemento();
		}
		else
		{
			Node<K,V> actual=primero;
			K temporal=actual.darLlave();
			while(temporal!=key && iterador.hasNext())
			{
				actual=actual.darSiguiente();
				if(actual!=null)
				temporal=actual.darLlave();
				
				iterador.next();
			}	
			iterador=iterator();
			if(actual!=null)
			return actual.darElemento();
			else return null;
		}
	}
	public Node<K,V> getNode(Integer indice) 
	{
		Integer contador=0;
		while(contador<indice && iterador.hasNext())
		{
			contador++;
			iterador.next();
		}		
		Node<K,V> actual=((iterador<K>) iterador).darActual();
		iterador=iterator();
		return actual;
	}


	public V getCurrentElement() 
	{

		return ((iterador<V>) iterador).darActual().darElemento();
	}

	public void delete(V item) 
	{
		if(primero.darElemento()==item)
		{
			primero=primero.darSiguiente();
		}
		else if(ultimo.darElemento()==item)
		{
			Node<K,V> actual=((iterador<K>) iterador).darActual();
			while(actual.darSiguiente()!=ultimo)
				iterador.next();
			actual.cambiarSiguiente(null);
			ultimo=actual;
		}
		else
		{
			Boolean ya=false;
			while(iterador.hasNext()&& !ya)
			{
				Node<K,V> actual=((iterador<K>) iterador).darActual();
				if(actual.darSiguiente().darElemento()==item)
				{
					actual.cambiarSiguiente(actual.darSiguiente().darSiguiente());
					ya=true;
				}
				iterador.next();
			}
		}
		iterador=iterator();
		tamanio--;
	}


	public void deleteAtK(Integer indice) 
	{
		if(indice==0)
		{
			primero=primero.darSiguiente();
		}
		else if(indice==getSize()-1)
		{
			int contador=0;
			Node<K,V> actual=((iterador<K>) iterador).darActual();
			while(contador<getSize()-2)
			{
				actual=actual.darSiguiente();
				contador++;
			}
			actual.cambiarSiguiente(null);
			ultimo=actual;
		}
		else
		{
			Integer contador=0;
			while(contador<indice-1 && iterator().hasNext())
			{
				contador++;
				iterador.next();
			}		
			Node<K,V>actual=((iterador<K>) iterador).darActual();
			actual.cambiarSiguiente(actual.darSiguiente().darSiguiente());
		}
		iterador=iterator();
		tamanio--;
	}
	public V deleteAtKey(K key)
	{
		V retornable=null;
		if(primero.darLlave().equals(key))
		{
			retornable=primero.darElemento();
			primero=primero.darSiguiente();
		}
		else if(ultimo.darLlave().equals(key))
		{
			retornable=ultimo.darElemento();
			Node<K,V> actual=((iterador<K>) iterador).darActual();
			while(actual.darSiguiente()!=ultimo)
				iterador.next();
			actual.cambiarSiguiente(null);
			ultimo=actual;
		}
		else
		{
			Boolean ya=false;
			while(iterador.hasNext()&& !ya)
			{
				Node<K,V> actual=((iterador<K>) iterador).darActual();
				if(actual.darSiguiente().darLlave().equals(key))
				{
					retornable=actual.darSiguiente().darElemento();
					actual.cambiarSiguiente(actual.darSiguiente().darSiguiente());
					ya=true;
				}
				iterador.next();
			}
		}
		iterador=iterator();
		tamanio--;
		return retornable;
	}


	public Node<K,V> darPrimero()
	{
		return primero;
	}
	public Node<K,V> darUltimo()
	{
		return ultimo;
	}
	public boolean next()
	{
		return ((iterador<V>) iterador).darActual().darSiguiente()!=null;

	}
	public Iterator<K> darIterador()
	{
		return iterador;
	}

	public void cambiarPrimero(Node<K,V> nodo)
	{
		primero=nodo;
	}

	public Node<K,V> darMitad()
	{
		return getNode((tamanio/2)-1);
	}
	public int darCantidadNodos()
	{
		int contador=0;
		Node<K,V> actual=primero;
		while(actual!=null)
		{
			contador++;
			actual=actual.darSiguiente();
		}
		return contador;
	}
	public int cantidadDeVecesElemento(V item)
	{
		int contador=0;
		int contador2=0;
		Node<K,V> actual=primero;	
		while(actual!=null)
		{
			if(actual.darElemento()== item)
			{
				contador++;
			}
			contador2++;
			actual=actual.darSiguiente();
		}

		return contador;
	}
	public boolean hayElemento(V value)
	{
		Node<K,V> actual=primero;	
		while(actual!=null)
		{
			if(actual.darElemento()== value)
			{
				return true;
			}
			actual=actual.darSiguiente();
		}
		return false;
	}





}
