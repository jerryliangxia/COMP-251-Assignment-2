package Greed;

import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
	int deadline;
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline) {
		this.number = number;
		this.weight = weight;
		this.deadline = deadline;
	}
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) {
		// TODO Implement this
		
		// 0, if the two items are equivalent
		if(a1.deadline == a2.deadline && a1.weight == a2.weight) return 0;
		
		else if(a1.deadline == a2.deadline && a1.weight <= a2.weight) return 1;
		// 1, if a1 should appear before a2 in the list
		else if(a1.deadline > a2.deadline) return 1;
			
		//-1, if a2 should appear before a1 in the list
		else return -1;
	}
}
