/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.educational_institution;

import com.fon.common.domain.EducationalInstitution;
import java.util.List;
import com.fon.server.system_operations.AbstractSO;

/**
 *
 * @author Aleksa
 */
public class GetAllEducationalInstitutionsSO extends AbstractSO {

    private List<EducationalInstitution> element;

    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        element = REPOSITORY.getByCondition(new EducationalInstitution(), "");
    }

    public List<EducationalInstitution> getElement() {
        return element;
    }
}
