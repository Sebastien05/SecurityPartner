package Rules;

import java.util.ArrayList;

import components.correlators.managingelement.PortReferencer;
import interfaces.event.EventI;
import interfaces.rule.AbstractRule;
import interfaces.rule.EventMatcherI;
import ports.CorrelatorOutboundPort;

public class TemperatureRule extends AbstractRule {

	public TemperatureRule(PortReferencer<CorrelatorOutboundPort> pr) {
		super(pr);
	}
	public TemperatureRule() {
		super();
	}
	
	public static final EventMatcherI MATCHER_TEMPERATURE = (e -> e.getPropertyValue("type").equals("temperature"));

	@Override
	public void init() {
	}

	@Override
	public ArrayList<EventI> trigger() {
		EventI temperature = this.match(MATCHER_TEMPERATURE);
		ArrayList<EventI> triggeringEvent = new ArrayList<EventI>();
		if (temperature == null || ((double) temperature.getPropertyValue("temperature") < 50.0)) {
			System.out.println("temperature OK");
			return null;
		} else {
			System.out.println("DANGER HIGH TEMPERATURE: TRIGGERING ACTION!");
			triggeringEvent.add(temperature);
		}
		return triggeringEvent;
	}

	@Override
	public void actions(ArrayList<EventI> triggeringEvents) {
		// emit alarm message
		StringBuilder message = new StringBuilder();
		message.append(triggeringEvents.get(0).getPropertyValue("room"));
		message.append(" : Warning /!\\ High temperature detected at ");
		message.append(triggeringEvents.get(0).getTimeStamp().getTime());
		System.out.println(message);

	}

	@Override
	public void effects(ArrayList<EventI> triggeringEvents) {
		this.eventBase.removeEvent(triggeringEvents.get(0)) ;
	}

}
