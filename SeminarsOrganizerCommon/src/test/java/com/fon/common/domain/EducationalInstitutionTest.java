/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import com.fon.common.utils.Utility;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class EducationalInstitutionTest extends GenericEntityTest {

    private EducationalInstitution educationalInstitution;
    private final int ID = 1;
    private final String IDString = ID + "";
    private final String name = "fon";
    private final String address = "jove ilica 154";

    private final int IDOther = 2;
    private final String IDStringOther = IDOther + "";
    private final String nameOther = "etf";
    private final String addressOther = "bulevara kralja aleksandra 73";

    @BeforeEach
    void setUp() {
        educationalInstitution = new EducationalInstitution(ID, name, address);
        genericEntity = educationalInstitution;
    }

    @AfterEach
    void tearDown() {
        educationalInstitution = null;
    }

    @Test
    void test_getAttributeNames() {
        super.test_getAttributeNames("educationalInstitutionID, name, address");
    }

    @Test
    void test_getAttributeValues() {
        super.test_getAttributeValues(String.format("%d, '%s', '%s'",
                ID, name, address));
    }

    @Test
    void test_getID() {
        super.test_getID(educationalInstitution.getEducationalInstitutionID());
    }

    @Test
    void test_setID() {
        super.test_setID(educationalInstitution.getEducationalInstitutionID());
    }

    @Test
    void test_setAttributeValues_null() {
        super.test_setAttributeValues_null();
    }

    @Test
    void test_setAttributeValues() {
        super.test_setAttributeValues(String.format("educationalInstitutionID = %d, name = '%s', address = '%s'",
                ID, name, address));
    }

    @Test
    void test_getSelectAllQuery() {
        super.test_getSelectAllQuery("SELECT * from educationalInstitutions");
    }

    @Test
    void test_getEntityFromResultSet() {
        try {
            ResultSet rs = mock(ResultSet.class);

            when(rs.getInt("educationalInstitutionID")).thenReturn(ID);
            when(rs.getString("name")).thenReturn(name);
            when(rs.getString("address")).thenReturn(address);

            EducationalInstitution educationalInstitutionFromRS = (EducationalInstitution) educationalInstitution.getEntityFromResultSet(rs);
            assertEquals(ID, educationalInstitutionFromRS.getEducationalInstitutionID());
            assertEquals(name, educationalInstitutionFromRS.getName());
            assertEquals(address, educationalInstitutionFromRS.getAddress());
        } catch (SQLException ex) {
            Logger.getLogger(EducationalInstitutionTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    void test_getQueryCondition() {
        super.test_getQueryCondition(String.format("educationalInstitutionID = %d ", ID));
    }

    @Test
    void test_getState() {
        super.test_getState_notImplemented();
    }

    //tests for class specific methods
    @Test
    void test_setEducationalInstitutionID() {
        educationalInstitution.setEducationalInstitutionID(ID);
        assertEquals(educationalInstitution.getEducationalInstitutionID(), ID);
    }

    @Test
    void test_setName() {
        educationalInstitution.setName(name);
        assertEquals(educationalInstitution.getName(), name);
    }

    @Test
    void test_setName_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> educationalInstitution.setName(null));
    }

    @Test
    void test_setName_empty() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> educationalInstitution.setName(Utility.STRING_EMPTY));
    }

    @Test
    void test_setName_tooLong() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> educationalInstitution.setName(Utility.STRING_31_LENGTH));
    }

    @Test
    void test_setAddress() {
        educationalInstitution.setAddress(address);
        assertEquals(educationalInstitution.getAddress(), address);
    }

    @Test
    void test_setAddress_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> educationalInstitution.setAddress(null));
    }

    @Test
    void test_setAddress_empty() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> educationalInstitution.setAddress(Utility.STRING_EMPTY));
    }

    @Test
    void test_setAddress_tooLong() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> educationalInstitution.setAddress(Utility.STRING_61_LENGTH));
    }

    @Test
    void test_equals_sameObject() {
        assertTrue(educationalInstitution.equals(educationalInstitution));
    }

    @Test
    void test_equals_null() {
        assertFalse(educationalInstitution.equals(null));
    }

    @Test
    void test_equals_differentClass() {
        assertFalse(educationalInstitution.equals(new Exception()));
    }

    @ParameterizedTest
    @CsvSource({
        //all equal
        IDString + "," + name + "," + address + ",true",
        //all equal but educationalInstitutionID
        IDStringOther + "," + name + "," + address + ",false",
        //all equal but name
        IDString + "," + nameOther + "," + address + ",true",
        //all equal but address
        IDString + "," + name + "," + addressOther + ",true"
    })
    void test_equals(int ID2, String name2, String address2,
            boolean result) {

        EducationalInstitution educationalInstitutionOther = new EducationalInstitution(ID2, name2, address2);

        assertEquals(result,
                educationalInstitution.equals(educationalInstitutionOther));
    }

    @Test
    void test_toString() {
        String educationalInstitutionString = educationalInstitution.toString();
        assertTrue(educationalInstitutionString.contains(name));
        assertTrue(educationalInstitutionString.contains(address));
    }
}
