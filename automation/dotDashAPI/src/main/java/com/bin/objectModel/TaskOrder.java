package com.bin.objectModel;

import java.util.Comparator;

public class TaskOrder implements Comparator<Task>{

		public int compare(Task o1, Task o2) {
			int firstValue;
			int secondValue;
		// TODO Auto-generated method stub
	    if(o1.getDueDate() == null || o1.getDueDate().contentEquals("")) {
	    	firstValue = 0;
	    }else {
	    	firstValue = Integer.parseInt(o1.getDueDate());
	    }
	    if(o2.getDueDate() == null || o2.getDueDate().contentEquals("")) {
	    	secondValue = 0;
	    }else {
	    	secondValue = Integer.parseInt(o2.getDueDate());
	    }
	    int diffInInt = secondValue-firstValue;
		return diffInInt;
	}

}
