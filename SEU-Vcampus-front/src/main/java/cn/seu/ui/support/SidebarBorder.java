package cn.seu.ui.support;

/**
 * @author 牟倪
 * 阴影边框，用于界面的侧边栏，阴影深度以1/x渐变
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

public class SidebarBorder implements Border {
	public int pixels;
	static int TOP_OPACITY = 40;

	public SidebarBorder(int p) {
		this.pixels = p;
	}

	public Insets getBorderInsets(Component c) {
		return new Insets(0, 0, 0, 0);
	}

	public boolean isBorderOpaque() {
		return false;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		int shade = 120;
		for (int i = 0; i < pixels; ++i) {
			g.setColor(new Color(shade, shade, shade, (TOP_OPACITY / (pixels - i))));
			g.drawRoundRect(-1, -1, c.getWidth() - i, c.getHeight(), 0, 0);
		}
	}

}