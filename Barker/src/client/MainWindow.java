package client;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import barker.BarkerServerAnon;
import barker.BarkerServerAuth;
import barker.Bark;
import barker.ConnectionServer;

public class MainWindow extends JFrame {
	
	public static final String APPNAME = "Barker";
	
	private DisplayFromTopicOrUser displayTopicUser;
	private JList<BarkPanel> topicOrUserList;
	private JList<BarkPanel> allBarksList;
	private JList<BarkPanel> barkList;
	private JList<BarkPanel> lastFromMeList;
	private JList<String> trendingTopics;
	
	private SystemTray tray;
	private PopupMenu popup;
	private boolean anonSession;
	private BarkerServerAnon anon;
	private BarkerServerAuth auth;
	private JTextArea barkArea;
	private String username;
	
	public MainWindow(final ConnectionServer conserver, BarkerServerAuth server, BarkerServerAnon anonym, 
			final TrayIcon trayIcon, String username) {
		super();
		this.auth = server;
		this.anon = anonym;
		this.popup = new PopupMenu();
		this.username = username;
		MenuItem disconnectItem = new MenuItem("Se déconnecter");
		MenuItem exitItem = new MenuItem("Quitter Barker");
		MenuItem restoreItem = new MenuItem("Restaurer Barker");
		
		anonSession = auth == null;
		
		final MainWindow caller = this;
		disconnectItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!anonSession)
						auth.logout();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				caller.dispose();
				LoginWindow newLogin = new LoginWindow(trayIcon);
				tray.remove(trayIcon);
				newLogin.setServer(conserver);
				newLogin.setVisible(true);
			}
		});
		
		exitItem.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(NORMAL);			
			}
		});
		
		restoreItem.addActionListener(new RestoreActionListener(this));
		popup.add(disconnectItem);
		popup.add(exitItem);
		popup.add(restoreItem);
		
		if (!SystemTray.isSupported()) {
			System.out.println("System tray is not supported");
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		else {
			trayIcon.setPopupMenu(popup);
			tray = SystemTray.getSystemTray();
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
		
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle(APPNAME + " : "+username);
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		
		
		final JTabbedPane tabPane = new JTabbedPane();
		
		BarkRenderer renderer = new BarkRenderer();
		
		allBarksList = new JList<BarkPanel>();
		allBarksList.setCellRenderer(renderer);
		allBarksList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		barkList = new JList<BarkPanel>();
		barkList.setCellRenderer(renderer);
		barkList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		lastFromMeList = new JList<BarkPanel>();
		lastFromMeList.setCellRenderer(renderer);
		lastFromMeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		topicOrUserList = new JList<BarkPanel>();
		topicOrUserList.setCellRenderer(renderer);
		topicOrUserList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		
		trendingTopics = new JList<String>();

		displayTopicUser = new DisplayFromTopicOrUser(this);
		
		this.refreshLists();
		
		List<JList<BarkPanel>> tabLists = new ArrayList<>();
		
		tabPane.add("Topics", new JScrollPane(trendingTopics));
		
		tabPane.add("Afficher les barks", displayTopicUser);
		tabLists.add(topicOrUserList);
		
		tabPane.add("Tous les barks", new JScrollPane(allBarksList));
		tabLists.add(allBarksList);
		
		if (!anonSession) {
			tabPane.add("Fil d'actualité", new JScrollPane(barkList));
			tabLists.add(barkList);
			tabPane.add("Mes barks", new JScrollPane(lastFromMeList));
			tabLists.add(lastFromMeList);
		}
		
		this.add(tabPane);
		
		JPanel barkPanel = new JPanel();
		barkArea = new JTextArea();
		barkArea.setLineWrap(true);
		barkArea.setPreferredSize(new Dimension(300, 100));
		JScrollPane barkPane = new JScrollPane(barkArea);
		JButton barkButton = new JButton("Bark");
		barkButton.addActionListener(new BarkerListener(this));
		
		barkPanel.add(barkPane);
		barkPanel.add(barkButton);
		if (anonSession) {
			barkButton.setEnabled(false);
			barkArea.setEnabled(false);
		}
		
		JPanel buttonPanel = new JPanel();
		JButton sniff = new JButton("sniff");		
		sniff.addActionListener(new ListActionListener(this, tabPane, tabLists, auth));
		
		JButton rebark = new JButton("rebark");
		rebark.addActionListener(new ListActionListener(this, tabPane, tabLists, auth));
		
		JButton displayBarks = new JButton("afficher les barks");
		displayBarks.addActionListener(new ListActionListener(this, tabPane, tabLists, auth));
		
		JButton refresh = new JButton("Rafraichir");
		refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				refreshLists();
				
			}
		});
		
		
		buttonPanel.add(sniff);
		buttonPanel.add(rebark);
		buttonPanel.add(displayBarks);
		buttonPanel.add(refresh);
		
		if (anonSession) {
			sniff.setEnabled(false);
			rebark.setEnabled(false);
		}
		
		this.add(buttonPanel);
		
		this.add(barkPanel);
		
		this.pack();
	}

	public boolean isAnonSession() {
		return anonSession;
	}

	public BarkerServerAnon getAnon() {
		return anon;
	}

	public BarkerServerAuth getAuth() {
		return auth;
	}

	public void refreshLists () {
		
		List<Bark> myLastBarks = null;
		List<Bark> lastFromMe = null;
		List<Bark> allBarks = null;
		List<String> topics = null;
		final MainWindow caller = this;
			try {
				allBarks = anon.lastBarks(50);
				topics = anon.trendingTopics();
				if (! anonSession) {
					myLastBarks = auth.myLastBarks(50);
					lastFromMe = auth.lastBarksFrom(50, username);
				}
			} catch (RemoteException e1) {
				JOptionPane.showConfirmDialog(caller, "Impossible de récupérer les barks : \n"+e1.getMessage(), 
						MainWindow.APPNAME, JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		
		DefaultListModel<BarkPanel> allBarksModel = new DefaultListModel<BarkPanel>();
		DefaultListModel<BarkPanel> myLastBarksModel = new DefaultListModel<BarkPanel>();
		DefaultListModel<BarkPanel> lastFromMeModel = new DefaultListModel<BarkPanel>();
		DefaultListModel<String> topicsModel = new DefaultListModel<String>();
		if (myLastBarks == null && !anonSession)
			JOptionPane.showConfirmDialog(caller, "Vous n'avez aucun bark dans votre liste", 
					MainWindow.APPNAME, JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
		else if (myLastBarks != null)
			for (Bark bark : myLastBarks) {
				myLastBarksModel.addElement(new BarkPanel(bark));
			}
		if (lastFromMe != null) {
			for (Bark bark : lastFromMe)
				lastFromMeModel.addElement(new BarkPanel(bark));
		}
		if (allBarks != null) {
			for (Bark bark : allBarks)
				allBarksModel.addElement(new BarkPanel(bark));
		}
		if (topics != null) {
			for (String topic : topics)
				topicsModel.addElement(topic);
		}
		
		
		allBarksList.setModel(allBarksModel);
		if (!anonSession) {
			barkList.setModel(myLastBarksModel);
			lastFromMeList.setModel(lastFromMeModel);
		}
		trendingTopics.setModel(topicsModel);
		
		displayTopicUser.refresh();
	}

	public DisplayFromTopicOrUser getDisplayTopicUser() {
		return displayTopicUser;
	}

	public JList<String> getTrendingTopics() {
		return trendingTopics;
	}

	public JTextArea getBarkArea() {
		return barkArea;
	}

	public JList<BarkPanel> getTopicOrUserList() {
		return topicOrUserList;
	}

}
