/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import com.fon.common.utils.IOJson;
import com.fon.common.utils.Utility;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
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
import java.io.IOException;

/**
 *
 * @author Aleksa
 */
public class ParticipantTest extends GenericEntityTest {

    private static Participant participant;

    private static int participantIDOther;
    private static String nameOther;
    private static String surnameOther;
    private static Sex sexOther;
    private static Date dateBirthOther;
    private static Admin createdByAdminOther;

    public static void initializeParticipant() throws ParseException {
        participant = (Participant) IOJson.deserializeJson("participant", Participant.class);

        participantIDOther = participant.getParticipantID() + 1;
        nameOther = participant.getName() + Utility.STRING_OTHER;
        surnameOther = participant.getSurname() + Utility.STRING_OTHER;
        sexOther = Sex.FEMALE;
        dateBirthOther = Utility.DATE_FORMAT.parse("30.06.2002");
        createdByAdminOther = new Admin(participant.getCreatedByAdmin().getAdminID() + 1);
    }

    @BeforeEach
    void setUp() throws ParseException {
        initializeParticipant();
        genericEntity = participant;
    }

    @AfterEach
    void tearDown() {
        participant = null;
        genericEntity = null;
    }

    @Test
    void test_getAttributeNames() {
        super.test_getAttributeNames("participantID, name, surname, sex, dateBirth, createdByAdminID");
    }

    @Test
    void test_getAttributeValues() {
        super.test_getAttributeValues(String.format("%d, '%s', '%s', '%s', '%s', %d",
                participant.getParticipantID(),
                participant.getName(),
                participant.getSurname(),
                participant.getSex().toString(),
                new java.sql.Date(participant.getDateBirth().getTime()).toString(),
                participant.getCreatedByAdmin().getAdminID()));
    }

    @Test
    void test_getID() {
        super.test_getID(participant.getParticipantID());
    }

    @Test
    void test_setID() {
        super.test_setID(participant.getParticipantID());
    }

    @Test
    void test_setAttributeValues() {
        super.test_setAttributeValues_notImplemented();
    }

    @Test
    void test_getSelectAllQuery() {
        super.test_getSelectAllQuery("SELECT * from participants");
    }

    @Test
    void test_getEntityFromResultSet() {
        try {
            ResultSet rs = mock(ResultSet.class);

            when(rs.getInt("participantID")).thenReturn(participant.getParticipantID());
            when(rs.getString("name")).thenReturn(participant.getName());
            when(rs.getString("surname")).thenReturn(participant.getSurname());
            when(rs.getString("sex")).thenReturn(participant.getSex().toString());
            when(rs.getDate("dateBirth")).thenReturn(new java.sql.Date(participant.getDateBirth().getTime()));

            Participant participantFromRS = (Participant) participant.getEntityFromResultSet(rs);
            assertEquals(participant.getParticipantID(), participantFromRS.getParticipantID());
            assertEquals(participant.getName(), participantFromRS.getName());
            assertEquals(participant.getSurname(), participantFromRS.getSurname());
            assertEquals(participant.getSex(), participantFromRS.getSex());
            assertEquals(participant.getDateBirth(), participantFromRS.getDateBirth());

        } catch (SQLException ex) {
            Logger.getLogger(ParticipantTest.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    void test_getQueryCondition() {
        super.test_getQueryCondition(String.format("participantID = %d ", participant.getParticipantID()));
    }

    @Test
    void test_getState() {
        super.test_getState_notImplemented();
    }

    //tests for class specific methods
    @Test
    void test_setParticipantID() {
        participant.setParticipantID(participantIDOther);
        assertEquals(participant.getParticipantID(), participantIDOther);
    }

    @Test
    void test_setName() {
        participant.setName(nameOther);
        assertEquals(participant.getName(), nameOther);
    }

    @Test
    void test_setName_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> participant.setName(null));
    }

    @Test

    void test_setName_empty() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> participant.setName(Utility.STRING_EMPTY));
    }

    @Test
    void test_setName_tooLong() {
        String longName = Utility.STRING_31_LENGTH;
        assertThrowsExactly(IllegalArgumentException.class,
                () -> participant.setName(longName));
    }

    @Test
    void test_setSurname() {
        participant.setSurname(surnameOther);
        assertEquals(participant.getSurname(), surnameOther);
    }

    @Test
    void test_setSurname_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> participant.setSurname(null));
    }

    @Test

    void test_setSurname_empty() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> participant.setSurname(Utility.STRING_EMPTY));
    }

    @Test
    void test_setSurname_tooLong() {
        String longSurname = Utility.STRING_31_LENGTH;
        assertThrowsExactly(IllegalArgumentException.class,
                () -> participant.setSurname(longSurname));
    }

    @Test
    void test_setSex() {
        participant.setSex(sexOther);
        assertEquals(participant.getSex(), sexOther);
    }

    @Test
    void test_setDateBirth() {
        participant.setDateBirth(dateBirthOther);
        assertEquals(participant.getDateBirth(), dateBirthOther);
    }

    @Test
    void test_setDateBirth_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> participant.setDateBirth(null));
    }

    @Test
    void test_setDateBirth_futureDate() throws ParseException {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> participant.setDateBirth(Utility.GET_DATE_FUTURE()));
    }

    @Test
    void test_setCreatedByAdmin() {
        participant.setCreatedByAdmin(createdByAdminOther);
        assertEquals(participant.getCreatedByAdmin(), createdByAdminOther);
    }

    @Test
    void test_setCreatedByAdmin_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> participant.setCreatedByAdmin(null));
    }

    @Test
    void test_equals_sameObject() {
        assertTrue(participant.equals(participant));
    }

    @Test
    void test_equals_null() {
        assertFalse(participant.equals(null));
    }

    @Test
    void test_equals_differentClass() {
        assertFalse(participant.equals(new Exception()));
    }

    /*
    Cases checked:
    -> all equal
    -> all equal but participantID
    -> all equal but name
    -> all equal but surname
    -> all equal but sex
    -> all equal but dateBirth  
    -> all equal but createdByAdminID  
     */
    @ParameterizedTest
    @MethodSource("equalsProvider")
    void test_equals(Participant pOther, boolean result) {
        assertEquals(result, participant.equals(pOther));
    }

    static Stream<Arguments> equalsProvider() throws Exception {
        initializeParticipant();
        return Stream.of(
                arguments(Utility.clone(participant), true),
                arguments(cloneParticipantAndModify("participantID"), false),
                arguments(cloneParticipantAndModify("name"), true),
                arguments(cloneParticipantAndModify("surname"), true),
                arguments(cloneParticipantAndModify("sex"), true),
                arguments(cloneParticipantAndModify("dateBirth"), true),
                arguments(cloneParticipantAndModify("createdByAdminID"), true)
        );
    }

    public static Participant cloneParticipantAndModify(String attribute) throws IOException, ClassNotFoundException, ParseException {
        Participant modifiedParticipant = Utility.clone(participant);

        switch (attribute) {
            case "participantID" ->
                modifiedParticipant.setParticipantID(participantIDOther);
            case "name" ->
                modifiedParticipant.setName(nameOther);
            case "surname" ->
                modifiedParticipant.setSurname(surnameOther);
            case "sex" ->
                modifiedParticipant.setSex(sexOther);
            case "dateBirth" ->
                modifiedParticipant.setDateBirth(dateBirthOther);
            case "createdByAdminID" ->
                modifiedParticipant.setCreatedByAdmin(createdByAdminOther);
            default ->
                throw new AssertionError();
        }
        return modifiedParticipant;
    }

    @Test
    void test_toString() {
        String participantString = participant.toString();
        assertTrue(participantString.contains(participant.getName()));
        assertTrue(participantString.contains(participant.getSurname()));
        assertTrue(participantString.contains("м, ") || participantString.contains("ж, "));
        assertTrue(participantString.contains(participant.getAge().toString()));
    }
}
