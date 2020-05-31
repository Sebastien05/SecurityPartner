package components.correlators;

import java.util.ArrayList;

import components.correlators.managingelement.PortReferencer;
import interfaces.event.EventI;

public class AbnormalActivityCorrelator extends AbstractCorrelator{

	protected AbnormalActivityCorrelator(
		String eripURI,
		String ropURI,
		ArrayList<String> urisToListen,
		PortReferencer<String> iprURIs
		)
	throws Exception 
	{
		super(eripURI, ropURI, urisToListen, iprURIs);
	}

	@Override
	public void initBis() {
		// TODO Add specific Rules
		
	}

	@Override
	public void executeBis() {
		// TODO (optional)
		
	}

	@Override
	public void eventProcess(String emitterURI, EventI e) throws Exception {
		// TODO how to process a new received event
		
	}

}
