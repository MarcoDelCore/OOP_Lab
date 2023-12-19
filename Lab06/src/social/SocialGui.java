package social;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serial;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class SocialGui extends JFrame implements KeyListener{
	@Serial
	private static final long serialVersionUID = 1L;

	// The following components are declared public
	// in order to allow testing the user interface
	
	/**
	 * The code of the person to log in
	 */
	public JTextField id ;
	
	/**
	 * The button to perform login
	 */
	public JButton login ;
	
	/**
	 * The label that shall contain the info
	 * of the logged in person 
	 */
	public JLabel name ;
	
	/**
	 * The list of friends of the person
	 * that is logged in
	 */
	public JList<String> friends ;
	
	private Social s;
	
	public JLabel text;
	
	public JDialog errorMessage;
	

	public SocialGui(Social m){
		s = m;
		
		setTitle("MyFacebook");
		setSize(500,500);
		
		text = new JLabel("ID:");
		id = new JTextField(10);
		name = new  JLabel("User name", SwingConstants.CENTER);
		login = new JButton("Login");
		friends = new JList<>();
		id.addKeyListener(this);
		
		setLayout(new BorderLayout());
		JPanel upper = new JPanel();
		upper.setLayout(new FlowLayout());
		
		JPanel mid = new JPanel();
		mid.setLayout(new BorderLayout());
		
		upper.add(text);
		upper.add(id);
		upper.add(login);
		
		mid.add(name, BorderLayout.NORTH);
		mid.add(friends);
		add(upper, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		
		login.addActionListener(
				evento -> {
					String p = id.getText();
					aggiorna(p);
				});
		
		
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setVisible(true);
	}

	public void aggiorna(String p) {
		String[] res;
		if (! s.users.containsKey(p)) {
			JFrame f = new JFrame();
			JOptionPane.showMessageDialog(f,"The user code is invalid!","Login error",JOptionPane.ERROR_MESSAGE);
			
		}
		else {
			name.setText(s.users.get(p).toString());
			res = s.users.get(p).getFriends().toArray(String[]::new);
			friends.setListData(res);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ENTER) login.doClick();
		
	}

}
