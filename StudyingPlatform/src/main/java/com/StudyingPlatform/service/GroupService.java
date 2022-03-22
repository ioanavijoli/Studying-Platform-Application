package com.StudyingPlatform.service;

import com.StudyingPlatform.model.Activity;
import com.StudyingPlatform.model.Group;
import com.StudyingPlatform.service.Exceptions.EmptyResultSetException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupService {
    public static List<Group> mapResultSet(ResultSet resultSet) throws SQLException, EmptyResultSetException {
        List<Group> groups = new ArrayList<>();
        while (resultSet.next()) {
            Group studentGroup = new Group(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("subject_id")
            );
            groups.add(studentGroup);
        }
        return groups;
    }

    public static List<Activity> groupGetActivities(Group group) throws SQLException {
        List<Activity> activities = new ArrayList<>();
        Connection connection = DataBaseService.getConnection();
        CallableStatement stmt = connection.prepareCall("call group_get_activities(?)");
        stmt.setInt(1, group.getId());
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            Activity activity = new Activity(
                    resultSet.getInt("id"),
                    resultSet.getInt("studying_group_id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date"),
                    resultSet.getInt("start_hour"),
                    resultSet.getInt("duration"),
                    resultSet.getInt("min_participants"),
                    resultSet.getTimestamp("expire_time")
                    );
            activities.add(activity);
        }
        return activities;
    }

}
