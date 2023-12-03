/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.participant;

import com.fon.common.domain.Participant;
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
public class GetAllParticipantsSOTest {

    private GetAllParticipantsSO gapSO;

    @BeforeEach
    void setUp() {
        gapSO = new GetAllParticipantsSO();
    }

    @AfterEach
    void tearDown() {
        gapSO = null;
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation() {
        try {
            assertDoesNotThrow(() -> gapSO.executeOperation(null));

            List<Participant> allParticipants = gapSO.getAllParticipants();

            assertEquals(false, allParticipants.isEmpty());
        } catch (Exception ex) {
            Logger.getLogger(GetAllParticipantsSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }
}
