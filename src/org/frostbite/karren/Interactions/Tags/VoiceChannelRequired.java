/*
 * Copyright (c) 2019 Owen Bennett.
 *  You may use, distribute and modify this code under the terms of the MIT licence.
 *  You should have obtained a copy of the MIT licence with this software,
 *  if not please obtain one from https://opensource.org/licences/MIT
 *
 *
 *
 */

package org.frostbite.karren.Interactions.Tags;

import org.frostbite.karren.Interactions.Interaction;
import org.frostbite.karren.Interactions.InteractionResult;
import org.frostbite.karren.Interactions.Tag;

public class VoiceChannelRequired extends Tag {
    @Override
    public String handleTemplate(String msg, Interaction interaction, InteractionResult result) {
        if(result.getEvent().getMember()!=null) {
            if(result.getEvent().getMember().getVoiceState()!=null) {
                if (!result.getEvent().getMember().getVoiceState().inVoiceChannel()) {
                    msg = interaction.getRandomTemplate("novoice").getTemplate();
                    interaction.stopProcessing();
                }
            }
        }
        return msg;
    }

    @Override
    public String getTagName() {
        return "requiresvoice";
    }

}
