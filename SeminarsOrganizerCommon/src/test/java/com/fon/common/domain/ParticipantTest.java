/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import com.fon.common.utils.Utility;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Aleksa
 */
public class ParticipantTest extends GenericEntityTest {

    private Participant participant;
    private final int ID = 1;
    private final String IDString = ID + "";
    private final String name = "name";
    private final String surname = "surname";
    private final Sex sex = Sex.MALE;
    private final String sexString = "MALE";
    private Date dateBirth;
    private final String dateBirthString = "01.01.1980";
    private final Admin createdByAdmin = new Admin();
    private final String createdByAdminIDString = "1";

    private final int IDOther = 2;
    private final String IDStringOther = IDOther + "";
    private final String nameOther = "nameOther";
    private final String surnameOther = "surnameOther";
    private final String sexStringOther = "FEMALE";
    private final String dateBirthStringOther = "02.02.1999";
    private final String createdByAdminIDStringOther = "2";

    @BeforeEach
    void setUp() throws ParseException {
        dateBirth = Utility.DATE_FORMAT.parse(dateBirthString);
        participant = new Participant(ID, name, surname, sex, dateBirth, createdByAdmin);
        genericEntity = participant;
    }

    @AfterEach
    void tearDown() {
        participant = null;
    }

    @Test
    void test_getAttributeNames() {
        super.test_getAttributeNames("participantID, name, surname, sex, dateBirth, createdByAdminID");
    }

    @Test
    void test_getAttributeValues() {
        super.test_getAttributeValues(String.format("%d, '%s', '%s', '%s', '%s', %d",
                ID, name, surname, sex, new java.sql.Date(dateBirth.getTime()).toString(), createdByAdmin.getAdminID()));
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

            when(rs.getInt("participantID")).thenReturn(ID);
            when(rs.getString("name")).thenReturn(name);
            when(rs.getString("surname")).thenReturn(surname);
            when(rs.getString("sex")).thenReturn(sex.toString());
            when(rs.getDate("dateBirth")).thenReturn(new java.sql.Date(dateBirth.getTime()));

            Participant participantFromRS = (Participant) participant.getEntityFromResultSet(rs);
            assertEquals(ID, participantFromRS.getParticipantID());
            assertEquals(name, participantFromRS.getName());
            assertEquals(surname, participantFromRS.getSurname());
            assertEquals(sex, participantFromRS.getSex());
            assertEquals(dateBirth, participantFromRS.getDateBirth());
        } catch (SQLException ex) {
            Logger.getLogger(ParticipantTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    void test_getQueryCondition() {
        super.test_getQueryCondition(String.format("participantID = %d ", ID));
    }

    @Test
    void test_getState() {
        super.test_getState_notImplemented();
    }

    //tests for class specific methods
    @Test
    void test_setParticipantID() {
        participant.setParticipantID(ID);
        assertEquals(participant.getParticipantID(), ID);
    }

    @Test
    void test_setName() {
        participant.setName(name);
        assertEquals(participant.getName(), name);
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
        participant.setSurname(surname);
        assertEquals(participant.getSurname(), surname);
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
        participant.setSex(sex);
        assertEquals(participant.getSex(), sex);
    }

    @Test
    void test_setDateBirth() {
        participant.setDateBirth(dateBirth);
        assertEquals(participant.getDateBirth(), dateBirth);
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
        participant.setCreatedByAdmin(createdByAdmin);
        assertEquals(participant.getCreatedByAdmin(), createdByAdmin);
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

    @ParameterizedTest
    @CsvSource({
        //all equal
        IDString + "," + name + "," + surname + "," + sexString + "," + dateBirthString + "," + createdByAdminIDString + ",true",
        //all equal but educationalInstitutionID
        IDStringOther + "," + name + "," + surname + "," + sexString + "," + dateBirthString + "," + createdByAdminIDString + ",false",
        //all equal but name
        IDString + "," + nameOther + "," + surname + "," + sexString + "," + dateBirthString + "," + createdByAdminIDString + ",true",
        //all equal but surname
        IDString + "," + name + "," + surnameOther + "," + sexString + "," + dateBirthString + "," + createdByAdminIDString + ",true",
        //all equal but sex
        IDString + "," + name + "," + surname + "," + sexStringOther + "," + dateBirthString + "," + createdByAdminIDString + ",true",
        //all equal but dateBirth
        IDString + "," + name + "," + surname + "," + sexString + "," + dateBirthStringOther + "," + createdByAdminIDString + ",true",
        //all equal but createdByAdminID
        IDString + "," + name + "," + surname + "," + sexString + "," + dateBirthString + "," + createdByAdminIDStringOther + ",true"
    })
    void test_equals(int ID2, String name2, String surname2, String sex2, String dateBirth2, String createdByAdminID2,
            boolean result) {

        try {
            Admin admin2 = new Admin();
            admin2.setAdminID(Integer.parseInt(createdByAdminID2));

            Participant participantOther = new Participant(ID2, name2, surname2, Sex.valueOf(sex2),
                    Utility.DATE_FORMAT.parse(dateBirth2), admin2);

            assertEquals(result,
                    participant.equals(participantOther));
        } catch (ParseException ex) {
            Logger.getLogger(ParticipantTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    void test_toString() {
        String participantString = participant.toString();
        assertTrue(participantString.contains(name));
        assertTrue(participantString.contains(surname));
        assertTrue(participantString.contains("м, ") || participantString.contains("ж, "));
        assertTrue(participantString.contains(participant.getAge().toString()));
    }
}
