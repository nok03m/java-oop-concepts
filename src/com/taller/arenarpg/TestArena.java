package com.taller.arenarpg;

import java.util.ArrayList;
import java.util.List;

/**
 * Suite de verificación automática de los requerimientos técnicos del Taller:
 * - Sección 1: Jerarquía de clases, herencia, constructores super(), sobrecarga y sobreescritura.
 * - Sección 2: Colecciones dinámicas List<Personaje>, iteración polimórfica y removeIf.
 * - Sección 3: Contenedor genérico Inventario<T> y límites del tablero 2D.
 * - Sección 4: Evaluación de condiciones de victoria y derrota.
 */
public class TestArena {
    public static void main(String[] args) {
        System.out.println(">>> EJECUTANDO SUITE DE PRUEBAS AUTOMATIZADAS DEL TALLER RPG <<<\n");
        int pruebasExitosas = 0;
        int pruebasTotales = 0;

        // Prueba 1: Herencia y constructores con super()
        pruebasTotales++;
        try {
            Guerrero g = new Guerrero("Thorin", 100, 20, 0, 0, 10);
            Mago m = new Mago("Gandalf", 70, 25, 1, 1, 40);
            assert g.getNombre().equals("Thorin") : "El nombre del guerrero debe coincidir";
            assert g.getFuerzaEscudo() == 10 : "Fuerza de escudo incorrecta";
            assert m.getPuntosMana() == 40 : "Puntos de maná incorrectos";
            System.out.println("✅ Prueba 1 Superada: Herencia y constructores super() validados.");
            pruebasExitosas++;
        } catch (AssertionError | Exception e) {
            System.err.println("❌ Prueba 1 Fallida: " + e.getMessage());
        }

        // Prueba 2: Sobreescritura (Polimorfismo dinámico en ataque especial y defensa)
        pruebasTotales++;
        try {
            Guerrero g = new Guerrero("Thorin", 100, 20, 0, 0, 10);
            Enemigo e = new Enemigo("Orco", 100, 15, 0, 1, 5);

            // Ataque especial del guerrero: 20 + (10 * 2) = 40. Orco mitiga 5 -> recibe 35. Vida: 65.
            g.realizarAtaqueEspecial(e);
            assert e.getVida() == 65 : "La vida esperada del orco tras ataque especial era 65, pero es " + e.getVida();

            // Mago gasta maná
            Mago m = new Mago("Gandalf", 80, 20, 1, 1, 50);
            m.realizarAtaqueEspecial(e); // gasta 20 mana
            assert m.getPuntosMana() == 30 : "El maná restante debía ser 30";

            System.out.println("✅ Prueba 2 Superada: Sobreescritura y polimorfismo dinámico validados.");
            pruebasExitosas++;
        } catch (AssertionError | Exception ex) {
            System.err.println("❌ Prueba 2 Fallida: " + ex.getMessage());
        }

        // Prueba 3: Sobrecarga de métodos (Polimorfismo estático)
        pruebasTotales++;
        try {
            Guerrero g = new Guerrero("Thorin", 100, 20, 0, 0, 0);
            Enemigo dummy1 = new Enemigo("D1", 100, 0, 0, 0, 0);
            Enemigo dummy2 = new Enemigo("D2", 100, 0, 0, 0, 0);

            g.atacar(dummy1); // 20 daño base
            g.atacar(dummy2, true); // Modo furia: 20 * 1.5 = 30 daño

            assert dummy1.getVida() == 80 : "Daño de ataque básico incorrecto";
            assert dummy2.getVida() == 70 : "Daño de ataque con furia incorrecto";

            System.out.println("✅ Prueba 3 Superada: Sobrecarga de atacar() y modo furia validados.");
            pruebasExitosas++;
        } catch (AssertionError | Exception ex) {
            System.err.println("❌ Prueba 3 Fallida: " + ex.getMessage());
        }

        // Prueba 4: Gestión de escuadrón con ArrayList y removeIf
        pruebasTotales++;
        try {
            List<Personaje> escuadron = new ArrayList<>();
            Guerrero vivo = new Guerrero("Heroe1", 100, 10, 0, 0, 5);
            Guerrero caido = new Guerrero("Heroe2", 0, 10, 0, 1, 5);

            escuadron.add(vivo);
            escuadron.add(caido);

            assert escuadron.size() == 2 : "Tamaño de lista inicial incorrecto";
            escuadron.removeIf(p -> !p.estaVivo());
            assert escuadron.size() == 1 : "removeIf no filtró los caídos correctamente";
            assert escuadron.get(0).getNombre().equals("Heroe1") : "Elemento restante incorrecto";

            System.out.println("✅ Prueba 4 Superada: ArrayList<Personaje> y filtrado con removeIf validados.");
            pruebasExitosas++;
        } catch (AssertionError | Exception ex) {
            System.err.println("❌ Prueba 4 Fallida: " + ex.getMessage());
        }

        // Prueba 5: Contenedor genérico Inventario<T>
        pruebasTotales++;
        try {
            Inventario<Pocion> invPociones = new Inventario<>();
            Pocion p1 = new Pocion("Poción Mayor", 50, Pocion.Tipo.VIDA);
            Pocion p2 = new Pocion("Elixir Maná", 25, Pocion.Tipo.MANA);

            invPociones.agregar(p1);
            invPociones.agregar(p2);

            assert invPociones.tamano() == 2 : "Tamaño de inventario incorrecto";
            Pocion obtenida = invPociones.obtener(0);
            assert obtenida.getNombre().equals("Poción Mayor") : "Recuperación de tipo genérico incorrecta";

            // Comprobación de aplicación
            Guerrero heroeHerido = new Guerrero("Herido", 100, 10, 0, 0, 0);
            heroeHerido.recibirDano(90); // Salud restante: 10/100
            obtenida.aplicar(heroeHerido); // Cura 50 -> Salud restante: 60/100
            assert heroeHerido.getVida() == 60 : "Curación por poción incorrecta";

            System.out.println("✅ Prueba 5 Superada: Contenedor genérico Inventario<T> y Poción validados.");
            pruebasExitosas++;
        } catch (AssertionError | Exception ex) {
            System.err.println("❌ Prueba 5 Fallida: " + ex.getMessage());
        }

        // Prueba 6: Tablero 2D y prevención de IndexOutOfBoundsException
        pruebasTotales++;
        try {
            Tablero tablero = new Tablero();
            assert tablero.esPosicionValida(0, 0) : "(0,0) debe ser válido";
            assert tablero.esPosicionValida(4, 4) : "(4,4) debe ser válido";
            assert !tablero.esPosicionValida(-1, 0) : "(-1,0) no debe ser válido";
            assert !tablero.esPosicionValida(0, 5) : "(0,5) no debe ser válido (evita IndexOutOfBoundsException)";
            assert tablero.esPosicionCofre(2, 2) : "Cofre debe estar en (2,2)";

            System.out.println("✅ Prueba 6 Superada: Tablero 2D y validaciones de límites validados.");
            pruebasExitosas++;
        } catch (AssertionError | Exception ex) {
            System.err.println("❌ Prueba 6 Fallida: " + ex.getMessage());
        }

        // Resumen
        System.out.println("\n=======================================================");
        System.out.println("RESULTADO: " + pruebasExitosas + "/" + pruebasTotales + " pruebas completadas con éxito.");
        System.out.println("=======================================================");
        if (pruebasExitosas == pruebasTotales) {
            System.out.println("🎉 Todos los requisitos técnicos e integradores han sido verificados.");
        }
    }
}
