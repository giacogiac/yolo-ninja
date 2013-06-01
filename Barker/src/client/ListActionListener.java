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
			JList<BarkPanel> listBarkPanel = tabLists.get(tabPane.getSelectedIndex()-1);
			List<BarkPanel> barkPanels = listBarkPanel.getSelectedValuesList();
			if (barkPanels.size() > 0) {
				JButton source = (JButton) e.getSource();
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
						//TODO
						break;
					default:
						break;
					}
				}
				mainWin.refreshLists();
			}
		}
	}

}
