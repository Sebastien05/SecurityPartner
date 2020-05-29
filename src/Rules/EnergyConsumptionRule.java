package Rules;

import java.sql.Timestamp;
import java.util.ArrayList;

import interfaces.event.EventI;
import interfaces.rule.AbstractRule;
import interfaces.rule.EventMatcherI;
import ports.CorrelatorOutboundPort;

import java.util.Date;

public class EnergyConsumptionRule extends AbstractRule {

	public EnergyConsumptionRule(CorrelatorOutboundPort cop) {
		super(cop);
	}
	
	public EnergyConsumptionRule() {
		super();
	}

	public static final EventMatcherI MATCHER_ENERGY = (e -> e.getPropertyValue("type").equals("energy consumption"));

	private int currentHours;
	private int currentMinutes;
	
	@Override
	public void init() {
		Timestamp stamp = new Timestamp(System.currentTimeMillis());
		Date date = new Date(stamp.getTime());
		currentHours = date.getHours();
		currentMinutes = date.getMinutes();
	}

	@Override
	public ArrayList<EventI> trigger() {
		//System.out.println(currentHour);
		EventI energy = this.match(MATCHER_ENERGY);
		ArrayList<EventI> triggeringEvent = new ArrayList<EventI>();
		if (energy == null) {
			System.out.println("No energy consumption detected");
			return null;
		} else {
			if (((double) energy.getPropertyValue("energyValue") < 200.0)){
				System.out.println("ENERGY CONSUMPTION OK");
				return null;
			}
			double currentTime = (currentHours+currentMinutes)/60.0;
			if (currentTime<8||currentTime > 21) {
				System.out.println("ACTIVITY DETECTED AT INACTIVITY TIME: TRIGGERING ACTION");
				triggeringEvent.add(energy);
			}
			else {
				return null;
			}
		}
		return triggeringEvent;
	}

	@Override
	public void actions(ArrayList<EventI> triggeringEvents) {
		// emit alarm message
		StringBuilder message = new StringBuilder();
		message.append(triggeringEvents.get(0).getPropertyValue("room"));
		message.append(" : ACTIVITY DETECTED at ");
		message.append(triggeringEvents.get(0).getTimeStamp().getTime());
		System.out.println(message);
	}

	@Override
	public void effects(ArrayList<EventI> triggeringEvents) {
		this.eventBase.removeEvent(triggeringEvents.get(0)) ;
	}

}
