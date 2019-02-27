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

import io.github.vrchatapi.VRCWorld;
import org.frostbite.karren.Interactions.Interaction;
import org.frostbite.karren.Interactions.Tag;
import org.frostbite.karren.Karren;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.MessageBuilder;

import java.util.EnumSet;
import java.util.List;

public class VRCWorldSearch extends Tag {
    @Override
    public String handleTemplate(String msg, Interaction interaction, MessageBuilder response, MessageReceivedEvent event) {
        if(interaction.hasParameter()){
            List<VRCWorld> worlds = VRCWorld.list(0, 1, false, interaction.getParameter());
            if(worlds.size()>0){
                VRCWorld world = worlds.get(0);
                interaction.setEmbedImage(world.getThumbnailImageUrl());
                Karren.log.debug(world.toString());
                msg = interaction.replaceMsg(msg, "%name", world.getName());
                msg = interaction.replaceMsg(msg, "%author", world.getAuthorName());
                msg = interaction.replaceMsg(msg, "%relstatus", world.getReleaseStatus().name());
                msg = interaction.replaceMsg(msg, "%occupants", Integer.toString(world.getOccupants()));
                msg = interaction.replaceMsg(msg, "%worldid", "https://www.vrchat.net/launch?worldId=" + world.getId());

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
        return "VRCWorldSearch";
    }

    @Override
    public EnumSet<Permissions> getRequiredPermissions() {
        return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
    }
}
