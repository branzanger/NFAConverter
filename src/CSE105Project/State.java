package CSE105Project;

import java.util.ArrayList;
import java.util.HashMap;


public class State {
	
	String name;
	
	//Given certain input output a valid state
	HashMap<String, ArrayList<State>> transitions;

	public State(String name, HashMap<String, ArrayList<State>> transitions) {
		this.name = name;
		this.transitions = transitions;
	}

	public State(String name) {
		this.name = name;
		transitions = new HashMap<String, ArrayList<State>>();
	}
	
	public void addTransition(String input, State state) {
		if(transitions.get(input) == null) {
			ArrayList<State> outputStates = new ArrayList<>();
			outputStates.add(state);
			transitions.put(input, outputStates);
		}else {
			transitions.get(input).add(state);
		}
	}
	
	public String[] getTransition(String input) {
		
		ArrayList<String> outputNames = new ArrayList<>();
		ArrayList<State> outputStates = transitions.get(input);
		
		if(outputStates == null) {
			return null;
		}
		
		for(int i = 0; i < outputStates.size(); ++i) {
			outputNames.add(outputStates.get(i).name);
		}
		
		String[] output = outputNames.toArray(new String[0]);
		
		return output;
	}
	
	public boolean equals(State obj) {
		return obj.name.equals(this.name);
	}
	
	public int hashCode() {
		 return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		
		State other = (State) obj;
		return other.name.equals(this.name);
	}
	
	public String getTransitionFunction() {
		StringBuilder sb = new StringBuilder();
		
		if(transitions.keySet().isEmpty()) {
			return null;
		}
		
		for(String input : transitions.keySet()) {
			for(State outputState : transitions.get(input)) {
				sb.append("(");
				sb.append(this.name);
				sb.append(":");
				sb.append(input);
				sb.append(":");
				sb.append(outputState.name);
				sb.append("),");
			}
		}
		
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	public String toString() {
		
		return getTransitionFunction();
	}
	
}
