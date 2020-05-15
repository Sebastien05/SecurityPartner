package Events;



import interfaces.event.AbstractAtomicEvent;

public class Close extends AbstractAtomicEvent{
	
	private static final String URI_close= "Close";
	private static int cpt = 0;
	private int id;
	
	public Close () {
		super();
		id=cpt++;
	}
	
	@Override
	public String getURI() {
		return URI_close+"_"+this.id;
	}

	@Override
	public String getType() {
		return URI_close;
	}
}
