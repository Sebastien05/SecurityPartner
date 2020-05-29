package interfaces.component;

import components.physicaldevices.AlarmComponent;
import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

public interface ExecutorCI extends OfferedI, RequiredI{
	
	public void execute(ExecutorCommandI<AlarmComponent> command) throws Exception;
}
