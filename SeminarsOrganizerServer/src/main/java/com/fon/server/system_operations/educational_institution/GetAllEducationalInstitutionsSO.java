/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.educational_institution;

import com.fon.common.domain.EducationalInstitution;
import java.util.List;
import com.fon.server.system_operations.AbstractSO;

/**
 * System operation used for retrieving all educational institutions from the
 * database.
 *
 * <p>
 * Extends abstract system operation class {@code AbstractSO}.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class GetAllEducationalInstitutionsSO extends AbstractSO {

    /**
     * All educational institutions retrieved from the database as
     * {@code List<EducationalInstitution>}.
     */
    private List<EducationalInstitution> element;

    /**
     * No preconditions are checked.
     */
    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    /**
     * Retrieves all educational institutions from the database and stores it in
     * {@code element} attribute.
     *
     * @param arg Not used.
     * @throws Exception When an error happened while retrieving all educational
     * institutions.
     */
    @Override
    protected void executeOperation(Object arg) throws Exception {
        element = REPOSITORY.getByCondition(new EducationalInstitution(), "");
    }

    /**
     * Getter for element.
     *
     * @return All educational institutions retrieved from the database as
     * {@code List<EducationalInstitution>}.
     */
    public List<EducationalInstitution> getElement() {
        return element;
    }
}
