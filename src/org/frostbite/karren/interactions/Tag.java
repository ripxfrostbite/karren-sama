/*
 * Copyright (c) 2018 Owen Bennett.
 *  You may use, distribute and modify this code under the terms of the MIT licence.
 *  You should have obtained a copy of the MIT licence with this software,
 *  if not please obtain one from https://opensource.org/licences/MIT
 *
 *
 *
 */

package org.frostbite.karren.interactions;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.MessageBuilder;

import java.util.EnumSet;

public class Tag {
    public String handleTemplate(String msg, Interaction interaction, MessageBuilder response, MessageReceivedEvent event){
        return msg;
    }
    public String getTagName(){
        return "NO NAME";
    }
    public EnumSet<Permissions> getRequiredPermissions(){
        return EnumSet.of(Permissions.SEND_MESSAGES);
    }
    public Boolean getVoiceUsed() { return false; }
}
