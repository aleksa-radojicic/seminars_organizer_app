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
public class GetParticipantsByConditionSOTest {

    private GetParticipantsByConditionSO gpbcSO;

    @BeforeEach
    void setUp() {
        gpbcSO = new GetParticipantsByConditionSO();
    }

    @AfterEach
    void tearDown() {
        gpbcSO = null;
    }

    @Test
    void test_preconditions() {
        assertDoesNotThrow(() -> gpbcSO.preconditions("filter"));
    }

    @Test
    void test_preconditions_dClass() {
        assertThrowsExactly(Exception.class,
                () -> gpbcSO.preconditions(new Exception()));
    }

    @Test
    void test_preconditions_null() {
        assertThrowsExactly(Exception.class,
                () -> gpbcSO.preconditions(null));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_doesntExist() {
        try {
            assertDoesNotThrow(() -> gpbcSO.execute("noParticipantsWillBeFound"));

            assertEquals(gpbcSO.getList().isEmpty(), true);
        } catch (Exception ex) {
            Logger.getLogger(GetParticipantsByConditionSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_nameFilter() {
        try {
            String nameFilter = "leks";

            assertDoesNotThrow(() -> gpbcSO.execute(nameFilter));

            List<Participant> list = gpbcSO.getList();

            assertEquals(gpbcSO.getList().isEmpty(), false);
            assertEquals(true, isValidParticipants(list, nameFilter));
        } catch (Exception ex) {
            Logger.getLogger(GetParticipantsByConditionSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_surnameFilter() {
        try {
            String surnameFilter = "ojic";

            assertDoesNotThrow(() -> gpbcSO.execute(surnameFilter));

            List<Participant> list = gpbcSO.getList();

            assertEquals(gpbcSO.getList().isEmpty(), false);
            assertEquals(true, isValidParticipants(list, surnameFilter));

        } catch (Exception ex) {
            Logger.getLogger(GetParticipantsByConditionSOTest.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_nameOrSurnameFilter() {
        try {
            String nameOrSurnameFilter = "ilan";

            assertDoesNotThrow(() -> gpbcSO.execute(nameOrSurnameFilter));

            List<Participant> list = gpbcSO.getList();

            assertEquals(gpbcSO.getList().isEmpty(), false);
            assertEquals(true, isValidParticipants(list, nameOrSurnameFilter));

        } catch (Exception ex) {
            Logger.getLogger(GetParticipantsByConditionSOTest.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    private boolean isValidParticipants(List<Participant> list, String nameOrSurnameFilter) {
        for (Participant p : list) {
            if (!p.getName().contains(nameOrSurnameFilter) && !p.getSurname().contains(nameOrSurnameFilter)) {
                return false;
            }
        }
        return true;
    }
}
