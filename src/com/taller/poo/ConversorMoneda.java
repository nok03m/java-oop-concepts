package com.taller.poo;

/**
 * Utility class providing static currency conversion and formatting routines.
 * Demonstrates the utility class pattern: private constructor to disallow instantiation,
 * and pure static methods that operate without instance state.
 */
public final class ConversorMoneda {

    /**
     * Explicit private constructor to prevent instantiation of this utility class.
     */
    private ConversorMoneda() {
        throw new UnsupportedOperationException("ConversorMoneda is a utility class and cannot be instantiated.");
    }

    /**
     * Converts a USD amount to EUR using the provided exchange rate.
     * Validates that both the amount and exchange rate are strictly positive.
     *
     * @param montoDolares Amount in USD.
     * @param tasaCambio   Exchange rate (EUR per USD).
     * @return Converted amount in EUR, or 0.0 if either input is less than or equal to zero.
     */
    public static double convertirDolaresAEuros(double montoDolares, double tasaCambio) {
        if (montoDolares <= 0 || tasaCambio <= 0) {
            return 0.0;
        }
        return montoDolares * tasaCambio;
    }

    /**
     * Formats a monetary amount into a two-decimal currency representation.
     *
     * @param monto Numerical amount.
     * @return Formatted monetary string, e.g., "$1250.75".
     */
    public static String formatearMoneda(double monto) {
        return String.format("$%.2f", monto);
    }
}
