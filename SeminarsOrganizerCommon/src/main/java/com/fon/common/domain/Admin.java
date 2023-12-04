/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Domain class representing an Admin who has access to the application and all
 * available system operations.
 *
 * <p>
 * This class implements the GenericEntity interface, providing generic methods
 * to interact with the database.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 *
 */
public class Admin implements GenericEntity {

    /**
     * Unique identifier of the admin as integer.
     */
    private int adminID;

    /**
     * Admin's username as string.
     */
    private String username;

    /**
     * Admin's password as string.
     */
    private String password;

    /**
     * Admin's name as string.
     */
    private String name;

    /**
     * Admin's surname as string.
     */
    private String surname;

    /**
     * Non-parametric constructor.
     */
    public Admin() {
    }

    /**
     * Constructor with adminID (primary key).
     *
     * @param adminID ID as integer.
     */
    public Admin(int adminID) {
        this.adminID = adminID;
    }

    /**
     * Constructor with all parameters.
     *
     * @param adminID ID as integer.
     * @param username Username as string.
     * @param password Password as string.
     * @param name Name as string.
     * @param surname Surname as string.
     */
    public Admin(int adminID, String username, String password, String name, String surname) {
        this.adminID = adminID;
        this.username = validateUsername(username);
        this.password = validatePassword(password);
        this.name = validateName(name);
        this.surname = validateSurname(surname);
    }

    /**
     * Getter for ID.
     *
     * @return ID as integer.
     */
    public int getAdminID() {
        return adminID;
    }

    /**
     * Setter for ID.
     *
     * @param adminID ID as integer.
     */
    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    /**
     * Getter for username.
     *
     * @return Username as string.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for username.
     *
     * @param username Username as string.
     */
    public void setUsername(String username) {
        this.username = validateUsername(username);
    }

    /**
     * Validation for username.
     *
     * @param username Username as {@code String}.
     * @return Username as {@code String}.
     * @throws NullPointerException When {@code username} is null.
     * @throws IllegalArgumentException When {@code username} is empty or
     * greater than 30.
     */
    private String validateUsername(String username) throws IllegalArgumentException, NullPointerException {
        if (username == null) {
            throw new NullPointerException("Username must not be null!");
        }
        if (username.isEmpty()) {
            throw new IllegalArgumentException("Username must not be empty!");
        }
        int maxLength = 30;
        if (username.length() > maxLength) {
            throw new IllegalArgumentException(String.format("Username must not be greater than %d!", maxLength));
        }
        return username;
    }

    /**
     * Getter for password.
     *
     * @return Password as string.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password.
     *
     * @param password Password as string.
     */
    public void setPassword(String password) {
        this.password = validatePassword(password);
    }

    /**
     * Validation for password.
     *
     * @param password Password as {@code String}.
     * @return Password as {@code String}.
     * @throws NullPointerException When {@code password} is null.
     * @throws IllegalArgumentException When {@code password} is empty or
     * greater than 30.
     */
    private String validatePassword(String password) throws IllegalArgumentException, NullPointerException {
        if (password == null) {
            throw new NullPointerException("Password must not be null!");
        }
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Password must not be empty!");
        }
        int maxLength = 30;
        if (password.length() > maxLength) {
            throw new IllegalArgumentException(String.format("Password must not be greater than %d!", maxLength));
        }
        return password;
    }

    /**
     * Getter for name.
     *
     * @return Name as string.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     *
     * @param name Name as string.
     */
    public void setName(String name) {
        this.name = validateName(name);
    }

    /**
     * Validation for name.
     *
     * @param name Name as {@code String}.
     * @return Name as {@code String}.
     * @throws NullPointerException When {@code name} is null.
     * @throws IllegalArgumentException When {@code name} is empty or greater
     * than 30.
     */
    private String validateName(String name) throws NullPointerException, IllegalArgumentException {
        if (name == null) {
            throw new NullPointerException("Name must not be null!");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty!");
        }
        int maxLength = 30;
        if (name.length() > maxLength) {
            throw new IllegalArgumentException(String.format("Name must not be greater than %d!", maxLength));
        }
        return name;
    }

    /**
     * Getter for surname.
     *
     * @return Surname as string.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Setter for surname.
     *
     * @param surname Surname as string.
     */
    public void setSurname(String surname) {
        this.surname = validateSurname(surname);
    }

    /**
     * Validation for surname.
     *
     * @param surname Surname as string.
     * @return Surname as integer.
     * @throws NullPointerException When {@code surname} is null.
     * @throws IllegalArgumentException When {@code surname} is empty or greater
     * than 30.
     */
    private String validateSurname(String surname) throws NullPointerException, IllegalArgumentException {
        if (surname == null) {
            throw new NullPointerException("Surname must not be null!");
        }
        if (surname.isEmpty()) {
            throw new IllegalArgumentException("Surname must not be empty!");
        }
        int maxLength = 30;
        if (surname.length() > maxLength) {
            throw new IllegalArgumentException(String.format("Surname must not be greater than %d!", maxLength));
        }
        return surname;
    }

    /**
     * Returns hash code, calculated using adminID, username, password, name and
     * surname.
     *
     * @return Hash code as integer.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.adminID;
        hash = 97 * hash + Objects.hashCode(this.username);
        hash = 97 * hash + Objects.hashCode(this.password);
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.surname);
        return hash;
    }

    /**
     * Equals method which compares adminID.
     *
     * @return true if adminIDs are equal, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Admin other = (Admin) obj;
        return this.adminID == other.adminID;
    }

    /**
     * toString method which returns all data about the Admin.
     *
     * @return String representation of the Admin.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Admin{");
        sb.append("adminID=").append(adminID);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", name=").append(name);
        sb.append(", surname=").append(surname);
        sb.append('}');
        return sb.toString();
    }

    /**
     * This method is not implemented.
     *
     * @throws UnsupportedOperationException Signifies the method is not yet
     * supported.
     */
    @Override
    public State getState() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        Admin a = new Admin(rs.getInt("adminID"), rs.getString("username"), rs.getString("password"), rs.getString("name"),
                rs.getString("surname"));
        return a;
    }

    /**
     * Returns full name of an Admin, using his name and surname, separated by a
     * single space.
     *
     * @return Full name as string.
     */
    public String getFullName() {
        return name + " " + surname;
    }
}
