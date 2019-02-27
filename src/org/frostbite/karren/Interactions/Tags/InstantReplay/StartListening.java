/*
 * Copyright (c) 2019 Owen Bennett.
 *  You may use, distribute and modify this code under the terms of the MIT licence.
 *  You should have obtained a copy of the MIT licence with this software,
 *  if not please obtain one from https://opensource.org/licences/MIT
 *
 *
 *
 */

package org.frostbite.karren.Interactions.Tags.InstantReplay;

import org.frostbite.karren.Interactions.Interaction;
import org.frostbite.karren.Interactions.Tag;
import org.frostbite.karren.Karren;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.MessageBuilder;

import java.util.EnumSet;

public class StartListening extends Tag {
    @Override
    public String handleTemplate(String msg, Interaction interaction, MessageBuilder response, MessageReceivedEvent event) {
        if(!Karren.bot.getIrm().isGuildIRActive(event.getGuild()) && event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel()!=null){
            Karren.bot.getIrm().getInstantReplay(event.getGuild(), event.getAuthor()).startListening();
        }
        return msg;
    }

    @Override
    public String getTagName() {
        return "irstart";
    }

    @Override
    public EnumSet<Permissions> getRequiredPermissions() {
        return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.VOICE_CONNECT);
    }

    @Override
    public Boolean getVoiceUsed() {
        return true;
    }
}
