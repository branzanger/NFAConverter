package CSE105Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class FiniteAutomaton {
	
	
	int stateNum;
	HashMap<String, State> states;
	String[] alphabet;
	String startState;
	HashMap<String, State> acceptStates;
	
	
	public FiniteAutomaton(int stateNum, String[] alphabet, String startState, HashMap<String, State> acceptStates) {
		this.stateNum = stateNum;
		this.states = new HashMap<String, State>();
		this.alphabet = alphabet;
		this.startState = startState;
		this.acceptStates = acceptStates;
	}
	public FiniteAutomaton(HashMap<String, State> states, String[] alphabet, String startState, HashMap<String, State> acceptStates) {
		this.states = states;
		this.stateNum = states.size();
		this.alphabet = alphabet;
		this.startState = startState;
		this.acceptStates = acceptStates;
	}
	
	public FiniteAutomaton(String fileName) {
		try {
			readFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param fileName
	 * @throws IOException
	 * 
	 * 
	 * File Syntax (Separate Lines)
	 * number
	 * String Alphabet (comma separated)
	 * String Transition (state:input:outputState)(comma separated)
	 * (q1:a:q2),(q2:b:q1),(q2:a:q3)
	 * String Start state
	 * String Accept states (comma separated)
	 */
	public void readFile(String fileName) throws IOException {
		File file = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(file));
	
		this.stateNum = Integer.parseInt(br.readLine());
		this.alphabet = br.readLine().split(",");
		
		
		String transitionRaw = br.readLine();
		this.states = convertTransition(transitionRaw);
		
		
		this.startState = br.readLine();
		
		
		String[] accept = br.readLine().split(",");
		this.acceptStates = new HashMap<String, State>();
		for(int i = 0; i < accept.length; ++i) {
			this.acceptStates.put(accept[i], states.get(accept[i]));
		}
	}
	
	private HashMap<String, State> convertTransition(String transitionRaw) {
		HashMap<String, State> states = new HashMap<String, State>();
		
		for(int i = 1; i <= stateNum; ++i) {
			states.put(""+i, new State(""+i));
		}

		String[] transitions = transitionRaw.split(",");
		for(int i = 0; i < transitions.length;++i) {
			String[] currentTransition = transitions[i].replaceAll("[()]", "").split(":");
			states.get(currentTransition[0]).addTransition(currentTransition[1], states.get(currentTransition[2]));
		}
		return states;
	}
	
	/**
	 * 
	 * @param inputStateName
	 * @param input
	 * @return outputStateName
	 */
	public String[] transition(String inputStateName, String input) {
		
		if(states.get(inputStateName) == null) {
			return null;
		}
		
		return states.get(inputStateName).getTransition(input);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String s : states.keySet()) {
			sb.append(s);
			sb.append(",");
		}
		
		sb.deleteCharAt(sb.length()-1);
		sb.append("\n");
		
		for(String s: alphabet) {
			sb.append(s);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("\n");
		
		for(State s : states.values()) {
			if(s.getTransitionFunction() != null) {
				sb.append(s.getTransitionFunction());
				sb.append(",");
			}
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("\n");
		
		sb.append(startState);
		sb.append("\n");
		
		for(String s : acceptStates.keySet()) {
			sb.append(s);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("\n");
		
		return sb.toString();
	}
	
}
