package Rules;

import java.util.ArrayList;
import Events.TemperatureReading;
import Events.Window;
import commands.DecreaseTemperature;
import commands.RaiseTemperature;
import commands.SwitchModeThermostat;
import components.correlators.managingelement.PortReferencer;
import components.physicaldevices.ThermostatComponent;
import components.physicaldevices.WindowController;
import interfaces.event.AbstractAtomicEvent;
import interfaces.event.EventI;
import interfaces.rule.AbstractRuleMultiRoom;
import interfaces.rule.EventMatcherRoomI;
import ports.CorrelatorOutboundPort;

public class HeatingOptimizerRule extends AbstractRuleMultiRoom {

	public static final EventMatcherRoomI MATCHER_WINDOW_OPEN =
			((e, r) -> e.getClass().getSimpleName().equals(Window.class.getSimpleName())
					&& e.getPropertyValue(AbstractAtomicEvent.TYPE_PROPERTY).equals(WindowController.OPENED_WINDOW) 
					&& e.getPropertyValue(AbstractAtomicEvent.ROOM_PROPERTY) == r) ;
	
	public static final EventMatcherRoomI MATCHER_ANORMAL_TEMP =
			((e, r) -> e.getClass().getSimpleName().equals(TemperatureReading.class.getSimpleName())
					&& !e.getPropertyValue(AbstractAtomicEvent.TYPE_PROPERTY).equals(ThermostatComponent.NORMAL_TEMPERATURE)
					&& e.getPropertyValue(ThermostatComponent.STATE).equals(ThermostatComponent.ON)
					&& e.getPropertyValue(AbstractAtomicEvent.ROOM_PROPERTY) == r) ;
	
	public HeatingOptimizerRule(PortReferencer<CorrelatorOutboundPort> pr) {
		super(pr);
	}
	
	public HeatingOptimizerRule() {
		super();
	}
	
	@Override
	public void init() {}

	@Override
	public ArrayList<EventI> trigger(String room) throws Exception {
				
		// Pattern event matching
		EventI windowOpen = this.match(MATCHER_WINDOW_OPEN, room) ;
		EventI anormalTemp = this.match(MATCHER_ANORMAL_TEMP, room) ;
				
		// If not any event have been matched for intrusion, notify all rooms
		if (anormalTemp==null) {
			return null;
		}
		ArrayList<EventI> ret = new ArrayList<EventI>() ;
		
		if (windowOpen==null) {	
			// Decrease or raise the temperature
			ret.add(anormalTemp) ;
		} else {
			// Turn off the thermostat so as not to waste heating
			ret.add(anormalTemp) ;
			ret.add(windowOpen) ;
		}
		return ret ;
	}

	@Override
	public void actionsWhenNoTriggeringEvent(ArrayList<EventI> triggeringEvents) throws Exception {
		// Nothing
	}

	@Override
	public void actions(ArrayList<EventI> triggeringEvents) throws Exception {
				
		String room = (String) triggeringEvents.get(0).getPropertyValue(AbstractAtomicEvent.ROOM_PROPERTY);
		String type = triggeringEvents.get(0).getClass().getSimpleName();
		
		CorrelatorOutboundPort port = pr.getPort(room, type);
		
		System.out.println(" -> ICI port = "+port);
		
		// If there is closed window 
		if (triggeringEvents.size()==1) {
			int detectedTemp = (int) triggeringEvents.get(0).getPropertyValue(TemperatureReading.TEMP_PROPERTY);
			int targetTemp = (int) triggeringEvents.get(0).getPropertyValue(TemperatureReading.TEMP_TARGET);
			int differential = Math.abs(detectedTemp - targetTemp);
			
			if (differential < 0) {
				// if temperature is below the threshold raise the thermostat temperature
				port.execute(new RaiseTemperature(1));
			} else {
				// else decrease the thermostat temperature
				port.execute(new DecreaseTemperature(1));
			}
		} else {
			// if there is Opened window, turnOff the thermostat
			port.execute(new SwitchModeThermostat(ThermostatComponent.OFF));
		}
	}

	@Override
	public void effects(ArrayList<EventI> triggeringEvents) {
		for (EventI e : triggeringEvents) {
			this.eventBase.removeEvent(e);
		}
	}
}
