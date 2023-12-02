/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Domain class representing a scheduled seminar event within an educational
 * institution, along with all seminar enrollments.
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
public class SeminarSchedule implements GenericEntity {

    /**
     * Unique identifier of the SeminarSchedule as {@code int}.
     */
    private int seminarScheduleID;

    /**
     * SeminarSchedule's beginning date and time as {@code Date}, default is
     * today's date.
     */
    private Date datetimeBegins = new Date();

    /**
     * SeminarSchedule's end date and time as {@code Date}, default is today's
     * date.
     */
    private Date datetimeEnds = new Date();

    /**
     * Admin who created the SeminarSchedule as {@code Admin}.
     */
    private Admin createdByAdmin;

    /**
     * Seminar which is scheduled as {@code Seminar}.
     */
    private Seminar seminar;

    /**
     * Educational institution hosting the SeminarSchedule as
     * {@code EducationalInstitution}.
     */
    private EducationalInstitution educationalInstitution;

    /**
     * List of all SeminarEnrollments that the SeminarSchedule contains as
     * {@code List<SeminarEnrollment>}, default is an empty linked list.
     */
    private List<SeminarEnrollment> seminarEnrollments = new LinkedList();

    /**
     * Seminar's state as {@code State}, default is {@code State.UNCHANGED}.
     */
    private State state = State.UNCHANGED;

    /**
     * Non-parametric constructor.
     */
    public SeminarSchedule() {
    }

    /**
     * Constructor with seminarScheduleID (primary key).
     *
     * @param seminarScheduleID ID as {@code int}.
     */
    public SeminarSchedule(int seminarScheduleID) {
        this.seminarScheduleID = seminarScheduleID;
    }

    /**
     * Constructor with all parameters except state.
     *
     * @param seminarScheduleID ID as {@code int}.
     * @param datetimeBegins Beginning date and time as {@code Date}.
     * @param datetimeEnds End date and time as {@code Date}.
     * @param createdByAdmin Admin who created the Seminar as {@code Admin}.
     * @param seminar Seminar which is scheduled as {@code Seminar}.
     * @param educationalInstitution Educational institution hosting the
     * SeminarSchedule as {@code EducationalInstitution}.
     * @param seminarEnrollments List of all SeminarEnrollments that the
     * SeminarSchedule contains as {@code List<SeminarEnrollment>}.
     */
    public SeminarSchedule(int seminarScheduleID, Date datetimeBegins, Date datetimeEnds, Admin createdByAdmin,
            Seminar seminar, EducationalInstitution educationalInstitution, List<SeminarEnrollment> seminarEnrollments) {
        this.seminarScheduleID = seminarScheduleID;
        this.datetimeBegins = datetimeBegins;
        this.datetimeEnds = datetimeEnds;
        this.createdByAdmin = createdByAdmin;
        this.seminar = seminar;
        this.educationalInstitution = educationalInstitution;
        this.seminarEnrollments = seminarEnrollments;
    }

    /**
     * toString method which returns all attributes of SeminarSchedule.
     *
     * @return String representation of the SeminarSchedule.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SeminarSchedule{");
        sb.append("seminarScheduleID=").append(seminarScheduleID);
        sb.append(", datetimeBegins=").append(datetimeBegins);
        sb.append(", datetimeEnds=").append(datetimeEnds);
        sb.append(", createdByAdmin=").append(createdByAdmin);
        sb.append(", seminar=").append(seminar);
        sb.append(", educationalInstitution=").append(educationalInstitution);
        sb.append(", state=").append(state);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Returns hash code, calculated using seminarScheduleID.
     *
     * @return Hash code as {@code int}.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.seminarScheduleID;
        return hash;
    }

    /**
     * Equals method which compares all attributes except createdByAdmin and
     * state. Used in a form for updating seminar schedules to check if the
     * SeminarSchedule is changed.
     *
     * @param obj
     * @return {@code true} if all attributes except createdByAdmin and state
     * are equal including full equality of seminarEnrollments, otherwise
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
        final SeminarSchedule other = (SeminarSchedule) obj;
        if (this.seminarScheduleID != other.seminarScheduleID) {
            return false;
        }
        if (!Objects.equals(this.datetimeBegins, other.datetimeBegins)) {
            return false;
        }
        if (!Objects.equals(this.datetimeEnds, other.datetimeEnds)) {
            return false;
        }
        if (!Objects.equals(this.seminar, other.seminar)) {
            return false;
        }
        if (!Objects.equals(this.educationalInstitution, other.educationalInstitution)) {
            return false;
        }

        return SeminarEnrollment.equalsAll(this.seminarEnrollments, other.seminarEnrollments);
    }

    /**
     * Equals method which compares all attributes except createdByAdmin, state
     * and seminarEnrollments. Used in a form for updating seminar schedules s
     * to check if the SeminarSchedules's main attributes are changed.
     *
     * @param obj
     * @return {@code true} if all attributes except createdByAdmin, state and
     * seminarEnrollments are equal, otherwise {@code false}.
     */
    public boolean equalsAllWithoutSeminarEnrollments(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SeminarSchedule other = (SeminarSchedule) obj;
        if (this.seminarScheduleID != other.seminarScheduleID) {
            return false;
        }
        if (!Objects.equals(this.datetimeBegins, other.datetimeBegins)) {
            return false;
        }
        if (!Objects.equals(this.datetimeEnds, other.datetimeEnds)) {
            return false;
        }
        if (!Objects.equals(this.seminar, other.seminar)) {
            return false;
        }
        return Objects.equals(this.educationalInstitution, other.educationalInstitution);
    }

    /**
     * Equals method which compares seminarScheduleID.
     *
     * @return {@code true} if seminarScheduleIDs are equal, otherwise
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
        final SeminarSchedule other = (SeminarSchedule) obj;
        return this.seminarScheduleID == other.seminarScheduleID;
    }

    @Override
    public String getAttributeNames() {
        return "seminarScheduleID, datetimeBegins, datetimeEnds, createdByAdminID, seminarID, educationalInstitutionID";
    }

    @Override
    public String getAttributeValues() {
        StringBuilder result = new StringBuilder();
        result
                .append(seminarScheduleID)
                .append(", '")
                .append(new java.sql.Timestamp(datetimeBegins.getTime()))
                .append("', '")
                .append(new java.sql.Timestamp(datetimeEnds.getTime()))
                .append("', ")
                .append(createdByAdmin.getAdminID())
                .append(", ")
                .append(seminar.getSeminarID())
                .append(", ")
                .append(educationalInstitution.getEducationalInstitutionID());
        return result.toString();
    }

    @Override
    public String setAttributeValues() {
        return "datetimeBegins= '" + new java.sql.Timestamp(getDatetimeBegins().getTime()) + "',"
                + "datetimeEnds= '" + new java.sql.Timestamp(getDatetimeEnds().getTime()) + "',"
                + "seminarID=" + getSeminar().getSeminarID();
    }

    @Override
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException {
        SeminarSchedule seminarSchedule = new SeminarSchedule(rs.getInt("ss.seminarScheduleID"),
                rs.getTimestamp("datetimeBegins"),
                rs.getTimestamp("datetimeEnds"),
                null,
                new Seminar(rs.getInt("seminarID"), rs.getString("s.name"), rs.getString("description"), null, null),
                new EducationalInstitution(rs.getInt("educationalInstitutionID"), rs.getString("ei.name"), rs.getString("address")),
                null);
        return seminarSchedule;
    }

    @Override
    public String getSelectAllQuery() {
        return """
               SELECT ss.`seminarScheduleID`, ss.`datetimeBegins`, ss.`datetimeEnds`, s.`seminarID`, s.`name` as 's.name', s.`description`, ei.`educationalInstitutionID`, ei.`name` as 'ei.name', ei.`address`
               FROM `seminarschedules` ss JOIN `educationalinstitutions` ei ON ss.`educationalInstitutionID` = ei.`educationalInstitutionID`
               JOIN `seminars` s ON ss.`seminarID` = s.`seminarID`""";
    }

    /**
     * Setter for state.
     *
     * @param state State as {@code State}
     */
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public State getState() {
        return state;
    }

    /**
     * Getter for seminarScheduleID.
     *
     * @return ID as {@code int}.
     */
    public int getSeminarScheduleID() {
        return seminarScheduleID;
    }

    /**
     * Setter for seminarScheduleID.
     *
     * @param seminarScheduleID ID as {@code int}.
     */
    public void setSeminarScheduleID(int seminarScheduleID) {
        this.seminarScheduleID = seminarScheduleID;
    }

    /**
     * Getter for datetimeBegins.
     *
     * @return SeminarSchedule's beginning date and time as {@code Date}.
     */
    public Date getDatetimeBegins() {
        return datetimeBegins;
    }

    /**
     * Setter for datetimeBegins.
     *
     * @param datetimeBegins SeminarSchedule's beginning date and time as
     * {@code Date}.
     */
    public void setDatetimeBegins(Date datetimeBegins) {
        this.datetimeBegins = datetimeBegins;
    }

    /**
     * Getter for datetimeEnds.
     *
     * @return SeminarSchedule's end date and time as {@code Date}.
     */
    public Date getDatetimeEnds() {
        return datetimeEnds;
    }

    /**
     * Setter for datetimeEnds.
     *
     * @param datetimeEnds SeminarSchedule's end date and time as {@code Date}.
     */
    public void setDatetimeEnds(Date datetimeEnds) {
        this.datetimeEnds = datetimeEnds;
    }

    /**
     * Getter for createdByAdmin.
     *
     * @return Admin who created the SeminarSchedule as {@code Admin}.
     */
    public Admin getCreatedByAdmin() {
        return createdByAdmin;
    }

    /**
     * Setter for createdByAdmin.
     *
     * @param createdByAdmin Admin who created the SeminarSchedule as
     * {@code Admin}.
     */
    public void setCreatedByAdmin(Admin createdByAdmin) {
        this.createdByAdmin = createdByAdmin;
    }

    /**
     * Getter for seminar.
     *
     * @return Seminar which is scheduled as {@code Seminar}.
     */
    public Seminar getSeminar() {
        return seminar;
    }

    /**
     * Setter for seminar.
     *
     * @param seminar Seminar which is scheduled as {@code Seminar}.
     */
    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
    }

    /**
     * Getter for educationalInstitution.
     *
     * @return Educational institution hosting the SeminarSchedule as
     * {@code EducationalInstitution}.
     */
    public EducationalInstitution getEducationalInstitution() {
        return educationalInstitution;
    }

    /**
     * Setter for educationalInstitution.
     *
     * @param educationalInstitution Educational institution hosting the
     * SeminarSchedule as {@code EducationalInstitution}.
     */
    public void setEducationalInstitution(EducationalInstitution educationalInstitution) {
        this.educationalInstitution = educationalInstitution;
    }

    /**
     * Getter for seminarEnrollments.
     *
     * @return List of all SeminarEnrollments that the SeminarSchedule contains
     * as {@code List<SeminarEnrollment>}.
     */
    public List<SeminarEnrollment> getSeminarEnrollments() {
        return seminarEnrollments;
    }

    /**
     * Setter for seminarEnrollments.
     *
     *
     * @param seminarEnrollments List of all SeminarEnrollments that the
     * SeminarSchedule contains as {@code List<SeminarEnrollment>}.
     */
    public void setSeminarEnrollments(List<SeminarEnrollment> seminarEnrollments) {
        this.seminarEnrollments = seminarEnrollments;
    }
}
