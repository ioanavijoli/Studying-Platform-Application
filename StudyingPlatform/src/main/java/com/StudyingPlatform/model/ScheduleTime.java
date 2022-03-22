package com.StudyingPlatform.model;

import com.StudyingPlatform.model.Interfaces.Overlapable;

import java.time.DayOfWeek;

public class ScheduleTime implements Comparable<ScheduleTime>, Overlapable<ScheduleTime> {
    DayOfWeek dayOfWeek;
    int hour;
    int duration;

    public ScheduleTime() {

    }

    public ScheduleTime(DayOfWeek dayOfWeek, int hour, int duration) {
        this.dayOfWeek = dayOfWeek;
        this.hour = hour;
        this.duration = duration;
    }

    public ScheduleTime(String day, int hour, int duration) {
        if (day != null) {
            this.dayOfWeek = DayOfWeek.valueOf(day);
        } else {
            this.dayOfWeek = DayOfWeek.MONDAY;
        }
        this.hour = hour;
        this.duration = duration;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public int compareTo(ScheduleTime o) {
        if(this.dayOfWeek.getValue() < o.dayOfWeek.getValue())
            return -1;
        if(this.dayOfWeek.getValue() > o.dayOfWeek.getValue())
            return 1;
        return Integer.compare(this.hour,o.hour);
    }

    @Override
    public boolean overlaps(ScheduleTime o) {
        ScheduleTime min,max;
        if(!this.dayOfWeek.equals(o.dayOfWeek)){
            return false;
        }
        if(this.compareTo(o) < 0){
            min = this;
            max = o;
        }else{
            min = o;
            max = this;
        }
        if(min.hour + min.duration <= max.hour)
            return false;
        return true;
    }
}
