/**
 * 
 * @author Fernando Faria
 * fvf215
 * 
 * This file is a Token object that represents every terminal in the program
 * 
 * Java 1.6
 * Operating System: Windows 7
 *
 */
public class Token {
	public static final int INVALID = -1, EOI = 0, ID = 1, INT = 2, FLOAT = 3, OP = 4, COMMA = 5, WORD = 6;
	
	public final int type;
	
	private int valueInt;
	private double valueFloat;
	private String valueId;
	private char valueOp;
	
	
	//for ints
	public Token(int type, int value) {
		this.type = type;
		valueInt = value;
	}
	
	//for doubles
	public Token(int type, double value) {
		this.type = type;
		valueFloat = value;
	}
	
	//for ids, keywords, and invalids
	public Token(int type, String value) {
		this.type = type;
		valueId = value;
	}
	
	//for operators
	public Token(int type, char value) {
		this.type = type;
		valueOp = value;
	}
	
	//for EOI and comma
	public Token(int type) {
		this.type= type;
	}
	
	
	//return the type
	public int getType() {
		return type;
	}
	
	
	/* the next 6 getter methods return their value if the type of this token matches the value type
	 * will throw an error if attempting to get another type of value 
	 * e.g.  type = INT, a call to any get method except for getValInt() will print an error and quit the program
	 */ 
	
	
	//int
	public int getValInt() {
		if(type == INT)
			return valueInt;
		System.err.println("not an int");
		System.exit(1);
		return -1;
	}
	
	//float
	public double getValFloat() {
		if(type == FLOAT)
			return valueFloat;
		System.err.println("not a float");
		System.exit(1);
		return -1;
	}
	
	//id
	public String getValId() {
		if(type == ID)
			return valueId;
		System.err.println("not an id");
		System.exit(1);
		return "";
	}
	
	//operator
	public char getValOp() {
		if(type == OP)
			return valueOp;
		System.err.println("not a symbol");
		System.exit(1);
		return '?';
	}
	
	//keyword
	public String getValKeyWord() {
		if(type == WORD)
			return valueId;
		System.err.println("not a keyword");
		System.exit(1);
		return "";
	}
	
	//invalid
	public String getValInvalid() {
		if(type == INVALID)
			return valueId;
		System.err.println("not an invalid");
		System.exit(1);
		return "";
	}
	
	
	//print this token with surrounding type
	public String toString() {
		switch(type) {
			case 0:
				return "EOI";
			case 1:
				return "<Id>"+valueId+"</Id>";
			case 2:
				return "<Int>"+valueInt+"</Int>";
			case 3:
				return "<Float>"+valueFloat+"</Float>";
			case 4:
				return "<Operator>"+valueOp+"</Operator>";
			case 5:
				return "<Comma>,</Comma>";
			case 6:
				return "<Keyword>"+valueId+"</Keyword>";
			default:
				return "INVALID";
		}
	}
}
