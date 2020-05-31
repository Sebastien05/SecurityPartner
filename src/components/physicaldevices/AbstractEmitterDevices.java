package components.physicaldevices;

import java.util.Random;

import components.CEPBus;
import components.connectors.CEPBusManagementConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import ports.EventEmissionOutboundPort;
import ports.RegisterOutboundPort;

public abstract class AbstractEmitterDevices extends AbstractComponent {//pour presence detector
	
	protected String eeopURI;
	protected String ropURI;
	protected EventEmissionOutboundPort eeop;
	protected RegisterOutboundPort rop;
	
	Random random;
	
	protected int fixedTimeExecution;
	protected int fixedTimeStartExecution;
	protected int fixedDelay;
	protected String room;
	
	protected AbstractEmitterDevices(
			String eventEmissionOutboundPortURI,
			String registeredOutboundPortURI,
			int fixedTimeExecution,
			int fixedTimeStartExecution,
			int fixedDelay,
			String room
			)throws Exception{
		super(1, 0) ;
		this.eeopURI=eventEmissionOutboundPortURI;
		this.ropURI=registeredOutboundPortURI;
		
		this.fixedDelay=fixedDelay;
		this.fixedTimeExecution=fixedTimeExecution;
		this.fixedTimeStartExecution=fixedTimeStartExecution;

		this.random = new Random();
		this.room = room;
		
		this.init() ;
	}

	protected void	init() throws Exception
	{			
		// Port initialization 
		this.eeop = new EventEmissionOutboundPort(eeopURI, this) ;
		this.rop  = new RegisterOutboundPort(ropURI, this); 
		// Publish them
		this.eeop.publishPort();
		this.rop.publishPort();
		
		// connection with CEPBus inbound port manager for registration
		doPortConnection(rop.getPortURI(), CEPBus.INBOUND_PORT_MANAGEMENT_URI,
				CEPBusManagementConnector.class.getCanonicalName());
	}
	
	public void	start(String component) throws ComponentStartException
	{
		this.logMessage("starting" + component +" component.") ;
		super.start();
	}
	
	public abstract void execute() throws Exception;

	public void finalise() throws Exception {
		this.doPortDisconnection(this.eeop.getPortURI());
		this.doPortDisconnection(this.rop.getPortURI());
		super.finalise();
	}
	
	public void shutdown() throws ComponentShutdownException {
		try {
			this.eeop.unpublishPort();
			this.rop.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
}
