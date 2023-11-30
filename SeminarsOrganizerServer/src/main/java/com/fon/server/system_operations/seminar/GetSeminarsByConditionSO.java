/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar;

import com.fon.common.domain.Seminar;
import com.fon.common.domain.SeminarTopic;
import java.util.LinkedList;
import java.util.List;
import com.fon.server.system_operations.AbstractSO;

/**
 * System operation used for retrieving seminars from the database that meet a
 * certain condition.
 *
 * <p>
 * Extends abstract system operation class {@code AbstractSO}.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class GetSeminarsByConditionSO extends AbstractSO {

    /**
     * All seminars retrieved from the database that meet a certain condition as
     * {@code List<Seminar>}.
     */
    private List<Seminar> seminars;

    /**
     * Checks if the sent object is instance of class {@code String}.
     *
     * @param arg Probably seminar name condition as {@code String}.
     * @throws Exception When the sent object is not instance of class
     * {@code String}.
     */
    @Override
    protected void preconditions(Object arg) throws Exception {
        if (arg == null || !(arg instanceof String)) {
            throw new Exception("Послати објекат није одговарајућег типа");
        }
    }

    /**
     * Getter for seminars.
     *
     * @return All seminars retrieved from the database that meet a certain
     * condition as {@code List<Seminar>}.
     */
    public List<Seminar> getSeminars() {
        return seminars;
    }

    /**
     * Retrieves all seminars from the database that meet a certain condition
     * and stores it in {@code seminars} attribute.
     *
     * @param arg Not used.
     * @throws Exception When an error happened while retrieving all seminars
     * that meet a certain condition.
     */
    @Override
    protected void executeOperation(Object arg) throws Exception {
        String condition = (String) arg;
        seminars = new LinkedList();

        String whereQuerySection = "";

        if (condition != null && !condition.isBlank()) {
            whereQuerySection = "WHERE name LIKE '%" + condition + "%'";
        }

        List<Seminar> seminars_ = REPOSITORY.getByCondition(new Seminar(), whereQuerySection);

        if (seminars_ != null) {
            for (Seminar seminar : seminars_) {
                List<SeminarTopic> seminarTopics = REPOSITORY.getByCondition(new SeminarTopic(), " WHERE seminarID = " + seminar.getSeminarID());
                seminar.setSeminarTopics(seminarTopics);

                for (SeminarTopic seminarTopic : seminarTopics) {
                    seminarTopic.setSeminar(seminar);
                }
            }
            seminars = seminars_;
        }
    }
}
