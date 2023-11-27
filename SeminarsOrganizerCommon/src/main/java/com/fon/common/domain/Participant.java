/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Aleksa
 */
public class Participant implements GenericEntity, Serializable {

    private int participantID;
    private String name;
    private String surname;
    private Sex sex;
    private Date dateBirth;
    private Admin createdByAdmin;

    public Participant(int participantID, String name, String surname, Sex sex, Date dateBirth, Admin admin) {
        this.participantID = participantID;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.dateBirth = dateBirth;
        this.createdByAdmin = admin;
    }

    public Participant() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" ");
        sb.append(surname);
        sb.append(", ");
        if (sex.equals(Sex.FEMALE)) {
            sb.append("ж, ");
        } else {
            sb.append("м, ");
        }
        Long age = getAge();
        sb.append(age);
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Participant other = (Participant) obj;
        return this.participantID == other.participantID;
    }

    public int getParticipantID() {
        return participantID;
    }

    public void setParticipantID(int participantID) {
        this.participantID = participantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public Admin getAdmin() {
        return createdByAdmin;
    }

    public void setAdmin(Admin admin) {
        this.createdByAdmin = admin;
    }

    @Override
    public String getAttributeNames() {
        return "participantID, name, surname, sex, dateBirth, createdByAdminID";
    }

    @Override
    public String getAttributeValues() {
        StringBuilder result = new StringBuilder();
        result.append(getParticipantID())
                .append(", '")
                .append(getName())
                .append("','")
                .append(getSurname())
                .append("', '")
                .append(getSex().toString())
                .append("', '")
                .append(new java.sql.Date(getDateBirth().getTime()))
                .append("', ")
                .append(getCreatedByAdmin().getAdminID());
        return result.toString();
    }

    public Admin getCreatedByAdmin() {
        return createdByAdmin;
    }

    public void setCreatedByAdmin(Admin createdByAdmin) {
        this.createdByAdmin = createdByAdmin;
    }

    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        return new Participant(rs.getInt(1), rs.getString(2), rs.getString(3), Sex.valueOf(rs.getString(4)), rs.getDate(5), null);
    }

    @Override
    public State getState() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Long getAge() {
        Date currentDate = new Date();
        Long age = (currentDate.getTime() - dateBirth.getTime()) / 1000 / 60 / 60 / 24 / 365;
        return age;
    }
}
