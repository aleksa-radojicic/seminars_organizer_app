/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.fon.common.intercomm;

/**
 *
 * @author Aleksa
 */
public enum Operation {
    LOGIN, 
    CREATE_SEMINAR, GET_ALL_SEMINARS, GET_SEMINAR_BY_ID, GET_SEMINARS_BY_CONDITION, SAVE_SEMINAR,
    CREATE_PARTICIPANT, GET_ALL_PARTICIPANTS, GET_PARTICIPANTS_BY_CONDITION, GET_PARTICIPANT_BY_ID, 
    GET_ALL_EDUCATIONAL_INSTITUTIONS, 
    CREATE_SEMINAR_SCHEDULE, GET_SEMINAR_SCHEDULES_BY_CONDITION, GET_SEMINAR_SCHEDULE_BY_ID, SAVE_SEMINAR_SCHEDULE;
}
