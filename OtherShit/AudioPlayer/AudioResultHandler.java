/*
 * Copyright (c) 2019 Owen Bennett.
 *  You may use, distribute and modify this code under the terms of the MIT licence.
 *  You should have obtained a copy of the MIT licence with this software,
 *  if not please obtain one from https://opensource.org/licences/MIT
 *
 *
 *
 */

package org.frostbite.karren.AudioPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.VoiceChannel;
import discord4j.voice.VoiceConnection;
import org.frostbite.karren.Interactions.Interaction;
import org.frostbite.karren.Karren;
import reactor.core.publisher.Flux;

import java.util.Objects;

public class AudioResultHandler implements AudioLoadResultHandler {
    MessageCreateEvent event;
    Interaction interaction;
    GuildMusicManager gm;
    String msg;
    boolean isPlaylist = false;
    int trackCount = 0;
    AudioTrack queuedTrack = null;
    long startAt = 0L;

    boolean failed = false;

    public AudioResultHandler(MessageCreateEvent event, Interaction interaction, GuildMusicManager gm, String msg) {
        this.event = event;
        this.interaction = interaction;
        this.gm = gm;
        this.msg = msg;
    }

    @Override
    public void trackLoaded(AudioTrack audioTrack) {
        connectToVoiceChannel(event);
        audioTrack.setPosition(startAt);
        gm.scheduler.queue(audioTrack);
        queuedTrack = audioTrack;
    }

    @Override
    public void playlistLoaded(AudioPlaylist audioPlaylist) {
        connectToVoiceChannel(event);
        isPlaylist = true;
        trackCount = audioPlaylist.getTracks().size();
        for (AudioTrack track : audioPlaylist.getTracks()) {
            gm.scheduler.queue(track);
        }
    }

    @Override
    public void noMatches() {
        failed = true;
        msg = interaction.getRandomTemplate("fail").getTemplate();
    }

    @Override
    public void loadFailed(FriendlyException e) {
        failed = true;
        msg = interaction.getRandomTemplate("permission").getTemplate();
    }

    public void connectToVoiceChannel(MessageCreateEvent event){
        User self = event.getClient().getSelf().block();
        Member author = event.getMessage().getAuthorAsMember().block();
        if(self !=null && author!=null && event.getGuildId().isPresent()) {
            Karren.log.info("Getting user voice channel");
            Member selfMember = self.asMember(event.getGuildId().get()).block();
            VoiceState vs = author.getVoiceState().block();
            if (vs != null && selfMember !=null) {
                Karren.log.info("Loaded user voice state and self user");
                VoiceState vsSelf = selfMember.getVoiceState().block();
                if (vsSelf == null) {
                    Karren.log.info("Bot not already in channel");
                    VoiceChannel voiceChannel = vs.getChannel().block();
                    if(voiceChannel!=null){
                        Karren.log.info("Connecting to VC");
                        Flux.first(Objects.requireNonNull(event.getGuild().block()).getChannels().ofType(VoiceChannel.class).filter(vc -> vc.getVoiceStates().hasElement(vs).block())).toIterable().forEach(it -> it.join(spec -> spec.setProvider(gm.getAudioProvider())).subscribe(VoiceConnection::disconnect));
                    }

                }
            } else {
                failed = true;
                msg = "Hold on a sec, you're not in a voice channel!";
            }
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setStartAt(long startAt){
        this.startAt = startAt;
    }

    public boolean isPlaylist() {
        return isPlaylist;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public AudioTrack getQueuedTrack() {
        return queuedTrack;
    }

    public boolean isFailed() {
        return failed;
    }
}