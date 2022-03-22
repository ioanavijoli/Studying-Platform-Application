package com.StudyingPlatform.service;

import com.StudyingPlatform.model.Group;
import com.StudyingPlatform.model.Message;
import com.StudyingPlatform.service.Exceptions.EmptyResultSetException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageService {
    public static List<Message> mapResultSet(ResultSet resultSet) throws SQLException, EmptyResultSetException {
        List<Message> messages = new ArrayList<>();
        boolean empty = true;
        while (resultSet.next()) {
            empty = false;
            Message message = new Message(
                    resultSet.getInt("id"),
                    resultSet.getString("text"),
                    resultSet.getInt("group_id"),
                    resultSet.getTimestamp("time_sent"),
                    resultSet.getInt("user_id")
            );
            messages.add(message);
        }
        if(empty)throw new EmptyResultSetException();
        return messages;
    }
}
