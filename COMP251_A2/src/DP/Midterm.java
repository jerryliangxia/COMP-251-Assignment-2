package DP;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Midterm {
	private static int[][] dp_table;
	private static int[] penalization;
	

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int chairs;
		try {
			chairs = Integer.valueOf(reader.readLine());
			penalization = new int[chairs];
			for (int i=0; i< chairs; i++) {
				penalization[i] = Integer.valueOf(reader.readLine());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int answer = lost_marks(penalization);
		System.out.println(answer);
	}
	
	public static int lost_marks(int[] penalization) {
		//To Do => Please start coding your solution here
		
		// fill up table (BCs)
		dp_table= new int[penalization.length][penalization.length];
		return opt(dp_table, penalization, 0, 0);
	}
    

	private static int opt(int[][] dp_table, int[] penalization, int jump, int pos) {

		// TODO Auto-generated method stub
		int value = 0;
		if(pos==penalization.length-1) return penalization[penalization.length-1]; // base case
		
		int forwards = 0, backwards = 0;
		try {
			if(dp_table[jump+1][pos+jump+1]==0) {
				forwards = opt(dp_table, penalization, jump+1,pos+jump+1);
			} else forwards = dp_table[jump+1][pos+jump+1];
		} catch(Exception e) {
			forwards = Integer.MAX_VALUE;
		}
		
		try {
			if(pos == 0 && jump == 0) backwards = Integer.MAX_VALUE;
			else if(dp_table[jump][-1*jump+pos]==0) {
				backwards = opt(dp_table, penalization, jump, -1*jump+pos);
			} else backwards = dp_table[jump][-1*jump+pos];
		} catch(Exception e) {
			backwards = Integer.MAX_VALUE;
		}
		
		value=Math.min(forwards, backwards);
		try {
			dp_table[jump+1][pos+jump+1]=forwards;
		} catch(Exception e) {}
		
		try {
			dp_table[jump][-1*jump+pos]=backwards;
		} catch(Exception e) {}
		
		if(value==Integer.MAX_VALUE) dp_table[jump][pos]=value;
		if(backwards < forwards) {
			return penalization[pos]+backwards;
		}
		else if(forwards < backwards){
			if(pos == 0 && jump == 0) return forwards;
			else return penalization[pos]+forwards;
		}
		return dp_table[jump][pos];
		
		}
}
