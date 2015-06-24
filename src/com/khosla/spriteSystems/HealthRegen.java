package com.khosla.spriteSystems;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class HealthRegen extends Health {
	private int x;
	private int y;
	private boolean visible;
	private boolean offScreen;
	private int width;
	private int height;
	private int moveSpeed;
	private BufferedImage img;
	private Random rand = new Random();

	public HealthRegen(int x, int y, BufferedImage SpriteSheet) {
		super(x, y);
		img = SpriteSheet;
		img = img.getSubimage(32, 64, 16, 16);
		width = img.getWidth();
		height = img.getHeight();
		visible = true;
		offScreen = true;
		this.x = x;
		this.y = y;
		moveSpeed = 2;
	}
	public void move() {
		if (x <= 1280)
			offScreen = false;
		x -= moveSpeed;
		if (x < 0) {
			x = 2000 + rand.nextInt(2000) + 1;
			y = rand.nextInt(650) + 1;
		}
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

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
