package cn.seu.ui.support;

/**
 * @author 牟倪
 * 绘制一个填充的圆角矩形，只在课表中用过
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

public class RoundedRectangleBorder implements Border {
	Color color;

	public RoundedRectangleBorder(Color c) {
		this.color = c;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		g.setColor(color);
		g.fillRoundRect(5, 4, c.getWidth() - 1 - 8, c.getHeight() - 1 - 6, 10, 10);
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(0, 0, 0, 0);
	}

	@Override
	public boolean isBorderOpaque() {
		return false;
	}
}
