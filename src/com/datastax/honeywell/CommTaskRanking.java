package com.datastax.honeywell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommTaskRanking {
	private List<CommTask> commTasks;
	private int max_commtasks;
	private CommTask min;
	
	public CommTaskRanking(int max_commtasks){
		this.max_commtasks = max_commtasks;
		commTasks = new ArrayList<CommTask>();
	}
	
	public List<CommTask> getCommTasks(){
		return this.commTasks;
	}
	
	private void add_and_update_min(CommTask o){
		commTasks.add(o);
		if ((null == min) || (o.lessThan(min))){
			min = o;
		}
	}
	
	private void add_and_remove_min(CommTask o){
		commTasks.remove(min);
		commTasks.add(o);
		Collections.sort(commTasks);
		min = commTasks.get(0);
	}
	
	public void add(CommTask o){
		if (commTasks.size() < max_commtasks){
			this.add_and_update_min(o);
		}
		else if (min.lessThan(o)){
			this.add_and_remove_min(o);
		}
	}	
}
