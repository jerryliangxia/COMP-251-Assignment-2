package Greed;

import java.util.*;

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int size) {
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to the assignment 
	 * that will be done at time i.
	 */
	public int[] SelectAssignments() {
		//TODO Implement this
		
		//Sort assignments
		// order now based on deadline - increasing
		Collections.sort(Assignments, new Assignment());

		// If homeworkPlan[i] has a value -1, it indicates that the 
		// i'th timeslot in the homeworkPlan is empty
		//homeworkPlan contains the homework schedule between now and the last deadline
		int[] homeworkPlan = new int[lastDeadline];
		for (int i = 0; i < homeworkPlan.length; ++i) {
			homeworkPlan[i] = -1;
		}
		// once sorted
		// go through the whole list, and fill it up!
		int i = 0;
		int j = 0;
		while(i < homeworkPlan.length) {	// while the whole list hasn't been filled
			// get the current assignment			
			Assignment cur = this.Assignments.get(j);
			homeworkPlan[i] = cur.number;
			if(i == homeworkPlan.length-1) break;
			if(j == this.Assignments.size()-1) break;
			while(j < this.Assignments.size()) {

				Assignment toCheck = this.Assignments.get(j);
				 if(toCheck.deadline == cur.deadline) {
					 if(cur.compare(cur, toCheck) == 0) {
						 j++;
						 continue;
					 }
					 int hwSpots = homeworkPlan.length-i-1;
					 int asgnSpots = this.Assignments.size() - this.Assignments.indexOf(toCheck)-1;
					 
					 if(hwSpots == asgnSpots && 
							 this.Assignments.get(j+1).deadline != cur.deadline) { 
						 		j++; 
						 		break; // if there's still space and one entry
							 }
					 else if(j == this.Assignments.size()-1 || 
							 this.Assignments.get(this.Assignments.size()-1).deadline == toCheck.deadline) break;
					 else {
						 j++; 
						 continue;
					 }
				 }	// because afterwards all are lower
				 if(cur.weight <= toCheck.weight && (j != this.Assignments.size()-1)) { // else if
					 
					 int hwSpots = homeworkPlan.length-i-1;
					 int asgnSpots = this.Assignments.size() - this.Assignments.indexOf(toCheck);
					 
					 if(hwSpots >= asgnSpots) break;
					 else {
						 if(j < this.Assignments.size()-1 && cur.weight >= 
								 this.Assignments.get(this.Assignments.indexOf(toCheck)+1).weight) {
							 
							 break;
						 }
						 homeworkPlan[i] = toCheck.number; j++; // if the weight is more
						 break;
					 }
					 
				 }
				else break;
			}
			i++;
		}
		
		return homeworkPlan;
	}
	
	
}
	



