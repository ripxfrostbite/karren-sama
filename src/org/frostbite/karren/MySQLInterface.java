package org.frostbite.karren;

import org.frostbite.karren.listencast.Song;
import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class MySQLInterface {
    private String sqlhost;
    private String sqluser;
    private String sqldb;
    private String sqlpass;
    private int sqlport;
    private String query;
    private boolean search;
    private boolean pstNeeded;
    private String overrideDB;
    private Logger log;
    private boolean rwEnabled;
    private Date date = new Date();
    private ArrayList<String> sqlPayload = new ArrayList<>();
    /*
    CONSTRUCTORS
     */
    public MySQLInterface(BotConfiguration botConf, Logger log){
        sqldb = botConf.getSqldb();
        sqlhost = botConf.getSqlhost();
        sqlpass = botConf.getSqlpass();
        sqlport = Integer.parseInt(botConf.getSqlport());
        sqluser = botConf.getSqluser();
        rwEnabled = Boolean.parseBoolean(botConf.getAllowSQLRW());
        this.log = log;
    }
    /*
    UTILITY OPERATIONS
     */
    private void resetSQL(){
        sqlPayload.clear();
        query = null;
        search = false;
        pstNeeded = false;
        overrideDB = null;
    }
    public boolean isNewUser(String nick){
        ArrayList<String> savedUsers = new ArrayList<>();
        boolean userNew = true;
        try{
            query = "SELECT user FROM users";
            search = true;
            pstNeeded = false;
            ArrayList<Object> usrTemp = executeQuery();
            for (Object user : usrTemp) {
                savedUsers.add((String) user);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            log.error("Error in SQL Operation:", e);
        }
        if(savedUsers.size() > 0){
            for(String curUser : savedUsers){
                if(nick.equalsIgnoreCase(curUser)){
                    userNew = false;
                }
            }
        }
        return userNew;
    }
    public void makeUser(String nick){
        resetSQL();
        query = "INSERT INTO users (ircuserid, user, botpart, timepart, linkCode) VALUES (null, ?, false, 0, null)";
        sqlPayload.add(nick);
        try {
            search = false;
            pstNeeded = true;
            executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
    USER OPERATIONS
     */
    public ArrayList<Object> getUserData(String nick) throws SQLException {
        ArrayList<Object> result;
        if(isNewUser(nick))
            makeUser(nick);
        resetSQL();
        query = "SELECT * FROM users WHERE user= ?";
        sqlPayload.add(nick);
        search = true;
        pstNeeded = true;
        result = executeQuery();
        return result;
    }
    /*
    Expected arguments:
        1: Nick of user
     */
    public void userOperation(String mod, String[] args) throws SQLException {
        Date date = new Date();
        ArrayList<Object> userData = getUserData(args[0]);
        resetSQL();
        switch(mod.toLowerCase()){
            case "return":
                if((Boolean)userData.get(1)){
                    query = "UPDATE users SET botpart=false WHERE user= ?";
                    sqlPayload.add(args[0]);
                    search = false;
                    pstNeeded = true;
                    executeQuery();
                }
                break;
            case "part":
                if(!((Boolean)userData.get(1))){
                    query = "UPDATE users SET botpart=true, timepart= ? WHERE user= ?";
                    sqlPayload.add(String.valueOf(date.getTime()));
                    sqlPayload.add(args[0]);
                    search = false;
                    pstNeeded = true;
                    executeQuery();
                }
                break;
        }
    }
    public void userLink(String uid, String linkCode) throws SQLException {
        resetSQL();
        query = "UPDATE Users SET linkCode=? WHERE ID=?";
        sqlPayload.add(linkCode);
        sqlPayload.add(uid);
        search = false;
        pstNeeded = true;
        executeQuery();
    }
    /*
    RADIO OPERATIONS
     */
    public ArrayList<Object> getUserFaves(Song song) throws SQLException {
        ArrayList<Object> result;
        resetSQL();
        query = "SELECT User FROM userfaves WHERE SongID=?";
        sqlPayload.add(String.valueOf(song.getSongID()));
        search = true;
        pstNeeded = true;
        result = executeQuery();
        return result;
    }
    public boolean addFave(String user, Song song) throws SQLException {
        resetSQL();
        query = "SELECT * FROM userfaves WHERE User=? AND SongID=?";
        sqlPayload.add(user);
        sqlPayload.add(String.valueOf(song.getSongID()));
        search = true;
        pstNeeded = true;
        ArrayList<Object> returned = executeQuery();
        if(returned.size()==0){
            resetSQL();
            query = "INSERT INTO UserFaves(ID, User, SongID) VALUES (null, ?, ?)";
            sqlPayload.add(user);
            sqlPayload.add(String.valueOf(song.getSongID()));
            search = false;
            pstNeeded = true;
            executeQuery();
            resetSQL();
            query = "UPDATE songdb SET FavCount=FavCount+1 WHERE id=?";
            sqlPayload.add(String.valueOf(song.getSongID()));
            search = false;
            pstNeeded = true;
            executeQuery();
            return true;
        } else {
            return false;
        }
    }
    public ArrayList<Song> getOldSongDataFromDB() throws SQLException {
        ArrayList<Song> songs = new ArrayList<>();
        resetSQL();
        query = "SELECT * FROM songdb_old";
        search = true;
        pstNeeded = false;
        ArrayList<Object> result = executeQuery();
        int songCount = result.size()/7;
        for(int i=0; i<songCount; i++){
            songs.add(new Song((String)result.get(1+(7*(i))),(int)result.get(3+(7*(i))),(int)result.get(4+(7*(i))),(boolean)result.get(6+(7*(i))),(String)result.get(2+(7*(i))), (long)result.get(5+(7*(i)))));
        }
        return songs;
    }
    public void insertSongData(Song song) throws SQLException {
        resetSQL();
        int durLock = 0;
        if(song.isDurationLocked())
            durLock = 1;
        query = "INSERT INTO songdb (ID, SongTitle, LPTime, PlayCount, FavCount, SongDuration, DurationLock) VALUES (null, ?, ?, ?, ?, ?, ?)";
        sqlPayload.add(song.getSongName());
        sqlPayload.add(String.valueOf(song.getLastPlayedRaw()));
        sqlPayload.add(String.valueOf(song.getPlayCount()));
        sqlPayload.add(String.valueOf(song.getFavCount()));
        sqlPayload.add(String.valueOf(song.getLastSongDuration()));
        sqlPayload.add(String.valueOf(durLock));
        pstNeeded = true;
        search = false;
        executeQuery();
    }
    public void updateDJActivity(String curDJ, String streamName) throws SQLException {
        if(curDJ.length()==0){
            //Setting no DJ to active(Stream offair)
            resetSQL();
            query = "UPDATE radio_dj SET active=false";
            search = false;
            pstNeeded = false;
            executeQuery();
        } else {
            resetSQL();
            query = "INSERT INTO radio_dj(ID,displayName,connectName,streamName,djPicture,active) VALUES (null, ?, ?, ?, 'default', true) ON DUPLICATE KEY UPDATE active=true, streamName=?";
            sqlPayload.add(curDJ);
            sqlPayload.add(curDJ);
            sqlPayload.add(streamName);
            sqlPayload.add(streamName);
            search = false;
            pstNeeded = true;
            executeQuery();
        }
    }
    public void updateSongData(Song lastsong) throws SQLException{
        if(!lastsong.isDurationLocked() || lastsong.getLastSongDuration() == 0) {
            resetSQL();
            if (lastsong.getLastSongDuration() == lastsong.getSongDuration() && lastsong.getLastSongDuration() > 0) {
                query = "UPDATE songdb SET songduration=?, DurationLock=1 WHERE id=?";
                log.debug("Setting \"" + lastsong.getSongName() + "\" duration lock to true");
            } else {
                query = "UPDATE songdb SET songduration=? WHERE id=?";
            }
            sqlPayload.add(String.valueOf(lastsong.getSongDuration()));
            sqlPayload.add(String.valueOf(lastsong.getSongID()));
            search = false;
            pstNeeded = true;
            executeQuery();
        }
    }
    public void updateRadioDatabase(Song song) throws SQLException {
        if(rwEnabled) {
            resetSQL();
            ArrayList<Object> returned;
            Long curTime;
            query = "SELECT ID FROM songdb WHERE SongTitle = ?";
            pstNeeded = true;
            search = true;
            sqlPayload.add(song.getSongName());
            returned = executeQuery();
            resetSQL();
            if (returned.size() > 0) {
                song.setSongID((int) returned.get(0));
                returned.clear();
                query = "SELECT * FROM songdb WHERE ID= ?";
                sqlPayload.add(String.valueOf(song.getSongID()));
                search = true;
                pstNeeded = true;
                returned = executeQuery();
                song.setFieldsFromSQL(returned);
            } else {
                returned.clear();
                song.setSongID(0);
                returned.add(null);
                returned.add(null);
                returned.add((long) 0);
                returned.add(0);
                returned.add(0);
                returned.add(0);
                returned.add(false);
                song.setFieldsFromSQL(returned);
            }
            returned.clear();
            if (song.getSongID() == 0) {
                //Adding song to DB and getting new ID for song
                resetSQL();
                query = "INSERT INTO songdb (ID, SongTitle, LPTime, PlayCount, FavCount, SongDuration, DurationLock) VALUES (null, ?, ?, 1, 0, 0, false)";
                sqlPayload.add(song.getSongName());
                curTime = date.getTime();
                sqlPayload.add(curTime.toString());
                search = false;
                pstNeeded = true;
                executeQuery();
                resetSQL();
                query = "SELECT ID FROM songdb WHERE SongTitle = ?";
                sqlPayload.add(song.getSongName());
                search = true;
                pstNeeded = true;
                returned = executeQuery();
                if (returned.size() > 0) {
                    song.setSongID((int) returned.get(0));
                }
                resetSQL();
            } else {
                resetSQL();
                //Update info for song
                query = "UPDATE songdb SET LPTime= ?, PlayCount=PlayCount+1 WHERE ID=?";
                curTime = date.getTime();
                sqlPayload.add(curTime.toString());
                sqlPayload.add(String.valueOf(song.getSongID()));
                search = false;
                pstNeeded = true;
                executeQuery();
            }
            log.info("Now playing: " + song.getSongName() + ":" + song.getSongID() + ":" + song.getPlayCount());
        }
    }
    /*
    SQL OPERATIONS
     */
    public ArrayList<Object> executeQuery() throws SQLException {
        String targetDB = sqldb;
        ArrayList<Object> result = new ArrayList<>();
        if(overrideDB != null)
            targetDB = overrideDB;
        Connection run = DriverManager.getConnection("jdbc:mysql://" + sqlhost + ":" + sqlport + "/" + targetDB + "?useUnicode=true&characterEncoding=UTF-8", sqluser, sqlpass);
        PreparedStatement pst;
        ResultSet rs;
        pst = run.prepareStatement(query);
        if(pstNeeded){
            for(int i=0; i<sqlPayload.size(); i++){
                pst.setString(i+1, sqlPayload.get(i));
            }
        }
        if(search){
            rs = pst.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int cCount = md.getColumnCount();
            while(rs.next()){
                for(int i=1; i<=cCount; i++){
                    result.add(rs.getObject(i));
                }
            }
        }
        if(!search)
            pst.execute();
        run.close();
        return result;
    }
}
