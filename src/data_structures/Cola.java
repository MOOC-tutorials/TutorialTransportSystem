package data_structures;

import java.util.Iterator;





public class Cola<T> implements Iterable<T>
{
	private SimpleIndividualLinkedList<T> lista;
	public Cola()
	{
		lista=new SimpleIndividualLinkedList<>();
	}
	public void enqueue(T paradas)
	{
		lista.add(paradas);
	}
	public T dequeue()
	{
		Integer tamanio=lista.getSize();
		T ultimoElemento=lista.getElement(tamanio-1);
		lista.deleteAtK(tamanio-1);
		return ultimoElemento;
	}
	public boolean estaVacia()
	{
		return lista.getSize()==0;
	}
	public Integer size()
	{
		return lista.getSize();
	}
public T peek()
{
	return lista.getElement(0);
}
public boolean conatins(T item)
{
	return lista.contiene(item);
}

@Override
public Iterator<T> iterator() {
	
	return lista.darIterador();
}

}
