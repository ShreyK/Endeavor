package com.khosla.endeavor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.khosla.background.Stars;
import com.khosla.utilities.SoundClip;

@SuppressWarnings("serial")
public class Menu extends JPanel {
	private Timer timer;
	private String gameName = "ENDEAVOR";
	private SoundClip menuSound = new SoundClip(this.getClass().getResource("/menuClick.wav"));
	JLabel start = new JLabel();
	JLabel pvp = new JLabel();
	JLabel help = new JLabel();
	JLabel exit = new JLabel();
	JPanel panel = new JPanel();

	Font small;
	Font big;
	private int i;
	public ArrayList<Stars> stars;
	public static boolean startGame;
	public static boolean pvpGame;
	public boolean helpGame;
	public boolean exitGame;
	private int count;
	private Image infoImg;

	public Menu() {
		try {
			infoImg = ImageIO.read(this.getClass().getResource("/INFO_IMG.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		stars = new ArrayList<Stars>();
		small = new Font("Velvenda Cooler", Font.ITALIC, 18);
		big = new Font("Velvenda Cooler", Font.ITALIC, 18);
		// STARTGAME = 1
		startGame = false;
		// PVPGAME = 2
		pvpGame = false;
		// HELP = 3
		helpGame = false;
		// EXIT =4
		exitGame = false;
		count = 1;

		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 500));
		setBackground(Color.black);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setFocusable(true);
		panel.setBackground(new Color(0, 0, 0, 0));

		for (i = 0; i < 400; i++) {
			stars.add(new Stars());
		}

		start.setText("Start  ");
		pvp.setText("PvP  ");
		help.setText("Help  ");
		exit.setText("Exit  ");
		checkLocationOnMenu();

		panel.add(start);
		panel.add(pvp);
		panel.add(help);
		panel.add(exit);

		add(panel);
		panel.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					count++;
					if (count > 4)
						count = 4;
					checkLocationOnMenu();
					menuSound.play();
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					count--;
					if (count < 1)
						count = 1;
					checkLocationOnMenu();
					menuSound.play();
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					setAllFalse();
					if (count == 1)
						startGame = true;
					if (count == 2)
						pvpGame = true;
					if (count == 3)
						helpGame = true;
					if (count == 4)
						exitGame = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (helpGame == true)
						helpGame = false;
				}
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		});
		timer = new Timer(9, new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (i = 0; i < stars.size(); i++) {
					Stars s = (Stars) stars.get(i);
					s.move();
				}
				repaint();
			}
		});
		timer.start();
	}

	public void checkLocationOnMenu() {
		if (count == 1) {
			start.setForeground(Color.yellow);
			start.setFont(big);
		} else {
			start.setForeground(Color.white);
			start.setFont(small);
		}
		if (count == 2) {
			pvp.setForeground(Color.yellow);
			pvp.setFont(big);
		} else {
			pvp.setForeground(Color.white);
			pvp.setFont(small);
		}
		if (count == 3) {
			help.setForeground(Color.yellow);
			help.setFont(big);
		} else {
			help.setForeground(Color.white);
			help.setFont(small);
		}
		if (count == 4) {
			exit.setForeground(Color.yellow);
			exit.setFont(big);
		} else {
			exit.setForeground(Color.white);
			exit.setFont(small);
		}

	}

	public void setAllFalse() {
		startGame = false;
		exitGame = false;
		helpGame = false;
		pvpGame = false;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.black);
		g2d.fillRect(0, 0, 1280, 720);
		for (i = 0; i < stars.size(); i++) {
			Stars s = (Stars) stars.get(i);
			g2d.setColor(s.getColor());
			if (s.getX() < 1280)
				g2d.fillOval(s.getX(), s.getY(), s.getRadius(), s.getRadius());
		}
		g.setFont(new Font("Velvenda Cooler", Font.HANGING_BASELINE, 48));
		g.setColor(Color.blue);
		g2d.drawString(gameName, getWidth() / 2 - getWidth() / 10, getHeight() / 4);
		checkLocationOnMenu();

		if (helpGame) {
			g2d.drawImage(infoImg, 0, 0, null);
		}
	}
}
