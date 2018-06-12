package com.srkw.memorytester.gui;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.srkw.memorytester.thread.ThreadMain;

public class GuiForceCrash extends JFrame {

    private ThreadMain threadInstance;
    
    public JFrame frame;
    
    public JLabel crash;
    public JLabel currentAllocation;
    public JLabel recommendedAllocation;
    public JLabel crashInfo;


    public GuiForceCrash(ThreadMain threadInstance) {
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
            public void windowActivated(WindowEvent arg0) {}

            @Override
            public void windowClosed(WindowEvent arg0) {
                threadInstance.shouldGameStart = false;
                frame.setEnabled(false);
                frame.setVisible(false);
            }

            @Override
            public void windowClosing(WindowEvent arg0) {}

            @Override
            public void windowDeactivated(WindowEvent arg0) {}

            @Override
            public void windowDeiconified(WindowEvent arg0) {}

            @Override
            public void windowIconified(WindowEvent arg0) {}

            @Override
            public void windowOpened(WindowEvent arg0) {}

        });

        int textWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int textCenterX = frame.getWidth() / 2 - textWidth / 2;
        int textOffsetX = 0;

        int textHeight = 40;
        int textCenterY = frame.getHeight() / 2 - textHeight / 2;
        int textOffsetY = -400;

        crash = new JLabel("loading...");
        crash.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 30));
        crash.setHorizontalAlignment(SwingConstants.CENTER);
        crash.setBounds(textCenterX + textOffsetX, textCenterY + textOffsetY, textWidth, textHeight);
        frame.getContentPane().add(crash);

        currentAllocation = new JLabel("loading...");
        currentAllocation.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 30));
        currentAllocation.setHorizontalAlignment(SwingConstants.CENTER);
        currentAllocation.setBounds(textCenterX + textOffsetX, textCenterY + textOffsetY + 40, textWidth, textHeight);
        frame.getContentPane().add(currentAllocation);

        recommendedAllocation = new JLabel("loading...");
        recommendedAllocation.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 30));
        recommendedAllocation.setHorizontalAlignment(SwingConstants.CENTER);
        recommendedAllocation.setBounds(textCenterX + textOffsetX, textCenterY + textOffsetY + 80, textWidth, textHeight);
        frame.getContentPane().add(recommendedAllocation);

        crashInfo = new JLabel("loading...");
        crashInfo.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 30));
        crashInfo.setHorizontalAlignment(SwingConstants.CENTER);
        crashInfo.setBounds(textCenterX + textOffsetX, textCenterY + textOffsetY + 140, textWidth, textHeight);
        frame.getContentPane().add(crashInfo);

    }

}
