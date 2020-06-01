package Rules;

import java.util.ArrayList;

import components.correlators.managingelement.PortReferencer;
import interfaces.event.EventI;
import interfaces.rule.AbstractRule;
import interfaces.rule.AbstractRuleMultiRoom;
import interfaces.rule.EventMatcherI;
import interfaces.rule.EventMatcherRoomI;
import ports.CorrelatorOutboundPort;

public class FireRule extends AbstractRule {

	public FireRule(PortReferencer<CorrelatorOutboundPort> pr) {
		super(pr);
	}
	
	public FireRule() {
		super();
	}

	public static final EventMatcherI MATCHER_SMOKE = (e -> e.getPropertyValue("type").equals("smoke"));

	@Override
	public void init() {
	}

	@Override
	public ArrayList<EventI> trigger() {
		EventI smoke = this.match(MATCHER_SMOKE);
		ArrayList<EventI> triggeringEvent = new ArrayList<EventI>();
		if (smoke == null) {
			System.out.println("NO SMOKE");
			return null;
		} else {
			System.out.println("DANGER SMOKE: TRIGGERING ACTION!");
			triggeringEvent.add(smoke);
		}
		return triggeringEvent;
	}

	@Override
	public void actions(ArrayList<EventI> triggeringEvents) {
		// emit alarm message
		StringBuilder message = new StringBuilder();
		message.append(triggeringEvents.get(0).getPropertyValue("room"));
		message.append(" : Warning /!\\ SMOKE detected at ");
		message.append(triggeringEvents.get(0).getTimeStamp().getTime());
		System.out.println(message);
	}

	@Override
	public void effects(ArrayList<EventI> triggeringEvents) {
		this.eventBase.removeEvent(triggeringEvents.get(0)) ;
	}

}
