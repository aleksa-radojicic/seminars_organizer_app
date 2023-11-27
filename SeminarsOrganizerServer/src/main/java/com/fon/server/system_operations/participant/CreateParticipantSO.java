/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.participant;

import com.fon.common.domain.Participant;
import com.fon.server.system_operations.AbstractSO;

/**
 *
 * @author Aleksa
 */
public class CreateParticipantSO extends AbstractSO {

    @Override
    protected void preconditions(Object arg) throws Exception {
        if (arg == null || !(arg instanceof Participant)) {
            throw new Exception("Послати објекат није одговарајућег типа");
        }
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        Participant participant = (Participant) arg;
        REPOSITORY.create(participant);
    }
}
