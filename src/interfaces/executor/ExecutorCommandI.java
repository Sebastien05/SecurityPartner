package interfaces.executor;

public interface ExecutorCommandI <T> {
	
	public void set(T o);
	public void execute();
}