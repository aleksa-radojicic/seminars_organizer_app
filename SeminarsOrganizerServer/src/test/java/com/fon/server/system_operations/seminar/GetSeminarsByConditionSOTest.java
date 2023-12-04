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
public class GetSeminarsByConditionSOTest {

    private GetSeminarsByConditionSO gsbcSO;

    @BeforeEach
    void setUp() {
        gsbcSO = new GetSeminarsByConditionSO();
    }

    @AfterEach
    void tearDown() {
        gsbcSO = null;
    }

    @Test
    void test_preconditions() {
        assertDoesNotThrow(() -> gsbcSO.preconditions("filter"));
    }

    @Test
    void test_preconditions_dClass() {
        assertThrowsExactly(Exception.class,
                () -> gsbcSO.preconditions(new Exception()));
    }

    @Test
    void test_preconditions_null() {
        assertThrowsExactly(Exception.class,
                () -> gsbcSO.preconditions(null));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_doesntExist() {
        try {
            assertDoesNotThrow(() -> gsbcSO.execute("noSeminarsWillBeFound"));

            assertEquals(gsbcSO.getSeminars().isEmpty(), true);
        } catch (Exception ex) {
            Logger.getLogger(GetSeminarsByConditionSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation() {
        try {
            String nameFilter = "model";

            assertDoesNotThrow(() -> gsbcSO.execute(nameFilter));

            List<Seminar> list = gsbcSO.getSeminars();

            assertEquals(list.isEmpty(), false);
            assertEquals(true, isValidParticipants(list, nameFilter));
        } catch (Exception ex) {
            Logger.getLogger(GetSeminarsByConditionSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    private boolean isValidParticipants(List<Seminar> list, String nameOrSurnameFilter) {
        for (Seminar s : list) {
            if (!s.getName().contains(nameOrSurnameFilter)) {
                return false;
            }
        }
        return true;
    }
}
