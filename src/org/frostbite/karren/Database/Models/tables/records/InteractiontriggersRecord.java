/**
 * This class is generated by jOOQ
 */
package org.frostbite.karren.Database.Models.tables.records;


import javax.annotation.Generated;

import org.frostbite.karren.Database.Models.tables.Interactiontriggers;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
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
public class InteractiontriggersRecord extends UpdatableRecordImpl<InteractiontriggersRecord> implements Record3<Integer, String, Integer> {

	private static final long serialVersionUID = 1909979901;

	/**
	 * Setter for <code>KarrenDB.InteractionTriggers.TriggerID</code>.
	 */
	public void setTriggerid(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>KarrenDB.InteractionTriggers.TriggerID</code>.
	 */
	public Integer getTriggerid() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>KarrenDB.InteractionTriggers.Trigger</code>.
	 */
	public void setTrigger(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>KarrenDB.InteractionTriggers.Trigger</code>.
	 */
	public String getTrigger() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>KarrenDB.InteractionTriggers.InteractionID</code>.
	 */
	public void setInteractionid(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>KarrenDB.InteractionTriggers.InteractionID</code>.
	 */
	public Integer getInteractionid() {
		return (Integer) getValue(2);
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
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Integer, String, Integer> fieldsRow() {
		return (Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Integer, String, Integer> valuesRow() {
		return (Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Interactiontriggers.INTERACTIONTRIGGERS.TRIGGERID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Interactiontriggers.INTERACTIONTRIGGERS.TRIGGER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return Interactiontriggers.INTERACTIONTRIGGERS.INTERACTIONID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getTriggerid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getTrigger();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getInteractionid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InteractiontriggersRecord value1(Integer value) {
		setTriggerid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InteractiontriggersRecord value2(String value) {
		setTrigger(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InteractiontriggersRecord value3(Integer value) {
		setInteractionid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InteractiontriggersRecord values(Integer value1, String value2, Integer value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached InteractiontriggersRecord
	 */
	public InteractiontriggersRecord() {
		super(Interactiontriggers.INTERACTIONTRIGGERS);
	}

	/**
	 * Create a detached, initialised InteractiontriggersRecord
	 */
	public InteractiontriggersRecord(Integer triggerid, String trigger, Integer interactionid) {
		super(Interactiontriggers.INTERACTIONTRIGGERS);

		setValue(0, triggerid);
		setValue(1, trigger);
		setValue(2, interactionid);
	}
}
