package com.khosla.spriteSystems;

public class Energy {
	private int x;
	private int y;
	private int width;
	private int height;
	private int Energy;
	private boolean visible;
	private int MAX_Energy, MIN_Energy;

	public Energy(int x, int y) {
		Energy = 200;
		width = 200;
		height = 15;
		visible = true;
		MAX_Energy = 200;
		MIN_Energy = 0;
		this.x = x;
		this.y = y;
	}

	public void setEnergy(int Energy) {
		this.Energy = Energy;
	}

	public int getEnergy() {
		if (Energy > MAX_Energy)
			Energy = 200;
		if (Energy < MIN_Energy)
			Energy = 0;
		return Energy;
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

	public boolean getVisible() {
		return visible;
	}

	public int getEnergyWidth() {
		if (width < 0)
			width = 0;
		if (width > 200)
			width = 200;
		return width;
	}

	public void setEnergyWidth(int width) {
		this.width = width;
		if (width < 0)
			width = 0;
		if (width > 200)
			width = 200;
	}

	public int getEnergyHeight() {
		return height;
	}

}
