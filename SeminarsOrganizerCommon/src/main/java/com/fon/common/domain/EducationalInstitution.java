/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Aleksa
 */
public class EducationalInstitution implements GenericEntity {

    private int educationalInstitutionID;
    private String name;
    private String address;

    public EducationalInstitution(int educationalInstitutionID, String name, String address) {
        this.educationalInstitutionID = educationalInstitutionID;
        this.name = name;
        this.address = address;
    }

    public EducationalInstitution() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.educationalInstitutionID;
        return hash;
    }

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

    public int getEducationalInstitutionID() {
        return educationalInstitutionID;
    }

    public void setEducationalInstitutionID(int educationalInstitutionID) {
        this.educationalInstitutionID = educationalInstitutionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    @Override
    public State getState() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
