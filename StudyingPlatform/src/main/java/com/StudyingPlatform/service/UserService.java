package com.StudyingPlatform.service;

import com.StudyingPlatform.model.User;
import com.StudyingPlatform.service.Exceptions.EmptyResultSetException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    public static User mapResultSet(ResultSet resultSet) throws SQLException, EmptyResultSetException {
        if (resultSet.next()) {
            String role = resultSet.getString("role");
            resultSet.previous();
            if("STUDENT".equals(role)){
                return StudentService.mapResultSet(resultSet);
            }else if("PROFESSOR".equals(role)){
                return ProfessorService.mapResultSet(resultSet);
            }else throw new IllegalStateException("Unexpected value: " + role + " for user's role");
        }else{
            throw new EmptyResultSetException();
        }
    }
}
