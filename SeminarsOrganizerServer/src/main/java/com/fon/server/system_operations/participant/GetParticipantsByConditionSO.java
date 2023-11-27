/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.participant;

import com.fon.common.domain.Participant;
import java.util.LinkedList;
import java.util.List;
import com.fon.server.system_operations.AbstractSO;
import com.fon.common.utils.Utility;

/**
 *
 * @author Aleksa
 */
public class GetParticipantsByConditionSO extends AbstractSO {

    private List<Participant> list;

    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    public List<Participant> getList() {
        return list;
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        String condition = (String) arg;
        list = new LinkedList();

        String whereQuerySection = "";

        if (!Utility.isStringNullOrBlank(condition)) {
            whereQuerySection = "WHERE name LIKE '%" + condition + "%' or surname LIKE '%" + condition + "%'";
        }

        List<Participant> participants = REPOSITORY.getByCondition(new Participant(), whereQuerySection);
        if (participants != null) {
            list = participants;
        }
    }
}
