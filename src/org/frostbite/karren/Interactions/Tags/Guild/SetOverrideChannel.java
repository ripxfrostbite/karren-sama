/*
 * Copyright (c) 2019 Owen Bennett.
 *  You may use, distribute and modify this code under the terms of the MIT licence.
 *  You should have obtained a copy of the MIT licence with this software,
 *  if not please obtain one from https://opensource.org/licences/MIT
 *
 *
 *
 */

package org.frostbite.karren.Interactions.Tags.Guild;

import net.dv8tion.jda.api.entities.TextChannel;
import org.frostbite.karren.Database.Objects.DbGuild;
import org.frostbite.karren.Interactions.Interaction;
import org.frostbite.karren.Interactions.InteractionResult;
import org.frostbite.karren.Interactions.Tag;
import org.frostbite.karren.Karren;

import java.util.List;

public class SetOverrideChannel extends Tag {
    @Override
    public String handleTemplate(String msg, Interaction interaction, InteractionResult result) {
        if (interaction.hasParameter()) {
            List<TextChannel> channels = result.getEvent().getGuild().getTextChannelsByName(interaction.getParameter(), true);
            //List<IChannel> channels = event.getGuild().getChannelsByName(interaction.getParameter());
            if (channels.size() == 1) {
                DbGuild dbGuild = Karren.bot.getSql().getGuild(result.getEvent().getGuild());
                dbGuild.setOverrideChannel(channels.get(0).getIdLong());
                dbGuild.update();
                msg = interaction.replaceMsg(msg, "%channel", channels.get(0).getName());
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
        return "setoverridechannel";
    }

}
