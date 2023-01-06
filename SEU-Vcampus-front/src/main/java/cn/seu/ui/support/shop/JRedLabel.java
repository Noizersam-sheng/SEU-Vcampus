package cn.seu.ui.support.shop;

/**
 * @author 牟倪
 * @version 1.0
 * 淘宝风格红色按钮，需要自行添加鼠标特效
 */

import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import cn.seu.ui.support.MyType;
import cn.seu.ui.support.RoundBorder;

public class JRedLabel extends JLabel {
	public static int PIXEL = 2;
	private ImageIcon icon;
	private ImageIcon iconEntered;

	public JRedLabel(ImageIcon icon, ImageIcon iconEntered, int ALIGNMENT) {
		super("", icon, ALIGNMENT);
		this.icon = icon;
		this.iconEntered = iconEntered;
//		this.setBorder(new RoundBorder(0, MyType.redColor, PIXEL));
//		this.setFont(new Font("微软雅黑", Font.PLAIN, 16));
	}

	public JRedLabel(String text, ImageIcon icon, int ALIGNMENT) {
		super(text, icon, ALIGNMENT);
		this.setBorder(new RoundBorder(0, MyType.redColor, PIXEL));
		this.setFont(new Font("微软雅黑", Font.PLAIN, 16));
	}

	public JRedLabel(String text) {
		super(text);
		this.setBorder(new RoundBorder(0, MyType.redColor, PIXEL));
		this.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		this.setHorizontalAlignment(JLabel.CENTER);
	}

	public void EnteredEffect(){
		this.setIcon(iconEntered);
	}
	public void ExitedEffect(){
		this.setIcon(icon);
	}

}
