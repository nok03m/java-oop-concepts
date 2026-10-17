package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.modelo.Personaje;
import co.edu.puj.rpg.modelo.enemigo.Enemigo;

import java.util.Scanner;

/**
 * Clase principal que gestiona el flujo de juego, el bucle de batalla en consola,
 * el menú interactivo, la tienda financiera y el registro formateado de eventos.
 */
public class Main {

    private static final double PRECIO_POCION_GRANDE = 30.0;
    private static final int CURACION_POCION = 40;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        imprimirEncabezado();

        // Creación del héroe y del enemigo inicializando con constructores validados
        Personaje heroe = new Personaje("Aragorn", 120, 25, 75.0);
        Enemigo enemigo = new Enemigo("Orco Berserker", 90, 18, 45.0, 120);

        System.out.printf("%n=== ¡EL COMBATE HA COMENZADO EN LA ARENA DE HÉROES! ===%n");
        System.out.printf("Héroe: %s [Nivel %d] | Enemigo: %s%n%n",
                heroe.getNombre(), heroe.getNivel(), enemigo.getNombre());

        boolean haHuido = false;
        int ronda = 1;

        // Bucle principal del combate (Sección 4.1: do-while)
        do {
            StringBuilder registroRonda = new StringBuilder();
            registroRonda.append(String.format("--- REGISTRO DE EVENTOS (RONDA %d) ---%n", ronda));

            mostrarEstadoRapido(heroe, enemigo);
            mostrarMenuOpciones();

            System.out.print("Seleccione una acción [1-5]: ");
            String entrada = scanner.nextLine().trim();

            int opcion = -1;
            // Validación de entrada mediante if-else para evitar caídas
            if (entrada.matches("\\d+")) {
                opcion = Integer.parseInt(entrada);
            } else {
                System.out.println(">> [ERROR]: Por favor, ingrese un número entero válido.");
                continue;
            }

            boolean turnoConsumido = false;

            // Selección de acciones con switch (Sección 4.1)
            switch (opcion) {
                case 1:
                    // 1. Atacar
                    int vidaAntesEnemigo = enemigo.getPuntosVida();
                    heroe.atacar(enemigo);
                    int danoInfligido = vidaAntesEnemigo - enemigo.getPuntosVida();

                    registroRonda.append(String.format("- [%s] infligió %d de daño a [%s]. Salud restante del enemigo: %d/%d.%n",
                            heroe.getNombre(), danoInfligido, enemigo.getNombre(), enemigo.getPuntosVida(), enemigo.getVidaMaxima()));

                    if (!enemigo.isEstaVivo()) {
                        registroRonda.append(String.format("☠ ¡[%s] ha sido derrotado!%n", enemigo.getNombre()));
                    }
                    turnoConsumido = true;
                    break;

                case 2:
                    // 2. Usar Poción
                    int vidaAntesHeroe = heroe.getPuntosVida();
                    heroe.usarPocion(CURACION_POCION);
                    int curacion = heroe.getPuntosVida() - vidaAntesHeroe;

                    if (curacion > 0) {
                        registroRonda.append(String.format("- [%s] consumió una poción y restauró +%d de salud (Salud actual: %d/%d).%n",
                                heroe.getNombre(), curacion, heroe.getPuntosVida(), heroe.getVidaMaxima()));
                        turnoConsumido = true;
                    }
                    break;

                case 3:
                    // 3. Ver Estado detallado (Salida formateada con printf)
                    mostrarEstadoCompleto(heroe, enemigo);
                    // Consultar estado no consume turno de combate
                    break;

                case 4:
                    // 4. Submenú Tienda (Reto 1: Sistema de Tienda y Gestión Financiera)
                    gestionarTienda(heroe, scanner, registroRonda);
                    break;

                case 5:
                    // 5. Huir de la arena
                    System.out.printf("%n[%s] decide retirarse estratégicamente del campo de batalla.%n", heroe.getNombre());
                    registroRonda.append(String.format("- [%s] ha huido de la arena de combate.%n", heroe.getNombre()));
                    haHuido = true;
                    break;

                default:
                    System.out.printf(">> [ERROR]: Opción %d no válida. Seleccione entre 1 y 5.%n", opcion);
                    break;
            }

            // Si el héroe realizó una acción que consume turno y el enemigo sigue con vida, el enemigo contraataca
            if (turnoConsumido && enemigo.isEstaVivo() && !haHuido) {
                System.out.println("\n--- Turno del Enemigo ---");
                int vidaHeroeAntes = heroe.getPuntosVida();
                String resultadoAtaque = enemigo.atacar(heroe);
                System.out.println(resultadoAtaque);

                int danoRecibido = vidaHeroeAntes - heroe.getPuntosVida();
                registroRonda.append(String.format("- [%s] contraatacó infligiendo %d de daño a [%s]. Salud del héroe: %d/%d.%n",
                        enemigo.getNombre(), danoRecibido, heroe.getNombre(), heroe.getPuntosVida(), heroe.getVidaMaxima()));

                if (!heroe.isEstaVivo()) {
                    registroRonda.append(String.format("☠ ¡[%s] ha caído en combate!%n", heroe.getNombre()));
                }
            }

            // Sección 4.2: Impresión consolidada del historial acumulado en el StringBuilder al finalizar la ronda
            if (turnoConsumido || haHuido) {
                System.out.println();
                System.out.print(registroRonda.toString());
                ronda++;
            }

        } while (heroe.isEstaVivo() && enemigo.isEstaVivo() && !haHuido);

        // Despeje y resolución final del combate
        System.out.println("\n========================================================");
        System.out.println("                 FIN DEL COMBATE                        ");
        System.out.println("========================================================");

        if (!enemigo.isEstaVivo()) {
            System.out.printf("🎉 ¡VICTORIA! [%s] ha derrotado a [%s].%n", heroe.getNombre(), enemigo.getNombre());

            // Recompensas financieras y de experiencia (Reto 2)
            double nuevoOro = heroe.getOro() + enemigo.getOroRecompensa();
            heroe.setOro(nuevoOro);
            System.out.printf("💰 Recompensa obtenida: +%.2f de oro (Saldo total: %.2f).%n",
                    enemigo.getOroRecompensa(), heroe.getOro());

            heroe.ganarExperiencia(enemigo.getExpRecompensa());
        } else if (!heroe.isEstaVivo()) {
            System.out.printf("💀 DERROTA: [%s] ha perecido en combate ante [%s]. Fin del juego.%n",
                    heroe.getNombre(), enemigo.getNombre());
        } else {
            System.out.printf("🏃 ESCAPE: [%s] abandonó el combate de forma segura.%n", heroe.getNombre());
        }

        System.out.println("\nEstado definitivo del héroe tras la expedición:");
        mostrarEstadoCompleto(heroe, enemigo);

        scanner.close();
    }

    // ==========================================
    // MÉTODOS AUXILIARES Y FORMATO (PRINTF)
    // ==========================================

    private static void imprimirEncabezado() {
        System.out.println("========================================================");
        System.out.println("           SIMULADOR DE BATALLA / ARENA RPG             ");
        System.out.println("      Módulo de Encapsulamiento y Modificadores         ");
        System.out.println("========================================================");
    }

    private static void mostrarMenuOpciones() {
        System.out.println("\n--- ACCIONES DISPONIBLES ---");
        System.out.println("1. Atacar al enemigo");
        System.out.println("2. Usar poción de curación");
        System.out.println("3. Ver estado de combatientes");
        System.out.println("4. Entrar a la tienda (Reto 1)");
        System.out.println("5. Huir del combate");
    }

    /**
     * Muestra una vista resumida de los combatientes usando printf con formato estricto.
     */
    private static void mostrarEstadoRapido(Personaje heroe, Enemigo enemigo) {
        System.out.printf("%n[COMBATIENTES] %s: %d/%d HP | %s: %d/%d HP%n",
                heroe.getNombre(), heroe.getPuntosVida(), heroe.getVidaMaxima(),
                enemigo.getNombre(), enemigo.getPuntosVida(), enemigo.getVidaMaxima());
    }

    /**
     * Muestra el estado exhaustivo de los combatientes utilizando los especificadores
     * %s, %d y %.2f requeridos en la Sección 4.2.
     */
    private static void mostrarEstadoCompleto(Personaje heroe, Enemigo enemigo) {
        System.out.println("\n--------------------------------------------------------");
        System.out.println("               ESTADO DETALLADO DE COMBATE              ");
        System.out.println("--------------------------------------------------------");

        System.out.printf("HÉROE: %-15s | Estado: %-10s%n",
                heroe.getNombre(), heroe.isEstaVivo() ? "VIVO" : "DERROTADO");
        System.out.printf("  - Nivel:             %d%n", heroe.getNivel());
        System.out.printf("  - Puntos de Vida:    %d / %d%n", heroe.getPuntosVida(), heroe.getVidaMaxima());
        System.out.printf("  - Puntos de Ataque:  %d%n", heroe.getPuntosAtaque());
        System.out.printf("  - Oro acumulado:     %.2f monedas%n", heroe.getOro());
        System.out.printf("  - Pociones en bolsa: %d unidad(es)%n", heroe.getPociones());
        System.out.printf("  - Experiencia (EXP): %d / %d%n", heroe.getExperiencia(), heroe.getExpSiguienteNivel());

        System.out.println("--------------------------------------------------------");
        System.out.printf("ENEMIGO: %-13s | Estado: %-10s%n",
                enemigo.getNombre(), enemigo.isEstaVivo() ? "VIVO" : "DERROTADO");
        System.out.printf("  - Puntos de Vida:    %d / %d%n", enemigo.getPuntosVida(), enemigo.getVidaMaxima());
        System.out.printf("  - Puntos de Ataque:  %d%n", enemigo.getPuntosAtaque());
        System.out.printf("  - Oro Recompensa:    %.2f monedas%n", enemigo.getOroRecompensa());
        System.out.printf("  - EXP Recompensa:    %d puntos%n", enemigo.getExpRecompensa());
        System.out.println("--------------------------------------------------------");
    }

    /**
     * Implementación del Reto 1: Submenú de Tienda y Gestión Financiera.
     * Invoca explícitamente el método mutador setOro() validando la transacción.
     */
    private static void gestionarTienda(Personaje heroe, Scanner scanner, StringBuilder log) {
        System.out.println("\n========================================================");
        System.out.println("               TIENDA DE PROVISIONES                    ");
        System.out.println("========================================================");
        System.out.printf("Saldo actual de [%s]: %.2f de oro%n", heroe.getNombre(), heroe.getOro());
        System.out.printf("1. Comprar Poción de Salud Grande (Costo: %.2f de oro | +40 HP)%n", PRECIO_POCION_GRANDE);
        System.out.println("2. Volver al combate");
        System.out.print("Seleccione una opción: ");

        String entrada = scanner.nextLine().trim();
        if ("1".equals(entrada)) {
            double nuevoSaldo = heroe.getOro() - PRECIO_POCION_GRANDE;
            // Invocación obligatoria al método mutador setOro() con control de saldo
            boolean exitoTransaccion = heroe.setOro(nuevoSaldo);

            if (exitoTransaccion) {
                heroe.agregarPocion();
                System.out.printf("✔ ¡Compra exitosa! Se añadió 1 poción a tu inventario. Saldo restante: %.2f de oro.%n",
                        heroe.getOro());
                log.append(String.format("- [%s] compró 1 poción en la tienda por %.2f de oro (Saldo: %.2f).%n",
                        heroe.getNombre(), PRECIO_POCION_GRANDE, heroe.getOro()));
            } else {
                System.out.printf("❌ Transacción rechazada: Oro insuficiente (Se requieren %.2f, pero posees %.2f).%n",
                        PRECIO_POCION_GRANDE, heroe.getOro());
            }
        } else if ("2".equals(entrada)) {
            System.out.println("Regresando a la arena de batalla...");
        } else {
            System.out.println("Opción inválida. Regresando a la arena...");
        }
    }
}
