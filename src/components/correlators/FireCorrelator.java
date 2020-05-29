package components.correlators;

import fr.sorbonne_u.components.AbstractComponent;
import interfaces.component.ReceptorEventI;
import interfaces.event.EventI;

/**
 * fumee ou fumee + lumiere : incendi : action alarm
 *
 */
public class FireCorrelator  
extends AbstractComponent 
implements ReceptorEventI{
	protected FireCorrelator () {
		super(1, 0);
	}

	@Override
	public void eventProcess(String emitterURI, EventI e) throws Exception {
		// TODO Auto-generated method stub
	}
}


