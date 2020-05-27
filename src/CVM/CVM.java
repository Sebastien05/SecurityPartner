package CVM;

import components.CEPBus;
import components.connectors.CEPBusEventEmissionConnector;
import components.connectors.CEPBusManagementConnector;
import components.connectors.PresenceDetectedConnector;
import components.physicaldevices.PresenceDetector;
import components.physicaldevices.AlarmComponent;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

public class CVM extends AbstractCVM{

	// CepBus URIs
	protected static final String OUTBOUNDPORT_URI_CEPB = "cepoport";
	protected static final String INBOUNDPORT_URI_CEPB = "cepiport";
	
	// 
	protected static final String OUTBOUNDPORT_URI_PRESENCE = "poport";
	protected static final String INBOUNDPORT_URI_ALARM = "Aiport";


	public static final int LIFE_CYCLE_DURATION = 20000;
	
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
				new Object[] {INBOUNDPORT_URI_ALARM});

		String uriC = AbstractComponent.createComponent(
				CEPBus.class.getCanonicalName(),
				new Object[] {OUTBOUNDPORT_URI_CEPB});

		this.doPortConnection(uriC, OUTBOUNDPORT_URI_CEPB, INBOUNDPORT_URI_ALARM,
				PresenceDetectedConnector.class.getCanonicalName());
*/
		AbstractComponent.createComponent(
				AlarmComponent.class.getCanonicalName(),
				new Object[] {INBOUNDPORT_URI_ALARM});

		String uri = AbstractComponent.createComponent(
				PresenceDetector.class.getCanonicalName(),
				new Object[] {OUTBOUNDPORT_URI_PRESENCE});

		this.doPortConnection(uri, OUTBOUNDPORT_URI_PRESENCE, INBOUNDPORT_URI_ALARM,
				PresenceDetectedConnector.class.getCanonicalName());
		 

		super.deploy();	
	}
	public static void	main(String[] args)
	{
		try {
			CVM cvm = new CVM() ;
			cvm.startStandardLifeCycle(1000L);
			Thread.sleep(10000L) ;
			System.exit(0) ;
		} catch (Exception e) {
			throw new RuntimeException(e) ;
		}
	}

}
