# AssemblerCAOS Project: Assembler
File By File Documentation
Chinmay: 2017274 , Rudraroop Ray: 2017311
Syntax
  8 Spaces before opcode, may include the variable name or label name
  3 space for operator
  9 spaces
  8 spaces for the operand
  Single line comments starting from “//”
  Literals defined as “=a”
Files
1. Opcodes.java
This file is made for the access and cross-referencing of all the given opcodes.
Each opcode is composed of two data elements, namely, the representation of
opcode in Machine Language and the representation of the opcode in Assembly
Language. We store the data elements in separate ArrayLists to promote ease of
access. This storage works as follows:
If the first opcode is “CLA” the machine language representation for which
is “0000” we put “CLA” into the first index of the assembly ArrayList and
“0000” into the first index of the machine Language ArrayList.
The Opcodes class contains two data elements which are the aforementioned
ArrayLists. The constructor for the class simply fills the data elements up with the
assembly opcodes and their machine language counterparts. There is only one
function in the class which is getbinary(). This takes a String as input, compares it
to all indices of the assembly opcode arrayList and if a match is found, returns the
corresponding Machine language opcode from the parallel ArrayList.
2. LabelTable.javaThis file is made so that we can create a table for the Labels encountered in our
first pass. The storage of the two data elements of each of the labels is done via
two parallel ArrayLists (the same as the rest of the files). The arrayLists for Label
names and their corresponding offset values are the only data elements of the
LabelTable class.
We have a function enter_new_label(String L, int offset) to add a label to our
labeltable when we have already established that line L has a label in it. The offset
value is supplied to this function externally by the ILC value from our actual first
pass program (firstpass.java is documented below).
The functioning of this function is simple. Label name occurs between string
index 0 and 8, thus we make this our substring and work with it. Clearly, the
label name needn’t occupy all of this substring, necessitating further
shortening of our substring. We make a pointer which stops at the index at
which we find our first empty (space) character and shorten the string to this
point and add it to the label name ArrayList. If the pointer doesn’t stop, we
take the entire (0,8) substring as our label and put it into said ArrayList.
Apart from the above, LabelTable class contains a printing function used by the
programmers for error identification.
3. SymbolTable.java
This is the same as the LabelTable file, except this helps us create a table for the
Symbols encountered in our first pass. Each symbol has 4 data elements, thus we
have 4 parallel ArrayLists (symbol, offset, value, size). Apart from these data
elements, we have 4 other data elements which we use to keep track of things
(tempsymbol, tempsize, tempvalue, temp).
The main function in the SymbolTable class is the add(String L, int offset)
function. The purpose of this function is similar to that of enter_new_label() in
LabelTable class. The working is slightly different as instead of parsing the
substrings on encountering a space character we initialize the temp variables as
empty strings and keep adding the next encountered character to these until the
first space is encountered. The temp variables are then added to their
corresponding ArrayLists.
The only other function in this class is the search() function which takes a String
parameter, compares it to all the symbol names in the array list, and returns the
appropriate array index.
4. LiteralTable.javaThis is once again is nearly the same as all the above classes. It helps us make a
table of all the literals we encounter in our first pass.
Once we know a line has a literal in it we use the function enter_new_literal(String
L) and omit the “=” in the beginning of the literal and use the same pointer
technique we have used in labeltable to stop when we encounter a space
character. We add this literal name to our singular ArrayList containing literal
names.
We have a search() function in this which works the same as the search() function
in SymbolTable.java.
We have another function which we use at the end of the first pass to assign
addresses to the labels using ILC values.
5. passOne.java
This contains the passOne class which, obviously, performs the first pass in the
assembly process. There are a few data variables, including the location counter
initialized at zero, an object of type Labeltable, Opcode, Literaltable, etc. Each
function in the passOne class and their use is outlined in this section
boolean checkcomment(String line)
this function checks if the line starts with “//” if it does, we shouldn’t process it
as a line of code as it is just a comment. So we return true, else we return true
boolean checksymbol(String line)
this function checks if the line contains a variable declaration. If it does, it
contains the keywords DW or DS in the 8,10 substring of the line, and we return
true, else false. This helps us invoke the SymbolTable class and its associated
function for adding a symbol to the symbol table.
boolean checkLabel(String line)
this function checks if line contains a label. The way to see if it does is if 8,11
substring is an opcode, and the 0,8 substring doesn’t start with a space
character.
boolean checkliteral(String line)
this checks if a line contains a literal (literal starts with “=”)
void passone()
this is the main function. It uses a while loop to go through the input text file line
by line until it encounters a null line at which point the execution stops. We
reassign the line variable to the next line in the text file at the end of the whileloop body. The loop works with an if condition which checks if the line has no
length or if the line is a comment. If not a set of if conditions exist which check
things. For example it checks if the line has a label and invokes the label table
object to process the line and add label into it if it does have a label. It does the
same to check if the line contains a literal or symbol and breaks out of the loop
if the line contains an END command. After the while loop body is over, the
value of the Instruction Location Counter is used to assign addresses to the
literals in the literal table.
6. PassTwo.java
This file contains PassTwo class which performs the second pass of the
assembly process. Its data elements are broadly the same as those in passOne
however it also contains arrays to store the machine language translated code
and the memory addresses of the instructions. The following functions are also
included in the PassTwo class.
boolean checkcomment(String line)
same as that in the first pass
public void convertbinary(int lc)
converts the passed integer into binary using the memory array which is a
data element of the PassTwo class.
boolean checkLabel(String line)
same as that in first pass
boolean checkvalidlabel(String line)
checks if the label contained in the line is valid
boolean checksymbol(String line)
same as that in first pass
checkopcode(String line)
checks if there exists a valid opcode in the given line
boolean checkaddressfield(String line)checks if the address field is valid i.e. if the line contains a “//” somewhere
in the substring which is supposed to be its address field, it isn’t a valid
address field.
String getaddressfield(String line)
returns the address field substring of the line.
boolean checkvalid(String line)
This checks the entire line’s validity using all the smaller functions
previously defined.
public void passtwo()
this is what actually carries out the second pass line by line for the input
file and puts the assembled machine language instructions into an output
file. Once again this runs using a while loop. Each line’s validity is checked
and this is then translated using the convertbinary, addressfield and other
functions. This finishes the second pass.
7. Error.java
class FileNotFound :- If file is invaled
class IllegalOpcode :- If Opcode is not valid
class UnknownSymbol :- If Symbol is not found in any of the Label or symbol
table
class IllegalLiteralDeclaration :- Illegal format of literals.
class MultipleDeclaration :- Multiple Declarations of a variable or label
class LessArguments :- Less arguments provided for opcode
class ExcessArguments :- Excess Arguments provided for opcodes
class ENDMissing :- End Missing from the code
class bitOverflow :- if the result is large for the no. of bits in machine, here 12 bits
class InvalidFormat :- Invalid format for the instruction
class IllegalDatatype :- Datatype for the variable is not valid
