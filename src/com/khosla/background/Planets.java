package com.khosla.background;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Planets {
	private int x;
	private int y;
	private int radius;
	private boolean visible;
	private boolean offScreen;
	private Random rand = new Random();
	private int R, G, B, A;
	private Color randColor;

	public Planets() {
		x = 2000 + rand.nextInt(400) + 1;
		y = rand.nextInt(650) + 1;
		radius = 100 + rand.nextInt(300);
		R = rand.nextInt(255) + 1;
		G = rand.nextInt(255) + 1;
		B = rand.nextInt(255) + 1;
		A = 255;
		randColor = new Color(R, G, B, A);
		
		visible = true;
		offScreen = true;
	}

	public void move() {
		if (x <= 1280)
			offScreen = false;
		if ((x + radius + radius) < 0)
			visible = false;
		x-=2;
	}

	public Color getColor() {
		return randColor;
	}

	public boolean isOffScreen() {
		return offScreen;
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

	public int getRadius() {
		return radius;
	}
}
