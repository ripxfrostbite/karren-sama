/*
 * Copyright (c) 2017 Owen Bennett.
 *  You may use, distribute and modify this code under the terms of the MIT licence.
 *  You should have obtained a copy of the MIT licence with this software,
 *  if not please obtain one from https://opensource.org/licences/MIT
 *
 *
 *
 */

package org.frostbite.karren.Database.Objects;

import org.knowm.yank.Yank;

import java.sql.Timestamp;

public class DbReminder {
    public int reminderID;
    public String authorID;
    public int targetID;
    public Timestamp reminderTime;
    public String message;
    public Long channelID;
    public boolean reminderSent;

    public DbReminder() {
    }

    public DbReminder(int reminderID, String authorID, int targetID, Timestamp reminderTime, String message, Long channelID, boolean reminderSent) {
        this.reminderID = reminderID;
        this.authorID = authorID;
        this.targetID = targetID;
        this.reminderTime = reminderTime;
        this.message = message;
        this.channelID = channelID;
        this.reminderSent = reminderSent;
    }

    public int getReminderID() {
        return reminderID;
    }

    public void setReminderID(int reminderID) {
        this.reminderID = reminderID;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public int getTargetID() {
        return targetID;
    }

    public void setTargetID(int targetID) {
        this.targetID = targetID;
    }

    public Timestamp getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(Timestamp reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isReminderSent() {
        return reminderSent;
    }

    public void setReminderSent(boolean reminderSent) {
        this.reminderSent = reminderSent;
    }

    public Long getChannelID() {
        return channelID;
    }

    public void setChannelID(Long channelID) {
        this.channelID = channelID;
    }

    public void update(){
        String sql = "UPDATE Reminder SET ReminderSent=? WHERE ReminderID=?";
        Yank.execute(sql, new Object[]{reminderSent, reminderID});
    }

}
