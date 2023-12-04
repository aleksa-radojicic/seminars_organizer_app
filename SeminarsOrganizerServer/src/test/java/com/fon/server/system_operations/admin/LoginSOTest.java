/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.admin;

import com.fon.common.domain.Admin;
import com.fon.common.exceptions.LoginException;
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
public class LoginSOTest {

    private LoginSO loginSO;
    private Admin admin;

    private final int adminID = 1;
    private final String username = "aleksar";
    private final String password = "a";

    private Admin loggedAdmin;

    @BeforeEach
    void setUp() {
        loginSO = new LoginSO();
        final String name = "aleksa";
        final String surname = "radojicic";
        admin = new Admin(adminID, username, password, name, surname);
        loggedAdmin = admin;
    }

    @AfterEach
    void tearDown() {
        loginSO = null;
        admin = null;
    }

    @Test
    void test_setLoggedAdmin() {
        loggedAdmin.setAdminID(adminID);
        assertEquals(loggedAdmin.getAdminID(), adminID);
    }

    @Test
    void test_preconditions() {
        assertDoesNotThrow(() -> loginSO.preconditions(admin));
    }

    @Test

    void test_preconditions_dClass() {
        assertThrowsExactly(Exception.class,
                () -> loginSO.preconditions(new Exception()));
    }

    @Test

    void test_preconditions_null() {
        assertThrowsExactly(Exception.class,
                () -> loginSO.preconditions(null));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation() {
        try {
            assertDoesNotThrow(() -> loginSO.execute(admin));

        } catch (Exception ex) {
            Logger.getLogger(LoginSOTest.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_wrongCredentials_username() {
        try {
            admin.setUsername(username + "Other");
            assertThrowsExactly(LoginException.class,
                    () -> loginSO.execute(admin));

        } catch (Exception ex) {
            Logger.getLogger(LoginSOTest.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_wrongCredentials_password() {
        try {
            admin.setPassword(password + "Other");
            assertThrowsExactly(LoginException.class,
                    () -> loginSO.execute(admin));

        } catch (Exception ex) {
            Logger.getLogger(LoginSOTest.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }
}
