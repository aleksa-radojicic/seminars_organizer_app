/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar;

import com.fon.common.domain.Seminar;
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
public class GetSeminarByIDSOTest {

    private GetSeminarByIDSO gsbidSO;
    private Seminar seminar;
    private int filterID;

    @BeforeEach
    void setUp() throws ParseException {
        gsbidSO = new GetSeminarByIDSO();

        seminar = (Seminar) IOJson.deserializeJson("seminar", Seminar.class);
        seminar.getSeminarTopics().forEach(x -> x.setSeminar(seminar));

        filterID = seminar.getSeminarID();
    }

    @AfterEach
    void tearDown() {
        gsbidSO = null;
        seminar = null;
    }

    @Test
    void test_preconditions() {
        assertDoesNotThrow(() -> gsbidSO.preconditions(filterID));
    }

    @Test
    void test_preconditions_dClass() {
        assertThrowsExactly(Exception.class,
                () -> gsbidSO.preconditions(new Exception()));
    }

    @Test
    void test_preconditions_null() {
        assertThrowsExactly(Exception.class,
                () -> gsbidSO.preconditions(null));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_doesntExist() {
        try {
            assertDoesNotThrow(() -> gsbidSO.execute(-filterID));

            assertEquals(gsbidSO.getSeminar(), null);
        } catch (Exception ex) {
            Logger.getLogger(GetSeminarByIDSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation() {
        try {
            assertDoesNotThrow(() -> gsbidSO.execute(filterID));

            Seminar sOther = gsbidSO.getSeminar();

            assertEquals(true, seminar.equalsAll(sOther));
        } catch (Exception ex) {
            Logger.getLogger(GetSeminarByIDSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }
}
