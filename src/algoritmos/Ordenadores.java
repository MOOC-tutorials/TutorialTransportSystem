package algoritmos;
import java.util.Comparator;

import data_structures.IList;
import data_structures.SimpleArrayLIst;
import data_structures.SimpleIndividualLinkedList;
import data_structures.SimpleIndividualLinkedList.Node;
//	Ordena por Merge las listas enlazadas y shellsort a los arraylist
public class Ordenadores
{
	public static<T> void cambiar(IList<T> lista, Integer x, Integer y)
	{
		Node<T> nodo1=lista.getNode(x);
		Node<T> nodo2=lista.getNode(y);
		T ob1=nodo1.darElemento();
		T ob2=nodo2.darElemento();
		nodo1.cambiarElemento(ob2);
		nodo2.cambiarElemento(ob1);
	}
	//	public static<T extends Comparable<T>> void quickSort(IList<T> lista, Integer posi, Integer posf )
	//	{
	//		if((posf-posi)<=0)return;
	//
	//		int particion=partir(lista, posi, posf);
	//		quickSort(lista,posi,particion-1);
	//		quickSort(lista,particion+1,posf);
	//
	//	}
	//	public static<T extends Comparable<T>>int partir(IList<T> lista, Integer posi, Integer posf)
	//	{
	//
	//		Integer pivote=posi;
	//		while(posi<posf)
	//		{
	//			if(lista.getElement(posi).compareTo(lista.getElement(posf))>0)
	//			{
	//				cambiar(lista, posi, posf);
	//				if(pivote==posi)
	//				{
	//					pivote=posf;
	//					posi++;
	//				}
	//				else 
	//				{
	//					pivote=posi;
	//					posf--;
	//				}
	//			}
	//			else
	//			{
	//				if(pivote==posi)
	//					posf--;
	//				else 
	//				{
	//					posi++;
	//				}
	//			}
	//		}
	//
	//		return pivote;
	//	}
	public static <T> SimpleIndividualLinkedList<T> mergeSort(SimpleIndividualLinkedList<T> lista, Comparator<T> comp) 
	{
		if(lista.darPrimero()==null||lista.darPrimero().darSiguiente()==null)
			return lista;

		SimpleIndividualLinkedList<T> izquierda=new SimpleIndividualLinkedList<T>();
		SimpleIndividualLinkedList<T> derecha=new SimpleIndividualLinkedList<T>();

		Node<T>mitad=lista.darMitad();
		Node<T> actual1=lista.darPrimero();
		Node<T> actual2=mitad.darSiguiente();
		while(actual1!=mitad.darSiguiente())
		{
			izquierda.addAtEnd(actual1.darElemento());
			actual1=actual1.darSiguiente();
		}
		while(actual2!=null)
		{
			derecha.addAtEnd(actual2.darElemento());
			actual2=actual2.darSiguiente();
		}
		return merge(mergeSort(izquierda, comp), mergeSort(derecha, comp), comp);


	}

	public  static<T> SimpleIndividualLinkedList<T> merge(SimpleIndividualLinkedList<T> izquierda, SimpleIndividualLinkedList<T> derecha, Comparator<T> comp) 
	{
		SimpleIndividualLinkedList<T> retornable= new SimpleIndividualLinkedList<T>();
		Node<T> nI=izquierda.darPrimero();
		Node<T> nD=derecha.darPrimero();
		while(nI!=null&&nD!=null)
		{		
			T pI=nI.darElemento();
			T pD=nD.darElemento();
			if(comp.compare(pI, pD)<=0)
			{
				retornable.addAtEnd(pI);
				nI=nI.darSiguiente();
			}
			else
			{
				retornable.addAtEnd(pD);
				nD=nD.darSiguiente();
			}
		}
		if(nI==null&&nD!=null)
		{
			while(nD!=null)
			{
				retornable.addAtEnd(nD.darElemento());
				nD=nD.darSiguiente();
			}
		}
		else if(nI!=null&&nD==null)
		{
			while(nI!=null)
			{
				retornable.addAtEnd(nI.darElemento());
				nI=nI.darSiguiente();
			}
		}
		return retornable;
	}
	/**
	 * 
	 * @param lista ordenar es de tipo arrayList
	 * @return la lista ordenada
	 */
	public static  <T>SimpleArrayLIst<T> shellSort(SimpleArrayLIst<T>list,Comparator<T> comp)
	{
				SimpleArrayLIst<T> listaOrdenada=list ;
				int intervalo = listaOrdenada.size()/2;
				while(intervalo >= 1){
					for(int i = 0; i < intervalo; i++){
						for(int j = intervalo+i; j < listaOrdenada.size(); j+=intervalo){
							T aux = listaOrdenada.get(j);
							int k = j - intervalo;
							while(k >= 0 && comp.compare(listaOrdenada.get(k), aux) > 0){
								listaOrdenada.set(k +intervalo, listaOrdenada.get(k));
								k-=intervalo;
							}
							listaOrdenada.set(k + intervalo, aux);
						}
					}
					intervalo /= 2;
				}	
				return listaOrdenada;
			}

	
//	public static<T> SimpleArrayLIst<T> quicksort(SimpleArrayLIst<T> input,Comparator<T> comp){
//
//		if(input.size() <= 1){
//			return input;
//		}
//
//		int middle = (int) Math.ceil((double)input.size() / 2);
//		T pivot = input.get(middle);
//
//		SimpleArrayLIst<T> less = new SimpleArrayLIst<>();
//		SimpleArrayLIst<T> greater = new SimpleArrayLIst<>();
//
//		for (int i = 0; i < input.size(); i++) {
//			T data=input.get(i);
//			if(comp.compare(data, pivot)<=0)
//			{
//
//				if(i == middle)
//				{
//					continue;
//				}
//				less.add(input.get(i));
//			}
//			else{
//				greater.add(input.get(i));
//			}
//		}
//
//		return concatenate(quicksort(less, comp), pivot, quicksort(greater,comp));
//	}
//
//
//
//
//private static <T> SimpleArrayLIst<T> concatenate(SimpleArrayLIst<T> simpleArrayLIst, T pivot, SimpleArrayLIst<T> simpleArrayLIst2){
//
//	SimpleArrayLIst<T> list = new SimpleArrayLIst<T>();
//
//	for (int i = 0; i < simpleArrayLIst.size(); i++) {
//		list.add(simpleArrayLIst.get(i));
//	}
//
//	list.add(pivot);
//
//	for (int i = 0; i < simpleArrayLIst2.size(); i++) {
//		list.add(simpleArrayLIst2.get(i));
//	}
//
//	return list;
//}
}


