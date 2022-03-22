package com.StudyingPlatform.model;

import com.StudyingPlatform.service.DataBaseService;
import com.StudyingPlatform.service.Exceptions.GroupNotFoundException;
import com.StudyingPlatform.service.Exceptions.UserNotFoundException;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class Message {
    private int id;
    private String text;
    private int groupId;
    private Group group;
    private Timestamp timeSent;
    private int userId;
    private User user;

    public Message() {

    }

    public Message(int id, String text, int groupId,Timestamp timeSent, int userId) {
        this.id = id;
        this.text = text;
        this.groupId = groupId;
        this.timeSent = timeSent;
        this.userId = userId;
    }

    public Message(int id, String text, Group group,Timestamp timeSent, User user) {
        this.id = id;
        this.text = text;
        this.groupId = group.getId();
        this.group = group;
        this.timeSent = timeSent;
        this.userId = user.getId();
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Group getGroup() {
        if (group == null) {
            try {
                group = DataBaseService.getGroupById(groupId);
            } catch (SQLException | GroupNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return group;
    }

    public void setGroup(Group group) {
        this.groupId = group.getId();
        this.group = group;
    }

    public Timestamp getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Timestamp timeSent) {
        this.timeSent = timeSent;
    }

    public User getUser() {
        if(user == null){
            try{
                user = DataBaseService.getUserById(userId);
            }catch (UserNotFoundException e){
                e.printStackTrace();
                return null;
            }
        }
        return user;
    }

    public void setUser(User user) {
        this.userId = user.getId();
        this.user = user;
    }
}
