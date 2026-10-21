package com.taller.arenarpg;

import java.util.List;

/**
 * Representa el tablero bidimensional 5x5 de la arena de combate.
 */
public class Tablero {
    public static final int FILAS = 5;
    public static final int COLUMNAS = 5;

    public static final char CELDA_VACIA = '.';
    public static final char SIMBOLO_GUERRERO = 'G';
    public static final char SIMBOLO_MAGO = 'M';
    public static final char SIMBOLO_ENEMIGO = 'E';
    public static final char SIMBOLO_COFRE = 'C';

    private char[][] matriz;
    private final int cofreFila = 2;
    private final int cofreColumna = 2;
    private boolean cofreAbierto = false;

    public Tablero() {
        this.matriz = new char[FILAS][COLUMNAS];
        limpiarTablero();
    }

    /**
     * Reinicia todas las celdas a vacías y coloca el cofre en (2,2).
     */
    public void limpiarTablero() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                matriz[i][j] = CELDA_VACIA;
            }
        }
        if (!cofreAbierto) {
            matriz[cofreFila][cofreColumna] = SIMBOLO_COFRE;
        }
    }

    /**
     * Sincroniza la matriz 2D con las posiciones actuales de los personajes vivos.
     * Previene colisiones visuales y excepciones de límites.
     * @param heroes Lista dinámica de héroes.
     * @param enemigos Lista dinámica de enemigos.
     */
    public void sincronizar(List<Personaje> heroes, List<Personaje> enemigos) {
        limpiarTablero();

        // Posicionar enemigos vivos
        for (Personaje enemigo : enemigos) {
            if (enemigo.estaVivo() && esPosicionValida(enemigo.getPosicionX(), enemigo.getPosicionY())) {
                matriz[enemigo.getPosicionX()][enemigo.getPosicionY()] = SIMBOLO_ENEMIGO;
            }
        }

        // Posicionar héroes vivos (sobrescriben visualmente si coinciden con cofre)
        for (Personaje heroe : heroes) {
            if (heroe.estaVivo() && esPosicionValida(heroe.getPosicionX(), heroe.getPosicionY())) {
                if (heroe instanceof Guerrero) {
                    matriz[heroe.getPosicionX()][heroe.getPosicionY()] = SIMBOLO_GUERRERO;
                } else if (heroe instanceof Mago) {
                    matriz[heroe.getPosicionX()][heroe.getPosicionY()] = SIMBOLO_MAGO;
                }
            }
        }
    }

    /**
     * Renderizado del tablero en consola con bucles anidados según especificación de la Sección 3.2.
     * @param matriz Matriz bidimensional a mostrar.
     */
    public void mostrarTablero(char[][] matriz) {
        System.out.println("      0    1    2    3    4  (Columna)");
        for (int i = 0; i < matriz.length; i++) {
            System.out.print("Fila " + i + " ");
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print("[" + matriz[i][j] + "] ");
            }
            System.out.println(); // Salto de línea por cada fila
        }
        System.out.println("Leyenda: [G] Guerrero | [M] Mago | [E] Enemigo | [C] Cofre | [.] Celda Vacía\n");
    }

    public void mostrarTablero() {
        mostrarTablero(this.matriz);
    }

    /**
     * Valida que las coordenadas estén dentro del rango permitido (0 a 4).
     * Evita IndexOutOfBoundsException.
     * @param fila Fila en la matriz.
     * @param columna Columna en la matriz.
     * @return true si la posición es válida.
     */
    public boolean esPosicionValida(int fila, int columna) {
        return fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS;
    }

    public boolean esPosicionCofre(int fila, int columna) {
        return fila == cofreFila && columna == cofreColumna;
    }

    public void setCofreAbierto(boolean cofreAbierto) {
        this.cofreAbierto = cofreAbierto;
    }

    public boolean isCofreAbierto() {
        return cofreAbierto;
    }

    public char[][] getMatriz() {
        return matriz;
    }

    public int getCofreFila() {
        return cofreFila;
    }

    public int getCofreColumna() {
        return cofreColumna;
    }
}
