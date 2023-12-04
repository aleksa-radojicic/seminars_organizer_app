/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Domain class representing a seminar, containing several seminar topics and
 * can be scheduled.
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
public class Seminar implements GenericEntity {

    /**
     * Unique identifier of the Seminar as {@code int}.
     */
    private int seminarID;

    /**
     * Seminar's name as {@code String}.
     */
    private String name;

    /**
     * Seminar's description as {@code String}.
     */
    private String description;

    /**
     * Admin who created the Seminar as {@code Admin}.
     */
    private Admin createdByAdmin;

    /**
     * List of all SeminarTopics that the Seminar contains as
     * {@code List<SeminarTopic>}, default is an empty linked list.
     */
    private List<SeminarTopic> seminarTopics = new LinkedList();

    /**
     * Seminar's state as {@code State}, default is {@code State.UNCHANGED}.
     */
    private State state = State.UNCHANGED;

    /**
     * Non-parametric constructor.
     */
    public Seminar() {
    }

    /**
     * Constructor with seminarID (primary key).
     *
     * @param seminarID ID as {@code int}.
     */
    public Seminar(int seminarID) {
        this.seminarID = seminarID;
    }

    /**
     * Constructor with all parameters except state.
     *
     * @param seminarID ID as {@code int}.
     * @param name Name as {@code String}.
     * @param description Description as {@code String}.
     * @param createdByAdmin Admin who created the Seminar as {@code Admin}.
     * @param seminarTopics List of all SeminarTopics that the Seminar contains
     * as {@code List<SeminarTopic>}.
     */
    public Seminar(int seminarID, String name, String description,
            Admin createdByAdmin, List<SeminarTopic> seminarTopics) {
        this.seminarID = seminarID;
        this.name = validateName(name);
        this.description = validateDescription(description);
        this.createdByAdmin = createdByAdmin;
        this.seminarTopics = seminarTopics;
    }

    /**
     * Constructor with all parameters except state, createdByAdmin and
     * seminarTopics. Initializes createdByAdmin to {@code new Admin()}.
     *
     * @param seminarID ID as {@code int}.
     * @param name Name as {@code String}.
     * @param description Description as {@code String}.
     */
    public Seminar(int seminarID, String name, String description) {
        this.seminarID = seminarID;
        this.name = validateName(name);
        this.description = validateDescription(description);
        this.createdByAdmin = new Admin();
    }
    
    @Override
    public String getAttributeNames() {
        return "seminarID, name, description, createdByAdminID";
    }
    
    @Override
    public String getAttributeValues() throws Exception {
        return String.format("%d, '%s', '%s', %d", seminarID, name, description, createdByAdmin.getAdminID());
    }
    
    @Override
    public String setAttributeValues() {
        return String.format("name ='%s', description = '%s'", name, description);
    }
    
    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        Seminar seminar = new Seminar(rs.getInt("seminarID"), rs.getString("name"), rs.getString("description"), null, null);
        return seminar;
    }
    
    @Override
    public State getState() {
        return state;
    }

    /**
     * Returns hash code, calculated using seminarID.
     *
     * @return Hash code as {@code int}.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.seminarID;
        return hash;
    }

    /**
     * Equals method which compares seminarID.
     *
     * @return {@code true} if seminarIDs are equal, otherwise {@code false}.
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
        final Seminar other = (Seminar) obj;
        return this.seminarID == other.seminarID;
    }

    /**
     * Equals method which compares all attributes except createdByAdmin. Used
     * in a form for updating seminars to check if the Seminar is changed.
     *
     * @param obj
     * @return {@code true} if all attributes except createdByAdmin are equal
     * including full equality of seminarTopics, otherwise {@code false}.
     */
    public boolean equalsAll(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Seminar other = (Seminar) obj;
        if (this.seminarID != other.seminarID) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return SeminarTopic.equalsAll(this.seminarTopics, other.seminarTopics);
    }

    /**
     * Equals method which compares all attributes except createdByAdmin and
     * seminarTopics.Used in a form for updating seminars to check if the
     * Seminar's main attributes are changed.
     *
     * @param obj
     * @return {@code true} if all attributes except createdByAdmin and
     * seminarTopics are equal, otherwise {@code false}.
     */
    public boolean equalsAllWithoutSeminarTopics(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Seminar other = (Seminar) obj;
        if (this.seminarID != other.seminarID) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.description, other.description);
    }

    /**
     * Getter for createdByAdmin.
     *
     * @return Admin who created the Seminar as {@code Admin}.
     */
    public Admin getCreatedByAdmin() {
        return createdByAdmin;
    }

    /**
     * Setter for createdByAdmin.
     *
     * @param createdByAdmin Admin who created the Seminar as {@code Admin}.
     */
    public void setCreatedByAdmin(Admin createdByAdmin) {
        this.createdByAdmin = validateCreatedByAdmin(createdByAdmin);
    }

    /**
     * Validation for createdByAdmin.
     *
     * @param createdByAdmin Admin who created the Seminar as {@code Admin}.
     * @return Admin who created the Seminar as {@code Admin}.
     * @throws NullPointerException When {@code createdByAdmin} is null.
     *
     */
    private Admin validateCreatedByAdmin(Admin createdByAdmin) {
        if (createdByAdmin == null) {
            throw new NullPointerException("Admin who created the seminar must not be null!");
        }
        return createdByAdmin;
    }

    /**
     * Getter for seminarID.
     *
     * @return ID as {@code int}.
     */
    public int getSeminarID() {
        return seminarID;
    }

    /**
     * Setter for seminarID.
     *
     * @param seminarID ID as {@code int}.
     */
    public void setSeminarID(int seminarID) {
        this.seminarID = seminarID;
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
     * than 60.
     */
    private String validateName(String name) throws NullPointerException, IllegalArgumentException {
        if (name == null) {
            throw new NullPointerException("Name must not be null!");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty!");
        }
        int maxLength = 60;
        if (name.length() > maxLength) {
            throw new IllegalArgumentException(String.format("Name must not be greater than %d!", maxLength));
        }
        return name;
    }

    /**
     * Getter for description.
     *
     * @return Description as {@code String}.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for description.
     *
     * @param description Description as {@code String}.
     */
    public void setDescription(String description) {
        this.description = validateDescription(description);
    }

    /**
     * Validation for description.
     *
     * @param description Description as {@code String}.
     * @return Description as {@code String}.
     * @throws NullPointerException When {@code description} is null.
     * @throws IllegalArgumentException When {@code description} is greater than
     * 200.
     */
    private String validateDescription(String description) throws NullPointerException, IllegalArgumentException {
        if (description == null) {
            throw new NullPointerException("Description must not be null!");
        }
        int maxLength = 200;
        if (description.length() > maxLength) {
            throw new IllegalArgumentException(String.format("Description must not be greater than %d!", maxLength));
        }
        return description;
    }

    /**
     * Getter for seminarTopics.
     *
     * @return Seminar topics as {@code List<SeminarTopic>}.
     */
    public List<SeminarTopic> getSeminarTopics() {
        return seminarTopics;
    }

    /**
     * Setter for seminarTopics.
     *
     * @param seminarTopics Seminar topics as {@code List<SeminarTopic>}.
     */
    public void setSeminarTopics(List<SeminarTopic> seminarTopics) {
        this.seminarTopics = validateSeminarTopics(seminarTopics);
    }

    /**
     * Validation for seminarTopics.
     *
     * @param seminarTopics Seminar topics as {@code List<SeminarTopic>}.
     * @return Seminar topics as {@code List<SeminarTopic>}.
     * @throws NullPointerException When {@code seminarTopics} is null.
     *
     */
    private List<SeminarTopic> validateSeminarTopics(List<SeminarTopic> seminarTopics) {
        if (seminarTopics == null) {
            throw new NullPointerException("Seminar topics must not be null!");
        }
        //Ensure no topics are present with null data
        seminarTopics.forEach(x -> x.validateNull());
        return seminarTopics;
    }

    /**
     * toString method which returns name and description of the Seminar.
     *
     * @return String representation of the Seminar.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" : ");
        sb.append(description);
        return sb.toString();
    }

    /**
     * Setter for state.
     *
     * @param state State as {@code State}.
     */
    public void setState(State state) {
        this.state = validateState(state);
    }

    /**
     * Validation for state.
     *
     * @param state State as {@code State}.
     * @return State as {@code State}.
     * @throws NullPointerException When {@code state} is null.
     *
     */
    private State validateState(State state) {
        if (state == null) {
            throw new NullPointerException("State must not be null!");
        }
        return state;
    }
}
