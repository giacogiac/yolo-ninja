package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTabbedPane;

import security.module.BarkerPrincipal;

import barker.BarkerServerAuth;

public class ListActionListener implements ActionListener {
	
	private JTabbedPane tabPane;
	private List<JList<BarkPanel>> tabLists;
	private BarkerServerAuth auth;
	private MainWindow mainWin;

	public ListActionListener(MainWindow mainWin, JTabbedPane tabPane, List<JList<BarkPanel>> tabLists, BarkerServerAuth auth) {
		this.mainWin = mainWin;
		this.tabPane = tabPane;
		this.tabLists = tabLists;
		this.auth = auth;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (tabPane.getSelectedIndex() > 0) {
			JList<BarkPanel> listBarkPanel = tabLists.get(tabPane.getSelectedIndex()-2);
			List<BarkPanel> barkPanels = listBarkPanel.getSelectedValuesList();
			JButton source = (JButton) e.getSource();
			if (barkPanels.size() > 0) {
				for (int i = 0; i < barkPanels.size(); ++i) {
					switch (source.getText()) {
					case "sniff":
						try {
							auth.sniff(barkPanels.get(i).getBark().getUsername());
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						break;
					case "rebark":
						try {
							auth.rebark(barkPanels.get(i).getBark());
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						break;
					case "afficher les barks":
						mainWin.getDisplayTopicUser().displayFromUser(barkPanels.get(i).getBark().getUsername());
						tabPane.setSelectedIndex(1);
						break;
					default:
						break;
					}
				}
				mainWin.refreshLists();
			}
		}
		else if (((JButton)e.getSource()).getText().equals("afficher les barks")) {
			String topic = mainWin.getTrendingTopics().getSelectedValue();
			mainWin.getDisplayTopicUser().displayAbout(topic);
			tabPane.setSelectedIndex(1);
		}
	}

}
