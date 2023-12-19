package example;


import java.util.Collection;

import social.NoSuchCodeException;
import social.PersonExistsException;
import social.Social;
import social.SocialGui;

public class app {
	public void main(String[] args) throws PersonExistsException, NoSuchCodeException {
		Social f = new Social();

		f.addPerson("Mario99", "Mario", "Rossi");
		f.addPerson("Mario88", "Mario", "Verdi");
		f.addPerson("Elena66", "Elena", "Aresti");
		f.addPerson("BigLupo", "Lupo", "Bianchi");
		f.addPerson("FFA", "Franca", "Rosetti");
		f.addPerson("Sally", "Sandra", "Sandroni");
		
		String s = f.getPerson("Mario99"); // "Mario99 Mario Rossi"
		
		
		f.addFriendship("Mario99", "BigLupo");
		f.addFriendship("Mario99", "Elena66");
		f.addFriendship("Elena66", "FFA");
		f.addFriendship("Elena66", "Sally");
		f.addFriendship("BigLupo", "FFA");
		
		Collection<String> friends = f.listOfFriends("Mario99"); // "Elena66" "BigLupo" 

		friends = f.friendsOfFriends("Mario99"); // "FFA" "Sally"

		String id = f.personWithMostFriendsOfFriends(); // "FFA"

		SocialGui gui = new SocialGui(f);
		gui.setVisible(true);
	}
}
