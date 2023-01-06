package cn.seu.ui.support;

/**
 * @author 牟倪
 * 用来展示图片栏
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CarouselPanel extends JPanel {
	public static int sizeX = 700, sizeY = 116;

	private ImageIcon[] posters = null;
	int index = 0;

	public CarouselPanel() {
		posters = new ImageIcon[2];
		posters[0] = new ImageIcon(MyType.iconPath + "poster1.jpg");
		posters[1] = new ImageIcon(MyType.iconPath + "poster2.jpg");

		Timer timer = new Timer(4000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		timer.start();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(posters[index % posters.length].getImage(), 0, 0, this);
		index++;
	}
}
/*
 * public class Demo4 extends JFrame { CarouselPanel mp; int index; ImageIcon[]
 * imgs = { new ImageIcon("src/images/1.jpg"), new
 * ImageIcon("src/images/2.jpg"), new ImageIcon("src/images/3.jpg"), new
 * ImageIcon("src/images/4.jpg"), new ImageIcon("src/images/5.jpg"), new
 * ImageIcon("src/images/6.jpg"), new ImageIcon("src/images/7.jpg"), new
 * ImageIcon("src/images/8.jpg"), };
 * 
 * public Demo4() { mp = new CarouselPanel(); this.add(mp);
 * this.setExtendedState(JFrame.MAXIMIZED_BOTH);
 * this.setDefaultCloseOperation(EXIT_ON_CLOSE); this.setTitle("窗口");
 * this.setVisible(true); Timer timer = new Timer(500, new ActionListener() {
 * 
 * @Override public void actionPerformed(ActionEvent e) { mp.repaint(); } });
 * timer.start(); }
 * 
 * public static void main(String[] args) { new Demo4(); }
 * 
 * }
 */