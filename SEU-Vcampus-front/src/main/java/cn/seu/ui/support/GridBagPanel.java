package cn.seu.ui.support;

/**
 * @author 牟倪
 * 为GridBagLayout定制的面板，将add操作进行更简洁的封装。
 */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class GridBagPanel {

	public GridBagLayout gbLayout = new GridBagLayout();
	public JPanel panel = new JPanel(gbLayout); // Panel!
	public GridBagConstraints gbConstraints = new GridBagConstraints();
	{
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.insets = new Insets(5, 5, 5, 5);
	}

	public GridBagPanel(int X, int Y) {
		panel.setBounds(0, 0, X, Y);
		panel.setOpaque(false);
		panel.setFont(MyType.beautFont);
	}

	public void gridAddComponent(JComponent c, int x, int y, int width, int height) {
		gbConstraints.gridx = x;
		gbConstraints.gridy = y;
		gbConstraints.gridwidth = width;
		gbConstraints.gridheight = height;
		gbLayout.setConstraints(c, gbConstraints);
		panel.add(c);
	}
}
