package com.khosla.background;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class BlackHole {
	private int x;
	private int y;
	private int width;
	private int height;
	private int extraP;
	private boolean offScreen;
	private boolean visible;
	private BufferedImage img;
	private Random rand = new Random();

	public BlackHole(BufferedImage SpriteImg) {
		x = 2000 + rand.nextInt(400) + 1;
		y = rand.nextInt(650) + 1;
		extraP = 10;
		visible = true;
		img = SpriteImg;
		img = img.getSubimage(0, 0, 0, 0);

	}

	public void move() {
		if (x <= 1280)
			offScreen = false;
		if (x < 0)
			visible = false;
		x--;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Rectangle getBounds() {
		return new Rectangle(x - extraP, y - extraP, width + extraP * 2, height + extraP * 2);
	}

	public Point getCenter() {
		return new Point(x + width / 2, y + height / 2);
	}
}
