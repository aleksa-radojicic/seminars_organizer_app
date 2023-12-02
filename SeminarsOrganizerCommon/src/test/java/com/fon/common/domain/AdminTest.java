/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
public class AdminTest extends GenericEntityTest {

    private Admin admin;
    private final int adminID = 1;
    private final String adminIDString = adminID + "";
    private final String username = "aleksar";
    private final String password = "a";
    private final String name = "aleksa";
    private final String surname = "radojicic";

    private final int adminIDOther = 2;
    private final String adminIDStringOther = adminIDOther + "";
    private final String usernameOther = "username";
    private final String passwordOther = "password";
    private final String nameOther = "name";
    private final String surnameOther = "surname";

    @BeforeEach
    void setUp() {
        admin = new Admin(adminID, username, password, name, surname);
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
                adminID, username, password, name, surname));
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
                adminID, username, password, name, surname));
    }

    @Test
    void test_getSelectAllQuery() {
        super.test_getSelectAllQuery("SELECT * from admins");
    }

    @Test
    void test_getEntityFromResultSet() {
        try {
            ResultSet rs = mock(ResultSet.class);

            when(rs.getInt("adminID")).thenReturn(adminID);
            when(rs.getString("username")).thenReturn(username);
            when(rs.getString("password")).thenReturn(password);
            when(rs.getString("name")).thenReturn(name);
            when(rs.getString("surname")).thenReturn(surname);

            Admin adminFromRs = (Admin) admin.getEntityFromResultSet(rs);
            assertEquals(adminID, adminFromRs.getAdminID());
            assertEquals(username, adminFromRs.getUsername());
            assertEquals(password, adminFromRs.getPassword());
            assertEquals(name, adminFromRs.getName());
            assertEquals(surname, adminFromRs.getSurname());
        } catch (SQLException ex) {
            Logger.getLogger(AdminTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    void test_getQueryCondition() {
        super.test_getQueryCondition(String.format("adminID = %d ", adminID));
    }

    @Test
    void test_getState() {
        super.test_getState_notImplemented();
    }

    //tests for class specific methods
    @Test
    void test_setAdminID() {
        admin.setAdminID(adminID);
        assertEquals(admin.getAdminID(), adminID);
    }

    @Test
    void test_setUsername() {
        admin.setUsername(username);
        assertEquals(admin.getUsername(), username);
    }

    @Test
    void test_setPassword() {
        admin.setPassword(password);
        assertEquals(admin.getPassword(), password);
    }

    @Test
    void test_setName() {
        admin.setName(name);
        assertEquals(admin.getName(), name);
    }

    @Test
    void test_setSurname() {
        admin.setSurname(surname);
        assertEquals(admin.getSurname(), surname);
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

    @ParameterizedTest
    @CsvSource({
        //all equal
        adminIDString + "," + username + "," + password + "," + name + "," + surname + ",true",
        //all equal but adminID
        adminIDStringOther + "," + username + "," + password + "," + name + "," + surname + ",false",
        //all equal but username
        adminIDString + "," + usernameOther + "," + password + "," + name + "," + surname + ",true",
        //all equal but password
        adminIDString + "," + username + "," + passwordOther + "," + name + "," + surname + ",true",
        //all equal but name
        adminIDString + "," + username + "," + password + "," + nameOther + "," + surname + ",true",
        //all equal but surname
        adminIDString + "," + username + "," + password + "," + name + "," + surnameOther + ",true"
    })
    void test_equals(int adminID2, String username2, String password2, String name2, String surname2,
            boolean result) {

        Admin adminOther = new Admin(adminID2, username2, password2, name2, surname2);

        assertEquals(result,
                admin.equals(adminOther));
    }

    @Test
    void test_toString() {
        String adminString = admin.toString();
        assertTrue(adminString.contains(adminIDString));
        assertTrue(adminString.contains(username));
        assertTrue(adminString.contains(password));
        assertTrue(adminString.contains(name));
        assertTrue(adminString.contains(surname));
    }

    @Test
    void test_getFullName() {
        String fullName = String.format("%s %s", name, surname);
        assertEquals(admin.getFullName(), fullName);
    }
}
