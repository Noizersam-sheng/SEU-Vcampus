package cn.seu.ui.support;

/**
 * @author 牟倪
 * 经典的圆角边框
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

public class RoundBorder implements Border {
	int round = 8;
	Color color = Color.GRAY;
	int pixel = 1;

	public RoundBorder(int r, Color c, int p) {
		this.round = r;
		this.color = c;
		this.pixel = p;
	}

	public Insets getBorderInsets(Component c) {
		return new Insets(0, 0, 0, 0);
	}

	public boolean isBorderOpaque() {
		return false;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		g.setColor(color);
		if (pixel == 1) {
			g.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, round, round);
			return;
		} else {
			for (int i = 0; i < pixel; ++i) {
				g.drawRoundRect(i, i, c.getWidth() - 1 - 2 * i, c.getHeight() - 1 - 2 * i, round - i, round - i);
				g.drawRoundRect(i, i, c.getWidth() - 1 - 2 * i, c.getHeight() - 1 - 2 * i, round - i + 1,
						round - i +1);
				g.drawRoundRect(i, i, c.getWidth() - 1 - 2 * i, c.getHeight() - 1 - 2 * i, round - i - 1,
						round - i - 1);
			}
		}
	}
}