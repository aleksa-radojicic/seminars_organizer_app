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
        this.name = name;
        this.description = description;
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
        this.name = name;
        this.description = description;
        createdByAdmin = new Admin();
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
        return String.format("seminarID = %d, name ='%s', description = '%s'", seminarID, name, description);
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
        this.createdByAdmin = createdByAdmin;
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
        this.name = name;
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
        this.description = description;
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
        this.seminarTopics = seminarTopics;
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
        this.state = state;
    }
}
