package CSE105Project;

import java.util.*;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

public class test {

	public static void main(String[] args) {
		
	}
	
	@Test
	public void testConvert() {
		String fileName = "C:\\Users\\Angel\\eclipse-workspace\\Stuff\\src\\NFA.txt";
		
		FiniteAutomaton NFA = new FiniteAutomaton(fileName);
		
		FiniteAutomaton DFA = ConvertNFA.convert(NFA);
		
		System.out.println(DFA.toString());
	}
	
//	@Test
//	public void testReadFile() {
//		String fileName = "C:\\Users\\Angel\\eclipse-workspace\\Stuff\\src\\NFA.txt";
//		
//		FiniteAutomaton FA = new FiniteAutomaton(fileName);
//
//		
//		System.out.println(FA.toString());
//	}
	
	@Test
	public void testPowerSetOrder() {
		Set<String> test = new HashSet<>();
		test.add("1");
		test.add("2");
		test.add("3");
		test.add("4");
		test.add("5");
		
		Set<Set<String>> powerSet = ConvertNFA.powerSet(test);
		
		//System.out.println(powerSet.toString());
		
	}
	
	@Test
	public void testPowerSet() {
		Set<String> test = new HashSet<>();
		test.add("1");
		test.add("2");
		test.add("3");
		
		Set<Set<String>> powerSet = ConvertNFA.powerSet(test);
		
		assertEquals("[[], [1], [2], [3], [1, 2], [1, 3], [2, 3], [1, 2, 3]]", powerSet.toString());
	}
	
}
