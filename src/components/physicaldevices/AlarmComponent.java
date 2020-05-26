package components.physicaldevices;

import components.interfaces.EventReceptionCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import interfaces.event.EventI;
import interfaces.executor.ExecutorCI;
import interfaces.executor.ExecutorCommandI;
import ports.AlarmeInboundPort;
import ports.PresenceDetectorInboundPort;
/**
 * un composant qui peut executer des commandes pour declencher ou arreter une
 * alarme aupres d�un gardien accompagnee d�un message d�alarme
 *
 */
@OfferedInterfaces(offered={ExecutorCI.class})
public class AlarmComponent 
extends AbstractComponent 		{
	
	protected AlarmeInboundPort alarmInp;
	protected String inboundPortURI;
	
	protected AlarmComponent(String alarmInboundPortURI, int nbThreads, int nbSchedulableThreads) throws Exception {
		super(alarmInboundPortURI, 1, 1);
		this.alarmInp = new AlarmeInboundPort(alarmInboundPortURI, this);
		this.alarmInp.publishPort();
	}
	
	protected AlarmComponent(String reflectionInboundPortURI)
			throws Exception
			{
				super(reflectionInboundPortURI, 1, 0) ;
				this.initialise() ;
			}

	protected void	initialise() throws Exception
	{
		/*this.alarmInp = this.createPort() ;
		this.alarmInp.publishPort() ;
		this.pdop = new PresenceDetectorOutboundPort(this) ;
		this.pdop.publishPort() ;*/
	}

	protected AlarmeInboundPort	createPort()
			throws Exception
	{
		return new AlarmeInboundPort(
				inboundPortURI, this) ;
	}

	//-------------------------------------------------------------------------
	// Component life-cycle
	//-------------------------------------------------------------------------

	public void execute(ExecutorCommandI command) {
		// TODO
		System.out.println("DRING DRING");
	}
	
}

