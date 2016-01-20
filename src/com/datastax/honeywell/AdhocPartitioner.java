package com.datastax.honeywell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AdhocPartitioner {
	//Value to use as wildcard search
	private String WILDCARD = "*";
	
	public AdhocPartitioner(){
	}
	
	public AdhocPartitioner(String wildcard){
		WILDCARD = wildcard;
	}
	
	public Set<ArrayList<Object>> createPartitions(Object...objects){
		ArrayList<Object> attributes = new ArrayList<Object>();
		
		for(Object o:objects){
			attributes.add(o);
		}
		
		return createPartitions(attributes);
	}

	
	public Set<ArrayList<Object>> createPartitions(ArrayList<Object> attributes){		
		return Expand(attributes);
	}

	
	private Set<ArrayList<Object>> Expand(ArrayList<Object> inlist) {
		  Set<ArrayList<Object>> retset = new HashSet<ArrayList<Object>>();
		  int N = inlist.size();
		  
		  for (int i = 0; i < (1<<N); i++) {
		    ArrayList<Object> al = new ArrayList<Object>();
		    for (int j = 0; j < N; j++) {
		      if (1 == ((i >> j)&0x1))
		        al.add(inlist.get(j));
		      else
		        al.add(WILDCARD);
		    }
		    retset.add(al);
		  }
		  
		  return retset;
		}

}
