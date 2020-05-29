package interfaces.component;

import interfaces.event.EventI;

public interface ReceptorEventI {

	public void eventProcess(String emitterURI, EventI e) throws Exception; 
}
