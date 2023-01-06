package cn.seu.ui.support;

/**
 * 带背景的frame，功能死板，不建议使用
 */

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class BackgroundFrame extends JFrame {

	JLayeredPane layeredPane = new JLayeredPane();
	private int X, Y;

	public BackgroundFrame(ImageIcon background, int X, int Y) {
		super();
		this.X = X;
		this.Y = Y;
		this.setBounds(650 - X / 2, 350 - Y / 2, X, Y + 34); // mid位置是650 350
		JLabel label = new JLabel(background);
		JPanel bgpanel = new JPanel();
		bgpanel.setBounds(0, -5, X, Y); // -5是玄学offset
		bgpanel.add(label);
		layeredPane.add(bgpanel, JLayeredPane.DEFAULT_LAYER);
	}

	public void addContentPanel(JComponent panel) {
		panel.setBounds(0, 0, X, Y);
		panel.setOpaque(false);
		layeredPane.add(panel, JLayeredPane.MODAL_LAYER);
		this.add(layeredPane);
	}

	public void display() {
		this.setVisible(true);
	}
}
