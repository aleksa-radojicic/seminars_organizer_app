/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar;

import com.fon.common.domain.Seminar;
import com.fon.common.domain.SeminarTopic;
import com.fon.common.domain.State;
import com.fon.server.constants.ServerConstants;
import java.util.List;
import com.fon.server.system_operations.AbstractSO;

/**
 * System operation used for saving a given seminar in the database.
 *
 * <p>
 * Extends abstract system operation class {@code AbstractSO}.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class SaveSeminarSO extends AbstractSO {

    /**
     * Checks if the sent object is instance of class {@code Seminar}.
     *
     * @param arg Probably a seminar ({@code Seminar}) that needs to be saved in
     * the database.
     * @throws Exception When the sent object is not instance of class
     * {@code Seminar}.
     */
    @Override
    protected void preconditions(Object arg) throws Exception {
        if (arg == null || !(arg instanceof Seminar)) {
            throw new Exception(ServerConstants.INCORRECT_TYPE_ERROR_MESSAGE);
        }
    }

    /**
     * Saves a given seminar in the database.
     *
     * @param arg A seminar ({@code Seminar}) that needs to be saved in the
     * database.
     * @throws Exception When an error happened while saving a given seminar.
     */
    @Override
    protected void executeOperation(Object arg) throws Exception {
        Seminar seminar = (Seminar) arg;

        if (seminar.getState().equals(State.CHANGED)) {
            REPOSITORY.update(seminar);
        }

        List<SeminarTopic> seminarTopics = seminar.getSeminarTopics();

        for (SeminarTopic seminarTopic : seminarTopics) {
            switch (seminarTopic.getState()) {
                case UNCHANGED -> {
                }
                case CREATED -> {
                    REPOSITORY.create(seminarTopic);
                }
                case CHANGED -> {
                    REPOSITORY.update(seminarTopic);
                }
                case DELETED -> {
                    String whereSection = "WHERE seminarID = "
                            + seminarTopic.getSeminar().getSeminarID()
                            + " AND seminarTopicID = "
                            + seminarTopic.getSeminarTopicID();
                    REPOSITORY.delete(seminarTopic, whereSection);
                }
                default ->
                    throw new AssertionError();
            }
        }
    }
}
