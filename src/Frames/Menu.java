package Frames;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import Misc.ConfigBuilder;
import Segments.Header;
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
		contentPane.add(new ConfigBuilder().editorPanel());
		contentPane.add(new Header().editorPanel());
	}
	
	/**
	 * Create the frame.
	 */
	public Menu()
	{
		ConfigBuilder builder = new ConfigBuilder();
		this.setTitle(MiscUtils.getProgramName());
		setPreferredSize(new Dimension(640, 480));
		
		contentPane = new JTabbedPane();
		contentPane.setVisible(true);
		add(contentPane);
		
		addPanes();
		
		pack();
	}

}
