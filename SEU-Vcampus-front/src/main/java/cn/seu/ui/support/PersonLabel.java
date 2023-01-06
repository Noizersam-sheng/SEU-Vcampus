package cn.seu.ui.support;

/**
 * @author 牟倪
 * 个人信息标签，展示身份（老师/学生）、学生性别、人物姓名
 */

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PersonLabel extends JLabel {

	public PersonLabel(String name, ImageIcon icon) {
		super(name + "，您好！", icon, JLabel.CENTER);
		this.setSize(300, 120);
		this.setFont(new Font("微软雅黑", Font.BOLD, 20));
	}

}
