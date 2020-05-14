package interfaces.rule;

import java.util.ArrayList;

import correlator.EventBase;
import interfaces.event.EventI;

public interface RuleI {
	public RuleI cloneRule();
	public RuleI copyFrom(RuleI r);
	public EventI match(EventMatcherI em);
	public void init();
	public ArrayList<EventI> trigger();
	public void actions(ArrayList<EventI> triggeringEvents);
	public void effects(ArrayList<EventI> triggeringEvents);
	public boolean executeOn(EventBase events);
}
