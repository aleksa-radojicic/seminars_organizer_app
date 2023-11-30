/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar;

import com.fon.common.domain.Seminar;
import com.fon.common.domain.SeminarTopic;
import com.fon.server.system_operations.AbstractSO;

/**
 * System operation used for creating a given seminar in the database.
 *
 * <p>
 * Extends abstract system operation class {@code AbstractSO}.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class CreateSeminarSO extends AbstractSO {

    /**
     * Checks if the sent object is instance of class {@code Seminar}.
     *
     * @param arg Probably a seminar ({@code Seminar}) that needs to be created
     * in the database.
     * @throws Exception When the sent object is not instance of class
     * {@code Seminar}.
     */
    @Override
    protected void preconditions(Object arg) throws Exception {
        if (arg == null || !(arg instanceof Seminar)) {
            throw new Exception("Послати објекат није одговарајућег типа");
        }
    }

    /**
     * Creates a given seminar in the database.
     *
     * @param arg A seminar ({@code Seminar}) that needs to be created in the
     * database.
     * @throws Exception When an error happened while creating a given seminar.
     */
    @Override
    protected void executeOperation(Object arg) throws Exception {
        Seminar seminar = (Seminar) arg;
        REPOSITORY.create(seminar);

        for (SeminarTopic seminarTopic : seminar.getSeminarTopics()) {
            REPOSITORY.create(seminarTopic);
        }
    }
}
