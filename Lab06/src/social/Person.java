package social;

import java.util.*;

public class Person {
		private String code;
		private String first, last;
		private ArrayList<String> friends = new ArrayList<>();
		private ArrayList<String> groups = new ArrayList<>();
		
		public Person(String c, String f, String l) {
			code = c;
			first = f;
			last = l;
		}
		
		public String toString() {
			return (code + " " + first + " " + last);
		}
		
		public void addFriend(String code) {
			friends.add(code);
		}
		
		public void addGroup(String g) {
			groups.add(g);
		}
		
		public Collection<String> getFriends() {
			return friends;
		}
		
		public int getNumberOfFriends() {
			return friends.size();
		}
		
		public String getCode() {
			return code;
		}
		
		public int getNumberOfGroups() {
			return groups.size();
		}
		
		public String getName() {
			return first + " " + last;
		}
}
