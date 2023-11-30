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
 * Domain class representing a scheduled enrollment, containing details of a
 * participant who will be attending a seminar on the schedule.
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
public class SeminarEnrollment implements GenericEntity {

    /**
     * Seminar on the schedule participant is attending as
     * {@code SeminarSchedule}, part of the primary key.
     */
    private SeminarSchedule seminarSchedule;

    /**
     * Participant attending the seminar on the schedule as {@code Participant},
     * part of the primary key.
     */
    private Participant participant;

    /**
     * SeminarSchedule's notes as {@code String}.
     */
    private String notes;

    /**
     * SeminarSchedule's state as {@code State}, default is
     * {@code State.UNCHANGED}.
     */
    private State state = State.UNCHANGED;

    /**
     * Non-parametric constructor.
     */
    public SeminarEnrollment() {
    }

    /**
     * Constructor with all parameters except state.
     *
     * @param seminarSchedule Seminar on the schedule participant is attending
     * as {@code SeminarSchedule}.
     * @param participant Participant attending the seminar on the schedule as
     * {@code Participant}.
     * @param notes SeminarSchedule's notes as {@code String}.
     */
    public SeminarEnrollment(SeminarSchedule seminarSchedule, Participant participant, String notes) {
        this.seminarSchedule = seminarSchedule;
        this.participant = participant;
        this.notes = notes;
    }

    /**
     * Returns hash code, calculated using seminarSchedule and participant.
     *
     * @return Hash code as {@code int}.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.seminarSchedule);
        hash = 37 * hash + Objects.hashCode(this.participant);
        return hash;
    }

    /**
     * toString method which returns all attributes of SeminarEnrollment.
     *
     * @return String representation of the SeminarEnrollment.
     */
    @Override
    public String toString() {
        return "SeminarEnrollment{" + "seminarSchedule=" + seminarSchedule + ", participant=" + participant + ", notes=" + notes + ", state=" + state + '}';
    }

    /**
     * Setter for state.
     *
     * @param state State as {@code State}.
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Compares two lists of seminar enrollments to check for full equality.
     *
     * <p>
     * This static method compares two lists of SeminarEnrollment instances,
     * validating their full equality (meaning comparing all attributes). It
     * iterates through both lists to verify if the sizes match and if each
     * corresponding SeminarEnrollment within the lists are fully equaled.
     * </p>
     *
     * @param seminarEnrollments First list of seminar enrollments for
     * comparison.
     * @param others Second list of seminar enrollments for comparison.
     *
     * @return {@code false} if lists sizes are not equal. While iterating
     * through first list, if according SeminarEnrollments are not fully
     * equaled, then returns {@code false}. If all according seminar enrollments
     * are equal returns {@code true}.
     */
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

    /**
     * Equals method which compares seminarSchedule and participant (composite
     * primary key).
     *
     * @return {@code true} if seminarSchedules and participants are equal,
     * otherwise {@code false}.
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
        final SeminarEnrollment other = (SeminarEnrollment) obj;
        if (!this.seminarSchedule.equals(other.seminarSchedule)) {
            return false;
        }
        return Objects.equals(this.participant, other.participant);
    }

    /**
     * Equals method which compares all attributes except state. Used in a form
     * for updating seminar schedules to check if the SeminarSchedule is
     * changed.
     *
     * @param obj
     * @return {@code true} if all attributes except state are equal, otherwise
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
        final SeminarEnrollment other = (SeminarEnrollment) obj;
        if (!Objects.equals(this.seminarSchedule, other.seminarSchedule)) {
            return false;
        }
        if (!Objects.equals(this.participant, other.participant)) {
            return false;
        }
        return Objects.equals(this.notes, other.notes);
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

    /**
     * Getter for seminarSchedule.
     *
     * @return Seminar on the schedule as {@code SeminarSchedule}.
     */
    public SeminarSchedule getSeminarSchedule() {
        return seminarSchedule;
    }

    /**
     * Setter for seminarSchedule.
     *
     * @param seminarSchedule Seminar on the schedule as
     * {@code SeminarSchedule}.
     */
    public void setSeminarSchedule(SeminarSchedule seminarSchedule) {
        this.seminarSchedule = seminarSchedule;
    }

    /**
     * Getter for participant.
     *
     * @return Participant attending the seminar on the schedule as
     * {@code Participant}.
     */
    public Participant getParticipant() {
        return participant;
    }

    /**
     * Setter for participant.
     *
     * @param participant Participant attending the seminar on the schedule as
     * {@code Participant}.
     */
    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    /**
     * Getter for notes.
     *
     * @return SeminarSchedule's notes as {@code String}.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Setter for notes.
     *
     * @param notes SeminarSchedule's notes as {@code String}.
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
