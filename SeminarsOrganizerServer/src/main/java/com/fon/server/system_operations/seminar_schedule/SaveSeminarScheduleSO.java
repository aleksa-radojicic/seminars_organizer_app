/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar_schedule;

import com.fon.common.domain.SeminarEnrollment;
import com.fon.common.domain.SeminarSchedule;
import com.fon.common.domain.State;
import java.util.List;
import com.fon.server.system_operations.AbstractSO;

/**
 *
 * @author Aleksa
 */
public class SaveSeminarScheduleSO extends AbstractSO {

    @Override
    protected void preconditions(Object arg) throws Exception {
        if (arg == null || !(arg instanceof SeminarSchedule)) {
            throw new Exception("Послати објекат није одговарајућег типа");
        }
    }

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
