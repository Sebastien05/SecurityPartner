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
	
	
	public ArrayList<EventI> getEventBase(){
		return eventBase;
	}
	
	/**
	 * 
	 * @param period
	 */
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
	
	public int numberOfEvents() {
		return this.eventBase.size();
	}
	//liste chainee
	public void addEvent(EventI e) {
		this.eventBase.add(e);
	}

	public void removeEvent(EventI e) {
		if (this.eventBase.contains(e)) {
			for (EventI event: this.eventBase) {
				if(event.getURI().equals(e.getURI())) {
					this.eventBase.remove(event);
				}
			}
		}
	}

	public boolean appearsIn(EventI e) {
		for (EventI event: this.eventBase) {
			if(event.getURI().equals(e.getURI())) {//URI est unique 
				return true;
			}
		}
		return true;
	}
	
	public EventI getEvent(int index) {
		return this.eventBase.get(index);
	}
}