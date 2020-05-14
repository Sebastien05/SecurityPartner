package correlator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import interfaces.event.EventI;

public class EventBase {
	
	private ArrayList<EventI> eventBase;
	
	public EventBase () {
		this.eventBase = new ArrayList<>();
	}
	public void clearEvents(int period) {
		Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());
		
		if (period==0) {
			this.eventBase.clear();
			return;
		}
		
		for (EventI event : this.eventBase) {
			if (currentTime.compareTo(event.getTimeStamp())> period)
				this.eventBase.remove(event);
		}
		return;
	}
}
