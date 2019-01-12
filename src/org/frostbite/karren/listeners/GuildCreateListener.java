/*
 * Copyright (c) 2017 Owen Bennett.
 *  You may use, distribute and modify this code under the terms of the MIT licence.
 *  You should have obtained a copy of the MIT licence with this software,
 *  if not please obtain one from https://opensource.org/licences/MIT
 *
 *
 *
 */

package org.frostbite.karren.listeners;

import org.frostbite.karren.AudioPlayer.GuildMusicManager;
import org.frostbite.karren.Karren;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;

public class GuildCreateListener implements IListener<GuildCreateEvent> {
    @Override
    public void handle(GuildCreateEvent guildCreateEvent) {
        GuildMusicManager gm = Karren.bot.createGuildMusicManager(guildCreateEvent.getGuild());
        guildCreateEvent.getGuild().getAudioManager().setAudioProvider(gm.getAudioProvider());
        Karren.log.info("Guild " + guildCreateEvent.getGuild().getName() + " has been registered with the database, and a GuildMusicManager has been spawned.");
    }
}