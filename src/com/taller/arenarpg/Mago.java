package com.taller.arenarpg;

/**
 * Especialización de combate mágico a distancia con consumo y gestión de maná.
 */
public class Mago extends Personaje {
    protected int puntosMana;
    protected int manaMaximo;

    public Mago(String nombre, int vida, int ataqueBase, int posX, int posY, int puntosMana) {
        super(nombre, vida, ataqueBase, posX, posY);
        this.puntosMana = puntosMana;
        this.manaMaximo = puntosMana;
    }

    public int getPuntosMana() {
        return puntosMana;
    }

    public int getManaMaximo() {
        return manaMaximo;
    }

    public void restaurarMana(int cantidad) {
        this.puntosMana = Math.min(manaMaximo, this.puntosMana + cantidad);
        System.out.println("🔮 " + nombre + " recupera " + cantidad + " PM. Maná actual: " + puntosMana + "/" + manaMaximo);
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        if (!estaVivo()) return;
        int costoMana = 20;

        if (this.puntosMana >= costoMana) {
            this.puntosMana -= costoMana;
            int danoMagico = (this.ataqueBase * 2) + 15;
            System.out.println("⚡🔮 ¡" + nombre + " conjura [Meteoro Arcano] contra " + objetivo.getNombre() +
                               "! Consume " + costoMana + " PM e inflige " + danoMagico + " de daño arcano. Maná restante: " + puntosMana);
            objetivo.defender(danoMagico);
        } else {
            System.out.println("⚠️ " + nombre + " no tiene suficiente maná (" + puntosMana + "/" + costoMana + " PM). Ejecuta un ataque básico.");
            atacar(objetivo, false);
        }
    }

    @Override
    public void defender(int danoRecibido) {
        int mitigacion = 0;
        int costoBarrera = 5;

        // Si el mago tiene maná suficiente, absorbe una porción del daño mediante un escudo de maná
        if (this.puntosMana >= costoBarrera) {
            mitigacion = 8;
            this.puntosMana -= costoBarrera;
            System.out.println("✨ " + nombre + " activa una [Barrera Mágica Menor] absorbiendo " + mitigacion +
                               " de daño (-" + costoBarrera + " PM). Maná: " + puntosMana);
        }

        int danoFinal = Math.max(0, danoRecibido - mitigacion);
        recibirDano(danoFinal);
    }

    @Override
    public String toString() {
        return String.format("[Mago] %s | HP: %d/%d | ATK: %d | MP: %d/%d | Pos: (%d,%d)",
                nombre, vida, vidaMaxima, ataqueBase, puntosMana, manaMaximo, posicionX, posicionY);
    }
}
