package com.khosla.spriteSystems;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.khosla.sprites.Craft;

public class Missile {
	private int x, y, width, height;
	private BufferedImage img, img2;
	boolean visible;
	boolean offScreen;
	boolean hit = false;
	boolean otherHit = false;
	boolean type;
	private final int BOARD_WIDTH = 1280;
	private final int MISSILE_SPEED = 5;

	public Missile(int x, int y, BufferedImage SpriteSheet, boolean playerType) {
		img = SpriteSheet;
		img2 = img.getSubimage(16, 48, 16, 16);
		if (playerType)
			img = img.getSubimage(0, 48, 16, 16);
		else
			img = img.getSubimage(16, 48, 16, 16);

		visible = true;
		offScreen = true;
		this.x = x;
		this.y = y;
		width = img.getWidth();
		height = img.getHeight();
		type = playerType;
	}

	public void move() {
		if (type) {
			if (x <= 1280)
				offScreen = false;
			x += MISSILE_SPEED;
			if (x > BOARD_WIDTH)
				visible = false;
			if (hit)
				visible = false;
		} else {
			if (x <= 1280)
				offScreen = false;
			x -= MISSILE_SPEED;
			if (x < 0)
				visible = false;
			if (hit)
				visible = false;
		}
	}

	public boolean isOffScreen() {
		return offScreen;
	}

	public Image getImage() {
		return img;
	}

	public Image getImage2() {
		return img2;
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

	public void isHit() {
		hit = true;
	}

	public void isHitOther() {
		otherHit = true;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
