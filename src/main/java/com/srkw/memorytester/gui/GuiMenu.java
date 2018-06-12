package com.srkw.memorytester.gui;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.srkw.memorytester.thread.ThreadMain;

public class GuiMenu extends JFrame {

    private ThreadMain threadInstance;

    public JFrame frame;

    public JLabel memoryAllocatedText;
    public JLabel memoryAllocatedRecommendedText;

    public GuiMenu(ThreadMain threadInstance) {
        this.threadInstance = threadInstance;
        initialize();
    }

    private void initialize() {

        frame = new JFrame();
        frame.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        frame.setTitle("Memory Tester");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        frame.addWindowListener(new WindowListener() {

            @Override
            public void windowActivated(WindowEvent arg0) {
            }

            @Override
            public void windowClosed(WindowEvent arg0) {
                threadInstance.shouldGameStart = false;
                frame.setEnabled(false);
                frame.setVisible(false);
            }

            @Override
            public void windowClosing(WindowEvent arg0) {
            }

            @Override
            public void windowDeactivated(WindowEvent arg0) {
            }

            @Override
            public void windowDeiconified(WindowEvent arg0) {
            }

            @Override
            public void windowIconified(WindowEvent arg0) {
            }

            @Override
            public void windowOpened(WindowEvent arg0) {
            }

        });

        //TEXT
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

        //BUTTON
        int buttonWidth = 500;
        int buttonCenterX = frame.getWidth() / 2 - buttonWidth / 2;
        int buttonOffsetX = 0;

        int buttonHeight = 40;
        int buttonCenterY = frame.getHeight() / 2 - buttonHeight / 2;
        int buttonOffsetY = -250;

        JButton startButton = new JButton("Start");
        startButton.setEnabled(true);
        startButton.setBounds(buttonCenterX + buttonOffsetX, buttonCenterY + buttonOffsetY, buttonWidth, buttonHeight);
        frame.getContentPane().add(startButton);

        JButton quitButton = new JButton("Quit");
        quitButton.setEnabled(true);
        quitButton.setBounds(buttonCenterX + buttonOffsetX, buttonCenterY + buttonOffsetY + 50, buttonWidth, buttonHeight);
        frame.getContentPane().add(quitButton);

        startButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                threadInstance.isInMenu = false;
                frame.setEnabled(false);
                frame.setVisible(false);
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {}

            @Override
            public void mouseExited(MouseEvent arg0) {}

            @Override
            public void mousePressed(MouseEvent arg0) {}

            @Override
            public void mouseReleased(MouseEvent arg0) {}

        });

        quitButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                threadInstance.shouldGameStart = false;
                frame.setEnabled(false);
                frame.setVisible(false);
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {}

            @Override
            public void mouseExited(MouseEvent arg0) {}

            @Override
            public void mousePressed(MouseEvent arg0) {}

            @Override
            public void mouseReleased(MouseEvent arg0) {}

        });

    }

}
