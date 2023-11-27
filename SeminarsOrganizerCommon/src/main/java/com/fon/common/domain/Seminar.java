/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Aleksa
 */
public class Seminar implements GenericEntity, Serializable {

    private int seminarID;
    private String name;
    private String description;
    private Admin createdByAdmin;
    private List<SeminarTopic> seminarTopics;
    private State state;

    public Seminar() {
        this.state = State.UNCHANGED;
    }

    public Seminar(int seminarID, String name, String description,
            Admin createdByAdmin, List<SeminarTopic> seminarTopics) {
        this.seminarID = seminarID;
        this.name = name;
        this.description = description;
        this.createdByAdmin = createdByAdmin;
        this.seminarTopics = seminarTopics;
        this.state = State.UNCHANGED;
    }

    @Override
    public String getAttributeNames() {
        return "seminarID, name, description, createdByAdminID";
    }

    @Override
    public String getAttributeValues() throws Exception {
        StringBuilder result = new StringBuilder();
        result
                .append(seminarID)
                .append(", '")
                .append(name)
                .append("', '")
                .append(description)
                .append("', ")
                .append(createdByAdmin.getAdminID());
        return result.toString();
    }

    @Override
    public String setAttributeValues() {
        return "seminarID = " + seminarID + ", name = '" + name + "', description = '" + description+"'";
    }

    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        Seminar seminar = new Seminar(rs.getInt(1), rs.getString(2), rs.getString(3), null, null);
        return seminar;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.seminarID;
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
        final Seminar other = (Seminar) obj;
        return this.seminarID == other.seminarID;
    }

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

    public Admin getCreatedByAdmin() {
        return createdByAdmin;
    }

    public void setCreatedByAdmin(Admin createdByAdmin) {
        this.createdByAdmin = createdByAdmin;
    }

    public int getSeminarID() {
        return seminarID;
    }

    public void setSeminarID(int seminarID) {
        this.seminarID = seminarID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SeminarTopic> getSeminarTopics() {
        return seminarTopics;
    }

    public void setSeminarTopics(List<SeminarTopic> seminarTopics) {
        this.seminarTopics = seminarTopics;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" : ");
        sb.append(description);
        return sb.toString();
    }

    public void setState(State state) {
        this.state = state;
    }
}
