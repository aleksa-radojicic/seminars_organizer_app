/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.participant;

import com.fon.common.domain.Participant;
import java.util.List;
import com.fon.server.system_operations.AbstractSO;

/**
 * System operation used for retrieving all participants from the database.
 *
 * <p>
 * Extends abstract system operation class {@code AbstractSO}.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class GetAllParticipantsSO extends AbstractSO {

    /**
     * All participants retrieved from the database as
     * {@code List<Participant>}.
     */
    private List<Participant> allParticipants;

    /**
     * No preconditions are checked.
     *
     * @param arg Empty.
     */
    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    /**
     * Retrieves all participants from the database and stores it in
     * {@code allParticipants} attribute.
     *
     * @param param Not used.
     * @throws Exception When an error happened while retrieving all
     * participants.
     */
    @Override
    protected void executeOperation(Object param) throws Exception {
        allParticipants = REPOSITORY.getByCondition(new Participant(), "");
    }

    /**
     * Getter for allParticipants.
     *
     * @return All participants retrieved from the database as
     * {@code List<Participant>}.
     */
    public List<Participant> getAllParticipants() {
        return allParticipants;
    }
}
