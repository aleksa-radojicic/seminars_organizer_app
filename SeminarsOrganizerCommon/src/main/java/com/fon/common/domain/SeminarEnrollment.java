/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Aleksa
 */
public class SeminarEnrollment implements GenericEntity, Serializable {

    private SeminarSchedule seminarSchedule;
    private Participant participant;
    private String notes;
    private State state;

    public SeminarEnrollment() {
        this.state = State.UNCHANGED;
    }

    public SeminarEnrollment(SeminarSchedule seminarSchedule, Participant participant, String notes) {
        this.seminarSchedule = seminarSchedule;
        this.participant = participant;
        this.notes = notes;
        this.state = State.UNCHANGED;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public String toString() {
        return "SeminarEnrollment{" + "seminarSchedule=" + seminarSchedule + ", participant=" + participant + ", notes=" + notes + ", state=" + state + '}';
    }

    public void setState(State state) {
        this.state = state;
    }

    public static boolean equalsAll(List<SeminarEnrollment> seminarEnrollments, List<SeminarEnrollment> others) {
        if (seminarEnrollments.size() != others.size()) {
            return false;
        }

        for (int i = 0; i < seminarEnrollments.size(); i++) {
            SeminarEnrollment seminarEnrollment = seminarEnrollments.get(i);
            SeminarEnrollment other = others.get(i);

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
        final SeminarEnrollment other = (SeminarEnrollment) obj;
        if (!this.seminarSchedule.equals(other.seminarSchedule)) {
            return false;
        }
        return Objects.equals(this.participant, other.participant);
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
        final SeminarEnrollment other = (SeminarEnrollment) obj;
        if (!Objects.equals(this.notes, other.notes)) {
            return false;
        }
        return Objects.equals(this.participant, other.participant);
    }

    @Override
    public String getAttributeNames() {
        return "seminarScheduleID, participantID, notes";
    }

    @Override
    public String getAttributeValues() {
        StringBuilder result = new StringBuilder();
        result
                .append(seminarSchedule.getSeminarScheduleID())
                .append(", ")
                .append(participant.getParticipantID())
                .append(", '")
                .append(notes)
                .append("'");
        return result.toString();
    }

    @Override
    public String setAttributeValues() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        SeminarEnrollment seminarEnrollment = new SeminarEnrollment(null,
                new Participant(rs.getInt("participantID"), rs.getString("name"), rs.getString("surname"), Sex.valueOf(rs.getString("sex")), rs.getDate("dateBirth"), null),
                rs.getString("notes"));
        return seminarEnrollment;
    }

    @Override
    public String getSelectAllQuery() {
        return """
               SELECT se.`seminarScheduleID`, se.`participantID`, se.`notes`, p.`name`, p.`surname`, p.`dateBirth`, p.`sex` 
               FROM `seminarenrollments` se JOIN `participants` p ON se.`participantID` = p.`participantID`""";
    }

    @Override
    public State getState() {
        return state;
    }

    public SeminarSchedule getSeminarSchedule() {
        return seminarSchedule;
    }

    public void setSeminarSchedule(SeminarSchedule seminarSchedule) {
        this.seminarSchedule = seminarSchedule;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
