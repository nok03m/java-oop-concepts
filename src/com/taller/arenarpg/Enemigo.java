package com.taller.arenarpg;

/**
 * Representa los adversarios hostiles dentro de la arena de combate.
 */
public class Enemigo extends Personaje {
    protected int armadura;

    public Enemigo(String nombre, int vida, int ataqueBase, int posX, int posY, int armadura) {
        super(nombre, vida, ataqueBase, posX, posY);
        this.armadura = armadura;
    }

    public int getArmadura() {
        return armadura;
    }

    public void setArmadura(int armadura) {
        this.armadura = armadura;
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        if (!estaVivo()) return;
        int danoEspecial = (int) (this.ataqueBase * 1.6);
        System.out.println("☠️ ¡" + nombre + " descarga una [Furia Despiadada] sobre " + objetivo.getNombre() +
                           " infringiendo " + danoEspecial + " de daño!");
        objetivo.defender(danoEspecial);
    }

    @Override
    public void defender(int danoRecibido) {
        int danoMitigado = Math.max(0, danoRecibido - armadura);
        if (armadura > 0) {
            System.out.println("🛡️ " + nombre + " amortigua " + (danoRecibido - danoMitigado) + " de daño con su armadura natural.");
        }
        recibirDano(danoMitigado);
    }

    @Override
    public String toString() {
        return String.format("[Enemigo] %s | HP: %d/%d | ATK: %d | Armadura: %d | Pos: (%d,%d)",
                nombre, vida, vidaMaxima, ataqueBase, armadura, posicionX, posicionY);
    }
}
