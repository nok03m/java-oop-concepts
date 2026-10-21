package com.taller.arenarpg;

/**
 * Contrato comportamental defensivo que deben implementar las entidades combatientes.
 */
public interface ICombatiente {
    /**
     * Procesa la reducción y mitigación de daño recibido según la defensa de la entidad.
     * @param danoRecibido Cantidad de daño entrante.
     */
    void defender(int danoRecibido);
}
