package components.correlators.managingelement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import interfaces.event.EventI;

/**
 * The class <code>EventBase</code> contains all the events received by a correlator and on which the correlation rules will operate
 * 
 * <p><strong>Description</strong></p>
 * This class is used to facilitate correlations using temporalities,
 * the event database keeps them in the order of their occurrence
 * 
 * <p>
 * In this class we can simply add rules which will be kept in the order of additions to this database
 * </p>
 * 
 * @author Hadrien Cazes, Sebastien Lefevre, Kady Soumahoro
 *
 */
public class EventBase {

	private ArrayList<EventI> eventBase;

	public EventBase () {
		this.eventBase = new ArrayList<>();
	}
	
	
	public ArrayList<EventI> getEventBase(){
		return eventBase;
	}
	
	/**
	 * Clear allows to destroy all the events which occurred more than
	 * period of time compared to the current instant
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
	
	/**
	 * returns the number of events contained in an event database
	 * 
	 * @return
	 */
	public int numberOfEvents() {
		return this.eventBase.size();
	}
	
	/**
	 * Add an event to an event database
	 * 
	 * 
	 * @param e
	 */
	public void addEvent(EventI e) {
		this.eventBase.add(e);
	}
	
	/**
	 * Delete an element in an event database
	 * @param e
	 */
	public void removeEvent(EventI e) {
        for (EventI event: this.eventBase) {
            if(event.equals(e)) {
                this.eventBase.remove(e);
               return;
            }
        }
    }
	
	/**
	 * renvoie true si l'evenement est present dans la base sinon false
	 * @param e
	 * @return
	 */
	public boolean appearsIn(EventI e) {
		for (EventI event: this.eventBase) {
			if(event.getURI().equals(e.getURI())) {//URI est unique 
				return true;
			}
		}
		return true;
	}
	
	/**
	 * returns the desired event though index in the event database
	 * 
	 * @param index
	 * @return
	 */
	public EventI getEvent(int index) {
		return this.eventBase.get(index);
	}
}