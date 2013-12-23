package org.frostbite.karren;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import org.frostbite.karren.Logging;
import org.pircbotx.hooks.events.MessageEvent;

public class MySQLConnector {
	public static ArrayList<String> sqlPush(String type, String mod, String[] data) throws IOException, SQLException{
		ArrayList<String> result = new ArrayList<String>();
		boolean validType = false;
		switch(type){
			case "news":
				pushNews(mod, data);
				break;
			case "site":
				pushSite(mod, data);
				break;
			case "radio":
				result.add(pushRadio(mod, data));
				break;
			case "stats":
				pushStats(mod);
				break;
			case "part":
				result.add(pushPart(mod, data));
				break;
			case "hash":
				result.add(pushHash(mod, data));
				break;
			case "song":
				result.addAll(pushSong(mod, data));
				break;
			default:
				result.add(null);
				break;
		}
		return result;
	}
	public static ArrayList<String> pushSong(String mod ,String[] data) throws SQLException{
		ArrayList<String> result = new ArrayList<String>();
		String statmentBuild = "";
		ResultSet returned;
		ArrayList<String> dataForSQL = new ArrayList<String>();
		int songID = 0;
		statmentBuild = "SELECT ID FROM SongDB WHERE SongTitle = ?";
		dataForSQL.add(data[0]);
		returned = runCommand(statmentBuild, dataForSQL, true, true, "sitebackend");
		dataForSQL.clear();
		if(returned.next()){
			songID = returned.getInt(1);
		}
		if(songID == 0){
			//Adding song to DB
			statmentBuild = "INSERT INTO SongDB(ID, SongTitle, LPTime, PlayCount, FavCount) VALUES (null, ?, ?, 1, 0)";
			dataForSQL.add(data[0]);
			dataForSQL.add(getCurTime());
		} else {
			//Update info for song
			statmentBuild = "UPDATE SongDB SET LPTime= ?, PlayCount=PlayCount+1";
			dataForSQL.add(getCurTime());
		}
		//if()
		return result;
	}
	public static void pushNews(String mod, String[] data) throws IOException{
		ArrayList<String> dataForSQL = new ArrayList<String>();
		String sqldb = "sitebackend";
		String curdate = getSqlDate();
		Logging.log(curdate, true);
		String query = "INSERT INTO news (author, post, id, header, date) VALUES ( ? , ? , null, 1, ?)";
		//adds author info
		dataForSQL.add(data[0]);
		//Adds post
		dataForSQL.add(data[1]);
		//Adds timestamp
		dataForSQL.add(curdate);
		try{
			runCommand(query, dataForSQL, false, true, sqldb);
		} catch(SQLException e) {
			Logging.log(e.toString(), true);
		}
	}
	public static boolean pushSite(String mod, String[] data) throws IOException{
		boolean result = false;
		String statmentBuild = "";
		return false;
	}
	public static String pushHash(String mod, String[] data) throws SQLException{
		String statmentBuild = "";
		ArrayList<String> dataForSQL = new ArrayList<String>();
		ResultSet result;
		long hashTemp = 1;
		String resultHash = "";
		String hashStringComp = "";
		boolean hasHash = false;
		ArrayList<String> djList = new ArrayList<String>();
		statmentBuild = "SELECT * FROM `Radio-DJ`";
		result = runCommand(statmentBuild, dataForSQL, true, false, "sitebackend");
		while(result.next()){
			djList.add(result.getString("DJName"));
		}
		result.close();
		hasHash = djList.contains(data[0]);
		if(!hasHash){
			//Generating new DJHash code
			for(int i=0; i<data[0].length(); i++){
				hashTemp = hashTemp * data[0].charAt(i);
			}
			hashTemp = hashTemp*GlobalVars.djHashGenKey;
			char[] hashArray = String.valueOf(hashTemp).toCharArray();
			if(hashArray.length>8){
				for(int c=0; c<7; c++){
					hashStringComp = hashStringComp + Character.toString(hashArray[c]);
				}
				resultHash = hashStringComp;
			} else {
				resultHash = String.valueOf(hashTemp);
			}
			statmentBuild = "INSERT INTO `Radio-DJ`(DJName, DJHash) VALUES (?, ?)";
			dataForSQL.add(data[0]);
			dataForSQL.add(String.valueOf(resultHash));
			runCommand(statmentBuild, dataForSQL, false, true, "sitebackend");
			dataForSQL.clear();
		
		} else {
			statmentBuild = "SELECT DJHash FROM `Radio-DJ` WHERE DJName= ?";
			dataForSQL.add(data[0]);
			result = runCommand(statmentBuild, dataForSQL, true, true, "sitebackend");
			if(result.next()){
				resultHash = String.valueOf(result.getInt(1));
			}
		}
		return resultHash;
	}
	public static String pushRadio(String mod, String[] data) throws IOException, SQLException{
		String result = "";
		String statmentBuild = "";
		ArrayList<String> dataForSQL = new ArrayList<String>();
		//Updating now playing song
		if(mod.equalsIgnoreCase("GetSong")){
			statmentBuild = "SELECT NowPlaying FROM radio";
			ResultSet returned;
			try {
				returned = runCommand(statmentBuild, dataForSQL, true, false, "sitebackend");
				if(returned.next()){
					result = returned.getString(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(mod.equalsIgnoreCase("Song")){
			//Moving the last played down and adding new song
			for(int i=2; i<=GlobalVars.icecastLPNum; i++){
				statmentBuild = "UPDATE lastplayed dt1, lastplayed dt2 SET dt1.SongTitle = dt2.SongTitle WHERE dt1.Spot = " + (i-1) + " AND dt2.Spot = " + i;
				try {
					runCommand(statmentBuild, dataForSQL, false, false, "sitebackend");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			statmentBuild = "UPDATE radio dt1, lastplayed dt2 SET dt2.SongTitle = dt1.NowPlaying WHERE dt2.Spot = '1'";
			try {
				runCommand(statmentBuild, dataForSQL, false, false, "sitebackend");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(mod.equalsIgnoreCase("listen")){
			statmentBuild = "UPDATE radio SET Listeners = ?";
			dataForSQL.add(data[0]);
			try {
				runCommand(statmentBuild, dataForSQL, false, true, "sitebackend");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(mod.equalsIgnoreCase("dj")){
			statmentBuild = "UPDATE radio SET CurrentDJ = ?";
			dataForSQL.add(data[0]);
			try {
				runCommand(statmentBuild, dataForSQL, false, true, "sitebackend");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(mod.equalsIgnoreCase("title")){
			statmentBuild = "UPDATE radio SET StreamName = ?";
			dataForSQL.add(data[0]);
			try {
				runCommand(statmentBuild, dataForSQL, false, true, "sitebackend");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	public static boolean pushStats(String mod) throws SQLException{
		boolean doesExist = false;
		boolean result = false;
		ArrayList<String> dataForSQL = new ArrayList<String>();
		String stamentBuild = "UPDATE ";
		
		return result;
	}
	/*
	 * pushPart is used to access the tables containing the data of all user afk times
	 * 
	 * Used to track and tell users how long they have been gone.
	 */
	public static String pushPart(String mod, String[] data) throws IOException{
		boolean userExists = false;
		String result = null;
		boolean isParted = false;
		ArrayList<String> dataForSQL = new ArrayList<String>();
		Date date = new Date();
		String query;
		PreparedStatement pst;
		ArrayList<String> savedUsers = new ArrayList<String>();
		try{
			query = "SELECT user FROM users";
			ResultSet userGet = runCommand(query, dataForSQL, true, false, "");
			while(userGet.next()){
				savedUsers.add(userGet.getString("user"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			Logging.log(e.getMessage(), true);
		}
		if(savedUsers.size() > 0){
			for(String curUser : savedUsers){
				if(data[0].equalsIgnoreCase(curUser)){
					userExists = true;
				}
			}
		}
		if(mod.equalsIgnoreCase("back")){
			//Sets botpart to false and sends a message to the server stating how long user has been away
			try{
				query = "SELECT botpart FROM users WHERE user= ?";
				dataForSQL.clear();
				dataForSQL.add(data[0]);
				ResultSet returned1 = runCommand(query, dataForSQL, true, true, "");
				if(returned1.next()){
					isParted = returned1.getBoolean(1);
				} else {
					isParted = false;
				}
			} catch(SQLException e){
				e.printStackTrace();
				Logging.log(e.getMessage(), true);
			}
			if(isParted){
				try{
					query = "UPDATE users SET botpart=false WHERE user= ?";
					dataForSQL.clear();
					dataForSQL.add(data[0]);
					runCommand(query, dataForSQL, false, true, "");
				} catch(SQLException e){
					e.printStackTrace();
					Logging.log(e.getMessage(), true);
				}
				try{
					query = "SELECT timepart FROM users WHERE user= ?";
					dataForSQL.clear();
					dataForSQL.add(data[0]);
					ResultSet returned = runCommand(query, dataForSQL, true, true, "");
					if(returned.next()){
						result = String.valueOf(returned.getObject(1));
					} else {
						result = "0";
					}
				} catch(SQLException e){
					e.printStackTrace();
					Logging.log(e.getMessage(), true);
				}
			}
		} else {
			if(userExists){
				try{
					query = "UPDATE users SET botpart=true, timepart= ? WHERE user= ?";
					dataForSQL.add(String.valueOf(date.getTime()));
					dataForSQL.add(data[0]);
					runCommand(query, dataForSQL, false, true, "");
				} catch(SQLException e) {
					e.printStackTrace();
					Logging.log(e.getMessage(), true);
				}
			} else {
				try{
					query = "INSERT INTO users (user, botpart, timepart) VALUES ( ? , 1 , ? )";
					dataForSQL.add(data[0]);
					dataForSQL.add(String.valueOf(date.getTime()));
					runCommand(query, dataForSQL, false, true, "");
				} catch(SQLException e) {
					e.printStackTrace();
					Logging.log(e.toString(), true);
				}
			}
		}
		return result;
	}
	/*
	 * runCommand is used to compile and run the query generated by the previous methods
	 */
	public static ResultSet runCommand(String command, ArrayList<String> data, boolean search, boolean pstNeeded, String overrideDB) throws SQLException{
		String activeDb = GlobalVars.sqldb;
		if(overrideDB != "")
			activeDb = overrideDB;
		Connection run = DriverManager.getConnection("jdbc:mysql://" + GlobalVars.sqlhost + ":" + GlobalVars.sqlport + "/" + activeDb, GlobalVars.sqluser, GlobalVars.sqlpass);
		PreparedStatement pst;
		ResultSet rs = null;
		pst = run.prepareStatement(command);
		if(pstNeeded){
			for(int i=0; i<data.size(); i++){
				pst.setString(i+1, data.get(i));
			}
		}
		if(search)
			rs = pst.executeQuery();
		if(!search)
			pst.execute();
		return rs;
	}
	/*
	 * Returns a date in the format of YYYY-MM-DD to use with the SQL 
	 * 
	 */
	public static String getSqlDate(){
		String sqldate = "0000-00-00";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		sqldate = dateFormat.format(date);
		return sqldate;
	}
	public static String getCurTime(){
		String curTime = "00-00-0000 00:00:00";
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy @ HH:mm:ss");
		Date date = new Date();
		curTime = dateFormat.format(date);
		return curTime;
	}
}
