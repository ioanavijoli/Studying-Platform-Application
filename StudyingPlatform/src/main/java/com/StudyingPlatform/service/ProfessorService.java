package com.StudyingPlatform.service;

import com.StudyingPlatform.model.*;
import com.StudyingPlatform.service.Exceptions.EmptyResultSetException;
import com.StudyingPlatform.service.Exceptions.ScheduleException;
import com.StudyingPlatform.service.Exceptions.SubjectNotFoundException;
import com.StudyingPlatform.service.Exceptions.UserNotFoundException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorService {
    public static Professor mapResultSet(ResultSet resultSet) throws SQLException, EmptyResultSetException {
        if (resultSet.next()) {
            Professor professor = new Professor(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("role"),
                    resultSet.getString("CNP"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    new Address(
                            resultSet.getString("country"),
                            resultSet.getString("town"),
                            resultSet.getString("region"),
                            resultSet.getString("street_address"),
                            resultSet.getString("postal_code")
                    ),
                    resultSet.getString("phone"),
                    resultSet.getString("email"),
                    resultSet.getString("iban"),
                    resultSet.getString("contract_number"),
                    resultSet.getBoolean("is_admin"),
                    resultSet.getBoolean("is_super_admin"),
                    resultSet.getInt("min_teaching_hours"),
                    resultSet.getInt("max_teaching_hours"),
                    resultSet.getString("department")
            );
            resultSet.previous();
            return professor;
        } else {
            resultSet.previous();
            throw new EmptyResultSetException();
        }
    }

    public static void assignProfessorToSubject(Professor professor, Subject subject) throws SQLException {
        Connection connection = DataBaseService.getConnection();
        CallableStatement stmt = connection.prepareCall("call professor_assign_subject(?,?)");
        stmt.setInt(1, professor.getId());
        stmt.setInt(2, subject.getId());
        stmt.execute();
    }

    public static List<SubjectProfessor> professorGetSubjects(Professor professor) throws SQLException, SubjectNotFoundException {
        Connection connection = DataBaseService.getConnection();
        CallableStatement stmt = connection.prepareCall("call  professor_get_subjects(?)", ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, professor.getId());
        ResultSet resultSet = stmt.executeQuery();
        try {
            List<SubjectProfessor> mySubjects = SubjectProfessorService.mapFullResultSet(resultSet);
            for (SubjectProfessor subjectProfessor : mySubjects) {
                subjectProfessor.setProfessor(professor);
            }
            return mySubjects;
        } catch (EmptyResultSetException e) {
            throw new SubjectNotFoundException();
        }
    }

    public static List<ScheduleEntry> professorGetSchedule(Professor professor) throws ScheduleException {
        List<ScheduleEntry> schedule = new ArrayList<>();
        List<SubjectProfessor> subjects;
        try {
            subjects = ProfessorService.professorGetSubjects(professor);
        } catch (SubjectNotFoundException e) {
            subjects = new ArrayList<>();
        } catch (SQLException e) {
            throw new ScheduleException(e.getMessage());
        }

        for (SubjectProfessor subject : subjects) {
            if (!subject.isFinishedSchedule()) continue;
            if (subject.getHasLecture()) {
                schedule.add(
                        new ScheduleEntry(subject.getScheduleLecture(), "LECTURE", subject.getName())
                );
            }
            if (subject.getHasSeminar()) {
                schedule.add(
                        new ScheduleEntry(subject.getScheduleSeminar(), "SEMINAR", subject.getName())
                );
            }
            if (subject.getHasLab()) {
                schedule.add(
                        new ScheduleEntry(subject.getScheduleLab(), "LAB", subject.getName())
                );
            }
        }
        return schedule;
    }

    public static List<Student> professorGetStudentsBySubject(Professor professor, Subject subject) throws SQLException {
        Connection connection = DataBaseService.getConnection();
        CallableStatement stmt = connection.prepareCall("call  professor_get_students_id_by_subject (?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, professor.getId());
        stmt.setInt(2, subject.getId());
        ResultSet resultSet = stmt.executeQuery();
        List<Integer> ids = new ArrayList<>();
        while (resultSet.next()) {
            ids.add(resultSet.getInt("student_id"));
        }
        List<User> userList;
        try {
            userList = DataBaseService.usersByIdList(ids);
        } catch (UserNotFoundException e) {
            return new ArrayList<>();
        }
        List<Student> students = new ArrayList<Student>();
        for (User user : userList) {
            students.add((Student) user);
        }
        return students;
    }

    public static void gradeStudent(Student student, Subject subject, int[] grades) throws SQLException {
        CallableStatement stmt = DataBaseService.getConnection().prepareCall("call set_grades(?,?,?,?,?)",ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1,student.getId());
        stmt.setInt(2,subject.getId());
        stmt.setInt(3,grades[0]);
        stmt.setInt(4,grades[1]);
        stmt.setInt(5,grades[2]);
        stmt.executeUpdate();
    }
}
