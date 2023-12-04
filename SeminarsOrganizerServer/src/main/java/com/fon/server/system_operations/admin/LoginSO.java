/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.admin;

import java.util.List;
import com.fon.common.domain.Admin;
import com.fon.common.exceptions.LoginException;
import com.fon.server.constants.ServerConstants;
import com.fon.server.system_operations.AbstractSO;

/**
 * System operation used when an administrator wants to log in to the
 * application.
 *
 * <p>
 * Extends abstract system operation class {@code AbstractSO}.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class LoginSO extends AbstractSO {

    /**
     * Logged admin retrieved from the database with all data as {@code Admin}.
     */
    private Admin loggedAdmin;

    /**
     * Getter for loggedAdmin.
     *
     * @return Logged admin retrieved from the database with all data as
     * {@code Admin}.
     */
    public Admin getLoggedAdmin() {
        return loggedAdmin;
    }

    /**
     * Setter for loggedAdmin.
     *
     * @param loggedAdmin Logged admin retrieved from the database with all data
     * as {@code Admin}.
     */
    public void setLoggedAdmin(Admin loggedAdmin) {
        this.loggedAdmin = loggedAdmin;
    }

    /**
     * Checks if the sent object is instance of class {@code Admin} and that
     * username and password are not empty.
     *
     * @param arg Probably instance of {@code Admin} class with filled username
     * and password.
     * @throws Exception When the sent object is not instance of class
     * {@code Admin}.
     */
    @Override
    protected void preconditions(Object arg) throws Exception {
        if (arg == null || !(arg instanceof Admin)) {
            throw new Exception(ServerConstants.INCORRECT_TYPE_ERROR_MESSAGE);
        }
    }

    /**
     * Retrieves an admin with all data with entered credentials and stores it
     * in {@code loggedAdmin} attribute.
     *
     * @param arg Instance of {@code Admin} class with filled username and
     * password.
     * @throws LoginException When admin entered wrong username and /
     * or password.
     * @throws Exception When an error happened while retrieving an admin with
     * all data.
     */
    @Override
    protected void executeOperation(Object arg) throws LoginException, Exception {
        Admin admin = (Admin) arg;

        String whereQuerySection = "WHERE username = '" + admin.getUsername() + "' and password = '" + admin.getPassword() + "'";

        List<Admin> admins = (List<Admin>) REPOSITORY.getByCondition(new Admin(), whereQuerySection);

        if (admins.isEmpty()) {
            throw new LoginException("Нетачно корисничко име или лозинка");
        }
        loggedAdmin = admins.get(0);
    }
}
