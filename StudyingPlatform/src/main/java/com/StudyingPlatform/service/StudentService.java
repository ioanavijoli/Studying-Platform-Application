package com.StudyingPlatform.service;

import com.StudyingPlatform.model.*;
import com.StudyingPlatform.service.Exceptions.EmptyResultSetException;
import com.StudyingPlatform.service.Exceptions.GradesNotFoundException;
import com.StudyingPlatform.service.Exceptions.ScheduleException;
import com.StudyingPlatform.service.Exceptions.SubjectNotFoundException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    public static Student mapResultSet(ResultSet resultSet) throws SQLException, EmptyResultSetException {
        if (resultSet.next()) {
            Student student = new Student(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("role"),
                    resultSet.getString("CNP"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    new Address(
                            resultSet.getString("country"),
                            resultSet.getString("region"),
                            resultSet.getString("town"),
                            resultSet.getString("street_address"),
                            resultSet.getString("postal_code")
                    ),
                    resultSet.getString("phone"),
                    resultSet.getString("email"),
                    resultSet.getString("iban"),
                    resultSet.getString("contract_number"),
                    resultSet.getBoolean("is_admin"),
                    resultSet.getBoolean("is_super_admin"),
                    resultSet.getInt("year"),
                    resultSet.getInt("min_studying_hours")
            );
            resultSet.previous();
            return student;
        } else {
            resultSet.previous();
            throw new EmptyResultSetException();
        }
    }

    public static List<SubjectStudent> studentGetSubjects(Student student) throws SQLException, SubjectNotFoundException {
        Connection connection = DataBaseService.getConnection();
        CallableStatement stmt = connection.prepareCall("call  student_get_subjects(?)", ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, student.getId());
        ResultSet resultSet = stmt.executeQuery();
        try {
            List<SubjectStudent> mySubjects = SubjectStudentService.mapFullResultSet(resultSet);
            for (SubjectStudent subjectStudent : mySubjects) {
                subjectStudent.setStudent(student);
            }
            return mySubjects;
        } catch (EmptyResultSetException e) {
            throw new SubjectNotFoundException();
        }
    }

    public static List<Subject> studentGetJoinableSubjects(Student student) throws SQLException {
        Connection connection = DataBaseService.getConnection();
        CallableStatement stmt = connection.prepareCall("call  joinable_subjects(?)", ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, student.getId());
        ResultSet resultSet = stmt.executeQuery();
        try {
            List<Subject> mySubjects = SubjectService.mapFullResultSet(resultSet);
            return mySubjects;
        } catch (EmptyResultSetException e) {
            return new ArrayList<Subject>();
        }
    }

    public static List<ScheduleEntry> studentGetSchedule(Student student) throws ScheduleException {
        List<ScheduleEntry> schedule = new ArrayList<>();
        List<SubjectStudent> subjects;
        try {
            subjects = StudentService.studentGetSubjects(student);
        } catch (SubjectNotFoundException e) {
            subjects = new ArrayList<>();
        } catch (SQLException e) {
            throw new ScheduleException(e.getMessage());
        }

        for (SubjectStudent subject : subjects) {
            if (!subject.isFinishedSchedule()) continue;
            if (subject.getHasLecture() && subject.isEnrolledLecture()) {
                schedule.add(
                        new ScheduleEntry(subject.getScheduleLecture(), "LECTURE", subject.getName())
                );
            }
            if (subject.getHasSeminar() && subject.isEnrolledSeminar()) {
                schedule.add(
                        new ScheduleEntry(subject.getScheduleSeminar(), "SEMINAR", subject.getName())
                );
            }
            if (subject.getHasLab() && subject.isEnrolledLab()) {
                schedule.add(
                        new ScheduleEntry(subject.getScheduleLab(), "LAB", subject.getName())
                );
            }
        }
        return schedule;
    }

    public static int[] studentGetGrades(Student student, Subject subject) throws GradesNotFoundException, SQLException {
        Connection connection = DataBaseService.getConnection();
        CallableStatement stmt = connection.prepareCall("call  get_grades(?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, student.getId());
        stmt.setInt(2, subject.getId());
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            int[] myGrades = new int[3];
            myGrades[0] = resultSet.getInt("grade_lecture");
            myGrades[1] = resultSet.getInt("grade_seminar");
            myGrades[2] = resultSet.getInt("grade_lab");
            return myGrades;
        } else
            throw new GradesNotFoundException();
    }

    public static String enrollActivity(SubjectStudent subjectStudent,String activity) throws SQLException {
        Connection connection = DataBaseService.getConnection();
        CallableStatement stmt;
        switch (activity){
            case "LECTURE":
                stmt = connection.prepareCall("call enroll_in_lecture(?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                break;
            case "SEMINAR":
                stmt = connection.prepareCall("call enroll_in_seminar(?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                break;
            case "LAB":
                stmt = connection.prepareCall("call enroll_in_lab(?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                break;
            default:
                throw new IllegalStateException("Unexpected activity for enroll");
        }
        stmt.setInt(1, subjectStudent.getStudent().getId());
        stmt.setInt(2, subjectStudent.getProfessor().getId());
        stmt.setInt(3, subjectStudent.getId());
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
           return resultSet.getString("enroll_status");
        } else {
            throw new SQLException("got no status");
        }
    }
    public static void studentJoinSubject(Student student,Subject subject) throws SQLException{
        Connection connection = DataBaseService.getConnection();
        CallableStatement stmt = connection.prepareCall("call student_join_subject(?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, student.getId());
        stmt.setInt(2, subject.getId());
        stmt.executeUpdate();
    }
    public static void studentQuitSubject(Student student, Subject subject) throws SQLException{
        Connection connection = DataBaseService.getConnection();
        CallableStatement stmt = connection.prepareCall("call student_quit_subject(?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, student.getId());
        stmt.setInt(2, subject.getId());
        stmt.executeUpdate();
    }
}
