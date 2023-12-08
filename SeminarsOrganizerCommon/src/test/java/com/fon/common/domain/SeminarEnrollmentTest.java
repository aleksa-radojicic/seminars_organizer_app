/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import com.fon.common.utils.IOJson;
import com.fon.common.utils.Utility;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.junit.jupiter.params.provider.MethodSource;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Aleksa
 */
public class SeminarEnrollmentTest extends GenericEntityTest {

    private static SeminarEnrollment se;

    private static List<SeminarEnrollment> seminarEnrollments;

    private static SeminarSchedule seminarScheduleOther;
    private static Participant participantOther;
    private static String notesOther;

    private static void initializeSeminarEnrollment() {
        //Get all seminar enrollments from JSON
        SeminarEnrollment[] seminarEnrollmentsArray = (SeminarEnrollment[]) IOJson.deserializeJson("seminar_enrollments", SeminarEnrollment[].class);
        seminarEnrollments = new LinkedList(Arrays.asList(seminarEnrollmentsArray));

        se = seminarEnrollments.get(0);

        seminarScheduleOther = new SeminarSchedule(se.getSeminarSchedule().getSeminarScheduleID() + 1);
        participantOther = new Participant(se.getParticipant().getParticipantID() + 1);
        notesOther = se.getNotes() + Utility.STRING_OTHER;
    }

    @BeforeEach
    void setUp() {
        initializeSeminarEnrollment();
        genericEntity = se;
    }

    @AfterEach
    void tearDown() {
        se = null;
        genericEntity = null;
    }

    @Test
    void test_getAttributeNames() {
        super.test_getAttributeNames("seminarScheduleID, participantID, notes");
    }

    @Test
    void test_getAttributeValues() {
        super.test_getAttributeValues(String.format("%d, %d, '%s'",
                se.getSeminarSchedule().getSeminarScheduleID(),
                se.getParticipant().getParticipantID(),
                se.getNotes()));
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

            when(rs.getInt("participantID")).thenReturn(se.getParticipant().getParticipantID());
            when(rs.getString("name")).thenReturn(se.getParticipant().getName());
            when(rs.getString("surname")).thenReturn(se.getParticipant().getSurname());
            when(rs.getString("sex")).thenReturn(se.getParticipant().getSex().toString());
            when(rs.getDate("dateBirth")).thenReturn(new java.sql.Date(se.getParticipant().getDateBirth().getTime()));
            when(rs.getString("notes")).thenReturn(se.getNotes());

            SeminarEnrollment seFromRS = (SeminarEnrollment) se.getEntityFromResultSet(rs);
            //Note: from db seminarSchedule is not returned and thus why seminarSchedule is added like this.
            seFromRS.setSeminarSchedule(new SeminarSchedule(se.getSeminarSchedule().getSeminarScheduleID()));

            assertEquals(true, se.equalsAll(seFromRS));
        } catch (SQLException ex) {
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
        assertEquals(State.UNCHANGED, se.getState());
    }

    //tests for class specific methods
    @Test
    void test_setSeminarSchedule() {
        se.setSeminarSchedule(seminarScheduleOther);
        assertEquals(se.getSeminarSchedule(), seminarScheduleOther);
    }

    @Test
    void test_setSeminarSchedule_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> se.setSeminarSchedule(null));
    }

    @Test
    void test_setParticipant() {
        se.setParticipant(participantOther);
        assertEquals(se.getParticipant(), participantOther);
    }

    @Test
    void test_setParticipant_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> se.setParticipant(null));
    }

    @Test
    void test_setNotes() {
        se.setNotes(notesOther);
        assertEquals(se.getNotes(), notesOther);
    }

    @Test
    void test_setNotes_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> se.setNotes(null));
    }

    @Test
    void test_setNotes_tooLong() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> se.setNotes(Utility.STRING_101_LENGTH));
    }

    @Test
    void test_setState() {
        se.setState(State.CREATED);
        assertEquals(State.CREATED, se.getState());
    }

    @Test
    void test_setState_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> se.setState(null));
    }

    @Test
    void test_equals_sameObject() {
        assertTrue(se.equals(se));
    }

    @Test
    void test_equals_null() {
        assertFalse(se.equals(null));
    }

    @Test
    void test_equals_differentClass() {
        assertFalse(se.equals(new Exception()));
    }

    /*
    Cases checked:
    -> all equal
    -> all equal but seminarSchedule
    -> all equal but participant
    -> all equal but notes
     */
    @ParameterizedTest
    @MethodSource("equalsProvider")
    void test_equals(SeminarEnrollment seOther, boolean result) {
        assertEquals(result, se.equals(seOther));
    }

    static Stream<Arguments> equalsProvider() throws Exception {
        initializeSeminarEnrollment();
        return Stream.of(
                arguments(Utility.clone(se), true),
                arguments(cloneSeminarEnrollmentAndModify("seminarSchedule"), false),
                arguments(cloneSeminarEnrollmentAndModify("participant"), false),
                arguments(cloneSeminarEnrollmentAndModify("notes"), true)
        );
    }

    @Test
    void test_equalsAll_sameObject() {
        assertTrue(se.equalsAll(se));
    }

    @Test
    void test_equalsAll_null() {
        assertFalse(se.equalsAll(null));
    }

    @Test
    void test_equalsAll_differentClass() {
        assertFalse(se.equalsAll(new Exception()));
    }

    @ParameterizedTest
    @MethodSource("equalsAllProvider")
    void test_equalsAll(SeminarEnrollment seOther, boolean result) {
        assertEquals(result, se.equalsAll(seOther));
    }

    static Stream<Arguments> equalsAllProvider() throws Exception {
        initializeSeminarEnrollment();
        return Stream.of(
                arguments(Utility.clone(se), true),
                arguments(cloneSeminarEnrollmentAndModify("seminarSchedule"), false),
                arguments(cloneSeminarEnrollmentAndModify("participant"), false),
                arguments(cloneSeminarEnrollmentAndModify("notes"), false)
        );
    }

    public static SeminarEnrollment cloneSeminarEnrollmentAndModify(String attribute) throws IOException, ClassNotFoundException {
        SeminarEnrollment modifiedSe = Utility.clone(se);

        switch (attribute) {
            case "seminarSchedule" ->
                modifiedSe.setSeminarSchedule(seminarScheduleOther);
            case "participant" ->
                modifiedSe.setParticipant(participantOther);
            case "notes" ->
                modifiedSe.setNotes(notesOther);
            default ->
                throw new AssertionError();
        }
        return modifiedSe;
    }

    @ParameterizedTest
    @MethodSource("staticEqualsAllProvider")
    void test_staticEqualsAll(List<SeminarEnrollment> seminarEnrollmentsOther, boolean result) {
        assertEquals(result, SeminarEnrollment.equalsAll(seminarEnrollments, seminarEnrollmentsOther));
    }

    /*
    Cases checked:
    -> all equal
    -> sizes differ - other is larger
    -> sizes differ - other is smaller
    -> all equal but seminarSchedule
    -> all equal but participant
    -> all equal but notes
     */
    static Stream<Arguments> staticEqualsAllProvider() throws Exception {
        initializeSeminarEnrollment();
        return Stream.of(
                arguments(Utility.clone(seminarEnrollments), true),
                arguments(cloneSeminarEnrollmentsListAndModify("larger"), false),
                arguments(cloneSeminarEnrollmentsListAndModify("smaller"), false),
                arguments(cloneSeminarEnrollmentsListAndModify("seminarSchedule"), false),
                arguments(cloneSeminarEnrollmentsListAndModify("participant"), false),
                arguments(cloneSeminarEnrollmentsListAndModify("notes"), false)
        );
    }

    public static List<SeminarEnrollment> cloneSeminarEnrollmentsListAndModify(String attribute) throws IOException, ClassNotFoundException {
        List<SeminarEnrollment> modifiedSes = Utility.clone(seminarEnrollments);

        switch (attribute) {
            case "larger" ->
                modifiedSes.add(new SeminarEnrollment(new SeminarSchedule(1), participantOther, notesOther));
            case "smaller" ->
                modifiedSes.remove(0);
            case "seminarSchedule" ->
                modifiedSes.get(0).setSeminarSchedule(seminarScheduleOther);
            case "participant" ->
                modifiedSes.get(0).setParticipant(participantOther);
            case "notes" ->
                modifiedSes.get(0).setNotes(notesOther);
            default ->
                throw new AssertionError();
        }
        return modifiedSes;
    }

    @Test
    void test_toString() {
        String seminarEnrollmentString = se.toString();
        assertTrue(seminarEnrollmentString.contains(se.getSeminarSchedule().toString()));
        assertTrue(seminarEnrollmentString.contains(se.getParticipant().toString()));
        assertTrue(seminarEnrollmentString.contains(se.getNotes()));
        assertTrue(seminarEnrollmentString.contains(State.UNCHANGED.toString()));
    }
}
