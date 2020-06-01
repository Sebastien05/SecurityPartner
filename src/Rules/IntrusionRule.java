package Rules;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import Events.Presence;
import Events.Window;
import commands.TurnOFFAlarm;
import commands.TurnONAlarm;
import components.correlators.managingelement.PortReferencer;
import components.physicaldevices.PresenceDetector;
import components.physicaldevices.WindowController;
import interfaces.event.AbstractAtomicEvent;
import interfaces.event.EventI;
import interfaces.rule.AbstractRuleMultiRoom;
import interfaces.rule.EventMatcherRoomI;
import ports.CorrelatorOutboundPort;

public class IntrusionRule extends AbstractRuleMultiRoom {
		
	public static final EventMatcherRoomI MATCHER_WINDOW_OPEN =
			((e, r) -> e.getClass().getSimpleName().equals(Window.class.getSimpleName())
					&& e.getPropertyValue("type").equals(WindowController.OPENED_WINDOW) 
					&& e.getPropertyValue(AbstractAtomicEvent.ROOM_PROPERTY) == r) ;
	
	public static final EventMatcherRoomI MATCHER_PRESENCE_DETECTION =
			((e, r) -> e.getClass().getSimpleName().equals(Presence.class.getSimpleName())
					&& e.getPropertyValue("type").equals(PresenceDetector.PRESENCE_DETECTED)
					&& e.getPropertyValue(AbstractAtomicEvent.ROOM_PROPERTY) == r) ;
		
	public IntrusionRule(PortReferencer<CorrelatorOutboundPort> pr) {
		super(pr);
	}
	
	public IntrusionRule() {
		super();
	}

	@Override
	public void init() {
		return;
	}

	@Override
	public ArrayList<EventI> trigger(String room) throws Exception {
		
		
		// Pattern event matching
		EventI windowOpen = this.match(MATCHER_WINDOW_OPEN, room) ;
		EventI presence = this.match(MATCHER_PRESENCE_DETECTION, room) ;
				
		// If not any event have been matched for intrusion, notify all rooms
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
	public void actionsWhenNoTriggeringEvent (ArrayList<EventI> triggeringEvents) 
	throws Exception 
	{
		CorrelatorOutboundPort cop;
		for (String roomName: this.pr.getAllRoom()){
			cop = (CorrelatorOutboundPort) pr.getPort(roomName, Presence.class.getSimpleName());
			cop.execute(new TurnOFFAlarm(new Timestamp((new Date()).getTime())));
		}		
	}

	@Override
	public void actions (ArrayList<EventI> triggerringEvents) 
	throws Exception 
	{
		String room = (String) ((AbstractAtomicEvent)triggerringEvents.get(1)).getRoom();
		String type = (String) ((AbstractAtomicEvent)triggerringEvents.get(1)).getType();
		this.pr.getPort(room, type).execute(new TurnONAlarm(triggerringEvents.get(1).getTimeStamp()));
	}

	@Override
	public void effects(ArrayList<EventI> triggerringEvents) {
		// side effect no eventBase
		this.eventBase.removeEvent(triggerringEvents.get(0)) ;
		this.eventBase.removeEvent(triggerringEvents.get(1)) ;
	}

}
