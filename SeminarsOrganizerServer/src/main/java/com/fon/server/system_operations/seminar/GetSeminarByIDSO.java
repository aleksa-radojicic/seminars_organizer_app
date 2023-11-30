/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar;

import com.fon.common.domain.Seminar;
import com.fon.common.domain.SeminarTopic;
import java.util.List;
import com.fon.server.system_operations.AbstractSO;

/**
 * System operation used for retrieving a seminar from the database with a given
 * ID.
 *
 * <p>
 * Extends abstract system operation class {@code AbstractSO}.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class GetSeminarByIDSO extends AbstractSO {

    /**
     * A seminar retrieved from the database with a given ID as
     * {@code List<Seminar>}.
     */
    private Seminar seminar;

    /**
     * Checks if the sent object is instance of class {@code Integer}.
     *
     * @param arg Probably ID of the wanted seminar as {@code Integer}.
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
     * Getter for seminar.
     *
     * @return A seminar retrieved from the database with a given ID as
     * {@code List<Seminar>}.
     */
    public Seminar getSeminar() {
        return seminar;
    }

    /**
     * Retrieves a seminar from the database with a given ID and stores it in
     * {@code seminar} attribute.
     *
     * @param arg ID of the wanted seminar as {@code int}.
     * @throws Exception When an error happened while retrieving a seminar with
     * a given ID.
     */
    @Override
    protected void executeOperation(Object arg) throws Exception {
        int id = (int) arg;
        seminar = null;
        List<Seminar> seminars = REPOSITORY.getByCondition(new Seminar(), "WHERE seminarID =" + id);

        seminar = seminars.get(0);

        List<SeminarTopic> seminarTopics = REPOSITORY.getByCondition(new SeminarTopic(), " WHERE seminarID = " + seminar.getSeminarID());
        seminar.setSeminarTopics(seminarTopics);

        for (SeminarTopic seminarTopic : seminarTopics) {
            seminarTopic.setSeminar(seminar);
        }
    }
}
