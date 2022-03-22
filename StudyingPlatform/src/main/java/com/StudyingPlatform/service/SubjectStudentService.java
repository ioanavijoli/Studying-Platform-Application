package com.StudyingPlatform.service;

import com.StudyingPlatform.model.*;
import com.StudyingPlatform.service.Exceptions.EmptyResultSetException;
import com.StudyingPlatform.service.Exceptions.UserNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectStudentService {
    public static SubjectStudent mapResultSet(ResultSet resultSet) throws SQLException, EmptyResultSetException {
        if (resultSet.next()) {
            SubjectStudent subjectStudent = new SubjectStudent(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getBoolean("has_lecture"),
                    resultSet.getBoolean("has_seminar"),
                    resultSet.getBoolean("has_lab"),
                    resultSet.getDate("date_start"),
                    resultSet.getDate("date_end"),
                    null,
                    resultSet.getInt("students_capacity"),
                    resultSet.getFloat("lecture_weight"),
                    resultSet.getFloat("seminar_weight"),
                    resultSet.getFloat("lab_weight"),
                    new ScheduleTime(
                            resultSet.getString("schedule_lecture_day"),
                            resultSet.getInt("schedule_lecture_hour"),
                            resultSet.getInt("lecture_duration")
                    ),
                    new ScheduleTime(
                            resultSet.getString("schedule_seminar_day"),
                            resultSet.getInt("schedule_seminar_hour"),
                            resultSet.getInt("seminar_duration")
                    ),
                    new ScheduleTime(
                            resultSet.getString("schedule_lab_day"),
                            resultSet.getInt("schedule_lab_hour"),
                            resultSet.getInt("lab_duration")
                    ),
                    resultSet.getBoolean("finished_schedule"),
                    null,
                    resultSet.getInt("grade_lecture"),
                    resultSet.getInt("grade_seminar"),
                    resultSet.getInt("grade_lab"),
                    resultSet.getBoolean("enrolled_in_lecture"),
                    resultSet.getBoolean("enrolled_in_seminar"),
                    resultSet.getBoolean("enrolled_in_lab")
            );
            try {
                subjectStudent.setProfessor((Professor)DataBaseService.getUserById(resultSet.getInt("professor_id")));
            }catch(UserNotFoundException e){
                e.printStackTrace();
            }
            resultSet.previous();
            return subjectStudent;
        } else {
            resultSet.previous();
            throw new EmptyResultSetException();
        }
    }

    public static List<SubjectStudent> mapFullResultSet(ResultSet resultSet) throws SQLException, EmptyResultSetException {
        List<SubjectStudent> subjectList = new ArrayList<>();
        while (resultSet.next()) {
            SubjectStudent subjectStudent = new SubjectStudent(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getBoolean("has_lecture"),
                    resultSet.getBoolean("has_seminar"),
                    resultSet.getBoolean("has_lab"),
                    resultSet.getDate("date_start"),
                    resultSet.getDate("date_end"),
                    null,
                    resultSet.getInt("students_capacity"),
                    resultSet.getFloat("lecture_weight"),
                    resultSet.getFloat("seminar_weight"),
                    resultSet.getFloat("lab_weight"),
                    new ScheduleTime(
                            resultSet.getString("schedule_lecture_day"),
                            resultSet.getInt("schedule_lecture_hour"),
                            resultSet.getInt("lecture_duration")
                    ),
                    new ScheduleTime(
                            resultSet.getString("schedule_seminar_day"),
                            resultSet.getInt("schedule_seminar_hour"),
                            resultSet.getInt("seminar_duration")
                    ),
                    new ScheduleTime(
                            resultSet.getString("schedule_lab_day"),
                            resultSet.getInt("schedule_lab_hour"),
                            resultSet.getInt("lab_duration")
                    ),
                    resultSet.getBoolean("finished_schedule"),
                    null,
                    resultSet.getInt("grade_lecture"),
                    resultSet.getInt("grade_seminar"),
                    resultSet.getInt("grade_lab"),
                    resultSet.getBoolean("enrolled_in_lecture"),
                    resultSet.getBoolean("enrolled_in_seminar"),
                    resultSet.getBoolean("enrolled_in_lab")
            );
            try {
                subjectStudent.setProfessor((Professor)DataBaseService.getUserById(resultSet.getInt("professor_id")));
            }catch(UserNotFoundException e){
                e.printStackTrace();
            }
            subjectList.add(subjectStudent);
        }
        if (subjectList.isEmpty()) {
            throw new EmptyResultSetException();
        }
        return subjectList;
    }
}
