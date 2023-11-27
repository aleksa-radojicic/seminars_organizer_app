/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.participant;

import com.fon.common.domain.Participant;
import java.util.List;
import com.fon.server.system_operations.AbstractSO;

/**
 *
 * @author Aleksa
 */
public class GetAllParticipantsSO extends AbstractSO {

    private List<Participant> allParticipants;

    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        allParticipants = REPOSITORY.getByCondition(new Participant(), "");
    }

    public List<Participant> getAllParticipants() {
        return allParticipants;
    }
}
