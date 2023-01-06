package cn.seu.ui.library_manager_panel;

/**
 * @author 牟倪
 * 图书管理员界面
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.seu.ui.support.BackgroundTabbedFrame;
import cn.seu.ui.support.JCButton;
import cn.seu.ui.support.MyType;
import cn.seu.ui.support.PersonLabel;
import cn.seu.ui.support.SidebarPanel;
import cn.seu.ui.support.TranslucPanel;

public class LibraryManagerUI {
	// figure & icon
	ImageIcon background = new ImageIcon(MyType.figurePath + "seubg2-cast-1200-650.jpg");
	static int X = 1200, Y = 650, X1 = 300, X2 = 900;
	ImageIcon studentIcon = new ImageIcon(MyType.iconPath + "student.png");
	ImageIcon bookIcon = new ImageIcon(MyType.iconPath + "book.png");
	ImageIcon penIcon = new ImageIcon(MyType.iconPath + "pen.png");
	ImageIcon libraryIcon = new ImageIcon(MyType.iconPath + "library.png");
	ImageIcon adminIcon = new ImageIcon(MyType.iconPath + "admin.png");
	ImageIcon seulogoIcon = new ImageIcon(MyType.iconPath + "seulogo.png");
	ImageIcon vlogoIcon = new ImageIcon(MyType.iconPath + "vlogo.png");

	// frame & content panel
	private BackgroundTabbedFrame jf = new BackgroundTabbedFrame(background, X, Y);
	private JPanel sum = new JPanel(null);
	private SidebarPanel sidebar = new SidebarPanel(5);
	private JPanel grid = new JPanel(null);

	// big button
	JCButton manageBookButton = new JCButton("管理书籍信息", bookIcon, jf, null);

	// side bar element
	PersonLabel perLabel = new PersonLabel("图书管理员", adminIcon);
	TranslucPanel tellMe = new TranslucPanel(new Color(175, 135, 70));
	JLabel tellMeLabel = new JLabel(" 留言板 ", penIcon, JLabel.CENTER);

	// decorator
	JLabel bigtitle = new JLabel("VCampus - 东南大学虚拟校园");
	JLabel seulogo = new JLabel(seulogoIcon);

	public LibraryManagerUI() {
		// title & size & location & format
		jf.setTitle("VCampus - 东南大学虚拟校园 - 图书管理员");
		jf.setIconImage(vlogoIcon.getImage());
		sum.setSize(X, Y);
		sidebar.setBounds(0, 0, X1, Y);
		sidebar.setBackground(new Color(220, 220, 240, 255));
		grid.setBounds(X1, 0, X2, Y);
		grid.setOpaque(false);

		// element size & location & format
		perLabel.setBounds(0, 10, 260, 80);
		tellMe.setBounds(45, 520, 180, 75);
		tellMeLabel.setBounds(45, 520, 180, 75);
		tellMeLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
		tellMeLabel.setOpaque(false);
		tellMe.add(tellMeLabel);

		int basex = 350, basey = 250, widthx = 120, heighty = 100;
		manageBookButton.setBounds(basex, basey, widthx, heighty);

		seulogo.setBounds(650, 20, 204, 64);
		bigtitle.setFont(MyType.h0Font);
		bigtitle.setBounds(60, 30, 600, 70);

		// mouse click
		tellMe.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showInputDialog(jf, "请留下您对我们程序的宝贵意见和改进建议吧！");
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

		// add side bar element
		sidebar.add(perLabel);
		sidebar.add(tellMe);
		sidebar.add(tellMeLabel);

		// add main panel element
		grid.add(manageBookButton);

		grid.add(seulogo);
		grid.add(bigtitle);

		// add content
		sum.add(grid);
		sum.add(sidebar);

		jf.addMainPanel(sum);
	}

	public void display() {
		jf.display();
	}

	public static void main(String args[]) {
		System.gc();
		MyType.initGlobalFont();
		LibraryManagerUI libraryManagerUI = new LibraryManagerUI();
		libraryManagerUI.display();
	}
}
