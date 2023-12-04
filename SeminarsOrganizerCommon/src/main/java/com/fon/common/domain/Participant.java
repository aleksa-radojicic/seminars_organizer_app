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
     * Participant's sex as {@code Sex}, default is {@code Sex.MALE}.
     */
    private Sex sex = Sex.MALE;

    /**
     * Participant's date of birth as {@code Date}, default is today's date.
     */
    private Date dateBirth = new Date();

    /**
     * Admin who created the Participant as {@code Admin}.
     */
    private Admin createdByAdmin;

    /**
     * Constructor with participantID (primary key).
     *
     * @param participantID ID as {@code int}.
     */
    public Participant(int participantID) {
        this.participantID = participantID;
    }

    /**
     * Constructor with all parameters.
     *
     * @param participantID ID as {@code int}.
     * @param name Name as {@code String}.
     * @param surname Surname as {@code String}.
     * @param sex Sex as {@code Sex}.
     * @param dateBirth Date of birth as {@code Date}.
     * @param createdByAdmin Admin who created the Participant as {@code Admin}.
     */
    public Participant(int participantID, String name, String surname, Sex sex, Date dateBirth, Admin createdByAdmin) {
        this.participantID = participantID;
        this.name = validateName(name);
        this.surname = validateSurname(surname);
        this.sex = validateSex(sex);
        this.dateBirth = validateDateBirth(dateBirth);
        this.createdByAdmin = createdByAdmin;
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
        this.surname = validateSurname(surname);
    }

    /**
     * Validation for surname.
     *
     * @param surname Surname as integer.
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
        this.sex = validateSex(sex);
    }

    /**
     * Validation for sex.
     *
     * @param sex Sex as {@code Sex}.
     * @return Sex as {@code Sex}.
     * @throws IllegalArgumentException When {@code sex}'s string length is
     * greater than 6.
     */
    private Sex validateSex(Sex sex) throws IllegalArgumentException {
        if (sex.toString().length() > 6) {
            throw new IllegalArgumentException("Sex enum must not be greater than 6!");
        }
        return sex;
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
        this.dateBirth = validateDateBirth(dateBirth);
    }

    /**
     * Validation for dateBirth.
     *
     * @param dateBirth Date of birth as {@code Date}.
     * @return Date of birth as {@code Date}.
     * @throws NullPointerException When {@code dateBirth} is null.
     * @throws IllegalArgumentException When {@code dateBirth} is in the future.
     */
    private Date validateDateBirth(Date dateBirth) throws IllegalArgumentException, NullPointerException {
        if (dateBirth == null) {
            throw new NullPointerException("Date of birth must not be null!");
        }
        Date todaysDate = new Date();
        if (dateBirth.after(todaysDate)) {
            throw new IllegalArgumentException("Date of birth must not be in the future!");
        }
        return dateBirth;
    }

    @Override
    public String getAttributeNames() {
        return "participantID, name, surname, sex, dateBirth, createdByAdminID";
    }

    @Override
    public String getAttributeValues() {
        return String.format("%d, '%s', '%s', '%s', '%s', %d",
                participantID, name, surname, sex, new java.sql.Date(dateBirth.getTime()), createdByAdmin.getAdminID());
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
        this.createdByAdmin = validateCreatedByAdmin(createdByAdmin);
    }

    /**
     * Validation for createdByAdmin.
     *
     * @param createdByAdmin Admin who created the Participant as {@code Admin}.
     * @return Educational Admin who created the Participant as {@code Admin}.
     * @throws NullPointerException When {@code createdByAdmin} is null.
     *
     */
    private Admin validateCreatedByAdmin(Admin createdByAdmin) {
        if (createdByAdmin == null) {
            throw new NullPointerException("Admin who created the participant must not be null!");
        }
        return createdByAdmin;
    }

    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        return new Participant(rs.getInt("participantID"),
                rs.getString("name"),
                rs.getString("surname"),
                Sex.valueOf(rs.getString("sex")),
                new Date(rs.getDate("dateBirth").getTime()),
                null);
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

    @Override
    public String setAttributeValues() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
