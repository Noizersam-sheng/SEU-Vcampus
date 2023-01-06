package cn.seu.ui.support;

/**
 * @author 牟倪
 * 文武边框，即外圈粗、里圈细的边框，边框粗细已经硬编码确定。支持圆角矩形，颜色和圆角大小可以改变。
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

public class WenwuBorder implements Border {
	int round = 8;
	Color color = Color.GRAY;

	public WenwuBorder(int r, Color c) {
		this.round = r;
		this.color = c;
	}

	public Insets getBorderInsets(Component c) {
		return new Insets(0, 0, 0, 0);
	}

	public boolean isBorderOpaque() {
		return false;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		g.setColor(color);
		for (int i = 0; i < 6; ++i) {
			if (i == 3 || i == 4)
				continue;
			if (round != 0) {
				g.drawRoundRect(i, i, c.getWidth() - 2 * i - 1, c.getHeight() - 2 * i - 1, round - i, round - i);
				g.drawRoundRect(i, i, c.getWidth() - 2 * i - 1, c.getHeight() - 2 * i - 1, round - i - 1,
						round - i - 1);
				g.drawRoundRect(i, i, c.getWidth() - 2 * i - 1, c.getHeight() - 2 * i - 1, round - i + 1,
						round - i + 1);
			} else {
				g.drawRoundRect(i, i, c.getWidth() - 2 * i - 1, c.getHeight() - 2 * i - 1, 0, 0);
			}
		}

//		g.drawRoundRect(1, 1, c.getWidth() - 3, c.getHeight() - 3, round, round);
//		g.drawRoundRect(1, 1, c.getWidth() - 3, c.getHeight() - 3, round - 1, round - 1);
//		g.drawRoundRect(1, 1, c.getWidth() - 3, c.getHeight() - 3, round + 1, round + 1);

	}

}