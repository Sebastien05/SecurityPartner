package interfaces.rule;

import java.util.ArrayList;

import correlator.EventBase;
import interfaces.event.EventI;

public abstract class AbstractRule {
	
	private EventBase eventBase;
	
	public AbstractRule(EventBase eventBase) {
		this.eventBase = eventBase;
	}
	
	/*
	 * Abstracts methods
	 */
	
	public abstract EventI match(EventMatcherI em);
	
	public abstract void init();
	public abstract ArrayList<EventI> trigger();
	public abstract void actions(ArrayList<EventI> triggeringEvents);
	public abstract void effects(ArrayList<EventI> triggeringEvents);
	public abstract boolean executeOn(EventBase events);

	public abstract RuleI cloneRule();
	public abstract RuleI copyFrom(RuleI r);
}
