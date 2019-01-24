package Assembler;

import java.util.ArrayList;

public class SymbolTable {

	ArrayList<String> symbol = new ArrayList<String>(); 			// To store the label name
	ArrayList<Integer> offset = new ArrayList<Integer>();			// To store the offset value
	ArrayList<String> value = new ArrayList<String>();				// To store the value if the variable
	ArrayList<String> size = new ArrayList<String>();				// To store the size of the variable
	
	String tempsymbol;
	String tempvalue;
	String tempsize;
	String temp;
	
																									// ******Add the size of the variable***** 
	public void add(String line, int offset) {
		
		// Reading different parts of the instruction
		
		tempsymbol = line.substring(0, 8);
		tempsize = line.substring(8, 10);
		tempvalue = line.substring(20,Math.min(28, line.length()));
		
		
		int pointer = 0;
		
		
		
		// Adding Label Name
		
		temp = "";
		
		while(tempsymbol.charAt(pointer) != ' ') {
			temp = temp + tempsymbol.substring(pointer, pointer+1);			
			pointer++;
		}		
		
		symbol.add(temp);
		//System.out.println(symbol.get(symbol.size()-1));
		// Adding offset value
		
		this.offset.add(offset);
		
		// Adding value
		
		pointer = 0;
		
		temp = "";
		
		while(pointer < line.length()-20 && tempvalue.charAt(pointer) != ' ' ) {
			temp = temp + tempvalue.substring(pointer,pointer+1);
			pointer++;
		}
		
		value.add(temp);
		
		// Adding size
		
		
	}
	
	public int search(String symbol) {
		
		int pos = -1;
		for (int i = 0; i < this.symbol.size(); i++) {
			if (symbol.equals(this.symbol.get(i))) {
				pos = i;
				break;
			}
		}
		
		return pos;
	}
	
	public void print () {
		
		for (int i = 0; i < offset.size(); i++) {
			
			System.out.println(symbol.get(i) + " " + offset.get(i) + " " + value.get(i));
		}
	}
}
