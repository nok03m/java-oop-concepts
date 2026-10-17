package co.edu.puj.rpg.modelo;

import co.edu.puj.rpg.modelo.enemigo.Enemigo;

/**
 * Representa al héroe o personaje principal del simulador RPG.
 * Aplica rigurosamente el principio de encapsulamiento mediante atributos privados,
 * constructores con 'this', mutadores validados y una API pública segura.
 */
public class Personaje {

    // ==========================================
    // ATRIBUTOS PRIVADOS (ENCAPSULAMIENTO)
    // ==========================================
    private String nombre;
    private int puntosVida;
    private int vidaMaxima;
    private int puntosAtaque;
    private int nivel;
    private double oro;
    private boolean estaVivo;

    // Atributos adicionales para soporte de retos (Reto 1 y Reto 2)
    private int pociones;
    private int experiencia;
    private int expSiguienteNivel;

    // ==========================================
    // CONSTRUCTORES
    // ==========================================

    /**
     * Constructor completo que inicializa las propiedades centrales del personaje.
     * Utiliza 'this' para desambiguar parámetros y asegurar estado válido.
     *
     * @param nombre       Nombre del héroe.
     * @param vidaMaxima   Capacidad máxima de salud.
     * @param puntosAtaque Poder de ataque base.
     * @param oro          Monto de oro inicial.
     */
    public Personaje(String nombre, int vidaMaxima, int puntosAtaque, double oro) {
        this.nombre = nombre;
        this.vidaMaxima = Math.max(vidaMaxima, 1);
        this.puntosAtaque = Math.max(puntosAtaque, 1);
        this.nivel = 1;
        this.oro = Math.max(oro, 0.0);
        this.pociones = 2; // Pociones iniciales en el inventario
        this.experiencia = 0;
        this.expSiguienteNivel = 100;

        // Se inicializa la salud a la vida máxima mediante el setter para validar estado
        setPuntosVida(this.vidaMaxima);
    }

    /**
     * Constructor sobrecargado con valores predeterminados para una partida estándar.
     * Demuestra el encadenamiento de constructores usando 'this(...)'.
     *
     * @param nombre Nombre del personaje.
     */
    public Personaje(String nombre) {
        this(nombre, 100, 20, 50.0);
    }

    // ==========================================
    // MÉTODOS MUTADORES (SETTERS CON LÓGICA DE CONTROL)
    // ==========================================

    /**
     * Asigna los puntos de vida respetando las reglas de negocio:
     * - Si vida <= 0: vida = 0 y estaVivo = false.
     * - Si vida > vidaMaxima: vida = vidaMaxima y estaVivo = true.
     * - Si es valor intermedio: asigna vida y estaVivo = true.
     *
     * @param vida Nueva cantidad de vida.
     */
    public void setPuntosVida(int vida) {
        if (vida <= 0) {
            this.puntosVida = 0;
            this.estaVivo = false;
        } else if (vida > this.vidaMaxima) {
            this.puntosVida = this.vidaMaxima;
            this.estaVivo = true;
        } else {
            this.puntosVida = vida;
            this.estaVivo = true;
        }
    }

    /**
     * Asigna la cantidad de oro del personaje.
     * Si la operación produce un saldo negativo, la asignación es rechazada
     * para preservar la invariante del dominio.
     *
     * @param oro Nuevo saldo de oro propuesto.
     * @return true si la asignación fue exitosa; false si fue rechazada por saldo insuficiente.
     */
    public boolean setOro(double oro) {
        if (oro < 0.0) {
            System.out.printf("[ERROR DE TRANSACCIÓN]: Saldo insuficiente. No se puede asignar un valor de oro negativo (%.2f). Operación cancelada.%n", oro);
            return false;
        }
        this.oro = oro;
        return true;
    }

    /**
     * Asigna el nivel del personaje impidiendo cualquier valor inferior a 1.
     * Si el valor es inválido, conserva el valor actual.
     *
     * @param nivel Nuevo nivel sugerido.
     */
    public void setNivel(int nivel) {
        if (nivel < 1) {
            System.out.printf("[ADVERTENCIA]: Nivel inválido (%d). El nivel no puede ser menor a 1. Se mantiene en nivel %d.%n", nivel, this.nivel);
            return;
        }
        this.nivel = nivel;
    }

    public void setVidaMaxima(int vidaMaxima) {
        if (vidaMaxima > 0) {
            this.vidaMaxima = vidaMaxima;
            if (this.puntosVida > this.vidaMaxima) {
                this.puntosVida = this.vidaMaxima;
            }
        }
    }

    public void setPuntosAtaque(int puntosAtaque) {
        if (puntosAtaque > 0) {
            this.puntosAtaque = puntosAtaque;
        }
    }

    public void setPociones(int pociones) {
        if (pociones >= 0) {
            this.pociones = pociones;
        }
    }

    // ==========================================
    // MÉTODOS LECTORES (GETTERS PÚBLICOS)
    // ==========================================

    public String getNombre() {
        return this.nombre;
    }

    public int getPuntosVida() {
        return this.puntosVida;
    }

    public int getVidaMaxima() {
        return this.vidaMaxima;
    }

    public int getPuntosAtaque() {
        return this.puntosAtaque;
    }

    public int getNivel() {
        return this.nivel;
    }

    public double getOro() {
        return this.oro;
    }

    public boolean isEstaVivo() {
        return this.estaVivo;
    }

    public int getPociones() {
        return this.pociones;
    }

    public int getExperiencia() {
        return this.experiencia;
    }

    public int getExpSiguienteNivel() {
        return this.expSiguienteNivel;
    }

    // ==========================================
    // API PÚBLICA DE COMPORTAMIENTO
    // ==========================================

    /**
     * Ejecuta un ataque contra el enemigo especificado.
     * Incluye mecánica de golpe crítico con un 15% de probabilidad (Reto 2).
     *
     * @param enemigo Instancia del objetivo enemigo.
     */
    public void atacar(Enemigo enemigo) {
        if (!this.estaVivo) {
            System.out.printf("[%s] está derrotado y no puede atacar.%n", this.nombre);
            return;
        }

        // Reto 2: Probabilidad del 15% de golpe crítico
        boolean esCritico = Math.random() < 0.15;
        int dano = esCritico ? this.puntosAtaque * 2 : this.puntosAtaque;

        if (esCritico) {
            System.out.printf("¡¡GOLPE CRÍTICO!! [%s] desata un ataque devastador contra [%s] por %d de daño.%n",
                    this.nombre, enemigo.getNombre(), dano);
        } else {
            System.out.printf("[%s] ataca a [%s] causando %d de daño.%n",
                    this.nombre, enemigo.getNombre(), dano);
        }

        enemigo.recibirDano(dano);
    }

    /**
     * Utiliza una poción de salud para recuperar puntos de vida.
     * Delega en el método mutador setPuntosVida para asegurar los límites.
     *
     * @param cantidadRestaurada Puntos de salud que restaura la poción.
     */
    public void usarPocion(int cantidadRestaurada) {
        if (!this.estaVivo) {
            System.out.printf("[%s] está inconsciente y no puede consumir pociones.%n", this.nombre);
            return;
        }

        if (this.pociones <= 0) {
            System.out.println("[INVENTARIO]: No tienes pociones disponibles en este momento.");
            return;
        }

        if (this.puntosVida >= this.vidaMaxima) {
            System.out.printf("[%s] ya tiene la salud al máximo (%d/%d). No es necesario usar una poción.%n",
                    this.nombre, this.puntosVida, this.vidaMaxima);
            return;
        }

        this.pociones--;
        int vidaAnterior = this.puntosVida;
        setPuntosVida(this.puntosVida + cantidadRestaurada);
        int curacionEfectiva = this.puntosVida - vidaAnterior;

        System.out.printf("[%s] utilizó una poción y recuperó +%d puntos de vida (Salud actual: %d/%d). Pociones restantes: %d.%n",
                this.nombre, curacionEfectiva, this.puntosVida, this.vidaMaxima, this.pociones);
    }

    /**
     * Reduce los puntos de vida del personaje delegando en el setter validado.
     *
     * @param cantidadDano Daño recibido.
     */
    public void recibirDano(int cantidadDano) {
        if (cantidadDano > 0) {
            setPuntosVida(this.puntosVida - cantidadDano);
        }
    }

    /**
     * Sistema de experiencia y subida de nivel (Reto 2).
     *
     * @param exp Cantidad de experiencia obtenida.
     */
    public void ganarExperiencia(int exp) {
        if (exp <= 0) return;

        this.experiencia += exp;
        System.out.printf("[%s] ha ganado %d puntos de experiencia (EXP: %d/%d).%n",
                this.nombre, exp, this.experiencia, this.expSiguienteNivel);

        while (this.experiencia >= this.expSiguienteNivel) {
            this.experiencia -= this.expSiguienteNivel;
            setNivel(this.nivel + 1);
            this.vidaMaxima += 25;
            this.puntosAtaque += 5;
            this.expSiguienteNivel = (int) (this.expSiguienteNivel * 1.5);
            setPuntosVida(this.vidaMaxima); // Cura completa al subir de nivel

            System.out.printf("⭐ ¡¡SUBIDA DE NIVEL!! [%s] alcanzó el nivel %d. Salud máxima aumentada a %d y ataque a %d.%n",
                    this.nombre, this.nivel, this.vidaMaxima, this.puntosAtaque);
        }
    }

    /**
     * Agrega una poción al inventario.
     */
    public void agregarPocion() {
        this.pociones++;
    }
}
