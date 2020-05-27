package interfaces.rule;

import java.util.ArrayList;

import components.correlators.managingelement.EventBase;
import interfaces.event.EventI;

public abstract class AbstractRule implements RuleI {
	
	protected EventBase eventBase;
	
	public AbstractRule() {}
	
	public RuleI clone() throws CloneNotSupportedException {
		return (RuleI) super.clone();
	}
	
	public EventI match(EventMatcherI em) {
		for (int i = 0; i < this.eventBase.numberOfEvents(); i++) {
			EventI event = this.eventBase.getEvent(i);
			if (em.match(event)) {
				return event;
			}
		} return null;
	}
	/***
	 *  prend en parametre la base d�evenements et lance les quatres methodes les unes
	 * apres les autres en commencant par init puis trigger 
	 */
	public boolean executeOn(EventBase events) {
		this.eventBase = events;
		this.init();
		
		// try to triggering events
		ArrayList<EventI> triggeringEvents = this.trigger();
		
		// if no matching event pattern stop here
		if (triggeringEvents==null)
			return false;
		
		// else make actions and effects
		this.actions(triggeringEvents);
		this.effects(triggeringEvents);
		return true;		
	}
	/*
	 * method for JUnit test
	 */
	public boolean executeOnTest(EventBase events) {
		this.eventBase = events;
		this.init();
		
		// try to triggering events
		ArrayList<EventI> triggeringEvents = this.trigger();
		
		// if no matching event pattern stop here
		if (triggeringEvents==null)
			return false;
		
		// else make effects
		this.effects(triggeringEvents);
		return true;		
	}

	//Abstracts methods
	public abstract void init();
	public abstract ArrayList<EventI> trigger();
	public abstract void actions(ArrayList<EventI> triggeringEvents);
	public abstract void effects(ArrayList<EventI> triggeringEvents);
}
