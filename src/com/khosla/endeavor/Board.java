package com.khosla.endeavor;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
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
import com.khosla.spriteSystems.Points;
import com.khosla.sprites.Alien;
import com.khosla.sprites.Craft;
import com.khosla.sprites.FastAlien;
import com.khosla.utilities.Distance;
import com.khosla.utilities.Money;
import com.khosla.utilities.SaveAndRead;
import com.khosla.utilities.SoundClip;

@SuppressWarnings("serial")
public class Board extends JPanel {
	private Craft craft;
	private Money money;
	private Distance distance;
	private Health craftHealth;
	private Energy craftEnergy;
	@SuppressWarnings("unused")
	private Points pointCount;
	private Planets planet;
	private Timer timer;
	private SaveAndRead SR;
	private int count = 0;
	private int B_WIDTH;
	private int B_HEIGHT;
	private int pointsGathered;
	private int aliensKilled;
	private int totalAliens;
	private int x1, y1, x2, y2;
	private int i;
	private int p;
	private int distance1, distance2;
	private int escapeCount;
	private int menuInt;
	private int menuPointer;
	private boolean ingame;
	@SuppressWarnings("unused")
	private boolean allAliensDead = false;
	private boolean hitPoint;
	private boolean hitAlien;
	@SuppressWarnings("unused")
	private boolean gotDamaged;
	private boolean hitFastAlien;
	private boolean isPaused;
	private boolean restart;
	private boolean menuGame;
	private boolean helpGame;
	private boolean exitGame;
	private boolean left;
	private boolean right;
	private Color pauseColor = new Color(28, 28, 28, 200);
	private Color darkGreen = new Color(0, 225, 0, 255);
	private Color darkYellow = new Color(235, 230, 7, 255);
	private Color darkOrange = new Color(255, 160, 0, 255);
	private ArrayList<Alien> aliens;
	private ArrayList<FastAlien> FastAliens;
	private ArrayList<Planets> planets;
	private ArrayList<HealthRegen> healthPacks;
	private ArrayList<EnergyRegen> energyPacks;
	private ArrayList<Stars> stars;
	private ArrayList<Points> points;
	private ArrayList<Missile> ms;
	private BufferedImage imgPlanet;
	private BufferedImage img;
	private BufferedImage img1;
	private BufferedImage img2;
	private BufferedImage img3;
	private BufferedImage infoImg;
	private BufferedImage infoImgLeft;
	private BufferedImage infoImgRight;
	private BufferedImage infoImgPoint;
	private SoundClip missileSound = new SoundClip(this.getClass().getResource("/missileShot.wav"));
	private SoundClip hitSound;
	private SoundClip pointSound;
	private SoundClip healthSound;
	private SoundClip energySound;
	private SoundClip menuSound = new SoundClip(this.getClass().getResource("/menuClick.wav"));
	private SoundClip menuEnterSound;
	private SoundClip dangerSound;
	private Random rand = new Random();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Board() {
		setFocusable(true);
		setBackground(Color.black);
		setDoubleBuffered(true);
		setSize(1280, 720);
		ingame = true;

		try {
			imgPlanet = ImageIO.read(this.getClass().getResource("/planet.png"));
			img = ImageIO.read(this.getClass().getResource("/SPRITE_SHEET.png"));
			infoImg = ImageIO.read(this.getClass().getResource("/INFO_IMG.png"));
		} catch (IOException e) {
			e.printStackTrace();
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

		// Craft Spawn
		craft = new Craft(5, 300, img, true);
		craftHealth = new Health(craft.getX(), craft.getY());
		craftEnergy = new Energy(craft.getX(), craft.getY());

		distance = new Distance(0);
		money = new Money(img);
		planet = new Planets();
		SR = new SaveAndRead();

		B_WIDTH = 1280;
		B_HEIGHT = 720;
		pointsGathered = 0;
		escapeCount = 0;
		p = 0;
		menuPointer = 0;
		menuInt = 1;

		isPaused = false;
		hitPoint = false;
		hitAlien = false;
		hitFastAlien = false;
		restart = false;
		exitGame = false;
		helpGame = false;
		left = true;
		right = false;

		FastAliens = new ArrayList();
		aliens = new ArrayList();
		healthPacks = new ArrayList();
		energyPacks = new ArrayList();
		stars = new ArrayList();
		points = new ArrayList();
		planets = new ArrayList();

		for (i = 0; i < 400; i++) {
			stars.add(new Stars());
		}

		totalAliens = aliens.size() + FastAliens.size();
		if (totalAliens == 0)
			allAliensDead = true;

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

						menuSound.play();
					}
					if (key == KeyEvent.VK_DOWN) {
						menuInt++;
						menuPointer += 50;
						if (menuPointer > 150)
							menuPointer = 150;

						menuSound.play();
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

							menuSound.play();
						}

					}
					if (key == KeyEvent.VK_LEFT) {
						if (right) {
							right = false;
							left = true;
							menuSound.play();
						}

					}
				}
				// GAME SCREEN
				else {
					craft.keyPressed(e, true);

					if (key == KeyEvent.VK_SPACE) {
						if (craft.getEnoughEnergy()) {
							craftEnergy.setEnergy(craftEnergy.getEnergy() - 20);
							craftEnergy.setEnergyWidth(craftEnergy.getEnergyWidth() - 20);
							missileSound.play();
						}
						if (craftEnergy.getEnergy() < 20)
							craft.setEnoughEnergy(false);
						if (craftEnergy.getEnergy() > 20 && craftEnergy.getEnergyWidth() > 20)
							craft.setEnoughEnergy(true);
					}
					if (key == KeyEvent.VK_ENTER) {
						if (!ingame)
							restart = true;
					}
				}
			}

			public void keyReleased(KeyEvent e) {
				craft.keyReleased(e, true);
			}

			public void keyTyped(KeyEvent e) {
			}
		});

		timer = new Timer(9, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isPaused) {
					SR.Read();
					System.out.println(SR.getRead());
					pauseMenu();
				} else
					updateGame(); // Update Game
				updateScreen(); // Drawing on Screen
			}
		});
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		for (i = 0; i < stars.size(); i++) {
			/*
			 * // Clouds for (i = 0; i < clouds.size(); i++) { Cloud c = (Cloud)
			 * clouds.get(i); g.setColor(c.getColor()); if (c.getX() < 1280)
			 * g2d.fillOval(c.getX(), c.getY(), c.getWidth(), c.getHeight()); }
			 */

			// Stars
			Stars s = (Stars) stars.get(i);
			g.setColor(s.getColor());
			if (s.getX() < 1280)
				g2d.fillOval(s.getX(), s.getY(), s.getRadius(), s.getRadius());
		}
		for (i = 0; i < planets.size(); i++) {
			if (planets.size() != 0) {
				if (i < planets.size()) {
					Planets p = (Planets) planets.get(i);
					g.setColor(p.getColor());
					if (p.getX() < 1280)
						g2d.fillOval(p.getX(), p.getY(), p.getRadius(), p.getRadius());
				}
			}
		}
		// Planet
		g2d.drawImage(imgPlanet, planet.getX() - 1900, 15, null);

		if (ingame) {

			if (craft.isVisible()) {
				// Craft
				g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(), null);

				// EnergyBarBorder(s)
				g.setColor(Color.lightGray);
				g2d.drawRect(19, 25 + 3, 203, 19);
				g.setColor(Color.black);
				g2d.drawRect(20, 26 + 3, 201, 17);

				// EnergyBar
				g.setColor(Color.yellow);
				g2d.fillRect(21, 27 + 3, craftEnergy.getEnergyWidth(), craftEnergy.getEnergyHeight());
				g.setColor(darkYellow); // To give a better look
				g2d.fillRect(21, 27 + 10, craftEnergy.getEnergyWidth(), craftEnergy.getEnergyHeight() / 2 + 2);

				// BarLineSeperatorsEnergy
				for (i = 0; i < 200; i += 10) {
					x1 = 21 + i;
					y1 = 30;
					x2 = x1;
					y2 = 30 + craftEnergy.getEnergyHeight();

					g.setColor(Color.DARK_GRAY);
					g2d.drawLine(x1, y1, x2, y2);
				}

				// HealthBarBorder(s)
				g.setColor(Color.red);
				g2d.drawRect(19, 3 + 3, 153, 19);
				g.setColor(Color.black);
				g2d.drawRect(20, 4 + 3, 151, 17);

				// HealthBar
				g.setColor(Color.green);
				if (craftHealth.getHealth() <= 200)
					g.setColor(Color.orange);
				g2d.fillRect(21, 5 + 3, craftHealth.getWidth(), craftHealth.getHeight());

				g.setColor(darkGreen);
				if (craftHealth.getHealth() <= 200)
					g.setColor(darkOrange);
				g2d.fillRect(21, 5 + 10, craftHealth.getWidth(), craftHealth.getHeight() / 2 + 2);

				// BarLineSeperatorsHealth
				for (i = 0; i < 150; i += 10) {
					x1 = 21 + i;
					y1 = 8;
					x2 = x1;
					y2 = 8 + craftHealth.getHeight();

					g.setColor(Color.DARK_GRAY);
					g2d.drawLine(x1, y1, x2, y2);
				}

				// ANIMATIONS OF AWESOMENESS......I AM A GOD
				// DamageToCraft
				if (craftHealth.getHealth() <= 100) {
					g.setColor(Color.red);
					if (p < 10)
						g.drawImage(img1, craft.getX(), craft.getY(), null);
					else if (p < 20)
						g.drawImage(img2, craft.getX(), craft.getY(), null);
					else if (p < 30)
						g.drawImage(img3, craft.getX(), craft.getY(), null);
					else
						g.drawImage(img2, craft.getX(), craft.getY(), null);
					p++;
					if (p > 40)
						p = 0;
				}
			}

			ms = craft.getMissiles();

			for (i = 0; i < stars.size(); i++) {
				// Missiles
				if (ms.size() != 0) {
					if (i < ms.size()) {
						Missile m = (Missile) ms.get(i);
						if (!m.isOffScreen())
							g2d.drawImage(m.getImage(), m.getX(), m.getY(), null);
					}
				}
				// Health Packs
				if (healthPacks.size() != 0) {
					if (i < healthPacks.size()) {
						HealthRegen h = (HealthRegen) healthPacks.get(i);
						if (!h.isOffScreen())
							g2d.drawImage(h.getImage(), h.getX(), h.getY(), null);
					}
				}
				// Energy Packs
				if (energyPacks.size() != 0) {
					if (i < energyPacks.size()) {
						EnergyRegen e = (EnergyRegen) energyPacks.get(i);
						if (!e.isOffScreen())
							g2d.drawImage(e.getImage(), e.getX(), e.getY(), null);
					}
				}
				// Points
				if (points.size() != 0) {
					if (i < points.size()) {
						Points p = (Points) points.get(i);
						if (!p.isOffScreen())
							g2d.drawImage(p.getImage(), p.getX(), p.getY(), null);
					}
				}
				// Aliens
				if (aliens.size() != 0) {
					if (i < aliens.size()) {
						Alien a = (Alien) aliens.get(i);
						if (!a.isOffScreen())
							g2d.drawImage(a.getImage(), a.getX(), a.getY(), null);
					}
				}
				// Fast Aliens
				if (FastAliens.size() != 0) {
					if (i < FastAliens.size()) {
						FastAlien ba = (FastAlien) FastAliens.get(i);
						if (!ba.isOffScreen())
							g2d.drawImage(ba.getImage(), ba.getX(), ba.getY(), null);
					}
				}
			}

			g2d.setColor(Color.WHITE);
			g2d.drawString("Distance " + distance.getDistance(), 5, 640);
			g2d.drawString("Aliens left: " + (aliens.size() + FastAliens.size()), 5, 655);
			g2d.drawString("Health Packs: " + healthPacks.size(), 5, 670);
			g2d.drawString("Rare Materials Found: " + pointsGathered, 5, 685);
			g2d.drawImage(money.getImage(), money.getX(), money.getY(), null);
			g2d.drawString("  " + money.getMoney(), money.getX() + money.getWidth(), money.getY() + money.getHeight()
					/ 2 + 5);

			distance1 = distance.getDistance();
			distance2 = distance1 + 700;

			if (distance.getDistance() < distance2) {
				g2d.setColor(Color.yellow);
				if (hitAlien)
					g2d.drawString("+5", craft.getX() + craft.getWidth() + craft.getWidth() / 2, craft.getY());
				if (hitFastAlien)
					g2d.drawString("+10", craft.getX() + craft.getWidth() + craft.getWidth() / 2, craft.getY());
				if (hitPoint)
					g2d.drawString("+20", craft.getX() + craft.getWidth() + craft.getWidth() / 2, craft.getY());
			}
			hitAlien = false;
			hitFastAlien = false;
			hitPoint = false;

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

			timer.stop();
			aliensKilled = count + 1;
			String msg = "Game Over";

			Font big = new Font("Velvenda Cooler", Font.PLAIN, 42);
			Font small = new Font("Velvenda Cooler", Font.PLAIN, 25);
			FontMetrics metr = this.getFontMetrics(big);

			g.setColor(Color.red);
			g.setFont(big);
			g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, (B_HEIGHT / 2) - 200);
			g.setColor(Color.orange);
			g.setFont(small);
			g.drawString("Materials Gathered: " + pointsGathered, (B_WIDTH / 2) - 90, (B_HEIGHT / 2) + 30);
			g.drawString("Aliens Killed: " + aliensKilled, (B_WIDTH / 2) - 90, (B_HEIGHT / 2) + 60);
			g.drawString("Distance Travelled: " + distance.getDistance(), (B_WIDTH / 2) - 90, (B_HEIGHT / 2) + 90);
			g.drawString("Gold Gathered: " + money.getMoney(), (B_WIDTH / 2) - 90, (B_HEIGHT / 2) + 120);
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

	public void checkCollisions() {
		ms = craft.getMissiles();
		// Setting the Aliens to be placed farther away from each other
		/*
		 * for (i = 1; i < aliens.size(); i++) { Alien a1 = (Alien)
		 * aliens.get(i); Alien a2 = (Alien) aliens.get(i - 1); Rectangle r1 =
		 * a1.getBounds(); Rectangle r2 = a2.getBounds(); if (r1.intersects(r2))
		 * { a1.setX(a2.getX() + rand.nextInt(10) + 1); a1.setY(a2.getY() +
		 * rand.nextInt(10) + 1); a2.setX(a1.getX() + rand.nextInt(10) + 1);
		 * a2.setY(a1.getY() + rand.nextInt(10) + 1); } }
		 */
		Rectangle craftR = craft.getBounds();
		// HEALTHPACKS COLLISION
		for (i = 0; i < healthPacks.size(); i++) {
			HealthRegen h = (HealthRegen) healthPacks.get(i);
			Rectangle healthR = h.getBounds();

			if (craftR.intersects(healthR)) {
				if (craftHealth.getHealth() < 500) {
					if (craftHealth.getWidth() < 150) {
						craftHealth.setHealth(craftHealth.getHealth() + 100);
						craftHealth.setWidth(craftHealth.getWidth() + 30);
						h.setVisible(false);
					}
				}
			}
		}
		// ENERGYPACKS COLLISION
		for (i = 0; i < energyPacks.size(); i++) {
			EnergyRegen e = (EnergyRegen) energyPacks.get(i);
			Rectangle energyR = e.getBounds();

			if (craftR.intersects(energyR)) {
				if (craftEnergy.getEnergy() < 200) {
					if (craftEnergy.getEnergyWidth() < 150) {
						craftEnergy.setEnergy(200);
						craftEnergy.setEnergyWidth(150);
						e.setVisible(false);
					}
				}
			}
		}
		// POINTS COLLISION
		for (i = 0; i < points.size(); i++) {
			Points p = (Points) points.get(i);
			Rectangle pointR = p.getBounds();

			if (craftR.intersects(pointR)) {
				hitPoint = true;
				money.addMoney(20);
				pointsGathered++;
				p.setVisible(false);
			}
			if (aliens.size() != 0) {
				for (int j = 0; j < aliens.size(); j++) {
					Alien a = (Alien) aliens.get(j);
					if (pointR.intersects(a.getBounds()))
						p.setVisible(false);
				}
			}
		}

		if (aliens.size() != 0) {
			// ALIEN COLLISIONS
			for (int j = 0; j < aliens.size(); j++) {
				Alien a = (Alien) aliens.get(j);
				Rectangle alienR = a.getBounds();
				// HITTING ALIENS
				if (craftR.intersects(alienR)) {
					craft.knockback();
					if (craftHealth.getHealth() > 0) {
						craftHealth.setHealth(craftHealth.getHealth() - 100);
						craftHealth.setWidth(craftHealth.getWidth() - 30);
					}
					gotDamaged = true;
					a.setVisible(false);
				}

				if (ms.size() != 0) {
					for (i = 0; i < ms.size(); i++) {

						Missile m = (Missile) ms.get(i);
						Rectangle missileR = m.getBounds();
						// MISSILE HITTING ALIENS
						if (missileR.intersects(alienR)) {
							hitAlien = true;
							money.addMoney(5);
							if (rand.nextInt(4) == 1)
								healthPacks.add(new HealthRegen(a.getX(), a.getY(), img));
							a.setVisible(false);
							m.isHit();
						}
					}
				}
			}
		}
		if (FastAliens.size() != 0) {
			// FAST ALIENS COLLISIONS
			for (int j = 0; j < FastAliens.size(); j++) {
				FastAlien ba = (FastAlien) FastAliens.get(j);
				Rectangle FastAlienR = ba.getBounds();
				// HITTING FAST-ALIENS
				if (FastAlienR.intersects(craftR)) {
					craft.knockback();
					if (craftHealth.getHealth() > 0) {
						craftHealth.setHealth(craftHealth.getHealth() - 100);
						craftHealth.setWidth(craftHealth.getWidth() - 30);
					}
					gotDamaged = true;
					ba.setVisible(false);
				}
				for (i = 0; i < ms.size(); i++) {
					// MISSILE HITTING FAST-ALIENS
					if (ms.size() != 0) {
						Missile m = (Missile) ms.get(i);
						Rectangle missileR = m.getBounds();
						if (missileR.intersects(FastAlienR)) {
							hitFastAlien = true;
							money.addMoney(10);
							if (rand.nextInt(6) == 1)
								energyPacks.add(new EnergyRegen(ba.getX(), ba.getY(), img));
							ba.setVisible(false);
							m.isHit();
						}
					}
				}
			}
		}
	}

	public void updateGame() {
		if (craftHealth.getHealth() == 0) {
			craftHealth.setWidth(0);
			ingame = false;
			SR.Save(SR.getRead() + money.getMoney());
		}
		// Energy Regeneration every Second
		if (distance.getDistance() % 100 == 0) {
			craftEnergy.setEnergy(craftEnergy.getEnergy() + 5);
			craftEnergy.setEnergyWidth(craftEnergy.getEnergyWidth() + 5);
		}
		planet.move();
		if (planet.getX() < 0)
			planet.setVisible(false);
		if (distance.getDistance() % 100 == 0)
			money.getIncome();

		// Updating all the Logic
		craft.move();
		distance.moveDistance();
		spawn();
		checkCollisions();
		/*
		 * // Updating Clouds for (i = 0; i < clouds.size(); i++) { Cloud c =
		 * (Cloud) clouds.get(i); if (c.isVisible()) { c.move();
		 * 
		 * if (distance.getDistance() < 1280) c.setX(-1); else { if
		 * (distance.getDistance() % 100 == 0) c.setX(-1); }
		 * 
		 * } else clouds.remove(i); }
		 */

		ms = craft.getMissiles();
		// Updating Stars
		for (i = 0; i < stars.size(); i++) {
			// Updating Stars
			Stars s = (Stars) stars.get(i);
			if (s.isVisible())
				s.move();

			// Updating Craft Missiles
			if (ms.size() != 0) {
				if (i < ms.size()) {
					Missile m = (Missile) ms.get(i);
					if (m.isVisible())
						m.move();
					else
						ms.remove(i);
				}
			}

			// Updating planets
			if (planets.size() != 0) {
				if (i < planets.size()) {
					Planets p = (Planets) planets.get(i);
					if (p.isVisible())
						p.move();
					else {
						planets.remove(i);
					}
				}
			}

			// Updating Aliens
			if (aliens.size() != 0) {
				if (i < aliens.size()) {
					Alien a = (Alien) aliens.get(i);
					if (a.isVisible()) {
						a.move();
					} else {
						aliens.remove(i);
						count++;
					}
				}
			}

			// Updating Fast Aliens
			if (FastAliens.size() != 0) {
				if (i < FastAliens.size()) {
					FastAlien ba = (FastAlien) FastAliens.get(i);
					if (ba.isVisible())
						ba.move();
					else {
						FastAliens.remove(i);
						count++;
					}
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

			// Updating Points
			if (points.size() != 0) {
				if (i < points.size()) {
					Points p = (Points) points.get(i);
					if (p.isVisible())
						p.move();
					else
						points.remove(i);
				}
			}
		}
	}

	public void spawn() {
		if (totalAliens < 60) {
			if (distance.getDistance() % 1000 == 0) {
				aliens.add(new Alien(distance.getDistance() + getPositionX(), getPositionY(), img));
				aliens.add(new Alien(distance.getDistance() + getPositionX(), getPositionY(), img));
				aliens.add(new Alien(distance.getDistance() + getPositionX(), getPositionY(), img));
				aliens.add(new Alien(distance.getDistance() + getPositionX(), getPositionY(), img));
				aliens.add(new Alien(distance.getDistance() + getPositionX(), getPositionY(), img));
				FastAliens.add(new FastAlien(distance.getDistance() + getPositionX(), getPositionY(), img));
				FastAliens.add(new FastAlien(distance.getDistance() + getPositionX(), getPositionY(), img));
				FastAliens.add(new FastAlien(distance.getDistance() + getPositionX(), getPositionY(), img));
				FastAliens.add(new FastAlien(distance.getDistance() + getPositionX(), getPositionY(), img));
				FastAliens.add(new FastAlien(distance.getDistance() + getPositionX(), getPositionY(), img));
			}
		}
		if (points.size() < 2)
			points.add(new Points(img));
		if (distance.getDistance() % 2000 == 0)
			planets.add(new Planets());
	}

	public void updateScreen() {
		repaint();
	}

	public int getPositionX() {
		return 1280 + rand.nextInt(500) + 1;
	}

	public int getPositionY() {
		return rand.nextInt(720) + 1;
	}

	public boolean restartGame() {
		return restart;
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
		menuGame = false;
		restart = false;
		exitGame = false;
	}

	public boolean getMenu() {
		return menuGame;
	}
}
