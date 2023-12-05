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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
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
public class AdminTest extends GenericEntityTest {

    private static Admin admin;

    private static int adminIDOther;
    private static String usernameOther;
    private static String passwordOther;
    private static String nameOther;
    private static String surnameOther;

    public static void initializeParticipant() {
        admin = (Admin) IOJson.deserializeJson("admin", Admin.class);
        adminIDOther = admin.getAdminID() + 1;
        usernameOther = admin.getUsername() + Utility.STRING_OTHER;
        passwordOther = admin.getPassword() + Utility.STRING_OTHER;
        nameOther = admin.getName() + Utility.STRING_OTHER;
        surnameOther = admin.getSurname() + Utility.STRING_OTHER;
    }

    @BeforeEach
    void setUp() {
        initializeParticipant();
        genericEntity = admin;
    }

    @AfterEach
    void tearDown() {
        admin = null;
    }

    @Test
    void test_getAttributeNames() {
        super.test_getAttributeNames("adminID, username, password, name, surname");
    }

    @Test
    void test_getAttributeValues() {
        super.test_getAttributeValues(String.format("%d, '%s', '%s', '%s', '%s'",
                admin.getAdminID(), admin.getUsername(), admin.getPassword(), admin.getName(), admin.getSurname()));
    }

    @Test
    void test_setAttributeValues_null() {
        super.test_setAttributeValues_null();
    }

    @Test
    void test_getID() {
        super.test_getID(admin.getAdminID());
    }

    @Test
    void test_setID() {
        super.test_setID(admin.getAdminID());
    }

    @Test
    void test_setAttributeValues() {
        super.test_setAttributeValues(String.format("adminID = %d, username = '%s', password = '%s', name = '%s', surname = '%s'",
                admin.getAdminID(), admin.getUsername(), admin.getPassword(), admin.getName(), admin.getSurname()));
    }

    @Test
    void test_getSelectAllQuery() {
        super.test_getSelectAllQuery("SELECT * from admins");
    }

    @Test
    void test_getEntityFromResultSet() {
        try {
            ResultSet rs = mock(ResultSet.class);

            when(rs.getInt("adminID")).thenReturn(admin.getAdminID());
            when(rs.getString("username")).thenReturn(admin.getUsername());
            when(rs.getString("password")).thenReturn(admin.getPassword());
            when(rs.getString("name")).thenReturn(admin.getName());
            when(rs.getString("surname")).thenReturn(admin.getSurname());

            Admin adminFromRs = (Admin) admin.getEntityFromResultSet(rs);
            //EqualsAll would be better
            assertEquals(admin.getAdminID(), adminFromRs.getAdminID());
            assertEquals(admin.getUsername(), adminFromRs.getUsername());
            assertEquals(admin.getPassword(), adminFromRs.getPassword());
            assertEquals(admin.getName(), adminFromRs.getName());
            assertEquals(admin.getSurname(), adminFromRs.getSurname());
        } catch (SQLException ex) {
            Logger.getLogger(AdminTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    void test_getQueryCondition() {
        super.test_getQueryCondition(String.format("adminID = %d ", admin.getAdminID()));
    }

    @Test
    void test_getState() {
        super.test_getState_notImplemented();
    }

    //tests for class specific methods
    @Test
    void test_setAdminID() {
        admin.setAdminID(adminIDOther);
        assertEquals(admin.getAdminID(), adminIDOther);
    }

    @Test
    void test_setUsername() {
        admin.setUsername(usernameOther);
        assertEquals(admin.getUsername(), usernameOther);
    }

    @Test
    void test_setUsername_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> admin.setUsername(null));
    }

    @Test
    void test_setUsername_empty() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> admin.setUsername(Utility.STRING_EMPTY));
    }

    @Test
    void test_setUsername_tooLong() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> admin.setUsername(Utility.STRING_31_LENGTH));
    }

    @Test
    void test_setPassword() {
        admin.setPassword(passwordOther);
        assertEquals(admin.getPassword(), passwordOther);
    }

    @Test
    void test_setPassword_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> admin.setPassword(null));
    }

    @Test
    void test_setPassword_empty() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> admin.setPassword(Utility.STRING_EMPTY));
    }

    @Test
    void test_setPassword_tooLong() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> admin.setPassword(Utility.STRING_31_LENGTH));
    }

    @Test
    void test_setName() {
        admin.setName(nameOther);
        assertEquals(admin.getName(), nameOther);
    }

    @Test
    void test_setName_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> admin.setName(null));
    }

    @Test
    void test_setName_empty() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> admin.setName(Utility.STRING_EMPTY));
    }

    @Test
    void test_setName_tooLong() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> admin.setName(Utility.STRING_31_LENGTH));
    }

    @Test
    void test_setSurname() {
        admin.setSurname(surnameOther);
        assertEquals(admin.getSurname(), surnameOther);
    }

    @Test
    void test_setSurname_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> admin.setSurname(null));
    }

    @Test
    void test_setSurname_empty() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> admin.setSurname(Utility.STRING_EMPTY));
    }

    @Test
    void test_setSurname_tooLong() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> admin.setSurname(Utility.STRING_31_LENGTH));
    }

    @Test
    void test_equals_sameObject() {
        assertTrue(admin.equals(admin));
    }

    @Test
    void test_equals_null() {
        assertFalse(admin.equals(null));
    }

    @Test
    void test_equals_differentClass() {
        assertFalse(admin.equals(new Exception()));
    }

    /*
    Cases checked:
    -> all equal
    -> all equal but adminID
    -> all equal but username
    -> all equal but password
    -> all equal but name
    -> all equal but surname  
     */
    @ParameterizedTest
    @MethodSource("equalsProvider")
    void test_equals(Admin aOther, boolean result) {
        assertEquals(result, admin.equals(aOther));
    }

    static Stream<Arguments> equalsProvider() throws Exception {
        initializeParticipant();
        return Stream.of(
                arguments(Utility.clone(admin), true),
                arguments(cloneParticipantAndModify("adminID"), false),
                arguments(cloneParticipantAndModify("username"), true),
                arguments(cloneParticipantAndModify("password"), true),
                arguments(cloneParticipantAndModify("name"), true),
                arguments(cloneParticipantAndModify("surname"), true)
        );
    }

    @Test
    void test_toString() {
        String adminString = admin.toString();
        assertTrue(adminString.contains(admin.getAdminID() + ""));
        assertTrue(adminString.contains(admin.getUsername()));
        assertTrue(adminString.contains(admin.getPassword()));
        assertTrue(adminString.contains(admin.getName()));
        assertTrue(adminString.contains(admin.getSurname()));
    }

    @Test
    void test_getFullName() {
        String fullName = String.format("%s %s", admin.getName(), admin.getSurname());
        assertEquals(admin.getFullName(), fullName);
    }

    public static Admin cloneParticipantAndModify(String attribute) throws IOException, ClassNotFoundException {
        Admin modifiedAdmin = Utility.clone(admin);

        switch (attribute) {
            case "adminID" ->
                modifiedAdmin.setAdminID(adminIDOther);
            case "username" ->
                modifiedAdmin.setUsername(usernameOther);
            case "password" ->
                modifiedAdmin.setPassword(passwordOther);
            case "name" ->
                modifiedAdmin.setName(nameOther);
            case "surname" ->
                modifiedAdmin.setSurname(usernameOther);
            default ->
                throw new AssertionError();
        }
        return modifiedAdmin;
    }
}
