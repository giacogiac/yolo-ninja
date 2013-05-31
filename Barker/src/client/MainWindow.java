package client;

import java.awt.AWTException;
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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import barker.BarkerServerAnon;
import barker.BarkerServerAuth;

public class MainWindow extends JFrame {
	
	public static final String APPNAME = "Barker";
	
	private JList<JLabel> barkList;
	private SystemTray tray;
	private PopupMenu popup;
	
	public MainWindow(BarkerServerAuth server, BarkerServerAnon anon, final TrayIcon trayIcon) {
		super();
		this.popup = new PopupMenu();
		MenuItem disconnectItem = new MenuItem("Se déconnecter");
		MenuItem exitItem = new MenuItem("Quitter Barker");
		MenuItem restoreItem = new MenuItem("Restaurer Barker");
		
		final MainWindow mainWin = this;
		disconnectItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWin.dispose();
				new LoginWindow(trayIcon).setVisible(true);
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
		this.setTitle(APPNAME);
		this.setLayout(new GridLayout(0,1));
		
		barkList = new JList<JLabel>();
		this.add(new JScrollPane(barkList));
		
		JPanel barkPanel = new JPanel(new GridLayout(0, 2));
		JTextArea barkArea = new JTextArea();
		barkArea.setLineWrap(true);
		JScrollPane barkPane = new JScrollPane(barkArea);
		JButton barkButton = new JButton("Bark");
		barkPanel.add(barkPane);
		barkPanel.add(barkButton);
		this.add(barkPanel);
		
		
		this.pack();
	}

}
