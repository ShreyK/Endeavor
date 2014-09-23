package com.khosla.background;

import java.awt.Color;
import java.util.Random;

public class Stars {
	private int x;
	private int y;
	private int radius;
	private float moveSpeed;
	private int R, G, B;
	private boolean visible;
	private boolean offScreen;
	private Color color;
	private Random rand = new Random();

	public Stars() {
		x = rand.nextInt(1280) + 1;
		y = rand.nextInt(720) + 1;
		radius = rand.nextInt(4) + 1;
		visible = true;
		offScreen = true;
		moveSpeed = (float) (radius / 1.5);
		if (moveSpeed == 6) {
			R = 255;
			G = 255;
			B = 200;
		} else if (moveSpeed == 5) {
			R = 255;
			G = 255;
			B = 100;
		} else {
			R = 255;
			G = 255;
			B = 255;
		}
		color = new Color(R, G, B);
	}

	public void move() {
		if (x <= 1280)
			offScreen = false;
		x -= moveSpeed;
		if (x < 0) {
			x = 1281;
			y = rand.nextInt(650) + 1;
		}
	}

	public Color getColor() {
		return color;
	}

	public boolean isOffScreen() {
		return offScreen;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getRadius() {
		return radius;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
