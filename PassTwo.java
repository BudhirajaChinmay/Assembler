package Assembler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PassTwo {


	String line, symbol, literal, opcode, vartype, assemblyop, addressfield;		// To store the parts of instruction 
	int location_counter, length, value;				
	
	// Different tables used
	
	SymbolTable stable;								
	LabelTable Labtable;
	literal_table littable;
	final int end_statement = -2;
	int flag,flag2;
	int p;
	String file;
		
	// Opcodes 
		Opcodes op = new Opcodes();
	
	// To store the code generated from the assembly language 
	final int maxCode = 12;
	byte code[] = new byte[maxCode];									
	
	// To store the memory address of the instruction'
	int[] memory = new int[12];
	
	public PassTwo( SymbolTable stable, LabelTable labelTable, literal_table literal_table, String file) throws IOException {

		this.stable = stable;
		this.Labtable = labelTable;
		this.littable = literal_table;
		location_counter = 0;
		this.file = file;
		flag = 0;
		flag2 = 0;
		p = 0;
	}
	
	// Checking comments 
	
	boolean checkcomment(String line) {
	
		if (line.startsWith("//")) {
			return true;
		}
		
		return false;
	}
		
	
	// Covert to binary for memory addresses
	
	public void convertbinary(int lc) {
		memory = new int[12];	
		
		int pointer = 0;
		while(lc!=0) {
			
			memory[pointer] = lc%2;
			lc = lc/2;
			pointer++;			
		}
		
	}
	
	
	// Checking label 
		boolean checkLabel(String line) {
			
			symbol = line.substring(0, 8);
			opcode = line.substring(8,11);
			
			
			if(op.getbinary(opcode) != null && !symbol.equals("        ") && !symbol.startsWith(" ")) {
				return true;
			}
			return false;
		}
		
		
	// Check Valid label
	
		boolean checkvalidlabel(String Line) throws InvalidFormat{
			
			symbol = line.substring(0, 8);
			if (symbol.matches("        ") || !symbol.startsWith(" ")) {
				return true;
			}
			else {
				throw (new InvalidFormat());
			}
		}
		
		
	// Checking Symbol for storing variables
		boolean checksymbol(String line) throws IllegalDatatype, UnknownSymbol, IllegalOpcode {
			
			symbol = line.substring(0, 8);
			vartype = line.substring(8,10);
			
			//  ************** ADD variable Types ****************
			//System.out.println(opcode);
			//System.out.println(vartype + " " + symbol + " " + opcode);
			if (vartype.equals("DC") && !symbol.equals("        ")) {
				return true;				
			}
			if (vartype.equals("DC") && symbol.equals("        ")) {
				//System.out.println(1);
				throw (new UnknownSymbol());
			}
			else if (!vartype.equals("DC") && opcode == null) {
				//System.out.println(1);
				throw (new IllegalOpcode());
			}
			
			return false;
		
		}
	
		
	// Checking opcode in the given line
		
		void checkopcode(String line){
			
			//System.out.println(location_counter);
			assemblyop = line.substring(8, 11);
			opcode = op.getbinary(assemblyop);
			
		}
		
		
	// Checking the validity of the address of the instruction or the data field
		
		boolean checkaddressfeild(String line) throws InvalidFormat {
			
			addressfield = line.substring(20, Math.max(21, line.length()));
			if (!addressfield.contains("//")) {
				if (op.getbinary(opcode) != null) {
					
					if (addressfield.startsWith(" ")) {
						throw (new InvalidFormat());
					}
				}
				return true;
			}
			
			return false;
		}
		
		
		String getaddressfeild(String line) throws LessArguments, ExcessArguments {
			
			int pointer = 0;
			
			String temp = "";
			
			int t = Math.min(line.length(), 27);
			String tempvalue = line.substring(20,t);
			while(pointer < line.length()-20 && tempvalue.charAt(pointer) != ' ' ) {
				temp = temp + tempvalue.substring(pointer,pointer+1);
				pointer++;
			}
			
			//System.out.println(line);
			//System.out.println(temp);
			if (temp.equals("")) {
				throw (new LessArguments());
			}
			//System.out.println(pointer);
			
			while(pointer < 8 && 20+pointer < line.length()) {
				
				if (line.charAt(20+pointer) == ' ') {
					
				}
				else {
					//System.out.println(line.charAt(pointer));
					throw (new ExcessArguments());
				}
				pointer++;
			}
			//System.out.println(line);
			//System.out.println(addressfield);
			
			return temp;
			
		}
		
		
	// Check validity of the full instruction line	
	boolean checkvalid(String line)throws Exception {
		
		//System.out.println(checkaddressfeild(line));
		checkopcode(line);
		if(checkvalidlabel(line) && opcode!=null && checkaddressfeild(line) ) {
			return true;
		}
		
		
		return false;
	}
		
	boolean addressLine(String line)throws IllegalDatatype, UnknownSymbol, IllegalOpcode {
		
		return checksymbol(line);
	}
	public void passtwo() throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader("/home/chinmay/eclipse-workspace/Assembler/src/Assembler/" + file));
		File f = new File("output.obj");
		if (!f.exists()) {
			f.createNewFile();
		}
	    BufferedWriter writer = new BufferedWriter(new FileWriter("/home/chinmay/eclipse-workspace/Assembler/src/Assembler/" + f.getName()));
	

	    line = reader.readLine();
	    while(line != null && line.length() != 0) {
			
	    	
			if (!checkcomment(line) && line.length() != 0) {
				
				try {

					
					if (line.substring(0, 3).equals("END")) {
						flag = 1;
						break;
					}
					if(line.length() <20) {
						throw (new InvalidFormat());
					}
					//System.out.println(location_counter);
					if (checkvalid(line)) {
						
						
						//System.out.println(100);
						convertbinary(location_counter);
						for (int i = 0; i < 8; i++) {
							//System.out.print(memory[7-i]);
							writer.write(Integer.toString(memory[7-i]));
						}
						//System.out.println();
						
						writer.write("    ");
						
						writer.write(opcode);
						
						writer.write("    ");
						
						addressfield = getaddressfeild(line);
						
						//System.out.println(addressfield + " " + stable.search(addressfield) + " " + Labtable.search(addressfield));
						if (stable.search(addressfield) != -1) {
							//System.out.println(stable.offset.get(stable.search(addressfield)));
							int temp = stable.offset.get(stable.search(addressfield));
							convertbinary(temp); 
							for (int i = 0; i < 8; i++) {
								//System.out.println(memory[7-i]);
								writer.write(Integer.toString(memory[7-i]));
							}
						}
						else if (Labtable.search(addressfield) != -1) {
							//System.out.println(1000);
							writer.write(Labtable.search(addressfield));
						}
						
						else if (littable.search(addressfield) != -1) {
							int l = Integer.parseInt(addressfield.substring(2, addressfield.length()-1));
							convertbinary(l); 
							for (int i = 0; i < 8; i++) {
								//System.out.print(memory[11-i]);
								writer.write(Integer.toString(memory[7-i]));
							}
						}
						else {
							//System.out.println(1);
							throw (new UnknownSymbol());
						}
						
						
						writer.newLine();
			
					}
					
					if(addressLine(line)) {
						
						convertbinary(location_counter);
						for (int i = 0; i < 8; i++) {
							//System.out.print(memory[7-i]);
							writer.write(Integer.toString(memory[7-i]));
						}
						
//						addressfield = line.substring(0, 1);
//						int temp = stable.offset.get(stable.search(addressfield));
//						convertbinary(temp); 
//						for (int i = 0; i < 8; i++) {
//							//System.out.print(memory[7-i]);
//							writer.write(Integer.toString(memory[7-i]));
//						}
						writer.write("    ");
						writer.write("    ");
						writer.write("    ");
						
						

						String temp = "";
						String tempvalue = line.substring(0, 8);
						int pp = 0;
						while(pp < 8 && tempvalue.charAt(pp) != ' ' ) {
							temp = temp + tempvalue.substring(pp,pp+1);
							pp++;
						}
						int pointer = stable.search(temp);
						
						
						if (pointer == -1) {
							//System.out.println(1);
							throw (new UnknownSymbol());
						}
						String temp2 = stable.value.get(pointer);
						int val = Integer.parseInt(temp2);
						convertbinary(val);
						for (int i = 0; i < 12; i++) {
							//System.out.print(memory[11-i]);
							writer.write(Integer.toString(memory[11-i]));
						}
						
						writer.newLine();;
					}
				}
				
				catch(Exception e) {
					System.out.println("Line Number : " + ((int)(p+1)));
					System.out.println(e);
					flag2 = 1;
				}
				location_counter++;
	
			}
			p++;
			line = reader.readLine();
		}
	    reader.close();
		writer.close();
		
		if (flag2 == 1) {
			//System.out.println(f.getAbsolutePath());
			try {
			    writer = new BufferedWriter(new FileWriter("/home/chinmay/eclipse-workspace/Assembler/src/Assembler/" + f.getName()));
			    writer.newLine();
			} catch (Exception e) {
				// TODO: handle exception
			}
			//System.out.println(f.exists());
		}
	    try {
	    	 if (flag == 0) {
	 			throw (new ENDMissing());
	 		}
	    }
	    catch(Exception e) {
	    	System.out.println(e);
	    }
	    
	}
	
	
}
