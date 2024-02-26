package CSE105Project;

public class Main {

	public static void main(String[] args) {
		String fileName = "C:\\Users\\Angel\\eclipse-workspace\\Stuff\\src\\NFA.txt";
		
		FiniteAutomaton NFA = new FiniteAutomaton(fileName);
		
		FiniteAutomaton DFA = ConvertNFA.convert(NFA);
		
		System.out.println(DFA.toString());

	}

}
