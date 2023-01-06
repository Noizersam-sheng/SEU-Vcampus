package cn.seu.ui.support;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.seu.domain.courses.Course;
import cn.seu.domain.personnel.PassData;
import cn.seu.domain.personnel.Person;
import cn.seu.domain.store.Commodity;
import cn.seu.domain.store.Commodity_Car;
import cn.seu.domain.store.Commodity_Record;
import cn.seu.dto.AlipayInfoInDTO;
import cn.seu.dto.AlipayInfoOutDTO;
import cn.seu.dto.AlipayResultInDTO;
import cn.seu.ui.support.jwc.ClassBlock;
import cn.seu.ui.support.jwc.CourseScrollContent;
import cn.seu.ui.support.jwc.Timetable;
import cn.seu.ui.support.library.JBorrowTable;
import cn.seu.ui.support.library.JCollectionTable;
import cn.seu.ui.support.library.JLibraryManagerTable;
import cn.seu.ui.support.shop.CommoScrollContent;
import cn.seu.ui.support.shop.JCComboBox;
import cn.seu.ui.support.shop.JRedLabel;


/**
 * 
 * @author 牟倪
 * @version 3.0
 * 
 *          基于JLabel的按钮，封装了所有主要功能界面，命名为Java Cool Button
 */

public class JCButton extends JLabel {
	static int fontSize = 20;
	static int X = 1200, Y = 650;

	// the person
	public Person person;

	// pass data
	PassData passdata = new PassData();
	PassData passData = new PassData();

	// [start], 一些全局的元素
	// global-used elements
	ImageIcon vlogoIcon = new ImageIcon(MyType.iconPath + "vlogo.png");

	static public JLabel getVcampus() {
		ImageIcon vcampusIcon = new ImageIcon(MyType.iconPath + "vcampus.png");
		JLabel vcampus = new JLabel(vcampusIcon);
		vcampus.setBounds(40, Y - 120, 180, 65);
		return vcampus;
	}

	public JButton getExit() {
		JButton exit = new JButton("关闭");
		exit.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jf.tabbedPane.remove(jf.tabbedPane.getSelectedIndex());
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
		return exit;
	}

	// JFrame
	BackgroundTabbedFrame jf;

	// background
	ImageIcon bgleft = new ImageIcon(MyType.figurePath + "seubg3-local-600-650.jpg");
	ImageIcon bgright = new ImageIcon(MyType.figurePath + "seubg3-local-600-650.jpg");

	// icon
	ImageIcon shopcartIconOld = new ImageIcon(MyType.iconPath + "shopcart.png");
	ImageIcon shopcartIcon = new ImageIcon(MyType.iconPath + "shopcartIcon.png");
	ImageIcon shopcartIconEntered = new ImageIcon(MyType.iconPath + "shopcartIconEntered.png");
	ImageIcon myCommoIcon = new ImageIcon(MyType.iconPath + "myCommoIcon.png");
	ImageIcon myCommoIconEntered = new ImageIcon(MyType.iconPath + "myCommoIconEntered.png");
	ImageIcon myShoppingDetailIcon = new ImageIcon(MyType.iconPath + "myShoppingDetailIcon.png");
	ImageIcon myShoppingDetailIconEntered = new ImageIcon(MyType.iconPath + "myShoppingDetailIconEntered.png");
	ImageIcon myShoppingReportIcon = new ImageIcon(MyType.iconPath + "myShoppingReportIcon.png");
	ImageIcon myShoppingReportIconEntered = new ImageIcon(MyType.iconPath + "myShoppingReportIconEntered.png");
	ImageIcon shopExitIcon = new ImageIcon(MyType.iconPath + "shopExitIcon.png");
	ImageIcon shopExitIconEntered = new ImageIcon(MyType.iconPath + "shopExitIconEntered.png");

	// 迫不得已global的elements
	// library
	public JBorrowTable borrowTable = null;
	public JCollectionTable collectionTable = null;
	// library manager
	public JLibraryManagerTable libraryManagerTable = null;
	// [end]

	// constructor
	/**
	 * JCButton的构造函数
	 * 
	 * @param s     按钮显示名称
	 * @param icon  按钮显示的图标
	 * @param frame 被添加到哪个frame上，便于后面进行选项卡添加操作
	 * @param per   Person对象
	 */
	public JCButton(String s, ImageIcon icon, BackgroundTabbedFrame frame, Person per) {
		super(s, icon, JLabel.CENTER);
		this.person = per;
		this.jf = frame;
		this.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		this.setOpaque(true);
		this.setBackground(Color.WHITE);
		this.setVerticalTextPosition(JButton.BOTTOM);
		this.setHorizontalTextPosition(JButton.CENTER);
		this.setBorder(new ShadowBorder());
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (s.contains("添加教师账号")) {
					listenerAddTeacherAccount();
				} else if (s.contains("添加学生账号")) {
					listenerAddStudentAccount();
				} else if (s.contains("图书馆")) {
					listenerLibrary();
				} else if (s.contains("管理书籍信息")) {
					listenerLibraryManager();
				} else if (s.contains("校园商店")) {
					listenerShop();
				} else if (s.contains("教务系统") && per.getJurisdiction() == 5) {
					listenerStudentJwc();
				} else if (s.contains("教务系统") && per.getJurisdiction() == 4) {
					listenerTeacherJwc();
				} else if (s.contains("商店管理")) {
					listenerShopManager();
				} else if (s.contains("个人信息") && per.getJurisdiction() == 5) {
					listenerStudentPersonnalInfo();
				} else if (s.contains("个人信息") && per.getJurisdiction() == 4) {
					listenerTeacherPersonnalInfo();
				} else if (s.contains("一卡通管理")) {
					listenerCampusBank();
				} else if (s.contains("卡片充值")) {
					listenerBankPay(1);
				} else if (s.contains("缴网费")) {
					listenerBankPay(2);
				} else if (s.contains("我的账单")) {
					listenerMyBill();
				} else if (s.contains("消费报告")) {
					listenerMyBankReport();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(MyType.defaultColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(Color.WHITE);
			}
		});
	}

	// [start], 教务老师
	/**
	 * 添加教师账号的界面
	 */
	public void listenerAddTeacherAccount() {
		FunctionPanel jp = new FunctionPanel(jf, "添加教师账号");

		// [start], elements
		JLabel descripLabel = new JLabel("添加教师账号");
		descripLabel.setFont(new Font("微软雅黑", Font.BOLD, 22));
		JLabel zhanghaoxinxi = new JLabel("  账号信息：");
		JLabel gerenxinxi = new JLabel("  个人信息：");
		JLabel gongzuoxinxi = new JLabel("  工作信息：");
		zhanghaoxinxi.setFont(new Font("微软雅黑", Font.BOLD, 18));
		gerenxinxi.setFont(new Font("微软雅黑", Font.BOLD, 18));
		gongzuoxinxi.setFont(new Font("微软雅黑", Font.BOLD, 18));

		JLabel yikatonghao = new JLabel("  一卡通号：");
		JLabel bieming = new JLabel("  别名（可不填）：");
		JLabel mima = new JLabel("  密码：");
		JLabel querenmima = new JLabel("  确认密码：");
		JLabel xingming = new JLabel("  姓名：");
		JLabel xingbie = new JLabel("  性别：");
		JLabel nianling = new JLabel("  年龄：");
		JLabel xueyuan = new JLabel("  学院：");
		JTextField account = new JTextField(12);
		JTextField nickname = new JTextField(12);
		JPasswordField password = new JPasswordField(16);
		JPasswordField checkPassword = new JPasswordField(16);
		JTextField name = new JTextField(8);
		String[] temp = { "男", "女" };
		JComboBox<String> gender = new JComboBox<String>(temp);
		JTextField age = new JTextField(4);
		JTextField department = new JTextField(20);
		JButton addIt = new JButton("添加教师账号");
		JButton exit = getExit();
		// [end]

		// [start], size & location
		int basex = 100, basey = 100, spacex = 120, spacey = 50, widthx = 120, heighty = 25, offset = 20;
		descripLabel.setBounds(basex - offset, basey - spacey - 15, 200, heighty);
		zhanghaoxinxi.setBounds(basex - offset, basey, widthx, heighty);
		yikatonghao.setBounds(basex, basey + spacey, widthx, heighty);
		account.setBounds(basex + spacex - offset, basey + spacey, widthx, heighty);
		bieming.setBounds(basex + 2 * spacex, basey + spacey, widthx, heighty);
		nickname.setBounds(basex + 3 * spacex, basey + spacey, widthx, heighty);
		mima.setBounds(basex + 4 * spacex + offset, basey + spacey, widthx, heighty);
		password.setBounds(basex + 5 * spacex - offset, basey + spacey, widthx, heighty);
		querenmima.setBounds(basex + 6 * spacex, basey + spacey, widthx, heighty);
		checkPassword.setBounds(basex + 7 * spacex - offset, basey + spacey, widthx, heighty);

		gerenxinxi.setBounds(basex - 20, basey + 2 * spacey, widthx, heighty);
		xingming.setBounds(basex, basey + 3 * spacey, widthx, heighty);
		name.setBounds(basex + spacex - offset, basey + 3 * spacey, widthx, heighty);
		xingbie.setBounds(basex + 2 * spacex, basey + 3 * spacey, widthx, heighty);
		gender.setBounds(basex + 3 * spacex, basey + 3 * spacey, widthx, heighty);
		nianling.setBounds(basex + 4 * spacex + offset, basey + 3 * spacey, widthx, heighty);
		age.setBounds(basex + 5 * spacex - offset, basey + 3 * spacey, widthx, heighty);

		gongzuoxinxi.setBounds(basex - 20, basey + 4 * spacey, widthx, heighty);
		xueyuan.setBounds(basex, basey + 5 * spacey, widthx, heighty);
		department.setBounds(basex + spacex - offset, basey + 5 * spacey, widthx * 3, heighty);

		addIt.setBounds(X - 270, Y - 80, 120, 30);
		exit.setBounds(X - 110, Y - 80, 60, 30);
		// [end]

		// function: add teacher
		addIt.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StringBuffer pwbuffer = new StringBuffer();
				pwbuffer.append(password.getPassword());
				StringBuffer cpwbuffer = new StringBuffer();
				cpwbuffer.append(checkPassword.getPassword());
				if (account.getText().length() != 0 && pwbuffer.toString().length() != 0
						&& cpwbuffer.toString().length() != 0) {
					if (pwbuffer.toString().equals(cpwbuffer.toString())) {
						int result = passdata.register_t(account.getText(), nickname.getText(), pwbuffer.toString(),
								name.getText(), 4, Integer.parseInt(age.getText()),
								gender.getSelectedItem().equals("男") ? 1 : 2, department.getText());
						if (result == 1)
							JOptionPane.showMessageDialog(null, "账号添加成功！");
					} else
						JOptionPane.showMessageDialog(null, "两次密码输入不一致！");
				}
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
		jp.add(descripLabel);
		jp.add(zhanghaoxinxi);
		jp.add(yikatonghao);
		jp.add(account);
		jp.add(bieming);
		jp.add(nickname);
		jp.add(mima);
		jp.add(password);
		jp.add(querenmima);
		jp.add(checkPassword);
		jp.add(gerenxinxi);
		jp.add(xingming);
		jp.add(name);
		jp.add(nianling);
		jp.add(age);
		jp.add(xingbie);
		jp.add(gender);
		jp.add(gongzuoxinxi);
		jp.add(xueyuan);
		jp.add(department);
		jp.add(addIt);
		jp.add(exit);
		jp.add(getVcampus());
		// [end]

		jf.addFunctionPanel(jp, "添加教师账号");
	}

	/**
	 * 添加学生账号的界面
	 */
	public void listenerAddStudentAccount() {
		FunctionPanel jp = new FunctionPanel(jf, "添加学生账号");

		// [start], elements
		JLabel descripLabel = new JLabel("添加学生账号");
		descripLabel.setFont(new Font("微软雅黑", Font.BOLD, 22));
		JLabel zhanghaoxinxi = new JLabel("  账号信息：");
		JLabel gerenxinxi = new JLabel("  个人信息：");
		JLabel xuejixinxi = new JLabel("  学籍信息：");
		zhanghaoxinxi.setFont(new Font("微软雅黑", Font.BOLD, 18));
		gerenxinxi.setFont(new Font("微软雅黑", Font.BOLD, 18));
		xuejixinxi.setFont(new Font("微软雅黑", Font.BOLD, 18));

		JLabel yikatonghao = new JLabel("  一卡通号：");
		JLabel bieming = new JLabel("  别名（可不填）：");
		JLabel mima = new JLabel("  密码：");
		JLabel querenmima = new JLabel("  确认密码：");
		JLabel xingming = new JLabel("  姓名：");
		JLabel xingbie = new JLabel("  性别：");
		JLabel nianling = new JLabel("  年龄：");
		JLabel xueyuan = new JLabel("  学院：");
		JLabel zhuanye = new JLabel("  专业：");
		JLabel ruxueriqi = new JLabel("  入学日期：");
		JLabel nian = new JLabel("  年："), yue = new JLabel("  月："), ri = new JLabel("  日：");
		JTextField account = new JTextField(12);
		JTextField nickname = new JTextField(12);
		JPasswordField password = new JPasswordField(16);
		JPasswordField checkPassword = new JPasswordField(16);
		JTextField name = new JTextField(8);
		String[] temp = { "男", "女" };
		JComboBox<String> gender = new JComboBox<String>(temp);
		JTextField age = new JTextField(4);
		JTextField department = new JTextField(20);
		JTextField major = new JTextField();
		Integer[] yearArray = { 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025 };
		Integer[] monthArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		Integer[] dayArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
				25, 26, 27, 28, 29, 30, 31 };
		JComboBox<Integer> year = new JComboBox<Integer>(yearArray);
		JComboBox<Integer> month = new JComboBox<Integer>(monthArray);
		JComboBox<Integer> day = new JComboBox<Integer>(dayArray);
		JButton addIt = new JButton("添加学生账号");
		JButton exit = getExit();
		// [end]

		// [start], size & location
		int basex = 100, basey = 100, spacex = 120, spacey = 50, widthx = 120, heighty = 25, offset = 20;
		descripLabel.setBounds(basex - offset, basey - spacey - 15, 200, heighty);
		zhanghaoxinxi.setBounds(basex - offset, basey, widthx, heighty);
		yikatonghao.setBounds(basex, basey + spacey, widthx, heighty);
		account.setBounds(basex + spacex - offset, basey + spacey, widthx, heighty);
		bieming.setBounds(basex + 2 * spacex, basey + spacey, widthx, heighty);
		nickname.setBounds(basex + 3 * spacex, basey + spacey, widthx, heighty);
		mima.setBounds(basex + 4 * spacex + offset, basey + spacey, widthx, heighty);
		password.setBounds(basex + 5 * spacex - offset, basey + spacey, widthx, heighty);
		querenmima.setBounds(basex + 6 * spacex, basey + spacey, widthx, heighty);
		checkPassword.setBounds(basex + 7 * spacex - offset, basey + spacey, widthx, heighty);

		gerenxinxi.setBounds(basex - 20, basey + 2 * spacey, widthx, heighty);
		xingming.setBounds(basex, basey + 3 * spacey, widthx, heighty);
		name.setBounds(basex + spacex - offset, basey + 3 * spacey, widthx, heighty);
		xingbie.setBounds(basex + 2 * spacex, basey + 3 * spacey, widthx, heighty);
		gender.setBounds(basex + 3 * spacex, basey + 3 * spacey, widthx, heighty);
		nianling.setBounds(basex + 4 * spacex + offset, basey + 3 * spacey, widthx, heighty);
		age.setBounds(basex + 5 * spacex - offset, basey + 3 * spacey, widthx, heighty);

		xuejixinxi.setBounds(basex - 20, basey + 4 * spacey, widthx, heighty);
		xueyuan.setBounds(basex, basey + 5 * spacey, widthx, heighty);
		department.setBounds(basex + spacex - offset, basey + 5 * spacey, widthx * 3, heighty);
		zhuanye.setBounds(basex + 4 * spacex, basey + 5 * spacey, widthx, heighty);
		major.setBounds(basex + 5 * spacex - offset, basey + 5 * spacey, widthx * 2, heighty);

		ruxueriqi.setBounds(basex, basey + 6 * spacey, widthx, heighty);
		widthx = 80;
		spacex = 80;
		nian.setBounds(basex + spacex + offset, basey + 6 * spacey, widthx, heighty);
		year.setBounds(basex + 2 * spacex, basey + 6 * spacey, widthx, heighty);
		yue.setBounds(basex + 3 * spacex + offset, basey + 6 * spacey, widthx, heighty);
		month.setBounds(basex + 4 * spacex, basey + 6 * spacey, widthx, heighty);
		ri.setBounds(basex + 5 * spacex + offset, basey + 6 * spacey, widthx, heighty);
		day.setBounds(basex + 6 * spacex, basey + 6 * spacey, widthx, heighty);

		addIt.setBounds(X - 270, Y - 80, 120, 30);
		exit.setBounds(X - 110, Y - 80, 60, 30);
		// [end]

		// function: add student
		addIt.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StringBuffer pwbuffer = new StringBuffer();
				pwbuffer.append(password.getPassword());
				StringBuffer cpwbuffer = new StringBuffer();
				cpwbuffer.append(checkPassword.getPassword());
				if (account.getText().length() != 0 && pwbuffer.toString().length() != 0
						&& cpwbuffer.toString().length() != 0) {
					if (pwbuffer.toString().equals(cpwbuffer.toString())) {
						int result = passData.register_s(account.getText(), nickname.getText(), pwbuffer.toString(),
								name.getText(), 5, Integer.parseInt(age.getText()),
								gender.getSelectedItem().equals("男") ? 1 : 2,
								Integer.parseInt(year.getSelectedItem().toString()),
								Integer.parseInt(month.getSelectedItem().toString()),
								Integer.parseInt(day.getSelectedItem().toString()), major.getText(),
								department.getText());
						if (result == 1)
							JOptionPane.showMessageDialog(null, "账号添加成功！");
					} else
						JOptionPane.showMessageDialog(null, "两次密码输入不一致！");
				}
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
		jp.add(descripLabel);
		jp.add(zhanghaoxinxi);
		jp.add(yikatonghao);
		jp.add(account);
		jp.add(bieming);
		jp.add(nickname);
		jp.add(mima);
		jp.add(password);
		jp.add(querenmima);
		jp.add(checkPassword);
		jp.add(gerenxinxi);
		jp.add(xingming);
		jp.add(name);
		jp.add(nianling);
		jp.add(age);
		jp.add(xingbie);
		jp.add(gender);
		jp.add(xuejixinxi);
		jp.add(xueyuan);
		jp.add(department);
		jp.add(zhuanye);
		jp.add(major);
		jp.add(ruxueriqi);
		jp.add(nian);
		jp.add(year);
		jp.add(yue);
		jp.add(month);
		jp.add(ri);
		jp.add(day);
		jp.add(addIt);
		jp.add(exit);
		jp.add(getVcampus());
		// [end]

		jf.addFunctionPanel(jp, "添加学生账号");
	}
	// [end]

	// [start], 图书馆
	/**
	 * 学生/教师图书馆主界面
	 */
	public void listenerLibrary() {
		// panels
		FunctionPanel jp = new FunctionPanel(jf, "图书馆");
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JLayeredPane layerLeft = new JLayeredPane();
		JLayeredPane layerRight = new JLayeredPane();
		JPanel personnal = new JPanel(null); // 个人图书馆
		JPanel holding = new JPanel(null); // 馆藏信息
		personnal.setBackground(Color.WHITE);
		holding.setBackground(Color.WHITE);

		// background
		JLabel labelLeft = new JLabel(bgleft);
		JPanel bgpanelLeft = new JPanel();
		bgpanelLeft.setBounds(0, -5, X / 2, Y); // -5是玄学offset
		bgpanelLeft.add(labelLeft);
		layerLeft.add(bgpanelLeft, JLayeredPane.DEFAULT_LAYER);

		JLabel labelRight = new JLabel(bgright);
		JPanel bgpanelRight = new JPanel();
		bgpanelRight.setBounds(0, -5, X / 2, Y); // -5是玄学offset
		bgpanelRight.add(labelRight);
		layerRight.add(bgpanelRight, JLayeredPane.DEFAULT_LAYER);

		// elements
		JLabel wodejieyue = new JLabel("我的借阅：");
		JLabel guancangchaxun = new JLabel("馆藏查询：");
		wodejieyue.setFont(new Font("微软雅黑", Font.BOLD, 16));
		guancangchaxun.setFont(new Font("微软雅黑", Font.BOLD, 16));
		wodejieyue.setForeground(Color.WHITE);
		guancangchaxun.setForeground(Color.WHITE);

		JTextField searchIt = new JTextField("请输入想要查询的书名，支持模糊搜索");
		JButton searchButton = new JButton("查询！");
		JButton popularOrder = new JButton("按借阅量排序");
		JButton matchingOrder = new JButton("按匹配度排序");
		JButton canBorrowOrder = new JButton("按可借阅数量排序");
//		Object[][] contBorrow =new Object[][];
		Object[][] contBorrow = passData.show_s(passData.search_b_s(person.account_number)).clone();
		// TODO
//		Object[][] contBorrow = { { "003", "两周速成深度学习", new Date(), new Date() },
//				{ "004", "第五项修炼", new Date(), new Date() }, { "005", "程序设计竞赛指南", new Date(), new Date() }, };
		contBorrow = JBorrowTable.parseDateToString(contBorrow);
//		Object[][] contCollect = {};
		Object[][] contCollect = passData.show_b(passData.show_all_b());
		// TODO: 接口
		// getBorrowContent，返回用户的借阅信息。输入是person对象/一卡通号，业务逻辑那边怎么方便怎么来
		// 日期要Date类型，我可以顺便转换成string

		borrowTable = new JBorrowTable(contBorrow, this);
		collectionTable = new JCollectionTable(contCollect, this);
		borrowTable.setPreferredScrollableViewportSize(new Dimension(500, 460));
		collectionTable.setPreferredScrollableViewportSize(new Dimension(500, 420));
		JScrollPane borrow = new JScrollPane(borrowTable);
		JScrollPane collection = new JScrollPane(collectionTable);
		borrow.getViewport().setBackground(Color.WHITE);
		collection.getViewport().setBackground(Color.WHITE);

		// [start], refresh
		JLabel refresh = new JLabel("刷新", new ImageIcon(MyType.iconPath + "refresh.png"), JLabel.CENTER);
		refresh.setForeground(Color.WHITE);
		refresh.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				borrowTable.updateDisplay(passData.show_s(passData.search_b_s(person.account_number)).clone());
				borrow.setViewportView(borrowTable);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				refresh.setForeground(MyType.focusColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				refresh.setForeground(Color.BLACK);
			}
		});
		refresh.setBounds(470, 21, 80, 40);
		// [end]

		JButton exit = getExit();

		// [start], size & location
		personnal.setBounds(0, 0, X / 2, Y);
		wodejieyue.setBounds(50, 20, 100, 40);
		borrow.setBounds(50, 60, 500, 460);

		holding.setBounds(X / 2, 0, X / 2, Y);
		guancangchaxun.setBounds(40, 20, 100, 40);
		collection.setBounds(40, 520 - 420, 500, 420);
		exit.setBounds(X / 2 - 120, Y - 80, 60, 30);
		searchIt.setBounds(40, 60, 405, 30);

		searchButton.setBounds(465, 60, 73, 30);
		matchingOrder.setBounds(40, 540, 115, 30);
		popularOrder.setBounds(170, 540, 115, 30);
		canBorrowOrder.setBounds(300, 540, 140, 30);
		// [end]

		// function
		searchButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (searchIt.getText().length() != 0 && !searchIt.getText().contains("请输入想要查询的书名，支持模糊搜索")) {
					Object[][] contCollectNew = passData.show_b(passData.search_b("book_name", searchIt.getText()));
					// TODO: 查询书目接口，支持模糊搜索
					collectionTable.updateDisplay(contCollectNew);
				} else { // if (searchIt.getText().length() == 0) {
					Object[][] contCollect = passData.show_b(passData.show_all_b());
					// TODO: 查询书目接口，支持模糊搜索
					collectionTable.updateDisplay(contCollect);
				}

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
		matchingOrder.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (searchIt.getText().length() != 0 && !searchIt.getText().contains("请输入需要查询的书名，支持模糊搜索")) {
					Object[][] contCollectnew = passData.show_b(passData.sort("book_name", searchIt.getText(), "1"));
					// TODO: 查询书目接口，支持模糊搜索，按匹配度排序
					collectionTable.updateDisplay(contCollectnew);
				}
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
		popularOrder.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (searchIt.getText().length() != 0 && !searchIt.getText().contains("请输入需要查询的书名，支持模糊搜索")) {
					Object[][] contCollectnew = passData.show_b(passData.sort("book_name", searchIt.getText(), "2"));
					// TODO: 查询书目接口，支持模糊搜索，按总借阅量排序
					collectionTable.updateDisplay(contCollectnew);
				}
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
		canBorrowOrder.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (searchIt.getText().length() != 0 && !searchIt.getText().contains("请输入需要查询的书名，支持模糊搜索")) {
					Object[][] contCollectnew = passData.show_b(passData.sort("book_name", searchIt.getText(), "3"));

					collectionTable.updateDisplay(contCollectnew);
					// TODO: 查询书目接口，支持模糊搜索，按可借数量排序
				}

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
		personnal.add(wodejieyue);
		personnal.add(getVcampus());
		personnal.add(borrow);
		personnal.add(refresh);

		holding.add(guancangchaxun);
		holding.add(exit);
		holding.add(collection);
		holding.add(searchIt);
		holding.add(searchButton);
		holding.add(matchingOrder);
		holding.add(popularOrder);
		holding.add(canBorrowOrder);

		personnal.setBounds(0, 0, X / 2, Y);
		personnal.setOpaque(false);
		holding.setBounds(0, 0, X / 2, Y);
		holding.setOpaque(false);
		layerLeft.add(personnal, JLayeredPane.MODAL_LAYER);
		layerRight.add(holding, JLayeredPane.MODAL_LAYER);

		splitPane.setLeftComponent(layerLeft);
		splitPane.setRightComponent(layerRight);
		splitPane.setBounds(0, 0, X, Y);
		splitPane.setDividerLocation(0.5);
		// [end]

		jp.add(splitPane);
		jf.addFunctionPanel(jp, "图书馆");
	}

	/**
	 * 图书管理员界面
	 */
	public void listenerLibraryManager() {
		// panels
		FunctionPanel jp = new FunctionPanel(jf, "管理书籍信息");
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JLayeredPane layerLeft = new JLayeredPane();
		JPanel searchBook = new JPanel(null); // 管理/删除书籍
		JPanel addBook = new JPanel(null); // 添加书籍
		addBook.setBackground(Color.WHITE);
		searchBook.setBackground(Color.WHITE);

		// background, only left
		JLabel labelLeft = new JLabel(bgleft);
		JPanel bgpanelLeft = new JPanel();
		bgpanelLeft.setBounds(0, -5, X / 2, Y); // -5是玄学offset
		bgpanelLeft.add(labelLeft);
		layerLeft.add(bgpanelLeft, JLayeredPane.DEFAULT_LAYER);

		// elements
		JLabel shujiguanli = new JLabel("书籍管理：");
		shujiguanli.setFont(new Font("微软雅黑", Font.BOLD, 16));
		shujiguanli.setForeground(Color.WHITE);
		JTextField searchIt = new JTextField("请输入需要查询的书名/索书号");
		String[] searchTypeContent = { "书名", "索书号", };
		JComboBox searchType = new JComboBox(searchTypeContent);

		JLabel tianjiashuji = new JLabel("添加书籍：");
		tianjiashuji.setFont(new Font("微软雅黑", Font.BOLD, 16));
		tianjiashuji.setForeground(Color.BLACK);
		JLabel suoshuhao = new JLabel("索书号：");
		JTextField bookID = new JTextField();
		JLabel shuming = new JLabel("书名：");
		JTextField bookName = new JTextField();
		JLabel guancangshuliang = new JLabel("馆藏数量：");
		Integer[] bookNumberContent = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 18, 20, 30, 40 };
		JComboBox bookNumber = new JComboBox(bookNumberContent);
		JButton addIt = new JButton("添加书籍");

		JButton searchButton = new JButton("查询！");
		JButton popularOrder = new JButton("按借阅量排序");
		JButton matchingOrder = new JButton("按匹配度排序");
		JButton canBorrowOrder = new JButton("按可借阅数量排序");

		Object[][] contCollect = passData.show_b(passData.show_all_b());
//		Object[][] contCollect = {};

		libraryManagerTable = new JLibraryManagerTable(contCollect, this);
		libraryManagerTable.setPreferredScrollableViewportSize(new Dimension(500, 420));
		JScrollPane collection = new JScrollPane(libraryManagerTable);
		collection.getViewport().setBackground(Color.WHITE);

		JButton exit = getExit();

		// size & location & font
		shujiguanli.setBounds(50, 20, 100, 40);
		searchType.setBounds(50, 60, 70, 30);
		searchIt.setBounds(135, 60, 450 - 125, 30);
		searchButton.setBounds(475, 60, 73, 30);
		matchingOrder.setBounds(50, 540, 115, 30);
		popularOrder.setBounds(180, 540, 115, 30);
		canBorrowOrder.setBounds(310, 540, 140, 30);
		collection.setBounds(50, 520 - 420, 500, 420);

		suoshuhao.setFont(new Font("微软雅黑", Font.PLAIN, 14));

		tianjiashuji.setBounds(40, 20, 100, 40);
		int basex = 100, basey = 150, spacex = 150, spacey = 80, heighty = 28;
		suoshuhao.setBounds(basex, basey, 100, heighty);
		bookID.setBounds(basex + spacex, basey, 200, heighty);
		shuming.setBounds(basex, basey + spacey, 100, heighty);
		bookName.setBounds(basex + spacex, basey + spacey, 200, heighty);
		guancangshuliang.setBounds(basex, basey + 2 * spacey, 100, heighty);
		bookNumber.setBounds(basex + spacex, basey + 2 * spacey, 100, heighty);
		addIt.setBounds(basex, basey + 3 * spacey, 100, heighty);

		exit.setBounds(X / 2 - 120, Y - 80, 60, 30);

		JLabel tempVCampus = getVcampus();
		tempVCampus.setBounds(380, 10, 180, 64);

		// function
		searchButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (searchIt.getText().length() != 0 && !searchIt.getText().contains("请输入需要查询的书名/索书号")) {
					Object[][] contCollectnew = passData.show_b(passData.search_b(
							searchType.getSelectedItem().equals("书名") ? "book_name" : "book_id", searchIt.getText()));
					// TODO: 查询书目接口，支持模糊搜索
					libraryManagerTable.updateDisplay(contCollectnew);
				} else {
					Object[][] contCollectnew = passdata.show_b(passData.show_all_b());
					// TODO: 查询书目接口，支持模糊搜索
					libraryManagerTable.updateDisplay(contCollectnew);
				}

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
		matchingOrder.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (searchIt.getText().length() != 0 && !searchIt.getText().contains("请输入需要查询的书名/索书号")) {
					Object[][] contCollectnew = passData
							.show_b(passData.sort(searchType.getSelectedItem().equals("书名") ? "book_name" : "book_id",
									searchIt.getText(), "1"));
					libraryManagerTable.updateDisplay(contCollectnew);
					// TODO: 查询书目接口，支持模糊搜索，按匹配度排序
				}
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
		popularOrder.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (searchIt.getText().length() != 0 && !searchIt.getText().contains("请输入需要查询的书名/索书号")) {
					Object[][] contCollectnew = passData
							.show_b(passData.sort(searchType.getSelectedItem().equals("书名") ? "book_name" : "book_id",
									searchIt.getText(), "2"));
					libraryManagerTable.updateDisplay(contCollectnew);
					// TODO: 查询书目接口，支持模糊搜索，按总借阅量排序
				}
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
		canBorrowOrder.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (searchIt.getText().length() != 0 && !searchIt.getText().contains("请输入需要查询的书名，支持模糊搜索")) {
					Object[][] contCollectnew = passData
							.show_b(passData.sort(searchType.getSelectedItem().equals("书名") ? "book_name" : "book_id",
									searchIt.getText(), "3"));
					libraryManagerTable.updateDisplay(contCollectnew);
					// TODO: 查询书目接口，支持模糊搜索，按可借数量排序
				}
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
		addIt.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (bookID.getText().length() != 0 && bookName.getText().length() != 0) {
					PassData passData = new PassData();
					passData.add_book(bookID.getText(), bookName.getText(), bookNumber.getSelectedItem().toString());
					// TODO: 添加书籍接口，string索书号，string书名，int馆藏数量
					JOptionPane.showMessageDialog(null, "添加成功！");
				}
				libraryManagerTable.updateDisplay(passdata.show_b(passData.show_all_b()));
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

		// add elements
		searchBook.add(shujiguanli);
		searchBook.add(collection);
		searchBook.add(searchType);
		searchBook.add(searchIt);
		searchBook.add(searchButton);
		searchBook.add(matchingOrder);
		searchBook.add(popularOrder);
		searchBook.add(canBorrowOrder);

		addBook.add(tianjiashuji);
		addBook.add(suoshuhao);
		addBook.add(bookID);
		addBook.add(shuming);
		addBook.add(bookName);
		addBook.add(guancangshuliang);
		addBook.add(bookNumber);
		addBook.add(addIt);
		addBook.add(exit);
		addBook.add(tempVCampus);

		// panel & layer things
		searchBook.setBounds(0, 0, X / 2, Y);
		searchBook.setOpaque(false);
		addBook.setBounds(0, 0, X / 2, Y);
		addBook.setOpaque(true);
		layerLeft.add(searchBook, JLayeredPane.MODAL_LAYER);

		splitPane.setLeftComponent(layerLeft);
		splitPane.setRightComponent(addBook);
		splitPane.setBounds(0, 0, X, Y);
		splitPane.setDividerLocation(0.5);

		jp.add(splitPane);
		jf.addFunctionPanel(jp, "管理书籍信息");
	}

	// [end]

	// [start], 商店
	/**
	 * 学生/教师商店主界面
	 */
	public void listenerShop() {
		// panels
		FunctionPanel jp = new FunctionPanel(jf, "虚拟商店");

		// commodity lists
//		Commodity[] commoList = { new Commodity("", "学习资料", "C++ primer plus", 98, 0, 0.01, "", 1, 2, "213193904", ""),
//				new Commodity("", "学习资料", "C++ primer plus", 98, 0, 0.01, "", 1, 2, "213193904", ""), };
		ArrayList<Commodity> commoListArray = passdata.choose15(1, passdata.allCommodity());
		Commodity[] commoList = commoListArray.toArray(new Commodity[commoListArray.size()]);
		commoList = commoList == null ? new Commodity[0] : commoList;
		// TODO: 接口，返回给我推荐商品的数组，推荐算法随你们
		CommoScrollContent commoInfo = new CommoScrollContent(commoList, CommoScrollContent.SHOP, person);

		// [start], panel in middle
		JScrollPane commoScroll = new JScrollPane(commoInfo);
		commoScroll.getViewport().setOpaque(false);
		commoScroll.setBorder(new RoundBorder(0, Color.WHITE, 1));

		// size & location
		commoScroll.setBounds(41, 80, 1118, 460);

		// add it
		jp.add(commoScroll);

		// [end]

		// [start], top side
		String[] commoTypeContent = { "--商品种类--", "衣物&饰品", "食品&饮料", "学习用品", "学习资料", "体育器械", "娱乐相关", };
		JCComboBox commoType = new JCComboBox(commoTypeContent);
		String[] commoOriginContent = { "--商品来源--", "正规商家", "二手市场", };
		JCComboBox commoOrigin = new JCComboBox(commoOriginContent);
		JLabel jiagequjian = new JLabel("价格区间");
		JTextField priceLower = new JTextField();
		JTextField priceUpper = new JTextField();
		JLabel fromto = new JLabel("~");
		JTextField searchContent = new JTextField("  请输入搜索关键词");
		String[] orderWayContent = { "按匹配度排序", "按总销量排序" };
		JCComboBox orderWay = new JCComboBox(orderWayContent);
		JLabel searchIt = new JLabel("搜索");

		// format
		searchContent.setBorder(new RoundBorder(0, MyType.redColor, 2));
		searchContent.setAlignmentX((float) 0.5);
		searchIt.setBackground(MyType.redColor);
		searchIt.setOpaque(true);
		searchIt.setFont(new Font("微软雅黑", Font.BOLD, 15));
		searchIt.setForeground(Color.WHITE);
		searchIt.setHorizontalAlignment(JLabel.CENTER);
		priceLower.setBorder(new RoundBorder(0, MyType.redColor, 1));
		priceUpper.setBorder(new RoundBorder(0, MyType.redColor, 1));
		priceLower.setHorizontalAlignment(JLabel.CENTER);
		priceUpper.setHorizontalAlignment(JLabel.CENTER);

		// size & location
		commoType.setBounds(50, 20, 115, 30);
		commoOrigin.setBounds(190, 20, 115, 30);
		jiagequjian.setBounds(325, 20, 60, 30);
		priceLower.setBounds(390, 20, 50, 30);
		fromto.setBounds(443, 20, 10, 30);
		priceUpper.setBounds(457, 20, 50, 30);
		orderWay.setBounds(520, 20, 115, 30);
		searchContent.setBounds(650, 20, 430, 30);
		searchIt.setBounds(1080, 20, 70, 30);

		// function
		searchIt.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<Commodity> commoListNewList = passdata.searchCommodityH(orderWay.getSelectedIndex(),
						commoOrigin.getSelectedIndex() == 0 ? "null" : (commoOrigin.getSelectedIndex() + ""),
						priceLower.getText().length() == 0 ? (0 + "") : priceLower.getText(),
						priceUpper.getText().length() == 0 ? (2147483647 + "") : priceUpper.getText(),
						(searchContent.getText().length() == 0 || searchContent.getText().equals(" ")
								|| searchContent.getText().contains("  请输入搜索关键词")) ? "null" : searchContent.getText(),
						commoType.getSelectedIndex() == 0 ? "null" : commoType.getSelectedItem().toString());
				Commodity[] commoListNew = commoListNewList.toArray(new Commodity[commoListNewList.size()]);

				// TODO: 商品搜索接口
				// 参数分别为商品类别，来源（是否二手市场），价格区间（最低到最高，double类型，-1表示没有限制），排序方式，关键字
//				Commodity[] commoListNew = {
//						new Commodity("", "学习资料", "数据结构笔记", 98, 0, 0.01, "", 1, 2, "213193904", ""), };
				commoScroll.setViewportView(new CommoScrollContent(commoListNew, CommoScrollContent.SHOP, person)); // 刷新列表
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

		// add them
		jp.add(commoType);
		jp.add(commoOrigin);
		jp.add(jiagequjian);
		jp.add(priceLower);
		jp.add(fromto);
		jp.add(priceUpper);
		jp.add(orderWay);
		jp.add(searchContent);
		jp.add(searchIt);
		// [end]

		// [start], bottom side
//		JRedLabel shopcart = new JRedLabel(shopcartIcon, shopcartIconEntered, JLabel.CENTER);
//		JRedLabel myCommo = new JRedLabel(myCommoIcon, myCommoIconEntered, JLabel.CENTER);
//		JRedLabel myShoppingReport = new JRedLabel(myShoppingReportIcon, myShoppingReportIconEntered, JLabel.CENTER);
//		JRedLabel myShoppingDetail = new JRedLabel(myShoppingDetailIcon, myShoppingDetailIconEntered, JLabel.CENTER);
//		JRedLabel exit = new JRedLabel(shopExitIcon, shopExitIconEntered, JLabel.CENTER);
		JRedLabel shopcart = new JRedLabel(" 购物车", shopcartIconOld, JLabel.CENTER);
		JRedLabel myCommo = new JRedLabel("我的二手商品");
		JRedLabel myShoppingReport = new JRedLabel("我的消费记录");
		JRedLabel myShoppingDetail = new JRedLabel("我的消费明细");
		JRedLabel exit = new JRedLabel("退出");

		// size & location
		int PIXEL = 2, HEIGHT_BASE = 610, HEIGHT = 40; // JRedLabel.PIXEL
		shopcart.setBounds(0 - PIXEL, HEIGHT_BASE - HEIGHT + PIXEL, 120, HEIGHT);
		myCommo.setBounds(120 - 2 * PIXEL, HEIGHT_BASE - HEIGHT + PIXEL, 150, HEIGHT);
		myShoppingReport.setBounds(270 - 3 * PIXEL, HEIGHT_BASE - HEIGHT + PIXEL, 150, HEIGHT);
		myShoppingDetail.setBounds(420 - 4 * PIXEL, HEIGHT_BASE - HEIGHT + PIXEL, 150, HEIGHT);
		exit.setBounds(1200 - 80 - 19, HEIGHT_BASE - HEIGHT + PIXEL, 80, HEIGHT);

		// function
		// TODO: many things to add
		exit.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jf.tabbedPane.remove(jf.tabbedPane.getSelectedIndex());
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				exit.setForeground(MyType.redColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exit.setForeground(Color.BLACK);
			}
		});
		shopcart.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listenerShopcart();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				shopcart.setForeground(MyType.redColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				shopcart.setForeground(Color.BLACK);
			}
		});
		myCommo.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listenerMyCommo();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				myCommo.setForeground(MyType.redColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				myCommo.setForeground(Color.BLACK);
			}
		});
		myShoppingReport.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listenerMyShoppingReport();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				myShoppingReport.setForeground(MyType.redColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				myShoppingReport.setForeground(Color.BLACK);
			}
		});
		myShoppingDetail.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listenerMyShoppingDetail();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				myShoppingDetail.setForeground(MyType.redColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				myShoppingDetail.setForeground(Color.BLACK);
			}
		});

		// add them
		jp.add(shopcart);
		jp.add(myCommo);
		jp.add(myShoppingReport);
		jp.add(myShoppingDetail);
		jp.add(exit);
		// [end]

		jf.addFunctionPanel(jp, "虚拟商店");
	}

	/**
	 * 购物车界面
	 */
	public void listenerShopcart() {
		// panels
		FunctionPanel jp = new FunctionPanel(jf, "购物车");

		// [start], title & exit
		JLabel cartTitle = new JLabel("我的购物车");
		cartTitle.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		cartTitle.setBounds(50, 30, 120, 30);

		JRedLabel exit = new JRedLabel("退出");
		int PIXEL = JRedLabel.PIXEL, HEIGHT = 40;
		exit.setBounds(1200 - 80 - 19, 615 - HEIGHT + PIXEL, 80, HEIGHT);
		exit.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jf.tabbedPane.remove(jf.tabbedPane.getSelectedIndex());
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				exit.setForeground(MyType.redColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exit.setForeground(Color.BLACK);
			}
		});
		// [end]

		// [start], panel in middle
		ArrayList<Commodity_Car> cartCommoArray = passdata.searchCommodityCar(person.account_number);
		Commodity_Car[] cartCommoCar = cartCommoArray.toArray(new Commodity_Car[cartCommoArray.size()]);
		Commodity[] cartCommo = new Commodity[cartCommoCar.length];
		for (int i = 0; i < cartCommoCar.length; ++i) {
			cartCommo[i] = new Commodity(cartCommoCar[i].getID(), cartCommoCar[i].getType(), cartCommoCar[i].getName(),
					cartCommoCar[i].getPrice(), cartCommoCar[i].getValue(), cartCommoCar[i].getDiscount(),
					cartCommoCar[i].getDate(), cartCommoCar[i].getNum(), cartCommoCar[i].getMarket(), "",
					cartCommoCar[i].getImage());
		}
//		Commodity_Car[] cartCommo = { new Commodity("", "学习资料", "C++ primer plus", 98, 0, 0.01, "", 1, 2, "213193904", ""),
//				new Commodity("", "学习资料", "C++ primer plus 全新 低价甩卖", 98, 0, 0.01, "", 1, 2, "213193904", ""), };
		// 接口，购物车的商品，根据商品id逐个找到商品
		JScrollPane commoScroll = new JScrollPane(new CommoScrollContent(cartCommo, CommoScrollContent.CART, person));
		commoScroll.getViewport().setOpaque(false);
		commoScroll.setBorder(new RoundBorder(0, Color.WHITE, 1));

		// size & location
		commoScroll.setBounds(41, 80, 1118, 460);
		// [end]

		// add them
		jp.add(commoScroll);
		jp.add(cartTitle);
		jp.add(exit);

		jf.addFunctionPanel(jp, "购物车");
	}

	/**
	 * 消费记录界面
	 */
	public void listenerMyShoppingDetail() {
		// panels
		FunctionPanel jp = new FunctionPanel(jf, "我的消费明细");

		// [start], title & exit
		JLabel cartTitle = new JLabel("我的消费明细");
		cartTitle.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		cartTitle.setBounds(50, 30, 120, 30);

		JRedLabel exit = new JRedLabel("退出");
		int PIXEL = JRedLabel.PIXEL, HEIGHT = 40;
		exit.setBounds(1200 - 80 - 19, 615 - HEIGHT + PIXEL, 80, HEIGHT);
		exit.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jf.tabbedPane.remove(jf.tabbedPane.getSelectedIndex());
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				exit.setForeground(MyType.redColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exit.setForeground(Color.BLACK);
			}
		});
		// [end]

		// [start], panel in middle
		Object[][] buyRecord = getBuyRecordFromArrayToObject(passdata.searchCommodityRecord(person.account_number));
		// 接口，购买记录的Object[][]数组，按日期【从最近到最遥远的过去】排序，我渲染成表格
//		Object[][] buyRecord = { { "2019-08-27", "C++ primer plus", "学习资料", "二手市场", 1, 0.98 },
//				{ "2021-06-25", "奶酪口味威化饼干", "食品&饮料", "正规商家", 3, 10.5 } };
		Object[] buyRecordHead = { "日期", "商品名称", "商品类型", "商品来源", "购买数量", "实付价格", };

		JTable buyRecordecordTable = new JTable(buyRecord, buyRecordHead);
		buyRecordecordTable.setSelectionBackground(MyType.focusColor);
		buyRecordecordTable.setSelectionForeground(Color.WHITE);
		buyRecordecordTable.setPreferredScrollableViewportSize(new Dimension(1118, 460));

		JScrollPane commoScroll = new JScrollPane(buyRecordecordTable);
		commoScroll.getViewport().setOpaque(false);

		// size & location
		commoScroll.setBounds(41, 80, 1118, 460);
		// [end]

		// add them
		jp.add(commoScroll);
		jp.add(cartTitle);
		jp.add(exit);

		jf.addFunctionPanel(jp, "我的消费明细");
	}

	/**
	 * 用于把ArrayList<Commodity_Record>转化为消费记录界面可以渲染的二维数组
	 * 
	 * @param array 被转换的ArrayList
	 * @return Object[][]二维数组
	 */
	private Object[][] getBuyRecordFromArrayToObject(ArrayList<Commodity_Record> array) {
		// "日期", "商品名称", "商品类型", "商品来源", "购买数量", "实付价格"
		Object[][] record = new Object[array.size()][6];
		for (int i = 0; i < array.size(); ++i) {
			record[i][0] = array.get(i).getDate();
			record[i][1] = array.get(i).getComm_name();
			record[i][2] = array.get(i).getType();
			record[i][3] = array.get(i).getMarket();
			record[i][4] = array.get(i).getNum();
			record[i][5] = array.get(i).getCost();
		}
		return record;
	}

	/**
	 * 消费报告界面
	 */
	public void listenerMyShoppingReport() {
		// [start], frame & panel & scroll & background
		int reportX = 360, reportY = 3216;

		JFrame frame = new JFrame();
		frame.setBounds(650 - (reportX + 34) / 2, 350 - 250, reportX + 36, 500);
		frame.setTitle("虚拟商店 - 我的消费报告");
		frame.setIconImage(vlogoIcon.getImage());

		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(0, 0, reportX + 34, 500);

		JLayeredPane layer = new JLayeredPane();
		layer.setPreferredSize(new Dimension(reportX, reportY));
		JLabel bglabel = new JLabel(new ImageIcon(MyType.figurePath + "shopReport.png"));
		JPanel bgpanel = new JPanel();
		bgpanel.setBounds(0, -5, reportX, reportY + 5);
		bgpanel.add(bglabel);
		layer.add(bgpanel, JLayeredPane.DEFAULT_LAYER);

		JPanel panel = new JPanel(null);
		panel.setBounds(0, 0, reportX, reportY);
		// [end]

		// [start], elements
		Object[] feather = passdata.spendReport(person.account_number);
		// TODO: 接口，返回消费特征：消费最多的类型，消费最少的类型，二手市场/正规商店，总金额
//		Object[] feather = { "学习资料", "衣物&饰品", 2, 300 };

		JLabel typeMost = new JLabel(feather[0].toString() + "！");
		typeMost.setHorizontalAlignment(JLabel.CENTER);
		JLabel typeLeast = new JLabel(feather[1].toString() + "！");
		typeLeast.setHorizontalAlignment(JLabel.CENTER);
		JLabel marketIntro = new JLabel("您毫不犹豫地选择了");
		marketIntro.setHorizontalAlignment(JLabel.CENTER);
		JLabel market = new JLabel((feather[2].equals(1) ? "正规商店" : "二手市场") + "！");
		market.setHorizontalAlignment(JLabel.CENTER);
		JLabel sumComsume = new JLabel("共  " + String.format("%.2f", feather[3]).toString() + "  元！");

		int fontSize = 28;
		typeMost.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		typeLeast.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		marketIntro.setFont(new Font("微软雅黑", Font.PLAIN, fontSize - 4));
		market.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		sumComsume.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));

		typeMost.setBounds(100, 1245, 180, 60);
		typeLeast.setBounds(100, 1720, 180, 60);
		marketIntro.setBounds(60, 2000, 250, 120);
		market.setBounds(100, 2197, 180, 60);
		sumComsume.setBounds(90, 2705, 240, 60);
		// [end]

		int ttttemp_piesize = 268, tttemp_piex = 46;
		PieChart typeContent = new PieChart(tttemp_piex, 1144, ttttemp_piesize, ttttemp_piesize,
				0.4, "学习资料", 0.2, "食品饮料",
				0.3, "衣物饰品", 0.1, "学习用品");
		PieChart typeMarket = new PieChart(tttemp_piex, 1649, ttttemp_piesize, ttttemp_piesize,
				0.6, "正规商家", 0.4, "二手市场",
				0, "", 0, "");
		PieChart typeSum = new PieChart(tttemp_piex, 2151, ttttemp_piesize, ttttemp_piesize,
				0.1, "", 0.9, "共 357.5 元，超过了",
				0, "", 0, "");
		typeContent.setBounds(tttemp_piex, 1144, ttttemp_piesize, ttttemp_piesize);
		typeMarket.setBounds(tttemp_piex, 1649, ttttemp_piesize, ttttemp_piesize);
		typeSum.setBounds(tttemp_piex, 2151, ttttemp_piesize, ttttemp_piesize);

		// [start], add them
//		panel.add(typeMost);
//		panel.add(typeLeast);
//		panel.add(marketIntro);
//		panel.add(market);
//		panel.add(sumComsume);
		panel.add(typeContent);
		panel.add(typeMarket);
		panel.add(typeSum);

//		panel.add(typeContent.labelInfo1);
//		panel.add(typeContent.labelInfo2);
//		panel.add(typeContent.labelInfo3);
//		panel.add(typeContent.labelInfo4);
//		panel.add(typeMarket.labelInfo1);
//		panel.add(typeMarket.labelInfo2);
//		panel.add(typeSum.labelInfo1);

		panel.setOpaque(false);
		layer.add(panel, JLayeredPane.MODAL_LAYER);
		scroll.setViewportView(layer);
		frame.add(scroll);
		// [end]

		frame.setVisible(true);
	}

	/** 一卡通管理的消费报告界面 */
	public void listenerMyBankReport() {
		// [start], frame & panel & scroll & background
		int reportX = 360, reportY = 2710;

		JFrame frame = new JFrame();
		frame.setBounds(650 - (reportX + 34) / 2, 350 - 250, reportX + 36, 500);
		frame.setTitle("一卡通管理 - 我的消费报告");
		frame.setIconImage(vlogoIcon.getImage());

		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(0, 0, reportX + 34, 500);

		JLayeredPane layer = new JLayeredPane();
		layer.setPreferredSize(new Dimension(reportX, reportY));
		JLabel bglabel = new JLabel(new ImageIcon(MyType.figurePath + "bankReport.png"));
		JPanel bgpanel = new JPanel();
		bgpanel.setBounds(0, -5, reportX, reportY + 5);
		bgpanel.add(bglabel);
		layer.add(bgpanel, JLayeredPane.DEFAULT_LAYER);

		JPanel panel = new JPanel(null);
		panel.setBounds(0, 0, reportX, reportY);
		// [end]


		int ttttemp_piesize = 268, tttemp_piex = 46;
		PieChart typeContent = new PieChart(tttemp_piex, 1144, ttttemp_piesize, ttttemp_piesize,
				0.4, "学习资料", 0.2, "食品饮料",
				0.3, "衣物饰品", 0.1, "学习用品");
		double ttt_a = 357.5, ttt_b = 70, ttt_c = 360, ttt_d = 17.3, ttt_s = ttt_a + ttt_b + ttt_c + ttt_d;
		StringBuilder builder_a = new StringBuilder("<html>"), builder_b = new StringBuilder("<html>"),
				builder_c = new StringBuilder("<html>"), builder_d = new StringBuilder("<html>");
		builder_a.append("消费：校园商店,", 0, 7).append("<br/>").append(ttt_a + "元,", 0, 6).append("</html>");
		builder_b.append("消费：缴网费,", 0, 6).append("<br/>").append(ttt_b + "元,", 0, 5).append("</html>");
		builder_c.append("收入：二手市场,", 0, 7).append("<br/>").append(ttt_d + "元,", 0, 5).append("</html>");
		builder_d.append("收入：一卡通充值,", 0, 8).append("<br/>").append(ttt_c + "元,", 0, 6).append("</html>");

		PieChart typeSum = new PieChart(tttemp_piex, 1640, ttttemp_piesize, ttttemp_piesize,
				/* ttt_a/ttt_s */ 0.4442,  builder_a.toString(),
				/* ttt_b/ttt_s */ 0.087, builder_b.toString(),
				/* ttt_d/ttt_s */ 0.0215, builder_d.toString(),
				/* ttt_c/ttt_s */ 0.448, builder_c.toString());
		typeContent.setBounds(tttemp_piex, 1144, ttttemp_piesize, ttttemp_piesize);
		typeSum.setBounds(tttemp_piex, 1640, ttttemp_piesize, ttttemp_piesize);

		// [start], add them
		panel.add(typeContent);
		panel.add(typeSum);

		panel.setOpaque(false);
		layer.add(panel, JLayeredPane.MODAL_LAYER);
		scroll.setViewportView(layer);
		frame.add(scroll);
		// [end]

		frame.setVisible(true);
	}

	/**
	 * 我的二手商品管理界面
	 */
	public void listenerMyCommo() {
		// panels
		FunctionPanel jp = new FunctionPanel(jf, "我的二手商品");

		// [start], title & exit
		JLabel cartTitle = new JLabel("我的二手商品");
		cartTitle.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		cartTitle.setBounds(50, 30, 150, 30);

		JRedLabel exit = new JRedLabel("退出");
		int PIXEL = JRedLabel.PIXEL, HEIGHT = 40;
		exit.setBounds(1200 - 80 - 19, 615 - HEIGHT + PIXEL, 80, HEIGHT);
		exit.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jf.tabbedPane.remove(jf.tabbedPane.getSelectedIndex());
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				exit.setForeground(MyType.redColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exit.setForeground(Color.BLACK);
			}
		});
		// [end]

		// [start], panel in middle
		ArrayList<Commodity> cartCommoArray = passdata.searchCommodity(10, person.account_number);
		Commodity[] cartCommo = cartCommoArray.toArray(new Commodity[cartCommoArray.size()]);
//		Commodity[] cartCommo = { new Commodity("", "学习资料", "数据结构笔记", 98, 0, 0.01, "", 1, 2, "213193904", ""),
//				new Commodity("", "学习资料", "C++ primer plus", 98, 0, 0.01, "", 1, 2, "213193904", ""),
//				new Commodity("", "学习资料", "工科数学分析 东南小题 半旧 附答案和解题思路", 98, 0, 0.01, "", 1, 2, "213193904", ""),
//				new Commodity("", "学习资料", "C++ primer plus 全新 低价甩卖", 98, 0, 0.01, "", 1, 2, "213193904", ""), };
		// TODO: 接口，我的二手商品，根据商品id逐个找到商品
		CommoScrollContent commoInfo = new CommoScrollContent(cartCommo, CommoScrollContent.SECOND_HAND, person);
		JScrollPane commoScroll = new JScrollPane(commoInfo);
		commoScroll.getViewport().setOpaque(false);
		commoScroll.setBorder(new RoundBorder(0, Color.WHITE, 1));

		// size & location
		commoScroll.setBounds(41, 80, 1118, 460);
		// [end]

		// [start], add second-hand commodity
		JRedLabel addCommo = new JRedLabel("添加二手商品");
		addCommo.setBounds(-PIXEL, 615 - HEIGHT + PIXEL, 120, HEIGHT);
		addCommo.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listenerAddSecondCommo(commoInfo);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				addCommo.setForeground(MyType.redColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				addCommo.setForeground(Color.BLACK);
			}
		});
		// [end]

		// add them
		jp.add(commoScroll);
		jp.add(cartTitle);
		jp.add(addCommo);
		jp.add(exit);

		jf.addFunctionPanel(jp, "我的二手商品");
	}

	/**
	 * 添加二手商品界面
	 * 
	 * @param commoInfo 指向“二手商品内容”的引用，用来在界面上同步添加二手商品
	 */
	public void listenerAddSecondCommo(CommoScrollContent commoInfo) {
		// [start], frame & panel
		JFrame frame = new JFrame();
		frame.setTitle("添加二手商品");
		frame.setIconImage(vlogoIcon.getImage());
		frame.setBounds(650 - 300, 350 - 200, 600, 400);

		JPanel panel = new JPanel(null);
		panel.setBounds(0, 0, 600, 400);
		panel.setBackground(Color.WHITE);
		panel.setOpaque(true);
		// [end]

		// [start], elements
		JLabel miaoshu = new JLabel("商品描述");
		miaoshu.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		JTextField description = new JTextField();
		description.setBorder(new RoundBorder(0, MyType.redColor, 1));
		description.setHorizontalAlignment(JTextField.CENTER);

		JLabel leibie = new JLabel("商品类别");
		leibie.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		String[] commoTypeContent = { "衣物&饰品", "食品&饮料", "学习用品", "学习资料", "体育器械", "娱乐相关", };
		JCComboBox type = new JCComboBox(commoTypeContent);

		JLabel danjia = new JLabel("商品单价");
		danjia.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		JTextField price = new JTextField();
		price.setBorder(new RoundBorder(0, MyType.redColor, 1));
		price.setHorizontalAlignment(JTextField.CENTER);

		JLabel zhekou = new JLabel("折扣");
		zhekou.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		JTextField discount = new JTextField();
		discount.setBorder(new RoundBorder(0, MyType.redColor, 1));
		discount.setHorizontalAlignment(JTextField.CENTER);

		JLabel shuliang = new JLabel("商品数量");
		shuliang.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		JTextField number = new JTextField();
		number.setBorder(new RoundBorder(0, MyType.redColor, 1));
		number.setHorizontalAlignment(JTextField.CENTER);

		JLabel tupian = new JLabel("商品图片");
		tupian.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		JTextField imageURL = new JTextField();
		imageURL.setBorder(new RoundBorder(0, MyType.redColor, 1));
		imageURL.setHorizontalAlignment(JTextField.CENTER);
		JRedLabel goThrough = new JRedLabel("…");

		JRedLabel addIt = new JRedLabel("添加");
		// [end]

		// [start], size & location
		int basex = 50, basey = 50, spacex = 120, spacey = 60, widthx = 80, heighty = 30, offset = 20;
		miaoshu.setBounds(basex, basey, widthx, heighty);
		description.setBounds(basex + 80, basey, 380, heighty);
		leibie.setBounds(basex, basey + spacey, widthx, heighty);
		type.setBounds(basex + spacex - 2 * offset, basey + spacey, widthx + offset + offset, heighty);
		shuliang.setBounds(basex + 2 * spacex + offset, basey + spacey, widthx, heighty);
		number.setBounds(basex + 3 * spacex - offset, basey + spacey, widthx + offset + offset, heighty);
		danjia.setBounds(basex, basey + 2 * spacey, widthx, heighty);
		price.setBounds(basex + spacex - 2 * offset, basey + 2 * spacey, widthx + offset + offset, heighty);
		zhekou.setBounds(basex + 2 * spacex + offset, basey + 2 * spacey, widthx, heighty);
		discount.setBounds(basex + 3 * spacex - offset, basey + 2 * spacey, widthx + offset + offset, heighty);
		tupian.setBounds(basex, basey + 3 * spacey, widthx, heighty);
		imageURL.setBounds(basex + 80, basey + 3 * spacey, 345, heighty);
		goThrough.setBounds(basex + 80 + 350, basey + 3 * spacey, 30, heighty);
		addIt.setBounds(460, 300, 80, 30);
		// [end]

		// [start], function
		goThrough.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser(); // 创建一个文件对话框
				chooser.setCurrentDirectory(new File(".")); // 设置文件对话框的当前目录
				chooser.setDialogType(JFileChooser.OPEN_DIALOG);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { // 单击了确定按钮
					// 获取在文件对话框中选择的文件
					imageURL.setText(chooser.getSelectedFile().getAbsolutePath().replace(System.getProperty("user.dir"), "."));
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				goThrough.setForeground(MyType.redColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				goThrough.setForeground(Color.BLACK);
			}
		});
		addIt.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (description.getText().length() != 0 && price.getText().length() != 0
						&& number.getText().length() != 0) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Commodity newSecondCommo = new Commodity(person.account_number + new Random().nextInt(10000),
							type.getSelectedItem().toString(), description.getText(), new Double(price.getText()), 0,
							discount.getText().length() == 0 ? 1 : new Double(discount.getText()),
							sdf.format(new Date()), new Integer(number.getText()), 2, person.account_number,
							imageURL.getText().length() == 0 ? "null" : imageURL.getText()); // 第一个参数，id不知道
					passdata.addCommodity(newSecondCommo);
					// TODO: 接口，post二手商品
					// 修改界面
					commoInfo.appendCommoList(newSecondCommo);
					frame.dispose();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				addIt.setForeground(MyType.redColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				addIt.setForeground(Color.BLACK);
			}
		});
		// [end]

		// [start], add elements
		panel.add(miaoshu);
		panel.add(description);
		panel.add(leibie);
		panel.add(type);
		panel.add(shuliang);
		panel.add(number);
		panel.add(danjia);
		panel.add(price);
		panel.add(zhekou);
		panel.add(discount);
		panel.add(tupian);
		panel.add(imageURL);
		panel.add(goThrough);
		panel.add(addIt);
		// [end]

		frame.add(panel);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	/**
	 * 商店管理员界面
	 */
	public void listenerShopManager() {
		FunctionPanel jp = new FunctionPanel(jf, "商店管理");

		// commodity lists
//		Commodity[] commoList = { new Commodity("", "学习资料", "C++ primer plus", 98, 0, 0.01, "", 1, 2, "213193904", ""),
//				new Commodity("", "学习资料", "C++ primer plus", 98, 0, 0.01, "", 1, 2, "213193904", ""), };
		ArrayList<Commodity> commoListArray = passdata.choose15(1, passdata.allCommodity());
		Commodity[] commoList = commoListArray.toArray(new Commodity[commoListArray.size()]);
		// TODO: 接口，返回给我推荐商品的数组，推荐算法随你们
		CommoScrollContent commoInfo = new CommoScrollContent(commoList, CommoScrollContent.MANAGER, person);

		// [start], panel in middle
		JScrollPane commoScroll = new JScrollPane(commoInfo);
		commoScroll.getViewport().setOpaque(false);
		commoScroll.setBorder(new RoundBorder(0, Color.WHITE, 1));

		// size & location
		commoScroll.setBounds(41, 80, 1118, 460);

		// add it
		jp.add(commoScroll);

		// [end]

		// [start], top side
		String[] commoTypeContent = { "--商品种类--", "衣物&饰品", "食品&饮料", "学习用品", "学习资料", "体育器械", "娱乐相关", };
		JCComboBox commoType = new JCComboBox(commoTypeContent);
		String[] commoOriginContent = { "--商品来源--", "正规商家", "二手市场", };
		JCComboBox commoOrigin = new JCComboBox(commoOriginContent);
		JLabel jiagequjian = new JLabel("价格区间");
		JTextField priceLower = new JTextField();
		JTextField priceUpper = new JTextField();
		JLabel fromto = new JLabel("~");
		JTextField searchContent = new JTextField("  请输入搜索关键词");
		String[] orderWayContent = { "按匹配度排序", "按总销量排序" };
		JCComboBox orderWay = new JCComboBox(orderWayContent);
		String[] searchWayContent = { "商品名称", "商品ID" };
		JCComboBox searchWay = new JCComboBox(searchWayContent);
		JLabel searchIt = new JLabel("搜索");

		// format
		searchContent.setBorder(new RoundBorder(0, MyType.redColor, 2));
		searchIt.setBackground(MyType.redColor);
		searchIt.setOpaque(true);
		searchIt.setFont(new Font("微软雅黑", Font.BOLD, 15));
		searchIt.setForeground(Color.WHITE);
		searchIt.setHorizontalAlignment(JLabel.CENTER);
		priceLower.setBorder(new RoundBorder(0, MyType.redColor, 1));
		priceUpper.setBorder(new RoundBorder(0, MyType.redColor, 1));
		priceLower.setHorizontalAlignment(JLabel.CENTER);
		priceUpper.setHorizontalAlignment(JLabel.CENTER);

		// size & location
		commoType.setBounds(50, 20, 115, 30);
		commoOrigin.setBounds(190, 20, 115, 30);
		jiagequjian.setBounds(325 - 3, 20, 60, 30);
		priceLower.setBounds(390 - 3, 20, 50, 30);
		fromto.setBounds(443 - 3, 20, 10, 30);
		priceUpper.setBounds(457 - 3, 20, 50, 30);
		orderWay.setBounds(520, 20, 115, 30);
		searchWay.setBounds(648, 20, 97, 30);
		searchContent.setBounds(760, 20, 320, 30);
		searchIt.setBounds(1080, 20, 70, 30);

		// function
		searchIt.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<Commodity> commoListNewList = null;
				if (searchWay.getSelectedIndex() == 0) {
					commoListNewList = passdata.searchCommodityH(orderWay.getSelectedIndex(),
							commoOrigin.getSelectedIndex() == 0 ? "null" : (commoOrigin.getSelectedIndex() + ""),
							priceLower.getText().length() == 0 ? (0 + "") : priceLower.getText(),
							priceUpper.getText().length() == 0 ? (2147483647 + "") : priceUpper.getText(),
							(searchContent.getText().length() == 0 || searchContent.getText().equals(" ")
									|| searchContent.getText().contains("  请输入搜索关键词")) ? "null"
											: searchContent.getText(),
							commoType.getSelectedIndex() == 0 ? "null" : commoType.getSelectedItem().toString());
				} else {
					commoListNewList = passdata.searchCommodity(1, searchContent.getText());
				}
				Commodity[] commoListNew = commoListNewList.toArray(new Commodity[commoListNewList.size()]);
				// TODO: 商品搜索接口

//				Commodity[] commoListNew = {
//						new Commodity("", "学习资料", "数据结构笔记", 98, 0, 0.01, "", 1, 2, "213193904", ""),
//						new Commodity("", "学习资料", "算法竞赛经典模板", 98, 0, 0.01, "", 1, 2, "213193904", ""), };
				commoScroll.setViewportView(new CommoScrollContent(commoListNew, CommoScrollContent.MANAGER, person)); // 刷新列表

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

		// add them
		jp.add(commoType);
		jp.add(commoOrigin);
		jp.add(jiagequjian);
		jp.add(priceLower);
		jp.add(fromto);
		jp.add(priceUpper);
		jp.add(orderWay);
		jp.add(searchWay);
		jp.add(searchContent);
		jp.add(searchIt);
		// [end]

		// [start], bottom side
		JRedLabel addCommo = new JRedLabel("添加商品");
		JRedLabel exit = new JRedLabel("退出");

		// size & location
		int PIXEL = JRedLabel.PIXEL, HEIGHT = 40;
		addCommo.setBounds(0 - PIXEL, 615 - HEIGHT + PIXEL, 120, HEIGHT);
		exit.setBounds(1200 - 80 - 19, 615 - HEIGHT + PIXEL, 80, HEIGHT);

		// function
		// TODO: many things to add
		exit.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jf.tabbedPane.remove(jf.tabbedPane.getSelectedIndex());
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				exit.setForeground(MyType.redColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exit.setForeground(Color.BLACK);
			}
		});
		addCommo.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listenerAddCommo();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				addCommo.setForeground(MyType.redColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				addCommo.setForeground(Color.BLACK);
			}
		});

		// add them
		jp.add(addCommo);
		jp.add(exit);
		// [end]

		jf.addFunctionPanel(jp, "商店管理");
	}

	/**
	 * 商店管理员添加商品界面
	 */
	public void listenerAddCommo() {
		// [start], frame & panel
		JFrame frame = new JFrame();
		frame.setTitle("添加商品");
		frame.setIconImage(vlogoIcon.getImage());
		frame.setBounds(650 - 300, 350 - 200, 600, 400);

		JPanel panel = new JPanel(null);
		panel.setBounds(0, 0, 600, 400);
		panel.setBackground(Color.WHITE);
		panel.setOpaque(true);
		// [end]

		// [start], elements
		JLabel shangpinID = new JLabel("商品ID");
		shangpinID.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		JTextField commoID = new JTextField();
		commoID.setBorder(new RoundBorder(0, MyType.redColor, 1));
		commoID.setHorizontalAlignment(JTextField.CENTER);

		JLabel miaoshu = new JLabel("商品描述");
		miaoshu.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		JTextField description = new JTextField();
		description.setBorder(new RoundBorder(0, MyType.redColor, 1));
		description.setHorizontalAlignment(JTextField.CENTER);

		JLabel leibie = new JLabel("商品类别");
		leibie.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		String[] commoTypeContent = { "衣物&饰品", "食品&饮料", "学习用品", "学习资料", "体育器械", "娱乐相关", };
		JCComboBox type = new JCComboBox(commoTypeContent);

		JLabel danjia = new JLabel("商品单价");
		danjia.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		JTextField price = new JTextField();
		price.setBorder(new RoundBorder(0, MyType.redColor, 1));
		price.setHorizontalAlignment(JTextField.CENTER);

		JLabel zhekou = new JLabel("折扣");
		zhekou.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		JTextField discount = new JTextField();
		discount.setBorder(new RoundBorder(0, MyType.redColor, 1));
		discount.setHorizontalAlignment(JTextField.CENTER);

		JLabel shuliang = new JLabel("商品数量");
		shuliang.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		JTextField number = new JTextField();
		number.setBorder(new RoundBorder(0, MyType.redColor, 1));
		number.setHorizontalAlignment(JTextField.CENTER);

		JLabel tupian = new JLabel("商品图片");
		tupian.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		JTextField imageURL = new JTextField();
		imageURL.setBorder(new RoundBorder(0, MyType.redColor, 1));
		imageURL.setHorizontalAlignment(JTextField.CENTER);
		JRedLabel goThrough = new JRedLabel("…");

		JRedLabel addIt = new JRedLabel("添加");
		// [end]

		// [start], size & location
		int basex = 50, basey = 50, spacex = 120, spacey = 60, widthx = 80, heighty = 30, offset = 20;
		shangpinID.setBounds(basex, basey, widthx, heighty);
		commoID.setBounds(basex + spacex - 2 * offset, basey, widthx + offset + offset, heighty);
		miaoshu.setBounds(basex + 2 * spacex + offset, basey, widthx, heighty);
		description.setBounds(basex + 3 * spacex - offset, basey, widthx + offset + offset, heighty);

		leibie.setBounds(basex, basey + spacey, widthx, heighty);
		type.setBounds(basex + spacex - 2 * offset, basey + spacey, widthx + offset + offset, heighty);
		shuliang.setBounds(basex + 2 * spacex + offset, basey + spacey, widthx, heighty);
		number.setBounds(basex + 3 * spacex - offset, basey + spacey, widthx + offset + offset, heighty);

		danjia.setBounds(basex, basey + 2 * spacey, widthx, heighty);
		price.setBounds(basex + spacex - 2 * offset, basey + 2 * spacey, widthx + offset + offset, heighty);
		zhekou.setBounds(basex + 2 * spacex + offset, basey + 2 * spacey, widthx, heighty);
		discount.setBounds(basex + 3 * spacex - offset, basey + 2 * spacey, widthx + offset + offset, heighty);

		tupian.setBounds(basex, basey + 3 * spacey, widthx, heighty);
		imageURL.setBounds(basex + 80, basey + 3 * spacey, 345, heighty);
		goThrough.setBounds(basex + 80 + 350, basey + 3 * spacey, 30, heighty);

		addIt.setBounds(460, 300, 80, 30);
		// [end]

		// [start], function
		goThrough.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser(); // 创建一个文件对话框
				chooser.setCurrentDirectory(new File(".")); // 设置文件对话框的当前目录
				chooser.setDialogType(JFileChooser.OPEN_DIALOG);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { // 单击了确定按钮
					// 获取在文件对话框中选择的文件
					imageURL.setText(chooser.getSelectedFile().getPath().replace(System.getProperty("user.dir"), "."));
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				goThrough.setForeground(MyType.redColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				goThrough.setForeground(Color.BLACK);
			}
		});
		addIt.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (commoID.getText().length() != 0 && description.getText().length() != 0
						&& price.getText().length() != 0 && number.getText().length() != 0) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Commodity newSecondCommo = new Commodity(commoID.getText(), type.getSelectedItem().toString(),
							description.getText(), Double.parseDouble(price.getText()), 0,
							discount.getText().length() == 0 ? 1 : Double.parseDouble(discount.getText()),
							sdf.format(new Date()), Integer.parseInt(number.getText()), 1, "",
							imageURL.getText().length() == 0 ? "" : imageURL.getText());
					passdata.addCommodity(newSecondCommo);
					// TODO: 接口，post二手商品
					frame.dispose();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				addIt.setForeground(MyType.redColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				addIt.setForeground(Color.BLACK);
			}
		});
		// [end]

		// [start], add elements
		panel.add(shangpinID);
		panel.add(commoID);
		panel.add(miaoshu);
		panel.add(description);
		panel.add(leibie);
		panel.add(type);
		panel.add(shuliang);
		panel.add(number);
		panel.add(danjia);
		panel.add(price);
		panel.add(zhekou);
		panel.add(discount);
		panel.add(tupian);
		panel.add(imageURL);
		panel.add(goThrough);
		panel.add(addIt);
		// [end]

		frame.add(panel);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	// [end]

	// [start], 个人信息
	/**
	 * 展示学生个人信息界面
	 */
	public void listenerStudentPersonnalInfo() {
		FunctionPanel jp = new FunctionPanel(jf, "个人信息");

		// 照片的要求是345*450

		// [start], background & panel
		JLayeredPane layer = new JLayeredPane();
		layer.setBounds(0, 0, 1200, 630);
		JLabel bglabel = new JLabel(new ImageIcon(MyType.figurePath + "personalInfo.png"));
		JPanel bgpanel = new JPanel();
		bgpanel.setBounds(0, -5, 1200, 635);
		bgpanel.add(bglabel);
		layer.add(bgpanel, JLayeredPane.DEFAULT_LAYER);

		JPanel panel = new JPanel(null);
		panel.setBounds(0, 0, 1200, 630);
		// [end]

		// [start], elements
		Object[] info = passData.status_information(passData.search_s("id", person.account_number));
//		Object[] info = { "213193904", "muni", "牟倪", 5, "16", 2, "2019-08-20", "计算机科学与技术", "计算机科学与工程学院",
//				MyType.figurePath + "myPhoto.jpg" };
		if (info[6] instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			info[6] = sdf.format(info[6]);
		}
		JLabel title = new JLabel(info[2] + " 的个人档案");
		title.setForeground(Color.WHITE);
		JLabel xingming = new JLabel("姓名：");
		JLabel name = new JLabel(info[2].toString());
		JLabel xingbie = new JLabel("性别：");
		JLabel gender = new JLabel(info[5].equals("2") ? "女" : "男");
		JLabel nianling = new JLabel("年龄：");
		JLabel age = new JLabel(info[4].toString());
		JLabel yikatonghao = new JLabel("一卡通号：");
		JLabel account = new JLabel(info[0].toString());
		JLabel bieming = new JLabel("别名：");
		JLabel nickname = new JLabel(info[1].toString());
		JLabel xueyuan = new JLabel("学院：");
		JLabel department = new JLabel(info[8].toString());
		JLabel zhuanye = new JLabel("专业：");
		JLabel major = new JLabel(info[7].toString());
		JLabel ruxiaoriqi = new JLabel("入校日期：");
		JLabel dateIn = new JLabel(info[6].toString());

		JLabel tupian = new JLabel("档案照片：");
		JLabel photo = new JLabel(new ImageIcon(info[9].toString()));
		JTextArea imageURL = new JTextArea(info[9].toString());
		imageURL.setBorder(new RoundBorder(0, MyType.seuGreenColor, 1));
		imageURL.setLineWrap(true);
		JButton searchURL = new JButton("浏览…");
		JButton okURL = new JButton("确定");

		JLabel vcampusLabel = getVcampus();
		JButton exit = getExit();
		// [end]

		// [start], size & location
		int basex = 500, basey = 160, spacex = 110, spacey = 80, widthx = 100, heighty = 30, offset = 20;
		title.setBounds(basex - 10, basey - 85, 3 * widthx, heighty);
		xingming.setBounds(basex, basey, widthx, heighty);
		name.setBounds(basex + spacex, basey, widthx, heighty);
		xingbie.setBounds(basex + 2 * spacex, basey, widthx, heighty);
		gender.setBounds(basex + 3 * spacex, basey, widthx, heighty);
		nianling.setBounds(basex + 4 * spacex, basey, widthx, heighty);
		age.setBounds(basex + 5 * spacex, basey, widthx, heighty);

		yikatonghao.setBounds(basex, basey + spacey, widthx, heighty);
		account.setBounds(basex + spacex, basey + spacey, widthx, heighty);
		bieming.setBounds(basex + 380, basey + spacey, widthx, heighty);
		nickname.setBounds(basex + 380 + spacex, basey + spacey, widthx, heighty);

		xueyuan.setBounds(basex, basey + 2 * spacey, widthx, heighty);
		department.setBounds(basex + spacex, basey + 2 * spacey, 3 * widthx, heighty);
		zhuanye.setBounds(basex, basey + 3 * spacey, widthx, heighty);
		major.setBounds(basex + spacex, basey + 3 * spacey, 3 * widthx, heighty);
		ruxiaoriqi.setBounds(basex, basey + 4 * spacey, widthx, heighty);
		dateIn.setBounds(basex + spacex, basey + 4 * spacey, 3 * widthx, heighty);

		photo.setBounds(20, 30, 345, 450);
		tupian.setBounds(20, 490, 100, 30);
		imageURL.setBounds(20, 520, 345, 40);
		searchURL.setBounds(205, 570, 70, 25);
		okURL.setBounds(295, 570, 70, 25);

		vcampusLabel.setBounds(970, 20, 180, 64);
		exit.setBounds(1200 - 120, 650 - 95, 60, 30);
		// [end]

		// [start], font...
		int fontSize = 14;
		title.setFont(new Font("微软雅黑", Font.BOLD, 22));
		xingming.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		name.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		xingbie.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		gender.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		nianling.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		age.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		yikatonghao.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		account.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		bieming.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		nickname.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		xueyuan.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		department.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		zhuanye.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		major.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		ruxiaoriqi.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		dateIn.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
//		.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));

		// [end]

		// [start], function
		searchURL.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(); // 创建一个文件对话框
				chooser.setCurrentDirectory(new File(".")); // 设置文件对话框的当前目录
				chooser.setDialogType(JFileChooser.OPEN_DIALOG);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { // 单击了确定按钮
					// 获取在文件对话框中选择的文件
					System.out.println("current path is " + System.getProperty("user.dir"));
					System.out.println("image path is " + chooser.getSelectedFile().getPath());
					imageURL.setText(chooser.getSelectedFile().getPath().replace(System.getProperty("user.dir"), "."));
					photo.setIcon(new ImageIcon(chooser.getSelectedFile().getPath()));
				}
			}
		}));
		okURL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				photo.setIcon(new ImageIcon(imageURL.getText()));
				passdata.addpic(person.account_number, imageURL.getText());
				// 数据库更新照片url
				JOptionPane.showMessageDialog(null, "照片更新成功！");
			}

		});
		// [end]

		// [start], add elements
		panel.add(title);
		panel.add(xingming);
		panel.add(name);
		panel.add(xingbie);
		panel.add(gender);
		panel.add(nianling);
		panel.add(age);
		panel.add(yikatonghao);
		panel.add(account);
		panel.add(bieming);
		panel.add(nickname);
		panel.add(xueyuan);
		panel.add(department);
		panel.add(zhuanye);
		panel.add(major);
		panel.add(ruxiaoriqi);
		panel.add(dateIn);
		panel.add(tupian);
		panel.add(photo);
		panel.add(imageURL);
		panel.add(searchURL);
		panel.add(okURL);
		panel.add(vcampusLabel);
		panel.add(exit);
		// [end]

		panel.setOpaque(false);
		layer.add(panel, JLayeredPane.MODAL_LAYER);
		jp.add(layer);
		jf.addFunctionPanel(jp, "个人信息");
	}

	/**
	 * 展示教师个人信息界面
	 */
	public void listenerTeacherPersonnalInfo() {
		FunctionPanel jp = new FunctionPanel(jf, "个人信息");

		// 照片的要求是345*450

		// [start], background & panel
		JLayeredPane layer = new JLayeredPane();
		layer.setBounds(0, 0, 1200, 630);
		JLabel bglabel = new JLabel(new ImageIcon(MyType.figurePath + "personalInfoTeacher.png"));
		JPanel bgpanel = new JPanel();
		bgpanel.setBounds(0, -5, 1200, 635);
		bgpanel.add(bglabel);
		layer.add(bgpanel, JLayeredPane.DEFAULT_LAYER);

		JPanel panel = new JPanel(null);
		panel.setBounds(0, 0, 1200, 630);
		// [end]

		// [start], elements
		Object[] info = passData.status_information(passData.search_s("id", person.account_number));
//		Object[] info = { "213193904", "muni", "牟倪", 5, "16", 2, "2019-08-20", "计算机科学与技术", "计算机科学与工程学院",
//		MyType.figurePath + "myPhoto.jpg" };
		if (info[6] instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			info[6] = sdf.format(info[6]);
		}
		JLabel title = new JLabel(info[2] + " 的个人档案");
		title.setForeground(Color.WHITE);
		JLabel xingming = new JLabel("姓名：");
		JLabel name = new JLabel(info[2].toString());
		JLabel xingbie = new JLabel("性别：");
		JLabel gender = new JLabel(info[5].equals(2) ? "女" : "男");
		JLabel nianling = new JLabel("年龄：");
		JLabel age = new JLabel(info[4].toString());
		JLabel yikatonghao = new JLabel("一卡通号：");
		JLabel account = new JLabel(info[0].toString());
		JLabel bieming = new JLabel("别名：");
		JLabel nickname = new JLabel(info[1].toString());
		JLabel xueyuan = new JLabel("学院：");
		JLabel department = new JLabel(info[8].toString());
//		JLabel zhuanye = new JLabel("专业：");
//		JLabel major = new JLabel(info[7].toString());
//		JLabel ruxiaoriqi = new JLabel("入校日期：");
//		JLabel dateIn = new JLabel(info[6].toString());

		JLabel tupian = new JLabel("档案照片：");
		JLabel photo = new JLabel(new ImageIcon(info[9].toString()));
		JTextArea imageURL = new JTextArea(info[9].toString());
		imageURL.setBorder(new RoundBorder(0, MyType.seuGreenColor, 1));
		imageURL.setLineWrap(true);
		JButton searchURL = new JButton("浏览…");
		JButton okURL = new JButton("确定");

		JLabel vcampusLabel = getVcampus();
		JButton exit = getExit();
		// [end]

		// [start], size & location
		int basex = 500, basey = 173, spacex = 110, spacey = 88, widthx = 100, heighty = 30, offset = 20;
		title.setBounds(490, 75, 3 * widthx, heighty);
		xingming.setBounds(basex, basey, widthx, heighty);
		name.setBounds(basex + spacex, basey, widthx, heighty);
		xingbie.setBounds(basex + 2 * spacex, basey, widthx, heighty);
		gender.setBounds(basex + 3 * spacex, basey, widthx, heighty);
		nianling.setBounds(basex + 4 * spacex, basey, widthx, heighty);
		age.setBounds(basex + 5 * spacex, basey, widthx, heighty);

		yikatonghao.setBounds(basex, basey + spacey, widthx, heighty);
		account.setBounds(basex + spacex, basey + spacey, widthx, heighty);
		bieming.setBounds(basex + 380, basey + spacey, widthx, heighty);
		nickname.setBounds(basex + 380 + spacex, basey + spacey, widthx, heighty);

		xueyuan.setBounds(basex, basey + 2 * spacey, widthx, heighty);
		department.setBounds(basex + spacex, basey + 2 * spacey, 3 * widthx, heighty);
//		zhuanye.setBounds(basex, basey + 3 * spacey, widthx, heighty);
//		major.setBounds(basex + spacex, basey + 3 * spacey, 3 * widthx, heighty);
//		ruxiaoriqi.setBounds(basex, basey + 4 * spacey, widthx, heighty);
//		dateIn.setBounds(basex + spacex, basey + 4 * spacey, 3 * widthx, heighty);

		photo.setBounds(20, 30, 345, 450);
		tupian.setBounds(20, 490, 100, 30);
		imageURL.setBounds(20, 520, 345, 40);
		searchURL.setBounds(205, 570, 70, 25);
		okURL.setBounds(295, 570, 70, 25);

		vcampusLabel.setBounds(970, 20, 180, 64);
		exit.setBounds(1200 - 120, 650 - 95, 60, 30);
		// [end]

		// [start], font...
		int fontSize = 14;
		title.setFont(new Font("微软雅黑", Font.BOLD, 22));
		xingming.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		name.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		xingbie.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		gender.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		nianling.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		age.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		yikatonghao.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		account.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		bieming.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		nickname.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		xueyuan.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		department.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
//		zhuanye.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
//		major.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
//		ruxiaoriqi.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
//		dateIn.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
		// [end]

		// [start], function
		searchURL.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(); // 创建一个文件对话框
				chooser.setCurrentDirectory(new File(".")); // 设置文件对话框的当前目录
				chooser.setDialogType(JFileChooser.OPEN_DIALOG);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { // 单击了确定按钮
					// 获取在文件对话框中选择的文件
					imageURL.setText(chooser.getSelectedFile().getPath());
					photo.setIcon(new ImageIcon(chooser.getSelectedFile().getPath()));
				}
			}
		}));
		okURL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				photo.setIcon(new ImageIcon(imageURL.getText()));
				// TODO: 数据库更新照片url
				JOptionPane.showMessageDialog(null, "照片更新成功！");
			}

		});
		// [end]

		// [start], add elements
		panel.add(title);
		panel.add(xingming);
		panel.add(name);
		panel.add(xingbie);
		panel.add(gender);
		panel.add(nianling);
		panel.add(age);
		panel.add(yikatonghao);
		panel.add(account);
		panel.add(bieming);
		panel.add(nickname);
		panel.add(xueyuan);
		panel.add(department);
//		panel.add(zhuanye);
//		panel.add(major);
//		panel.add(ruxiaoriqi);
//		panel.add(dateIn);
		panel.add(tupian);
		panel.add(photo);
		panel.add(imageURL);
		panel.add(searchURL);
		panel.add(okURL);
		panel.add(vcampusLabel);
		panel.add(exit);
		// [end]

		panel.setOpaque(false);
		layer.add(panel, JLayeredPane.MODAL_LAYER);
		jp.add(layer);
		jf.addFunctionPanel(jp, "个人信息");
	}

	// [end]

	// [start], 一卡通管理
	/**
	 * 一卡通管理主界面
	 */
	public void listenerCampusBank() {
		FunctionPanel jp = new FunctionPanel(jf, "一卡通管理");

		// [start], global support
		ImageIcon girlIcon = new ImageIcon(MyType.iconPath + "girl.png");
		ImageIcon boyIcon = new ImageIcon(MyType.iconPath + "boy.png");
		ImageIcon adminIcon = new ImageIcon(MyType.iconPath + "admin.png");
		ImageIcon rechargeIcon = new ImageIcon(MyType.iconPath + "recharge.png");
		ImageIcon netIcon = new ImageIcon(MyType.iconPath + "net.png");
		ImageIcon recordIcon = new ImageIcon(MyType.iconPath + "record.png");
		ImageIcon reportIcon = new ImageIcon(MyType.iconPath + "report.png");
		// [end]

		// [start], background & panel
		JLayeredPane layer = new JLayeredPane();
		layer.setBounds(0, 0, 1200, 630);
		JLabel bglabel = new JLabel(new ImageIcon(MyType.figurePath + "seubg4-halfcast-1200-650.jpg"));
		JPanel bgpanel = new JPanel();
		bgpanel.setBounds(0, -5, 1200, 635);
		bgpanel.add(bglabel);
		layer.add(bgpanel, JLayeredPane.DEFAULT_LAYER);

		JPanel panel = new JPanel(null);
		panel.setBounds(0, 0, 1200, 630);
		// [end]

		// [start], side bar elements
		// [start], elements
		SidebarPanel sidebar = new SidebarPanel(5);
		Object[] personalInfo = passData.status_information(passData.search_s("id", person.account_number));
		PersonLabel perLabel = new PersonLabel(personalInfo[2].toString(),
				person.getJurisdiction() == 4 ? adminIcon : (personalInfo[5].equals("1") ? boyIcon : girlIcon));
//		PersonLabel perLabel = new PersonLabel("刘尊颐", girlIcon); // TODO:待修改

		JPanel quesToAsk = new JPanel(null);
		JLabel mayYouWannaKnow = new JLabel("你可能想知道：");
		WannaKnowItem wannaKnow1 = new WannaKnowItem("   学生酬金如何发放");
		WannaKnowItem wannaKnow2 = new WannaKnowItem("   学生如何缴费");
		WannaKnowItem wannaKnow3 = new WannaKnowItem("   国际会议报销综合定额");

		JPanel dataToShow = new JPanel(null);
		JLabel myData = new JLabel("我的数据：");
		JLabel accountState = new JLabel("【一卡通】");
		JLabel netState = new JLabel("【校园网】");
		JLabel refresh = new JLabel("刷新", new ImageIcon(MyType.iconPath + "refresh.png"), JLabel.CENTER);
//		double balanceValue = getBalance();
//		double netBalanceValue = getNetBalance();
		// TODO: 接口，一卡通余额和网费余额
		double balanceValue = Double.parseDouble(personalInfo[10].toString());
		double netBalanceValue = Double.parseDouble(personalInfo[11].toString());
		JLabel balance = new JLabel("状态：正常 | 余额：" + String.format("%.2f", balanceValue) + " 元");
		JLabel netBalance = new JLabel("状态：正常 | 余额：" + String.format("%.2f", netBalanceValue) + " 元");
		balance.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		netBalance.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		// [end]

		// refresh function
		refresh.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object[] personalInfo = passData.status_information(passData.search_s("id", person.account_number));
				double balanceValue = Double.parseDouble(personalInfo[10].toString());
				double netBalanceValue = Double.parseDouble(personalInfo[11].toString());
				// 接口，一卡通余额和网费余额
				balance.setText("状态：正常 | 余额：" + String.format("%.2f", balanceValue) + " 元");
				netBalance.setText("状态：正常 | 余额：" + String.format("%.2f", netBalanceValue) + " 元");
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				refresh.setForeground(MyType.focusColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				refresh.setForeground(Color.BLACK);
			}
		});

		// [start], size & location, copy from StudentUI.java
		sidebar.setBounds(0, 0, 300, 650);
		sidebar.setBackground(new Color(220, 220, 240, 255));
		perLabel.setBounds(20, 10, 230, 80);

		// dataToShow
		dataToShow.setBorder(new WenwuBorder(0, new Color(46, 82, 141)));
		dataToShow.setBounds(30, 120, 230, 180);
		dataToShow.setOpaque(true);
		dataToShow.setBackground(Color.WHITE);

		myData.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		myData.setBounds(20, 15, 100, 30);
		refresh.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		refresh.setBounds(150, 21, 80, 20);
		accountState.setForeground(Color.GRAY);
		netState.setForeground(Color.GRAY);
		accountState.setBounds(20, 50, 100, 25);
		balance.setBounds(25, 70, 200, 30);
		netState.setBounds(20, 105, 100, 25);
		netBalance.setBounds(25, 125, 200, 30);

		dataToShow.add(myData);
		dataToShow.add(accountState);
		dataToShow.add(balance);
		dataToShow.add(netState);
		dataToShow.add(netBalance);
		dataToShow.add(refresh);

		// quesToAsk
		quesToAsk.setBorder(new WenwuBorder(0, new Color(46, 82, 141)));
		quesToAsk.setBounds(30, 330, 230, 220);
		quesToAsk.setOpaque(true);
		quesToAsk.setBackground(Color.WHITE);

		mayYouWannaKnow.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		mayYouWannaKnow.setBounds(20, 15, 100, 30);
		wannaKnow1.setBounds(30, 60, 135, 30);
		wannaKnow2.setBounds(30, 60 + 45, 110, 30);
		wannaKnow3.setBounds(30, 60 + 2 * 45, 165, 30);
		quesToAsk.add(mayYouWannaKnow);
		quesToAsk.add(wannaKnow1);
		quesToAsk.add(wannaKnow2);
		quesToAsk.add(wannaKnow3);
		// [end]
		// [end]

		// [start], middle
		// [start], elements
		JLabel title = new JLabel("一卡通余额管理系统");
		title.setFont(MyType.h1Font);
		title.setForeground(Color.WHITE);
		JCButton rechargeButton = new JCButton("卡片充值", rechargeIcon, jf, person);
		JCButton netButton = new JCButton("缴网费", netIcon, jf, person);
		JCButton recordButton = new JCButton("我的账单", recordIcon, jf, person);
		JCButton reportButton = new JCButton("消费报告", reportIcon, jf, person);

		JLabel vcampusLabel = getVcampus();
		JButton exit = getExit();
		// [end]

		// [start], size & location
		title.setBounds(360, 50, 500, 60);
		int basex = 390, basey = 250, spacex = 190, widthx = 100, heighty = 100;
		rechargeButton.setBounds(basex, basey, widthx, heighty);
		netButton.setBounds(basex + spacex, basey, widthx, heighty);
		recordButton.setBounds(basex + 2 * spacex, basey, widthx, heighty);
		reportButton.setBounds(basex + 3 * spacex, basey, widthx, heighty);

		vcampusLabel.setBounds(970, 20, 180, 64);
		exit.setBounds(1200 - 120, 650 - 95, 60, 30);
		// [end]
		// [end]

		// [start], add elements
		// side bar
		sidebar.add(perLabel);
		sidebar.add(quesToAsk);
		sidebar.add(dataToShow);
		panel.add(sidebar);

		panel.add(title);
		panel.add(rechargeButton);
		panel.add(netButton);
		panel.add(recordButton);
		panel.add(reportButton);

		panel.add(vcampusLabel);
		panel.add(exit);

		// [end]

		panel.setOpaque(false);
		layer.add(panel, JLayeredPane.MODAL_LAYER);
		jp.add(layer);
		jf.addFunctionPanel(jp, "一卡通管理");
	}

	/**
	 * 卡片充值/缴网费窗口
	 * 
	 * @param state 1为卡片充值，2为缴网费
	 */
	public void listenerBankPay(int state) {
		JFrame frame = new JFrame();
		frame.setIconImage(vlogoIcon.getImage());
		frame.setTitle(state == 1 ? "卡片充值" : "缴网费");
		frame.setBounds(650 - 150, 350 - 100, 300, 200);

		JPanel panel = new JPanel(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 300, 200);
		panel.setOpaque(true);

		// elements
		JLabel chargeHowMuch = new JLabel("充值金额：");
		Integer[] chargeList = { 10, 20, 30, 50, 100, 200, 300, 400, 500, 1000 };
		JComboBox charge = new JComboBox(chargeList);
		JLabel tellMePassword = new JLabel("一卡通密码：");
		JPasswordField password = new JPasswordField();
		JButton yes = new JButton("确定");
		JButton exit = new JButton("取消");

		// size & location
		chargeHowMuch.setBounds(30, 30, 85, 25);
		charge.setBounds(130, 30, 130, 25);
		tellMePassword.setBounds(30, 75, 85, 25);
		password.setBounds(130, 75, 130, 25);
		yes.setBounds(130, 120, 60, 25);
		exit.setBounds(200, 120, 60, 25);

		// function
		yes.addActionListener(new ActionListener() {
			Date date = new Date();
			SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date2 = date1.format(date);

			@Override
			public void actionPerformed(ActionEvent e) {
				StringBuffer pwbuffer = new StringBuffer();
				pwbuffer.append(password.getPassword());
				if (pwbuffer.toString().length() != 0) {
//					byte[] bt=pwbuffer.toString().getBytes();
//					int secret=8;
//					for(int i=0;i<bt.length;i++){
//						bt[i]=(byte)(bt[i]^(int)secret); //通过异或运算进行加密
//					}
//					String newresult=new String(bt,0,bt.length);
					System.out.print(pwbuffer.toString() + person.getpassword());
					if (!pwbuffer.toString().equals(person.getpassword())) {
						JOptionPane.showMessageDialog(null, "密码错误！");
						return;
					}
					if (state == 1) {

						Object[] personalInfo = passData
								.status_information(passData.search_s("id", person.account_number));
						double money = Double.parseDouble(personalInfo[10].toString())
								+ Double.parseDouble(charge.getSelectedItem().toString());

						// 选择支付方式
						// region

						// create
						// region
						int width_wayToPay = 380, height_wayToPay = 170;

						JFrame frameWayToPay = new JFrame();
						frameWayToPay.setIconImage(vlogoIcon.getImage());
						frameWayToPay.setTitle("选择支付方式");
						frameWayToPay.setBounds(650 - 200, 350 - 100, width_wayToPay, height_wayToPay);

						JPanel panelWayToPay = new JPanel(null);
						panelWayToPay.setBackground(Color.WHITE);
						panelWayToPay.setBounds(0, 0, width_wayToPay, height_wayToPay);
						panelWayToPay.setOpaque(true);

						// elements
						int ttttemp_height = 27;
						JButton alipay = new JButton("支付宝付款");
						alipay.setBounds(50, 40, 100, ttttemp_height);
						JButton otherpay = new JButton("其他付款方式");
						otherpay.setBounds(200, 40, 120, ttttemp_height);
						otherpay.setEnabled(false);
						// endregion

						// function
						alipay.addActionListener(new ActionListener() {
							Date date = new Date();
							SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String date2 = date1.format(date);

							@Override
							public void actionPerformed(ActionEvent e) {
								AlipayInfoInDTO alipayInfoIn = new AlipayInfoInDTO(
									person.account_number, Double.parseDouble(charge.getSelectedItem().toString()),
									"一卡通充值", null, null
								);
								AlipayInfoOutDTO alipayInfoOut = passdata.create_alipay_order(alipayInfoIn);
								System.out.println("longId = " + Long.toString(alipayInfoOut.getLongId()) + ", outTradeNo = " + alipayInfoOut.getOutTradeNo());

								String url = "explorer \"http://120.46.215.180:8088/goAlipay?id=" +
										String.valueOf(alipayInfoOut.getLongId()) + "&outTradeNo=" +
										alipayInfoOut.getOutTradeNo() + "\"\n";
//								String url = "explorer \"http://120.46.215.180:8088/goAlipay?id=123456&outTradeNo=321654\"\n";
								Process process = null;
								try {
									process = Runtime.getRuntime().exec(url);
								} catch (IOException ex) {
									ex.printStackTrace();
								}
								System.out.println("浏览器已开始运行！");
								frameWayToPay.dispose();

								PollingForOrderState polling = new PollingForOrderState(
										new AlipayResultInDTO(alipayInfoOut.getOutTradeNo()), null);
								polling.start();
							}
						});

						// add
						// region
						panelWayToPay.add(alipay);
						panelWayToPay.add(otherpay);
						frameWayToPay.add(panelWayToPay);
						frameWayToPay.setVisible(true);
						// endregion

						// endregion

//						JOptionPane.showMessageDialog(null, "卡片充值成功！");
//						passdata.recharge(person.account_number, money);
//						passdata.c_record(date2, person.account_number, 1,
//								Double.parseDouble(charge.getSelectedItem().toString()), money);
						// TODO: 赖泽升说后端负责 1. 一卡通充值，2. 插入消费 / 充值记录

						frame.dispose();
					} else if (state == 2) { // 缴网费
						Object[] personalInfo = passData
								.status_information(passData.search_s("id", person.account_number));
						if (Double.parseDouble(personalInfo[10].toString())
								- Double.parseDouble(charge.getSelectedItem().toString()) >= 0) {
							double web_money = Double.parseDouble(personalInfo[11].toString())
									+ Double.parseDouble(charge.getSelectedItem().toString());
							double money = Double.parseDouble(personalInfo[10].toString())
									- Double.parseDouble(charge.getSelectedItem().toString());
							passdata.web_recharge(person.account_number, web_money);
							passdata.recharge(person.account_number, money);
							passdata.c_record(date2, person.account_number, 3,
									Double.parseDouble(charge.getSelectedItem().toString()), money);
							// TODO: 缴网费接口
							JOptionPane.showMessageDialog(null, "缴网费成功！");
						} else {
							JOptionPane.showMessageDialog(null, "余额不足！");
						}
						frame.dispose();
					}
				}
			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		// add them
		panel.add(chargeHowMuch);
		panel.add(charge);
		panel.add(tellMePassword);
		panel.add(password);
		panel.add(yes);
		panel.add(exit);
		frame.add(panel);

		frame.setVisible(true);
	}

	/**
	 * 我的账单界面
	 */
	public void listenerMyBill() {
		// panels
		FunctionPanel jp = new FunctionPanel(jf, "我的账单");

		// [start], title & exit
		JLabel cartTitle = new JLabel("我的账单");
		cartTitle.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		cartTitle.setBounds(50, 30, 120, 30);

		JButton exit = getExit();
		exit.setBounds(1200 - 120, 650 - 95, 60, 30);
		// [end]

		// [start], panel in middle
		Object[][] record = passData.show_c_r(passdata.search_r("id", person.account_number));
		// TODO: 接口，账单的Object[][]数组，按日期【从最近到最遥远的过去】排序，我渲染成表格
//		Object[][] record = { { "2021-08-27", "校园商店", -40, 50 }, { "2021-08-26", "缴网费", -100, 90 },
//				{ "2020-12-08", "校园商店", -10, 190 }, { "2019-08-27", "卡片充值", 100, 200 }, };
		Object[] recordHead = { "日期", "账单来源", "账单金额（元）", "一卡通余额（元）", };

		JTable recordecordTable = new JTable(record, recordHead);
		recordecordTable.setSelectionBackground(MyType.focusColor);
		recordecordTable.setSelectionForeground(Color.WHITE);
		recordecordTable.setPreferredScrollableViewportSize(new Dimension(1118, 460));

		JScrollPane commoScroll = new JScrollPane(recordecordTable);
		commoScroll.getViewport().setOpaque(false);

		// size & location
		commoScroll.setBounds(70, 80, 1060, 460);
		// [end]

		// add them
		jp.add(commoScroll);
		jp.add(cartTitle);
		jp.add(exit);

		jf.addFunctionPanel(jp, "我的账单");
	}
	// [end]

	// [start], 教务系统
	/**
	 * 学生教务系统主界面
	 */
	public void listenerStudentJwc() {
		// panels
		FunctionPanel jp = new FunctionPanel(jf, "教务系统");

		// [start], title & exit
		JLabel title = new JLabel("教务系统 - 课表");
		title.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		title.setBounds(20, 10, 210, 30);

		JLabel exit = new JLabel(new ImageIcon(MyType.iconPath + "exitBlue.png"));
		exit.setBounds(1140, 483, 40, 80);
		exit.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jf.tabbedPane.remove(jf.tabbedPane.getSelectedIndex());
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				exit.setIcon(new ImageIcon(MyType.iconPath + "exitPurple.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exit.setIcon(new ImageIcon(MyType.iconPath + "exitBlue.png"));
			}
		});
		// [end]

		// [start], timetable
		ArrayList<Course> classesArray = passdata.all_course(person.account_number);
		Course[] classes = classesArray.toArray(new Course[classesArray.size()]);
		// 接口，目前学生已经选择的课程
//		Course[] classes = {
//				new Course("计算机组成原理专题实践（双语、研讨）", "090001", "213193904", 1, 8, 10, 0, 0, 0, 1, 40, 10, "j4-402"),
//				new Course("数据结构（双语）", "090002", "213193904", 2, 3, 4, 4, 3, 4, 0, 40, 40, "j2-502"),
//				new Course("数据库原理及应用（全英文）", "090003", "213193904", 3, 8, 9, 5, 8, 9, 0, 100, 96, "j4-102"), };
		Timetable timetable = new Timetable(classes, ClassBlock.STUDENT, person);
		JLabel refresh = new JLabel("刷新", new ImageIcon(MyType.iconPath + "refresh.png"), JLabel.CENTER);
		refresh.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<Course> classesArray = passdata.all_course(person.account_number);
				Course[] classes = classesArray.toArray(new Course[classesArray.size()]);
				timetable.setClasses(classes);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				refresh.setForeground(MyType.focusColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				refresh.setForeground(Color.BLACK);
			}
		});
		refresh.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		refresh.setBounds(1035, 21, 80, 20);
		// [end]

		// [start], choose classes
		JLabel chooseClass = new JLabel(new ImageIcon(MyType.iconPath + "chooseCourseBlue.png"));
		chooseClass.setBounds(1140, 400, 40, 80);
		chooseClass.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listenerStudentChooseClass(timetable);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				chooseClass.setIcon(new ImageIcon(MyType.iconPath + "chooseCoursePurple.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				chooseClass.setIcon(new ImageIcon(MyType.iconPath + "chooseCourseBlue.png"));
			}
		});

		// [end]

		jp.add(title);
		jp.add(exit);
		jp.add(chooseClass);
		jp.add(timetable);
		jp.add(refresh);

		jf.addFunctionPanel(jp, "教务系统");
	}

	/**
	 * 学生选课界面
	 * 
	 * @param timetable 指向课表的引用，用来同步更新课表内容
	 */
	public void listenerStudentChooseClass(Timetable timetable) {
		// panels
		FunctionPanel jp = new FunctionPanel(jf, "选课");

		// course lists
//		Course[] courseList = {
//				new Course("计算机组成原理专题实践（双语、研讨）", "090001", "213193904", 1, 6, 8, 0, 0, 0, 2, 40, 10, "j4-402"),
//				new Course("数据结构（双语）", "090002", "213193904", 2, 3, 4, 4, 3, 4, 1, 40, 40, "j2-502"),
//				new Course("数据库原理及应用（全英文）", "090003", "213193904", 3, 8, 9, 5, 8, 9, 1, 100, 96, "j4-102"), };
		ArrayList<Course> courseListArray = passdata.search_course("null", person.account_number, 0, 0, 0);
		Course[] courseList = courseListArray.toArray(new Course[courseListArray.size()]);
		System.out.println("成功得到了所有课程");
		// 接口，返回给我所有课程
		CourseScrollContent courseInfo = new CourseScrollContent(courseList, person, timetable, 0);

		// [start], panel in middle
		JScrollPane courseScroll = new JScrollPane(courseInfo);
		courseScroll.getViewport().setOpaque(false);
		courseScroll.setBorder(new RoundBorder(0, Color.WHITE, 1));

		// size & location
		courseScroll.setBounds(50, 80, 1060, 500);

		// add it
		jp.add(courseScroll);

		// [end]

		// [start], top side
		String[] courseTypeContent = { "--课程性质--", "必修", "选修", };
		JComboBox courseType = new JComboBox(courseTypeContent);
		String[] courseFullContent = { "--是否已满--", "未选满", "已选满", };
		JComboBox courseFull = new JComboBox(courseFullContent);
		JTextField searchContent = new JTextField("  请输入搜索关键词");
		String[] courseConflictContent = { "--是否冲突--", "不冲突", "冲突", };
		JComboBox courseConflict = new JComboBox(courseConflictContent);
		JLabel searchIt = new JLabel("搜索");
		JLabel vcampusLogo = getVcampus();

		// format
		searchContent.setBorder(new RoundBorder(0, MyType.blueColor, 2));
		searchContent.setAlignmentX((float) 0.5);
		searchIt.setBackground(MyType.blueColor);
		searchIt.setOpaque(true);
		searchIt.setFont(new Font("微软雅黑", Font.BOLD, 15));
		searchIt.setForeground(Color.WHITE);
		searchIt.setHorizontalAlignment(JLabel.CENTER);

		// size & location
		vcampusLogo.setBounds(20, 5, 180, 65);
		courseType.setBounds(240, 35, 115, 30);
		courseFull.setBounds(380, 35, 115, 30);
		courseConflict.setBounds(520, 35, 115, 30);
		searchContent.setBounds(650, 35, 430, 30);
		searchIt.setBounds(1080, 35, 70, 30);

		// function
		searchIt.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<Course> courseArray = passdata.search_course(
						(searchContent.getText().length() == 0 || searchContent.getText().contains("  请输入搜索关键词"))
								? "null"
								: searchContent.getText(),
						person.account_number, courseType.getSelectedIndex(), courseFull.getSelectedIndex(),
						courseConflict.getSelectedIndex());
				Course[] courseListNew = courseArray.toArray(new Course[courseArray.size()]);
//				Course[] courseListNew = {
//						new Course("计算机组成原理专题实践（双语、研讨）", "090001", "213193904", 1, 6, 8, 0, 0, 0, 2, 40, 10, "j4-402"),
//						new Course("数据结构（双语）", "090002", "213193904", 2, 3, 4, 4, 3, 4, 1, 40, 40, "j2-502"),
//						new Course("数据库原理及应用（全英文）", "090003", "213193904", 3, 8, 9, 5, 8, 9, 1, 100, 96, "j4-102"), };
				// TODO: 搜索
				courseScroll.setViewportView(new CourseScrollContent(courseListNew, person, timetable, courseConflict.getSelectedIndex())); // 刷新列表

			}

			@Override
			public void mousePressed(MouseEvent e) {
				searchContent.setBorder(new RoundBorder(0, MyType.purpleColor, 2));
				searchIt.setBackground(MyType.purpleColor);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				searchContent.setBorder(new RoundBorder(0, MyType.blueColor, 2));
				searchIt.setBackground(MyType.blueColor);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

		// add them
		jp.add(vcampusLogo);
		jp.add(courseType);
		jp.add(courseFull);
		jp.add(courseConflict);
		jp.add(searchContent);
		jp.add(searchIt);
		// [end]

		// [start], exit
		JLabel exit = new JLabel(new ImageIcon(MyType.iconPath + "exitBlue.png"));
		exit.setBounds(1140, 483, 40, 80);
		exit.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jf.tabbedPane.remove(jf.tabbedPane.getSelectedIndex());
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				exit.setIcon(new ImageIcon(MyType.iconPath + "exitPurple.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exit.setIcon(new ImageIcon(MyType.iconPath + "exitBlue.png"));
			}
		});
		jp.add(exit);
		// [end]

		jf.addFunctionPanel(jp, "选课");
	}

	/**
	 * 教师教务系统主界面
	 */
	public void listenerTeacherJwc() {
		// panels
		FunctionPanel jp = new FunctionPanel(jf, "教务系统");

		// [start], title & exit
		JLabel title = new JLabel("教务系统 - 课表");
		title.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		title.setBounds(20, 10, 210, 30);

		JLabel exit = new JLabel(new ImageIcon(MyType.iconPath + "exitBlue.png"));
		exit.setBounds(1140, 483, 40, 80);
		exit.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jf.tabbedPane.remove(jf.tabbedPane.getSelectedIndex());
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				exit.setIcon(new ImageIcon(MyType.iconPath + "exitPurple.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exit.setIcon(new ImageIcon(MyType.iconPath + "exitBlue.png"));
			}
		});
		// [end]

		// [start], timetable
		ArrayList<Course> classesArray = passdata.all_course(person.account_number);
		Course[] classes = classesArray.toArray(new Course[classesArray.size()]);
		// 接口，老师开的课程也可以这样检索到
//		Course[] classes = {
//				new Course("计算机组成原理专题实践（双语、研讨）", "090001", "213193904", 1, 6, 8, 0, 0, 0, 2, 40, 10, "j4-402"),
//				new Course("数据结构（双语）", "090002", "213193904", 2, 3, 4, 4, 3, 4, 1, 40, 40, "j2-502"),
//				new Course("数据库原理及应用（全英文）", "090003", "213193904", 3, 8, 9, 5, 8, 9, 1, 100, 96, "j4-102"), }; // 周一周三下午67节
		Timetable timetable = new Timetable(classes, ClassBlock.TEACHER, person);

		JLabel refresh = new JLabel("刷新", new ImageIcon(MyType.iconPath + "refresh.png"), JLabel.CENTER);
		refresh.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<Course> classesArray = passdata.all_course(person.account_number);
				Course[] classes = classesArray.toArray(new Course[classesArray.size()]);
				timetable.setClasses(classes);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				refresh.setForeground(MyType.focusColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				refresh.setForeground(Color.BLACK);
			}
		});
		refresh.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		refresh.setBounds(1035, 21, 80, 20);
		// [end]

		// [start], add classes
		JLabel addClass = new JLabel(new ImageIcon(MyType.iconPath + "addCourseBlue.png"));
		addClass.setBounds(1140, 400, 40, 80);
		addClass.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listenerTeacherAddClass(timetable);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				addClass.setIcon(new ImageIcon(MyType.iconPath + "addCoursePurple.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				addClass.setIcon(new ImageIcon(MyType.iconPath + "addCourseBlue.png"));
			}
		});

		// [end]

		jp.add(title);
		jp.add(exit);
		jp.add(addClass);
		jp.add(timetable);
		jp.add(refresh);

		jf.addFunctionPanel(jp, "教务系统");
	}

	/**
	 * 教师添加课程界面
	 * 
	 * @param timetable 指向教师课表的引用，用来同步更新教师课表
	 */
	public void listenerTeacherAddClass(Timetable timetable) {
		// [start], frame & panel
		JFrame frame = new JFrame();
		frame.setTitle("开课");
		frame.setIconImage(vlogoIcon.getImage());
		frame.setBounds(650 - 300, 350 - 200, 600, 400);

		JPanel panel = new JPanel(null);
		panel.setBounds(0, 0, 600, 400);
		// [end]

		// [start], elements
		JLabel bianhao = new JLabel("课程编号：");
		JTextField id = new JTextField();
		JLabel mingcheng = new JLabel("课程名称：");
		JTextField name = new JTextField();

		JLabel rongliang = new JLabel("课容量：");
		Integer[] capacityContent = { 10, 15, 20, 25, 30, 35, 40, 45, 50, 60, 80, 100, 120, 150, 200, 250, 300 };
		JComboBox capacity = new JComboBox(capacityContent);
		JLabel bixiu = new JLabel("必修/选修：");
		String[] compulsoryContent = { "必修", "选修" };
		JComboBox compulsory = new JComboBox(compulsoryContent);
		JLabel jiaoshi = new JLabel("教室：");
		String[] classroomContent = { "j1-101", "j1-102", "j1-103", "j1-104", "j1-105", "j2-101", "j2-102", "j2-103",
				"j2-104", "j2-105", "j3-101", "j3-102", "j3-103", "j3-104", "j3-105", };
		JComboBox classroom = new JComboBox(classroomContent);

		JLabel shijian1 = new JLabel("开课时间1：");
		String[] dayOfWeekContent1 = { "星期一", "星期二", "星期三", "星期四", "星期五" };
		JComboBox dayOfWeek1 = new JComboBox(dayOfWeekContent1);
		JLabel fromto1 = new JLabel("第                  节     至     第                  节");
		Integer[] sectionContent = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
		JComboBox sectionStart1 = new JComboBox(sectionContent);
		JComboBox sectionEnd1 = new JComboBox(sectionContent);

		JLabel shijian2 = new JLabel("开课时间2：");
		String[] dayOfWeekContent2 = { "不填", "星期一", "星期二", "星期三", "星期四", "星期五" };
		JComboBox dayOfWeek2 = new JComboBox(dayOfWeekContent2);
		JLabel fromto2 = new JLabel("第                  节     至     第                  节");
		JComboBox sectionStart2 = new JComboBox(sectionContent);
		JComboBox sectionEnd2 = new JComboBox(sectionContent);
		JButton addIt = new JButton("添加课程");
		// [end]

		// [start], size & location
		int basex = 40, basey = 50, spacex = 120, spacey = 60, widthx = 100, heighty = 25, offset = 20;
		bianhao.setBounds(basex, basey, widthx, heighty);
		id.setBounds(basex + 80, basey, widthx + offset, heighty);
		mingcheng.setBounds(basex + 250, basey, widthx, heighty);
		name.setBounds(basex + 330, basey, 2 * widthx - offset, heighty);

		rongliang.setBounds(basex, basey + spacey, widthx, heighty);
		capacity.setBounds(basex + 80, basey + spacey, 70, heighty);
		bixiu.setBounds(basex + 180, basey + spacey, widthx, heighty);
		compulsory.setBounds(basex + 260, basey + spacey, 70, heighty);
		jiaoshi.setBounds(basex + 360, basey + spacey, widthx, heighty);
		classroom.setBounds(basex + 410, basey + spacey, 70, heighty);

		shijian1.setBounds(basex, basey + 10 + 2 * spacey, widthx, heighty);
		dayOfWeek1.setBounds(basex + spacex - offset, basey + 10 + 2 * spacey, widthx, heighty);
		fromto1.setBounds(basex + 2 * spacex + 10, basey + 10 + 2 * spacey, 5 * widthx, heighty);
		sectionStart1.setBounds(basex + 2 * spacex + offset + 10, basey + 10 + 2 * spacey, widthx - 2 * offset,
				heighty);
		sectionEnd1.setBounds(basex + 3 * spacex + 3 * offset, basey + 10 + 2 * spacey, widthx - 2 * offset, heighty);

		shijian2.setBounds(basex, basey + 20 + 3 * spacey, widthx, heighty);
		dayOfWeek2.setBounds(basex + spacex - offset, basey + 20 + 3 * spacey, widthx, heighty);
		fromto2.setBounds(basex + 2 * spacex + 10, basey + 20 + 3 * spacey, 5 * widthx, heighty);
		sectionStart2.setBounds(basex + 2 * spacex + offset + 10, basey + 20 + 3 * spacey, widthx - 2 * offset,
				heighty);
		sectionEnd2.setBounds(basex + 3 * spacex + 3 * offset, basey + 20 + 3 * spacey, widthx - 2 * offset, heighty);

		addIt.setBounds(basex + 3 * spacex, basey + 20 + 4 * spacey, 90, heighty + 3);
		// [end]

		// [start], function
		addIt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (name.getText().length() != 0 && id.getText().length() != 0) {
					if (sectionStart1.getSelectedIndex() > sectionEnd1.getSelectedIndex()
							|| sectionStart2.getSelectedIndex() > sectionEnd2.getSelectedIndex()) {
						JOptionPane.showMessageDialog(null, "错误，开始时间晚于结束时间！");
						return;
					}
					passdata.add_course(new Course(name.getText(), id.getText(), person.account_number,
							dayOfWeek1.getSelectedIndex() + 1, sectionStart1.getSelectedIndex() + 1,
							sectionEnd1.getSelectedIndex() + 1, dayOfWeek2.getSelectedIndex(),
							sectionStart2.getSelectedIndex() + 1, sectionEnd2.getSelectedIndex() + 1,
							compulsory.getSelectedIndex() + 1, Integer.parseInt(capacity.getSelectedItem().toString()),
							0, classroom.getSelectedItem().toString()));
					JOptionPane.showMessageDialog(null, "添加成功！");
					frame.dispose();
				}
			}
		});
		// [end]

		// [start], add elements
		panel.add(bianhao);
		panel.add(id);
		panel.add(mingcheng);
		panel.add(name);
		panel.add(rongliang);
		panel.add(capacity);
		panel.add(bixiu);
		panel.add(compulsory);
		panel.add(jiaoshi);
		panel.add(classroom);

		panel.add(shijian1);
		panel.add(dayOfWeek1);
		panel.add(fromto1);
		panel.add(sectionStart1);
		panel.add(sectionEnd1);
		panel.add(shijian2);
		panel.add(dayOfWeek2);
		panel.add(fromto2);
		panel.add(sectionStart2);
		panel.add(sectionEnd2);
		panel.add(addIt);
		// [end]

		// [start], shading
		JLabel shading1 = new JLabel();
		shading1.setBackground(new Color(219, 244, 255));
		shading1.setOpaque(true);
		JLabel shading2 = new JLabel();
		shading2.setBackground(new Color(219, 244, 255));
		shading2.setOpaque(true);
		shading1.setBounds(125, 168, 425, 50);
		shading2.setBounds(125, 237, 425, 50);

		JPanel temp = new JPanel(null);
		temp.setBounds(0, 0, 600, 400);
		temp.add(shading1);
		temp.add(shading2);
		temp.setBackground(Color.WHITE);
		temp.setOpaque(true);

		JLayeredPane layer = new JLayeredPane();
		layer.setBounds(0, 0, 600, 400);
		layer.add(temp, JLayeredPane.DEFAULT_LAYER);
		panel.setOpaque(false);
		layer.add(panel, JLayeredPane.MODAL_LAYER);
		// [end]

		frame.add(layer);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	// [end]
}
