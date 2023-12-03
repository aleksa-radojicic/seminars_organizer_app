/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.educational_institution;

import com.fon.common.domain.EducationalInstitution;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 *
 * @author Aleksa
 */
public class GetAllEducationalInstitutionsSOTest {

    private GetAllEducationalInstitutionsSO gaeiSO;

    @BeforeEach
    void setUp() {
        gaeiSO = new GetAllEducationalInstitutionsSO();
    }

    @AfterEach
    void tearDown() {
        gaeiSO = null;
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation() {
        try {
            assertDoesNotThrow(() -> gaeiSO.executeOperation(null));

            List<EducationalInstitution> element = gaeiSO.getElement();

            assertEquals(false, element.isEmpty());
        } catch (Exception ex) {
            Logger.getLogger(GetAllEducationalInstitutionsSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }
}
