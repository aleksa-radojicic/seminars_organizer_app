/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Domain class representing an educational institution where seminars can be
 * hosted in.
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
public class EducationalInstitution implements GenericEntity {

    /**
     * Unique identifier of the EducationalInstitution as integer.
     */
    private int educationalInstitutionID;

    /**
     * EducationalInstitution's name as string.
     */
    private String name;

    /**
     * EducationalInstitution's address as string.
     */
    private String address;

    /**
     * Constructor with educationalInstitutionID (primary key).
     *
     * @param educationalInstitutionID ID as integer.
     */
    public EducationalInstitution(int educationalInstitutionID) {
        this.educationalInstitutionID = educationalInstitutionID;
    }

    /**
     * Constructor with all parameters.
     *
     * @param educationalInstitutionID ID as integer.
     * @param name Name as string.
     * @param address Address as string.
     */
    public EducationalInstitution(int educationalInstitutionID, String name, String address) {
        this.educationalInstitutionID = educationalInstitutionID;
        this.name = name;
        this.address = address;
    }

    /**
     * Non-parametric constructor.
     */
    public EducationalInstitution() {
    }

    /**
     * Returns hash code, calculated using educationalInstitutionID.
     *
     * @return Hash code as integer.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.educationalInstitutionID;
        return hash;
    }

    /**
     * Equals method which compares educationalInstitutionID.
     *
     * @return true if educationalInstitutionIDs are equal, otherwise false.
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
        final EducationalInstitution other = (EducationalInstitution) obj;
        return this.educationalInstitutionID == other.educationalInstitutionID;
    }

    /**
     * Getter for educationalInstitutionID.
     *
     * @return ID as integer.
     */
    public int getEducationalInstitutionID() {
        return educationalInstitutionID;
    }

    /**
     * Setter for educationalInstitutionID.
     *
     * @param educationalInstitutionID ID as integer.
     */
    public void setEducationalInstitutionID(int educationalInstitutionID) {
        this.educationalInstitutionID = educationalInstitutionID;
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
        this.name = name;
    }

    /**
     * Getter for address.
     *
     * @return Address as string.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for address.
     *
     * @param address Address as string.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * toString method which returns name and address of the
     * EducationalInstitution, separated by colon and a single space.
     *
     * @return String representation of the EducationalInstitution.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(": ");
        sb.append(address);
        return sb.toString();
    }

    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        EducationalInstitution ei = new EducationalInstitution(rs.getInt("educationalInstitutionID"), rs.getString("name"), rs.getString("address"));
        return ei;
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
}
