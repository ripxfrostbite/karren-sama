/*
 * Copyright (c) 2018 Owen Bennett.
 *  You may use, distribute and modify this code under the terms of the MIT licence.
 *  You should have obtained a copy of the MIT licence with this software,
 *  if not please obtain one from https://opensource.org/licences/MIT
 *
 *
 *
 */

package org.frostbite.karren.Interactions.Tags.Guild;

import org.frostbite.karren.Database.Objects.DbGuild;
import org.frostbite.karren.Interactions.Interaction;
import org.frostbite.karren.Interactions.Tag;
import org.frostbite.karren.Karren;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.MessageBuilder;

import java.util.EnumSet;
import java.util.List;

public class SetAccessRole extends Tag {
    @Override
    public String handleTemplate(String msg, Interaction interaction, MessageBuilder response, MessageReceivedEvent event) {
        if(interaction.hasParameter()){
            List<IRole> roles = event.getGuild().getRolesByName(interaction.getParameter());
            if(roles.size()==1){
                DbGuild guild = Karren.bot.getSql().getGuild(event.getGuild());
                guild.setAccessRole(roles.get(0).getName());
                guild.update();
                msg = interaction.replaceMsg(msg, "%role", roles.get(0).getName());
            } else {
                msg = interaction.getRandomTemplate("fail").getTemplate();
            }
        } else {
            msg = interaction.getRandomTemplate("noparam").getTemplate();
        }
        return msg;
    }

    @Override
    public String getTagName() {
        return "setaccessrole";
    }

    @Override
    public EnumSet<Permissions> getRequiredPermissions() {
        return EnumSet.of(Permissions.SEND_MESSAGES);
    }
}
