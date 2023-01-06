package cn.seu.ui.support;

/**
 * 带背景、支持选项卡布局的frame
 */

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class BackgroundTabbedFrame extends JFrame {

	public JLayeredPane layeredPane = new JLayeredPane();
	public JTabbedPane tabbedPane = new JTabbedPane();
	private int X, Y;

	public BackgroundTabbedFrame(ImageIcon background, int X, int Y) {
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

	public void addMainPanel(JComponent panel) {
		panel.setBounds(0, 0, X, Y);
		panel.setOpaque(false);
		layeredPane.add(panel, JLayeredPane.MODAL_LAYER);
		tabbedPane.addTab("主界面", layeredPane);
	}

	public void addFunctionPanel(JComponent panel, String description) {
		panel.setBounds(0, 0, X, Y);
		panel.setOpaque(true);
		tabbedPane.addTab(description, panel);
		tabbedPane.setSelectedIndex(tabbedPane.indexOfTab(description));
	}

	public void display() {
		tabbedPane.setBounds(0, 0, X, Y);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setOpaque(true);
//		layeredPane.add(tabbedPane, JLayeredPane.MODAL_LAYER);
//		this.add(layeredPane);
		tabbedPane.setVisible(true);
		this.add(tabbedPane);
		this.setVisible(true);
	}
}
