package com.taller.poo;

/*
 * ============================================================================
 * CASE STUDY 2: INVOCATION OF INSTANCE CONTEXT FROM STATIC CONTEXT
 * ============================================================================
 *
 * ORIGINAL PROBLEMATIC CODE:
 * ----------------------------------------------------------------------------
 * public class GestorVehiculos {
 *     public void encenderMotor() {
 *         System.out.println("El motor del vehículo ha sido encendido.");
 *     }
 *
 *     public static void main(String[] args) {
 *         // Attempting to call an instance method directly from static main
 *         encenderMotor();
 *     }
 * }
 * ----------------------------------------------------------------------------
 *
 * ANALYTICAL QUESTIONS AND ANSWERS:
 *
 * 1. What is the error message emitted by the Java compiler?
 *
 *    ANSWER:
 *    "non-static method encenderMotor() cannot be referenced from a static context"
 *
 * 2. Why can't a 'static' method directly invoke an instance method from an OOP perspective?
 *
 *    ANSWER:
 *    - In Java and OOP theory, 'static' members belong to the class definition itself,
 *      loaded into the Metaspace/Method Area independently of any object instantiation.
 *      Crucially, static methods execute WITHOUT an implicit 'this' reference.
 *    - Conversely, instance methods belong to individual object instances allocated on
 *      the heap and require an active 'this' context to access instance variables and state.
 *    - When 'main' (static) attempts to invoke 'encenderMotor()' without an object reference,
 *      the JVM has no way of determining WHICH instance's state the method should act upon.
 *
 * 3. Two valid correction alternatives:
 *
 *    ALTERNATIVE A (Instantiate the object):
 *    Create an instance of the class within 'main' and invoke the method via the instance reference:
 *        GestorVehiculos gestor = new GestorVehiculos();
 *        gestor.encenderMotor();
 *
 *    ALTERNATIVE B (Make the method static):
 *    If the method does not rely on any instance variables or individual object state,
 *    promote the method itself to static:
 *        public static void encenderMotor() { ... }
 * ============================================================================
 */
public class GestorVehiculos {

    // Instance method (requires an object context)
    public void encenderMotor() {
        System.out.println("The vehicle engine has been successfully started (Instance context).");
    }

    // Alternative B: Static utility equivalent
    public static void encenderMotorStatico() {
        System.out.println("The vehicle engine has been successfully started (Static context).");
    }

    public static void main(String[] args) {
        // --- Demonstration of Alternative A (Object Instantiation) ---
        GestorVehiculos gestor = new GestorVehiculos();
        gestor.encenderMotor();

        // --- Demonstration of Alternative B (Static Method Call) ---
        encenderMotorStatico();
    }
}
