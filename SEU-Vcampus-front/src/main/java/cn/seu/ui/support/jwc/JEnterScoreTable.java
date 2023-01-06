package cn.seu.ui.support.jwc;

/**
 * @author 牟倪
 * @version 1.0
 * 用于显示/登记成绩的表格
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import cn.seu.ui.support.MyType;

public class JEnterScoreTable extends JTable {

	static Object[] head = { "一卡通号", "姓名", "成绩" };
	public static int current = -1; // 我也不知道这个地方写static会不会出现玄学事故

	Object[][] content = null;
	String courseID = "";

	public JEnterScoreTable(Object[][] content, String courseID) {
		super(new DefaultTableModel(content, head));

		// pointer
		this.content = content;
		this.courseID = courseID; // 课程号

		// [start], appearance
		this.setSelectionBackground(MyType.focusColor);
		this.setSelectionForeground(Color.WHITE);
		// 给表格添加鼠标移动的监听，鼠标移动刷新表格颜色
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				current = -1;
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				Point p = e.getPoint();
				current = rowAtPoint(p);
				for (int i = 0; i < content.length; ++i) {
					if (i == current) {
						setBackground(Color.BLACK); // 并不知道为什么要这么写
						break;
					}
				}
				setBackground(Color.WHITE);
			}
		});
		for (int i = 1; i < this.getColumnCount(); i++) {
			this.setDefaultRenderer(this.getColumnClass(i), new RoutineTableEnterScore());
		}
		// [end]

		// add the buttons
		DefaultTableModel myModel = (DefaultTableModel) this.getModel();
		myModel.addColumn("确定");
		this.getColumnModel().getColumn(3).setCellEditor(new EnterScoreButtonRender("确定", this)); // 设置编辑器，不能注释掉
		this.getColumnModel().getColumn(3).setCellRenderer(new EnterScoreButtonRender("确定", this));

	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex >= 2)
			return true;
		return false;
	}

	public Object[][] getContent() {
		return this.content;
	}

	public static void main(String args[]) {
		Object[][] testdata = { { "213193904", "牟倪", "" }, { "213192963", "刘尊颐", "" }, };
		JEnterScoreTable t = new JEnterScoreTable(testdata, "BS090792");
		t.setPreferredScrollableViewportSize(new Dimension(500, 400));
		JScrollPane scroll = new JScrollPane(t);
		JFrame f = new JFrame();
		f.setBounds(650 - 250, 350 - 200, 500, 400);
		f.add(scroll);
		f.setVisible(true);
	}
}

class RoutineTableEnterScore extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (row % 2 == 0) {
			// 设置偶数行的背景颜色
			setBackground(Color.WHITE);
		} else {
			// 基数行的背景颜色
			setBackground(new Color(240, 240, 240));
		}

		if (row == JEnterScoreTable.current) {
			setBackground(new Color(215, 225, 255));
		}

		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
	// 来自 https://blog.csdn.net/sdlcgxcqx/article/details/4539107
}
