package cn.seu.ui.support.library;

/**
 * @author 牟倪
 * @version 2.1
 * 渲染借阅信息的表格，需要与LibraryButtonRender配合使用
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import cn.seu.ui.support.JCButton;
import cn.seu.ui.support.MyType;

public class JBorrowTable extends JTable {
	// borrow是我借的书！！不是馆藏！！

	static Object[] head = { "索书号", "书名", "借阅日期", "应还日期" };
	public static int current = -1; // 我也不知道这个地方写static会不会出现玄学事故

	Object[][] content = null;
	JCButton fundam = null;

	public JBorrowTable(Object[][] content, JCButton fundam) {
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
			this.setDefaultRenderer(this.getColumnClass(i), new RoutineTableBorrow());
		}

		// add the buttons
		DefaultTableModel myModel = (DefaultTableModel) this.getModel();
		myModel.addColumn("续借");
		myModel.addColumn("还书");
		this.getColumnModel().getColumn(4).setCellEditor(new LibraryButtonRender("续借", fundam));// 设置编辑器
		this.getColumnModel().getColumn(4).setCellRenderer(new LibraryButtonRender("续借", fundam));
		this.getColumnModel().getColumn(5).setCellEditor(new LibraryButtonRender("还书", fundam));// 设置编辑器
		this.getColumnModel().getColumn(5).setCellRenderer(new LibraryButtonRender("还书", fundam));

		// reset column widths
		this.getColumnModel().getColumn(0).setPreferredWidth(60);
		this.getColumnModel().getColumn(1).setPreferredWidth(146);
		this.getColumnModel().getColumn(2).setPreferredWidth(82);
		this.getColumnModel().getColumn(3).setPreferredWidth(82);
		this.getColumnModel().getColumn(4).setPreferredWidth(65);
		this.getColumnModel().getColumn(5).setPreferredWidth(65);

	}

	public static Object[][] parseDateToString(Object[][] content) {
		Object[][] another = content.clone();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (Object[] i : another) {
			if (i[2] instanceof Date)
				i[2] = sdf.format(i[2]);
			if (i[3] instanceof Date)
				i[3] = sdf.format(i[3]);
		}
		return another;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex >= 4)
			return true;
		return false;
	}

	public Object[][] getContent() {
		return this.content;
	}

	public void updateDisplay(Object[][] newContent) {
		if (newContent.length != 0 && newContent[0][2] instanceof Date) {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (int i = 0; i < newContent.length; ++i) {
				newContent[i][3] = sdf.format(newContent[i][3]);
			}
		}
		this.setModel(new DefaultTableModel(newContent, head));
		this.content = newContent;

		// add the buttons
		DefaultTableModel myModel = (DefaultTableModel) this.getModel();
		myModel.addColumn("续借");
		myModel.addColumn("还书");
		this.getColumnModel().getColumn(4).setCellEditor(new LibraryButtonRender("续借", fundam));// 设置编辑器
		this.getColumnModel().getColumn(4).setCellRenderer(new LibraryButtonRender("续借", fundam));
		this.getColumnModel().getColumn(5).setCellEditor(new LibraryButtonRender("还书", fundam));// 设置编辑器
		this.getColumnModel().getColumn(5).setCellRenderer(new LibraryButtonRender("还书", fundam));

		// reset the column width
		this.getColumnModel().getColumn(0).setPreferredWidth(60);
		this.getColumnModel().getColumn(1).setPreferredWidth(146);
		this.getColumnModel().getColumn(2).setPreferredWidth(82);
		this.getColumnModel().getColumn(3).setPreferredWidth(82);
		this.getColumnModel().getColumn(4).setPreferredWidth(65);
		this.getColumnModel().getColumn(5).setPreferredWidth(65);
	}
}

class RoutineTableBorrow extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (row % 2 == 0) {
			// 设置偶数行的背景颜色
			setBackground(Color.WHITE);
		} else {
			// 基数行的背景颜色
			setBackground(new Color(240, 240, 240));
		}

		if (row == JBorrowTable.current) {
			setBackground(new Color(215, 225, 255));
		}

		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
	// 来自 https://blog.csdn.net/sdlcgxcqx/article/details/4539107
}