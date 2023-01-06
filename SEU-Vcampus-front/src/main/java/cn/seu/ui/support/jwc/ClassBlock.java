package cn.seu.ui.support.jwc;

/**
 * @author 牟倪
 * @version 2.3
 * 课程表上的课程块，添加炫酷鼠标悬停变色特效
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cn.seu.ui.support.RoundedRectangleBorder;
import cn.seu.ui.support.shop.CommoScrollContent;
import cn.seu.domain.courses.Course;
import cn.seu.domain.personnel.PassData;
import cn.seu.ui.support.MyType;


public class ClassBlock extends JPanel {
	public static final int STUDENT = 0, TEACHER = 1;

	ImageIcon vlogoIcon = new ImageIcon(MyType.iconPath + "vlogo.png");

	// PassData
	PassData passdata = new PassData();
	PassData passData = new PassData();

	static Color[] lightColor = { new Color(255, 255, 200), new Color(220, 220, 255), new Color(255, 220, 220),
			new Color(220, 255, 220), new Color(200, 235, 255), new Color(255, 235, 205), new Color(255, 220, 255),
			new Color(235, 255, 190), };

	private Course course;
	private int state;
	private Timetable timetable;

	public ClassBlock(Course c, Timetable t, int statePara) {
		super(null);
		this.course = c;
		this.state = statePara;
		this.timetable = t;
		this.setOpaque(false);
		this.setBorder(new RoundedRectangleBorder(lightColor[Math.abs(c.getName().hashCode() % lightColor.length)]));

		// [start], elements
		JLabel className = new JLabel(course.getName());
		System.out.println(course.getName());
		className.setBounds(10, 8, 180, 20);
		className.setFont(new Font("微软雅黑",Font.PLAIN,14));
		if (CommoScrollContent.JlabelSetText(className, course.getName()))
			className.setBounds(10, 8, 180, 40);
		this.add(className);

		Object[] infoTemp = passdata.status_information(passdata.search_s("id", course.getTeacher()));
		JLabel teacherName = new JLabel(infoTemp[2].toString());
		teacherName.setBounds(8, 47 + 40 * (c.getEnd_t() - c.getStart_t() - 1), 90, 27);
		this.add(teacherName);

		JLabel classroom = new JLabel(course.getClassroom());
		classroom.setBounds(98, 47 + 40 * (c.getEnd_t() - c.getStart_t() - 1), 90, 27);
		this.add(classroom);
		// [end]

		// function
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (state == STUDENT) {
					Object[] options = new Object[] { "查看班级名单", " 查看成绩 ", " 退课 ", "取消" };
					int optionSelected = JOptionPane.showOptionDialog(null, "请选择要执行的操作", "课程选项",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);

					if (optionSelected == 0) { // 查看班级名单
						// [start], frame & panel
						JPanel panel = new JPanel(null);
						panel.setBackground(Color.WHITE);
						panel.setOpaque(true);

						JFrame frame = new JFrame("课程名单");
						frame.setBounds(650 - 200, 350 - 250, 400, 500);
						frame.setIconImage(vlogoIcon.getImage());

						// [start], table & scroll
						Object[][] studentListContent = passdata.show_list(course.getCourseID());
						// 接口，得到班级名单
//						Object[][] studentListContent = { { "213193904", "牟倪" }, { "213192963", "刘尊颐" },
//								{ "213191246", "赖泽升" }, { "213190135", "金顾睿" }, { "213190136", "顾深远" },};
						Object[] studentListHead = { "一卡通号", "姓名" };
						JTable studentList = new JTable(studentListContent, studentListHead);
						studentList.setSelectionBackground(MyType.focusColor);
						studentList.setSelectionForeground(Color.WHITE);
						studentList.setPreferredScrollableViewportSize(new Dimension(386, 400));

						JScrollPane scroll = new JScrollPane(studentList);
						scroll.setBounds(0, 0, 386, 400);
						// [end]

						// [start], exit
						JButton exit = new JButton("关闭");
						exit.setBounds(310, 415, 60, 25);
						exit.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								frame.dispose();
							}
						});
						// [end]

						// add elements
						panel.add(scroll);
						panel.add(exit);

						frame.add(panel);
						frame.setVisible(true);

					} else if (optionSelected == 1) { // 查看成绩
						// TODO: 接口，学生查看成绩
						JOptionPane.showMessageDialog(null,
								"你的成绩是" + passdata.search_score(timetable.person.account_number, course.getCourseID())+"！");
					} else if (optionSelected == 2) { // 退课
						String score_string = passdata.search_score(timetable.person.account_number, course.getCourseID());
						if(!score_string.equals("null")){
							JOptionPane.showMessageDialog(null, "该门课程已登记成绩（你的成绩为" + score_string + "），退课失败！");
						} else {
							// TODO: 接口，学生退课
							int quit_state = passdata.quit_course(timetable.person.account_number, course.getCourseID());
							if (quit_state == 1) {
								JOptionPane.showMessageDialog(null, "退课成功！");
								// 在界面把课程删除
								timetable.removeClassFromTimetable(c);
							} else {
								JOptionPane.showMessageDialog(null, "退课失败！");
							}
						}
					}
				} else if (state == TEACHER) {
					Object[] options = new Object[] { "查看班级名单", " 登记成绩 ", "删除课程", "取消" };
					int optionSelected = JOptionPane.showOptionDialog(null, "请选择想要执行的操作", "课程选项",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

					if (optionSelected == 0) { // 查看班级名单
						// [start], frame & panel
						JPanel panel = new JPanel(null);
						panel.setBackground(Color.WHITE);
						panel.setOpaque(true);

						JFrame frame = new JFrame("课程名单");
						frame.setBounds(650 - 200, 350 - 250, 400, 500);
						frame.setIconImage(vlogoIcon.getImage());

						// [start], table & scroll
						Object[][] studentListContent = passdata.show_list(course.getCourseID());
						// TODO: 接口，得到班级名单
//						Object[][] studentListContent = { { "213193904", "牟倪" }, { "213192963", "刘尊颐" },
//								{ "213191246", "赖泽升" }, { "213190135", "金顾睿" }, { "213190136", "顾深远" },};
						Object[] studentListHead = { "一卡通号", "姓名" };
						JTable studentList = new JTable(studentListContent, studentListHead);
						studentList.setSelectionBackground(MyType.focusColor);
						studentList.setSelectionForeground(Color.WHITE);
						studentList.setPreferredScrollableViewportSize(new Dimension(386, 400));

						JScrollPane scroll = new JScrollPane(studentList);
						scroll.setBounds(0, 0, 386, 400);
						// [end]

						// [start], exit
						JButton exit = new JButton("关闭");
						exit.setBounds(310, 415, 60, 25);
						exit.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								frame.dispose();
							}
						});
						// [end]

						// add elements
						panel.add(scroll);
						panel.add(exit);

						frame.add(panel);
						frame.setVisible(true);

					} else if (optionSelected == 1) { // 登记成绩
						// [start], frame & panel
						JPanel panel = new JPanel(null);
						panel.setBackground(Color.WHITE);
						panel.setOpaque(true);

						JFrame frame = new JFrame("课程名单");
						frame.setBounds(650 - 200, 350 - 250, 500, 500);
						frame.setIconImage(vlogoIcon.getImage());
						// [end]

						// [start], table & scroll
						Object[][] studentScoreTemp = passdata.show_list(course.getCourseID());
						Object[][] studentScore = new Object[studentScoreTemp.length][3];
						for (int i = 0; i < studentScoreTemp.length; ++i) {
							studentScore[i][0] = studentScoreTemp[i][0];
							studentScore[i][1] = studentScoreTemp[i][1];
							studentScore[i][2] = passdata.search_score(studentScoreTemp[i][0].toString(),
									course.getCourseID());
						}
//						Object[][] studentScore = { { "213193904", "牟倪", "" }, { "213192963", "刘尊颐", "" },
//								{ "213191246", "赖泽升", "100" }, { "213190135", "金顾睿", "" },
//								{ "213190136", "顾深远", "" }, };
						JEnterScoreTable enterScoreTable = new JEnterScoreTable(studentScore, c.getCourseID());
						enterScoreTable.setPreferredScrollableViewportSize(new Dimension(486, 400));

						JScrollPane scroll = new JScrollPane(enterScoreTable);
						scroll.setBounds(0, 0, 486, 400);

						// [start], exit
						JButton exit = new JButton("关闭");
						exit.setBounds(410, 415, 60, 25);
						exit.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								frame.dispose();
							}
						});
						// [end]

						// add elements
						panel.add(scroll);
						panel.add(exit);

						frame.add(panel);
						frame.setVisible(true);
					} else if (optionSelected == 2) { // 删除课程
						passdata.delete_course("course_id",c.getCourseID());
						// 接口，删除课程
						JOptionPane.showMessageDialog(null, "删除成功！");
						// 在界面把课程删除
						timetable.removeClassFromTimetable(c);
					}
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
				Color temp = lightColor[Math.abs(c.getName().hashCode() % lightColor.length)];
				double gainR = 0.9, gainG = gainR, gainB = gainR;
				if (temp.getRed() >= temp.getBlue() && temp.getBlue() == temp.getGreen()) // 红色一枝独秀
					gainR = 1;
				else if (temp.getGreen() >= temp.getBlue() && temp.getBlue() == temp.getRed()) // 绿色一枝独秀
					gainG = 1;
				else if (temp.getBlue() >= temp.getRed() && temp.getRed() == temp.getGreen()) // 蓝色一枝独秀
					gainB = 1;
				else if (temp.getRed() < temp.getGreen() && temp.getRed() < temp.getBlue()) { // 红色最惨
					gainB = 1;
					gainG = 1;
					gainR = 0.8;
				} else if (temp.getBlue() < temp.getGreen() && temp.getBlue() < temp.getRed()) { // 蓝色最惨
					gainR = 1;
					gainG = 1;
					gainB = 0.8;
				} else if (temp.getGreen() < temp.getBlue() && temp.getGreen() < temp.getRed()) { // 绿色最惨
					gainR = 1;
					gainB = 1;
					gainG = 0.8;
				}

				setBorder(new RoundedRectangleBorder(new Color((int) (temp.getRed() * gainR),
						(int) (temp.getGreen() * gainG), (int) (temp.getBlue() * gainB))));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBorder(new RoundedRectangleBorder(lightColor[Math.abs(c.getName().hashCode() % lightColor.length)]));
			}

		});

	}
}
