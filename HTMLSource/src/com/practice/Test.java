package com.practice;

/*
 * Calculate square of a number without using *, / and pow()
 * 
 * 
 * 
 */


public class Test {

	
	public static void main(String[] args) throws Exception{
		
	}

	
	
	public static int days(int d1, int m1, int y1,
			int d2, int m2, int y2){
		
		int days=0;
		
		// add the days for the years in between
		for (int year=y1+1;year<y2;year++){
			if (year % 4 == 0){
				days += 366;
			}else{
				days += 365;
			}
		}
		
		// add the days for months in between
		for (int month=m1+1;month<=12;month++){
			days += daysInMonth(month);
		}
		
		for (int month=1;month<m2;month++){
			days += daysInMonth(month);
		}
		
		
		// add the days in current months
		days += daysInMonth(m1) - d1;
		days += d2;
		
		return days;
	}
	
	public static int daysInMonth(int month){
		return 30;
	}
	
	

}	