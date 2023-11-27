/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Aleksa
 */
public class SeminarSchedule implements GenericEntity, Serializable {

    private int seminarScheduleID;
    private Date datetimeBegins;
    private Date datetimeEnds;
    private Admin createdByAdmin;
    private Seminar seminar;
    private EducationalInstitution educationalInstitution;
    private List<SeminarEnrollment> seminarEnrollments;
    private State state;

    public SeminarSchedule() {
        seminarEnrollments = new LinkedList();
        this.state = State.UNCHANGED;
    }

    public SeminarSchedule(int seminarScheduleID, Date datetimeBegins, Date datetimeEnds, Admin createdByAdmin,
            Seminar seminar, EducationalInstitution educationalInstitution, List<SeminarEnrollment> seminarEnrollments) {
        this.seminarScheduleID = seminarScheduleID;
        this.datetimeBegins = datetimeBegins;
        this.datetimeEnds = datetimeEnds;
        this.createdByAdmin = createdByAdmin;
        this.seminar = seminar;
        this.educationalInstitution = educationalInstitution;
        this.seminarEnrollments = seminarEnrollments;
        this.state = State.UNCHANGED;
    }

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
        sb.append('}');
        return sb.toString();
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

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public State getState() {
        return state;
    }

    public int getSeminarScheduleID() {
        return seminarScheduleID;
    }

    public void setSeminarScheduleID(int seminarScheduleID) {
        this.seminarScheduleID = seminarScheduleID;
    }

    public Date getDatetimeBegins() {
        return datetimeBegins;
    }

    public void setDatetimeBegins(Date datetimeBegins) {
        this.datetimeBegins = datetimeBegins;
    }

    public Date getDatetimeEnds() {
        return datetimeEnds;
    }

    public void setDatetimeEnds(Date datetimeEnds) {
        this.datetimeEnds = datetimeEnds;
    }

    public Admin getCreatedByAdmin() {
        return createdByAdmin;
    }

    public void setCreatedByAdmin(Admin createdByAdmin) {
        this.createdByAdmin = createdByAdmin;
    }

    public Seminar getSeminar() {
        return seminar;
    }

    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
    }

    public EducationalInstitution getEducationalInstitution() {
        return educationalInstitution;
    }

    public void setEducationalInstitution(EducationalInstitution educationalInstitution) {
        this.educationalInstitution = educationalInstitution;
    }

    public List<SeminarEnrollment> getSeminarEnrollments() {
        return seminarEnrollments;
    }

    public void setSeminarEnrollments(List<SeminarEnrollment> seminarEnrollments) {
        this.seminarEnrollments = seminarEnrollments;
    }

}
