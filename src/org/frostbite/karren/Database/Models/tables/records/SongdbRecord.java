/**
 * This class is generated by jOOQ
 */
package org.frostbite.karren.Database.Models.tables.records;


import javax.annotation.Generated;

import org.frostbite.karren.Database.Models.tables.Songdb;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


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
public class SongdbRecord extends UpdatableRecordImpl<SongdbRecord> implements Record7<Integer, String, Long, Integer, Integer, Long, Byte> {

	private static final long serialVersionUID = 949294023;

	/**
	 * Setter for <code>KarrenDB.songdb.ID</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>KarrenDB.songdb.ID</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>KarrenDB.songdb.SongTitle</code>.
	 */
	public void setSongtitle(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>KarrenDB.songdb.SongTitle</code>.
	 */
	public String getSongtitle() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>KarrenDB.songdb.LPTime</code>.
	 */
	public void setLptime(Long value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>KarrenDB.songdb.LPTime</code>.
	 */
	public Long getLptime() {
		return (Long) getValue(2);
	}

	/**
	 * Setter for <code>KarrenDB.songdb.PlayCount</code>.
	 */
	public void setPlaycount(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>KarrenDB.songdb.PlayCount</code>.
	 */
	public Integer getPlaycount() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>KarrenDB.songdb.FavCount</code>.
	 */
	public void setFavcount(Integer value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>KarrenDB.songdb.FavCount</code>.
	 */
	public Integer getFavcount() {
		return (Integer) getValue(4);
	}

	/**
	 * Setter for <code>KarrenDB.songdb.songduration</code>.
	 */
	public void setSongduration(Long value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>KarrenDB.songdb.songduration</code>.
	 */
	public Long getSongduration() {
		return (Long) getValue(5);
	}

	/**
	 * Setter for <code>KarrenDB.songdb.durationlock</code>.
	 */
	public void setDurationlock(Byte value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>KarrenDB.songdb.durationlock</code>.
	 */
	public Byte getDurationlock() {
		return (Byte) getValue(6);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record7 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row7<Integer, String, Long, Integer, Integer, Long, Byte> fieldsRow() {
		return (Row7) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row7<Integer, String, Long, Integer, Integer, Long, Byte> valuesRow() {
		return (Row7) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Songdb.SONGDB.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Songdb.SONGDB.SONGTITLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field3() {
		return Songdb.SONGDB.LPTIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return Songdb.SONGDB.PLAYCOUNT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field5() {
		return Songdb.SONGDB.FAVCOUNT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field6() {
		return Songdb.SONGDB.SONGDURATION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field7() {
		return Songdb.SONGDB.DURATIONLOCK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getSongtitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value3() {
		return getLptime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value4() {
		return getPlaycount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value5() {
		return getFavcount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value6() {
		return getSongduration();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value7() {
		return getDurationlock();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SongdbRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SongdbRecord value2(String value) {
		setSongtitle(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SongdbRecord value3(Long value) {
		setLptime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SongdbRecord value4(Integer value) {
		setPlaycount(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SongdbRecord value5(Integer value) {
		setFavcount(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SongdbRecord value6(Long value) {
		setSongduration(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SongdbRecord value7(Byte value) {
		setDurationlock(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SongdbRecord values(Integer value1, String value2, Long value3, Integer value4, Integer value5, Long value6, Byte value7) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached SongdbRecord
	 */
	public SongdbRecord() {
		super(Songdb.SONGDB);
	}

	/**
	 * Create a detached, initialised SongdbRecord
	 */
	public SongdbRecord(Integer id, String songtitle, Long lptime, Integer playcount, Integer favcount, Long songduration, Byte durationlock) {
		super(Songdb.SONGDB);

		setValue(0, id);
		setValue(1, songtitle);
		setValue(2, lptime);
		setValue(3, playcount);
		setValue(4, favcount);
		setValue(5, songduration);
		setValue(6, durationlock);
	}
}
