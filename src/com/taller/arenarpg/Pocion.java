package com.taller.arenarpg;

/**
 * Representa una poción consumible utilizada en la arena.
 */
public class Pocion {
    public enum Tipo {
        VIDA,
        MANA
    }

    private String nombre;
    private int cantidad;
    private Tipo tipo;

    public Pocion(String nombre, int cantidad, Tipo tipo) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.tipo = tipo;
    }

    public Pocion(String nombre, int cantidad) {
        this(nombre, cantidad, Tipo.VIDA);
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Tipo getTipo() {
        return tipo;
    }

    /**
     * Aplica el efecto de la poción sobre un combatiente objetivo.
     * @param objetivo Personaje que consume la poción.
     */
    public void aplicar(Personaje objetivo) {
        if (tipo == Tipo.VIDA) {
            System.out.println("🧪 " + objetivo.getNombre() + " bebe " + nombre + ".");
            objetivo.curar(cantidad);
        } else if (tipo == Tipo.MANA) {
            if (objetivo instanceof Mago mago) {
                System.out.println("🧪 " + mago.getNombre() + " bebe " + nombre + ".");
                mago.restaurarMana(cantidad);
            } else {
                System.out.println("⚠️ " + objetivo.getNombre() + " no utiliza maná; la poción no surte efecto.");
            }
        }
    }

    @Override
    public String toString() {
        return nombre + " (+" + cantidad + (tipo == Tipo.VIDA ? " PV)" : " PM)");
    }
}
