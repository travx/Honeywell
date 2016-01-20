package com.datastax.honeywell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class WorstOffenders {
	private List<Offender> offenders;
	private int max_offenders;
	private Offender min;
	
	public WorstOffenders(int max_offenders){
		this.max_offenders = max_offenders;
		offenders = new ArrayList<Offender>();
	}
	
	private void add_and_update_min(Offender o){
		offenders.add(o);
		if ((null == min) || (o.lessThan(min))){
			min = o;
		}
	}
	
	private void add_and_remove_min(Offender o){
		offenders.remove(min);
		offenders.add(o);
		Collections.sort(offenders);
		min = offenders.get(0);
	}
	
	public void add(Offender o){
		if (offenders.size() < max_offenders){
			this.add_and_update_min(o);
		}
		else if (min.lessThan(o)){
			this.add_and_remove_min(o);
		}
	}
	
	public void print(){
		for (int i=offenders.size()-1; i>=0; i--){
			offenders.get(i).print();
		}
	}
}
