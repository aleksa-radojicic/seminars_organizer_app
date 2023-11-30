/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.participant;

import com.fon.common.domain.Participant;
import com.fon.server.system_operations.AbstractSO;

/**
 * System operation used for creating a given participant in the database.
 *
 * <p>
 * Extends abstract system operation class {@code AbstractSO}.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class CreateParticipantSO extends AbstractSO {

    /**
     * Checks if the sent object is instance of class {@code Participant}.
     *
     * @param arg Probably a participant ({@code Participant}) that needs to be
     * created in the database.
     * @throws Exception When the sent object is not instance of class
     * {@code Participant}.
     */
    @Override
    protected void preconditions(Object arg) throws Exception {
        if (arg == null || !(arg instanceof Participant)) {
            throw new Exception("Послати објекат није одговарајућег типа");
        }
    }

    /**
     * Creates a given participant in the database.
     *
     * @param arg A participant ({@code Participant}) that needs to be created
     * in the database.
     * @throws Exception When an error happened while creating a given
     * participant.
     */
    @Override
    protected void executeOperation(Object arg) throws Exception {
        Participant participant = (Participant) arg;
        REPOSITORY.create(participant);
    }
}
