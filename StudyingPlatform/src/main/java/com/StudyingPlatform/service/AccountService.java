package com.StudyingPlatform.service;

import com.StudyingPlatform.model.User;
import com.StudyingPlatform.service.Exceptions.SignupException;
import com.StudyingPlatform.service.Exceptions.UserNotFoundException;

import javax.security.auth.login.LoginException;
import java.sql.*;

public class AccountService {
    public static User logIn(String username,String password) throws LoginException{
        Connection connection = DataBaseService.getConnection();
        try {
            CallableStatement stmt = connection.prepareCall("call try_log_in(?,?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()){
                String logInStatus = resultSet.getString("log_in_status");
                int userId = resultSet.getInt("user_id");
                return switch (logInStatus) {
                    case "successful" -> DataBaseService.getUserById(userId);
                    case "username not found" -> throw new LoginException("username not found");
                    case "wrong password" -> throw new LoginException("wrong password");
                    default -> throw new LoginException("failed");
                };
            }
        }catch(SQLException | UserNotFoundException e){
            throw new LoginException("failed");
        }
        return null;
    }

    public static void signUp(User user) throws SignupException {
        try {
            DataBaseService.insertUser(user);
        }catch(SQLIntegrityConstraintViolationException e){
            throw new SignupException("unique constraint violated");
        }catch(SQLException e){
            e.printStackTrace();
            throw new SignupException("failed");
        }
    }
}
