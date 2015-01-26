/**
 * 
 * @author Fernando Faria
 * fvf215
 * 
 * This file is the parser that takes in a program string and verifies the syntax, and prints a parse tree representation of the program
 * will throw errors if the program does not have correct syntax
 * 
 * Java 1.6
 * Operating System: Windows 7
 *
 */
public class Parser {
	private Lexer l;
	
	private int indents;
	
	public Parser(String input) {
		l = new Lexer(input);
	}
	
	//makes a new parse tree
	public void run() {
		l.reset();
		indents = 0;
		query();
	}
	
	//print indentations to format tree
	private void indent() {
		for(int x = 0; x < indents; x++)
			System.out.print("\t");
	}
	
	//prints a custom error and quits.  takes in the expected token type and found token type
	private void error(int expected, int err) {
		System.err.println("\nSyntax error: expecting "+TokenType(expected)+"; saw "+TokenType(err));
		System.exit(1);
	}
	
	//returns the string name of the token type
	private String TokenType(int type) {
		switch(type) {
			case 0:
				return "EOI";
			case 1:
				return "Id";
			case 2:
				return "Int";
			case 3:
				return "Float";
			case 4:
				return "Operator";
			case 5:
				return "Comma";
			case 6:
				return "Keyword";
			default:
				return "Invalid";
		}
	}
	
	/*
	 * consumes all terminals and non-terminals that satisfy this query
	 */
	private void query() {
		System.out.println("<Query>");
		indents++;
		
		//consumes SELECT command
		
		Token temp = l.nextToken();
		if(temp.type == Token.WORD && temp.getValKeyWord().equals("SELECT")) {
			indent();
			System.out.println(temp);
		}
		else
			error(Token.WORD, temp.type);
		
		//IdList after SELECT
		IdList();
		
		//consumes FROM command
		temp = l.nextToken();
		if(temp.type == Token.WORD && temp.getValKeyWord().equals("FROM")) {
			indent();
			System.out.println(temp);
		}
		else
			error(Token.WORD, temp.type);
		
		//IdList after WHERE
		IdList();
		
		//consumes optional WHERE command if present
		temp = l.nextToken();
		if(temp.type == Token.WORD && temp.getValKeyWord().equals("WHERE")) {
			indent();
			System.out.println(temp);
			
			//CondList after WHERE
			CondList();
		}
		
		indents--;
		System.out.println("</Query>");
	}
	
	/*
	 * consumes all terminals and non-terminals that satisfy this IdList
	 */
	private void IdList() {
		indent();
		System.out.println("<IdList>");
		indents++;
		
		//consumes the first required ID
		Token temp = l.nextToken();
		if(temp.type == Token.ID) {
			indent();
			System.out.println(temp);
		}
		else
			error(Token.ID, temp.type);
		
		
		//loops while there are more commas followed by IDs, and consumes them
		while(l.peek().type == Token.COMMA) {
			temp = l.nextToken();
			indent();
			System.out.println(temp);
			
			temp = l.nextToken();
			if(temp.type == Token.ID) {
				indent();
				System.out.println(temp);
			}
			else
				error(Token.ID, temp.type);
		}
		
		indents--;
		indent();
		System.out.println("</IdList>");
	}
	
	/*
	 * consumes all terminals and non-terminals that satisfy this CondList
	 */
	private void CondList() {
		indent();
		System.out.println("<CondList>");
		indents++;
		
		//consumes first Cond
		Cond();
		
		
		//loos while there are more ANDs and Conds, and consumes them
		while(l.peek().type == Token.WORD && l.peek().getValKeyWord().equals("AND")) {
			indent();
			System.out.println(l.nextToken());
			
			Cond();
		}
		
		indents--;
		indent();
		System.out.println("</CondList>");
	}
	
	/*
	 * consumes all terminals and non-terminals that satisfy this Cond
	 */
	private void Cond() {
		indent();
		System.out.println("<Cond>");
		indents++;
		
		
		//consume first ID
		Token temp = l.nextToken();
		if(temp.type == Token.ID) {
			indent();
			System.out.println(temp);
		}
		else
			error(Token.ID, temp.type);
		
		//consume operator
		temp = l.nextToken();
		if(temp.type == Token.OP) {
			indent();
			System.out.println(temp);
		}
		else
			error(Token.OP, temp.type);
		
		//consumes term
		Term();
		
		indents--;
		indent();
		System.out.println("</Cond>");
	}
	
	/*
	 * consumes all terminals and non-terminals that satisfy this Term
	 */
	private void Term() {
		indent();
		System.out.println("<Term>");
		indents++;
		
		
		//consumes id or int or float, or prints an error and quits
		Token temp = l.nextToken();
		if(temp.type == Token.ID) {
			indent();
			System.out.println(temp);
		}
		else if(temp.type == Token.INT) {
			indent();
			System.out.println(temp);
		}
		else if(temp.type == Token.FLOAT) {
			indent();
			System.out.println(temp);
		}
		else
			error(Token.ID, temp.type);
		
		indents--;
		indent();
		System.out.println("</Term>");
	}
}

