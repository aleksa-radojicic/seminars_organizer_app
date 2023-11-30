/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar_schedule;

import com.fon.common.domain.SeminarEnrollment;
import com.fon.common.domain.SeminarSchedule;
import java.util.List;
import com.fon.server.system_operations.AbstractSO;

/**
 * System operation used for retrieving a seminar schedule from the database
 * with a given ID.
 *
 * <p>
 * Extends abstract system operation class {@code AbstractSO}.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class GetSeminarScheduleByIDSO extends AbstractSO {

    /**
     * A seminar schedule retrieved from the database with a given ID as
     * {@code List<SeminarSchedule>}.
     */
    private SeminarSchedule seminarSchedule;

    /**
     * Checks if the sent object is instance of class {@code Integer}.
     *
     * @param arg Probably ID of the wanted seminar schedule as {@code Integer}.
     * @throws Exception When the sent object is not instance of class
     * {@code Integer}.
     */
    @Override
    protected void preconditions(Object arg) throws Exception {
        if (arg == null || !(arg instanceof Integer)) {
            throw new Exception("Послати објекат није одговарајућег типа");
        }
    }

    /**
     * Getter for seminarSchedule.
     *
     * @return A seminar schedule retrieved from the database with a given ID as
     * {@code List<SeminarSchedule>}.
     */
    public SeminarSchedule getSeminarSchedule() {
        return seminarSchedule;
    }

    /**
     * Retrieves a seminar schedule from the database with a given ID and stores
     * it in {@code seminarSchedule} attribute.
     *
     * @param arg ID of the wanted seminar schedule as {@code int}.
     * @throws Exception When an error happened while retrieving a seminar
     * schedule with a given ID.
     */
    @Override
    protected void executeOperation(Object arg) throws Exception {
        int seminarScheduleID = (int) arg;

        String whereSeminarScheduleQuerySection = " WHERE seminarScheduleID = " + seminarScheduleID;

        seminarSchedule = null;
        List<SeminarSchedule> seminarSchedules = (List<SeminarSchedule>) REPOSITORY.getByCondition(new SeminarSchedule(), whereSeminarScheduleQuerySection);

        if (seminarSchedules != null) {
            seminarSchedule = seminarSchedules.get(0);

            String whereSeminarEnrollmentQuerySection = "WHERE seminarScheduleID = " + seminarSchedule.getSeminarScheduleID();

            List<SeminarEnrollment> seminarEnrollments = REPOSITORY.getByCondition(new SeminarEnrollment(), whereSeminarEnrollmentQuerySection);
            seminarSchedule.setSeminarEnrollments(seminarEnrollments);

            for (SeminarEnrollment seminarEnrollment : seminarEnrollments) {
                seminarEnrollment.setSeminarSchedule(seminarSchedule);
            }
        }
    }
}
