package cn.seu.ui.support.jwc;

/**
 * @author 牟倪
 * @version 1.2
 * 基于JLayeredPane的课表，放置于教务系统首页，需要与ClassBlock配合使用
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import cn.seu.domain.courses.Course;
import cn.seu.domain.personnel.Person;
import cn.seu.ui.support.MyType;

public class Timetable extends JLayeredPane {

	static ImageIcon background = new ImageIcon(MyType.figurePath + "timetable.png");
	static int X = 1050, Y = 555;
	static int offsetX = 70, offsetY = 30, spaceX = 196, spaceY = 40;

	Course[] classes = null;
	int state;
	public Person person;

	public Timetable(Course[] classesPara, int statePara, Person p) {
		super();
		this.classes = classesPara;
		this.state = statePara;
		this.person = p;
		this.setBounds(50, 47, X, Y);

		render();
	}

	public Course[] getClasses() {
		return classes;
	}

	public void render() {
		this.removeAll();
		this.repaint();

		// [start], layered pane
		JLabel label = new JLabel(background);
		JPanel bgpanel = new JPanel();
		bgpanel.setBounds(0, -5, X, Y); // -5是玄学offset
		bgpanel.add(label);
		this.add(bgpanel, JLayeredPane.DEFAULT_LAYER);
		// [end]

		// [start], content panel
		JPanel content = new JPanel(null);
		content.setBounds(0, 0, X, Y);

		for (int i = 0; i < classes.length; ++i) {
			ClassBlock classBlock = new ClassBlock(classes[i], this, state);
			classBlock.setBounds(offsetX + (classes[i].getDate() - 1) * spaceX,
					offsetY + (classes[i].getStart_t() - 1) * spaceY, spaceX,
					spaceY * (classes[i].getEnd_t() - classes[i].getStart_t() + 1));
			content.add(classBlock);
			if (classes[i].getDate_2() != 0) {
				Course temp = new Course(classes[i].getName(), classes[i].getCourseID(), classes[i].getTeacher(),
						classes[i].getDate_2(), classes[i].getStart_t2(), classes[i].getEnd_t2(), 0, 0, 0,
						classes[i].getStatus(), classes[i].getMax_num(), classes[i].getCount(),
						classes[i].getClassroom());
				ClassBlock classBlock1 = new ClassBlock(temp, this, state);
				classBlock1.setBounds(offsetX + (temp.getDate() - 1) * spaceX,
						offsetY + (temp.getStart_t() - 1) * spaceY, spaceX,
						spaceY * (temp.getEnd_t() - temp.getStart_t() + 1));
				content.add(classBlock1);
			}
		}
		// [end]

		content.setBounds(0, 0, X, Y);
		content.setOpaque(false);
		this.add(content, JLayeredPane.MODAL_LAYER);
	}

	public void setClasses(Course[] newClasses) {
		this.classes = newClasses.clone();
		render();
	}

	public void addClassToTimetable(Course course) {
		Course[] newClasses = new Course[classes.length + 1];
		for (int i = 0; i < classes.length; ++i)
			newClasses[i] = classes[i];
		newClasses[classes.length] = course;
		this.setClasses(newClasses);
	}

	public void removeClassFromTimetable(Course course) { // 只需要一个课程号，我就把所有课程都删除
		List<Course> arrList = Arrays.asList(this.classes);
		List<Course> classList = new ArrayList(arrList);
		for (int i = 0; i < classList.size(); i++) {
			if (classList.get(i).getCourseID().equals(course.getCourseID())) {
				classList.remove(i--);
			}
		}
		Course[] newClasses = classList.toArray(new Course[classList.size()]);
		this.setClasses(newClasses);
	}
}
