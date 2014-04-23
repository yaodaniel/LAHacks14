/**
 * 
 */
package com.me.hackathonGame;
import com.badlogic.gdx.graphics.Color;

/**
 * @author Phillip
 *
 */
public class Square {
	
	private GameColor color;
	
	/**
	 * Default Square is GRAY
	 */
	public Square() {
		this.color = GameColor.GRAY;
	}
	
	/**
	 * @param color, the color of Square
	 */
	public Square(GameColor color) {
		this.color = color;
	}
	
	/**
	 * @return color, the Square Color
	 */
	public GameColor getColor() {
		return color;
	}

	/**
	 * @return Color (GDX)
	 */
	public Color getGDXColor() {
		switch(color) {
			case GRAY: 
				return new Color(0.92f, 0.94f, 0.95f, 1.0f);
			case RED:
				return new Color(0.90f, 0.30f, 0.24f, 1.0f);
			case ORANGE:
				return new Color(0.90f, 0.94f, 0.13f, 1.0f);
			case YELLOW:
				return new Color(0.95f, 0.77f, 0.06f, 1.0f);
			case GREEN:
				return new Color(0.18f, 0.80f, 0.44f, 1.0f);
			case BLUE:
				return new Color(0.20f, 0.60f, 0.86f, 1.0f);
			case PURPLE:
				return new Color(0.60f, 0.35f, 0.71f, 1.0f);
			default:
				return new Color(0.92f, 0.94f, 0.95f, 1.0f);
		}
	}
	
	/**
	 * @param color, the Color to set the square
	 */
	public void setColor(GameColor color) {
		this.color = color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Square other = (Square) obj;
		if (color != other.color)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Square [color=" + color.toString() + "]";
	}
}
