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
public class GetParticipantByIDSO extends AbstractSO {

    private Participant element;

    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    public Participant getElement() {
        return element;
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        int id = (int) arg;
        element = null;
        List<Participant> l = (List<Participant>) REPOSITORY.getByCondition(new Participant(), "WHERE participantID = " + id);
        if (l != null) {
            element = l.get(0);
        }
    }
}
