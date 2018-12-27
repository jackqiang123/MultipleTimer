package lianlu;

import java.util.LinkedList;
import java.util.List;

public class SingleTimer {
	public String projectName;
	public boolean isRunning;
	public List<TimeInterval> intervals;
	public TimeInterval curInterval;
	
	
	public SingleTimer(String name) {
		this.projectName = name;
		this.isRunning = false;
		this.intervals = new LinkedList<TimeInterval>();
	}
	
	public void startTimer() {
		if (this.isRunning) return;
		this.isRunning = true;
		this.curInterval = new TimeInterval(System.currentTimeMillis());
	}
	
	public void stopTimer() {
		if(!this.isRunning)
			return;
		
		this.isRunning = false;
		if (this.curInterval != null)
			this.curInterval.end = System.currentTimeMillis();
		
		this.intervals.add(this.curInterval);
	}
	
	public String toString() {
		int seconds = 0;
		for (TimeInterval tt : intervals) {
			seconds += tt.inSeconds();
		}
		
		seconds += (System.currentTimeMillis() - this.curInterval.start)/TimeInterval.msToSecond;
		
		return (int)seconds + " Seconds";
	}
	
	public int toSeconds() {
		int seconds = 0;
		for (TimeInterval tt : intervals) {
			seconds += tt.inSeconds();
		}
		
		if (this.isRunning)
			seconds += (System.currentTimeMillis() - this.curInterval.start)/TimeInterval.msToSecond;
		
		return seconds;
	}
}
