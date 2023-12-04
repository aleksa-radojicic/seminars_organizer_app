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
     * Constructor with seminarSchedule and participant (composite primary key).
     *
     * @param seminarSchedule Seminar on the schedule participant is attending
     * as {@code SeminarSchedule}.
     * @param participant Participant attending the seminar on the schedule as
     * {@code Participant}.
     */
    public SeminarEnrollment(SeminarSchedule seminarSchedule, Participant participant) {
        this.seminarSchedule = seminarSchedule;
        this.participant = participant;
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
        this.notes = validateNotes(notes);
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
        StringBuilder sb = new StringBuilder();
        sb.append("SeminarEnrollment{");
        sb.append("seminarSchedule=").append(seminarSchedule);
        sb.append(", participant=").append(participant);
        sb.append(", notes=").append(notes);
        sb.append(", state=").append(state);
        sb.append('}');
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
    public String setAttributeValues() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
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

    /**
     * This method is not implemented.
     *
     * @throws UnsupportedOperationException Signifies the method is not yet
     * supported.
     */
    @Override
    public int getID() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * This method is not implemented.
     *
     * @param id Not used.
     * @throws UnsupportedOperationException Signifies the method is not yet
     * supported.
     */
    @Override
    public void setID(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getQueryCondition() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
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
        this.seminarSchedule = validateSeminarSchedule(seminarSchedule);
    }

    /**
     * Validation for seminarSchedule.
     *
     * @param seminarSchedule Seminar on the schedule as
     * {@code SeminarSchedule}.
     * @return Seminar on the schedule as {@code SeminarSchedule}.
     * @throws NullPointerException When {@code seminarSchedule} is null.
     *
     */
    private SeminarSchedule validateSeminarSchedule(SeminarSchedule seminarSchedule) {
        if (seminarSchedule == null) {
            throw new NullPointerException("Seminar on the schedule must not be null!");
        }
        return seminarSchedule;
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
        this.participant = validateParticipant(participant);
    }

    /**
     * Validation for seminarSchedule.
     *
     * @param participant Participant attending the seminar on the schedule as
     * {@code Participant}.
     * @return Participant attending the seminar on the schedule as
     * {@code Participant}.
     * @throws NullPointerException When {@code participant} is null.
     */
    private Participant validateParticipant(Participant participant) {
        if (participant == null) {
            throw new NullPointerException("Participant attending the seminar on the schedule must not be null!");
        }
        return participant;
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
        this.notes = validateNotes(notes);
    }

    /**
     * Validation for notes.
     *
     * @param notes SeminarSchedule's notes as {@code String}.
     * @return SeminarSchedule's notes as {@code String}.
     * @throws IllegalArgumentException When {@code notes} is greater than 100.
     */
    private String validateNotes(String notes) throws IllegalArgumentException {
        int maxLength = 100;
        if (notes.length() > maxLength) {
            throw new IllegalArgumentException(String.format("Notes must not be greater than %d!", maxLength));
        }
        return notes;
    }
}
