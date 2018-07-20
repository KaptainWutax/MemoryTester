package kaptainwutax.memorytester.gui;

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

import kaptainwutax.memorytester.data.IDataHandler;
import kaptainwutax.memorytester.thread.ThreadMain;

public class GuiErrorCrash {
	
	private ThreadMain threadInstance;
    
    public JFrame frame;
    
    public JLabel crash;
    public JLabel crashInfo;


    public GuiErrorCrash(ThreadMain threadInstance) {
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

        crashInfo = new JLabel("loading...");
        crashInfo.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 30));
        crashInfo.setHorizontalAlignment(SwingConstants.CENTER);
        crashInfo.setBounds(textCenterX + textOffsetX, textCenterY + textOffsetY + 60, textWidth, textHeight);
        frame.getContentPane().add(crashInfo);
        
        int buttonWidth = 500;
        int buttonCenterX = frame.getWidth() / 2 - buttonWidth / 2;
        int buttonOffsetX = 0;

        int buttonHeight = 40;
        int buttonCenterY = frame.getHeight() / 2 - buttonHeight / 2;
        int buttonOffsetY = -250;

        JButton resetButton = new JButton("Reset Config");
        resetButton.setEnabled(true);
        resetButton.setBounds(buttonCenterX + buttonOffsetX, buttonCenterY + buttonOffsetY, buttonWidth, buttonHeight);
        frame.getContentPane().add(resetButton);
        
        resetButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				for(IDataHandler dataObj : threadInstance.data) {
					dataObj.setDefaultDataToThread();
				}
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

        JButton quitButton = new JButton("Quit");
        quitButton.setEnabled(true);
        quitButton.setBounds(buttonCenterX + buttonOffsetX, buttonCenterY + buttonOffsetY + 50, buttonWidth, buttonHeight);
        frame.getContentPane().add(quitButton);
        
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
