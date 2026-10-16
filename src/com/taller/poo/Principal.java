package com.taller.poo;

/**
 * Main executable entry point demonstrating object instantiation, state manipulation,
 * static utility method calls, and formatted output reporting using System.out.printf.
 */
public class Principal {

    public static void main(String[] args) {
        System.out.printf("========================================================================%n");
        System.out.printf("        OBJECT-ORIENTED PROGRAMMING (OOP) DEMONSTRATION WORKSHOP        %n");
        System.out.printf("========================================================================%n%n");

        // ---------------------------------------------------------------------
        // 1. VEHICLE INSTANTIATION AND STATE MANAGEMENT
        // ---------------------------------------------------------------------
        // Instantiate at least 3 Vehiculo objects (1 default, 2 parameterized)
        Vehiculo vehiculo1 = new Vehiculo(); // Default constructor
        Vehiculo vehiculo2 = new Vehiculo("Toyota", "Corolla", 15000.50, 180, true);
        Vehiculo vehiculo3 = new Vehiculo("Tesla", "Model 3", 8230.25, 225, false);

        // Mutate state of vehiculo3
        vehiculo3.encenderMotor();
        vehiculo3.setKilometraje(vehiculo3.getKilometraje() + 45.75);

        Vehiculo[] vehiculos = { vehiculo2, vehiculo1, vehiculo3 };

        System.out.printf("=== REPORTE DE VEHÍCULOS ===%n");
        for (int i = 0; i < vehiculos.length; i++) {
            Vehiculo v = vehiculos[i];
            System.out.printf("[%d] Marca: %s | Modelo: %s | KM: %.2f | VelMax: %d km/h | Encendido: %b%n",
                    (i + 1),
                    v.getMarca(),
                    v.getModelo(),
                    v.getKilometraje(),
                    v.getVelocidadMaxima(),
                    v.isEncendido());
        }
        System.out.printf("%n");

        // ---------------------------------------------------------------------
        // 2. STUDENT INSTANTIATION AND ACADEMIC REPORT GENERATION
        // ---------------------------------------------------------------------
        // Instantiate at least 3 Estudiante objects
        Estudiante est1 = new Estudiante("E-101", "Alice Johnson", 3.85, true);
        Estudiante est2 = new Estudiante("E-102", "Robert Smith", 2.95, false);
        Estudiante est3 = new Estudiante("E-103", "Elena Rostova", 4.00, true);

        Estudiante[] estudiantes = { est1, est2, est3 };

        System.out.printf("=== REPORTE DE ESTUDIANTES ===%n");
        for (Estudiante est : estudiantes) {
            // Invoking instance method that internally uses StringBuilder and formatted specifiers
            System.out.printf("%s%n", est.obtenerReporteAcademico());
        }
        System.out.printf("%n");

        // ---------------------------------------------------------------------
        // 3. BANK ACCOUNT INSTANTIATION AND STATE MUTATION (DEPOSIT / WITHDRAW)
        // ---------------------------------------------------------------------
        // Instantiate at least 3 CuentaBancaria objects using 3 different overloaded constructors
        CuentaBancaria cuenta1 = new CuentaBancaria(); // Default constructor: 000000, Sin Nombre, 0.0, 0.0
        CuentaBancaria cuenta2 = new CuentaBancaria("1002", "Ana Gómez"); // 2-param: balance 0.0, credit 100.0
        CuentaBancaria cuenta3 = new CuentaBancaria("1001", "Carlos Pérez", 1000.00, 500.00); // 4-param full

        // Customize default account
        cuenta1.setNumeroCuenta("1000");
        cuenta1.setTitular("Default Holder");

        // Perform deposit and withdrawal operations
        cuenta3.depositar(500.75); // Balance becomes 1500.75
        cuenta3.retirar(250.00);  // Balance becomes 1250.75

        cuenta2.depositar(200.00); // Balance becomes 200.00
        cuenta2.retirar(150.00);  // Balance becomes 50.00

        cuenta1.depositar(300.00); // Balance becomes 300.00

        CuentaBancaria[] cuentas = { cuenta3, cuenta2, cuenta1 };

        System.out.printf("=== REPORTE DE CUENTAS BANCARIAS ===%n");
        for (CuentaBancaria c : cuentas) {
            System.out.printf("Cuenta: %s | Titular: %s | Saldo Final: %s | Crédito: %s%n",
                    c.getNumeroCuenta(),
                    c.getTitular(),
                    ConversorMoneda.formatearMoneda(c.getSaldo()),
                    ConversorMoneda.formatearMoneda(c.getLimiteCredito()));
        }
        System.out.printf("%n");

        // ---------------------------------------------------------------------
        // 4. STATIC UTILITY CLASS INVOCATIONS (ConversorMoneda)
        // ---------------------------------------------------------------------
        System.out.printf("=== PRUEBA DE MÉTODOS ESTÁTICOS (ConversorMoneda) ===%n");
        double usdAmount = 1250.75;
        double exchangeRate = 0.92; // 1 USD = 0.92 EUR
        double convertedEuros = ConversorMoneda.convertirDolaresAEuros(usdAmount, exchangeRate);

        System.out.printf("Monto original USD: %s%n", ConversorMoneda.formatearMoneda(usdAmount));
        System.out.printf("Tasa de cambio aplicada: %.4f EUR/USD%n", exchangeRate);
        System.out.printf("Monto convertido en Euros: EUR %.2f%n", convertedEuros);

        // Edge case: invalid rate test
        double invalidConversion = ConversorMoneda.convertirDolaresAEuros(-50.0, exchangeRate);
        System.out.printf("Prueba de validación con monto negativo (-$50): %.2f%n", invalidConversion);
    }
}
