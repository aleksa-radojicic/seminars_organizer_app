/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar_schedule;

import com.fon.common.domain.SeminarEnrollment;
import com.fon.common.domain.SeminarSchedule;
import com.fon.common.domain.State;
import com.fon.server.constants.ServerConstants;
import java.util.List;
import com.fon.server.system_operations.AbstractSO;

/**
 * System operation used for saving a given seminar schedule in the database.
 *
 * <p>
 * Extends abstract system operation class {@code AbstractSO}.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class SaveSeminarScheduleSO extends AbstractSO {

    /**
     * Checks if the sent object is instance of class {@code SeminarSchedule}.
     *
     * @param arg Probably a seminar schedule ({@code SeminarSchedule}) that
     * needs to be saved in the database.
     * @throws Exception When the sent object is not instance of class
     * {@code SeminarSchedule}.
     */
    @Override
    protected void preconditions(Object arg) throws Exception {
        if (arg == null || !(arg instanceof SeminarSchedule)) {
            throw new Exception(ServerConstants.INCORRECT_TYPE_ERROR_MESSAGE);
        }
    }

    /**
     * Saves a given seminar schedule in the database.
     *
     * @param arg A seminar schedule ({@code SeminarSchedule}) that needs to be
     * saved in the database.
     * @throws Exception When an error happened while saving a given seminar
     * schedule.
     */
    @Override
    protected void executeOperation(Object arg) throws Exception {
        SeminarSchedule seminarSchedule = (SeminarSchedule) arg;

        if (seminarSchedule.getState().equals(State.CHANGED)) {
            REPOSITORY.update(seminarSchedule);
        }

        List<SeminarEnrollment> seminarEnrollments = seminarSchedule.getSeminarEnrollments();

        for (SeminarEnrollment seminarEnrollment : seminarEnrollments) {
            switch (seminarEnrollment.getState()) {
                case UNCHANGED -> {
                }
                case CREATED -> {
                    REPOSITORY.create(seminarEnrollment);
                }
                case CHANGED -> {
                    REPOSITORY.update(seminarEnrollment);
                }
                case DELETED -> {
                    String whereSection = "WHERE seminarScheduleID = "
                            + seminarEnrollment.getSeminarSchedule().getSeminarScheduleID()
                            + " AND participantID = "
                            + seminarEnrollment.getParticipant().getParticipantID();
                    REPOSITORY.delete(seminarEnrollment, whereSection);
                }
                default ->
                    throw new AssertionError();
            }
        }
    }
}
