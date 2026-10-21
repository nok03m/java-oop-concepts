package com.taller.arenarpg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Motor central de combate y simulación de la Arena RPG.
 * Controla el ciclo do-while, los turnos polimórficos, las condiciones de victoria y derrota,
 * y la integración con el tablero y el inventario.
 */
public class ArenaJuego {
    private List<Personaje> heroes;
    private List<Personaje> enemigos;
    private Inventario<Pocion> inventario;
    private Tablero tablero;
    private Scanner scanner;
    private int turnoNumero;
    private boolean juegoTerminado;

    public ArenaJuego() {
        this(new Scanner(System.in));
    }

    public ArenaJuego(Scanner scanner) {
        this.scanner = scanner;
        this.heroes = new ArrayList<>();
        this.enemigos = new ArrayList<>();
        this.inventario = new Inventario<>();
        this.tablero = new Tablero();
        this.turnoNumero = 0;
        this.juegoTerminado = false;
        inicializarJuego();
    }

    /**
     * Inicializa los combatientes, el inventario genérico y sincroniza el tablero 2D.
     */
    private void inicializarJuego() {
        // Inicialización de Héroes según especificación
        Guerrero thorin = new Guerrero("Thorin", 120, 18, 0, 0, 10);
        Mago gandalf = new Mago("Gandalf", 80, 25, 1, 1, 50);

        heroes.add(thorin);
        heroes.add(gandalf);

        // Inicialización de Enemigos hostiles
        enemigos.add(new Enemigo("Orco Explorador", 50, 12, 0, 4, 3));
        enemigos.add(new Enemigo("Goblin Saqueador", 40, 10, 4, 0, 2));
        enemigos.add(new Enemigo("Jefe Orco", 85, 18, 4, 4, 5));

        // Inicialización del Inventario Genérico Inventario<Pocion>
        inventario.agregar(new Pocion("Poción de Salud Mayor", 45, Pocion.Tipo.VIDA));
        inventario.agregar(new Pocion("Poción de Salud Menor", 25, Pocion.Tipo.VIDA));
        inventario.agregar(new Pocion("Elixir de Maná Restaurador", 30, Pocion.Tipo.MANA));

        // Sincronizar estado inicial del tablero
        tablero.sincronizar(heroes, enemigos);
    }

    /**
     * Ciclo principal del combate (do-while) requerido en la Sección 4.1.
     */
    public void ejecutar() {
        System.out.println("=====================================================================");
        System.out.println("        BIENVENIDO A LA ARENA RPG (POO AVANZADA & GENÉRICOS)         ");
        System.out.println("=====================================================================");
        System.out.println("Objetivo: Elimina a todos los enemigos y alcanza el Cofre [C] en (2,2).\n");

        do {
            turnoNumero++;
            System.out.println("\n=====================================================================");
            System.out.println("                          TURNO #" + turnoNumero);
            System.out.println("=====================================================================");

            // 1. Mostrar estado del tablero y de las unidades
            mostrarEstadoGeneral();

            // 2. Turno del Jugador
            turnoJugador();

            // Limpieza de bajas enemigas
            limpiarBajas();
            tablero.sincronizar(heroes, enemigos);

            // Verificar si el juego termina tras la acción del jugador
            if (evaluarFinDePartida()) {
                break;
            }

            // 3. Turno de la IA Enemiga
            turnoEnemigos();

            // Limpieza de bajas de héroes
            limpiarBajas();
            tablero.sincronizar(heroes, enemigos);

            // Verificar si el juego termina tras la acción enemiga
            if (evaluarFinDePartida()) {
                break;
            }

        } while (!juegoTerminado);

        System.out.println("\n[FIN DEL JUEGO] Gracias por participar en la Arena RPG.");
    }

    /**
     * Muestra el mapa en consola y las estadísticas de salud y maná.
     */
    public void mostrarEstadoGeneral() {
        tablero.mostrarTablero();

        System.out.println("--- ESTADO DEL ESCUADRÓN (HÉROES) ---");
        for (Personaje heroe : heroes) {
            System.out.println("  • " + heroe);
        }

        System.out.println("\n--- ENEMIGOS EN LA ARENA ---");
        if (enemigos.isEmpty()) {
            System.out.println("  (No quedan enemigos activos)");
        } else {
            for (Personaje enemigo : enemigos) {
                System.out.println("  • " + enemigo);
            }
        }
        System.out.println("-------------------------------------\n");
    }

    /**
     * Turno interactivo del jugador usando bucle for-each polimórfico y menú switch.
     */
    private void turnoJugador() {
        System.out.println(">> INICIO DEL TURNO DE LOS HÉROES <<");

        // Creamos copia para iterar con seguridad
        List<Personaje> heroesActivos = new ArrayList<>(heroes);

        for (Personaje heroe : heroesActivos) {
            if (!heroe.estaVivo() || enemigos.isEmpty()) {
                continue;
            }

            System.out.println("\n👉 Es el turno de acción de: " + heroe.getNombre() + " (" + heroe.getClass().getSimpleName() + ")");
            boolean accionRealizada = false;

            while (!accionRealizada && heroe.estaVivo()) {
                System.out.println("Seleccione una acción para " + heroe.getNombre() + ":");
                System.out.println("  [1] Atacar a un enemigo");
                System.out.println("  [2] Usar Inventario (Pociones)");
                System.out.println("  [3] Mover en el Tablero 2D");
                System.out.println("  [4] Defenderse y esperar");
                System.out.print("Opción: ");

                String entrada = scanner.nextLine().trim();

                switch (entrada) {
                    case "1" -> accionRealizada = ejecutarAccionAtacar(heroe);
                    case "2" -> accionRealizada = ejecutarAccionInventario(heroe);
                    case "3" -> accionRealizada = ejecutarAccionMover(heroe);
                    case "4" -> {
                        System.out.println("🛡️ " + heroe.getNombre() + " adopta una postura defensiva.");
                        accionRealizada = true;
                    }
                    default -> System.out.println("⚠️ Opción no válida. Intente nuevamente.");
                }
            }
        }
    }

    /**
     * Menú para seleccionar el tipo de ataque y el objetivo polimórficamente.
     */
    private boolean ejecutarAccionAtacar(Personaje atacante) {
        if (enemigos.isEmpty()) {
            System.out.println("No quedan enemigos a los cuales atacar.");
            return false;
        }

        System.out.println("\nSeleccione el enemigo objetivo:");
        for (int i = 0; i < enemigos.size(); i++) {
            Personaje e = enemigos.get(i);
            System.out.println("  [" + (i + 1) + "] " + e.getNombre() + " (HP: " + e.getVida() + "/" + e.getVidaMaxima() + " | Pos: " + e.getPosicionX() + "," + e.getPosicionY() + ")");
        }
        System.out.println("  [0] Cancelar y regresar al menú anterior");
        System.out.print("Objetivo: ");

        int indiceObjetivo;
        try {
            indiceObjetivo = Integer.parseInt(scanner.nextLine().trim()) - 1;
        } catch (NumberFormatException ex) {
            System.out.println("⚠️ Entrada numérica no válida.");
            return false;
        }

        if (indiceObjetivo == -1) {
            return false;
        }

        if (indiceObjetivo < 0 || indiceObjetivo >= enemigos.size()) {
            System.out.println("⚠️ Selección de objetivo fuera de rango.");
            return false;
        }

        Personaje objetivo = enemigos.get(indiceObjetivo);

        System.out.println("\nElija el tipo de ofensiva:");
        System.out.println("  [1] Ataque Básico");
        System.out.println("  [2] Ataque Especial Polimórfico (realizarAtaqueEspecial)");
        System.out.println("  [3] Ataque en Modo Furia (Sobrecarga de atacar)");
        System.out.print("Tipo: ");

        String tipoAtaque = scanner.nextLine().trim();
        switch (tipoAtaque) {
            case "1" -> atacante.atacar(objetivo, false);
            case "2" -> atacante.realizarAtaqueEspecial(objetivo); // Invocación polimórfica
            case "3" -> atacante.atacar(objetivo, true); // Sobrecarga
            default -> {
                System.out.println("⚠️ Tipo de ataque desconocido. Se ejecuta ataque básico por defecto.");
                atacante.atacar(objetivo, false);
            }
        }

        return true;
    }

    /**
     * Permite al héroe consumir un ítem del Inventario<Pocion> genérico.
     */
    private boolean ejecutarAccionInventario(Personaje heroe) {
        if (inventario.estaVacio()) {
            System.out.println("🎒 El inventario está vacío. No hay pociones disponibles.");
            return false;
        }

        System.out.println("\n--- CONTENIDO DEL INVENTARIO GENÉRICO ---");
        for (int i = 0; i < inventario.tamano(); i++) {
            System.out.println("  [" + (i + 1) + "] " + inventario.obtener(i));
        }
        System.out.println("  [0] Cancelar");
        System.out.print("Seleccione una poción: ");

        int opcion;
        try {
            opcion = Integer.parseInt(scanner.nextLine().trim()) - 1;
        } catch (NumberFormatException ex) {
            System.out.println("⚠️ Entrada no válida.");
            return false;
        }

        if (opcion == -1) {
            return false;
        }

        if (opcion < 0 || opcion >= inventario.tamano()) {
            System.out.println("⚠️ Índice de poción inexistente.");
            return false;
        }

        Pocion pocionSeleccionada = inventario.remover(opcion);
        pocionSeleccionada.aplicar(heroe);
        return true;
    }

    /**
     * Desplaza al héroe en el tablero 2D validando límites contra IndexOutOfBoundsException.
     */
    private boolean ejecutarAccionMover(Personaje heroe) {
        System.out.println("\nPosición actual de " + heroe.getNombre() + ": Fila " + heroe.getPosicionX() + ", Columna " + heroe.getPosicionY());
        System.out.println("Seleccione dirección de movimiento:");
        System.out.println("  [W] Arriba   (Fila - 1)");
        System.out.println("  [S] Abajo    (Fila + 1)");
        System.out.println("  [A] Izquierda(Columna - 1)");
        System.out.println("  [D] Derecha  (Columna + 1)");
        System.out.println("  [0] Cancelar");
        System.out.print("Dirección: ");

        String direccion = scanner.nextLine().trim().toUpperCase();

        int deltaX = 0;
        int deltaY = 0;

        switch (direccion) {
            case "W" -> deltaX = -1;
            case "S" -> deltaX = 1;
            case "A" -> deltaY = -1;
            case "D" -> deltaY = 1;
            case "0" -> {
                return false;
            }
            default -> {
                System.out.println("⚠️ Dirección no reconocida.");
                return false;
            }
        }

        int nuevaFila = heroe.getPosicionX() + deltaX;
        int nuevaColumna = heroe.getPosicionY() + deltaY;

        // Validación de límites de la matriz 5x5
        if (!tablero.esPosicionValida(nuevaFila, nuevaColumna)) {
            System.out.println("🚫 Movimiento bloqueado: ¡Intentas salir de los límites de la arena (" + nuevaFila + "," + nuevaColumna + ")!");
            System.out.println("   (Previene IndexOutOfBoundsException según Sección 5.1)");
            return false;
        }

        heroe.setPosicionX(nuevaFila);
        heroe.setPosicionY(nuevaColumna);
        System.out.println("🚶 " + heroe.getNombre() + " se traslada a la casilla [" + nuevaFila + "," + nuevaColumna + "].");

        if (tablero.esPosicionCofre(nuevaFila, nuevaColumna)) {
            System.out.println("✨💎 ¡" + heroe.getNombre() + " ha llegado a la ubicación del Cofre del Tesoro [C]!");
            tablero.setCofreAbierto(true);
        }

        return true;
    }

    /**
     * Turno automático de los enemigos en la arena.
     */
    private void turnoEnemigos() {
        if (enemigos.isEmpty() || heroes.isEmpty()) {
            return;
        }

        System.out.println("\n>> TURNO DE LA INTELIGENCIA ARTIFICIAL HOSTIL <<");

        for (Personaje enemigo : new ArrayList<>(enemigos)) {
            if (!enemigo.estaVivo() || heroes.isEmpty()) {
                continue;
            }

            // Seleccionar héroe con vida disponible
            Personaje objetivo = heroes.get((int) (Math.random() * heroes.size()));

            System.out.println("\n👹 " + enemigo.getNombre() + " se enfoca en " + objetivo.getNombre() + "!");

            // 50% de probabilidad de ataque especial vs ataque regular
            if (Math.random() > 0.5) {
                enemigo.realizarAtaqueEspecial(objetivo);
            } else {
                enemigo.atacar(objetivo, false);
            }
        }
    }

    /**
     * Remueve los caídos de ambas colecciones mediante removeIf según Sección 2.2.
     */
    public void limpiarBajas() {
        int heroesAntes = heroes.size();
        heroes.removeIf(p -> !p.estaVivo());
        if (heroes.size() < heroesAntes) {
            System.out.println("ℹ️ [Colección] Se han actualizado las bajas de los héroes en tiempo real.");
        }

        int enemigosAntes = enemigos.size();
        enemigos.removeIf(p -> !p.estaVivo());
        if (enemigos.size() < enemigosAntes) {
            System.out.println("ℹ️ [Colección] Se han removido los enemigos eliminados de la lista.");
        }
    }

    /**
     * Evalúa las condiciones de victoria y derrota especificadas en la Sección 4.2.
     * @return true si la partida debe finalizar.
     */
    public boolean evaluarFinDePartida() {
        // Condición de Derrota:
        // - Suma total de vida de héroes <= 0 o lista heroes.isEmpty() == true
        int vidaTotalHeroes = heroes.stream().mapToInt(Personaje::getVida).sum();
        if (heroes.isEmpty() || vidaTotalHeroes <= 0) {
            System.out.println("\n☠️☠️☠️ ==================== DERROTA ==================== ☠️☠️☠️");
            System.out.println("Todos los héroes han caído en batalla. La oscuridad se apodera de la arena.");
            System.out.println("===============================================================");
            juegoTerminado = true;
            return true;
        }

        // Condición de Victoria:
        // - Todos los enemigos eliminados (enemigos.isEmpty() == true)
        // - El héroe principal alcanza la posición del cofre [C] en (2,2)
        boolean cofreAlcanzado = heroes.stream().anyMatch(h -> tablero.esPosicionCofre(h.getPosicionX(), h.getPosicionY()));

        if (enemigos.isEmpty() && cofreAlcanzado) {
            System.out.println("\n🏆🎉 ==================== ¡VICTORIA! ==================== 🎉🏆");
            System.out.println("¡Has derrotado a todas las amenazas de la arena y reclamado el Gran Tesoro del Cofre [C]!");
            System.out.println("¡Completaste satisfactoriamente el Reto Integrador!");
            System.out.println("===============================================================");
            juegoTerminado = true;
            return true;
        } else if (enemigos.isEmpty() && !cofreAlcanzado) {
            System.out.println("⚔️ ¡Todos los enemigos han sido abatidos!");
            System.out.println("🚩 Para completar la misión de Victoria, traslada a tus héroes a la casilla del cofre [C] en (2,2).");
        }

        return false;
    }

    public List<Personaje> getHeroes() {
        return heroes;
    }

    public List<Personaje> getEnemigos() {
        return enemigos;
    }

    public Inventario<Pocion> getInventario() {
        return inventario;
    }

    public Tablero getTablero() {
        return tablero;
    }
}
