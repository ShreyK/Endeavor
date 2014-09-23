package com.khosla.sprites;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Mob {
	// Screen max and min values
	protected int MAX_VALUE = 800, MIN_VALUE = 0;
	protected int x;
	protected int y;
	protected int height;
	protected int width;
	protected boolean visible;
	protected BufferedImage img;

	public Mob(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void move() {
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
		return new Rectangle(x, y, width, height);
	}
}
