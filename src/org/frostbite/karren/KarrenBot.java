package org.frostbite.karren;

import org.frostbite.karren.listencast.ListenCast;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by frostbite on 17/03/14.
 */
public class KarrenBot extends PircBotX {
    //private MySQLConnector sql;
    private ListenCast lc;
    private BotConfiguration botConf;
    private MySQLInterface sql;
    private ArrayList<Interactions> interactions;
    private int songID;
    public KarrenBot(Configuration<PircBotX> config, BotConfiguration botConf){
        super(config);
        this.botConf = botConf;
        lc = new ListenCast(this, botConf);
        sql = new MySQLInterface(botConf);
        interactions = loadInteractions();
    }
    private ArrayList<Interactions> loadInteractions(){
        String buffer;
        String[] temp1;
        String ident;
        String[] tags;
        String response;
        String[] activators;
        ArrayList<Interactions> interactions = new ArrayList<>();
        System.out.println("Initializing interactions!");
        try {
            BufferedReader in = new BufferedReader(new FileReader("conf/Interactions.txt"));
            buffer = in.readLine();
            while(buffer!=null){
                temp1 = buffer.split(":");
                if(temp1[0].equalsIgnoreCase("Interactions")){
                    ident = temp1[1];
                    response = temp1[3];
                    activators = temp1[2].split("[,]\\s*");
                    tags = temp1[4].split("[,]\\s*");
                    interactions.add(new Interactions(ident, tags, response, activators));
                }
                buffer = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return interactions;
    }
    public void startBot() throws IOException, IrcException {
        lc.start();
        super.startBot();
    }
    public void reloadInteractions(){
        interactions.clear();
        interactions = loadInteractions();
    }
    public ArrayList<Interactions> getInteractions(){return interactions;}
    public MySQLInterface getSql(){return sql;}
    public BotConfiguration getBotConf(){return botConf;}
    public void killListencast(){
        lc.kill();
    }
    public ListenCast getListenCast(){return lc;}
    public void setSongID(int id){
        songID = id;
    }
    public int getSongID(){return songID;}
}