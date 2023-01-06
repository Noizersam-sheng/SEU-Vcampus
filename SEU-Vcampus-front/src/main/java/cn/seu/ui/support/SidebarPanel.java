package cn.seu.ui.support;

/**
 * @author 牟倪
 * 半透明侧边栏
 */

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SidebarPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	// 最大阴影透明度
	private static final int TOP_OPACITY = 160;

	// 阴影大小像素
	public int pixels;

	public SidebarPanel(int pix) {
		super(null);
		this.pixels = pix;
//		Border border = BorderFactory.createEmptyBorder(pixels, pixels, pixels, pixels);
		this.setBorder(new SidebarBorder(pix));
//		this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), border));
//		this.setLayout(new BorderLayout());
	}

	@Override
	protected void paintComponent(Graphics g) {
		int shade = 255;
		g.setColor(new Color(shade, shade, shade, TOP_OPACITY));
		g.fillRoundRect(0, 0, this.getWidth() - 1-pixels, this.getHeight() - 1, 0, 0);

	}

}
