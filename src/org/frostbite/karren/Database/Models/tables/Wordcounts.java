/**
 * This class is generated by jOOQ
 */
package org.frostbite.karren.Database.Models.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.frostbite.karren.Database.Models.Karrendb;
import org.frostbite.karren.Database.Models.Keys;
import org.frostbite.karren.Database.Models.tables.records.WordcountsRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.4"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Wordcounts extends TableImpl<WordcountsRecord> {

	private static final long serialVersionUID = -885521043;

	/**
	 * The reference instance of <code>KarrenDB.WordCounts</code>
	 */
	public static final Wordcounts WORDCOUNTS = new Wordcounts();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<WordcountsRecord> getRecordType() {
		return WordcountsRecord.class;
	}

	/**
	 * The column <code>KarrenDB.WordCounts.WordID</code>.
	 */
	public final TableField<WordcountsRecord, Integer> WORDID = createField("WordID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>KarrenDB.WordCounts.Word</code>.
	 */
	public final TableField<WordcountsRecord, String> WORD = createField("Word", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * The column <code>KarrenDB.WordCounts.Count</code>.
	 */
	public final TableField<WordcountsRecord, Long> COUNT = createField("Count", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>KarrenDB.WordCounts.CountStarted</code>.
	 */
	public final TableField<WordcountsRecord, Timestamp> COUNTSTARTED = createField("CountStarted", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>KarrenDB.WordCounts.GuildID</code>.
	 */
	public final TableField<WordcountsRecord, String> GUILDID = createField("GuildID", org.jooq.impl.SQLDataType.VARCHAR.length(25).nullable(false), this, "");

	/**
	 * Create a <code>KarrenDB.WordCounts</code> table reference
	 */
	public Wordcounts() {
		this("WordCounts", null);
	}

	/**
	 * Create an aliased <code>KarrenDB.WordCounts</code> table reference
	 */
	public Wordcounts(String alias) {
		this(alias, WORDCOUNTS);
	}

	private Wordcounts(String alias, Table<WordcountsRecord> aliased) {
		this(alias, aliased, null);
	}

	private Wordcounts(String alias, Table<WordcountsRecord> aliased, Field<?>[] parameters) {
		super(alias, Karrendb.KARRENDB, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<WordcountsRecord, Integer> getIdentity() {
		return Keys.IDENTITY_WORDCOUNTS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<WordcountsRecord> getPrimaryKey() {
		return Keys.KEY_WORDCOUNTS_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<WordcountsRecord>> getKeys() {
		return Arrays.<UniqueKey<WordcountsRecord>>asList(Keys.KEY_WORDCOUNTS_PRIMARY, Keys.KEY_WORDCOUNTS_WORD);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<WordcountsRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<WordcountsRecord, ?>>asList(Keys.FK_WORDCOUNTS_GUILD1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Wordcounts as(String alias) {
		return new Wordcounts(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Wordcounts rename(String name) {
		return new Wordcounts(name, null);
	}
}
