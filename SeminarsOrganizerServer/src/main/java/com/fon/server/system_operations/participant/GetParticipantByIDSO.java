/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.participant;

import com.fon.common.domain.Participant;
import com.fon.server.constants.ServerConstants;
import java.util.List;
import com.fon.server.system_operations.AbstractSO;

/**
 * System operation used for retrieving a participant from the database with a
 * given ID.
 *
 * <p>
 * Extends abstract system operation class {@code AbstractSO}.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class GetParticipantByIDSO extends AbstractSO {

    /**
     * A participant retrieved from the database with a given ID as
     * {@code List<Participant>}.
     */
    private Participant element;

    /**
     * Checks if the sent object is instance of class {@code Integer}.
     *
     * @param arg Probably ID of the wanted participant as {@code Integer}.
     * @throws Exception When the sent object is not instance of class
     * {@code Integer}.
     */
    @Override
    protected void preconditions(Object arg) throws Exception {
        if (arg == null || !(arg instanceof Integer)) {
            throw new Exception(ServerConstants.INCORRECT_TYPE_ERROR_MESSAGE);
        }
    }

    /**
     * Getter for element.
     *
     * @return A participant retrieved from the database with a given ID as
     * {@code List<Participant>}.
     */
    public Participant getElement() {
        return element;
    }

    /**
     * Retrieves a participant from the database with a given ID and stores it
     * in {@code element} attribute.
     *
     * @param arg ID of the wanted participant as {@code int}.
     * @throws Exception When an error happened while retrieving a participant
     * with a given ID.
     */
    @Override
    protected void executeOperation(Object arg) throws Exception {
        int id = (int) arg;
        element = null;
        List<Participant> list = (List<Participant>) REPOSITORY.getByCondition(new Participant(), "WHERE participantID = " + id);
        if (!list.isEmpty()) {
            element = list.get(0);
        }
    }
}
