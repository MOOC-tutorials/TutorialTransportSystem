package data_structures;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import algoritmos.Ordenadores;




public class SimpleArrayLIst <T> 
{


	private T[] array = (T[]) new Object[1];

	private int size;


	public Iterator<T> iterator() {
		return new ElementsIterator();
	}


	public ListIterator<T> listIterator() {
		return new ElementsIterator(0);
	}


	public ListIterator<T> listIterator(final int index) {
		return new ElementsIterator(index);
	}


	public int size() {
		return this.size;
	}


	public boolean isEmpty() {
		return size == 0;
	}

	public Object[] toArray() {
		final T[] newArray = (T[]) new Object[this.size()];
		System.arraycopy(array, 0, newArray, 0, this.size());
		return newArray;
	}


	public <R> R[] toArray(final R[] a) {
		if (a.length < size) return (R[]) Arrays.copyOf(array, size, a.getClass());
		System.arraycopy(array, 0, a, 0, size);

		if (a.length > size) a[size] = null;
		return a;
	}


	public boolean add(final T t) {
		if (size == array.length) {
			final T[] oldArray = array;
			array = (T[]) new Object[this.size() + 15];
			System.arraycopy(oldArray, 0, array, 0, oldArray.length);
		}
		array[size++] = t;
		return true;
	}


	public void add(final int index, final T element) {
		rangeCheckForAdd(index);

		System.arraycopy(array, index, array, index + 1, size - index);
		array[index] = element;
		size++;
	}

	public boolean remove(T object) {
		for (int i = size() - 1; i >= 0; i--) {
			if (array[i].equals(object)) {
				this.remove(i);
				return true;
			}
		}
		return false;
	}


	public T remove(final int index) {
		final T element = array[index];
		if (index != this.size() - 1) System.arraycopy(array, index + 1, array, index, this.size() - index - 1);
		size--;
		return element;
	}



	public void clear() {
		array = (T[]) new Object[1];
		size = 0;
	}


	public T get(final int index) {
		rangeCheckForAdd(index);
		return array[index];
	}


	public T set(final int index, final T element) {
		rangeCheckForAdd(index);
		T oldValue = array[index];
		array[index] = element;
		return oldValue;
	}


	public int indexOf(final Object o) {
		nullCheck(o);
		for (int i = 0; i < size; i++) {
			if (array[i].equals(o)) return i;
		}
		return -1;
	}


	public int lastIndexOf(final Object o) {
		nullCheck(o);
		for (int i = size; i > 0; i--) {
			if (array[i].equals(o)) return i;
		}
		return -1;
	}


	private class ElementsIterator implements ListIterator<T> {

		private int index = 0;

		private int last = -1;

		public ElementsIterator() {
			this(0);
		}

		public ElementsIterator(final int index) {
			if (index < 0 || index > size()) throw new IndexOutOfBoundsException();
			this.index = index;
		}


		public boolean hasNext() {
			return SimpleArrayLIst.this.size > index;
		}


		public T next() {
			if (!hasNext()) throw new NoSuchElementException();
			last = index;
			return SimpleArrayLIst.this.array[index++];
		}


		public boolean hasPrevious() {
			return index != 0;
		}


		public T previous() {
			if (!hasPrevious()) throw new NoSuchElementException();
			last = previousIndex();
			index--;
			return SimpleArrayLIst.this.array[last];
		}


		public int nextIndex() {
			if (!hasNext()) return SimpleArrayLIst.this.size();
			return ++index;
		}


		public int previousIndex() {
			if (!hasPrevious()) return -1;
			return --index;
		}


		public void remove() {
			if (last == -1) throw new IllegalStateException();
			SimpleArrayLIst.this.remove(last);
			index--;
			last = -1;
		}


		public void set(final T element) {
			if (last == -1) throw new IllegalStateException();
			SimpleArrayLIst.this.set(last, element);
		}


		public void add(final T t) {
			SimpleArrayLIst.this.add(t);
		}
	}

	private void rangeCheckForAdd(final int index) {
		if (index > size || index < 0) throw new IndexOutOfBoundsException();
	}

	private void nullCheck(final Object element) {
		if (null == element) throw new NullPointerException();
	}
	public SimpleArrayLIst<T> ordenar(SimpleArrayLIst<T> arreglo, Comparator<T> comp) 
	{
		return Ordenadores.shellSort(arreglo, comp);
	}
	

}
