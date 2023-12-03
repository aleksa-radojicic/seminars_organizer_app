/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar;

import com.fon.common.domain.Seminar;
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
public class GetAllSeminarsSOTest {

    private GetAllSeminarsSO gasSO;

    @BeforeEach
    void setUp() {
        gasSO = new GetAllSeminarsSO();
    }

    @AfterEach
    void tearDown() {
        gasSO = null;
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation() {
        try {
            assertDoesNotThrow(() -> gasSO.executeOperation(null));

            List<Seminar> allSeminars = gasSO.getAllSeminars();

            assertEquals(false, allSeminars.isEmpty());
        } catch (Exception ex) {
            Logger.getLogger(GetAllSeminarsSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }
}
