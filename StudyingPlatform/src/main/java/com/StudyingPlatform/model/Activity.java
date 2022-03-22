package com.StudyingPlatform.model;

import com.StudyingPlatform.service.DataBaseService;
import com.StudyingPlatform.service.Exceptions.GradesNotFoundException;
import com.StudyingPlatform.service.Exceptions.GroupNotFoundException;
import com.StudyingPlatform.service.GroupService;

import java.sql.*;

public class Activity {
    private int id;
    private int groupId;
    private Group group;
    private String name;
    private Date date;
    private int startHour;
    private int duration;
    private int minParticipants;
    private Timestamp expireTime;

    public Activity(){

    }

    public Activity(int id, int groupId, String name, Date date, int startHour,int duration, int minParticipants, Timestamp expireTime) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.date = date;
        this.startHour = startHour;
        this.duration = duration;
        this.minParticipants = minParticipants;
        this.expireTime = expireTime;
    }

    public Activity(int id, Group group, String name, Date date, int startHour, int duration, int minParticipants, Timestamp expireTime) {
        this.id = id;
        this.group = group;
        this.groupId = group.getId();
        this.name = name;
        this.date = date;
        this.startHour = startHour;
        this.duration = duration;
        this.minParticipants = minParticipants;
        this.expireTime = expireTime;
    }

    public int countParticipants(){
        return 0;
    }

    public void insertInDataBase() throws SQLException{
        Connection connection = DataBaseService.getConnection();
        CallableStatement stmt = connection.prepareCall("call add_activity(?,?,?,?,?,?,?)");
        stmt.setInt(1,this.groupId);
        stmt.setString(2,this.name);
        stmt.setDate(3,this.date);
        stmt.setInt(4,this.startHour);
        stmt.setInt(5,this.duration);
        stmt.setInt(6,this.minParticipants);
        stmt.setTimestamp(7,this.expireTime);
        stmt.execute();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
        this.group = null;
    }

    public Group getGroup() {
        if(group == null){
            try {
                DataBaseService.getGroupById(groupId);
            }catch (SQLException | GroupNotFoundException e){
                e.printStackTrace();
                return null;
            }
        }
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
        this.groupId = group.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getMinParticipants() {
        return minParticipants;
    }

    public void setMinParticipants(int minParticipants) {
        this.minParticipants = minParticipants;
    }

    public Timestamp getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
