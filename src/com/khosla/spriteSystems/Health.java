package com.khosla.spriteSystems;

public class Health {
	private int x;
	private int y;
	private int width;
	private int height;
	private int health;
	private boolean visible;
	private int MAX_HEALTH, MIN_HEALTH, MAX_WIDTH, MIN_WIDTH;

	public Health(int x, int y) {
		health = 500;
		width = 150;
		height = 15;
		visible = true;
		MAX_HEALTH = 500;
		MIN_HEALTH = 0;
		MAX_WIDTH = 50;
		MIN_WIDTH = 0;
		this.x = x;
		this.y = y;

	}

	public void setHealth(int health) {
		this.health = health;
		if (health > MAX_HEALTH)
			health = 500;
		if (health < MIN_HEALTH)
			health = 0;
	}

	public int getHealth() {
		return health;
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

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean getVisible() {
		return visible;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
		if (width > MAX_WIDTH)
			width = MAX_WIDTH;
		if (width < MIN_WIDTH)
			width = MIN_WIDTH;
	}
}
