package com.datastax.honeywell;

public class BuildDeviceIndex {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataStaxCluster db = new DataStaxCluster("LCAStageDSE2Ubuntu02", "travistest");
		
		db.buildDeviceIndex();
		
		System.out.println("Done");
		System.exit(0);
	}

}
