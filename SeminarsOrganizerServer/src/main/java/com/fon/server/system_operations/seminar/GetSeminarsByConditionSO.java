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
 *
 * @author Aleksa
 */
public class GetSeminarsByConditionSO extends AbstractSO {

    private List<Seminar> seminars;

    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    public List<Seminar> getSeminars() {
        return seminars;
    }

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
