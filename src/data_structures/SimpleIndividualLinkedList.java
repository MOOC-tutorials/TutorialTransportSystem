package data_structures;

import java.util.Comparator;
import java.util.Iterator;



public class SimpleIndividualLinkedList<T> implements IList<T>
{
	private Node<T> primero;
	private Node<T> ultimo;
	private Iterator<T> iterador;
	private Integer tamanio;

	public SimpleIndividualLinkedList ()
	{
		tamanio=0;
		primero=null;
		ultimo=primero;

	}
	public static class Node<T>
	{
		T Item ;
		Node<T> siguiente;
		public Node()
		{
			Item=null;
			siguiente=null;
		}	
		public void cambiarElemento(T item)
		{
			Item=item;
		}

		public Node<T> darSiguiente()
		{
			return siguiente;
		}
		public T darElemento()
		{
			return Item;
		}

		public void cambiarSiguiente(Node<T> nuevo)
		{
			siguiente=nuevo;
		}
	}
	public class iterador<T> implements Iterator<T>
	{
		private Node actual;
		public iterador() 
		{
			actual= primero;
		}

		public boolean hasNext()
		{
			return actual!=null;

		}


		public T next()
		{
			Node<T> ahora=actual;
			T item = ahora.darElemento();
			actual=actual.darSiguiente();
			return item;
		}

		public Node<T> darActual()
		{
			return actual;
		}


		public void remove()
		{
			throw new UnsupportedOperationException();	

		}


	};



	public Iterator<T> iterator()
	{
		return new iterador<T>();
	}
	public void reiniciarIterador()
	{
		iterador=iterator();
	}

	public Integer getSize() 
	{
		return tamanio;
	}


	public void add(T item)
	{
		Node<T> nuevo=new Node<T>(); 
		nuevo.cambiarElemento(item);
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

	public void insertarJustoDespues(Node<T> nodo1,Node<T> anadible)
	{
		Node<T> next= nodo1.darSiguiente();
		nodo1.cambiarSiguiente(anadible);
		anadible.cambiarSiguiente(next);

	}
	public void addAtEnd(T item)
	{
		Node<T> nuevo=new Node<T>(); 
		nuevo.cambiarElemento(item);
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


	public void addAtK(Integer indice, T item)
	{
		if(indice==0)
		{
			add(item);
		}
		else if(indice==(getSize()))
		{
			addAtEnd(item);
		}
		else
		{
			Integer contador=0;
			Node<T> nuevo=new Node<T>(); 
			nuevo.cambiarElemento(item);
			while(contador<indice-1 && iterador.hasNext())
			{
				contador++;
				iterador.next();
			}	
			Node<T> actual=((iterador<T>) iterador).darActual();
			nuevo.cambiarSiguiente(actual.darSiguiente());
			actual.cambiarSiguiente(nuevo);
		}
		iterador=iterator();
		tamanio++;
	}


	public T getElement(Integer indice) 
	{

		if(indice==tamanio-1)
		{
			return ultimo.darElemento();
		}
		else if(indice==0)
		{
			if(primero!=null)
			return primero.darElemento();
			else 
			{
				return null;
			}
		}
		else{
			Integer contador=0;
			while(contador<indice && iterador.hasNext())
			{
				contador++;
				iterador.next();
			}		
			T elemento=iterador.next();
			iterador=iterator();
			return elemento;
		}
	}
	public Node<T> getNode(Integer indice) 
	{
		Integer contador=0;
		while(contador<indice && iterador.hasNext())
		{
			contador++;
			iterador.next();
		}		
		Node<T> actual=((iterador<T>) iterador).darActual();
		iterador=iterator();
		return actual;
	}


	public T getCurrentElement() 
	{

		return ((iterador<T>) iterador).darActual().darElemento();
	}

	public void delete(T item) 
	{
		if(primero.darElemento()==item)
		{
			primero=primero.darSiguiente();
		}
		else if(ultimo.darElemento()==item)
		{
			Node<T> actual=((iterador<T>) iterador).darActual();
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
				Node<T> actual=((iterador<T>) iterador).darActual();
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
			Node<T> actual=((iterador<T>) iterador).darActual();
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
			Node<T>actual=((iterador<T>) iterador).darActual();
			actual.cambiarSiguiente(actual.darSiguiente().darSiguiente());
		}
		iterador=iterator();
		tamanio--;
	}
	public Node<T> darPrimero()
	{
		return primero;
	}
	public Node<T> darUltimo()
	{
		return ultimo;
	}
	public boolean next()
	{
		return ((iterador<T>) iterador).darActual().darSiguiente()!=null;

	}
	public Iterator<T> darIterador()
	{
		iterador=iterator();
		return iterador;
	}

	public void cambiarPrimero(Node<T> nodo)
	{
		primero=nodo;
	}
	public void cambiarUltimo(Node<T> nodo)
	{
		ultimo=nodo;
	}
	public void cambiar(IList<T> lista, T ob1, T ob2, int x, int y) 
	{
		ordenadores.cambiar(lista, x, y);

	}


	public SimpleIndividualLinkedList ordenar2(IList<T> lista,Comparator<T> comp) 
	{
		return ordenadores.mergeSort((SimpleIndividualLinkedList)lista,comp);	
	}
	public Node<T> darMitad()
	{
		return getNode((tamanio/2)-1);
	}
	public int darCantidadNodos()
	{
		int contador=0;
		Node<T> actual=primero;
		while(actual!=null)
		{
			contador++;
			actual=actual.darSiguiente();
		}
		return contador;
	}
	public int cantidadDeVecesElemento(T item)
	{
		int contador=0;
		int contador2=0;
		Node<T> actual=primero;	
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


	public boolean contiene(T item)
	{
		Node<T> actual=primero;	
		while(actual!=null)
		{
			if(actual.darElemento()== item)
			{
				return true;
			}

			actual=actual.darSiguiente();
		}

		return false;
	}


}
