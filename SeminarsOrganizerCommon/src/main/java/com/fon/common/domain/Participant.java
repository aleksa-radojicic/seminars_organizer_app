/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Domain class representing a participant who can enroll seminars.
 *
 * <p>
 * This class implements the {@code GenericEntity} interface, providing generic
 * methods to interact with the database.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 *
 */
public class Participant implements GenericEntity {

    /**
     * Unique identifier of the Participant as {@code int}.
     */
    private int participantID;

    /**
     * Participant's name as {@code String}.
     */
    private String name;

    /**
     * Participant's surname as {@code String}.
     */
    private String surname;

    /**
     * Participant's sex as {@code Sex}.
     */
    private Sex sex;

    /**
     * Participant's date of birth as {@code Date}.
     */
    private Date dateBirth;

    /**
     * Admin who created the Participant as {@code Admin}.
     */
    private Admin createdByAdmin;

    /**
     * Constructor with all parameters.
     *
     * @param participantID ID as {@code int}.
     * @param name Name as {@code String}.
     * @param surname Surname as {@code String}.
     * @param sex Sex as {@code Sex}.
     * @param dateBirth Date of birth as {@code Date}.
     * @param admin Admin who created the Participant as {@code Admin}.
     */
    public Participant(int participantID, String name, String surname, Sex sex, Date dateBirth, Admin admin) {
        this.participantID = participantID;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.dateBirth = dateBirth;
        this.createdByAdmin = admin;
    }

    /**
     * Non-parametric constructor.
     */
    public Participant() {
    }

    /**
     * toString method which returns name, surname and sex of the Participant.
     *
     * @return String representation of the Participant.
     */
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

    /**
     * Returns hash code, calculated using participantID.
     *
     * @return Hash code as {@code int}.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.participantID;
        return hash;
    }

    /**
     * Equals method which compares participantID.
     *
     * @return {@code true} if participantIDs are equal, otherwise
     * {@code false}.
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
        final Participant other = (Participant) obj;
        return this.participantID == other.participantID;
    }

    /**
     * Getter for educationalInstitutionID.
     *
     * @return ID as {@code int}.
     */
    public int getParticipantID() {
        return participantID;
    }

    /**
     * Setter for participantID.
     *
     * @param participantID ID as {@code int}.
     */
    public void setParticipantID(int participantID) {
        this.participantID = participantID;
    }

    /**
     * Getter for name.
     *
     * @return Name as {@code String}.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     *
     * @param name Name as {@code String}.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for surname.
     *
     * @return Surname as {@code String}.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Setter for surname.
     *
     * @param surname Surname as {@code String}.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Getter for sex.
     *
     * @return Sex as {@code Sex}.
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * Setter for sex.
     *
     * @param sex Sex as {@code Sex}.
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    /**
     * Getter for dateBirth.
     *
     * @return Date of birth as {@code Date}.
     */
    public Date getDateBirth() {
        return dateBirth;
    }

    /**
     * Setter for dateBirth.
     *
     * @param dateBirth Date of birth as {@code Date}.
     */
    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
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

    /**
     * Getter for createdByAdmin.
     *
     * @return Admin who created the Participant as {@code Admin}.
     */
    public Admin getCreatedByAdmin() {
        return createdByAdmin;
    }

    /**
     * Setter for createdByAdmin.
     *
     * @param createdByAdmin Admin who created the Participant as {@code Admin}.
     */
    public void setCreatedByAdmin(Admin createdByAdmin) {
        this.createdByAdmin = createdByAdmin;
    }

    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        return new Participant(rs.getInt(1), rs.getString(2), rs.getString(3), Sex.valueOf(rs.getString(4)), rs.getDate(5), null);
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

    /**
     * Returns age of the Participant, calculated using his date of birth and
     * current date.
     *
     * @return Age of the Participant as {@code long}.
     */
    public Long getAge() {
        Date currentDate = new Date();
        Long age = (currentDate.getTime() - dateBirth.getTime()) / 1000 / 60 / 60 / 24 / 365;
        return age;
    }
}
