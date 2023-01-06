package cn.seu.ui.support.shop;

/**
 * 淘宝风格红色ComboBox定制部件！
 */

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import cn.seu.ui.support.MyType;

public class IComboBoxRenderer implements ListCellRenderer {

	private DefaultListCellRenderer defaultCellRenderer = new DefaultListCellRenderer();

	public IComboBoxRenderer() {
		super();
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {

		JLabel renderer = (JLabel) defaultCellRenderer.getListCellRendererComponent(list, value, index, isSelected,
				cellHasFocus);
		if (isSelected) {
			renderer.setBackground(MyType.redColor);
			renderer.setForeground(Color.WHITE);
		} else {
			renderer.setBackground(Color.WHITE);
		}
		list.setSelectionBackground(Color.white);
		list.setBorder(null);
//		renderer.setFont(MyType.beautFont); // XUtil.defaultComboBoxFont
		renderer.setHorizontalAlignment(JLabel.CENTER);
		return renderer;
	}
	// reference: http://www.blogjava.net/chensiyu04/archive/2011/03/23/346868.html
}
