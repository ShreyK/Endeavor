package com.khosla.sprites;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import com.khosla.spriteSystems.Missile;

public class Craft {
	private int x;
	private int y;
	private int height;
	private int width;
	private int dx, dy;
	private boolean visible;
	private boolean offScreen;
	private BufferedImage img;
	private BufferedImage SpriteImg;
	private ArrayList missiles;
	private boolean enough;
	public static boolean playerType;
	private Random rand = new Random();

	public Craft(int x, int y, BufferedImage SpriteSheet, boolean player) {
		// If Player1 = true;
		// If Player2 = false;
		missiles = new ArrayList();
		visible = true;
		offScreen = true;
		enough = true;
		SpriteImg = SpriteSheet;
		playerType = player;
		if (playerType)
			img = SpriteImg.getSubimage(0, 0, 16, 32);
		else
			img = SpriteImg.getSubimage(48, 0, 16, 32);

		width = img.getWidth();
		height = img.getHeight();
		this.x = x;
		this.y = y;
	}

	public boolean isOffScreen() {
		return offScreen;
	}

	public BufferedImage getImage() {
		return img;
	}

	public void move() {
		if (x <= 1280)
			offScreen = false;
		x += dx + dx;
		y += dy + dy;
		if (x < 1)
			x = 1;
		if (y < 1)
			y = 1;
		if (x > 1230)
			x = 1230;
		if (y > 670)
			y = 670;
	}

	public void knockback() {
		x -= 5;
		if (rand.nextInt(2) == 0)
			y -= 6;
		else
			y += 6;
	}

	public ArrayList getMissiles() {
		return missiles;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Rectangle getBounds() {
		return new Rectangle(x - 2, y + 3, width - 4, height - 6);
	}

	public void keyPressed(KeyEvent e, boolean type) {
		int key = e.getKeyCode();
		if (type) {
			if (key == KeyEvent.VK_A)
				dx = -1;
			if (key == KeyEvent.VK_D)
				dx = 1;
			if (key == KeyEvent.VK_W)
				dy = -1;
			if (key == KeyEvent.VK_S)
				dy = 1;
			if (key == KeyEvent.VK_SPACE) {
				if (enough)
					fire(type);
			}
		} else {
			if (key == KeyEvent.VK_LEFT)
				dx = -1;
			if (key == KeyEvent.VK_RIGHT)
				dx = 1;
			if (key == KeyEvent.VK_UP)
				dy = -1;
			if (key == KeyEvent.VK_DOWN)
				dy = 1;
			if (key == KeyEvent.VK_ENTER) {
				if (enough)
					fire(type);
			}
		}
	}

	public void keyReleased(KeyEvent e, boolean type) {
		int key = e.getKeyCode();
		if (type) {
			if (key == KeyEvent.VK_A)
				dx = 0;
			if (key == KeyEvent.VK_D)
				dx = 0;
			if (key == KeyEvent.VK_W)
				dy = 0;
			if (key == KeyEvent.VK_S)
				dy = 0;
		} else {

			if (key == KeyEvent.VK_LEFT)
				dx = 0;
			if (key == KeyEvent.VK_RIGHT)
				dx = 0;
			if (key == KeyEvent.VK_UP)
				dy = 0;
			if (key == KeyEvent.VK_DOWN)
				dy = 0;
		}
	}

	public void setEnoughEnergy(boolean enough) {
		this.enough = enough;
	}

	public boolean getEnoughEnergy() {
		return enough;
	}

	public void fire(boolean type) {
		missiles.add(new Missile(getX() + getWidth(), getY() + getHeight() / 3, SpriteImg, type));
	}
}
