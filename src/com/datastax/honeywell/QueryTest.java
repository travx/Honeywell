package com.datastax.honeywell;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryTest {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		DataStaxCluster db = new DataStaxCluster("LCAStageDSE2Ubuntu02", "travistest");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		String device_type = "Communicating Vision Pro"; 
		String firmware_version = "03.00.07.00";
		String monitored_group = "*";
		String os_index = "*";
		String power_type = "*";
		Date startdate = df.parse("2015-01-01");
		Date enddate = df.parse("2015-12-31");
		String metric = "CommunicationAlert"; 
		int numberOffenders = 100;
		
		WorstOffenders wo = db.getWorstOffenders(device_type, firmware_version, monitored_group, os_index, power_type, startdate, enddate, metric, numberOffenders);
		wo.print();
		
		System.exit(0);
	}

}
