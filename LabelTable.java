package Assembler;

import java.util.*;

public class LabelTable {
	public ArrayList<String> Label= new ArrayList<String>();
	public ArrayList<Integer> Offset= new ArrayList<Integer>();
	
	//Function to add Label into table once we already know that a line has a label in it
	public void enter_new_label(String L, int offset) {
		//Label name
		String lname=L.substring(0, 8);
		
		//cutting down lname string
		int ptr=-1;
		int i=0;
		
		while(ptr==-1 && i<=lname.length()){
			if(lname.charAt(i)==' ') {//as soon as first space is encountered
				ptr=i;
			}
			i++;
		}
		
		
		if(ptr==-1)
			Label.add(lname);	//Adding Label Name as it is
		else {
			String lxname=lname.substring(0, ptr);
			Label.add(lxname); //Adding cutdown label name without redundant spaces
		}
		
		Offset.add(offset); //Adding Offset Value
	}
	
	public int search(String symbol) {
		
		int pos = -1;
		for (int i = 0; i < this.Label.size(); i++) {
			if (symbol.equals(this.Label.get(i))) {
				pos = i;
				break;
			}
		}
		
		return pos;
	}

	public void print () {
		
		for (int i = 0; i < Offset.size(); i++) {
			
			System.out.println(Label.get(i) + " " + Offset.get(i));
		}
	}
}
