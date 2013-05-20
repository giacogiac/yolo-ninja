package client;

import java.awt.AWTException;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
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

public class MainWindow extends JFrame {
	
	public static final String APPNAME = "Barker";
	
	private JList<JLabel> barkList;
	private SystemTray tray;
	private PopupMenu popup;
	
	public MainWindow() {
		super();
		this.popup = new PopupMenu();
		MenuItem exitItem = new MenuItem("Exit Barker");
		MenuItem restoreItem = new MenuItem("Restore Barker");
		
		popup.add(exitItem);
		popup.add(restoreItem);
		
		if (!SystemTray.isSupported())
			System.out.println("System tray is not supported");
		else {
			BufferedImage picture = null;
			try {
				picture = ImageIO.read(new File("rsrc/barker.jpg"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			TrayIcon trayIcon = new TrayIcon(picture, APPNAME);
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
		JButton barkButton = new JButton("Bark");
		barkPanel.add(barkArea);
		barkPanel.add(barkButton);
		this.add(barkPanel);
		
		
		this.pack();
	}

}
