/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.participant;

import com.fon.common.domain.Admin;
import com.fon.common.domain.Participant;
import com.fon.common.domain.Sex;
import com.fon.common.utils.Utility;
import java.text.ParseException;
import java.util.Date;
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
    private final int participantID = 5;
    private final String name = "Aleksa";
    private final String surname = "Radojicic";
    private Date dateBirth;
    private final int createdByAdminID = 1;
    private final Admin createdByAdmin = new Admin(createdByAdminID);

    private final int filterID = participantID;

    @BeforeEach
    void setUp() throws ParseException {
        dateBirth = Utility.DATE_FORMAT.parse("28.03.2000");
        gpbidSO = new GetParticipantByIDSO();
    }

    @AfterEach
    void tearDown() {
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

            assertEquals(participantID, pOther.getParticipantID());
            assertEquals(name, pOther.getName());
            assertEquals(surname, pOther.getSurname());
            assertEquals(Sex.MALE, pOther.getSex());
            assertEquals(dateBirth, pOther.getDateBirth());
            assertEquals(null, pOther.getCreatedByAdmin());
        } catch (Exception ex) {
            Logger.getLogger(GetParticipantByIDSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }
}
