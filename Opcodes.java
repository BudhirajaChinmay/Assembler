package Assembler;

import java.util.ArrayList;

public class Opcodes {

	ArrayList<String> op = new ArrayList<String>();
	ArrayList<String> assemblyCode = new ArrayList<String>();
	
	public Opcodes() {
		
		// 1. CLEAR ACCUMULATOR
		op.add("0000");
		assemblyCode.add("CLA");
		
		// 2. LOAD INTO ACCUMULATOR FROM ADDRESS 
		op.add("0001");
		assemblyCode.add("LAC");
		
		// 3. STORE ACCUMULATOR CONTENTS INTO ADDRESS
		op.add("0010");
		assemblyCode.add("SAC");
		
		// 4. Add address contents to accumulator contents
		op.add("0011");
		assemblyCode.add("ADD");
		
		// 5. Subtract address contents from accumulator contents
		op.add("0100");
		assemblyCode.add("SUB");
		
		// 6. Branch to address if accumulator contains zero
		op.add("0101");
		assemblyCode.add("BRZ");
		
		// 7. Branch to address if accumulator contains negative value
		op.add("0110");
		assemblyCode.add("BRN");
		
		// 8. Branch to address if accumulator contains positive value
		op.add("0111");
		assemblyCode.add("BRP");
		
		// 9. Read from terminal and put in address
		op.add("1000");
		assemblyCode.add("INP");
		
		// 10. Display value in address on terminal
		op.add("1001");
		assemblyCode.add("DSP");
		
		// 11. Multiply accumulator and address contents
		op.add("1010");
		assemblyCode.add("MUL");
		
		// 12. Divide accumulator contents by address content. Quotient in R1 and remainder in R2
		op.add("1011");
		assemblyCode.add("DIV");
		
		// 13. Stop execution
		op.add("1100");
		assemblyCode.add("STP");
		
		
		
	}
	
	public String getbinary(String opcode) {
		
		for (int i = 0; i < op.size(); i++) {
			
			if (assemblyCode.get(i).equals(opcode)) {
				return op.get(i);
			}
		}
		
		return null;
	}

}
