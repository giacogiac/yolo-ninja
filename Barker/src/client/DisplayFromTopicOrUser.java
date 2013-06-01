package client;

import java.rmi.RemoteException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import barker.Bark;

public class DisplayFromTopicOrUser extends JScrollPane {
	
	private MainWindow mainWin;
	private boolean isTopic;
	private String name;
	
	public DisplayFromTopicOrUser(MainWindow window) {
		this.mainWin = window;
		this.isTopic = true;
		this.name = "";
	}
	
	public void displayAbout (String topic) {
		isTopic = true;
		name = topic;
		try {
			List<Bark> barks = mainWin.getAnon().lastBarksAbout(50, topic);
			DefaultListModel<BarkPanel> listModel = new DefaultListModel<BarkPanel>();
			for (Bark bark : barks) {
				listModel.addElement(new BarkPanel(bark));
			}
			mainWin.getTopicOrUserList().setModel(listModel);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void displayFromUser (String username) {
		isTopic = false;
		name = username;
		try {
			List<Bark> barks = mainWin.getAnon().lastBarksFrom(50, username);
			DefaultListModel<BarkPanel> listModel = new DefaultListModel<BarkPanel>();
			for (Bark bark : barks) {
				listModel.addElement(new BarkPanel(bark));
			}
			mainWin.getTopicOrUserList().setModel(listModel);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void refresh () {
		if (isTopic) {
			displayAbout(name);
		}
		else {
			displayFromUser(name);
		}
	}

}
