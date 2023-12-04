/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.participant;

import com.fon.common.domain.Participant;
import java.util.LinkedList;
import java.util.List;
import com.fon.server.system_operations.AbstractSO;
import com.fon.common.utils.Utility;
import com.fon.server.constants.ServerConstants;

/**
 * System operation used for retrieving participants from the database that meet
 * a certain condition.
 *
 * <p>
 * Extends abstract system operation class {@code AbstractSO}.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class GetParticipantsByConditionSO extends AbstractSO {

    /**
     * All participants retrieved from the database that meet a certain
     * condition as {@code List<Participant>}.
     */
    private List<Participant> list;

    /**
     * Checks if the sent object is instance of class {@code String}.
     *
     * @param arg Probably participant name and / or surname condition as
     * {@code String}.
     * @throws Exception When the sent object is not instance of class
     * {@code String}.
     */
    @Override
    protected void preconditions(Object arg) throws Exception {
        if (arg == null || !(arg instanceof String)) {
            throw new Exception(ServerConstants.INCORRECT_TYPE_ERROR_MESSAGE);
        }
    }

    /**
     * Getter for list.
     *
     * @return All participants retrieved from the database that meet a certain
     * condition as {@code List<Participant>}.
     */
    public List<Participant> getList() {
        return list;
    }

    /**
     * Retrieves all participants from the database that meet a certain
     * condition and stores it in {@code list} attribute.
     *
     * @param arg Not used.
     * @throws Exception When an error happened while retrieving all participant
     * that meet a certain condition.
     */
    @Override
    protected void executeOperation(Object arg) throws Exception {
        String condition = (String) arg;
        list = new LinkedList();

        String whereQuerySection = "";

        if (!Utility.isStringNullOrBlank(condition)) {
            whereQuerySection = "WHERE name LIKE '%" + condition + "%' or surname LIKE '%" + condition + "%'";
        }

        List<Participant> participants = REPOSITORY.getByCondition(new Participant(), whereQuerySection);
        if (!participants.isEmpty()) {
            list = participants;
        }
    }
}
