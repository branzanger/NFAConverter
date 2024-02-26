package CSE105Project;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class ConvertNFA {
	
	
	
	
	public static FiniteAutomaton convert (FiniteAutomaton NFA) {
		
		//alphabet stays the same
		String[] alphabet = Arrays.copyOf(NFA.alphabet, NFA.alphabet.length);
		
		//new DFA state list
		HashMap<String, State> states = statePowerSet(NFA.states);
		states.remove("");
		State trap = new State("0");
		for(int i = 0; i < alphabet.length; ++i) {
			trap.addTransition(alphabet[i], trap);
		}
		states.put("0", trap);
		
		
		
		
		//transition function
		for(String name: states.keySet()) {
			//skips trap state
			if(!name.equals("0")) {
				State currentState = states.get(name);
				char[] stateList = currentState.name.toCharArray();
				
				//checks the transition function for all characters in the alphabet
				for(int j = 0; j < alphabet.length; ++j) {
					
					SortedSet<Integer> outputStates = new TreeSet<>();
					for(int i = 0; i < stateList.length; ++i) {
						String[] output = NFA.transition(stateList[i]+"", alphabet[j]);
						if(output != null) {
							for(int k = 0; k < output.length; ++k) {
								outputStates.add(Integer.parseInt(output[k]));
							}
						}
					}
					if(outputStates.isEmpty()) {
						currentState.addTransition(alphabet[j], states.get("0"));
					}else {
						String outputState = "";
						for(int stateNumber : outputStates) {
							outputState += stateNumber;
						}
						currentState.addTransition(alphabet[j], states.get(outputState));
					}
				}
			}
		}
		
		
		//no spontaneous transitions so same start state
		String startStateString = NFA.startState;
		
		//find accept State
		HashMap<String, State> acceptStates = new HashMap<>();
		String[] oldAcceptStates = NFA.acceptStates.keySet().toArray(new String[0]);
		
		for(String name: states.keySet()) {
			State currentState = states.get(name);
			
			for(int i=0; i<oldAcceptStates.length;++i) {
				if(currentState.name.contains(oldAcceptStates[i])) {
					acceptStates.put(currentState.name, currentState);
					break;
				}
			}
			
		}
		
		//FiniteAutomaton DFA 
		
		FiniteAutomaton DFA = new FiniteAutomaton(states, alphabet, startStateString, acceptStates);
		return DFA;
	}
	
	public static HashMap<String, State> statePowerSet (HashMap<String, State> set){
		Set<String> names = new HashSet<>();
		
		for(String s : set.keySet()) {
			names.add(s);
		}
		Set<Set<String>> namePowerSet = powerSet(names);
		
		Set<String> namesCombined = new HashSet<>();
		
		for(Set<String> outer : namePowerSet) {
			String name = "";
			for(String s : outer) {
				name += s;
			}
			namesCombined.add(name);
		}
		
		HashMap<String, State> states = new HashMap<String, State>();
		
		for(String s : namesCombined) {
			states.put(s, new State(s));
		}
		
		return states;
	}
	
	public static Set<Set<String>>powerSet(Set<String> set) {
		if(set.isEmpty()) {
			Set<Set<String>> empty = new HashSet<>();
			empty.add(set);
			return empty;
		}
		
		String element = set.iterator().next();
		Set<String> subsetWithoutElement = getSubsetWithoutElement(set, element);
		Set<Set<String>> powerSetWithoutElement = powerSet(subsetWithoutElement);
		Set<Set<String>> powerSetWithElement = addElement(powerSetWithoutElement, element);
		
		Set<Set<String>> powerSet = new HashSet<>();
	    powerSet.addAll(powerSetWithoutElement);
	    powerSet.addAll(powerSetWithElement);
	    return powerSet;
	}
	
	private static Set<String> getSubsetWithoutElement(Set<String> set, String element) {
		Set<String> subsetWithoutElement = new HashSet<>();
		for(String i : set) {
			if(!i.equals(element)) {
				subsetWithoutElement.add(i);
			}
		}
		return subsetWithoutElement;
	}
	
	private static Set<Set<String>> addElement(Set<Set<String>> set, String element) {
		Set<Set<String>> setWithElement = new HashSet<>();
		for(Set<String> s : set) {
			Set<String> newSet = new HashSet<>(s);
			newSet.add(element);
			setWithElement.add(newSet);
		}
		return setWithElement;
	}
	
}
