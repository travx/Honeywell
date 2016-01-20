package com.datastax.honeywell;


public class Offender  implements Comparable<Offender>{
	private String mac_id;
	private double metric;
	
	public Offender(String mac_id){
		this.mac_id = mac_id;
		this.metric = 0;
	}
	
	public void increment(double inc){
		this.metric=+inc;
	}
	
	public void increment(){
		increment(1);
	}
	
	public String getMac_id() {
		return mac_id;
	}
	public void setMac_id(String mac_id) {
		this.mac_id = mac_id;
	}
	public double getMetric() {
		return metric;
	}
	public void setMetric(double metric) {
		this.metric = metric;
	}
	
	public boolean lessThan(Offender o){
		return (this.getMetric() < o.getMetric());
	}
	
	public void print(){
		System.out.println("MAC: " + mac_id + "\t" + "METRIC: " + metric);
	}
	
	@Override
	public int compareTo(Offender o) {
		// Compare based on metric
		return Double.compare(this.getMetric(), o.getMetric());
	}
}
