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
	 * clear permet de detruire tous les evenements qui se sont produits a plus de
	 * period de temps par rapport a l’instant courant
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
	 * retourne le nombre d'evenement contenu dans une base d'evenement
	 * @return
	 */
	public int numberOfEvents() {
		return this.eventBase.size();
	}
	
	/**
	 * ajouter un evenement dans une base d'evenement
	 * @param e
	 */
	public void addEvent(EventI e) {
		this.eventBase.add(e);
	}
	
	/**
	 * supprime un element dans une base d'evenement
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
	 * renvoie l'evenement voulu via son indice dans la base d'evenement
	 * @param index
	 * @return
	 */
	public EventI getEvent(int index) {
		return this.eventBase.get(index);
	}
}