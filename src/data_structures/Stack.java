package data_structures;

import java.util.Iterator;

public class Stack<T> implements Iterable<T>
{

	public SimpleIndividualLinkedList<T> lista;
	public Stack()
	{
		lista=new SimpleIndividualLinkedList<>();
	}

	public void push(T item)
	{
		lista.add(item);	
	}
	public T pop()
	{
		T elemento=lista.getElement(0);
		lista.deleteAtK(0);
		return elemento;
	}
	public Integer size()
	{
		return lista.getSize();
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return lista.darIterador();
	}
	public T peek()
	{
		T elemento=lista.getElement(0);
		return elemento;
	}
}
