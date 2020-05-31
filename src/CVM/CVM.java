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
		AbstractComponent.createComponent(
				CEPBus.class.getCanonicalName(),
				new Object[] {});

		////////////////
		// Components //
		////////////////
		
		// PresenceDetector Component Emitter Device
		AbstractComponent.createComponent(
				PresenceDetector.class.getCanonicalName(),
				new Object[] {OUTBOUNDPORT_URI_PRESENCE, REGISTER_URI_PRESENCE,
						LIFE_CYCLE_DURATION/1000, 1000, 1000, room1});
		
		// WindowController Component Multi Task Device
		AbstractComponent.createComponent(
				WindowController.class.getCanonicalName(),
				new Object[] {INBOUNDPORT_URI_WINDOW,OUTBOUNDPORT_URI_WINDOW,
						REGISTER_URI_WINDOW, LIFE_CYCLE_DURATION/1000, 1000, 1000, room1});
		
		// Alarm Component Executor Device
		AbstractComponent.createComponent(
				AlarmComponent.class.getCanonicalName(),
				new Object[] {INBOUNDPORT_URI_ALARM_ROOM1});
		
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
		
		AbstractComponent.createComponent(
				IntrusionCorrelator.class.getCanonicalName(),
				new Object[] {INBOUNDPORT_URI_INTRUSION,
						REGISTER_URI_INTRUSION, urisToListen, pr});
		
		super.deploy();	
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
