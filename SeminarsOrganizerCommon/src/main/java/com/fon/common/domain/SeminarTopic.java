/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Domain class representing a seminar topic belonging to a specific seminar.
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
public class SeminarTopic implements GenericEntity {

    /**
     * Seminar that contains the SeminarTopic as {@code Seminar}, part of the
     * primary key.
     */
    private Seminar seminar;

    /**
     * Unique identifier of the SeminarTopic as {@code int}, part of the primary
     * key.
     */
    private int seminarTopicID;

    /**
     * SeminarTopic's name as {@code String}.
     */
    private String name;

    /**
     * SeminarTopic's presenter, consisting of a name and a surname, as
     * {@code String}.
     */
    private String presenter;

    /**
     * SeminarTopic's state as {@code State}, default is
     * {@code State.UNCHANGED}.
     */
    private State state = State.UNCHANGED;

    /**
     * Non-parametric constructor.
     */
    public SeminarTopic() {
    }

    /**
     * Constructor with seminar and seminarTopicID (composite primary key).
     *
     * @param seminar Seminar that contains the SeminarTopic as {@code Seminar}.
     * @param seminarTopicID ID as {@code int}.
     */
    public SeminarTopic(Seminar seminar, int seminarTopicID) {
        this.seminar = seminar;
        this.seminarTopicID = seminarTopicID;
    }

    /**
     * Constructor with all parameters except state.
     *
     * @param seminar Seminar that contains the SeminarTopic as {@code Seminar}.
     * @param seminarTopicID ID as {@code int}.
     * @param name Name as {@code String}.
     * @param presenter Presenter as {@code String}.
     */
    public SeminarTopic(Seminar seminar, int seminarTopicID, String name, String presenter) {
        this.seminar = seminar;
        this.seminarTopicID = seminarTopicID;
        this.name = name;
        this.presenter = presenter;
    }

    /**
     * Constructor with seminarTopicID.
     *
     * @param seminarTopicID ID as {@code int}.
     */
    public SeminarTopic(int seminarTopicID) {
        this.seminarTopicID = seminarTopicID;
    }

    /**
     * toString method which returns all attributes of SeminarTopic.
     *
     * @return String representation of the SeminarTopic.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("seminar=").append(seminar.getSeminarID());
        sb.append(", seminarTopicID=").append(seminarTopicID);
        sb.append(", name=").append(name);
        sb.append(", presenter=").append(presenter);
        sb.append(", state=").append(state);
        return sb.toString();
    }

    @Override
    public String getAttributeNames() {
        return "seminarID, seminarTopicID, name, presenter";
    }

    @Override
    public String getAttributeValues() {
        return String.format("%d, %d, '%s', '%s'",
                seminar.getSeminarID(), seminarTopicID, name, presenter);
    }

    @Override
    public String setAttributeValues() {
        return String.format("name = '%s', presenter = '%s'", name, presenter);
    }

    /**
     * Returns hash code, calculated using seminar and seminarTopicID.
     *
     * @return Hash code as {@code int}.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.seminar);
        hash = 83 * hash + this.seminarTopicID;
        return hash;
    }

    /**
     * Equals method which compares all attributes.Used in a form for updating
     * seminars to check if the Seminar is changed.
     *
     * @param obj
     * @return {@code true} if all attributes are equal, otherwise
     * {@code false}.
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
        final SeminarTopic other = (SeminarTopic) obj;
        if (this.seminarTopicID != other.seminarTopicID) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.presenter, other.presenter)) {
            return false;
        }
        return Objects.equals(this.seminar, other.seminar);
    }

    /**
     * Compares two lists of seminar topics to check for full equality.
     *
     * <p>
     * This static method compares two lists of SeminarTopic instances,
     * validating their full equality (meaning comparing all attributes). It
     * iterates through both lists to verify if the sizes match and if each
     * corresponding SeminarTopic within the lists are fully equaled.
     * </p>
     *
     * @param seminarTopics First list of seminar topics for comparison.
     * @param others Second list of seminar topics for comparison.
     * @return {@code false} if lists sizes are not equal. While iterating
     * through first list, if according SeminarTopics are not fully equaled,
     * then returns {@code false}. If all according seminar topics are equal
     * returns {@code true}.
     */
    public static boolean equalsAll(List<SeminarTopic> seminarTopics, List<SeminarTopic> others) {
        if (seminarTopics.size() != others.size()) {
            return false;
        }

        for (int i = 0; i < seminarTopics.size(); i++) {
            SeminarTopic seminarEnrollment = seminarTopics.get(i);
            SeminarTopic other = others.get(i);

            if (!seminarEnrollment.equalsAll(other)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Equals method which compares seminarTopicID and seminar.
     *
     * @return {@code true} if seminarTopicIDs and seminars are equal, otherwise
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
        final SeminarTopic other = (SeminarTopic) obj;
        if (this.seminarTopicID != other.seminarTopicID) {
            return false;
        }
        return Objects.equals(this.seminar, other.seminar);
    }

    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        SeminarTopic st = new SeminarTopic(null, rs.getInt("seminarTopicID"), rs.getString("name"), rs.getString("presenter"));
        return st;
    }

    @Override
    public String getQueryCondition() throws Exception {
        return String.format("seminarID = %d AND seminarTopicID = %d ", seminar.getSeminarID(), seminarTopicID);
    }

    @Override
    public State getState() {
        return state;
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
     * Getter for presenter.
     *
     * @return Presenter as {@code String}.
     */
    public String getPresenter() {
        return presenter;
    }

    /**
     * Setter for presenter.
     *
     * @param presenter Presenter as {@code String}.
     */
    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    /**
     * Getter for seminar.
     *
     * @return Seminar as {@code Seminar}.
     */
    public Seminar getSeminar() {
        return seminar;
    }

    /**
     * Setter for seminar.
     *
     * @param seminar Seminar as {@code Seminar}.
     */
    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
    }

    /**
     * Getter for seminarTopicID.
     *
     * @return ID as {@code int}.
     */
    public int getSeminarTopicID() {
        return seminarTopicID;
    }

    /**
     * Setter for seminarTopicID.
     *
     * @param seminarTopicID ID as {@code int}.
     */
    public void setSeminarTopicID(int seminarTopicID) {
        this.seminarTopicID = seminarTopicID;
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
