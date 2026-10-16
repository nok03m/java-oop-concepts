# Analysis and Debugging of Common OOP Errors in Java

This document contains the complete technical analysis and solutions for **Section 6** of the OOP Workshop, answering all analytical questions for each case study in English.

---

## Case Study 1: Null Reference Exception (`NullPointerException`)

### Problematic Code Snippet
```java
package com.taller.poo;

public class PruebaEstudiante {
    public static void main(String[] args) {
        Estudiante estudiante1;
        // Attempting to invoke an instance method on an uninstantiated reference
        String reporte = estudiante1.obtenerReporteAcademico();
        System.out.println(reporte);
    }
}
```

### Analytical Questions & Technical Explanations

#### 1. What error does the Java compiler indicate or what exception is thrown at runtime?
- **Compile-time:** In Java, local variables inside a method are **not** assigned default values by the JVM. Therefore, the Java compiler emits the compilation error:
  ```text
  java: variable estudiante1 might not have been initialized
  ```
- **Runtime:** If `estudiante1` had been initialized to `null` (e.g., `Estudiante estudiante1 = null;`), the code would compile, but executing `estudiante1.obtenerReporteAcademico()` would throw:
  ```text
  java.lang.NullPointerException: Cannot invoke "com.taller.poo.Estudiante.obtenerReporteAcademico()" because "estudiante1" is null
  ```

#### 2. Why can't variable `estudiante1` invoke the instance method `obtenerReporteAcademico()` in its current state?
A reference variable in Java is merely a pointer capable of holding the memory address of an object allocated on the Java Virtual Machine (JVM) heap. 
- Declaring `Estudiante estudiante1;` only allocates memory on the stack for the reference itself; it does not allocate heap memory for an `Estudiante` object.
- Instance methods operate on the state of an instantiated object via the implicit `this` reference. Because no object was instantiated using the `new` operator, there is no heap memory or state to execute against.

#### 3. Exact instruction to properly instantiate `estudiante1`:
```java
Estudiante estudiante1 = new Estudiante("E-101", "Carlos Pérez", 3.85, true);
```

---

## Case Study 2: Invoking Instance Context from Static Context

### Problematic Code Snippet
```java
package com.taller.poo;

public class GestorVehiculos {
    public void encenderMotor() {
        System.out.println("El motor del vehículo ha sido encendido.");
    }

    public static void main(String[] args) {
        // Attempting to invoke instance method directly from static context
        encenderMotor();
    }
}
```

### Analytical Questions & Technical Explanations

#### 1. What is the compiler error message?
```text
java: non-static method encenderMotor() cannot be referenced from a static context
```

#### 2. Why can't a `static` method (like `main`) invoke an instance method from an OOP perspective?
- In Java, `static` members belong to the class blueprint itself and are loaded into the Metaspace/Method Area before any object instance is created. Static methods execute **without an implicit `this` pointer**.
- In contrast, an instance method requires an active object instance on the heap to access instance fields and execute its logic.
- When `main` directly invokes `encenderMotor()`, the JVM cannot infer *which* specific instance of `GestorVehiculos` should execute the method.

#### 3. Two valid correction alternatives:
- **Alternative A (Instantiate the object):**
  Create an instance of `GestorVehiculos` inside `main` and invoke the method through that reference:
  ```java
  GestorVehiculos gestor = new GestorVehiculos();
  gestor.encenderMotor();
  ```
- **Alternative B (Declare the method static):**
  If the method does not rely on any instance variables, convert the method into a static method:
  ```java
  public static void encenderMotor() {
      System.out.println("El motor del vehículo ha sido encendido.");
  }
  ```

---

## Case Study 3: Incorrect Constructor Signature (`void` Return Type)

### Problematic Code Snippet
```java
package com.taller.poo;

public class CuentaBancaria {
    private String numeroCuenta;
    private double saldo;

    // Erroneous constructor declaration
    public void CuentaBancaria(String numeroCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
    }
}
```

### Analytical Questions & Technical Explanations

#### 1. Why does adding `void` alter the nature of the method?
According to the Java Language Specification (JLS §8.8), constructors **must never specify a return type**, not even `void`. 
- Adding `void` causes the Java compiler to interpret `public void CuentaBancaria(...)` as a regular instance method that happens to share the same name as the class.
- It completely ceases to function as a constructor.

#### 2. Why does `new CuentaBancaria("A-101", 500.0);` fail to compile?
Because `CuentaBancaria(...)` was parsed as a method and not a constructor, the compiler determined that the class lacked any explicit constructor. 
- Consequently, Java generated the default no-argument constructor `CuentaBancaria()`.
- When attempting `new CuentaBancaria("A-101", 500.0);`, the compiler looks for a constructor accepting `(String, double)`, fails to find one, and reports:
  ```text
  java: constructor CuentaBancaria in class com.taller.poo.CuentaBancaria cannot be applied to given types;
    required: no arguments
    found:    java.lang.String,double
    reason:   actual and formal argument lists differ in length
  ```

#### 3. Correct signature and implementation:
Remove the return type `void`:
```java
public CuentaBancaria(String numeroCuenta, double saldoInicial) {
    this.numeroCuenta = numeroCuenta;
    this.saldo = saldoInicial;
}
```

---

## Case Study 4: Variable Shadowing Due to Absence of `this`

### Problematic Code Snippet
```java
package com.taller.poo;

public class Vehiculo {
    private int velocidadMaxima;
    private boolean encendido;

    public Vehiculo(int velocidadMaxima, boolean encendido) {
        // Variable shadowing without 'this'
        velocidadMaxima = velocidadMaxima;
        encendido = encendido;
    }

    public void imprimirEstado() {
        System.out.printf("Velocidad Máxima: %d Encendido: %b%n", velocidadMaxima, encendido);
    }
}
```

### Analytical Questions & Technical Explanations

#### 1. What values are printed when invoking `v.imprimirEstado();` and why?
- **Printed Output:**
  ```text
  Velocidad Máxima: 0 Encendido: false
  ```
- **Why:** The statements `velocidadMaxima = velocidadMaxima;` and `encendido = encendido;` assign the method parameters to themselves. The object's instance fields were never updated, so they retain the default values assigned by the JVM upon memory allocation: `0` for `int` and `false` for `boolean`.

#### 2. Explain variable shadowing:
Variable shadowing occurs when a variable declared in an inner scope (such as a method parameter) shares the exact same name as a variable in an outer scope (such as an instance field). 
- Within the inner scope, unqualified identifier references resolve to the innermost declaration (the parameter), hiding the outer field.
- Without the qualifying `this` keyword, assignments modify only the parameter within the local scope, leaving the instance variable untouched.

#### 3. Corrected constructor implementation:
Use the `this` keyword to explicitly refer to the instance attributes:
```java
public Vehiculo(int velocidadMaxima, boolean encendido) {
    this.velocidadMaxima = velocidadMaxima;
    this.encendido = encendido;
}
```
