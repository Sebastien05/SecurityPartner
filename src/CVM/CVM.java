package CVM;

import java.util.ArrayList;

import Events.Presence;
import Events.TemperatureReading;
import Events.Window;
import Rules.HeatingOptimizerRule;
import components.CEPBus;
import components.connectors.CEPBusEventEmissionConnector;
import components.connectors.CEPBusManagementConnector;
import components.correlators.HeatingOptimizerCorrelator;
import components.correlators.IntrusionCorrelator;
import components.correlators.LightOptimizerCorrelator;
import components.correlators.managingelement.PortReferencer;
import components.physicaldevices.PresenceDetector;
import components.physicaldevices.ThermostatComponent;
import components.physicaldevices.WindowController;
import components.physicaldevices.WindowDetector;
import components.physicaldevices.AlarmComponent;
import components.physicaldevices.LightComponent;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

public class CVM extends AbstractCVM{
	
	//////////////////////
	// Emitter Component//
	//////////////////////
	
	// Presence Detector
	private static final String OUTBOUNDPORT_URI_PRESENCE = "opURIp";
	
	private static final String OUTBOUNDPORT_URI_PRESENCE1 = "opURIp1";
	private static final String OUTBOUNDPORT_URI_PRESENCE2 = "opURIp2";
	
	/////////////////////////
	// Multi Task Component//
	/////////////////////////
	
	// Window controller
	private static final String OUTBOUNDPORT_URI_WINDOW = "oURIw";
	private static final String INBOUNDPORT_URI_WINDOW = "ipRIw";
	
	private static final String OUTBOUNDPORT_URI_WINDOW1 = "oURIw1";
	private static final String OUTBOUNDPORT_URI_WINDOW2 = "oURIw2";
	
	private static final String INBOUNDPORT_URI_WINDOW1 = "ipRIw1";
	private static final String INBOUNDPORT_URI_WINDOW2 = "ipRIw2";

	
	// Thermostat
	private static final String OUTBOUNDPORT_URI_THERMOS = "oURIt";
	private static final String INBOUNDPORT_URI_THERMOS = "ipURIt";	
	
	///////////////////////
	// Executor Component//
	///////////////////////
	
	// Executor component inboundPort
	private static final String INBOUNDPORT_URI_ALARM_ROOM1 = "iporta1";
	private static final String INBOUNDPORT_URI_ALARM_ROOM2 = "iporta2";

    private static final String INBOUNDPORT_URI_LIGHTNING_ROOM1 = "iportl";
	
	////////////////////////
	// Correlaor Component//
	////////////////////////
    
	// Correlator ports Intrusion
	private static final String INBOUNDPORT_URI_INTRUSION = "ipURIi";
	private static final String REGISTER_URI_INTRUSION = "rURIi";

	// Correlator ports HeatingOptimizer
	private static final String INBOUNDPORT_URI_HOPTI = "ipURIh";
	private static final String REGISTER_URI_HOPTI = "opURIh";
	
    // Correlator ports LightOptimizerCorrelator
    private static final String INBOUNDPORT_URI_LIGHT = "ipURIl";
    private static final String REGISTER_URI_LIGHT = "rURIl";
	
	
	public static final int LIFE_CYCLE_DURATION = 15000;
	
	public CVM() throws Exception {
		super();
	}

	@Override
	public void			deploy() throws Exception
	{	
		// script define when an event occurs for execute emitter and multitask
		
		// No event in LIFE_CYCLE_DURATION
		ArrayList<Integer> noscript = new ArrayList<>();
		
		// One event in LIFE_CYCLE_DURATION
		ArrayList<Integer> script1 = new ArrayList<>();
		script1.add(2);
		ArrayList<Integer> script2 = new ArrayList<>();
		script2.add(5);
		
		// Several events in LIFE_CYCLE_DURATION
		ArrayList<Integer> script3 = new ArrayList<>();
		script3.add(1); script3.add(6);
		ArrayList<Integer> script4 = new ArrayList<>();
		script4.add(3); script4.add(9);

		String room1 = "Chambre";
		String room2 = "Salon  ";
				
		////////////
		// CEPBus //
		////////////
		
		// CEPBus Component
		AbstractComponent.createComponent(
				CEPBus.class.getCanonicalName(),
				new Object[] {});

		assert room1 != room2;
		/*
		//////////////
		// Example 1// test intrusion script
		//////////////
		
		////////////////
		// Components //
		////////////////
		
		// PresenceDetector Component Emitter Device
		AbstractComponent.createComponent(
				PresenceDetector.class.getCanonicalName(),
				new Object[] {OUTBOUNDPORT_URI_PRESENCE1,
						LIFE_CYCLE_DURATION/1000, 1000, 1000, room1, script1});

		AbstractComponent.createComponent(
				PresenceDetector.class.getCanonicalName(),
				new Object[] {OUTBOUNDPORT_URI_PRESENCE2,
						LIFE_CYCLE_DURATION/1000, 1000, 1000, room2, script2});
		
		
		// WindowController Component Multi Task Device
		AbstractComponent.createComponent(
				WindowController.class.getCanonicalName(),
				new Object[] {INBOUNDPORT_URI_WINDOW1,OUTBOUNDPORT_URI_WINDOW1,
						LIFE_CYCLE_DURATION/1000, 1000, 1000, room1, script1});
		
		AbstractComponent.createComponent(
				WindowController.class.getCanonicalName(),
				new Object[] {INBOUNDPORT_URI_WINDOW2,OUTBOUNDPORT_URI_WINDOW2,
						LIFE_CYCLE_DURATION/1000, 1000, 1000, room2, script2});
		
		
		// Alarm Component Executor Device
		// In the AlarmComponent class you can change its delay duration 
		// before any correlator turns it off. If any correlator break downs
		// the alarm will continue to sound 
		AbstractComponent.createComponent(
				AlarmComponent.class.getCanonicalName(),
				new Object[] {INBOUNDPORT_URI_ALARM_ROOM1, room1});
		
		AbstractComponent.createComponent(
				AlarmComponent.class.getCanonicalName(),
				new Object[] {INBOUNDPORT_URI_ALARM_ROOM2, room2});
		
		/////////////////
		// Correlators //
		/////////////////
		
		// Specify which uri port to listen in order to receive these events
		ArrayList<String> urisToListen = new ArrayList<>();
		urisToListen.add(OUTBOUNDPORT_URI_PRESENCE1);
		urisToListen.add(OUTBOUNDPORT_URI_WINDOW1);
		urisToListen.add(OUTBOUNDPORT_URI_PRESENCE2);
		urisToListen.add(OUTBOUNDPORT_URI_WINDOW2);
		
		// Specify which event in room will associate which inboundPortURI from executor
		PortReferencer<String> pr = new PortReferencer<>();
		// Add connections for alarm in room1
		pr.addPort(room1, Presence.class.getSimpleName(), INBOUNDPORT_URI_ALARM_ROOM1);
		pr.addPort(room1, Window.class.getSimpleName(), INBOUNDPORT_URI_ALARM_ROOM1);
		// Add connections for alarm in room2
		pr.addPort(room2, Presence.class.getSimpleName(), INBOUNDPORT_URI_ALARM_ROOM2);
		pr.addPort(room2, Window.class.getSimpleName(), INBOUNDPORT_URI_ALARM_ROOM2);
		
		AbstractComponent.createComponent(
				IntrusionCorrelator.class.getCanonicalName(),
				new Object[] {INBOUNDPORT_URI_INTRUSION,
						REGISTER_URI_INTRUSION, urisToListen, pr});		
		
		*/
		/*
		//////////////
		// Example 2//  test light optimizer script
		//////////////

		////////////////
		// Components //
		////////////////
		
		// PresenceDetector Component Emitter Device
		AbstractComponent.createComponent(
				PresenceDetector.class.getCanonicalName(),
				new Object[] {OUTBOUNDPORT_URI_PRESENCE,
						LIFE_CYCLE_DURATION/1000, 1000, 1000, room1, script3});
		
		// In the LightComponent class you can change its delay duration
	    AbstractComponent.createComponent(
            LightComponent.class.getCanonicalName(),
            new Object[] {INBOUNDPORT_URI_LIGHTNING_ROOM1,room1});
        
        
        /////////////////
        // Correlators //
        /////////////////
        
        // Specify which uri port to listen in order to receive these events
        ArrayList<String> urisToListen = new ArrayList<>();
        urisToListen.add(OUTBOUNDPORT_URI_PRESENCE);
        
        // Specify which event in room will associate which inboundPortURI from executor
        PortReferencer<String> pr = new PortReferencer<>();
        pr.addPort(room1, Presence.class.getSimpleName(), INBOUNDPORT_URI_LIGHTNING_ROOM1);
		
        AbstractComponent.createComponent(
        LightOptimizerCorrelator.class.getCanonicalName(),
        new Object[] {INBOUNDPORT_URI_LIGHT,
                REGISTER_URI_LIGHT, urisToListen, pr});
		 
		*/
		
		/**/
		//////////////
		// Example 3//
		//////////////
		
		int setupdTemperature = 20;
		int detectedTemperature = 13;
		
		// Thermostat Component MultiTask Device
		AbstractComponent.createComponent(
				ThermostatComponent.class.getCanonicalName(),
				new Object[] {INBOUNDPORT_URI_THERMOS, OUTBOUNDPORT_URI_THERMOS,
						LIFE_CYCLE_DURATION/1000, 1000, 1000, room1, noscript, 
						setupdTemperature, detectedTemperature});
		
		// WindowDetector Component Multi Task Device
		AbstractComponent.createComponent(
				WindowDetector.class.getCanonicalName(),
				new Object[] {OUTBOUNDPORT_URI_WINDOW,
						LIFE_CYCLE_DURATION/1000, 1000, 1000, room1, script2});
		
		
		/////////////////
		// Correlators //
		/////////////////
		
		// Specify which uri port to listen in order to receive these events
		ArrayList<String> urisToListen = new ArrayList<>();
		urisToListen.add(OUTBOUNDPORT_URI_THERMOS);
		urisToListen.add(OUTBOUNDPORT_URI_WINDOW);
		
		// Specify which event in room will associate which inboundPortURI from executor
		PortReferencer<String> pr = new PortReferencer<>();
		pr.addPort(room1, WindowDetector.class.getSimpleName(), INBOUNDPORT_URI_THERMOS);
		pr.addPort(room1, TemperatureReading.class.getSimpleName(), INBOUNDPORT_URI_THERMOS);

		
		AbstractComponent.createComponent(
				HeatingOptimizerCorrelator.class.getCanonicalName(),
				new Object[] {INBOUNDPORT_URI_HOPTI,
						REGISTER_URI_HOPTI, urisToListen, pr});		
		
		/**/
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
