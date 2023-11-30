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
 * System operation used for retrieving all seminars from the database.
 *
 * <p>
 * Extends abstract system operation class {@code AbstractSO}.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class GetAllSeminarsSO extends AbstractSO {

    /**
     * All seminars retrieved from the database as {@code List<Seminar>}.
     */
    private List<Seminar> allSeminars;

    /**
     * No preconditions are checked.
     */
    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    /**
     * Retrieves all seminars from the database and stores it in
     * {@code allSeminars} attribute.
     *
     * @param arg Not used.
     * @throws Exception When an error happened while retrieving all seminars.
     */
    @Override
    protected void executeOperation(Object arg) throws Exception {
        allSeminars = REPOSITORY.getByCondition(new Seminar(), "");

        for (Seminar seminar : allSeminars) {
            List<SeminarTopic> seminarTopics = REPOSITORY.getByCondition(new SeminarTopic(), "WHERE seminarID = " + seminar.getSeminarID());
            seminar.setSeminarTopics(seminarTopics);

            for (SeminarTopic seminarTopic : seminarTopics) {
                seminarTopic.setSeminar(seminar);
            }
        }
    }

    /**
     * Getter for allSeminars.
     *
     * @return All seminars retrieved from the database as
     * {@code List<Seminar>}.
     */
    public List<Seminar> getAllSeminars() {
        return allSeminars;
    }
}
