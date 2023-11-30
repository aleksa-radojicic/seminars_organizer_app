/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.fon.common.intercomm;

/**
 * Enumeration representing an operation denoting what needs to be done with the
 * sent / received object.
 *
 * @author Aleksa
 * @since 0.0.1
 *
 */
public enum Operation {
    //Admin SOs:
    LOGIN,
    //Seminar SOs:
    CREATE_SEMINAR, GET_ALL_SEMINARS, GET_SEMINAR_BY_ID, GET_SEMINARS_BY_CONDITION, SAVE_SEMINAR,
    //Participant SOs:
    CREATE_PARTICIPANT, GET_ALL_PARTICIPANTS, GET_PARTICIPANTS_BY_CONDITION, GET_PARTICIPANT_BY_ID,
    //EducationalInstitution SOs:
    GET_ALL_EDUCATIONAL_INSTITUTIONS,
    //SeminarSchedule SOs:
    CREATE_SEMINAR_SCHEDULE, GET_SEMINAR_SCHEDULES_BY_CONDITION, GET_SEMINAR_SCHEDULE_BY_ID, SAVE_SEMINAR_SCHEDULE;
}