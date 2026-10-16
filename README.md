# Java OOP Concepts

A comprehensive Java project demonstrating foundational and advanced Object-Oriented Programming (OOP) concepts, memory analysis, encapsulation, static versus instance contexts, and debugging of common runtime exceptions.

## Project Structure

```text
src/com/taller/poo/
├── ConversorMoneda.java       # Static utility class for currency conversion and formatting
├── CuentaBancaria.java        # Domain entity demonstrating constructor overloading and validation
├── DEBUGGING_ANALYSIS.md      # Technical analysis of OOP errors (NPE, static context, constructors)
├── Estudiante.java            # Academic student entity demonstrating string formatting
├── GestorVehiculos.java       # Case study demonstrating static vs instance context
├── Principal.java             # Workshop demonstration entry point
├── PruebaEstudiante.java      # Case study demonstrating NullPointerException resolution
├── SistemaGestionCuentas.java # Interactive console account management system
└── Vehiculo.java              # Vehicle entity with memory analysis (primitives vs wrappers)
```

## Core Topics Covered

1. **Primitives vs Wrapper Types**: Memory footprints, stack vs heap allocation, overhead, and nullability considerations.
2. **Encapsulation & Access Control**: Proper use of private fields, getters, setters, and state validation.
3. **Constructor Overloading**: Chained constructors using `this(...)` and parameter disambiguation.
4. **Static Utilities**: Utility classes with private constructors and pure static functions.
5. **Exception Handling & Debugging**: Analysis of `NullPointerException`, static reference errors, and invalid constructor return types.
6. **Interactive Console Systems**: Menu routing with `Scanner`, loop controls, and object array manipulation.

## Running the Application

### Workshop Demonstration
```bash
javac -d out src/com/taller/poo/*.java
java -cp out com.taller.poo.Principal
```

### Account Management System
```bash
java -cp out com.taller.poo.SistemaGestionCuentas
```