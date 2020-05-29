package components.physicaldevices;

import java.sql.Timestamp;
import java.util.Date;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import interfaces.component.ExecutorCommandI;
import interfaces.component.ReceptorCommandI;
import ports.ExecutorInboundPort;

public abstract class AbstractExecutorDevices
extends AbstractComponent
implements ReceptorCommandI
{

	protected ExecutorInboundPort ComponentInp;
	protected String inboundPortURI;
	protected String state;
	protected Timestamp lastSwitch;

	protected AbstractExecutorDevices(int nbThreads, int nbSchedulableThreads) {
		super(nbThreads, nbSchedulableThreads);
	}

	protected void	init(String alarmInboundPortURI) throws Exception
	{
		this.ComponentInp = new ExecutorInboundPort(alarmInboundPortURI, this);
		this.ComponentInp.publishPort();
		this.lastSwitch = new Timestamp((new Date()).getTime());
	}

	/*
	 * Method to receive command and execute it
	 */
	public void processExecute(ExecutorCommandI command) {
		command.set(this);
		command.execute();
	}

	public void finalise() throws Exception {
		super.finalise();
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
	
	public abstract void execute() throws InterruptedException;

}
