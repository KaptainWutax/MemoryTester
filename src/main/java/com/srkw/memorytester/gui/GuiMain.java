package com.srkw.memorytester.gui;

import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GuiMain extends JFrame {

	public JFrame frame;
	public JLabel memoryAllocatedText;
	public JLabel memoryAllocatedRecommendedText;
	
	public GuiMain() {		
		initialize();		
	}
	
	private void initialize() {	

		frame = new JFrame();
		frame.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
		frame.setTitle("Memory Tester");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null); 
				
		int textWidth = 1000;
		int textCenterX = frame.getWidth() / 2 - textWidth / 2;
		int textOffsetX = 0;
		
		int textHeight = 40;
		int textCenterY = frame.getHeight() / 2 - textHeight / 2;
		int textOffsetY = -400;
		
		memoryAllocatedText = new JLabel("loading...");
		memoryAllocatedText.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 30));
		memoryAllocatedText.setHorizontalAlignment(SwingConstants.CENTER);
		memoryAllocatedText.setBounds(textCenterX + textOffsetX, textCenterY + textOffsetY, textWidth, textHeight);
		frame.getContentPane().add(memoryAllocatedText);
		
		memoryAllocatedRecommendedText = new JLabel("loading...");
		memoryAllocatedRecommendedText.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 30));
		memoryAllocatedRecommendedText.setHorizontalAlignment(SwingConstants.CENTER);
		memoryAllocatedRecommendedText.setBounds(textCenterX + textOffsetX, textCenterY + textOffsetY + 40, textWidth, textHeight);
		frame.getContentPane().add(memoryAllocatedRecommendedText);
	}
	
}
