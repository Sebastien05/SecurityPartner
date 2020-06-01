package CVM;

import java.util.ArrayList;

import Events.Presence;
import Events.Window;
import components.CEPBus;
import components.connectors.CEPBusEventEmissionConnector;
import components.connectors.CEPBusManagementConnector;
import components.correlators.IntrusionCorrelator;
import components.correlators.managingelement.PortReferencer;
import components.physicaldevices.PresenceDetector;
import components.physicaldevices.WindowController;
import components.physicaldevices.AlarmComponent;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

public class CVM extends AbstractCVM{

	// Emitter Component
	private static final String OUTBOUNDPORT_URI_PRESENCE = "opURIp";
	private static final String REGISTER_URI_PRESENCE = "rURIp";

	private static final String OUTBOUNDPORT_URI_WINDOW = "oURIw";
	private static final String INBOUNDPORT_URI_WINDOW = "ipRIw";
	private static final String REGISTER_URI_WINDOW = "rURIw";

	// Executor component inboundPort
	private static final String INBOUNDPORT_URI_ALARM_ROOM1 = "iporta";

	// Correlator ports
	private static final String OUTBOUNDPORT_URI_INTRUSION = "opURIi";
	private static final String INBOUNDPORT_URI_INTRUSION = "ipURIi";
	private static final String REGISTER_URI_INTRUSION = "rURIi";

	protected String cEPBus;
	protected String presenceDetector;
	protected String windowController;
	protected String alarmComponent;
	protected String intrusionCorrelator;
	
	public static final int LIFE_CYCLE_DURATION = 15000;

	public CVM() throws Exception {
		super();
	}

	@Override
	public void			deploy() throws Exception
	{	
		/*AbstractComponent.createComponent(
				CEPBus.class.getCanonicalName(),
				new Object[] {INBOUNDPORT_URI_CEPB});

		String uri = AbstractComponent.createComponent(
				PresenceDetector.class.getCanonicalName(),
				new Object[] {OUTBOUNDPORT_URI_PRESENCE});

		this.doPortConnection(uri, OUTBOUNDPORT_URI_PRESENCE, INBOUNDPORT_URI_CEPB,
				CEPBusConnector.class.getCanonicalName());
		 */
		/*
		AbstractComponent.createComponent(
				AlarmComponent.class.getCanonicalName(),
				new Object[] {INBOUNDPORT_URI_ALARM_ROOM1});

		String uriC = AbstractComponent.createComponent(
				CEPBus.class.getCanonicalName(),
				new Object[] {OUTBOUNDPORT_URI_CEPB});

		this.doPortConnection(uriC, OUTBOUNDPORT_URI_CEPB, INBOUNDPORT_URI_ALARM_ROOM1,
				PresenceDetectedConnector.class.getCanonicalName());
		 */
		String room1 = "Room 1";

		////////////
		// CEPBus //
		////////////

		// CEPBus Component
		this.cEPBus =
				AbstractComponent.createComponent(
						CEPBus.class.getCanonicalName(),
						new Object[] {});
		assert	this.isDeployedComponent(this.cEPBus) ;

		////////////////
		// Components //
		////////////////

		// PresenceDetector Component Emitter Device
		this.presenceDetector= 
				AbstractComponent.createComponent(
						PresenceDetector.class.getCanonicalName(),
						new Object[] {OUTBOUNDPORT_URI_PRESENCE, REGISTER_URI_PRESENCE,
								LIFE_CYCLE_DURATION/1000, 1000, 1000, room1});
		assert	this.isDeployedComponent(this.presenceDetector) ;

		// WindowController Component Multi Task Device
		this.windowController=
				AbstractComponent.createComponent(
						WindowController.class.getCanonicalName(),
						new Object[] {INBOUNDPORT_URI_WINDOW,OUTBOUNDPORT_URI_WINDOW,
								REGISTER_URI_WINDOW, LIFE_CYCLE_DURATION/1000, 1000, 1000, room1});
		assert	this.isDeployedComponent(this.windowController) ;

		// Alarm Component Executor Device
		this.alarmComponent=
				AbstractComponent.createComponent(
						AlarmComponent.class.getCanonicalName(),
						new Object[] {INBOUNDPORT_URI_ALARM_ROOM1});
		assert	this.isDeployedComponent(this.alarmComponent) ;

		/////////////////
		// Correlators //
		/////////////////

		// Specify which uri port to listen in order to receive these events
		ArrayList<String> urisToListen = new ArrayList<>();
		urisToListen.add(OUTBOUNDPORT_URI_PRESENCE);
		urisToListen.add(OUTBOUNDPORT_URI_WINDOW);

		// Specify which event in room will associate which inboundPortURI from executor
		PortReferencer<String> pr = new PortReferencer<>();
		pr.addPort(room1, Presence.class.getSimpleName(), INBOUNDPORT_URI_ALARM_ROOM1);
		pr.addPort(room1, Window.class.getSimpleName(), INBOUNDPORT_URI_ALARM_ROOM1);

		this.intrusionCorrelator=
				AbstractComponent.createComponent(
						IntrusionCorrelator.class.getCanonicalName(),
						new Object[] {INBOUNDPORT_URI_INTRUSION,
								REGISTER_URI_INTRUSION, urisToListen, pr});
		assert	this.isDeployedComponent(this.intrusionCorrelator) ;

		super.deploy();	
	}
	@Override
	public void				shutdown() throws Exception
	{
		assert	this.allFinalised() ;
		// any disconnection not done yet can be performed here
		
		super.shutdown();
	}

	public static void	main(String[] args)
	{
		try {
			CVM cvm = new CVM() ;
			cvm.startStandardLifeCycle(LIFE_CYCLE_DURATION);
			Thread.sleep(2000L) ;
			System.exit(0) ;
		} catch (Exception e) {
			throw new RuntimeException(e) ;
		}
	}

}
