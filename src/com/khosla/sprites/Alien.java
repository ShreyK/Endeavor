package com.khosla.sprites;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.khosla.spriteSystems.Health;

public class Alien {
	private int x, y, width, height, moveSpeed;
	private boolean visible;
	private boolean offScreen;
	private boolean chases;
	private BufferedImage img;
	Health alienHealth;
	Random rand = new Random();

	public Alien(int x, int y, BufferedImage SpriteSheet) {
		visible = true;
		offScreen = true;
		img = SpriteSheet;
		img = img.getSubimage(16, 0, 16, 16);
		width = img.getWidth();
		height = img.getHeight();
		this.x = x;
		this.y = y;
		moveSpeed = 3;
		if (rand.nextInt(10) == 0)
			chases = true;
		else
			chases = false;
	}

	public void move() {
		if (x <= 1280)
			offScreen = false;
		if (x < 0) {
			x = 1290;
			y = rand.nextInt(650) + 1;
		}
		x -= moveSpeed;
	}

	public void moveTo(int pX, int pY) {
		if (isClose(pX, pY)) {
			if (x < pX)
				x++;
			if (y < pY)
				y++;
			if (y > pY)
				y--;
		}
	}

	public boolean isClose(int pX, int pY) {
		if (getX() - pX < 50 && getY() - pY < 50)
			return true;
		else
			return false;
	}

	public boolean getChases() {
		return chases;
	}

	public boolean isOffScreen() {
		return offScreen;
	}

	public BufferedImage getImage() {
		return img;
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

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

	public Rectangle getBounds() {
		return new Rectangle(x + 2, y + 2, width - 2, height - 4);
	}
}
