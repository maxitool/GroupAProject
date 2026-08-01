package org.example.collections;

import java.util.AbstractList;
import java.util.Arrays;

public class CustomArrayList<T> extends AbstractList<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    public CustomArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public boolean add(T element) {
        if (size == elements.length) {
            int newCapacity = elements.length + (elements.length >> 1);
            elements = Arrays.copyOf(elements, newCapacity);
        }
        elements[size++] = element;
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elements[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        T oldValue = (T) elements[index];
        elements[index] = element;
        return oldValue;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public void clear(){
        for (int i = 0; i < size; i++){
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public Object[] toArray(){
        return Arrays.copyOf(elements, size);
    }

    @Override
    public String toString(){
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++){
            sb.append(elements[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
