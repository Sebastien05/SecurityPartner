package interfaces.component;

import fr.sorbonne_u.components.interfaces.RequiredI;
import interfaces.event.EventI;

/**
 * interface requise qui regroupe les operations par lesquelles il va transmettre les evenements aux entites qui doivent les traiter
 *
 */
public interface EventEmissionCI  extends RequiredI{
	
	public void sendEvent(String emitterURI, 
					String destinationURI, EventI e) throws Exception;
	
	public void multisendEvent(String emitterURI, 
					String [] destinationURIs, EventI e) throws Exception;
}
