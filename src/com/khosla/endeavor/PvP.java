package com.khosla.endeavor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.khosla.background.Planets;
import com.khosla.background.Stars;
import com.khosla.spriteSystems.Energy;
import com.khosla.spriteSystems.EnergyRegen;
import com.khosla.spriteSystems.Health;
import com.khosla.spriteSystems.HealthRegen;
import com.khosla.spriteSystems.Missile;
import com.khosla.sprites.Craft;
import com.khosla.utilities.Distance;
import com.khosla.utilities.Money;
import com.khosla.utilities.SoundClip;

@SuppressWarnings("serial")
public class PvP extends JPanel {
	private Craft player1;
	private Craft player2;
	private Money money;
	private Distance distance;
	private Health player1Health;
	private Energy player1Energy;
	private Health player2Health;
	private Energy player2Energy;
	private Planets planet;
	private Timer timer;
	private int count = 0;
	private int B_WIDTH;
	private int B_HEIGHT;
	private int i;
	private int p;
	private int x1, x2, y1, y2;
	private int escapeCount;
	private int menuInt;
	private int menuPointer;
	private boolean ingame;
	private boolean player1Win;
	private boolean player2Win;
	private boolean gotDamaged;
	private boolean isPaused;
	private boolean restart;
	private boolean helpGame;
	private boolean exitGame;
	private boolean menuGame;
	private boolean left;
	private boolean right;
	private Color pauseColor = new Color(28, 28, 28, 200);
	private Color darkGreen = new Color(0, 225, 0, 255);
	private Color darkYellow = new Color(235, 230, 7, 255);
	private Color darkOrange = new Color(255, 150, 0, 255);
	private ArrayList<HealthRegen> healthPacks;
	private ArrayList<EnergyRegen> energyPacks;
	private ArrayList<Stars> stars;
	private ArrayList<Missile> ms;
	private ArrayList<Missile> ems;
	private BufferedImage infoImgPoint;
	private BufferedImage imgPlanet;
	private BufferedImage img;
	private BufferedImage img1;
	private BufferedImage img2;
	private BufferedImage img3;
	private BufferedImage infoImg;
	private BufferedImage infoImgLeft;
	private BufferedImage infoImgRight;
	private Random rand = new Random();
	private SoundClip missileSound = new SoundClip(this.getClass().getResource("/missileShot.wav"));
	private SoundClip hitSound;
	private SoundClip pointSound;
	private SoundClip healthSound;
	private SoundClip energySound;
	private SoundClip menuSound = new SoundClip(this.getClass().getResource("/menuClick.wav"));
	private SoundClip menuEnterSound;
	private SoundClip dangerSound;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PvP() {
		setFocusable(true);
		setBackground(Color.black);
		setDoubleBuffered(true);
		setSize(1280, 720);
		ingame = true;

		try {
			img = ImageIO.read(this.getClass().getResource("/SPRITE_SHEET.png"));
		} catch (IOException e) {
			System.out.println("Damage is the problem!");
		}
		try {
			infoImg = ImageIO.read(this.getClass().getResource("/INFO_IMG.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			imgPlanet = ImageIO.read(this.getClass().getResource("/planet.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// Damage1
		img1 = img.getSubimage(0, 32, 16, 16);
		// Damage2
		img2 = img.getSubimage(16, 32, 16, 16);
		// Damage3
		img3 = img.getSubimage(32, 32, 16, 16);

		// InfoImgPointer
		infoImgPoint = img.getSubimage(32, 0, 16, 32);
		// InfoImgLeft
		infoImgLeft = infoImg.getSubimage(0, 0, 640, 720);
		// InfoImgRight
		infoImgRight = infoImg.getSubimage(640, 0, 640, 720);

		// player1 Spawn
		player1 = new Craft(5, 300, img, true);
		player2 = new Craft(1100, 300, img, false);
		player1Health = new Health(player1.getX(), player1.getY());
		player1Energy = new Energy(player1.getX(), player1.getY());
		player2Health = new Health(player2.getX(), player2.getY());
		player2Energy = new Energy(player2.getX(), player2.getY());

		distance = new Distance(0);
		money = new Money(img);
		planet = new Planets();

		B_WIDTH = 1280;
		B_HEIGHT = 720;
		escapeCount = 0;
		p = 0;
		menuPointer = 0;
		menuInt = 1;

		isPaused = false;
		restart = false;
		menuGame = false;
		exitGame = false;
		helpGame = false;
		left = true;
		right = false;
		player1Win = false;
		player2Win = false;

		healthPacks = new ArrayList();
		energyPacks = new ArrayList();
		stars = new ArrayList();

		for (i = 0; i < 400; i++) {
			stars.add(new Stars());
		}

		timer = new Timer(9, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isPaused) {
					pauseMenu();
				} else {
					if (player1Health.getHealth() == 0) {
						player1Health.setWidth(0);
						player2Win = true;
						ingame = false;
					}
					if (player2Health.getHealth() == 0) {
						player2Health.setWidth(0);
						player1Win = true;
						ingame = false;

					}
					// Updating all the Logic
					player1.move();
					player2.move();
					distance.moveDistance();
					checkCollisions();
					if (distance.getDistance() % 100 == 0)
						money.getIncome();
					updateGame();

				}
				// Drawing on Screen
				updateScreen();
			}
		});
		timer.start();

		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();

				if (key == KeyEvent.VK_ESCAPE) {
					escapeCount++;
					if (ingame) {
						if (escapeCount % 2 != 0) {
							isPaused = true;
						} else {
							isPaused = false;
							timer.start();
						}
					}
				}
				// PAUSE SCREEN
				if (isPaused) {
					if (key == KeyEvent.VK_UP) {
						menuInt--;
						menuPointer -= 50;
						if (menuPointer < 0)
							menuPointer = 0;
					}
					if (key == KeyEvent.VK_DOWN) {
						menuInt++;
						menuPointer += 50;
						if (menuPointer > 150)
							menuPointer = 150;
					}
					if (key == KeyEvent.VK_ENTER) {
						setAllFalse();
						if (menuInt == 1)
							helpGame = true;
						if (menuInt == 2)
							restart = true;
						if (menuInt == 3)
							menuGame = true;
						if (menuInt == 4)
							exitGame = true;
					}
					if (key == KeyEvent.VK_RIGHT) {
						if (left) {
							right = true;
							left = false;
						}
					}
					if (key == KeyEvent.VK_LEFT) {
						if (right) {
							right = false;
							left = true;
						}
					}
				}
				// GAME SCREEN
				else {
					player1.keyPressed(e, true);
					player2.keyPressed(e, false);
					if (key == KeyEvent.VK_SPACE) {
						if (player1.getEnoughEnergy()) {
							player1Energy.setEnergy(player1Energy.getEnergy() - 20);
							player1Energy.setEnergyWidth(player1Energy.getEnergyWidth() - 20);
							missileSound.play();
						}
						if (player1Energy.getEnergy() < 20)
							player1.setEnoughEnergy(false);
						else
							player1.setEnoughEnergy(true);
					}
					if (key == KeyEvent.VK_ENTER) {
						if (player2.getEnoughEnergy()) {
							player2Energy.setEnergy(player2Energy.getEnergy() - 20);
							player2Energy.setEnergyWidth(player2Energy.getEnergyWidth() - 20);
							missileSound.play();
						}
						if (player2Energy.getEnergy() < 20)
							player2.setEnoughEnergy(false);
						else
							player2.setEnoughEnergy(true);
						if (!ingame)
							restart = true;
					}
				}
			}

			public void keyReleased(KeyEvent e) {
				player1.keyReleased(e, true);
				player2.keyReleased(e, false);
			}

			public void keyTyped(KeyEvent e) {
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		for (i = 0; i < stars.size(); i++) {
			// Stars
			Stars s = (Stars) stars.get(i);
			g.setColor(s.getColor());
			if (s.getX() < 1280)
				g2d.fillOval(s.getX(), s.getY(), s.getRadius(), s.getRadius());
		}
		// Planet
		g2d.drawImage(imgPlanet, 525, -50, this);

		if (ingame) {

			if (player1.isVisible() && player2.isVisible()) {
				// Player1
				g2d.drawImage(player1.getImage(), player1.getX(), player1.getY(), this);
				// Player2
				g2d.drawImage(player2.getImage(), player2.getX(), player2.getY(), this);

				// EnergyBarBorder(s)
				g.setColor(Color.lightGray);
				g2d.drawRect(19, 25 + 3, 203, 19);
				g.setColor(Color.black);
				g2d.drawRect(20, 26 + 3, 201, 17);

				// EnergyBar
				g.setColor(Color.yellow);
				g2d.fillRect(21, 27 + 3, player1Energy.getEnergyWidth(), player1Energy.getEnergyHeight());
				g.setColor(darkYellow); // To give a better look
				g2d.fillRect(21, 27 + 10, player1Energy.getEnergyWidth(), player1Energy.getEnergyHeight() / 2 + 2);

				// BarLineSeperatorsEnergy
				for (i = 0; i < 200; i += 10) {
					x1 = 21 + i;
					y1 = 30;
					x2 = x1;
					y2 = 30 + player1Energy.getEnergyHeight();

					g.setColor(Color.DARK_GRAY);
					g2d.drawLine(x1, y1, x2, y2);
				}

				// PLAYER1 HealthBarBorder(s)
				g.setColor(Color.red);
				g2d.drawRect(19, 3 + 3, 153, 19);
				g.setColor(Color.black);
				g2d.drawRect(20, 4 + 3, 151, 17);

				// PLAYER1 HealthBar
				g.setColor(Color.green);
				player1Health.setX(player1.getX() - 10);
				player1Health.setY(player1.getY() - 15);
				if (player1Health.getHealth() <= 200)
					g.setColor(Color.orange);
				g2d.fillRect(21, 5 + 3, player1Health.getWidth(), player1Health.getHeight());
				g.setColor(darkGreen);
				if (player1Health.getHealth() <= 200)
					g.setColor(darkOrange);
				g2d.fillRect(21, 5 + 10, player1Health.getWidth(), player1Health.getHeight() / 2 + 2);

				// PLAYER1 BarLineSeperatorsHealth
				for (i = 0; i < 150; i += 8) {
					x1 = 21 + i;
					y1 = 8;
					x2 = x1;
					y2 = 8 + player1Health.getHeight();

					g.setColor(Color.DARK_GRAY);
					g2d.drawLine(x1, y1, x2, y2);
				}

				// PLAYER2 EnergyBarBorder(s)
				g.setColor(Color.lightGray);
				g2d.drawRect(900 + 19, 25 + 3, 203, 19);
				g.setColor(Color.black);
				g2d.drawRect(900 + 20, 26 + 3, 201, 17);

				// PLAYER2 SheildBar
				g.setColor(Color.yellow);
				if (player2Energy.getEnergy() <= 100)
					g.setColor(Color.yellow);
				g2d.fillRect(900 + 21, 27 + 3, player2Energy.getEnergyWidth(), player2Energy.getEnergyHeight());
				g.setColor(darkYellow);
				g2d.fillRect(900 + 21, 27 + 10, player2Energy.getEnergyWidth(), player2Energy.getEnergyHeight() / 2 + 2);

				// PLAYER2 BarLineSeperatorsEnergy
				for (i = 0; i < 200; i += 8) {
					x1 = 900 + 21 + i;
					y1 = 30;
					x2 = x1;
					y2 = 30 + player1Energy.getEnergyHeight();

					g.setColor(Color.DARK_GRAY);
					g2d.drawLine(x1, y1, x2, y2);
				}

				// PLAYER2 HealthBarBorder(s)
				g.setColor(Color.red);
				g2d.drawRect(919, 3 + 3, 153, 19);
				g.setColor(Color.black);
				g2d.drawRect(920, 4 + 3, 151, 17);

				// PLAYER2 HealthBar
				g.setColor(Color.green);
				player2Health.setX(player2.getX() - 10);
				player2Health.setY(player2.getY() - 15);
				if (player2Health.getHealth() <= 200)
					g.setColor(Color.orange);
				g2d.fillRect(921, 5 + 3, player2Health.getWidth(), player2Health.getHeight());
				g.setColor(darkGreen);
				if (player2Health.getHealth() <= 200)
					g.setColor(darkOrange);
				g2d.fillRect(921, 5 + 10, player2Health.getWidth(), player2Health.getHeight() / 2 + 2);

				// PLAYER2 BarLineSeperatorsHealth
				for (i = 0; i < 150; i += 8) {
					x1 = 921 + i;
					y1 = 8;
					x2 = x1;
					y2 = 8 + player1Health.getHeight();

					g.setColor(Color.DARK_GRAY);
					g2d.drawLine(x1, y1, x2, y2);
				}

				// DamageToplayer1
				if (player1Health.getHealth() <= 100) {
					g.setColor(Color.red);
					if (p < 10)
						g.drawImage(img1, player1.getX(), player1.getY(), this);
					else if (p < 20)
						g.drawImage(img2, player1.getX(), player1.getY(), this);
					else if (p < 30)
						g.drawImage(img3, player1.getX(), player1.getY(), this);
					else
						g.drawImage(img2, player1.getX(), player1.getY(), this);
					p++;
					if (p > 40)
						p = 0;
				}
				if (player2Health.getHealth() <= 100) {
					g.setColor(Color.red);
					if (p < 10)
						g.drawImage(img1, player2.getX(), player2.getY(), this);
					else if (p < 20)
						g.drawImage(img2, player2.getX(), player2.getY(), this);
					else if (p < 30)
						g.drawImage(img3, player2.getX(), player2.getY(), this);
					else
						g.drawImage(img2, player2.getX(), player2.getY(), this);
					p++;
					if (p > 40)
						p = 0;
				}
			}

			ms = player1.getMissiles();
			ems = player2.getMissiles();
			for (i = 0; i < stars.size(); i++) {
				// Missiles
				if (ms.size() != 0) {
					if (i < ms.size()) {
						Missile m = (Missile) ms.get(i);
						if (!m.isOffScreen())
							g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
					}
				}
				if (ems.size() != 0) {
					if (i < ems.size()) {
						Missile em = (Missile) ems.get(i);
						if (!em.isOffScreen())
							g2d.drawImage(em.getImage(), em.getX(), em.getY(), this);
					}
				}
			}

			if (isPaused) {
				// Darken Background
				g.setColor(pauseColor);
				g.fillRect(0, 0, 1280, 720);

				// Darken Text Background even more
				g.setColor(new Color(204, 204, 204, 200));
				g.fillRect(200, 0, 400, 720);

				// TextBackLash
				g2d.setFont(new Font("Velvenda Cooler", Font.PLAIN, 32));
				g.setColor(Color.black);
				g2d.drawString("Help", 252, 252);
				g2d.drawString("Restart", 252, 302);
				g2d.drawString("Menu", 252, 352);
				g2d.drawString("exit Game", 252, 402);

				// Text
				g.setColor(Color.white);
				g2d.drawString("Help", 250, 250);
				g2d.drawString("Restart", 250, 300);
				g2d.drawString("Menu", 250, 350);
				g2d.drawString("exit Game", 250, 400);

				// Pointer
				g2d.drawImage(infoImgPoint, 220, 220 + menuPointer, null);
				// g2d.drawLine(240, 235 + menuPointer, 230, 225 + menuPointer);
				// g2d.drawLine(230, 245 + menuPointer, 240, 235 + menuPointer);

				if (helpGame) {
					g2d.setColor(Color.DARK_GRAY);
					g2d.fillOval(925, 650, 10, 10);
					g2d.fillOval(1000, 650, 10, 10);

					if (left) {
						g2d.drawImage(infoImgLeft, 650, 0, null);
						g2d.setColor(Color.white);
						g2d.fillOval(925, 650, 10, 10);
					} else {
						g2d.drawImage(infoImgRight, 650, 0, null);
						g2d.setColor(Color.white);
						g2d.fillOval(1000, 650, 10, 10);
					}
				}
			}

		} else {
			Font big = new Font("Velvenda Cooler", Font.PLAIN, 42);
			Font small = new Font("Velvenda Cooler", Font.PLAIN, 25);

			timer.stop();
			String msg = "Wins";
			g.setFont(big);
			g.setColor(Color.blue);
			if (player1Win) {
				g2d.drawImage(player1.getImage(), (B_WIDTH / 2) - 50, (B_HEIGHT / 2) - 135, null);
			} else {
				g2d.drawImage(player2.getImage(), (B_WIDTH / 2) - 50, (B_HEIGHT / 2) - 135, null);
			}
			g.drawString(msg, (B_WIDTH / 2) - 10, (B_HEIGHT / 2) - 100);

			g.setFont(small);
			g.setColor(Color.white);
			g.drawString("Press Enter to Restart", 550, 630);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();

		if (restart) {
			g.clearRect(0, 0, 1280, 720);
			g.dispose();
		}
	}

	public void updateGame() {
		// Energy Regeneration every Second
		if (distance.getDistance() % 100 == 0) {
			player1Energy.setEnergy(player1Energy.getEnergy() + 4);
			player1Energy.setEnergyWidth(player1Energy.getEnergyWidth() + 3);
			player2Energy.setEnergy(player2Energy.getEnergy() + 4);
			player2Energy.setEnergyWidth(player2Energy.getEnergyWidth() + 3);
		}
		planet.move();
		if (planet.getX() < 0)
			planet.setVisible(false);

		ms = player1.getMissiles();
		ems = player2.getMissiles();
		// Updating Stars
		for (i = 0; i < stars.size(); i++) {
			// Updating Player1 Missiles
			if (ms.size() != 0) {
				if (i < ms.size()) {
					Missile m = (Missile) ms.get(i);
					if (m.isVisible())
						m.move();
					else
						ms.remove(i);
				}
			}
			// Updating Player2 Missiles
			if (ems.size() != 0) {
				if (i < ems.size()) {
					Missile em = (Missile) ems.get(i);
					if (em.isVisible())
						em.move();
					else
						ems.remove(i);
				}
			}

			// Updating HealthPacks
			if (healthPacks.size() != 0) {
				if (i < healthPacks.size()) {
					HealthRegen h = (HealthRegen) healthPacks.get(i);
					if (h.isVisible())
						h.move();
					else
						healthPacks.remove(i);
				}
			}

			// Updating EnergyPacks
			if (energyPacks.size() != 0) {
				if (i < energyPacks.size()) {
					EnergyRegen e = (EnergyRegen) energyPacks.get(i);
					if (e.isVisible())
						e.move();
					else
						energyPacks.remove(i);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void checkCollisions() {
		ms = player1.getMissiles();
		ems = player2.getMissiles();

		Rectangle player1R = player1.getBounds();
		Rectangle player2R = player2.getBounds();

		if (player1R.intersects(player2R)) {
			if (player1Health.getHealth() > 0 && player2Health.getHealth() > 0) {

				player1Health.setHealth(player1Health.getHealth() - 100);
				player1Health.setWidth(player1Health.getWidth() - 30);

				player2Health.setHealth(player2Health.getHealth() - 100);
				player2Health.setWidth(player2Health.getWidth() - 30);
			}
		}

		for (i = 0; i < stars.size(); i++) {
			// Plyaer1 Missile Hitting Player2
			if (ms.size() != 0 && i < ms.size()) {
				Missile m = (Missile) ms.get(i);
				Rectangle missileR = m.getBounds();
				if (missileR.intersects(player2R)) {
					money.addMoney(10);
					player2Health.setHealth(player2Health.getHealth() - 100);
					player2Health.setWidth(player2Health.getWidth() - 30);
					m.isHit();
				}
			}
			// Plyaer2 Missile Hitting Player1
			if (ems.size() != 0 && i < ems.size()) {
				Missile em = (Missile) ems.get(i);
				Rectangle missileR = em.getBounds();
				if (missileR.intersects(player1R)) {
					money.addMoney(10);
					player1Health.setHealth(player1Health.getHealth() - 100);
					player1Health.setWidth(player1Health.getWidth() - 30);
					em.isHit();
				}
			}
			// Missile Collisions
			if (ms.size() != 0 && i < ms.size()) {
				if (ems.size() != 0 && i < ems.size()) {
					Missile m = (Missile) ms.get(i);
					Rectangle mR = m.getBounds();

					Missile em = (Missile) ems.get(i);
					Rectangle emR = em.getBounds();

					if (mR.intersects(emR)) {
						m.isHit();
						em.isHit();
					}
				}
			}
		}

		// HEALTHPACKS COLLISION
		for (i = 0; i < healthPacks.size(); i++) {
			HealthRegen h = (HealthRegen) healthPacks.get(i);
			Rectangle healthR = h.getBounds();

			if (player1R.intersects(healthR)) {
				if (player1Health.getHealth() < 500) {
					if (player1Health.getWidth() < 150) {
						player1Health.setHealth(player1Health.getHealth() + 100);
						player1Health.setWidth(player1Health.getWidth() + 30);
						h.setVisible(false);
					}
				}
			}
		}
		// ENERGYPACKS COLLISION
		for (i = 0; i < energyPacks.size(); i++) {
			EnergyRegen e = (EnergyRegen) energyPacks.get(i);
			Rectangle energyR = e.getBounds();

			if (player1R.intersects(energyR)) {
				if (player1Energy.getEnergy() < 200) {
					if (player1Energy.getEnergyWidth() < 150) {
						player1Energy.setEnergy(200);
						player1Energy.setEnergyWidth(150);
						e.setVisible(false);
					}
				}
			}
		}
	}

	public void pauseMenu() {
		if (menuInt < 1)
			menuInt = 1;
		if (menuInt > 4)
			menuInt = 4;

		if (exitGame)
			System.exit(0);
		if (restart)
			timer.stop();
	}

	private void setAllFalse() {
		helpGame = false;
		restart = false;
		exitGame = false;
		menuGame = false;
	}

	public int getPositionX() {
		return 1280 + rand.nextInt(500) + 1;
	}

	public int getPositionY() {
		return rand.nextInt(720) + 1;
	}

	public void updateScreen() {
		repaint();
	}

	public boolean restartGame() {
		return restart;
	}

	public boolean getMenu() {
		return menuGame;
	}
}
