package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.modelo.Personaje;
import co.edu.puj.rpg.modelo.enemigo.Enemigo;

/**
 * Clase de verificación y pruebas automatizadas de las invariantes de encapsulamiento
 * y casos de prueba descritos en la Sección 6 del taller.
 */
public class PruebasEncapsulamiento {

    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("   SUITE DE VERIFICACIÓN DE ENCAPSULAMIENTO Y REGLAS      ");
        System.out.println("==========================================================\n");

        int pruebasExitosas = 0;
        int pruebasTotales = 0;

        // ------------------------------------------------------------------
        // PRUEBA 1: Validación de Límites en setPuntosVida (Caso C)
        // ------------------------------------------------------------------
        pruebasTotales++;
        System.out.println("[Prueba 1] Verificación de setPuntosVida con límites:");
        Personaje p1 = new Personaje("Lancelot", 100, 20, 50.0);

        // Intento de desbordamiento por arriba
        p1.setPuntosVida(999999);
        boolean p1_maxValido = (p1.getPuntosVida() == 100 && p1.isEstaVivo());

        // Intento de valor negativo
        p1.setPuntosVida(-50);
        boolean p1_minValido = (p1.getPuntosVida() == 0 && !p1.isEstaVivo());

        // Asignación de valor intermedio
        p1.setPuntosVida(60);
        boolean p1_intermedioValido = (p1.getPuntosVida() == 60 && p1.isEstaVivo());

        if (p1_maxValido && p1_minValido && p1_intermedioValido) {
            System.out.println("  ✔ Correcto: Puntos de vida acotados a [0, vidaMaxima] y estado 'estaVivo' coherente.");
            pruebasExitosas++;
        } else {
            System.out.println("  ❌ Falló: Puntos de vida no respetaron límites.");
        }

        // ------------------------------------------------------------------
        // PRUEBA 2: Validación de Transacciones Negativas de Oro (Reto 1)
        // ------------------------------------------------------------------
        pruebasTotales++;
        System.out.println("\n[Prueba 2] Verificación de setOro contra saldos negativos:");
        Personaje p2 = new Personaje("Thorin", 120, 25, 30.0);

        // Intento de transacción fraudulenta (saldo negativo)
        boolean transaccionInvalida = p2.setOro(-15.0);
        boolean saldoNoModificado = (p2.getOro() == 30.0);

        // Transacción válida
        boolean transaccionValida = p2.setOro(55.0);
        boolean saldoActualizado = (p2.getOro() == 55.0);

        if (!transaccionInvalida && saldoNoModificado && transaccionValida && saldoActualizado) {
            System.out.println("  ✔ Correcto: setOro rechaza valores negativos e impide corrupción de saldo.");
            pruebasExitosas++;
        } else {
            System.out.println("  ❌ Falló: setOro permitió transacciones con números negativos.");
        }

        // ------------------------------------------------------------------
        // PRUEBA 3: Restricción de Nivel Mínimo en setNivel
        // ------------------------------------------------------------------
        pruebasTotales++;
        System.out.println("\n[Prueba 3] Verificación de setNivel impidiendo niveles < 1:");
        Personaje p3 = new Personaje("Legolas", 90, 30, 40.0);

        p3.setNivel(0);
        boolean nivelCeroRechazado = (p3.getNivel() == 1);

        p3.setNivel(-10);
        boolean nivelNegativoRechazado = (p3.getNivel() == 1);

        p3.setNivel(3);
        boolean nivelValidoAceptado = (p3.getNivel() == 3);

        if (nivelCeroRechazado && nivelNegativoRechazado && nivelValidoAceptado) {
            System.out.println("  ✔ Correcto: setNivel mantuvo el nivel válido y rechazó valores < 1.");
            pruebasExitosas++;
        } else {
            System.out.println("  ❌ Falló: setNivel permitió asignar valores menores a 1.");
        }

        // ------------------------------------------------------------------
        // PRUEBA 4: Encapsulamiento del Enemigo (Subpaquete cruzado)
        // ------------------------------------------------------------------
        pruebasTotales++;
        System.out.println("\n[Prueba 4] Verificación de integridad en la entidad Enemigo:");
        Enemigo orco = new Enemigo("Goblin Explorador", 50, 10, 20.0, 40);

        orco.recibirDano(100); // Excede la vida
        boolean orcoDerrotado = (orco.getPuntosVida() == 0 && !orco.isEstaVivo());

        if (orcoDerrotado) {
            System.out.println("  ✔ Correcto: El enemigo actualiza su salud a 0 y estaVivo a false al morir.");
            pruebasExitosas++;
        } else {
            System.out.println("  ❌ Falló: Enemigo no manejó correctamente el daño mortal.");
        }

        // ------------------------------------------------------------------
        // PRUEBA 5: Reto 2 - Experiencia y Subida Automática de Nivel
        // ------------------------------------------------------------------
        pruebasTotales++;
        System.out.println("\n[Prueba 5] Verificación del Sistema de Experiencia y Progreso:");
        Personaje p5 = new Personaje("Gimli", 100, 20, 10.0);
        int nivelInicial = p5.getNivel();
        int vidaMaxInicial = p5.getVidaMaxima();

        p5.ganarExperiencia(150); // Supera el umbral de 100 EXP

        boolean subioNivel = (p5.getNivel() > nivelInicial);
        boolean aumentoVida = (p5.getVidaMaxima() > vidaMaxInicial);

        if (subioNivel && aumentoVida) {
            System.out.println("  ✔ Correcto: El personaje subió de nivel y actualizó sus estadísticas base.");
            pruebasExitosas++;
        } else {
            System.out.println("  ❌ Falló: El sistema de experiencia no incrementó el nivel.");
        }

        // ------------------------------------------------------------------
        // RESUMEN
        // ------------------------------------------------------------------
        System.out.println("\n==========================================================");
        System.out.printf("RESULTADO: %d / %d pruebas superadas exitosamente.%n", pruebasExitosas, pruebasTotales);
        System.out.println("==========================================================");
    }
}
