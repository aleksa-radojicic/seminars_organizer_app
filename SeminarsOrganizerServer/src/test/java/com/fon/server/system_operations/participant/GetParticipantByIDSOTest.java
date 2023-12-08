/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.participant;

import com.fon.common.domain.Participant;
import com.fon.common.utils.IOJson;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 *
 * @author Aleksa
 */
public class GetParticipantByIDSOTest {

    private GetParticipantByIDSO gpbidSO;
    private Participant participant;
    private int filterID;

    @BeforeEach
    void setUp() throws ParseException {
        participant = (Participant) IOJson.deserializeJson("participant", Participant.class);
        filterID = participant.getParticipantID();
        gpbidSO = new GetParticipantByIDSO();
    }

    @AfterEach
    void tearDown() {
        participant = null;
        gpbidSO = null;
    }

    @Test
    void test_preconditions() {
        assertDoesNotThrow(() -> gpbidSO.preconditions(filterID));
    }

    @Test
    void test_preconditions_dClass() {
        assertThrowsExactly(Exception.class,
                () -> gpbidSO.preconditions(new Exception()));
    }

    @Test
    void test_preconditions_null() {
        assertThrowsExactly(Exception.class,
                () -> gpbidSO.preconditions(null));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_doesntExist() {
        try {
            assertDoesNotThrow(() -> gpbidSO.execute(-filterID));

            assertEquals(gpbidSO.getElement(), null);
        } catch (Exception ex) {
            Logger.getLogger(GetParticipantByIDSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation() {
        try {
            assertDoesNotThrow(() -> gpbidSO.execute(filterID));

            Participant pOther = gpbidSO.getElement();

            assertEquals(participant.getParticipantID(), pOther.getParticipantID());
            assertEquals(participant.getName(), pOther.getName());
            assertEquals(participant.getSurname(), pOther.getSurname());
            assertEquals(participant.getSex(), pOther.getSex());
            assertEquals(participant.getDateBirth(), pOther.getDateBirth());
            assertEquals(null, pOther.getCreatedByAdmin());
        } catch (Exception ex) {
            Logger.getLogger(GetParticipantByIDSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }
}
