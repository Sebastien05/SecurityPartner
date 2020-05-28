package interfaces.component;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

/**
 * interface offerte qui regroupe les operations permetant d�enregistrer et desenregistrer les composants du systeme
 *
 */
public interface CEPBusManagementCI extends RequiredI, OfferedI{
	public String getEventReceptionInboundPortURI(String uri) throws Exception;
	public void registerEventReceptor(String uri, String inboundPortURI) throws Exception;
	public void unregisterEventReceptor(String uri) throws Exception;
	public void registerCommandExecutor(String uri, String inboundPortURI) throws Exception;
	public String getExecutorInboundPortURI(String executorURI) throws Exception;
	public void unregisterCommandExecutor(String uri) throws Exception;
}
