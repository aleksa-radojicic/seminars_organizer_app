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
public class EducationalInstitutionTest extends GenericEntityTest {

    private static EducationalInstitution educationalInstitution;

    private static int IDOther;
    private static String nameOther;
    private static String addressOther;

    private static void initializeEducationalInstitution() {
        educationalInstitution = (EducationalInstitution) IOJson.deserializeJson("educational_institution", EducationalInstitution.class);

        IDOther = educationalInstitution.getEducationalInstitutionID() + 1;
        nameOther = educationalInstitution.getName() + Utility.STRING_OTHER;
        addressOther = educationalInstitution.getAddress() + Utility.STRING_OTHER;
    }

    @BeforeEach
    void setUp() {
        initializeEducationalInstitution();
        genericEntity = educationalInstitution;
    }

    @AfterEach
    void tearDown() {
        educationalInstitution = null;
        genericEntity = null;
    }

    @Test
    void test_getAttributeNames() {
        super.test_getAttributeNames("educationalInstitutionID, name, address");
    }

    @Test
    void test_getAttributeValues() {
        super.test_getAttributeValues(String.format("%d, '%s', '%s'",
                educationalInstitution.getEducationalInstitutionID(),
                educationalInstitution.getName(),
                educationalInstitution.getAddress()));
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
                educationalInstitution.getEducationalInstitutionID(),
                educationalInstitution.getName(),
                educationalInstitution.getAddress()));
    }

    @Test
    void test_getSelectAllQuery() {
        super.test_getSelectAllQuery("SELECT * from educationalInstitutions");
    }

    @Test
    void test_getEntityFromResultSet() {
        try {
            ResultSet rs = mock(ResultSet.class);

            when(rs.getInt("educationalInstitutionID")).thenReturn(educationalInstitution.getEducationalInstitutionID());
            when(rs.getString("name")).thenReturn(educationalInstitution.getName());
            when(rs.getString("address")).thenReturn(educationalInstitution.getAddress());

            EducationalInstitution eiFromRS = (EducationalInstitution) educationalInstitution.getEntityFromResultSet(rs);
            assertEquals(educationalInstitution.getEducationalInstitutionID(), eiFromRS.getEducationalInstitutionID());
            assertEquals(educationalInstitution.getName(), eiFromRS.getName());
            assertEquals(educationalInstitution.getAddress(), eiFromRS.getAddress());
        } catch (SQLException ex) {
            Logger.getLogger(EducationalInstitutionTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    void test_getQueryCondition() {
        super.test_getQueryCondition(String.format("educationalInstitutionID = %d ", educationalInstitution.getEducationalInstitutionID()));
    }

    @Test
    void test_getState() {
        super.test_getState_notImplemented();
    }

    //tests for class specific methods
    @Test
    void test_setEducationalInstitutionID() {
        educationalInstitution.setEducationalInstitutionID(IDOther);
        assertEquals(educationalInstitution.getEducationalInstitutionID(), IDOther);
    }

    @Test
    void test_setName() {
        educationalInstitution.setName(nameOther);
        assertEquals(educationalInstitution.getName(), nameOther);
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
        educationalInstitution.setAddress(addressOther);
        assertEquals(educationalInstitution.getAddress(), addressOther);
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

    /*
    Cases checked:
    -> all equal
    -> all equal but educationalInstitutionID
    -> all equal but name
    -> all equal but address
     */
    @ParameterizedTest
    @MethodSource("equalsProvider")
    void test_equals(EducationalInstitution eiOther, boolean result) {
        assertEquals(result, educationalInstitution.equals(eiOther));
    }

    static Stream<Arguments> equalsProvider() throws Exception {
        initializeEducationalInstitution();
        return Stream.of(
                arguments(Utility.clone(educationalInstitution), true),
                arguments(cloneEducationalInstitutionAndModify("educationalInstitutionID"), false),
                arguments(cloneEducationalInstitutionAndModify("name"), true),
                arguments(cloneEducationalInstitutionAndModify("address"), true)
        );
    }

    public static EducationalInstitution cloneEducationalInstitutionAndModify(String attribute) throws IOException, ClassNotFoundException {
        EducationalInstitution modifiedEi = Utility.clone(educationalInstitution);

        switch (attribute) {
            case "educationalInstitutionID" ->
                modifiedEi.setEducationalInstitutionID(IDOther);
            case "name" ->
                modifiedEi.setName(nameOther);
            case "address" ->
                modifiedEi.setAddress(addressOther);
            default ->
                throw new AssertionError();
        }
        return modifiedEi;
    }

    @Test
    void test_toString() {
        String educationalInstitutionString = educationalInstitution.toString();
        assertTrue(educationalInstitutionString.contains(educationalInstitution.getName()));
        assertTrue(educationalInstitutionString.contains(educationalInstitution.getAddress()));
    }
}
