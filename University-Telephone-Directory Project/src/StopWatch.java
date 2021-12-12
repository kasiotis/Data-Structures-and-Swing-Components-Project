
/**
 * StopWatch
 *
 */
public class StopWatch {
	
	private long start;
	private long now;
	
	public void start() {
        this.start = System.nanoTime();
    } 
	
	public void stop() {
        now = System.nanoTime();
    }
	
	public long elapsedTime() { // milliseconds
        return (now - start);
    }
}
