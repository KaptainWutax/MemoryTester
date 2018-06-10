package com.srkw.memorytester.gui;

import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.srkw.memorytester.thread.ThreadMain;

public class GuiMain extends JFrame {
 
    private ThreadMain threadInstance;
    
    public JFrame frame;
    
    public JLabel memoryAllocatedText;
    public JLabel memoryAllocatedRecommendedText;
    public JLabel memoryAllocatedRatioUsageText;    
    public JLabel memorySpikesText;
    public JLabel totalTimeText;
    
    public ArrayList<Long> usage = new ArrayList<>();
    public long weight = 1;
    
    public int memorySpikesCount = 0;
    public long millisecondsPassed = 0;
    public long secondsPassed = 0;
    public long minutesPassed = 0;
    public long hoursPassed = 0;
    
    public Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (millisecondsPassed == 1000) {
                millisecondsPassed = 0;
                secondsPassed++;
            }
            if (secondsPassed == 60) {
                secondsPassed = 0;
                minutesPassed++;
            }

            if (minutesPassed == 60) {
                minutesPassed = 0;
                hoursPassed++;
            }

            millisecondsPassed++;
        }
    };


    public GuiMain(ThreadMain threadInstance) {
        this.threadInstance = threadInstance;
        timer.scheduleAtFixedRate(timerTask, 1, 1);
        initialize();
    }

    private void initialize() {

        frame = new JFrame();
        frame.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        frame.setTitle("Memory Tester");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        int textWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
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

        memoryAllocatedRatioUsageText = new JLabel("loading...");
        memoryAllocatedRatioUsageText.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 30));
        memoryAllocatedRatioUsageText.setHorizontalAlignment(SwingConstants.CENTER);
        memoryAllocatedRatioUsageText.setBounds(textCenterX + textOffsetX, textCenterY + textOffsetY + 80, textWidth, textHeight);
        frame.getContentPane().add(memoryAllocatedRatioUsageText);

        memorySpikesText = new JLabel("loading...");
        memorySpikesText.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 30));
        memorySpikesText.setHorizontalAlignment(SwingConstants.CENTER);
        memorySpikesText.setBounds(textCenterX + textOffsetX, textCenterY + textOffsetY + 120, textWidth, textHeight);
        frame.getContentPane().add(memorySpikesText);

        totalTimeText = new JLabel("loading...");
        totalTimeText.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 30));
        totalTimeText.setHorizontalAlignment(SwingConstants.CENTER);
        totalTimeText.setBounds(textCenterX + textOffsetX, textCenterY + textOffsetY + 160, textWidth, textHeight);
        frame.getContentPane().add(totalTimeText);
    }

}
