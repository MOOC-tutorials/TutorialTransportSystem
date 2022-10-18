package data_structures;

import java.util.Comparator;
import java.util.Iterator;

import data_structures.SimpleIndividualLinkedList.Node;
public class RedBlackBST<K extends Comparable<K>,V> implements Iterable<K>
{
	private NodoRojoNegro<K, V> primero;
	public static final boolean ROJO= true;
	public static final boolean NEGRO= false;
	public SimpleIndividualLinkedList<K> keys= new SimpleIndividualLinkedList<K>();
	public RedBlackBST()
	{
		primero=null;
	}
	public static class NodoRojoNegro<K,V>
	{

		private NodoRojoNegro<K, V> hijoDerecho;
		private NodoRojoNegro<K, V> hijoIzquierdo;
		private boolean color;
		private K key;
		private V item;
		private int cantidadN;

		public NodoRojoNegro(K key, V item,boolean color, int cantidad ) 
		{

			this.color = color;
			this.key = key;
			this.item = item;
			cantidadN=cantidad;
		}
		public NodoRojoNegro<K, V> darHijoDerecho()
		{
			return hijoDerecho;
		}
		public NodoRojoNegro<K, V> darHijoIzquierdo()
		{
			return hijoIzquierdo;
		}
		public boolean darColor()
		{
			return color;
		}
		public K darLlave()
		{
			return key;
		}
		public V darItem()
		{
			return item;
		}
		public int darCantidad()
		{
			return cantidadN;
		}
		public void cambiarHijoDerecho(NodoRojoNegro hijo)
		{
			hijoDerecho=hijo;
		}
		public void cambiarHijoIzquierdo(NodoRojoNegro hijo)
		{
			hijoIzquierdo=hijo;
		}
		public void cambiarColor(boolean pColor)
		{
			color=pColor;
		}
		public void cambiarLlave(K llave)
		{
			key=llave;
		}
		public void cambiarItem(V pItem)
		{
			item = pItem;
		}
		public void cambiarCantidad(int cant)
		{
			cantidadN=cant;
		}

	}
	public boolean esRojo(NodoRojoNegro<K, V> nodo)
	{
		if(nodo!=null)
			return nodo.darColor();
		else
			return false;
	}
	@SuppressWarnings("unchecked")
	public void put(K key, V item)
	{
		primero=putInTree(primero, key, item);
		primero.cambiarColor(NEGRO);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private NodoRojoNegro putInTree(NodoRojoNegro<K, V> nodo,K key, V item)
	{
		if(nodo==null)
		{
			return new NodoRojoNegro(key, item,ROJO,1);
		}
		else
		{
			K temporal=nodo.darLlave();
			//En este caso la llave es mayor y se inserta a la derecha.
			if(temporal.compareTo(key)<0)
			{
				nodo.cambiarHijoDerecho(putInTree(nodo.darHijoDerecho(), key, item));
			}
			//En este caso la llave es menor y se inserta a la izquierda.
			else if(temporal.compareTo(key)>0)
			{
				nodo.cambiarHijoIzquierdo(putInTree(nodo.darHijoIzquierdo(), key, item));
			}
			//En este caso simplemente cambia el item relacionado con la llave.
			else 
			{
				nodo.cambiarItem(item);
			}
			if(esRojo(nodo.darHijoDerecho())&&!esRojo(nodo.darHijoIzquierdo()))
			{
				nodo=corregirHayRojoALaDerecha(nodo);
			}
			if(esRojo(nodo.darHijoIzquierdo())&&esRojo(nodo.darHijoIzquierdo().darHijoIzquierdo()))
			{
				nodo=corregirHayDosRojosConsecutivos(nodo);
			}
			VerificarYCorregirLosDosSonRojos(nodo);
			int cantidad=1+sizeNode(nodo.darHijoIzquierdo())+sizeNode(nodo.darHijoDerecho());
			nodo.cambiarCantidad(cantidad);
			return nodo;
		}

	}
	public NodoRojoNegro<K, V> corregirHayRojoALaDerecha(NodoRojoNegro<K, V> nodo)
	{
		NodoRojoNegro<K, V> derecho=nodo.darHijoDerecho();
		nodo.cambiarHijoDerecho(derecho.darHijoIzquierdo());
		derecho.cambiarHijoIzquierdo(nodo);
		derecho.cambiarColor(nodo.darColor());
		nodo.cambiarColor(ROJO);
		int cantidad=1+sizeNode(nodo.darHijoIzquierdo())+sizeNode(nodo.darHijoDerecho());
		nodo.cambiarCantidad(cantidad);
		return derecho;

	}
	public NodoRojoNegro<K, V> corregirHayDosRojosConsecutivos(NodoRojoNegro<K, V> nodo)
	{
		NodoRojoNegro<K, V> izquierdo=nodo.darHijoIzquierdo();
		nodo.cambiarHijoIzquierdo(izquierdo.darHijoDerecho());
		izquierdo.cambiarHijoDerecho(nodo);
		izquierdo.cambiarColor(nodo.darColor());
		nodo.cambiarColor(ROJO);
		izquierdo.cambiarCantidad(nodo.darCantidad());
		int cantidad=1+sizeNode(nodo.darHijoIzquierdo())+sizeNode(nodo.darHijoDerecho());
		nodo.cambiarCantidad(cantidad);
		return izquierdo;
	}
	public void VerificarYCorregirLosDosSonRojos(NodoRojoNegro<K, V> nodo)
	{
		NodoRojoNegro<K, V> izquierdo=nodo.darHijoIzquierdo();
		NodoRojoNegro<K, V> derecho=nodo.darHijoDerecho();
		if(esRojo(derecho)&&esRojo(izquierdo))
		{
			nodo.cambiarColor(ROJO);
			izquierdo.cambiarColor(NEGRO);
			derecho.cambiarColor(NEGRO);

		}
	}

	public V get(K key)
	{
		if(primero==null)
			return null;
		else
			return getInTree(key, primero);
	}
	private V getInTree(K key, NodoRojoNegro<K, V> nodo)
	{
		K temporal=nodo.darLlave();
		if(temporal.compareTo(key)>0)
		{
			if(nodo.darHijoIzquierdo()!=null)
				return getInTree(key,nodo.darHijoIzquierdo());
			else return null;
		}
		else if(temporal.compareTo(key)<0)
		{
			if(nodo.darHijoDerecho()!=null)
				return getInTree(key,nodo.darHijoDerecho());
			else return null;
		}
		else
		{
			return nodo.darItem();
		}
	}
	public int size()
	{
		return sizeNode(primero);
	}
	private int sizeNode(NodoRojoNegro<K, V> nodo)
	{
		if(nodo==null)
			return 0;
		else return nodo.darCantidad();
	}
	public NodoRojoNegro<K, V> darPrimero()
	{
		return primero;
	}
	@Override
	public Iterator<K> iterator() 
	{
		keys.reiniciarIterador();
		llenarLlaves(primero);
		keys=keys.ordenar2(keys, new Comparator<K>(){

			@Override
			public int compare(K arg0, K arg1) {
				return arg0.compareTo(arg1);
			}

		});
		return keys.darIterador();
	}
	public void llenarLlaves( NodoRojoNegro<K, V> nodo)
	{
		if(nodo!=null)
		{
			keys.addAtEnd(nodo.darLlave());
			llenarLlaves(nodo.darHijoIzquierdo());
			llenarLlaves(nodo.darHijoDerecho());
		}
	}
	public NodoRojoNegro<K, V> darHojaMasIzquierdaPorNodo(NodoRojoNegro<K, V> nodo)
	{
		if(nodo!=null)
		{
			if(nodo.darHijoIzquierdo()!=null)
				return darHojaMasIzquierdaPorNodo(nodo.darHijoIzquierdo());
			else if(nodo.darHijoDerecho()!=null)
				return darHojaMasIzquierdaPorNodo(nodo.darHijoDerecho());
		}
		return nodo;
	}
	public NodoRojoNegro<K, V> darHojaMasDerechaPorNodo(NodoRojoNegro<K, V> nodo)
	{
		if(nodo!=null)
		{
			if(nodo.darHijoDerecho()!=null)
				return darHojaMasDerechaPorNodo(nodo.darHijoDerecho());
			else if(nodo.darHijoIzquierdo()!=null)
				return darHojaMasDerechaPorNodo(nodo.darHijoIzquierdo());
		}
		return nodo;
	}
	public void delete(K key)
	{
		deleteInTree(primero,key);
		if(primero!=null)
			primero.cambiarColor(NEGRO);
	}
	public void deleteInTree(NodoRojoNegro<K, V> nodo, K key)
	{
		if(nodo!=null)
		{
			NodoRojoNegro<K, V>izquierdo=nodo.darHijoIzquierdo();
			NodoRojoNegro<K, V> derecho= nodo.darHijoDerecho();
			K llave=nodo.darLlave();
			if(llave.compareTo(key)>0)
			{
				if(izquierdo!=null)
				{
					if(izquierdo.darLlave().equals(key))
					{
						//caso 1: el nodo a eliminar no tiene hijos
						if(izquierdo.darHijoDerecho()==null&&izquierdo.darHijoIzquierdo()==null)
						{
							nodo.cambiarHijoIzquierdo(null);
						}
						//caso 2: el nodo a eliminar tiene un hijo
						else if((izquierdo.darHijoIzquierdo()==null&&izquierdo.darHijoDerecho()!=null))
						{
							NodoRojoNegro<K, V> temporal=izquierdo.darHijoDerecho();
							izquierdo.cambiarCantidad(temporal.darCantidad());
							izquierdo.cambiarColor(temporal.darColor());
							izquierdo.cambiarItem(temporal.darItem());
							izquierdo.cambiarLlave(temporal.darLlave());
							izquierdo.cambiarHijoIzquierdo(temporal.darHijoIzquierdo());
							izquierdo.cambiarHijoDerecho(temporal.darHijoDerecho());
						}
						else if(izquierdo.darHijoIzquierdo()!=null&&izquierdo.darHijoDerecho()==null)
						{
							NodoRojoNegro<K, V> temporal=izquierdo.darHijoIzquierdo();
							izquierdo.cambiarCantidad(temporal.darCantidad());
							izquierdo.cambiarColor(temporal.darColor());
							izquierdo.cambiarItem(temporal.darItem());
							izquierdo.cambiarLlave(temporal.darLlave());
							izquierdo.cambiarHijoIzquierdo(temporal.darHijoIzquierdo());
							izquierdo.cambiarHijoDerecho(temporal.darHijoDerecho());
						}
						//caso 3: el nodo a eliminar tiene dos hijos
						else if(izquierdo.darHijoDerecho().darCantidad()<izquierdo.darHijoIzquierdo().darCantidad())
						{
							NodoRojoNegro<K, V> temporal=darHojaMasDerechaPorNodo(izquierdo.darHijoIzquierdo());
							NodoRojoNegro<K, V> pTemporal=darPadreDeNodo(temporal);
							izquierdo.cambiarCantidad(izquierdo.darCantidad()-1);
							izquierdo.cambiarItem(temporal.darItem());
							izquierdo.cambiarLlave(temporal.darLlave());
							if(pTemporal.darHijoDerecho()!=null)
							{
								if(pTemporal.darHijoDerecho().darLlave().compareTo(temporal.darLlave())==0)
								{
									pTemporal.cambiarHijoDerecho(null);
									if(pTemporal.darColor()==ROJO)
									{
										pTemporal.darHijoIzquierdo().cambiarColor(ROJO);
										pTemporal.cambiarColor(NEGRO);
									}
								}
							}
							else if(pTemporal.darHijoIzquierdo()!=null)
							{
								if(pTemporal.darHijoIzquierdo().darLlave().compareTo(temporal.darLlave())==0)
								{
									pTemporal.cambiarHijoIzquierdo(null);
									if(pTemporal.darHijoDerecho()!=null)
									{
										NodoRojoNegro<K, V> pTemporal2=temporal.darHijoDerecho();
										pTemporal.cambiarHijoIzquierdo(pTemporal2);
										pTemporal.darHijoIzquierdo().cambiarItem(pTemporal.darItem());
										pTemporal.darHijoIzquierdo().cambiarLlave(pTemporal.darLlave());
										pTemporal.cambiarLlave(pTemporal2.darLlave());
										pTemporal.cambiarItem(pTemporal2.darItem());
										pTemporal.cambiarHijoDerecho(null);

									}
									else
									{
										pTemporal.cambiarColor(ROJO);
									}
								}


							}
						}
						else if(izquierdo.darHijoDerecho().darCantidad()>=izquierdo.darHijoIzquierdo().darCantidad())
						{
							NodoRojoNegro<K, V> temporal=darHojaMasIzquierdaPorNodo(izquierdo.darHijoDerecho());
							NodoRojoNegro<K, V> pTemporal=darPadreDeNodo(temporal);
							izquierdo.cambiarCantidad(izquierdo.darCantidad()-1);
							izquierdo.cambiarItem(temporal.darItem());
							izquierdo.cambiarLlave(temporal.darLlave());
							if(pTemporal.darHijoDerecho()!=null)
							{
								if(pTemporal.darHijoDerecho().darLlave().compareTo(temporal.darLlave())==0)
								{
									pTemporal.cambiarHijoDerecho(null);
									if(pTemporal.darColor()==ROJO)
									{
										pTemporal.darHijoIzquierdo().cambiarColor(ROJO);
										pTemporal.cambiarColor(NEGRO);
									}
								}
							}
							else if(pTemporal.darHijoIzquierdo()!=null)
							{
								if(pTemporal.darHijoIzquierdo().darLlave().compareTo(temporal.darLlave())==0)
								{
									pTemporal.cambiarHijoIzquierdo(null);
									if(pTemporal.darHijoDerecho()!=null)
									{
										NodoRojoNegro<K, V> pTemporal2=temporal.darHijoDerecho();
										pTemporal.cambiarHijoIzquierdo(pTemporal2);
										pTemporal.darHijoIzquierdo().cambiarItem(pTemporal.darItem());
										pTemporal.darHijoIzquierdo().cambiarLlave(pTemporal.darLlave());
										pTemporal.cambiarLlave(pTemporal2.darLlave());
										pTemporal.cambiarItem(pTemporal2.darItem());
										pTemporal.cambiarHijoDerecho(null);

									}
									else
									{
										pTemporal.cambiarColor(ROJO);
									}
								}


							}
						}
					}
					else
					{
						deleteInTree(izquierdo,key);
					}
					nodo.cambiarCantidad((nodo.darCantidad()-1));
				}
			}
			else if(llave.compareTo(key)<0)
			{
				if(derecho!=null)
				{
					if(derecho.darLlave().equals(key))
					{
						//caso 1: el nodo a eliminar no tiene hijos
						if(derecho.darHijoDerecho()==null&&derecho.darHijoIzquierdo()==null)
						{
							nodo.cambiarHijoDerecho(null);
						}
						//caso 2: el nodo a eliminar tiene un hijo
						else if((derecho.darHijoIzquierdo()==null&&derecho.darHijoDerecho()!=null))
						{
							NodoRojoNegro<K, V> temporal=derecho.darHijoDerecho();
							derecho.cambiarCantidad(temporal.darCantidad());
							derecho.cambiarColor(temporal.darColor());
							derecho.cambiarItem(temporal.darItem());
							derecho.cambiarLlave(temporal.darLlave());
							derecho.cambiarHijoIzquierdo(temporal.darHijoIzquierdo());
							derecho.cambiarHijoDerecho(temporal.darHijoDerecho());
						}
						else if(derecho.darHijoIzquierdo()!=null&&derecho.darHijoDerecho()==null)
						{
							NodoRojoNegro<K, V> temporal=derecho.darHijoIzquierdo();
							derecho.cambiarCantidad(temporal.darCantidad());
							derecho.cambiarColor(temporal.darColor());
							derecho.cambiarItem(temporal.darItem());
							derecho.cambiarLlave(temporal.darLlave());
							derecho.cambiarHijoIzquierdo(temporal.darHijoIzquierdo());
							derecho.cambiarHijoDerecho(temporal.darHijoDerecho());
						}
						//caso 3: el nodo a eliminar tiene dos hijos
						else if(derecho.darHijoDerecho().darCantidad()<derecho.darHijoIzquierdo().darCantidad())
						{
							NodoRojoNegro<K, V> temporal=darHojaMasDerechaPorNodo(derecho.darHijoIzquierdo());
							NodoRojoNegro<K, V> pTemporal=darPadreDeNodo(temporal);
							derecho.cambiarCantidad(derecho.darCantidad()-1);
							derecho.cambiarItem(temporal.darItem());
							derecho.cambiarLlave(temporal.darLlave());
							if(pTemporal.darHijoDerecho()!=null)
							{
								if(pTemporal.darHijoDerecho().darLlave().compareTo(temporal.darLlave())==0)
								{
									pTemporal.cambiarHijoDerecho(null);
									if(pTemporal.darColor()==ROJO)
									{
										pTemporal.darHijoIzquierdo().cambiarColor(ROJO);
										pTemporal.cambiarColor(NEGRO);
									}
								}
							}
							else if(pTemporal.darHijoIzquierdo()!=null)
							{
								if(pTemporal.darHijoIzquierdo().darLlave().compareTo(temporal.darLlave())==0)
								{
									pTemporal.cambiarHijoIzquierdo(null);
									if(pTemporal.darHijoDerecho()!=null)
									{
										NodoRojoNegro<K, V> pTemporal2=temporal.darHijoDerecho();
										pTemporal.cambiarHijoIzquierdo(pTemporal2);
										pTemporal.darHijoIzquierdo().cambiarItem(pTemporal.darItem());
										pTemporal.darHijoIzquierdo().cambiarLlave(pTemporal.darLlave());
										pTemporal.cambiarLlave(pTemporal2.darLlave());
										pTemporal.cambiarItem(pTemporal2.darItem());
										pTemporal.cambiarHijoDerecho(null);

									}
									else
									{
										pTemporal.cambiarColor(ROJO);
									}
								}


							}
						}
						else if(derecho.darHijoDerecho().darCantidad()>=derecho.darHijoIzquierdo().darCantidad())
						{
							NodoRojoNegro<K, V> temporal=darHojaMasIzquierdaPorNodo(derecho.darHijoDerecho());
							NodoRojoNegro<K, V> pTemporal=darPadreDeNodo(temporal);
							derecho.cambiarCantidad(derecho.darCantidad()-1);
							derecho.cambiarItem(temporal.darItem());
							derecho.cambiarLlave(temporal.darLlave());
							if(pTemporal.darHijoDerecho()!=null)
							{
								if(pTemporal.darHijoDerecho().darLlave().compareTo(temporal.darLlave())==0)
								{
									pTemporal.cambiarHijoDerecho(null);
									if(pTemporal.darColor()==ROJO)
									{
										pTemporal.darHijoIzquierdo().cambiarColor(ROJO);
										pTemporal.cambiarColor(NEGRO);
									}
								}
							}
							else if(pTemporal.darHijoIzquierdo()!=null)
							{
								if(pTemporal.darHijoIzquierdo().darLlave().compareTo(temporal.darLlave())==0)
								{
									pTemporal.cambiarHijoIzquierdo(null);
									if(pTemporal.darHijoDerecho()!=null)
									{
										NodoRojoNegro<K, V> pTemporal2=temporal.darHijoDerecho();
										pTemporal.cambiarHijoIzquierdo(pTemporal2);
										pTemporal.darHijoIzquierdo().cambiarItem(pTemporal.darItem());
										pTemporal.darHijoIzquierdo().cambiarLlave(pTemporal.darLlave());
										pTemporal.cambiarLlave(pTemporal2.darLlave());
										pTemporal.cambiarItem(pTemporal2.darItem());
										pTemporal.cambiarHijoDerecho(null);

									}
									else
									{
										pTemporal.cambiarColor(ROJO);
									}
								}


							}
						}

					}
					else
					{
						deleteInTree(derecho,key);
					}
					nodo.cambiarCantidad((nodo.darCantidad()-1));
				}
			}
			else
			{
				if(derecho!=null)
				{
					NodoRojoNegro<K, V> temporal=darHojaMasIzquierdaPorNodo(derecho);
					NodoRojoNegro<K, V> pTemporal=darPadreDeNodo(temporal);
					temporal.cambiarHijoDerecho(primero.darHijoDerecho());
					temporal.cambiarHijoIzquierdo(primero.darHijoIzquierdo());
					primero=temporal;
					if(pTemporal.darHijoIzquierdo()!=null)
						pTemporal.cambiarHijoIzquierdo(null);
					else
						if(pTemporal.darHijoDerecho()!=null)
							pTemporal.cambiarHijoDerecho(null);
				}
				else if(izquierdo!=null)
				{
					NodoRojoNegro<K, V> temporal=darHojaMasDerechaPorNodo(izquierdo);
					NodoRojoNegro<K, V> pTemporal=darPadreDeNodo(temporal);
					temporal.cambiarHijoDerecho(primero.darHijoDerecho());
					temporal.cambiarHijoIzquierdo(primero.darHijoIzquierdo());
					primero=temporal;
					pTemporal.cambiarHijoIzquierdo(null);
					if(pTemporal.darHijoDerecho()!=null)
						pTemporal.cambiarHijoDerecho(null);
					else
						if(pTemporal.darHijoIzquierdo()!=null)
							pTemporal.cambiarHijoIzquierdo(null);
				}
				else
				{
					primero=null;
				}

			}
		}
	}
	public NodoRojoNegro<K, V>  darPadreDeNodo(NodoRojoNegro<K, V> nodo)
	{
		return darPadreDeNodoInTree(primero,nodo);
	}
	private NodoRojoNegro<K, V> darPadreDeNodoInTree(NodoRojoNegro<K, V> nodo1,NodoRojoNegro<K, V> nodo2)
	{
		K key=nodo1.darLlave();
		K key2=nodo2.darLlave();
		if(key.compareTo(key2)==0)
		{
			return null;
		}
		else if(key.compareTo(key2)>0)
		{
			if(nodo1.darHijoIzquierdo().darLlave().compareTo(key2)==0)
			{
				return nodo1;
			}
			else 
			{
				return darPadreDeNodoInTree(nodo1.darHijoIzquierdo(), nodo2);
			}
		}
		else if(key.compareTo(key2)<0)
		{
			if(nodo1.darHijoDerecho().darLlave().compareTo(key2)==0)
			{
				return nodo1;
			}
			else 
			{
				return darPadreDeNodoInTree(nodo1.darHijoDerecho(), nodo2);
			}
		}
		return null;


	}
	public NodoRojoNegro<K, V> darNodoPorLlave(K key)
	{
		if(primero==null)
			return null;
		else
			return darNodoPorLlaveInTree(key, primero);
	}
	private NodoRojoNegro<K, V> darNodoPorLlaveInTree(K key, NodoRojoNegro<K, V> nodo)
	{
		K temporal=nodo.darLlave();
		if(temporal.compareTo(key)>0)
		{
			if(nodo.darHijoIzquierdo()!=null)
				return darNodoPorLlaveInTree(key,nodo.darHijoIzquierdo());
			else return null;
		}
		else if(temporal.compareTo(key)<0)
		{
			if(nodo.darHijoDerecho()!=null)
				return darNodoPorLlaveInTree(key,nodo.darHijoDerecho());
			else return null;
		}
		else
		{
			return nodo;
		}
	}
}
