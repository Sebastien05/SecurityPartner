package Rules;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import commands.TurnOFFAlarm;
import commands.TurnONAlarm;
import components.physicaldevices.AlarmComponent;
import components.physicaldevices.PresenceDetector;
import components.physicaldevices.WindowController;
import interfaces.component.ExecutorCommandI;
import interfaces.event.EventI;
import interfaces.rule.AbstractRule;
import interfaces.rule.EventMatcherI;
import ports.CorrelatorOutboundPort;

public class IntrusionRule extends AbstractRule {
		
	public static final EventMatcherI MATCHER_WINDOW_OPEN =
			(e -> e.getPropertyValue("type").
			equals(WindowController.OPENED_WINDOW)) ;
	public static final EventMatcherI MATCHER_PRESENCE_DETECTION =
			(e -> e.getPropertyValue("type").
			equals(PresenceDetector.PRESENCE_DETECTED)) ;
		
	public IntrusionRule(CorrelatorOutboundPort cop) {
		super(cop);
	}
	
	public IntrusionRule() {
		super();
	}

	@Override
	public void init() {
		return;
	}

	@Override
	public ArrayList<EventI> trigger() throws Exception {
		
		// Pattern event matching
		EventI windowOpen = this.match(MATCHER_WINDOW_OPEN) ;
		EventI presence = this.match(MATCHER_PRESENCE_DETECTION) ;
		
		if (windowOpen==null || presence==null) {
			this.cop.execute(new TurnOFFAlarm(new Timestamp((new Date()).getTime())));
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
	public void actions(ArrayList<EventI> triggerringEvents) throws Exception {
		ExecutorCommandI<AlarmComponent> cmd = new TurnONAlarm(triggerringEvents.get(1).getTimeStamp());
		this.cop.execute(cmd);
	}

	@Override
	public void effects(ArrayList<EventI> triggerringEvents) {
		// side effect no eventBase
		this.eventBase.removeEvent(triggerringEvents.get(0)) ;
		this.eventBase.removeEvent(triggerringEvents.get(1)) ;
	}
	
//		
//		public IntrusionRule(HashMap<String, CorrelatorOutboundPort> cop) {
//			super(cop);
//		}
//		
//		@Override
//		public void actions(ArrayList<EventI> triggerringEvents) throws Exception {
//			EventI event = triggerringEvents.get(1);
//			this.cop.get(event.getPropertyValue(AbstractAtomicEvent.ROOM_PROPERTY)).execute(new TurnONAlarm(event.getTimeStamp()));
//		}
//
	
}
