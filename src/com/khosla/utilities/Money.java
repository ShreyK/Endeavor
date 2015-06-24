package com.khosla.utilities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Money {
	private int x;
	private int y;
	private int width;
	private int height;
	private int money;
	private BufferedImage img;

	public Money(BufferedImage SpriteSheet) {
		x = 10;
		y = 600;
		money = 0;
		img = SpriteSheet;
		img = img.getSubimage(64, 64, 16, 16);
		width = img.getWidth();
		height = img.getHeight();
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BufferedImage getImage() {
		return img;
	}

	public int getMoney() {
		return money;
	}

	public void addMoney(int money) {
		this.money += money;
	}

	public void getIncome() {
		money += 5;
	}
}
