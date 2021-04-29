package Greed;

import java.util.ArrayList;
import java.util.Arrays;

public class GreedyTester {
	//Copy and paste this in GreedyTester.java, then call the method test() in your main as such: 
	//test(number of tests you want to make, maximum number of homeworks that you want for the tests);
	//ex: test(1000, 8);
	//In the example^ the maximum number of homeworks in the weight and deadline arrays will be 8 (the min is set as 5)
	//Warning, if you put the input for maximum number of homework at 15+,
	//you should decreased the number of tests by a lot, the brute force is slow as fuck

	private static void test(int numOfTests, int numOfHw){
		if(numOfHw < 5) numOfHw = 0;
		else numOfHw = numOfHw-5;
		ArrayList<Integer> unsuccessful = new ArrayList<Integer>();
		for(int i = 0; i < numOfTests; i++){
			//yeet("Starting test "+ i);
			int numberOfHW = (int) (5+numOfHw*Math.random());
			int size = (int) (numberOfHW/2+numberOfHW*Math.random());
			int[] weight = new int[size];
			int[] deadline = new int[size];
			for(int j = 0; j < size; j++){
				deadline[j] = (int) (1 + (int) ((size*Math.random()))*Math.random());
				weight[j] = (int) (1+99*Math.random());
			}
			HW_Sched schedules =  new HW_Sched(weight, deadline, weight.length);
			int [] results = new int[schedules.lastDeadline];
			Arrays.fill(results, -1);
			boolean[] done = new boolean[weight.length];
			Arrays.fill(done, false);
			//yeet("Computing brute force solution...");
			results = brute(weight,deadline,0,results,results, done);
			//yeet("Finished computing brute force solution");
			//yeet("Computing greedy solution...");
			int[] res = schedules.SelectAssignments();
			//yeet("Finished computing greedy solution");
			if(isWrong(deadline,res)) yeet("Greedy is wrong, something was placed after its deadline");
			if(isWrong(deadline,results)) yeet("Brute force is wrong, something was placed after its deadline");
			if(sum(results,weight) > sum(res,weight)){
				yeet("Test "+i+" unsuccessful");
				yeet("The sum of brute force is bigger");
				System.out.println("The deadlines: "+Arrays.toString(deadline));
				System.out.println("The weights: "+Arrays.toString(weight));
				yeet("Results: ");
				if(isWrong(deadline,res)) yeet("Greedy is wrong, something was placed after its deadline");
				if(isWrong(deadline,results)) yeet("Brute force is wrong, something was placed after its deadline");
				yeet("Greedy "+Arrays.toString(res) + " sum = "+ sum(res, weight));
				yeet("Brute force "+Arrays.toString(results)+ " sum = "+ sum(results, weight));
				yeet(" ");
				unsuccessful.add(i);
				continue;
			}
			if(sum(results,weight) < sum(res,weight)){
				yeet("Test "+i+" unsuccessful");
				yeet("The sum of greedy is bigger");
				System.out.println("The deadlines: "+Arrays.toString(deadline));
				System.out.println("The weights: "+Arrays.toString(weight));
				yeet("Results: ");
				if(isWrong(deadline,res)) yeet("Greedy is wrong, something was placed after its deadline");
				if(isWrong(deadline,results)) yeet("Brute force is wrong, something was placed after its deadline");
				yeet("Greedy "+Arrays.toString(res) + " sum = "+ sum(res, weight));
				yeet("Brute force "+Arrays.toString(results)+ " sum = "+ sum(results, weight));
				yeet(" ");
				unsuccessful.add(i);				
			}
			//yeet("Test "+i+" successful");
			//yeet("");
		}
		if(!unsuccessful.isEmpty()) yeet("List of unsuccessful tests: "+unsuccessful.toString());
		else yeet("All tests passed");
	}
	private static boolean isWrong(int[] deadline,int[] result){
		for(int i = 0; i<result.length; i++){
			if(result[i]<=-1) continue;
			if(deadline[result[i]] <= i){
				return true;
			}
		}
		return false;
	}
	private static int[] brute(int[] weights, int[] deadlines, int index, int[] result, int[] current, boolean[] done){
		if(index == result.length){
			return result;
		}
		int[] cur = copy(current);
		for(int i = 0; i < weights.length; i++){
			if(done[i]) continue;
			if(deadlines[i]<=index) continue;
			cur[index] = i;
			done[i] = true;
			if(sum(cur, weights) > sum(result, weights)) result = copy(cur);
			result = brute(weights,deadlines,index+1,result,cur, done);
			if(sum(cur, weights) > sum(result, weights)) result = copy(cur);
			cur[index] = -1;
			done[i] = false;
		}
		return result;
	}
	private static int sum(int[] result, int[] weights){
		int val = 0;
		for(int i = 0; i < result.length; i++){
			if(result[i]>=0)
			val+=weights[result[i]];
		}
		return val;
	}
	public static void yeet(Object o){
		System.out.println(o);
	}
	public static int[]copy(int[] arr){
		int[] cp = new int[arr.length];
		for(int i = 0; i < arr.length; i++){
			cp[i] = arr[i];
		}
		return cp;
	}
		
	public static void main(String[] args) {
		
		//This is the typical kind of input you will be tested with. The format will always be the same
		//Each index represents a single homework. For example, homework zero has weight 23 and deadline t=3.
		int[] weights = new int[] {24, 83, 87, 81, 34, 75}; 
		int[] deadlines = new int[] {2, 1, 1, 4, 1, 4};
		int m = weights.length;
		
		//This is the declaration of a schedule of the appropriate size
		HW_Sched schedule =  new HW_Sched(weights, deadlines, m);
		int[] res = schedule.SelectAssignments();	// sum = 243

		int[] weights1 = new int[] {36, 15, 8, 42, 49, 16}; 	// sum = 93
		int[] deadlines1 = new int[] {2, 1, 3, 1, 2, 1};
		int m1 = weights1.length;
		
		//This is the declaration of a schedule of the appropriate size
		HW_Sched schedule1 =  new HW_Sched(weights1, deadlines1, m1);
		int[] res1 = schedule1.SelectAssignments();

		int[] weights2 = new int[] {56, 46, 76, 43, 13}; 	// sum = ?
		int[] deadlines2 = new int[] {2, 1, 2, 1, 1};
		int m2 = weights2.length;
		
		//This is the declaration of a schedule of the appropriate size
		HW_Sched schedule2 =  new HW_Sched(weights2, deadlines2, m2);
		int[] res2 = schedule2.SelectAssignments();
		
		int[] w3 = new int[] {97, 48, 57, 98, 15}; 	// sum = 170, bf = 252
		int[] d3 = new int[] {1, 1, 3, 3, 3};
		int m3 = w3.length;
		
		//This is the declaration of a schedule of the appropriate size
		HW_Sched s3 =  new HW_Sched(w3, d3, m3);
		int[] res3 = s3.SelectAssignments();
		//This call organizes the assignments and outputs homeworkPlan
		test(10,5);
		//System.out.println(Arrays.toString(res));
	}
		
}
