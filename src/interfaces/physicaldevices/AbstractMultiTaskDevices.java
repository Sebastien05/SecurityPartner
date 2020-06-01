
package interfaces.physicaldevices;

import java.sql.Timestamp;
import java.util.Date;

import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import interfaces.component.ExecutorCommandI;
import interfaces.component.ReceptorCommandI;
import ports.ExecutorInboundPort;

public abstract class AbstractMultiTaskDevices 
extends AbstractEmitterDevices
implements ReceptorCommandI
{	
	protected ExecutorInboundPort ComponentInp;
	protected Timestamp lastSwitch;
	protected String componentInboundPortURI;

	protected AbstractMultiTaskDevices(
		String componentInboundPortURI,
		String eventEmissionOutboundPortURI,
		int fixedTimeExecution,
		int fixedTimeStartExecution,
		int fixedDelay,
		String room
		)
	throws Exception{
		super(eventEmissionOutboundPortURI,
				fixedTimeExecution,
				fixedTimeStartExecution,
				fixedDelay,room);
		this.componentInboundPortURI = componentInboundPortURI;
		this.initBIS();
	}

	public void initBIS() throws Exception {
		this.ComponentInp = new ExecutorInboundPort(componentInboundPortURI, this);
		this.ComponentInp.publishPort();
		this.lastSwitch = new Timestamp((new Date()).getTime());
	}

	public void processExecute(ExecutorCommandI command) {
		command.set(this);
		command.execute();
	}
	
	public void shutdown() 
			throws ComponentShutdownException {
		try {
			this.ComponentInp.unpublishPort();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.shutdown();
	}

}