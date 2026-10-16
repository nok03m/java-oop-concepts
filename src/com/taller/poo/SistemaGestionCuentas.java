package com.taller.poo;

import java.util.Scanner;

/**
 * Reto Integrador: Console Account Management System.
 * Implements an interactive console application using Scanner, do-while loop,
 * switch-case control structure, object array traversal, input validation,
 * and formatted output using StringBuilder and System.out.printf.
 */
public class SistemaGestionCuentas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Fixed-size collection of 3 bank accounts pre-populated with instances
        CuentaBancaria[] cuentas = new CuentaBancaria[3];
        cuentas[0] = new CuentaBancaria("1001", "Carlos Pérez", 1500.00, 300.00);
        cuentas[1] = new CuentaBancaria("1002", "Ana Gómez", 850.50, 150.00);
        cuentas[2] = new CuentaBancaria("1003", "John Doe", 2200.75, 500.00);

        int opcion = 0;

        // Continuous execution loop
        do {
            System.out.printf("%n=================================================%n");
            System.out.printf("       BANK ACCOUNT MANAGEMENT SYSTEM            %n");
            System.out.printf("=================================================%n");
            System.out.printf("1. Deposit into an account%n");
            System.out.printf("2. Withdraw from an account%n");
            System.out.printf("3. View consolidated system report%n");
            System.out.printf("4. Exit%n");
            System.out.printf("-------------------------------------------------%n");
            System.out.printf("Enter your choice (1-4): ");

            String inputLine = scanner.nextLine().trim();
            try {
                opcion = Integer.parseInt(inputLine);
            } catch (NumberFormatException e) {
                System.out.printf("[ERROR] Invalid selection. Please enter a number between 1 and 4.%n");
                continue;
            }

            // Menu routing using switch
            switch (opcion) {
                case 1: {
                    // Option 1: Deposit
                    System.out.printf("%n--- DEPOSIT FUNDS ---%n");
                    System.out.printf("Enter account number: ");
                    String numCuenta = scanner.nextLine().trim();

                    CuentaBancaria cuenta = buscarCuenta(cuentas, numCuenta);
                    if (cuenta == null) {
                        System.out.printf("[ERROR] Account with number '%s' was not found in the system.%n", numCuenta);
                        break;
                    }

                    System.out.printf("Enter deposit amount: ");
                    double monto = leerMontoPositivo(scanner);
                    if (monto <= 0) {
                        System.out.printf("[ERROR] Deposit aborted: amount must be strictly greater than zero.%n");
                        break;
                    }

                    boolean exito = cuenta.depositar(monto);
                    if (exito) {
                        System.out.printf("[SUCCESS] Deposited $%.2f into account '%s' (Holder: %s). New balance: $%.2f%n",
                                monto, cuenta.getNumeroCuenta(), cuenta.getTitular(), cuenta.getSaldo());
                    } else {
                        System.out.printf("[ERROR] Deposit failed due to invalid transaction parameters.%n");
                    }
                    break;
                }

                case 2: {
                    // Option 2: Withdraw
                    System.out.printf("%n--- WITHDRAW FUNDS ---%n");
                    System.out.printf("Enter account number: ");
                    String numCuenta = scanner.nextLine().trim();

                    CuentaBancaria cuenta = buscarCuenta(cuentas, numCuenta);
                    if (cuenta == null) {
                        System.out.printf("[ERROR] Account with number '%s' was not found in the system.%n", numCuenta);
                        break;
                    }

                    System.out.printf("Enter withdrawal amount: ");
                    double monto = leerMontoPositivo(scanner);
                    if (monto <= 0) {
                        System.out.printf("[ERROR] Withdrawal aborted: amount must be strictly greater than zero.%n");
                        break;
                    }

                    boolean exito = cuenta.retirar(monto);
                    if (exito) {
                        System.out.printf("[SUCCESS] Withdrew $%.2f from account '%s'. New balance: $%.2f%n",
                                monto, cuenta.getNumeroCuenta(), cuenta.getSaldo());
                    } else {
                        double limite = (cuenta.getLimiteCredito() != null) ? cuenta.getLimiteCredito() : 0.0;
                        double fondosTotales = cuenta.getSaldo() + limite;
                        System.out.printf("[ERROR] Insufficient funds! Requested: $%.2f | Total Available (Balance + Credit): $%.2f%n",
                                monto, fondosTotales);
                    }
                    break;
                }

                case 3: {
                    // Option 3: Consolidated Report
                    System.out.printf("%n========================================================================%n");
                    System.out.printf("                      CONSOLIDATED SYSTEM REPORT                        %n");
                    System.out.printf("========================================================================%n");

                    StringBuilder sb = new StringBuilder();
                    double saldoTotal = 0.0;
                    double creditoTotal = 0.0;

                    for (int i = 0; i < cuentas.length; i++) {
                        CuentaBancaria c = cuentas[i];
                        saldoTotal += c.getSaldo();
                        double cred = (c.getLimiteCredito() != null) ? c.getLimiteCredito() : 0.0;
                        creditoTotal += cred;

                        sb.append(String.format("  [%d] Account: %-6s | Holder: %-15s | Balance: $%10.2f | Credit: $%8.2f%n",
                                (i + 1),
                                c.getNumeroCuenta(),
                                c.getTitular(),
                                c.getSaldo(),
                                cred));
                    }

                    // Display details accumulated in StringBuilder
                    System.out.printf("%s", sb.toString());
                    System.out.printf("------------------------------------------------------------------------%n");
                    System.out.printf("  Total Accounts Managed: %d%n", cuentas.length);
                    System.out.printf("  Total Accumulated Balance : $%.2f%n", saldoTotal);
                    System.out.printf("  Total Extended Credit     : $%.2f%n", creditoTotal);
                    System.out.printf("  Total Liquidity in System : $%.2f%n", (saldoTotal + creditoTotal));
                    System.out.printf("========================================================================%n");
                    break;
                }

                case 4: {
                    System.out.printf("%n[INFO] Thank you for using the Bank Account Management System. Goodbye!%n");
                    break;
                }

                default: {
                    System.out.printf("[ERROR] Unrecognized option '%d'. Please choose an option from 1 to 4.%n", opcion);
                    break;
                }
            }

        } while (opcion != 4);

        scanner.close();
    }

    /**
     * Helper method to search for a bank account in an array by its account number.
     * Uses a linear search loop.
     *
     * @param cuentas      The array of accounts.
     * @param numeroCuenta The account number to look up.
     * @return The matching CuentaBancaria object, or null if not found.
     */
    private static CuentaBancaria buscarCuenta(CuentaBancaria[] cuentas, String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.isEmpty()) {
            return null;
        }

        for (CuentaBancaria c : cuentas) {
            if (c != null && c.getNumeroCuenta().equalsIgnoreCase(numeroCuenta)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Reads a double amount from the scanner with safe validation against format exceptions.
     *
     * @param scanner The active Scanner.
     * @return The entered double value, or -1.0 if the input was not a valid number.
     */
    private static double leerMontoPositivo(Scanner scanner) {
        String input = scanner.nextLine().trim();
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }
}
