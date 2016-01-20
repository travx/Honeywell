package com.datastax.honeywell;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Loader {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length < 2){
			System.out.println("Specify NODE and PATH_TO_FILE.");
			System.exit(1);			
		}
		
		String keyspace = "tccdev";
		
		String node = args[0];
		String csvpath = args[1];
		//String node = "172.16.232.175";
		//String csvpath = "/Users/tprice/Desktop/Honeywell/Sample_Data.csv";
		
		int counter=0;
		
		DataStaxCluster db = new DataStaxCluster(node, keyspace);
		
		try {
			FileReader csvreader = new FileReader(csvpath);
			Iterable<CSVRecord> csvrecords = CSVFormat.EXCEL.withAllowMissingColumnNames().parse(csvreader);
			
			for (CSVRecord csvrecord : csvrecords){
				UIData uidata = new UIData(csvrecord);
				db.writeRecord(uidata);
				counter++;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot locate CSV File. Check path.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot read data in CSV File.");
		}
		
		System.out.println("Imported " + counter + " records.");
		System.exit(0);
		
	}

}
