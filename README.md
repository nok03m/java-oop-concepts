# Java OOP Concepts

A comprehensive Java project demonstrating foundational and advanced Object-Oriented Programming (OOP) concepts, memory analysis, encapsulation, access modifiers across packages, static versus instance contexts, inheritance, polymorphism, generic containers, 2D grid matrix mechanics, and debugging of common runtime exceptions.

## Project Structure

```text
src/
├── co/edu/puj/rpg/
│   ├── modelo/
│   │   ├── Personaje.java              # Hero entity with strictly private attributes, validated setters, and combat behavior
│   │   └── enemigo/
│   │       └── Enemigo.java            # Enemy entity isolated in subpackage to demonstrate package-private boundaries
│   └── principal/
│       ├── Main.java                   # Interactive battle arena loop (do-while, switch), StringBuilder event log & shop
│       └── PruebasEncapsulamiento.java # Automated verification suite for encapsulation and business invariants
│
└── com/taller/
    ├── arenarpg/
    │   ├── ArenaJuego.java             # Main game engine managing turns, IA actions, win/loss conditions
    │   ├── Enemigo.java                # Hostile unit extending Personaje with custom defense and special attack
    │   ├── Guerrero.java               # Physical combatant specialization with shield block mechanics
    │   ├── ICombatiente.java           # Behavioral interface contract for defensive mitigation
    │   ├── Inventario.java             # Type-safe generic container (Inventario<T>) for managing items
    │   ├── Mago.java                   # Arcane combatant specialization with mana resource management
    │   ├── Main.java                   # Game entry point initializing and launching the arena
    │   ├── Personaje.java              # Abstract base entity with method overloading, health, and grid coordinates
    │   ├── Pocion.java                 # Consumable item model supporting health and mana restoration
    │   ├── Tablero.java                # 5x5 2D matrix arena grid with coordinate bounds validation and chest objective
    │   └── TestArena.java              # Automated technical verification suite (inheritance, generics, collections)
    │
    └── poo/
        ├── ConversorMoneda.java        # Static utility class for currency conversion and formatting
        ├── CuentaBancaria.java         # Domain entity demonstrating constructor overloading and validation
        ├── DEBUGGING_ANALYSIS.md       # Technical analysis of OOP errors (NPE, static context, constructors)
        ├── Estudiante.java             # Academic student entity demonstrating string formatting
        ├── GestorVehiculos.java        # Case study demonstrating static vs instance context
        ├── Principal.java              # Workshop demonstration entry point
        ├── PruebaEstudiante.java       # Case study demonstrating NullPointerException resolution
        ├── SistemaGestionCuentas.java  # Interactive console account management system
        └── Vehiculo.java               # Vehicle entity with memory analysis (primitives vs wrappers)
```

## Modules and Core Topics

### 1. Encapsulation & Access Modifiers (RPG Battle Arena)
Located in `co.edu.puj.rpg`:
* **Strict Private Encapsulation**: Complete hiding of internal instance state (`private` fields).
* **Cross-Package Access Segregation**: Separation of entities (`modelo` and `modelo.enemigo`) and the execution layer (`principal`).
* **Controlled Mutators (Setters)**: Business validation preventing invalid state (clamped HP, negative gold rejection, minimum level restrictions).
* **Public Query API (Getters)**: Safe state inspection for exterior consumers.
* **Console Battle Engine**: `do-while` fight loop, `switch` command dispatcher, formatted status reporting with `System.out.printf`, and round-by-round event history logging with `StringBuilder`.
* **Expansion Mechanics**: Gold store sub-menu for item purchases and a 15% critical strike system with auto-scaling level/experience progression.

### 2. Foundations of OOP & Memory Analysis
Located in `com.taller.poo`:
* **Primitives vs Wrapper Types**: Memory footprints, stack vs heap allocation, overhead, and nullability considerations.
* **Constructor Overloading**: Chained constructors using `this(...)` and parameter disambiguation.
* **Static Utilities**: Utility classes with private constructors and pure static functions.
* **Exception Handling & Debugging**: Resolution of `NullPointerException`, static reference errors, and invalid constructor return types.
* **Interactive Account Management**: Menu routing with `Scanner` and dynamic object collections.

### 3. Advanced OOP, Generics & Polymorphism (Arena RPG)
Located in `com.taller.arenarpg`:
* **Inheritance Hierarchy & Abstract Classes**: Abstract base class `Personaje` unifying shared state and combat contracts, extended by concrete specializations (`Guerrero`, `Mago`, `Enemigo`) with constructor chaining via `super()`.
* **Defensive Behavioral Interface**: Interface `ICombatiente` providing standard defensive mitigation contracts across combatant types.
* **Static & Dynamic Polymorphism**:
  * *Method Overloading*: `atacar(Personaje)` for standard attacks and `atacar(Personaje, boolean)` for frenzy mode.
  * *Method Overriding*: Distinct implementations of `realizarAtaqueEspecial(Personaje)` and `defender(int)` for each class specialization.
* **Generic Containers (Type Safety)**: Custom generic class `Inventario<T>` ensuring type safety when storing and consuming items such as `Pocion`.
* **Dynamic Collections & Functional Removal**: Real-time management of active units in `List<Personaje>` using `removeIf` predicates to filter defeated combatants.
* **2D Matrix Grid System**: 5x5 arena matrix (`Tablero`) rendered with nested loops, dynamic unit synchronization, boundary checks preventing `IndexOutOfBoundsException`, and chest objective navigation at `(2,2)`.
* **Interactive Game Engine**: Central `ArenaJuego` coordination loop (`do-while`), user command routing (`switch`), autonomous enemy AI actions, and victory/defeat evaluation.

---

## Compiling and Running

Ensure JDK 17 or higher is installed and accessible.

### Advanced Arena RPG (Polymorphism & Generics)
```bash
# Compile Arena RPG module
javac -d out src/com/taller/arenarpg/*.java

# Run interactive Arena RPG game
java -cp out com.taller.arenarpg.Main

# Run automated Arena RPG test suite
java -cp out com.taller.arenarpg.TestArena
```

### RPG Battle Simulator (Arena de Héroes)
```bash
# Compile RPG module
javac -d out src/co/edu/puj/rpg/modelo/enemigo/*.java src/co/edu/puj/rpg/modelo/*.java src/co/edu/puj/rpg/principal/*.java

# Run interactive battle simulator
java -cp out co.edu.puj.rpg.principal.Main

# Run automated encapsulation verification tests
java -cp out co.edu.puj.rpg.principal.PruebasEncapsulamiento
```

### Foundational Workshop Modules
```bash
# Compile foundational OOP module
javac -d out src/com/taller/poo/*.java

# Run workshop demonstration
java -cp out com.taller.poo.Principal

# Run interactive account management system
java -cp out com.taller.poo.SistemaGestionCuentas
```
