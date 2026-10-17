package co.edu.puj.rpg.modelo.enemigo;

import co.edu.puj.rpg.modelo.Personaje;

/**
 * Representa una entidad enemiga en el simulador RPG.
 * Ubicada en el subpaquete 'co.edu.puj.rpg.modelo.enemigo' para demostrar
 * el aislamiento y los modificadores de acceso entre paquetes en Java.
 */
public class Enemigo {

    // Atributos estrictamente privados (Encapsulamiento)
    private String nombre;
    private int puntosVida;
    private int vidaMaxima;
    private int puntosAtaque;
    private double oroRecompensa;
    private int expRecompensa;
    private boolean estaVivo;

    /**
     * Constructor completo utilizando la palabra clave 'this'.
     *
     * @param nombre         Nombre del enemigo.
     * @param vidaMaxima     Vida máxima del enemigo.
     * @param puntosAtaque   Puntos de ataque base.
     * @param oroRecompensa  Oro otorgado al ser derrotado.
     * @param expRecompensa  Experiencia otorgada al ser derrotado.
     */
    public Enemigo(String nombre, int vidaMaxima, int puntosAtaque, double oroRecompensa, int expRecompensa) {
        this.nombre = nombre;
        this.vidaMaxima = Math.max(vidaMaxima, 1);
        this.puntosAtaque = Math.max(puntosAtaque, 0);
        this.oroRecompensa = Math.max(oroRecompensa, 0.0);
        this.expRecompensa = Math.max(expRecompensa, 0);
        setPuntosVida(this.vidaMaxima);
    }

    /**
     * Constructor de sobrecarga con valores de recompensa por defecto.
     *
     * @param nombre       Nombre del enemigo.
     * @param vidaMaxima   Vida máxima.
     * @param puntosAtaque Puntos de daño.
     */
    public Enemigo(String nombre, int vidaMaxima, int puntosAtaque) {
        this(nombre, vidaMaxima, puntosAtaque, 25.0, 50);
    }

    // ==========================================
    // MÉTODOS MUTADORES (SETTERS CON VALIDACIÓN)
    // ==========================================

    /**
     * Modifica los puntos de vida respetando los límites de negocio.
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

    public void setVidaMaxima(int vidaMaxima) {
        if (vidaMaxima > 0) {
            this.vidaMaxima = vidaMaxima;
            if (this.puntosVida > this.vidaMaxima) {
                this.puntosVida = this.vidaMaxima;
            }
        }
    }

    public void setPuntosAtaque(int puntosAtaque) {
        if (puntosAtaque >= 0) {
            this.puntosAtaque = puntosAtaque;
        }
    }

    public void setOroRecompensa(double oroRecompensa) {
        if (oroRecompensa >= 0.0) {
            this.oroRecompensa = oroRecompensa;
        }
    }

    public void setExpRecompensa(int expRecompensa) {
        if (expRecompensa >= 0) {
            this.expRecompensa = expRecompensa;
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

    public double getOroRecompensa() {
        return this.oroRecompensa;
    }

    public int getExpRecompensa() {
        return this.expRecompensa;
    }

    public boolean isEstaVivo() {
        return this.estaVivo;
    }

    // ==========================================
    // API PÚBLICA DE COMPORTAMIENTO
    // ==========================================

    /**
     * Aplica reducción de vida delegando en el setter validado.
     *
     * @param cantidadDano Daño a infligir.
     */
    public void recibirDano(int cantidadDano) {
        if (cantidadDano > 0) {
            setPuntosVida(this.puntosVida - cantidadDano);
        }
    }

    /**
     * Realiza un ataque sobre el héroe objetivo.
     *
     * @param heroe Instancia del Personaje atacado.
     * @return Mensaje descriptivo de la acción para el historial.
     */
    public String atacar(Personaje heroe) {
        if (!this.estaVivo) {
            return String.format("[%s] está derrotado y no puede atacar.", this.nombre);
        }

        heroe.recibirDano(this.puntosAtaque);
        return String.format("¡[%s] atacó a [%s] infligiendo %d puntos de daño!",
                this.nombre, heroe.getNombre(), this.puntosAtaque);
    }
}
