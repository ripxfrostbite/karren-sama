/*
 * Copyright (c) 2019 Owen Bennett.
 *  You may use, distribute and modify this code under the terms of the MIT licence.
 *  You should have obtained a copy of the MIT licence with this software,
 *  if not please obtain one from https://opensource.org/licences/MIT
 *
 *
 *
 */

package org.frostbite.karren.Interactions.Tags.VRChat;

import io.github.vrchatapi.VRCFriends;
import io.github.vrchatapi.VRCUser;
import org.frostbite.karren.Database.Objects.DbUser;
import org.frostbite.karren.Interactions.Interaction;
import org.frostbite.karren.Interactions.Tag;
import org.frostbite.karren.Karren;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.MessageBuilder;

import java.util.EnumSet;
import java.util.List;

public class VRCLinkAccount extends Tag {
    @Override
    public String handleTemplate(String msg, Interaction interaction, MessageBuilder response, MessageReceivedEvent event) {
        if(interaction.hasParameter()){
            if(Karren.bot.sql.getUserData(event.getAuthor()).getVrcUserID()==null) {
                List<VRCUser> users = VRCUser.list(0, 1, false, interaction.getParameter());
                if (users.size() > 0) {
                    VRCUser user = VRCUser.fetch(users.get(0).getId());
                    VRCFriends.sendFriendRequest(user);
                    DbUser dbUser = Karren.bot.sql.getUserData(event.getAuthor());
                    dbUser.setVrcUserID(user.getId());
                    dbUser.update();
                    msg = interaction.replaceMsg(msg, "%username", user.getDisplayName());
                } else {
                    msg = interaction.getRandomTemplate("fail").getTemplate();
                }
            } else {
                msg = interaction.getRandomTemplate("linked").getTemplate();
            }
        } else {
            msg = interaction.getRandomTemplate("noparam").getTemplate();
        }
        return msg;
    }

    @Override
    public String getTagName() {
        return "VRCAddFriend";
    }

    @Override
    public EnumSet<Permissions> getRequiredPermissions() {
        return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
    }
}
