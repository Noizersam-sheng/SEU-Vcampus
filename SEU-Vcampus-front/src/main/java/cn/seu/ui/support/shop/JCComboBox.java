package cn.seu.ui.support.shop;

/**
 * @author 牟倪
 * @version 1.0
 * 淘宝风格红色ComboBox，命名为Java Cool Combo Box，使用与正常JComboBox一样
 */

import java.awt.Color;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

public class JCComboBox extends JComboBox {

	public JCComboBox() {
		super();
		init();
	}

	public JCComboBox(ComboBoxModel model) {
		super(model);
		init();
	}

	public JCComboBox(Object[] items) {
		super(items);
		init();
	}

	public JCComboBox(Vector<?> items) {
		super(items);
		init();
	}

	private void init() {
		setOpaque(false);
		setUI(new IComboBoxUI());
		setRenderer(new IComboBoxRenderer());
		setBackground(Color.white);
	}

//	public Dimension getPreferredSize() {
//		return super.getPreferredSize();
//	}
}
// XUtil.defaultComboBoxColor: orange