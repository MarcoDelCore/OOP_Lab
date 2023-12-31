package example;


import java.util.Collection;

import org.junit.Test;

import social.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestExample {
	
	@Test
	public void testAll() throws PersonExistsException, NoSuchCodeException, InterruptedException {
		Social f = new Social();

		f.addPerson("Mario99", "Mario", "Rossi");
		f.addPerson("Mario88", "Mario", "Verdi");
		f.addPerson("Elena66", "Elena", "Aresti");
		f.addPerson("BigLupo", "Lupo", "Bianchi");
		f.addPerson("FFA", "Franca", "Rosetti");
		f.addPerson("Sally", "Sandra", "Sandroni");
		
		String s = f.getPerson("Mario99"); // "Mario99 Mario Rossi"

		assertTrue(s.contains("Mario99"));
		assertTrue(s.contains("Rossi"));
		
		
		f.addFriendship("Mario99", "BigLupo");
		f.addFriendship("Mario99", "Elena66");
		f.addFriendship("Elena66", "FFA");
		f.addFriendship("Elena66", "Sally");
		f.addFriendship("BigLupo", "FFA");
		
		Collection<String> friends = f.listOfFriends("Mario99"); // "Elena66" "BigLupo"    
		assertTrue(friends.contains("Elena66"));
		assertTrue(friends.contains("BigLupo"));
		assertTrue(f.listOfFriends("Mario88").isEmpty());

		friends = f.friendsOfFriends("Mario99"); // "FFA" "Sally"
		assertTrue(friends.contains("FFA"));
		assertTrue(friends.contains("Sally"));

		String id = f.personWithMostFriendsOfFriends(); // "FFA"
		assertEquals("Wrong person with most friends","FFA", id);

		SocialGui gui = new SocialGui(f);
		gui.setVisible(true);

		Thread.sleep(100);

		assertTrue(gui.isVisible());
		gui.dispose();
	}
}