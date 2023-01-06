package cn.seu.ui.teacher_panel;

/**
 * @author 牟倪
 * 教师界面
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.seu.domain.personnel.PassData;
import cn.seu.domain.personnel.Person;
import cn.seu.domain.personnel.Teacher;
import cn.seu.ui.support.BackgroundTabbedFrame;
import cn.seu.ui.support.CarouselPanel;
import cn.seu.ui.support.JCButton;
import cn.seu.ui.support.MyType;
import cn.seu.ui.support.PersonLabel;
import cn.seu.ui.support.SidebarPanel;
import cn.seu.ui.support.TranslucPanel;
import cn.seu.ui.support.WenwuBorder;

public class TeacherUI {
	// student
	Person teacher;

	// PassData
	PassData passdata = new PassData();
	PassData passData = new PassData();

	// [start], elements
	// [start], figure & icon
	ImageIcon background = new ImageIcon(MyType.figurePath + "seubg2-cast-1200-650.jpg");
	static int X = 1200, Y = 650, X1 = 300, X2 = 900;
	ImageIcon personIcon = new ImageIcon(MyType.iconPath + "person.png");
	ImageIcon jwcIcon = new ImageIcon(MyType.iconPath + "jwc.png");
	ImageIcon shopIcon = new ImageIcon(MyType.iconPath + "shop.png");
	ImageIcon libraryIcon = new ImageIcon(MyType.iconPath + "library.png");
	ImageIcon bankIcon = new ImageIcon(MyType.iconPath + "bank.png");
	ImageIcon penIcon = new ImageIcon(MyType.iconPath + "pen.png");
	ImageIcon questIcon = new ImageIcon(MyType.iconPath + "question.png");
	ImageIcon boyIcon = new ImageIcon(MyType.iconPath + "boy.png");
	ImageIcon girlIcon = new ImageIcon(MyType.iconPath + "girl.png");
	ImageIcon adminIcon = new ImageIcon(MyType.iconPath + "admin.png");
	ImageIcon seulogoIcon = new ImageIcon(MyType.iconPath + "seulogo.png");
	ImageIcon vlogoIcon = new ImageIcon(MyType.iconPath + "vlogo.png");
	CarouselPanel posterPanel = new CarouselPanel();
	// [end]

	// frame & content panel
	private BackgroundTabbedFrame jf = new BackgroundTabbedFrame(background, X, Y);
	private JPanel sum = new JPanel(null);
	private SidebarPanel sidebar = new SidebarPanel(5);
	private JPanel grid = new JPanel(null);

	// big button
	JCButton perButton = null;
	JCButton jwcButton = null;
	JCButton shopButton = null;
	JCButton libraryButton = null;
	JCButton bankButton = null;

	// side bar element
	PersonLabel perLabel = null;
	JPanel quesToAsk = new JPanel(null);
	JLabel mayYouWannaKnow = new JLabel("你可能想知道：");
//	WannaKnowItem wannaKnow1 = new WannaKnowItem("   选不上实践课怎么办");
//	WannaKnowItem wannaKnow2 = new WannaKnowItem("   如何订购教材");
//	WannaKnowItem wannaKnow3 = new WannaKnowItem("   如何查询SRTP学分");
	TranslucPanel tellMe = new TranslucPanel(new Color(175, 135, 70));
	JLabel tellMeLabel = new JLabel(" 留言板 ", penIcon, JLabel.CENTER);

	// decorator
	JLabel bigtitle = new JLabel("VCampus - 东南大学虚拟校园");
	JLabel seulogo = new JLabel(seulogoIcon);
	// [end]

	public TeacherUI(Person tea) {
		this.teacher = tea;

		// [start], big button student things
		Object[] personalInfo = passdata.status_information(passdata.search_s("id", teacher.account_number));
		perLabel = new PersonLabel(personalInfo[2].toString(),
				teacher.getJurisdiction() == 4 ? adminIcon : (personalInfo[5].equals(1) ? boyIcon : girlIcon));
//		perLabel = new PersonLabel("王世杰", adminIcon); // TODO:待修改

		JCButton perButton = new JCButton(" 个人信息 ", personIcon, jf, teacher);
		JCButton jwcButton = new JCButton(" 教务系统 ", jwcIcon, jf, teacher);
		JCButton shopButton = new JCButton(" 校园商店 ", shopIcon, jf, teacher);
		JCButton libraryButton = new JCButton(" 图书馆 ", libraryIcon, jf, teacher);
		JCButton bankButton = new JCButton(" 一卡通管理 ", bankIcon, jf, teacher);
		// [end]

		// [start], title & size & location & format
		jf.setTitle("VCampus - 东南大学虚拟校园 - 教师端");
		jf.setIconImage(vlogoIcon.getImage());
		sum.setSize(X, Y);
		sidebar.setBounds(0, 0, X1, Y);
		sidebar.setBackground(new Color(220, 220, 240, 255));
		grid.setBounds(X1, 0, X2, Y);
		grid.setOpaque(false);
		// [end]

		// [start], element size & location & format
		perLabel.setBounds(20, 10, 230, 80);
		quesToAsk.setBorder(new WenwuBorder(20, Color.GRAY));
		quesToAsk.setBounds(30, 140, 230, 300);
		quesToAsk.setOpaque(true);
		quesToAsk.setBackground(Color.WHITE);
		mayYouWannaKnow.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		mayYouWannaKnow.setBounds(20, 15, 100, 30);
//		wannaKnow1.setBounds(30, 60, 150, 30);
//		wannaKnow2.setBounds(30, 60 + 45, 110, 30);
//		wannaKnow3.setBounds(30, 60 + 2 * 45, 145, 30);
		quesToAsk.add(mayYouWannaKnow);
//		quesToAsk.add(wannaKnow1);
//		quesToAsk.add(wannaKnow2);
//		quesToAsk.add(wannaKnow3);

		tellMe.setBounds(45, 520, 180, 75);
		tellMeLabel.setBounds(45, 520, 180, 75);
		tellMeLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
		tellMeLabel.setOpaque(false);
		tellMe.add(tellMeLabel);
		posterPanel.setBounds(70, 150, CarouselPanel.sizeX, CarouselPanel.sizeY);

		int basex = 70, basey = 330, spacex = 155, widthx = 100, heighty = 100;
		perButton.setBounds(basex, basey, widthx, heighty);
		jwcButton.setBounds(basex + spacex, basey, widthx, heighty);
		shopButton.setBounds(basex + 2 * spacex, basey, widthx, heighty);
		libraryButton.setBounds(basex + 3 * spacex, basey, widthx, heighty);
		bankButton.setBounds(basex + 4 * spacex, basey, widthx, heighty);

		seulogo.setBounds(650, 20, 204, 64);
		bigtitle.setFont(MyType.h0Font);
		bigtitle.setBounds(60, 30, 600, 70);
		// [end]

		// mouse event
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

		// [start], add elements
		// add side bar element
		sidebar.add(perLabel);
		sidebar.add(quesToAsk);
		sidebar.add(tellMe);
		sidebar.add(tellMeLabel);

		// add main panel element
		grid.add(perButton);
		grid.add(jwcButton);
		grid.add(shopButton);
		grid.add(libraryButton);
		grid.add(bankButton);

		grid.add(seulogo);
		grid.add(bigtitle);
		grid.add(posterPanel);

		// add content
		sum.add(grid);
		sum.add(sidebar);
		// [end]

		jf.addMainPanel(sum);
	}

	public void display() {
		jf.display();
	}

	public static void main(String args[]) {
		System.gc();
		MyType.initGlobalFont();
		TeacherUI studentUI = new TeacherUI(
				new Teacher("213191246", "小赖", "123456", "赖泽升", 4, 20, 1, "计算机科学与工程学院、软件学院、人工智能学院", 0));
		studentUI.display();
	}
}