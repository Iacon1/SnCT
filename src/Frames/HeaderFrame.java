package Frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Segments.Header;
import Utils.EditorPanel;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JList;

@SuppressWarnings("serial")
public class HeaderFrame extends JFrame {

	private JPanel contentPane;
	private Header header;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HeaderFrame frame = new HeaderFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HeaderFrame() {
		header = new Header();
		
		this.setTitle("Header Editor");
		contentPane = new EditorPanel("Header Editor");
		contentPane.setVisible(true);
		add(contentPane);
	}

}
