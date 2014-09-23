package com.khosla.spriteSystems;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Points {
	private int points;
	private int x;
	private int y;
	private int height;
	private int width;
	private int moveSpeed;
	private BufferedImage img;
	private boolean visible;
	private boolean offScreen;
	Random rand = new Random();

	public Points(BufferedImage SpriteSheet) {
		img = SpriteSheet;
		img = img.getSubimage(16, 64, 16, 16);
		x = 2000 + rand.nextInt(1280) + 1;
		y = rand.nextInt(720) + 1;
		height = img.getHeight();
		width = img.getWidth();
		visible = true;
		offScreen = true;
		moveSpeed = 2;
	}
	public void move() {
		if (x <= 1280)
			offScreen = false;
		x -= moveSpeed;
		if (x < 0)
			x = 3000 + rand.nextInt(1000) + 1;
	}
	public boolean isOffScreen(){
		return offScreen;
	}
	public Image getImage() {
		return img;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
