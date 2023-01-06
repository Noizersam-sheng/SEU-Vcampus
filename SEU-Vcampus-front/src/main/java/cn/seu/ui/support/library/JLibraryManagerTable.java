package cn.seu.ui.support.library;

/**
 * @author 牟倪
 * @version 2.1
 * 图书管理员界面中渲染馆藏信息的表格，需要与LibraryManagerButtonRender配合使用
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

import cn.seu.ui.support.JCButton;
import cn.seu.ui.support.MyType;

public class JLibraryManagerTable extends JTable {

	static Object[] head = { "索书号", "书名", "馆藏数量", "可借数量", "总借阅量" };
	public static int current = -1; // 我也不知道这个地方写static会不会出现玄学事故

	Object[][] content = null;
	JCButton fundam = null;

	public JLibraryManagerTable(Object[][] content, JCButton fundam) {
		super(new DefaultTableModel(content, head));

		// pointer
		this.content = content;
		this.fundam = fundam;

		// appearance
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
			this.setDefaultRenderer(this.getColumnClass(i), new RoutineTableManager());
		}

		// add the buttons
		DefaultTableModel myModel = (DefaultTableModel) this.getModel();
		myModel.addColumn("馆藏");
		myModel.addColumn("馆藏");
		this.getColumnModel().getColumn(5).setCellEditor(new LibraryManagerButtonRender("减少", fundam)); // 设置编辑器，不能注释掉
		this.getColumnModel().getColumn(5).setCellRenderer(new LibraryManagerButtonRender("减少", fundam));
		this.getColumnModel().getColumn(6).setCellEditor(new LibraryManagerButtonRender("增加", fundam)); // 设置编辑器，不能注释掉
		this.getColumnModel().getColumn(6).setCellRenderer(new LibraryManagerButtonRender("增加", fundam));

		// reset column widths
		this.getColumnModel().getColumn(0).setPreferredWidth(52);
		this.getColumnModel().getColumn(1).setPreferredWidth(130);
		this.getColumnModel().getColumn(2).setPreferredWidth(64);
		this.getColumnModel().getColumn(3).setPreferredWidth(64);
		this.getColumnModel().getColumn(4).setPreferredWidth(64);
		this.getColumnModel().getColumn(5).setPreferredWidth(63);
		this.getColumnModel().getColumn(6).setPreferredWidth(63);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex >= 5)
			return true;
		return false;
	}

	public Object[][] getContent() {
		return this.content;
	}

	public void updateDisplay(Object[][] newContent) {
		this.setModel(new DefaultTableModel(newContent, head));
		this.content = newContent;

		DefaultTableModel myModel = (DefaultTableModel) this.getModel();
		myModel.addColumn("馆藏");
		myModel.addColumn("馆藏");
		this.getColumnModel().getColumn(5).setCellEditor(new LibraryManagerButtonRender("减少", fundam)); // 设置编辑器，不能注释掉
		this.getColumnModel().getColumn(5).setCellRenderer(new LibraryManagerButtonRender("减少", fundam));
		this.getColumnModel().getColumn(6).setCellEditor(new LibraryManagerButtonRender("增加", fundam)); // 设置编辑器，不能注释掉
		this.getColumnModel().getColumn(6).setCellRenderer(new LibraryManagerButtonRender("增加", fundam));

		// reset column widths
		this.getColumnModel().getColumn(0).setPreferredWidth(52);
		this.getColumnModel().getColumn(1).setPreferredWidth(130);
		this.getColumnModel().getColumn(2).setPreferredWidth(64);
		this.getColumnModel().getColumn(3).setPreferredWidth(64);
		this.getColumnModel().getColumn(4).setPreferredWidth(64);
		this.getColumnModel().getColumn(5).setPreferredWidth(63);
		this.getColumnModel().getColumn(6).setPreferredWidth(63);
	}

	public static void main(String args[]) {
		Object[][] testdata = { { "001", "神奇的书", 3, 1, 100 }, { "001", "神奇的书", 3, 1, 100 },
				{ "001", "神奇的书", 3, 1, 100 }, { "001", "神奇的书", 3, 1, 100 }, { "001", "神奇的书", 3, 1, 100 },
				{ "001", "神奇的书", 3, 1, 100 }, };
		JLibraryManagerTable t = new JLibraryManagerTable(testdata, null);
		t.setPreferredScrollableViewportSize(new Dimension(500, 400));
		JScrollPane scroll = new JScrollPane(t);
		JFrame f = new JFrame();
		f.setBounds(650 - 250, 350 - 200, 500, 400);
		f.add(scroll);
		f.setVisible(true);
	}
}

class RoutineTableManager extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (row % 2 == 0) {
			// 设置偶数行的背景颜色
			setBackground(Color.WHITE);
		} else {
			// 基数行的背景颜色
			setBackground(new Color(240, 240, 240));
		}

		if (row == JLibraryManagerTable.current) {
			setBackground(new Color(215, 225, 255));
		}

		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
	// 来自 https://blog.csdn.net/sdlcgxcqx/article/details/4539107
}
