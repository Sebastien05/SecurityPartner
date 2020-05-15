package Rules;

import java.util.ArrayList;

import interfaces.event.EventI;
import interfaces.rule.AbstractRule;
import interfaces.rule.EventMatcherI;

public class IntrusionRule extends AbstractRule {

	
	public static final EventMatcherI MATCHER_WINDOW_OPEN =
			(e -> e.getPropertyValue("type").
			equals("window open")) ;
	public static final EventMatcherI MATCHER_PRESENCE_DETECTION =
			(e -> e.getPropertyValue("type").
			equals("presence detection")) ;
	
	@Override
	public void init() {
		return;
	}

	@Override
	public ArrayList<EventI> trigger() {
		
		// Pattern event matching
		EventI windowOpen = this.match(MATCHER_WINDOW_OPEN) ;
		EventI presence = this.match(MATCHER_PRESENCE_DETECTION) ;
		
		if (windowOpen==null || presence==null) {
			return null;
		}
		
		if (windowOpen.getTimeStamp().compareTo(presence.getTimeStamp()) <= 0) {
			
			// the event window open happened before the detection of a
			// presence, hence we suspect an intrusion.
			ArrayList<EventI> ret = new ArrayList<EventI>() ;
			ret.add(windowOpen) ;
			ret.add(presence) ;
			return ret ;
		} else {
			// no intrusion detected.
			return null ;
		}
	}

	@Override
	public void actions(ArrayList<EventI> triggerringEvents) {
		// emit alarm message
		StringBuilder message = new StringBuilder();
		message.append(triggerringEvents.get(0).getPropertyValue("room"));
		message.append(" : Warning /!\\ Intrusion detected at ");
		message.append(triggerringEvents.get(0).getTimeStamp().getTime());
		System.out.println(message) ;
	}

	@Override
	public void effects(ArrayList<EventI> triggerringEvents) {
		// side effect no eventBase
		this.eventBase.removeEvent(triggerringEvents.get(0)) ;
		this.eventBase.removeEvent(triggerringEvents.get(1)) ;
	}
}
