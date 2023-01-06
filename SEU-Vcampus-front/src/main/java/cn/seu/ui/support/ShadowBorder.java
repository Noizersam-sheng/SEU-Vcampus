package cn.seu.ui.support;

/**
 * @author 牟倪
 * 主界面功能按钮的立体效果边框
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

public class ShadowBorder implements Border {
	static int pixels = 12;
	static int TOP_OPACITY = 40;

	public Insets getBorderInsets(Component c) {
		return new Insets(0, 0, 0, 0);
	}

	public boolean isBorderOpaque() {
		return false;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
//		int round = 8;
//		g.setColor(Color.GRAY);
//		for (int i = 0; i < 1; ++i) {
//			g.drawRoundRect(i, i, c.getWidth() - 1 - 2 * i, c.getHeight() - 1 - 2 * i, round - i + 1, round - i + 1);
//			g.drawRoundRect(i, i, c.getWidth() - 1 - 2 * i, c.getHeight() - 1 - 2 * i, round - i, round - i);
//			g.drawRoundRect(i, i, c.getWidth() - 1 - 2 * i, c.getHeight() - 1 - 2 * i, round - i - 1, round - i - 1);
//		}
		for (int i = 0; i < pixels; ++i) {
			g.setColor(new Color(0, 0, 0, (TOP_OPACITY / (i + 1))));
			g.drawRoundRect(i, i, c.getWidth() - 1 - 2 * i, c.getHeight() - 1 - 2 * i, 0, 0);
		}
	}

}