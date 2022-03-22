package com.StudyingPlatform.service;

import com.StudyingPlatform.model.Address;
import com.StudyingPlatform.model.Student;
import com.StudyingPlatform.model.Subject;
import com.StudyingPlatform.service.Exceptions.EmptyResultSetException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectService {
    public static Subject mapResultSet(ResultSet resultSet) throws SQLException, EmptyResultSetException {
        if (resultSet.next()){
            Subject subject = new Subject(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getBoolean("has_lecture"),
                    resultSet.getBoolean("has_seminar"),
                    resultSet.getBoolean("has_lab"),
                    resultSet.getDate("date_start"),
                    resultSet.getDate("date_end")
            );
            resultSet.previous();
            return subject;
        }else{
            resultSet.previous();
            throw new EmptyResultSetException();
        }
    }

    public static List<Subject> mapFullResultSet(ResultSet resultSet) throws SQLException, EmptyResultSetException {
        List<Subject> subjectList = new ArrayList<>();
        while(resultSet.next()){
            Subject subject = new Subject(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getBoolean("has_lecture"),
                    resultSet.getBoolean("has_seminar"),
                    resultSet.getBoolean("has_lab"),
                    resultSet.getDate("date_start"),
                    resultSet.getDate("date_end")
            );
            subjectList.add(subject);
        }
        if(subjectList.isEmpty()){
            throw new EmptyResultSetException();
        }
        return subjectList;
    }
}
