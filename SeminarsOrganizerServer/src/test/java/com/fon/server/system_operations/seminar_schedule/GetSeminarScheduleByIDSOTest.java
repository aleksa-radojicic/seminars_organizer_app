/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar_schedule;

import com.fon.common.domain.SeminarSchedule;
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
public class GetSeminarScheduleByIDSOTest {

    private GetSeminarScheduleByIDSO gssbidSO;
    private SeminarSchedule seminarSchedule;
    private int filterID;

    @BeforeEach
    void setUp() throws ParseException {
        gssbidSO = new GetSeminarScheduleByIDSO();
        seminarSchedule = (SeminarSchedule) IOJson.deserializeJson("seminar_schedule", SeminarSchedule.class);
        seminarSchedule.getSeminarEnrollments().forEach(x -> x.setSeminarSchedule(seminarSchedule));
        filterID = seminarSchedule.getSeminarScheduleID();
    }

    @AfterEach
    void tearDown() {
        seminarSchedule = null;
        gssbidSO = null;
    }

    @Test
    void test_preconditions() {
        assertDoesNotThrow(() -> gssbidSO.preconditions(filterID));
    }

    @Test
    void test_preconditions_dClass() {
        assertThrowsExactly(Exception.class,
                () -> gssbidSO.preconditions(new Exception()));
    }

    @Test
    void test_preconditions_null() {
        assertThrowsExactly(Exception.class,
                () -> gssbidSO.preconditions(null));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_doesntExist() {
        try {
            assertDoesNotThrow(() -> gssbidSO.execute(-filterID));

            assertEquals(gssbidSO.getSeminarSchedule(), null);
        } catch (Exception ex) {
            Logger.getLogger(GetSeminarScheduleByIDSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation() {
        try {
            assertDoesNotThrow(() -> gssbidSO.execute(filterID));

            SeminarSchedule ssOther = gssbidSO.getSeminarSchedule();

            assertEquals(true, seminarSchedule.equalsAll(ssOther));
        } catch (Exception ex) {
            Logger.getLogger(GetSeminarScheduleByIDSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }
}
