package org.frostbite.karren.interactions.Tags.D4JPlayer;

import org.apache.commons.io.FilenameUtils;
import org.frostbite.karren.AudioPlayer.AudioProvider;
import org.frostbite.karren.AudioPlayer.AudioResultHandler;
import org.frostbite.karren.AudioPlayer.GuildMusicManager;
import org.frostbite.karren.Karren;
import org.frostbite.karren.interactions.Interaction;
import org.frostbite.karren.interactions.Tag;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageBuilder;

import java.util.Arrays;
import java.util.Objects;


public class D4JPlay implements Tag{

    public final String[] usableExtensions = {"mp3", "ogg", "webm", "flac", "wav", "mp4", "m4a", "aac", "m3u", "pls"};

    @Override
    public String handleTemplate(String msg, Interaction interaction, MessageBuilder response, MessageReceivedEvent event) {
        if(event.getGuild().getAudioManager().getAudioProvider() instanceof AudioProvider) {
            GuildMusicManager gm = Karren.bot.getGuildMusicManager(event.getGuild());
            String voiceFile = interaction.getRandomVoiceFile();
            gm.scheduler.setAnnounceChannel(event.getChannel());
            AudioResultHandler arh = new AudioResultHandler(event, interaction, gm, msg);
            if (!gm.scheduler.isPlaying())
                gm.player.setVolume(Math.round(interaction.getVoiceVolume()));
            if (interaction.hasParameter() || voiceFile != null) {
                if (voiceFile != null) {
                    Karren.bot.getPm().loadItemOrdered(gm, voiceFile, arh);
                } else {
                    Karren.bot.getPm().loadItemOrdered(gm, interaction.getParameter(), arh);
                }
            } else if (!event.getMessage().getAttachments().isEmpty()) {
                for (IMessage.Attachment media : event.getMessage().getAttachments()) {
                    if (Arrays.stream(usableExtensions).anyMatch(x -> Objects.equals(x, FilenameUtils.getExtension(media.getFilename())))) {
                        Karren.bot.getPm().loadItemOrdered(gm, media.getUrl(), arh);
                    }
                }
            } else {
                msg = interaction.getRandomTemplatesFail();
            }
            if (arh.isFailed())
                msg = arh.getMsg();
        } else {
            msg = "Provider disabled";
        }

        return msg;
    }


}