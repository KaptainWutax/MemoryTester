package com.srkw.memorytester.gui;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GuiMain extends JFrame {

	public JFrame frame;
	public JLabel memoryAllocatedText;
	
	public GuiMain() {		
		initialize();		
	}
	
	private void initialize() {		
		frame = new JFrame();
		frame.setBounds(100, 100, 825, 440);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null); 
		
		memoryAllocatedText = new JLabel("loading...");
		memoryAllocatedText.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 30));
		memoryAllocatedText.setHorizontalAlignment(SwingConstants.CENTER);
		memoryAllocatedText.setBounds(100, 100, 1000, 64);
		frame.getContentPane().add(memoryAllocatedText);
	}
	
}
