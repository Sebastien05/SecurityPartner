package InterfaceEvent;

import java.io.Serializable;

public interface AtomicEventI extends EventI{
	Serializable putproperty(String name, Serializable value);
	Serializable removeProperty(String name);
}
