package InterfaceEvent;

import java.util.ArrayList;

public interface AggregateEventI extends EventI {
	ArrayList<EventI> getCorrelatedEvents();
	
}
