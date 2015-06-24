package com.khosla.sprites;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import com.khosla.spriteSystems.Missile;

public class FastAlien {
	private int x, y, width, height, moveSpeed;
	private boolean visible;
	private boolean offScreen;
	private BufferedImage img;
	Random rand = new Random();

	public FastAlien(int x, int y, BufferedImage SpriteSheet) {
		visible = true;
		offScreen = true;
		img = SpriteSheet;
		img = img.getSubimage(16, 16, 16, 16);

		width = img.getWidth();
		height = img.getHeight();
		this.x = x;
		this.y = y;
		moveSpeed = rand.nextInt(4) + 3;
	}

	public void move() {
		if (x <= 1280)
			offScreen = false;
		if (x < 0) {
			x = 2000;
			y = rand.nextInt(650) + 1;
		}
		x -= moveSpeed;
	}
	public boolean isOffScreen(){
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
		return new Rectangle(x, y, width, height);
	}

}
