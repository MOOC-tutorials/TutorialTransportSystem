package data_structures;

import java.util.Comparator;
import java.util.List;

import data_structures.SimpleIndividualLinkedList.Node;
import algoritmos.Ordenadores;

/**
 * @author Miguel Muñoz
 * @param <T>
 *
 */
public interface IList<T> extends Iterable <T> 
{
	Ordenadores ordenadores=new Ordenadores();
	void add(T item);
	void addAtEnd(T item);
	void addAtK(Integer indice ,T item);
	Node<T> getNode(Integer indice) ;
	T getElement(Integer indice);
	T getCurrentElement();
	Integer getSize();
	Node <T> darPrimero();
	void delete(T item);
	void deleteAtK(Integer indice );
	void cambiar(IList<T> lista, T ob1, T ob2, int x, int y);
	SimpleIndividualLinkedList ordenar2(IList<T> lista,Comparator<T>comp);
}
