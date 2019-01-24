package Assembler;

public class Error {

}


// Done
class FileNotFound extends Exception{
	
	public FileNotFound() {
		super("File Not Found In the System");
	}
}

// Done
class IllegalOpcode extends Exception{
	
	public IllegalOpcode() {
		super("Opcode Not Found");
	}
}

// Done
class UnknownSymbol extends Exception{
	
	public UnknownSymbol() {
		super("Nonexistent Symbol");
	}
}


// Done
class IllegalLiteralDeclaration extends Exception{

	public IllegalLiteralDeclaration() {
		super("Incorrect format in Literal Declaration");
	}
}

//Done
class MultipleDeclaration extends Exception{

	public MultipleDeclaration() {
		super("Symbol been declared more than once");
	}
}

class LessArguments extends Exception{

	public LessArguments() {
		super("Not enough Arguments for concerned Opcode");
	}
}

class ExcessArguments extends Exception{

	public ExcessArguments() {
		super("Too many Arguments for concerned Opcode");
	}
}

//Done
class ENDMissing extends Exception{

	public ENDMissing() {
		super("END statement not found");
	}
}


// Done
class InvalidFormat extends Exception{
	
	public InvalidFormat() {
		super("Illegal Format");
	}
}

//Done
class bitOverflow extends Exception{
	
	public bitOverflow() {
		super("8-Bit Overflow");
	}
}

//Done
class IllegalDatatype extends Exception{
	
	public IllegalDatatype()
	{
		super("Unidentified Data Type");
	}
}