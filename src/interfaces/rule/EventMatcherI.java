package interfaces.rule;

import interfaces.event.EventI;

public interface EventMatcherI 
{
	public boolean match(EventI e);
}