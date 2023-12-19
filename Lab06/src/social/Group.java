package social;

import java.util.*;

public class Group {
	private String code;
	private ArrayList<String> partecipants = new ArrayList<>();
	
	public Group(String c) {
		code = c;
	}
	
	public void addPartecipant(String code) {
		partecipants.add(code);
	}
	
	public Collection<String> getPartecipants() {
		return partecipants;
	}
	
	public int getNumberOfPartecipants() {
		return partecipants.size();
	}
	
	public String getGroupName() {
		return code;
	}
}
