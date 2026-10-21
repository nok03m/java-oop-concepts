package com.taller.arenarpg;

/**
 * Especialización de combate físico con alto blindaje y bloqueo por escudo.
 */
public class Guerrero extends Personaje {
    protected int fuerzaEscudo;

    public Guerrero(String nombre, int vida, int ataqueBase, int posX, int posY, int fuerzaEscudo) {
        super(nombre, vida, ataqueBase, posX, posY);
        this.fuerzaEscudo = fuerzaEscudo;
    }

    public int getFuerzaEscudo() {
        return fuerzaEscudo;
    }

    public void setFuerzaEscudo(int fuerzaEscudo) {
        this.fuerzaEscudo = fuerzaEscudo;
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        if (!estaVivo()) return;
        int danoEspecial = this.ataqueBase + (this.fuerzaEscudo * 2);
        System.out.println("🛡️💥 ¡" + nombre + " ejecuta [Golpe de Escudo Brutal] contra " + objetivo.getNombre() +
                           "! Impacto masivo de " + danoEspecial + " puntos de daño físico.");
        objetivo.defender(danoEspecial);
    }

    @Override
    public void defender(int danoRecibido) {
        int danoMitigado = Math.max(0, danoRecibido - fuerzaEscudo);
        int bloqueoEfectivo = danoRecibido - danoMitigado;
        System.out.println("🛡️ " + nombre + " levanta su escudo bloqueando " + bloqueoEfectivo + " de daño (Fuerza de Escudo: " + fuerzaEscudo + ").");
        recibirDano(danoMitigado);
    }

    @Override
    public String toString() {
        return String.format("[Guerrero] %s | HP: %d/%d | ATK: %d | Escudo: %d | Pos: (%d,%d)",
                nombre, vida, vidaMaxima, ataqueBase, fuerzaEscudo, posicionX, posicionY);
    }
}
