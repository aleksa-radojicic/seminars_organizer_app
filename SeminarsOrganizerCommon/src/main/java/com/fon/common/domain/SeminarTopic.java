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
 *
 * @author Aleksa
 */
public class SeminarTopic implements GenericEntity {

    private Seminar seminar;
    private int seminarTopicID;
    private String name;
    private String presenter;
    private State state;

    public SeminarTopic() {
        this.state = State.UNCHANGED;
    }

    public SeminarTopic(Seminar seminar, int seminarTopicID, String name, String presenter) {
        this.seminar = seminar;
        this.seminarTopicID = seminarTopicID;
        this.name = name;
        this.presenter = presenter;
        this.state = State.UNCHANGED;
    }

    public SeminarTopic(int seminarTopicID) {
        this.seminarTopicID = seminarTopicID;
    }

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
        StringBuilder result = new StringBuilder();
        result
                .append(seminar.getSeminarID())
                .append(", ")
                .append(seminarTopicID)
                .append(", '")
                .append(name)
                .append("', '")
                .append(presenter)
                .append("'");
        return result.toString();
    }

    @Override
    public String setAttributeValues() {
        return "seminarID=" + getSeminar().getSeminarID() + ", "
                + "seminarTopicID= " + seminarTopicID + ","
                + "name= '" + getName() + "',"
                + "presenter= '" + getPresenter() + "'";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
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
        SeminarTopic st = new SeminarTopic(null, rs.getInt(2), rs.getString(3), rs.getString(4));
        return st;
    }

    @Override
    public State getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPresenter() {
        return presenter;
    }

    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    public Seminar getSeminar() {
        return seminar;
    }

    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
    }

    public int getSeminarTopicID() {
        return seminarTopicID;
    }

    public void setSeminarTopicID(int seminarTopicID) {
        this.seminarTopicID = seminarTopicID;
    }

    public void setState(State state) {
        this.state = state;
    }

}
