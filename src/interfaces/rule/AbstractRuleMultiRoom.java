package interfaces.rule;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import commands.TurnOFFAlarm;
import components.correlators.managingelement.EventBase;
import components.correlators.managingelement.PortReferencer;
import components.physicaldevices.PresenceDetector;
import interfaces.event.EventI;
import ports.CorrelatorOutboundPort;

public abstract class AbstractRuleMultiRoom implements RuleMultiRoomI {
	
	protected EventBase eventBase;
	protected PortReferencer<CorrelatorOutboundPort> pr;
	
	public AbstractRuleMultiRoom(PortReferencer<CorrelatorOutboundPort> pr) {
		this.pr = pr;
	}
	
	// JUnit Test constructor
	public AbstractRuleMultiRoom() {}
	
	public RuleMultiRoomI clone() throws CloneNotSupportedException {
		return (RuleMultiRoomI) super.clone();
	}
	
	public EventI match(EventMatcherRoomI em, String room) {
		for (int i = 0; i < this.eventBase.numberOfEvents(); i++) {
			EventI event = this.eventBase.getEvent(i);
			if (em.match(event, room)) {
				return event;
			}
		} return null;
	}
	
	/***
	 *  prend en parametre la base d�evenements et lance les quatres methodes les unes
	 * apres les autres en commencant par init puis trigger 
	 */
	public boolean executeOn(EventBase events) throws Exception{
		this.eventBase = events;
		this.init();
		
		ArrayList<EventI> triggeringEvents = null;
		
		// For each room try to trigger event
		for (String room : this.pr.getAllRoom()) {
			// try to triggering events
			triggeringEvents = this.trigger(room);
			if (triggeringEvents != null) {
				break;
			}
		}

		// Make actions, you'll have to separate actions
		// - actions if not any have been triggered
		// - other actions if there is triggered events
		
		// If not any event have been matched for intrusion, notify all rooms
		if (triggeringEvents==null) {
			this.actionsWhenNoTriggeringEvent(triggeringEvents);
			return false;
		}
		
		this.actions(triggeringEvents);
		
		this.effects(triggeringEvents);
		return true;		
	}
	/*
	 * method for JUnit test
	 */
	public boolean executeOnTest(EventBase events) throws Exception {
		this.eventBase = events;
		this.init();
		
		ArrayList<EventI> triggeringEvents = null;
		
		// For each room try to trigger event
		for (String room : this.pr.getAllRoom()) {
			// try to triggering events
			triggeringEvents = this.trigger(room);
			if (triggeringEvents != null) {
				break;
			}
		}
		// If not any event have been matched for intrusion, notify all rooms
		if (triggeringEvents==null) {
			CorrelatorOutboundPort cop;
			for (String roomName: this.pr.getAllRoom()){
				cop = (CorrelatorOutboundPort) pr.getPort(roomName, PresenceDetector.NO_PRESENCE_DETECTED);
				cop.execute(new TurnOFFAlarm(new Timestamp((new Date()).getTime())));
			}
			return false;
		}
		// else make effects
		this.effects(triggeringEvents);
		return true;		
	}

	//Abstracts methods
	
	/**
	 * If you want to initialize your rule for any reason
	 */
	public abstract void init();
	
	/** Trigger : Try to find event matching in the same room if necessary
	 * If you wont to take room in rule detection use it only in your lambdas parameters
	 * @param room 
	 * @return
	 * @throws Exception
	 */
	public abstract ArrayList<EventI> trigger(String room) throws Exception;
	
	/** If not any event have been matched for intrusion, notify all rooms (Optional)
	 * @param triggeringEvents from trigger method
	 * @throws Exception
	 */
	public abstract void actionsWhenNoTriggeringEvent(ArrayList<EventI> triggeringEvents) throws Exception;
	
	/** Send action command if necessary 
	 * @triggeringEvents from trigger method
	 */
	public abstract void actions(ArrayList<EventI> triggeringEvents) throws Exception;
	
	/** Make side effects on the eventBase after processing triggeringEvents
	 * @triggeringEvents from trigger method
	 */
	public abstract void effects(ArrayList<EventI> triggeringEvents);
}
