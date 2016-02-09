package com.datastax.honeywell;

public class CommTask implements Comparable<CommTask>{
	private String mac_id;
	private int commTasks;
	private double duration;

	public CommTask(String mac_id){
		this.mac_id = mac_id;
		this.commTasks = 0;
		this.duration = 0;
	}		

	public void increment(double inc){
		this.duration=+inc;
		this.commTasks++;
	}
	
	public double getAverageCommTaskDuration(){
		return duration/commTasks;
	}
	
	public boolean lessThan(CommTask o){
		return (this.getDuration() < o.getDuration());
	}	
		
	public String getMac_id() {
		return mac_id;
	}

	public void setMac_id(String mac_id) {
		this.mac_id = mac_id;
	}

	public int getCommTasks() {
		return commTasks;
	}

	public void setCommTasks(int commTasks) {
		this.commTasks = commTasks;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	@Override
	public int compareTo(CommTask o) {
		return Double.compare(this.getDuration(), o.getDuration());
	}
}
