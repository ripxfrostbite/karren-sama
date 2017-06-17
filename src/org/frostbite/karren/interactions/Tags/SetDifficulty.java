/*
 * Copyright (c) 2017 Owen Bennett.
 *  You may use, distribute and modify this code under the terms of the MIT licence.
 *  You should have obtained a copy of the MIT licence with this software,
 *  if not please obtain one from https://opensource.org/licences/MIT
 *
 *
 *
 */

package org.frostbite.karren.interactions.Tags;

import org.frostbite.karren.Database.Objects.DbGuild;
import org.frostbite.karren.Karren;
import org.frostbite.karren.interactions.Interaction;
import org.frostbite.karren.interactions.Tag;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

public class SetDifficulty implements Tag {
    @Override
    public String handleTemplate(String msg, Interaction interaction, MessageBuilder response, MessageReceivedEvent event) {
        if(interaction.hasParameter()){
            int difficulty = -1;
            try {
                difficulty = Integer.parseInt(interaction.getParameter());
            } catch (NumberFormatException e){
                msg = interaction.getRandomTemplatesFail();
            }
            if(difficulty>=0 && difficulty<=100){
                DbGuild dbGuild = Karren.bot.getSql().getGuild(event.getGuild());
                dbGuild.setRollDifficulty(difficulty);
                dbGuild.update();
                msg = msg.replace("%newdiff", String.valueOf(difficulty));
            } else {
                msg = interaction.getRandomTemplatesFail();
            }
        }
        return msg;
    }
}