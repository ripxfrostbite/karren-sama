/*
 * Copyright (c) 2016 Owen Bennett.
 *  You may use, distribute and modify this code under the terms of the MIT licence.
 *  You should have obtained a copy of the MIT licence with this software,
 *  if not please obtain one from https://opensource.org/licences/MIT
 *
 *
 *
 */

package org.frostbite.karren.interactions.Tags;

import org.frostbite.karren.interactions.Interaction;
import org.frostbite.karren.interactions.Tag;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

public class Random implements Tag {
    @Override
    public String handleTemplate(String msg, Interaction interaction, MessageBuilder response, MessageReceivedEvent event) {
        String[] tempArray = event.getMessage().getContent().split(":");
        if(tempArray.length==2){
            return msg.replace("%result", randomList(tempArray[1]));
        } else {
            return interaction.getRandomTemplatesFail();
        }
    }
    private static String randomList(String message){
        String[] choiceSet = message.split(",");
        String choice;
        int random = new java.util.Random().nextInt(choiceSet.length);
        choice = choiceSet[random].trim();
        return choice;
    }
}