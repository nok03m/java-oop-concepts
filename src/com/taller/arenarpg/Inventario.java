package com.taller.arenarpg;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor genérico para almacenamiento de ítems con seguridad de tipos (Type Safety).
 * @param <T> Tipo de objeto almacenado.
 */
public class Inventario<T> {
    private List<T> items = new ArrayList<>();

    public void agregar(T item) {
        items.add(item);
    }

    public T obtener(int index) {
        return items.get(index);
    }

    public List<T> getItems() {
        return items;
    }

    public T remover(int index) {
        return items.remove(index);
    }

    public int tamano() {
        return items.size();
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }
}
