/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import com.fon.common.utils.Utility;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Aleksa
 */
public class SeminarEnrollmentTest extends GenericEntityTest {

    private static SeminarEnrollment seminarEnrollment;

    private static final SeminarSchedule seminarSchedule = new SeminarSchedule();
    private static final int seminarScheduleID = 1;
    private static final String seminarScheduleIDString = "1";
    private static final Participant participant = new Participant();
    private static final int participantID = 1;
    private static final String participantIDString = "1";
    private static final String notes = "notes";

    private static List<SeminarEnrollment> seminarEnrollments;

    private static int seminarScheduleIDOther = 2;
    private static final String seminarScheduleIDStringOther = "2";
    private static final int participantIDOther = 2;
    private static final String participantIDStringOther = "2";
    private static final String notesOther = "notesOther";

    @BeforeEach
    void setUp() {
        seminarSchedule.setSeminarScheduleID(seminarScheduleID);
        participant.setParticipantID(participantID);

        SeminarEnrollment se11 = new SeminarEnrollment(new SeminarSchedule(11), new Participant(11), "notes11");
        SeminarEnrollment se12 = new SeminarEnrollment(new SeminarSchedule(12), new Participant(12), "notes12");
        SeminarEnrollment se13 = new SeminarEnrollment(new SeminarSchedule(13), new Participant(13), "notes13");
        seminarEnrollments = new LinkedList(Arrays.asList(se11, se12, se13));

        seminarEnrollment = new SeminarEnrollment(seminarSchedule, participant, notes);
        genericEntity = seminarEnrollment;
    }

    @AfterEach
    void tearDown() {
        seminarEnrollment = null;
    }

    @Test
    void test_getAttributeNames() {
        super.test_getAttributeNames("seminarScheduleID, participantID, notes");
    }

    @Test
    void test_getAttributeValues() {
        super.test_getAttributeValues(String.format("%d, %d, '%s'",
                seminarSchedule.getSeminarScheduleID(), participant.getParticipantID(), notes));
    }

    @Test
    void test_getID() {
        super.test_getID_notImplemented();
    }

    @Test
    void test_setID() {
        super.test_setID_notImplemented();
    }

    @Test
    void test_setAttributeValues() {
        super.test_setAttributeValues_notImplemented();
    }

    @Test
    void test_getSelectAllQuery() {
        String selectAllQuery = """
               SELECT se.`seminarScheduleID`, se.`participantID`, se.`notes`, p.`name`, p.`surname`, p.`dateBirth`, p.`sex` 
               FROM `seminarenrollments` se JOIN `participants` p ON se.`participantID` = p.`participantID`""";
        super.test_getSelectAllQuery(selectAllQuery);
    }

    @Test
    void test_getEntityFromResultSet() {
        try {
            ResultSet rs = mock(ResultSet.class);

            String name = "name";
            String surname = "surname";
            String sex = "MALE";
            java.sql.Date dateBirth = new java.sql.Date(Utility.DATE_FORMAT.parse("01.01.1980").getTime());

            when(rs.getInt("participantID")).thenReturn(participantID);
            when(rs.getString("name")).thenReturn(name);
            when(rs.getString("surname")).thenReturn(surname);
            when(rs.getString("sex")).thenReturn(sex);
            when(rs.getDate("dateBirth")).thenReturn(dateBirth);
            when(rs.getString("notes")).thenReturn(notes);

            SeminarEnrollment seminarEnrollmentFromRS = (SeminarEnrollment) seminarEnrollment.getEntityFromResultSet(rs);

            assertEquals(participantID, seminarEnrollmentFromRS.getParticipant().getParticipantID());
            assertEquals(name, seminarEnrollmentFromRS.getParticipant().getName());
            assertEquals(surname, seminarEnrollmentFromRS.getParticipant().getSurname());
            assertEquals(sex, seminarEnrollmentFromRS.getParticipant().getSex().toString());
            assertEquals(dateBirth, seminarEnrollmentFromRS.getParticipant().getDateBirth());
            assertEquals(notes, seminarEnrollmentFromRS.getNotes());
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(SeminarEnrollmentTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    void test_getQueryCondition() {
        super.test_getQueryCondition_notImplemented();
    }

    @Test
    void test_getState() {
        assertEquals(State.UNCHANGED, seminarEnrollment.getState());
    }

    //tests for class specific methods
    @Test
    void test_setSeminarSchedule() {
        SeminarSchedule ss = new SeminarSchedule();
        seminarEnrollment.setSeminarSchedule(ss);
        assertEquals(seminarEnrollment.getSeminarSchedule(), ss);
    }

    @Test
    void test_setSeminarSchedule_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminarEnrollment.setSeminarSchedule(null));
    }

    @Test
    void test_setParticipant() {
        Participant p = new Participant();
        seminarEnrollment.setParticipant(p);
        assertEquals(seminarEnrollment.getParticipant(), p);
    }

    @Test
    void test_setParticipant_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminarEnrollment.setParticipant(null));
    }

    @Test
    void test_setNotes() {
        seminarEnrollment.setNotes(notes);
        assertEquals(seminarEnrollment.getNotes(), notes);
    }

    @Test
    void test_setNotes_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminarEnrollment.setNotes(null));
    }

    @Test
    void test_setNotes_tooLong() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> seminarEnrollment.setNotes(Utility.STRING_101_LENGTH));
    }

    @Test
    void test_setState() {
        seminarEnrollment.setState(State.CREATED);
        assertEquals(State.CREATED, seminarEnrollment.getState());
    }

    @Test
    void test_setState_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminarEnrollment.setState(null));
    }

    @Test
    void test_equals_sameObject() {
        assertTrue(seminarEnrollment.equals(seminarEnrollment));
    }

    @Test
    void test_equals_null() {
        assertFalse(seminarEnrollment.equals(null));
    }

    @Test
    void test_equals_differentClass() {
        assertFalse(seminarEnrollment.equals(new Exception()));
    }

    @ParameterizedTest
    @CsvSource({
        //all equal
        seminarScheduleIDString + "," + participantIDString + "," + notes + ",true",
        //all equal but seminarSchedule
        seminarScheduleIDStringOther + "," + participantIDString + "," + notes + ",false",
        //all equal but participant
        seminarScheduleIDString + "," + participantIDStringOther + "," + notes + ",false",
        //all equal but notes
        seminarScheduleIDString + "," + participantIDString + "," + notesOther + ",true"
    })
    void test_equals(int seminarScheduleID2, int participantID2, String notes2,
            boolean result) {

        SeminarSchedule ss2 = new SeminarSchedule();
        ss2.setSeminarScheduleID(seminarScheduleID2);
        Participant p2 = new Participant();
        p2.setParticipantID(participantID2);

        SeminarEnrollment seOther = new SeminarEnrollment(ss2, p2, notes2);

        assertEquals(result,
                seminarEnrollment.equals(seOther));
    }

    @Test
    void test_equalsAll_sameObject() {
        assertTrue(seminarEnrollment.equalsAll(seminarEnrollment));
    }

    @Test
    void test_equalsAll_null() {
        assertFalse(seminarEnrollment.equalsAll(null));
    }

    @Test
    void test_equalsAll_differentClass() {
        assertFalse(seminarEnrollment.equalsAll(new Exception()));
    }

    @ParameterizedTest
    @CsvSource({
        //all equal
        seminarScheduleIDString + "," + participantIDString + "," + notes + ",true",
        //all equal but seminarSchedule
        seminarScheduleIDStringOther + "," + participantIDString + "," + notes + ",false",
        //all equal but participant
        seminarScheduleIDString + "," + participantIDStringOther + "," + notes + ",false",
        //all equal but notes
        seminarScheduleIDString + "," + participantIDString + "," + notesOther + ",false",})
    void test_equalsAll(int seminarScheduleID2, int participantID2, String notes2,
            boolean result) {

        SeminarSchedule ss2 = new SeminarSchedule(seminarScheduleID2);
        Participant p2 = new Participant(participantID2);

        SeminarEnrollment seOther = new SeminarEnrollment(ss2, p2, notes2);

        assertEquals(result,
                seminarEnrollment.equalsAll(seOther));
    }

    @ParameterizedTest
    @MethodSource("seminarEnrollmentStaticEqualsAllsProvider")
    void test_staticEqualsAll(List<SeminarEnrollment> se, List<SeminarEnrollment> seOther, boolean result) {
        assertEquals(result, SeminarEnrollment.equalsAll(se, seOther));
    }

    static Stream<Arguments> seminarEnrollmentStaticEqualsAllsProvider() {
        try {
            SeminarSchedule ss2 = new SeminarSchedule(seminarScheduleIDOther);
            Participant p2 = new Participant(participantIDOther);

            //all equal
            List<SeminarEnrollment> se2_equal = Utility.clone(seminarEnrollments);

            //sizes differ - other is larger
            List<SeminarEnrollment> se2_dSize_otherLarger = Utility.clone(seminarEnrollments);
            SeminarEnrollment se = new SeminarEnrollment(new SeminarSchedule(21), new Participant(21), "notes21");
            se2_dSize_otherLarger.add(se);

            //sizes differ - other is smaller
            List<SeminarEnrollment> se2_dSize_otherSmaller = Utility.clone(seminarEnrollments);
            se2_dSize_otherSmaller.remove(0);

            //all equal but seminarSchedule
            List<SeminarEnrollment> se2_dSeminar = Utility.clone(seminarEnrollments);
            se2_dSeminar.get(0).setSeminarSchedule(ss2);

            //all equal but participant
            List<SeminarEnrollment> se2_dSeminarTopicID = Utility.clone(seminarEnrollments);
            se2_dSeminarTopicID.get(0).setParticipant(p2);

            //all equal but notes
            List<SeminarEnrollment> se2_dName = Utility.clone(seminarEnrollments);
            se2_dName.get(0).setNotes(notesOther);

            return Stream.of(
                    arguments(seminarEnrollments, se2_equal, true),
                    arguments(seminarEnrollments, se2_dSize_otherLarger, false),
                    arguments(seminarEnrollments, se2_dSize_otherSmaller, false),
                    arguments(seminarEnrollments, se2_dSeminar, false),
                    arguments(seminarEnrollments, se2_dSeminarTopicID, false),
                    arguments(seminarEnrollments, se2_dName, false)
            );
        } catch (Exception ex) {
            Logger.getLogger(SeminarEnrollmentTest.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Test
    void test_toString() {
        String seminarEnrollmentString = seminarEnrollment.toString();
        assertTrue(seminarEnrollmentString.contains(seminarSchedule.toString()));
        assertTrue(seminarEnrollmentString.contains(participant.toString()));
        assertTrue(seminarEnrollmentString.contains(notes));
        assertTrue(seminarEnrollmentString.contains(State.UNCHANGED.toString()));
    }
}
