package Assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class passOne {


	String line, symbol, literal, opcode,vartype;		// To store the parts of instruction 
	int location_counter, length, value;				
	
	// Different tables used
	
	SymbolTable stable;								
	LabelTable Labtable;
	literal_table littable;
	final int end_statement = -2;
	// Input file 
	BufferedReader reader;
	int pointer;
	// Opcodes 
	Opcodes op = new Opcodes();
	
	public passOne(BufferedReader reader, SymbolTable s, LabelTable l, literal_table t, File f) {
		stable = s;
		Labtable = l;
		littable = t;
		this.reader = reader;
		location_counter = 0;
		pointer = 0;
	}
	
	
	// Checking comments 
	boolean checkcomment(String line) {
		
		if (line.startsWith("//")) {
			return true;
		}
		
		return false;
	}
	
	// Checking Symbol for storing variables
	boolean checksymbol(String line){
		
		symbol = line.substring(0, 8);
		vartype = line.substring(8,10);
		
		if (vartype.equals("DC")) {
			return true;
		}
		
		return false;
	}
	
	
	// Checking label 
	boolean checkLabel(String line){
		
		symbol = line.substring(0, 8);
		opcode = line.substring(8,11);
		
		if(op.getbinary(opcode) != null && !symbol.equals("        ") && !symbol.startsWith(" ")) {
			return true;
		}
		
		return false;
	}
	
	// Checking Literal
	boolean checkLiteral(String line) throws IllegalLiteralDeclaration{
		
		
		literal = line.substring(20,Math.min(27,line.length()));
		//System.out.println(literal);
		
		if (literal.charAt(0) == '"') {
			if (literal.charAt(1) == '=') {
				return true;
			}
			else {
				throw (new IllegalLiteralDeclaration());
			}
		}
		
		return false;
		
	}
	
	
	// Function for passone
	void passone() throws IOException {
		
		line  = reader.readLine();
		
		while(line != null) {
		
			pointer++;
			//System.out.println(line);
			if (!checkcomment(line) && line.length()!=0) {
				
				if (line.substring(0, 3).equals("END")) {
					break;
				}
				
				try {
					
					if (line.length() <20) {
						throw (new InvalidFormat());
					}
					if (checksymbol(line)) {
						//System.out.println(111);
						
						symbol = line.substring(0, 8);
						
						String tempsymbol = symbol;
						String temp = "";
						
						int pp = 0;
						while(tempsymbol.charAt(pp) != ' ') {
							temp = temp + tempsymbol.substring(pp, pp+1);
							pp++;
						}		
					
						try {
							if (stable.search(temp) != -1) {
								throw (new MultipleDeclaration());
							}
							stable.add(line, location_counter);
						}
						catch(Exception e){
							System.out.println("Line Number : " + ((int)(location_counter+1)));
							System.out.println(e);
						}
					}
					
					if (checkLabel(line)) {
						
						symbol = line.substring(0, 8);
						
						String tempsymbol = symbol;
						String temp = "";
						
						int pp = 0;
						while(tempsymbol.charAt(pp) != ' ') {
							temp = temp + tempsymbol.substring(pp, pp+1);
							pp++;
						}	
						try {
							if (Labtable.search(temp) != -1) {
								throw (new MultipleDeclaration());
							}
							Labtable.enter_new_label(line, location_counter);
						}
						catch(Exception e){
							System.out.println("Line Number : " + ((int)(location_counter+1)));
							System.out.println(e);
						}
						
					}
					
					if (checkLiteral(line)) {
						littable.enter_new_literal(line);
					}
				}
				
				catch(Exception e) {
					
					if (e.getClass().getName().equals("Assembler.InvalidFormat")) {
						
					}
					else {
						System.out.println("Line Number " + ((int)pointer));
						System.out.println(e);
						
					}
					
				}
//				if (checksymbol(line)) {
//					//System.out.println(111);
//					stable.add(line, location_counter);
//				}
//				
//				if (checkLabel(line)) {
//					Labtable.enter_new_label(line, location_counter);
//				}
//				
//				if (checkLiteral(line)) {
//					littable.enter_new_literal(line);
//				}
				
				location_counter++;
				
				//System.out.println(line.substring(0, 3));
				
			}
			
			line = reader.readLine();
		}
		 
		location_counter = littable.address_assignment(location_counter);
	}
	
	
}
