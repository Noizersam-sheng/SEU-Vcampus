package cn.seu.ui.support.jwc;

/**
 * @author 牟倪
 * @version 1.2
 * 选课界面中，用来放置课程信息的Panel
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.seu.domain.courses.Course;
import cn.seu.domain.personnel.PassData;
import cn.seu.domain.personnel.Person;
import cn.seu.ui.support.MyType;
import cn.seu.ui.support.RoundBorder;

public class CourseScrollContent extends JPanel {
	static int X = 350, Y = 140;

	PassData passdata = new PassData();

	Course[] courseList = null;
	int state;
	Person person = null;
	Timetable timetable = null;
	int expect_confilct = 0;

	public CourseScrollContent(Course[] courseListPara, Person personPara, Timetable timetablePara, int confilctPara) {
		super(null);
		this.courseList = courseListPara;
		this.person = personPara;
		this.timetable = timetablePara;
		this.expect_confilct = confilctPara;
		this.setPreferredSize(new Dimension(1040, (courseList.length + 2) / 3 * Y + 50));

		render();
	}

	/**
	 * 这是一个渲染的函数
	 */
	public void render() {
		this.removeAll(); // 首先移除所有
		this.repaint(); // 然后刷新面板
		this.setBackground(Color.WHITE);

		// let's render
		int real_count = 0;
		for (int i = 0; i < courseList.length; ++i) {
			int i0 = i;
			JPanel info = new JPanel(null);
			info.setBounds((real_count % 3) * X, (real_count / 3) * Y, X - 10, Y - 10);
			info.setBackground(Color.WHITE);
			info.setBorder(new RoundBorder(20, Color.LIGHT_GRAY, 1));

			// [start], elements
			// 课程名称
			JLabel name = new JLabel(courseList[i].getName());
			name.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			name.setBounds(10, 5, 330, 30);
			info.add(name);

			// 课程时间
			String[] dayweek = { "一", "二", "三", "四", "五", "六", "日" };
			JLabel time1 = new JLabel("星期" + dayweek[courseList[i].getDate() - 1] + " " + courseList[i].getStart_t()
					+ "-" + courseList[i].getEnd_t() + "节");
			time1.setBounds(10, 45, 90, 30);
			info.add(time1);
			if (courseList[i].getDate_2() != 0) {
				JLabel time2 = new JLabel("星期" + dayweek[courseList[i].getDate_2() - 1] + " "
						+ courseList[i].getStart_t2() + "-" + courseList[i].getEnd_t2() + "节");
				time2.setBounds(108, 45, 90, 30);
				info.add(time2);
			}

			// 老师
			Object[] infoTemp = passdata.status_information(passdata.search_s("id", courseList[i].getTeacher()));
			JLabel teacher = new JLabel(infoTemp[2].toString());
			teacher.setBounds(220, 45, 100, 30);
			info.add(teacher);

			// 教室
			JLabel classroom = new JLabel(courseList[i].getClassroom());
			classroom.setBounds(275, 45, 50, 30);
			info.add(classroom);

			if (courseList[i].getCount() == courseList[i].getMax_num()) { // 已满
				JLabel full = new JLabel("已满");
				full.setFont(new Font("微软雅黑", Font.BOLD, 13));
				full.setHorizontalAlignment(JLabel.CENTER);
				full.setForeground(Color.WHITE);
				full.setBackground(MyType.redColor);
				full.setOpaque(true);
				full.setBounds(15, 95, 50, 23);
				info.add(full);
			}

			// 是否已选
			boolean hasChoose = false;
			for (int j = 0; j < timetable.getClasses().length; ++j)
				if (courseList[i0].getCourseID().equals(timetable.getClasses()[j].getCourseID())) {
					hasChoose = true;
					break;
				}
			if (hasChoose) { // 已选
				JLabel choosen = new JLabel("已选");
				choosen.setFont(new Font("微软雅黑", Font.BOLD, 13));
				choosen.setHorizontalAlignment(JLabel.CENTER);
				choosen.setForeground(Color.WHITE);
				choosen.setBackground(new Color(81, 200, 230));
				choosen.setOpaque(true);
				choosen.setBounds(145, 95, 50, 23);
				info.add(choosen);
			}

			// 是否冲突
			boolean hasConflict = false;
			for (int j = 0; j < timetable.getClasses().length; ++j) {
				if (timetable.getClasses()[j].getDate() == courseList[i0].getDate()
						&& ((timetable.getClasses()[j].getStart_t() <= courseList[i0].getStart_t()
								&& timetable.getClasses()[j].getEnd_t() >= courseList[i0].getStart_t())
								|| (timetable.getClasses()[j].getStart_t() <= courseList[i0].getEnd_t()
										&& timetable.getClasses()[j].getEnd_t() >= courseList[i0].getEnd_t()))) {
					hasConflict = true;
					break;
				}
				if (timetable.getClasses()[j].getDate_2() == courseList[i0].getDate()
						&& ((timetable.getClasses()[j].getStart_t2() <= courseList[i0].getStart_t()
								&& timetable.getClasses()[j].getEnd_t2() >= courseList[i0].getStart_t())
								|| (timetable.getClasses()[j].getStart_t2() <= courseList[i0].getEnd_t()
										&& timetable.getClasses()[j].getEnd_t2() >= courseList[i0].getEnd_t()))) {
					hasConflict = true;
					break;
				}
				if (timetable.getClasses()[j].getDate() == courseList[i0].getDate_2()
						&& ((timetable.getClasses()[j].getStart_t() <= courseList[i0].getStart_t2()
								&& timetable.getClasses()[j].getEnd_t() >= courseList[i0].getStart_t2())
								|| (timetable.getClasses()[j].getStart_t() <= courseList[i0].getEnd_t2()
										&& timetable.getClasses()[j].getEnd_t() >= courseList[i0].getEnd_t2()))) {
					hasConflict = true;
					break;
				}
				if (timetable.getClasses()[j].getDate_2() == courseList[i0].getDate_2()
						&& ((timetable.getClasses()[j].getStart_t2() <= courseList[i0].getStart_t2()
								&& timetable.getClasses()[j].getEnd_t2() >= courseList[i0].getStart_t2())
								|| (timetable.getClasses()[j].getStart_t2() <= courseList[i0].getEnd_t2()
										&& timetable.getClasses()[j].getEnd_t2() >= courseList[i0].getEnd_t2()))) {
					hasConflict = true;
					break;
				}
			}
			if (this.expect_confilct == 1 && hasConflict) { // 希望展示未冲突的课程
				continue;
			} else if (this.expect_confilct == 2 && (!hasConflict)) { // 希望展示冲突的课程
				continue;
			}
			if (hasConflict && (!hasChoose)) { // 未选且冲突
				JLabel conflict = new JLabel("冲突");
				conflict.setFont(new Font("微软雅黑", Font.BOLD, 13));
				conflict.setHorizontalAlignment(JLabel.CENTER);
				conflict.setForeground(Color.WHITE);
				conflict.setBackground(MyType.redColor);
				conflict.setOpaque(true);
				conflict.setBounds(80, 95, 50, 23);
				info.add(conflict);
			}


			JLabel compulsory = new JLabel(courseList[i].getStatus() == 1 ? "必修" : "选修");
			compulsory.setBounds(210, 90, 60, 30);
			info.add(compulsory);

			JLabel choose = new JLabel("已选 " + courseList[i].getCount() + "/" + courseList[i].getMax_num());
			choose.setBounds(255, 90, 100, 30);
			info.add(choose);
			// [end]

			// function
			info.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// [start], chosen
					boolean hasChooseTemp = false;
					for (int j = 0; j < timetable.getClasses().length; ++j)
						if (courseList[i0].getCourseID().equals(timetable.getClasses()[j].getCourseID())) {
							hasChooseTemp = true;
							break;
						}
					// [end]
					// [start], conflict
					boolean hasConflict = false;
					for (int j = 0; j < timetable.getClasses().length; ++j) {
						if (timetable.getClasses()[j].getDate() == courseList[i0].getDate()
								&& ((timetable.getClasses()[j].getStart_t() <= courseList[i0].getStart_t()
										&& timetable.getClasses()[j].getEnd_t() >= courseList[i0].getStart_t())
										|| (timetable.getClasses()[j].getStart_t() <= courseList[i0].getEnd_t()
												&& timetable.getClasses()[j].getEnd_t() >= courseList[i0]
														.getEnd_t()))) {
							hasConflict = true;
							break;
						}
						if (timetable.getClasses()[j].getDate_2() == courseList[i0].getDate()
								&& ((timetable.getClasses()[j].getStart_t2() <= courseList[i0].getStart_t()
										&& timetable.getClasses()[j].getEnd_t2() >= courseList[i0].getStart_t())
										|| (timetable.getClasses()[j].getStart_t2() <= courseList[i0].getEnd_t()
												&& timetable.getClasses()[j].getEnd_t2() >= courseList[i0]
														.getEnd_t()))) {
							hasConflict = true;
							break;
						}
						if (timetable.getClasses()[j].getDate() == courseList[i0].getDate_2()
								&& ((timetable.getClasses()[j].getStart_t() <= courseList[i0].getStart_t2()
										&& timetable.getClasses()[j].getEnd_t() >= courseList[i0].getStart_t2())
										|| (timetable.getClasses()[j].getStart_t() <= courseList[i0].getEnd_t2()
												&& timetable.getClasses()[j].getEnd_t() >= courseList[i0]
														.getEnd_t2()))) {
							hasConflict = true;
							break;
						}
						if (timetable.getClasses()[j].getDate_2() == courseList[i0].getDate_2()
								&& ((timetable.getClasses()[j].getStart_t2() <= courseList[i0].getStart_t2()
										&& timetable.getClasses()[j].getEnd_t2() >= courseList[i0].getStart_t2())
										|| (timetable.getClasses()[j].getStart_t2() <= courseList[i0].getEnd_t2()
												&& timetable.getClasses()[j].getEnd_t2() >= courseList[i0]
														.getEnd_t2()))) {
							hasConflict = true;
							break;
						}
					}
					if (hasConflict) { // 冲突
						JLabel conflict = new JLabel("冲突");
						conflict.setFont(new Font("微软雅黑", Font.BOLD, 13));
						conflict.setHorizontalAlignment(JLabel.CENTER);
						conflict.setForeground(Color.WHITE);
						conflict.setBackground(MyType.redColor);
						conflict.setOpaque(true);
						conflict.setBounds(80, 95, 50, 23);
						info.add(conflict);
					}
					// [end]
					if (courseList[i0].getCount() == courseList[i0].getMax_num() || hasConflict || hasChooseTemp)
						return;
					// 我们假设课程只能选不能退
					if (JOptionPane.showConfirmDialog(null, "选择该课程？") == 0) {
						passdata.choose_course(courseList[i0].getCourseID(), person.account_number);
						// 选择该课程
						// 改界面
						// 1. 改后面的课程表
						timetable.addClassToTimetable(courseList[i0]);
						JOptionPane.showMessageDialog(null, "选课成功！");
						// 2. 刷新选课界面……
						render();
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					info.setBorder(new RoundBorder(20, MyType.purpleColor, 2));
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					info.setBorder(new RoundBorder(20, MyType.blueColor, 1));
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					info.setBorder(new RoundBorder(20, MyType.blueColor, 1));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					info.setBorder(new RoundBorder(20, Color.LIGHT_GRAY, 1));
				}

			});

			this.add(info);
			++real_count;
		}

	}

	public static void JlabelSetText(JLabel jLabel, String longString) {
		// throws InterruptedException
		StringBuilder builder = new StringBuilder("<html>");
		char[] chars = longString.toCharArray();
		FontMetrics fontMetrics = jLabel.getFontMetrics(jLabel.getFont());
		int start = 0;
		int len = 0;
		while (start + len < longString.length()) {
			while (true) {
				len++;
				if (start + len > longString.length())
					break;
				if (fontMetrics.charsWidth(chars, start, len) > jLabel.getWidth()) {
					break;
				}
			}
			builder.append(chars, start, len - 1).append("<br/>");
			start = start + len - 1;
			len = 0;
		}
		builder.append(chars, start, longString.length() - start);
		builder.append("</html>");
		jLabel.setText(builder.toString());
	}
}
