package lianlu;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeInterval {
	public long start;
	public long end;
	public static long msToSecond = 1000;
	
	public TimeInterval(long start) {
		this.start = start;
	}
	
	public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return "[" + sdf.format(new Date(start)) + " to " + sdf.format(new Date(end)) + "]";
	}
	
	public int inSeconds() {
		return (int) ((this.end - this.start)/TimeInterval.msToSecond);
	}
}
