package interfaces.rule;

import interfaces.event.EventI;

public interface EventMatcherRoomI 
{
	public boolean match(EventI event, String room);

}