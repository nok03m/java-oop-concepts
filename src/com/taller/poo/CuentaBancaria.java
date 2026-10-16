package com.taller.poo;

/**
 * Represents a bank account domain entity demonstrating primitive vs wrapper usage,
 * constructor overloading, parameter disambiguation with 'this', and business logic validation.
 */
public class CuentaBancaria {

    private String numeroCuenta;
    private String titular;
    private double saldo;
    private Double limiteCredito;

    /*
     * ============================================================================
     * ANALYTICAL QUESTIONS AND ANSWERS:
     * ============================================================================
     *
     * 1. What difference occurs at the memory level and default assignment when
     *    declaring 'saldo' as a primitive double versus 'limiteCredito' as a Wrapper Double?
     *
     *    ANSWER:
     *    - At the memory level:
     *      * The primitive 'double' (saldo) allocates 8 contiguous bytes directly within
     *        the object memory block on the heap. It holds the raw 64-bit IEEE 754 floating-point
     *        value.
     *      * The wrapper 'Double' (limiteCredito) stores only an object reference (address pointer)
     *        of 4 or 8 bytes in the enclosing object. The actual numeric payload resides in a separate
     *        heap-allocated Double object comprising an object header (12-16 bytes), the 8-byte
     *        primitive value, and padding (totaling ~24 bytes).
     *    - At the default assignment level:
     *      * Primitive 'saldo' is automatically initialized by the JVM to 0.0.
     *      * Reference 'limiteCredito' is automatically initialized by the JVM to 'null',
     *        indicating that it does not point to any allocated Double object.
     *
     * 2. Why does an arithmetic operation on an uninitialized Double attribute cause
     *    a NullPointerException, while on a primitive double it operates with 0.0?
     *
     *    ANSWER:
     *    - When an arithmetic operation (+, -, *, /) or comparison is performed on a Wrapper
     *      object, Java must first "unbox" it into its corresponding primitive type. The Java
     *      compiler translates this into an invocation of 'limiteCredito.doubleValue()'.
     *    - If 'limiteCredito' was not explicitly initialized, its value is 'null'. Attempting
     *      to invoke '.doubleValue()' on a null reference dereferences an invalid memory address,
     *      triggering a runtime 'java.lang.NullPointerException'.
     *    - In contrast, the primitive 'double' is never null; it directly holds 0.0, so arithmetic
     *      operations execute without dereferencing or throwing exceptions.
     * ============================================================================
     */

    /**
     * Default constructor.
     * Initializes default baseline account values.
     */
    public CuentaBancaria() {
        this("000000", "Sin Nombre", 0.0, 0.0);
    }

    /**
     * Overloaded constructor accepting account number and account holder.
     * Initializes balance to 0.0 and credit limit to 100.0.
     *
     * @param numeroCuenta Unique account identifier.
     * @param titular      Name of account holder.
     */
    public CuentaBancaria(String numeroCuenta, String titular) {
        this(numeroCuenta, titular, 0.0, 100.0);
    }

    /**
     * Overloaded full constructor initializing all attributes.
     * Uses 'this' to prevent variable shadowing between parameter names and fields.
     *
     * @param numeroCuenta  Unique account identifier.
     * @param titular       Name of account holder.
     * @param saldo         Initial deposit balance.
     * @param limiteCredito Overdraft credit limit.
     */
    public CuentaBancaria(String numeroCuenta, String titular, double saldo, Double limiteCredito) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldo;
        this.limiteCredito = (limiteCredito != null) ? limiteCredito : 0.0;
    }

    /**
     * Deposits an amount into the account.
     * Validates that the amount is strictly positive (> 0).
     *
     * @param monto Amount to deposit.
     * @return true if deposit succeeded; false if amount was non-positive.
     */
    public boolean depositar(double monto) {
        if (monto > 0) {
            this.saldo += monto;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Withdraws an amount from the account.
     * Validates that the amount is positive and that total available funds
     * (balance + credit limit) are sufficient.
     *
     * @param monto Amount to withdraw.
     * @return true if withdrawal succeeded; false otherwise.
     */
    public boolean retirar(double monto) {
        if (monto <= 0) {
            return false;
        }

        double availableCredit = (this.limiteCredito != null) ? this.limiteCredito : 0.0;
        double totalFunds = this.saldo + availableCredit;

        if (totalFunds >= monto) {
            this.saldo -= monto;
            return true;
        } else {
            return false;
        }
    }

    // --- Getters and Setters ---

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(Double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }
}
