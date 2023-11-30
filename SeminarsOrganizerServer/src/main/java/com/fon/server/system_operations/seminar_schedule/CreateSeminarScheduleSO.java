/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar_schedule;

import com.fon.common.domain.SeminarEnrollment;
import com.fon.common.domain.SeminarSchedule;
import com.fon.server.system_operations.AbstractSO;

/**
 * System operation used for creating a given seminar schedule in the database.
 *
 * <p>
 * Extends abstract system operation class {@code AbstractSO}.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class CreateSeminarScheduleSO extends AbstractSO {

    /**
     * Checks if the sent object is instance of class {@code SeminarSchedule}.
     *
     * @param arg Probably a seminar schedule ({@code SeminarSchedule}) that
     * needs to be created in the database.
     * @throws Exception When the sent object is not instance of class
     * {@code SeminarSchedule}.
     */
    @Override
    protected void preconditions(Object arg) throws Exception {
        if (arg == null || !(arg instanceof SeminarSchedule)) {
            throw new Exception("Послати објекат није одговарајућег типа");
        }
    }

    /**
     * Creates a given seminar schedule in the database.
     *
     * @param arg A seminar schedule ({@code SeminarSchedule}) that needs to be
     * created in the database.
     * @throws Exception When an error happened while creating a given seminar
     * schedule.
     */
    @Override
    protected void executeOperation(Object arg) throws Exception {
        SeminarSchedule seminarSchedule = (SeminarSchedule) arg;
        REPOSITORY.create(seminarSchedule);

        for (SeminarEnrollment seminarEnrollment : seminarSchedule.getSeminarEnrollments()) {
            REPOSITORY.create(seminarEnrollment);
        }
    }
}
