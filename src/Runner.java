/**
 * 
 * @author Fernando Faria
 * fvf215
 * 
 * This file is my tester file
 * 
 * Java 1.6
 * Operating System: Windows 7
 *
 */
public class Runner {
	
	/**
	 * @param args
	 * @precondition 
	 * @postcondition TODO
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Parser p = new Parser(" SELECT C1,C2,C3,C4 FROM T1,T2 WHERE C1= .23 AND C2 < 2 AND C3 > C4");
		
		p.run();
	}
	
}
