package Frames;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import Editables.Config;
import Editables.Header;
import Editables.Image;
import Utils.DebugLogger;
import Utils.Logging;
import Utils.MiscUtils;

@SuppressWarnings("serial")
public class Menu extends JFrame {

	private JTabbedPane contentPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Logging.setLogger(new DebugLogger());
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					Logging.logException(e);
				}
			}
		});
	}

	public void addPanes()
	{
		contentPane.add(new Config().editorPanel());
		contentPane.add(new Header().editorPanel());
		contentPane.add(new Image().editorPanel());
	}
	
	/**
	 * Create the frame.
	 */
	public Menu()
	{
		this.setTitle(MiscUtils.getProgramName());
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(640, 480));
		
		contentPane = new JTabbedPane();
		contentPane.setVisible(true);
		add(contentPane);
		
		addPanes();
		
		pack();
	}

}
