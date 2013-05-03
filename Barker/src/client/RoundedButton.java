package client;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;

class RoundedButton extends JButton {
	
	private int radius;
	private Shape shape;
	
	public RoundedButton(String label, int radius) {
		super(label);
		this.radius = radius;
		
		setContentAreaFilled(false);
	}

	protected void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, radius, radius);

		super.paintComponent(g);
	}

	protected void paintBorder(Graphics g) {
		g.setColor(Color.gray);
		g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, radius, radius);
	}

	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new java.awt.geom.RoundRectangle2D.Float(0, 0, getSize().width - 1, getSize().height - 1, radius, radius);
		}
		return shape.contains(x, y);
	}
}