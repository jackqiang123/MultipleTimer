package lianlu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimerColumn extends JPanel{
	public SingleTimer timer;
	
	private JLabel workingDuration;
	private CountingThread ct;
	
	public JButton startButton;
	public JButton stopButton;
	
	public static int activeHashCode;
	
	public TimerColumn(String projectName) {
		this.timer = new SingleTimer(projectName);
		
		this.setProjectName();
		
		this.startButton = this.addStartButton();
		this.stopButton = this.addStopButton();
		
		this.workingDuration = new JLabel();
		this.ct = new CountingThread();
		this.ct.start();

		
		this.add(startButton);
		this.add(stopButton);
		this.add(this.workingDuration);
		
	}
	
	public void setProjectName() {
		JLabel label = new JLabel(this.timer.projectName);
		this.add(label);
	}
	
	public JButton addStartButton() {
		JButton startButton = new JButton("Start"); 
		
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				TimerColumn.activeHashCode = startButton.hashCode();
				timer.startTimer();	
				ct.stopped = false;
			}
			
		});
		
		return startButton;
	}
	
	public JButton addStopButton() {
		JButton stopButton = new JButton("Pause"); 
		//TimerColumn.activeHashCode = 0;
		
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				timer.stopTimer();	
				ct.stopped = true;
			}
			
		});
		
		return stopButton;
	}
	
	private class CountingThread extends Thread{

		public boolean stopped = true;
		
		private CountingThread() {
			setDaemon(true);
		}
		
        @Override  
        public void run() {  
            while (true) {  
                if (!stopped) {  
                	workingDuration.setText(timer.toString());  
                }  
                
                if (activeHashCode != startButton.hashCode()) {
                	timer.stopTimer();
                	ct.stopped = true;
                }
                
                
                try {  
                    sleep(500);  // 1毫秒更新一次显示
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                    System.exit(1);  
                }  
                

            }  
        }  
	}

	public double computeHours() {
		int seconds = this.timer.toSeconds();
		return seconds/1.0/3600;
	}

	public List<String> getIntervals() {
		List<String> ls = new LinkedList();
		for (TimeInterval ti : this.timer.intervals) {
			ls.add(ti.toString());
		}
		
		if (this.timer.isRunning) 
		{
			TimeInterval curOne = new TimeInterval(this.timer.curInterval.start);
			curOne.end = System.currentTimeMillis();
			ls.add(curOne.toString());
		}
		
		return ls;
	}
}
