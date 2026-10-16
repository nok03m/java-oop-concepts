package com.taller.poo;

/*
 * ============================================================================
 * CASE STUDY 1: NULL REFERENCE EXCEPTION (NullPointerException)
 * ============================================================================
 *
 * ORIGINAL PROBLEMATIC CODE:
 * ----------------------------------------------------------------------------
 * public class PruebaEstudiante {
 *     public static void main(String[] args) {
 *         Estudiante estudiante1;
 *         String reporte = estudiante1.obtenerReporteAcademico();
 *         System.out.println(reporte);
 *     }
 * }
 * ----------------------------------------------------------------------------
 *
 * ANALYTICAL QUESTIONS AND ANSWERS:
 *
 * 1. What error does the Java compiler indicate or what exception is thrown
 *    at runtime when attempting to compile/run the code above?
 *
 *    ANSWER:
 *    - In Java, local variables declared inside a method are NOT automatically
 *      initialized by the JVM (unlike instance fields).
 *    - Therefore, the Java compiler (javac) fails at compile time with the error:
 *        "variable estudiante1 might not have been initialized"
 *    - If 'estudiante1' had been an uninitialized instance field or explicitly
 *      assigned 'null' (e.g., Estudiante estudiante1 = null;), compilation would
 *      succeed, but executing 'estudiante1.obtenerReporteAcademico()' at runtime
 *      would immediately throw:
 *        "java.lang.NullPointerException: Cannot invoke '...obtenerReporteAcademico()'
 *         because 'estudiante1' is null"
 *
 * 2. Why can't variable 'estudiante1' invoke the instance method in its current state?
 *
 *    ANSWER:
 *    - The variable 'estudiante1' is merely a reference variable capable of storing
 *      the memory address of an Estudiante instance in the Java heap.
 *    - Without invoking the 'new' operator, no memory has been allocated for the object,
 *      no constructor has executed to initialize its state, and the reference does not
 *      point to any valid heap instance.
 *    - An instance method operates on the specific state of an object (its 'this' context).
 *      Because no instance exists, there is no state to operate on.
 *
 * 3. Exact code instruction that must be added to properly instantiate 'estudiante1':
 *
 *    ANSWER:
 *    estudiante1 = new Estudiante("E-101", "John Doe", 3.75, true);
 * ============================================================================
 */
public class PruebaEstudiante {

    public static void main(String[] args) {
        // FIX: Proper instantiation using the 'new' operator and parameterized constructor
        Estudiante estudiante1 = new Estudiante("E-101", "John Doe", 3.75, true);

        // Safe invocation on an existing, initialized instance
        String reporte = estudiante1.obtenerReporteAcademico();
        System.out.println(reporte);
    }
}
