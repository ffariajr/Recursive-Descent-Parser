
/**
 * 
 * @author Fernando Faria
 * fvf215
 * 
 * This file is the lexer that tokenizes each terminal in the input string and returns them one by one
 * adds a $ to the end of any input string to denote end of input
 * only throws an error and returns if an identifier length is greater than 50 characters
 * 
 * 
 * Java 1.6
 * Operating System: Windows 7
 *
 */
public class Lexer { 
	private String input;
	
	private int loc;
	private Token tok;
	
	//adds $ to end of input to denote end of input
	public Lexer(String inputString) {
		input = inputString + "$";
		loc = 0;
	}
	
	//start over
	public void reset() {
		loc = 0;
		tok = null;
	}
	
	//returns next token, and sets tok to the returned token, and loc is incremented to next char
	public Token nextToken() {
		tok = next();
		return tok;
	}
	
	//return next token, tok and loc remain unchanged
	public Token peek() {
		int tempLoc = loc;
		Token temp = next();
		loc = tempLoc;
		return temp;
	}
	
	/*
	 * look at loc, and check if whitespace.  if yes, increment loc and repeat.
	 * if number, then call int or float handler and increment loc
	 * if letter, then call id or word handler and increment loc
	 * if symbol, then call op or comma handler and increment loc
	 * if $, return EOI and dont move
	 * anything else, return invalid token
	 * 
	 * return what those functions return
	 * when they return, loc should be at the beginning of a new token or whitespace
	 * tok is unchanged
	 * 
	 */
	private Token next() {
		while(input.charAt(loc) == ' ')
			loc++;
		
		
		if((input.charAt(loc) >= 'a' && input.charAt(loc) <= 'z') || (input.charAt(loc) >= 'A' && input.charAt(loc) <= 'Z'))
			return letter();
		
		if(input.charAt(loc) >= '0' && input.charAt(loc) <= '9')
			return number();
		
		if(input.charAt(loc) == ',' || input.charAt(loc) == '=' || input.charAt(loc) == '<' || input.charAt(loc) == '>')
			return symbol();
		
		if(input.charAt(loc) == '$')
			return new Token(Token.EOI);
		
		return new Token(Token.INVALID, input.charAt(loc));
	}
	
	/*
	 * consumes all numbers, and if included 1 decimal and all numbers after that.
	 * first char that is not a number or point must not be a letter, or return invalid
	 * 
	 * if there is a point, there must be at least 1 number after it
	 */
	private Token number() {
		int integer = 0;
		
		
		while(input.charAt(loc) >= '0' && input.charAt(loc) <= '9') {
			integer *= 10;
			integer += Character.getNumericValue(input.charAt(loc));
			loc++;
		}
		
		int places = -1;
		if(input.charAt(loc) == '.') {
			places++;
			loc++;
		}
		
		
		int decimal = 0;
		
		while(input.charAt(loc) >= '0' && input.charAt(loc) <= '9') {
			decimal *= 10;
			decimal += Character.getNumericValue(input.charAt(loc));
			places++;
			loc++;
		}
		
		if((input.charAt(loc) >= 'a' && input.charAt(loc) <= 'z') || (input.charAt(loc) >= 'A' && input.charAt(loc) <= 'Z'))
			return new Token(Token.INVALID, input.charAt(loc++));			
			
		
		if(places == 0)
			return new Token(Token.INVALID, input.charAt(loc));			
		
		if(places > 0) 
			return new Token(Token.FLOAT, (integer*1.0) + (decimal*1.0)/Math.pow(10, places));
		
		
		return new Token(Token.INT, integer);
	}
	
	/*
	 * consumes all letters and numbers after the first letter, if any
	 * must not be longer than 50 letters and numbers
	 * 
	 * if the token is a keyword, will return a keyword token, else returns an id token
	 */
	private Token letter() {
		char[] s = new char[50];
		int base = loc;
		
		s[loc-base] = input.charAt(loc);
		loc++;
		
		while((input.charAt(loc) >= 'a' && input.charAt(loc) <= 'z') || (input.charAt(loc) >= 'A' && input.charAt(loc) <= 'Z') || (input.charAt(loc) >= '0' && input.charAt(loc) <= '9')) {
			s[loc-base] = input.charAt(loc);
			loc++;
			if(loc-base >= 50) {
				System.err.println("Error: letter(), identifier name is too large (>50). choose another name");
				System.exit(1);
			}
		}
		
		String t = (new String(s)).trim();
		
		if("SELECT".equals(t) || "FROM".equals(t) || "WHERE".equals(t) || "AND".equals(t))
			return new Token(Token.WORD, t);
		
		return new Token(Token.ID, t);
	}
	
	/*
	 * returns a token representing the operator or comma
	 */
	private Token symbol() {
		loc++;
		if(input.charAt(loc-1) == ',')
			return new Token(Token.COMMA);
		
		return new Token(Token.OP, input.charAt(loc-1));
	}

}
