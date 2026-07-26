package org.example.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomArrayList<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    public CustomArrayList(){
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public void add(T element){
        if (size == elements.length){
            int newCapacity = elements.length + (elements.length >> 1);
            elements = Arrays.copyOf(elements, newCapacity);
        }
        elements[size++] = element;
    }

    @SuppressWarnings("unchecked")
    public T get(int index){
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elements[index];
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void clear(){
        for (int i = 0; i < size; i++){
            elements[i] = null;
        }
        size = 0;
    }

    public Object[] toArray(){
        return Arrays.copyOf(elements, size);
    }

    @Override
    public Iterator<T> iterator(){
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                return (T) elements[currentIndex++];
            }
        };
    }
}
