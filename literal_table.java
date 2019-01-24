package Assembler;

import java.util.*;

public class literal_table {

	public ArrayList<String> Literal=new ArrayList<String>();
	public ArrayList<Integer> Address=new ArrayList<Integer>();	
	
	//Function to Enter a new literal name once we know that line L has a literal in it
	public void enter_new_literal(String L) {
		
		int opstart=20; //start index of operand part of instruction
		int opend=Math.min(28, L.length());	 //end index of operand part
		
		String s1=L.substring(opstart,opend); //substring of L that contains literal name
		
		//clearly the literal's name may not take up the whole substring's length
		int ptr2=-1;
		int i=1;
		
		while(ptr2==-1 && i<=s1.length()) {
			if(s1.charAt(i)=='"') {
				ptr2=i;
			}
			i++;
		}
		
		String xn;	//Actual Label Name after cutdown
		
		if(ptr2==-1) 
			xn=s1;  //no need to cut down if ptr2 still equals -1
		else {
			xn=s1.substring(1, ptr2); //start after equals sign and end at first occurrence of space
		}
		
		int ctr=-1; //start with assumption that literal is a new one
		
		for(int j=0; i<this.Literal.size(); j++) {
			if(xn.equals(this.Literal.get(j)))
				ctr=j;	//if literal already exists in table
		}
		
		if(ctr==-1)
			this.Literal.add("\"" + xn+"\"");
	}
	
	public int address_assignment(int lc) {
		
		for (int i = 0; i < Literal.size(); i++) {
			Address.add(lc);
			lc++;
		}
		
		return lc;
	}
	
	public int search(String symbol) {
		
		int pos = -1;
		for (int i = 0; i < this.Literal.size(); i++) {
			if (symbol.equals(this.Literal.get(i))) {
				pos = i;
				break;
			}
		}
		
		return pos;
	}
	public void print () {
		
		for (int i = 0; i < Literal.size(); i++) {
			
			System.out.println(Literal.get(i));
		}
	}
}
