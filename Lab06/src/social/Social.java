package social;

import java.util.*;


public class Social {
	TreeMap<String, Person> users = new TreeMap<>();
	TreeMap<String, Group> groups = new TreeMap<>();

	/**
	 * Creates a new account for a person
	 * 
	 * @param code	nickname of the account
	 * @param name	first name
	 * @param surname last name
	 * @throws PersonExistsException in case of duplicate code
	 */
	public void addPerson(String code, String name, String surname) throws PersonExistsException {
		if (users.containsKey(code)) throw new PersonExistsException();
		Person tmp  = new Person(code, name, surname);
		users.put(code, tmp);
	}

	/**
	 * Retrieves information about the person given their account code.
	 * The info consists in name and surname of the person, in order, separated by blanks.
	 * 
	 * @param code account code
	 * @return the information of the person
	 * @throws NoSuchCodeException
	 */
	public String getPerson(String code) throws NoSuchCodeException {
		if (! users.containsKey(code)) throw new NoSuchCodeException();
		return users.get(code).toString();
	}

	/**
	 * Define a friendship relationship between to persons given their codes.
	 * 
	 * Friendship is bidirectional: if person A is friend of a person B, that means that person B is a friend of a person A.
	 * 
	 * @param codePerson1	first person code
	 * @param codePerson2	second person code
	 * @throws NoSuchCodeException in case either code does not exist
	 */
	public void addFriendship(String codePerson1, String codePerson2) throws NoSuchCodeException {
		if (! users.containsKey(codePerson1) || ! users.containsKey(codePerson2)) throw new NoSuchCodeException();
		users.get(codePerson1).addFriend(codePerson2);
		users.get(codePerson2).addFriend(codePerson1);
	}

	/**
	 * Retrieve the collection of their friends given the code of a person.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return the list of person codes
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> listOfFriends(String codePerson) throws NoSuchCodeException {
		if (! users.containsKey(codePerson)) throw new NoSuchCodeException();
		return users.get(codePerson).getFriends();
	}

	/**
	 * Retrieves the collection of the code of the friends of the friends
	 * of the person whose code is given, i.e. friends of the second level.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return collections of codes of second level friends
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> friendsOfFriends(String codePerson) throws NoSuchCodeException {
		if (! users.containsKey(codePerson)) throw new NoSuchCodeException();
		TreeSet<String> res = new TreeSet<>();
		for (String s : users.get(codePerson).getFriends()) {
			res.addAll(users.get(s).getFriends());
		}
		res.remove(codePerson);
		return res;
	}

	/**
	 * Retrieves the collection of the code of the friends of the friends
	 * of the person whose code is given, i.e. friends of the second level.
	 * The result has no duplicates.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return collections of codes of second level friends
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> friendsOfFriendsNoRepetition(String codePerson)
			throws NoSuchCodeException {
		if (! users.containsKey(codePerson)) throw new NoSuchCodeException();
		TreeSet<String> res = new TreeSet<>();
		for (String s : users.get(codePerson).getFriends()) {
			res.addAll(users.get(s).getFriends());
		}
		res.remove(codePerson);
		
		return res;
	}

	/**
	 * Creates a new group with the given name
	 * 
	 * @param groupName name of the group
	 */
	public void addGroup(String groupName) {
		Group tmp = new Group(groupName);
		groups.put(groupName, tmp);
	}

	/**
	 * Retrieves the list of groups.
	 * 
	 * @return the collection of group names
	 */
	public Collection<String> listOfGroups() {
		return groups.keySet();
	}

	/**
	 * Add a person to a group
	 * 
	 * @param codePerson person code
	 * @param groupName  name of the group
	 * @throws NoSuchCodeException in case the code or group name do not exist
	 */
	public void addPersonToGroup(String codePerson, String groupName) throws NoSuchCodeException {
		if (! users.containsKey(codePerson) || ! groups.containsKey(groupName)) throw new NoSuchCodeException();
		groups.get(groupName).addPartecipant(codePerson);
		users.get(codePerson).addGroup(groupName);
	}

	/**
	 * Retrieves the list of people on a group
	 * 
	 * @param groupName name of the group
	 * @return collection of person codes
	 */
	public Collection<String> listOfPeopleInGroup(String groupName) {
		if (! groups.containsKey(groupName)) return null;
		return groups.get(groupName).getPartecipants();
	}

	/**
	 * Retrieves the code of the person having the largest
	 * group of friends
	 * 
	 * @return the code of the person
	 */
	public String personWithLargestNumberOfFriends() {
		String res = users.values().stream().sorted(Comparator.comparing(Person::getNumberOfFriends).reversed()).map(x -> x.getCode()).toList().get(0);
		return res;
	}

	/**
	 * Find the code of the person with largest number
	 * of second level friends
	 * 
	 * @return the code of the person
	 */
	public String personWithMostFriendsOfFriends() {
		
		String s = new String();
		int max = 0;
		for (String u : users.keySet()) {
			int n;
			try {
				n = friendsOfFriendsNoRepetition(u).size();
				if (n > max) {
					max = n;
					s = u;
				}
			} catch (NoSuchCodeException e) {
			}
		}
		return s;
	}

	/**
	 * Find the name of group with the largest number of members
	 * 
	 * @return the name of the group
	 */
	public String largestGroup() {
		String res = groups.values().stream().sorted(Comparator.comparing(Group::getNumberOfPartecipants).reversed()).map(Group::getGroupName).toList().get(0);
		return res;
	}

	/**
	 * Find the code of the person that is member of
	 * the largest number of groups
	 * 
	 * @return the code of the person
	 */
	public String personInLargestNumberOfGroups() {
		String res = users.values().stream().sorted(Comparator.comparing(Person::getNumberOfGroups).reversed()).map(x -> x.getCode()).toList().get(0);
		return res;
	}
}