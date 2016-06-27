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
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.audio.AudioPlayer;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Speak implements Tag {

    @Override


    public String handleTemplate(String msg, Interaction interaction, MessageBuilder response, MessageReceivedEvent event){
        IVoiceChannel voiceChan = event.getMessage().getAuthor().getConnectedVoiceChannels().size()>0 ? event.getMessage().getAuthor().getConnectedVoiceChannels().get(0) : null;
        if(voiceChan!=null){
            AudioPlayer audio = AudioPlayer.getAudioPlayerForGuild(voiceChan.getGuild());
            if(audio.playlistSize()==0) {
                try {
                    voiceChan.join();
                    audio.setVolume(interaction.getVoiceVolume());
                    if(interaction.hasYoutubeFile()){
                        audio.queue(new File("cache/" + interaction.getYoutubeCacheFile() + ".mp3"));
                    } else {
                        audio.queue(new File(interaction.getRandomVoiceFile()));
                    }
                } catch (IOException | UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (MissingPermissionsException e) {
                    msg = "I don't have permission to enter the voice channel!";
                }
            } else {
                msg = interaction.getRandomTemplatesFail();
            }
        }
        return msg;
    }
}
