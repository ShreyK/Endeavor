package com.khosla.endeavor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Main extends JFrame {
	// The Frame we will be playing the game on
	// private int WIDTH = 160;
	// private int HEIGHT = WIDTH/12*9;
	// private int SCALE = 5;

	JFrame frame = new JFrame();
	Scanner scan = new Scanner(System.in);
	Menu menu;
	Board board;
	Timer timer;
	PvP pvp;

	public Main() {
		menu = new Menu();
		// Adds the menu Screen
		frame.add(menu);

		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(1280, 720);
		// Sets the window to be in center of screen
		frame.setLocationRelativeTo(null);
		frame.setTitle("Endeavor");
		frame.setResizable(false);
		frame.setVisible(true);

		timer = new Timer(9, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (menu.exitGame) {
					System.exit(0);
				}
				if (menu.startGame) {
					if (board == null)
						board = new Board();
					menu.setVisible(false);
					frame.add(board);
					board.requestFocusInWindow();
					if (board.restartGame())
						board = new Board();
					if (board.getMenu()) {
						if (menu == null)
							menu = new Menu();
						frame.remove(board);
						board = null;
						menu.setAllFalse();
						menu.setVisible(true);
						menu.panel.requestFocusInWindow();
					}
				}
				if (menu.pvpGame) {
					if (pvp == null)
						pvp = new PvP();
					menu.setVisible(false);
					frame.add(pvp);
					pvp.requestFocusInWindow();
					if (pvp.restartGame())
						pvp = new PvP();
					if (pvp.getMenu()) {
						if (menu == null)
							menu = new Menu();
						frame.remove(pvp);
						pvp = null;
						menu.setAllFalse();
						menu.setVisible(true);
						menu.panel.requestFocusInWindow();
					}
				}
			}
		});
		timer.start();
	}

	public static void main(String[] args) {
		new Main();
	}
}
