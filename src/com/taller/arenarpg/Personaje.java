package com.taller.arenarpg;

/**
 * Clase base abstracta que consolida el estado compartido y contratos de combate.
 * Implementa la interfaz ICombatiente.
 */
public abstract class Personaje implements ICombatiente {
    protected String nombre;
    protected int vida;
    protected int vidaMaxima;
    protected int ataqueBase;
    protected int posicionX;
    protected int posicionY;

    public Personaje(String nombre, int vida, int ataqueBase, int posicionX, int posicionY) {
        this.nombre = nombre;
        this.vida = vida;
        this.vidaMaxima = vida;
        this.ataqueBase = ataqueBase;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    /**
     * Ataque especial único que cada subclase concreta debe implementar.
     * @param objetivo Entidad contra la que se dirige el ataque.
     */
    public abstract void realizarAtaqueEspecial(Personaje objetivo);

    /**
     * Sobrecarga 1: Ataque básico estándar.
     * @param objetivo Entidad objetivo.
     */
    public void atacar(Personaje objetivo) {
        atacar(objetivo, false);
    }

    /**
     * Sobrecarga 2: Ataque con posibilidad de activar modo furia (polimorfismo estático).
     * @param objetivo Entidad objetivo.
     * @param modoFuria Indica si se activa la bonificación de daño por furia.
     */
    public void atacar(Personaje objetivo, boolean modoFuria) {
        if (!estaVivo()) {
            System.out.println("❌ " + nombre + " no puede atacar porque ha caído en combate.");
            return;
        }

        int danoFinal = ataqueBase;
        if (modoFuria) {
            danoFinal = (int) (ataqueBase * 1.5);
            System.out.println("🔥 ¡" + nombre + " entra en MODO FURIA! Daño potenciado a " + danoFinal + " contra " + objetivo.getNombre() + ".");
        } else {
            System.out.println("⚔️ " + nombre + " ataca normalmente a " + objetivo.getNombre() + " (" + danoFinal + " daño base).");
        }
        objetivo.defender(danoFinal);
    }

    /**
     * Verifica si la entidad continúa en combate.
     * @return true si vida > 0.
     */
    public boolean estaVivo() {
        return this.vida > 0;
    }

    /**
     * Descuenta la salud del personaje garantizando que no descienda de cero.
     * @param cantidad Cantidad de daño a descontar.
     */
    public void recibirDano(int cantidad) {
        this.vida = Math.max(0, this.vida - cantidad);
        System.out.println("💥 " + nombre + " recibe " + cantidad + " de daño. Salud restante: " + vida + "/" + vidaMaxima);
        if (this.vida == 0) {
            System.out.println("💀 ¡" + nombre + " ha sido derrotado!");
        }
    }

    /**
     * Restaura salud sin exceder el valor de vida máxima.
     * @param cantidad Puntos a restaurar.
     */
    public void curar(int cantidad) {
        if (!estaVivo()) {
            System.out.println("⚠️ " + nombre + " no puede ser curado porque está fuera de combate.");
            return;
        }
        this.vida = Math.min(vidaMaxima, this.vida + cantidad);
        System.out.println("💚 " + nombre + " recupera " + cantidad + " PV. Salud actual: " + vida + "/" + vidaMaxima);
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getAtaqueBase() {
        return ataqueBase;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }
}
