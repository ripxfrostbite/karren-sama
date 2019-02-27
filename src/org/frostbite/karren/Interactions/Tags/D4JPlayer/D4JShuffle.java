/*
 * Copyright (c) 2019 Owen Bennett.
 *  You may use, distribute and modify this code under the terms of the MIT licence.
 *  You should have obtained a copy of the MIT licence with this software,
 *  if not please obtain one from https://opensource.org/licences/MIT
 *
 *
 *
 */

package org.frostbite.karren.Interactions.Tags.D4JPlayer;

import org.frostbite.karren.AudioPlayer.GuildMusicManager;
import org.frostbite.karren.Interactions.Interaction;
import org.frostbite.karren.Interactions.Tag;
import org.frostbite.karren.Karren;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.MessageBuilder;

import java.util.EnumSet;

public class D4JShuffle extends Tag {
    @Override
    public String handleTemplate(String msg, Interaction interaction, MessageBuilder response, MessageReceivedEvent event) {
        if(Karren.bot.getClient().getConnectedVoiceChannels().size()>0){
            GuildMusicManager gm = Karren.bot.getGuildMusicManager(event.getGuild());
            if(gm.scheduler.isShuffle()) {
                gm.scheduler.setShuffle(false);
                msg = interaction.replaceMsg(msg,"%bool", "false");
            } else {
                gm.scheduler.setShuffle(true);
                msg = interaction.replaceMsg(msg,"%bool", "true");
            }
        }
        return msg;
    }

    @Override
    public String getTagName() {
        return "d4jshuffle";
    }

    @Override
    public EnumSet<Permissions> getRequiredPermissions() {
        return EnumSet.of(Permissions.SEND_MESSAGES);
    }


}
