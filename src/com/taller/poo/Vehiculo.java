package com.taller.poo;

/*
 * ============================================================================
 * TECHNICAL ANALYSIS: MEMORY CONSUMPTION AND NULLABILITY (WRAPPERS VS PRIMITIVES)
 * ============================================================================
 * 
 * 1. Memory Consumption:
 *    - Primitive types (such as double, int, boolean) store their raw binary values
 *      directly in memory (on the call stack for local variables, or packed inline
 *      within the object's contiguous heap space for instance variables).
 *      For example:
 *        - boolean takes 1 byte (or 4 bytes depending on JVM word alignment).
 *        - int takes 4 bytes.
 *        - double takes 8 bytes.
 *    - Wrapper classes (Boolean, Integer, Double) are full objects allocated on the Java Heap.
 *      Each wrapper object incurs overhead:
 *        - An object header (typically 12 to 16 bytes on a 64-bit JVM).
 *        - The primitive value field.
 *        - Memory alignment padding (aligned to 8-byte boundaries).
 *        - An object reference pointer (4 bytes with Compressed OOPs, or 8 bytes without).
 *      Consequently, an Integer or Double object consumes roughly 16 to 24 bytes in the heap,
 *      plus the reference variable itself, compared to just 4 or 8 bytes for a primitive.
 * 
 * 2. Nullability and NullPointerException:
 *    - Primitive types cannot hold a 'null' value; they always hold a default literal
 *      (e.g., 0, 0.0, false).
 *    - Wrappers can represent 'null', which is advantageous when modeling optional,
 *      missing, or uninitialized data (e.g., database columns allowing NULL).
 *    - However, using wrappers introduces the risk of NullPointerException (NPE) during
 *      implicit auto-unboxing when expressions require a primitive value.
 * ============================================================================
 */
public class Vehiculo {

    // Vehicle brand name
    private String marca;

    // Vehicle model name
    private String modelo;

    // Current mileage: 'double' primitive is selected because mileage is an essential,
    // continuous numeric measurement that starts at 0.0 and never requires a null/absent state.
    // Primitive double avoids heap overhead and boxing/unboxing penalties.
    private double kilometraje;

    // Maximum achievable speed: 'int' primitive is selected because maximum speed is a scalar,
    // non-null integer specification. Using primitive int prevents unnecessary object allocations.
    private int velocidadMaxima;

    // Engine ignition state: 'boolean' primitive is selected because an engine state is strictly
    // binary (either running: true, or off: false). There is no third 'null' or unknown state needed.
    private boolean encendido;

    /**
     * Default constructor.
     * Initializes the vehicle with predefined baseline values.
     */
    public Vehiculo() {
        this.marca = "Genérico";
        this.modelo = "Sin Modelo";
        this.kilometraje = 0.0;
        this.velocidadMaxima = 120;
        this.encendido = false;
    }

    /**
     * Parameterized constructor.
     * Uses the 'this' keyword to disambiguate parameters from instance attributes.
     *
     * @param marca           The brand of the vehicle.
     * @param modelo          The model of the vehicle.
     * @param kilometraje     The initial mileage.
     * @param velocidadMaxima The maximum speed in km/h.
     * @param encendido       The initial engine running status.
     */
    public Vehiculo(String marca, String modelo, double kilometraje, int velocidadMaxima, boolean encendido) {
        this.marca = marca;
        this.modelo = modelo;
        this.kilometraje = kilometraje;
        this.velocidadMaxima = velocidadMaxima;
        this.encendido = encendido;
    }

    // --- Instance Methods ---

    public void encenderMotor() {
        this.encendido = true;
    }

    public void apagarMotor() {
        this.encendido = false;
    }

    // --- Getters and Setters ---

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(double kilometraje) {
        this.kilometraje = kilometraje;
    }

    public int getVelocidadMaxima() {
        return velocidadMaxima;
    }

    public void setVelocidadMaxima(int velocidadMaxima) {
        this.velocidadMaxima = velocidadMaxima;
    }

    public boolean isEncendido() {
        return encendido;
    }

    public void setEncendido(boolean encendido) {
        this.encendido = encendido;
    }
}
