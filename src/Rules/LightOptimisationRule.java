package Rules;

import java.util.ArrayList;

import Events.Presence;
import commands.TurnONLight;
import components.correlators.managingelement.PortReferencer;
import components.physicaldevices.PresenceDetector;
import interfaces.event.AbstractAtomicEvent;
import interfaces.event.EventI;
import interfaces.rule.AbstractRuleMultiRoom;
import interfaces.rule.EventMatcherRoomI;
import ports.CorrelatorOutboundPort;

public class LightOptimisationRule extends AbstractRuleMultiRoom {

	public static final EventMatcherRoomI MATCHER_PRESENCE_DETECTION =
			((e, r) -> e.getClass().getSimpleName().equals(Presence.class.getSimpleName())
					&& e.getPropertyValue(AbstractAtomicEvent.TYPE_PROPERTY).equals(PresenceDetector.PRESENCE_DETECTED) 
					&& e.getPropertyValue(AbstractAtomicEvent.ROOM_PROPERTY) == r) ;
	
	public LightOptimisationRule(PortReferencer<CorrelatorOutboundPort> pr) {
		super(pr);
	}
	
	public LightOptimisationRule() {
		super();
	}
	
	@Override
	public void init() {
		return;
	}

	@Override
	public ArrayList<EventI> trigger(String room) throws Exception {
		// Pattern event matching
		EventI presence = this.match(MATCHER_PRESENCE_DETECTION, room) ;
		
		if (presence==null) {
			return null;
		}
		ArrayList<EventI> ret = new ArrayList<EventI>();
		ret.add(presence);
		return ret;
	}

	@Override
	public void actionsWhenNoTriggeringEvent(ArrayList<EventI> triggeringEvents) throws Exception {
	}

	@Override
	public void actions(ArrayList<EventI> triggerringEvents) throws Exception {
		Presence event = ((Presence)triggerringEvents.get(0));
		String room = (String) event.getRoom();
		String type = (String) event.getClass().getSimpleName();
		this.pr.getPort(room, type).execute(new TurnONLight(event.getTimeStamp()));
	}

	@Override
	public void effects(ArrayList<EventI> triggeringEvents) {
		this.eventBase.removeEvent(triggeringEvents.get(0));
	}

}
