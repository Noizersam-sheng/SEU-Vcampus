package cn.seu.ui.support;

/**
 * @author 牟倪
 * 半透明渐变阴影效果，用于“留言板”。不要加鼠标悬停特效，不然会发生惨案。
 */

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 
 * @author 牟倪
 * 
 *         这是一个半透明带弧光的panel。和透明label叠加使用，可以实现炫酷按钮的效果。
 *
 */

public class TranslucPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int TOP_OPACITY = 120;// 最大阴影透明度
	private static final int pixels = 10;// 弧光大小

	// RGB值
	public int r;
	public int g;
	public int b;

	public TranslucPanel(Color color) {
		super(null);
		this.r = color.getRed();
		this.g = color.getGreen();
		this.b = color.getBlue();
//		Border border = BorderFactory.createEmptyBorder(pixels, pixels, pixels, pixels);
//		this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), border));
	}

	@Override
	protected void paintComponent(Graphics gr) {
//		gr.setColor(new Color(r,g,b,120));
//		gr.fillRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 10, 10);
		int opacity = TOP_OPACITY / pixels;
		for (int i = 0; i < pixels; i++) {
//			gr.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 10, 10);
//			double opacity = TOP_OPACITY * ((double) 1 / (double) (i == 0 ? 1 : i));
			drawShadow(gr, pixels - i, opacity);// (int) opacity
		}
	}

	private void drawShadow(Graphics gr, int i, int opacity) {
		gr.setColor(new Color(r, g, b, opacity));
		gr.fillRoundRect(i + 1, i + 1, this.getWidth() - ((i * 2) + 1), this.getHeight() - ((i * 2) + 1), 20, 20);
	}
}

/*
 * 本来想直接写一个半透明带弧光+放置图片文字的label当作按钮，结果paintComponent直接把图片文字盖住了。
 * 然后不要给放在它正上面/附近的label加【和渲染有关的】鼠标事件，会发生惨案。
 * 还是不太了解graphics的用法。20210710。
 */