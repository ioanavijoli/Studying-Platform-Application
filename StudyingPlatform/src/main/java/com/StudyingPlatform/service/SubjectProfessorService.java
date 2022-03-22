package com.StudyingPlatform.service;

import com.StudyingPlatform.model.Professor;
import com.StudyingPlatform.model.ScheduleTime;
import com.StudyingPlatform.model.Subject;
import com.StudyingPlatform.model.SubjectProfessor;
import com.StudyingPlatform.service.Exceptions.EmptyResultSetException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectProfessorService {
    public static Subject mapResultSet(ResultSet resultSet) throws SQLException, EmptyResultSetException {
        if (resultSet.next()) {
            SubjectProfessor subjectProfessor = new SubjectProfessor(
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
                    resultSet.getBoolean("finished_schedule")
            );
            resultSet.previous();
            return subjectProfessor;
        } else {
            resultSet.previous();
            throw new EmptyResultSetException();
        }
    }

    public static List<SubjectProfessor> mapFullResultSet(ResultSet resultSet) throws SQLException, EmptyResultSetException {
        List<SubjectProfessor> subjectList = new ArrayList<>();
        while (resultSet.next()) {
            SubjectProfessor subjectProfessor = new SubjectProfessor(
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
                    resultSet.getBoolean("finished_schedule")
            );
            subjectList.add(subjectProfessor);
        }
        if (subjectList.isEmpty()) {
            throw new EmptyResultSetException();
        }
        return subjectList;
    }

    public static void schedule_activities(SubjectProfessor subjectProfessor) throws SQLException {
        Connection connection = DataBaseService.getConnection();
        CallableStatement stmt = connection.prepareCall("call schedule_activities(?,?,?,?,?,?,?,?,?,?,?,?)");
        stmt.setInt(1, subjectProfessor.getProfessor().getId());
        stmt.setInt(2, subjectProfessor.getId());
        if (subjectProfessor.getHasLecture()) {
            stmt.setString(3, subjectProfessor.getScheduleLecture().getDayOfWeek().toString());
            stmt.setInt(4, subjectProfessor.getScheduleLecture().getHour());
            stmt.setInt(5, subjectProfessor.getScheduleLecture().getDuration());
        } else {
            stmt.setString(3, null);
            stmt.setInt(4, 0);
            stmt.setInt(5, 0);
        }
        if (subjectProfessor.getHasSeminar()) {
            stmt.setString(6, subjectProfessor.getScheduleSeminar().getDayOfWeek().toString());
            stmt.setInt(7, subjectProfessor.getScheduleSeminar().getHour());
            stmt.setInt(8, subjectProfessor.getScheduleSeminar().getDuration());
        } else {
            stmt.setString(6, null);
            stmt.setInt(7, 0);
            stmt.setInt(8, 0);
        }
        if (subjectProfessor.getHasLab()) {
            stmt.setString(9, subjectProfessor.getScheduleLab().getDayOfWeek().toString());
            stmt.setInt(10, subjectProfessor.getScheduleLab().getHour());
            stmt.setInt(11, subjectProfessor.getScheduleLab().getDuration());
        } else {
            stmt.setString(9,null);
            stmt.setInt(10, 0);
            stmt.setInt(11, 0);
        }
        stmt.setInt(12, subjectProfessor.getStudentsCapacity());
        stmt.executeUpdate();
    }

    public static void setWeights(Subject subject,Professor professor, float weightLecture, float weightSeminar, float weightLab) throws SQLException{
        Connection connection = DataBaseService.getConnection();
        CallableStatement stmt = connection.prepareCall("call set_weight(?,?,?,?,?)");
        stmt.setInt(1,professor.getId());
        stmt.setInt(2, subject.getId());
        stmt.setFloat(3,weightLecture);
        stmt.setFloat(4,weightSeminar);
        stmt.setFloat(5,weightLab);
        stmt.executeUpdate();
    }
}
