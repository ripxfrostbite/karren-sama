/*
 * Copyright (c) 2017 Owen Bennett.
 *  You may use, distribute and modify this code under the terms of the MIT licence.
 *  You should have obtained a copy of the MIT licence with this software,
 *  if not please obtain one from https://opensource.org/licences/MIT
 *
 *
 *
 */

/**
 * This class is generated by jOOQ
 */
package org.frostbite.karren.Database.Models;


import javax.annotation.Generated;

import org.frostbite.karren.Database.Models.tables.Favorites;
import org.frostbite.karren.Database.Models.tables.Guild;
import org.frostbite.karren.Database.Models.tables.Interaction;
import org.frostbite.karren.Database.Models.tables.Interactionfailuretemplate;
import org.frostbite.karren.Database.Models.tables.Interactionpermissionerrortemplate;
import org.frostbite.karren.Database.Models.tables.Interactionsuccesstemplate;
import org.frostbite.karren.Database.Models.tables.Interactiontag;
import org.frostbite.karren.Database.Models.tables.InteractiontagHasInteraction;
import org.frostbite.karren.Database.Models.tables.Interactiontemplate;
import org.frostbite.karren.Database.Models.tables.Interactiontriggers;
import org.frostbite.karren.Database.Models.tables.Interactionvoicefile;
import org.frostbite.karren.Database.Models.tables.Songdb;
import org.frostbite.karren.Database.Models.tables.User;
import org.frostbite.karren.Database.Models.tables.Wordcounts;


/**
 * Convenience access to all tables in KarrenDB
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.4"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

	/**
	 * The table KarrenDB.Favorites
	 */
	public static final Favorites FAVORITES = org.frostbite.karren.Database.Models.tables.Favorites.FAVORITES;

	/**
	 * The table KarrenDB.Guild
	 */
	public static final Guild GUILD = org.frostbite.karren.Database.Models.tables.Guild.GUILD;

	/**
	 * The table KarrenDB.Interaction
	 */
	public static final Interaction INTERACTION = org.frostbite.karren.Database.Models.tables.Interaction.INTERACTION;

	/**
	 * The table KarrenDB.InteractionFailureTemplate
	 */
	public static final Interactionfailuretemplate INTERACTIONFAILURETEMPLATE = org.frostbite.karren.Database.Models.tables.Interactionfailuretemplate.INTERACTIONFAILURETEMPLATE;

	/**
	 * The table KarrenDB.InteractionPermissionErrorTemplate
	 */
	public static final Interactionpermissionerrortemplate INTERACTIONPERMISSIONERRORTEMPLATE = org.frostbite.karren.Database.Models.tables.Interactionpermissionerrortemplate.INTERACTIONPERMISSIONERRORTEMPLATE;

	/**
	 * The table KarrenDB.InteractionSuccessTemplate
	 */
	public static final Interactionsuccesstemplate INTERACTIONSUCCESSTEMPLATE = org.frostbite.karren.Database.Models.tables.Interactionsuccesstemplate.INTERACTIONSUCCESSTEMPLATE;

	/**
	 * The table KarrenDB.InteractionTag
	 */
	public static final Interactiontag INTERACTIONTAG = org.frostbite.karren.Database.Models.tables.Interactiontag.INTERACTIONTAG;

	/**
	 * The table KarrenDB.InteractionTag_has_Interaction
	 */
	public static final InteractiontagHasInteraction INTERACTIONTAG_HAS_INTERACTION = org.frostbite.karren.Database.Models.tables.InteractiontagHasInteraction.INTERACTIONTAG_HAS_INTERACTION;

	/**
	 * The table KarrenDB.InteractionTemplate
	 */
	public static final Interactiontemplate INTERACTIONTEMPLATE = org.frostbite.karren.Database.Models.tables.Interactiontemplate.INTERACTIONTEMPLATE;

	/**
	 * The table KarrenDB.InteractionTriggers
	 */
	public static final Interactiontriggers INTERACTIONTRIGGERS = org.frostbite.karren.Database.Models.tables.Interactiontriggers.INTERACTIONTRIGGERS;

	/**
	 * The table KarrenDB.InteractionVoiceFile
	 */
	public static final Interactionvoicefile INTERACTIONVOICEFILE = org.frostbite.karren.Database.Models.tables.Interactionvoicefile.INTERACTIONVOICEFILE;

	/**
	 * The table KarrenDB.songdb
	 */
	public static final Songdb SONGDB = org.frostbite.karren.Database.Models.tables.Songdb.SONGDB;

	/**
	 * The table KarrenDB.User
	 */
	public static final User USER = org.frostbite.karren.Database.Models.tables.User.USER;

	/**
	 * The table KarrenDB.WordCounts
	 */
	public static final Wordcounts WORDCOUNTS = org.frostbite.karren.Database.Models.tables.Wordcounts.WORDCOUNTS;
}
