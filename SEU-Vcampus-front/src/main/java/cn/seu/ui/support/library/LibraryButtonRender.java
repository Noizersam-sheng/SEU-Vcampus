package cn.seu.ui.support.library;

/**
 * @author 牟倪
 * @version 1.0
 * 放置于表格末列的按钮，渲染器/编辑器，支持借书/续借/还书
 */

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import cn.seu.domain.personnel.PassData;
import cn.seu.ui.support.JCButton;

class LibraryButtonRender extends AbstractCellEditor implements TableCellRenderer, ActionListener, TableCellEditor {
	public PassData passData = new PassData();

	static int MaxNumToBorrow = 30;

	private static final long serialVersionUID = 1L;
	private JButton button = null;
	private String instruction;
	public JCButton fundam;

	public LibraryButtonRender(String ins, JCButton f) {
		instruction = ins;
		fundam = f;
		button = new JButton(ins);
		button.addActionListener(this);
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		JOptionPane.showMessageDialog(null, instruction, "消息", JOptionPane.OK_OPTION);
		// 首先借书还书续借
		// 需要得到索书号和功能
		// 然后刷新
		// 要同时更改两个表格的内容
		Object[][] borrowContent = fundam.borrowTable.getContent().clone();
		Object[][] collectContent = fundam.collectionTable.getContent().clone();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (instruction.contains("借阅")) {
			int getIndex = fundam.collectionTable.getSelectedRow();
			if (borrowContent.length >= MaxNumToBorrow) {
				JOptionPane.showMessageDialog(null, "您的借阅已经达到最大借阅数量！");
				return;
			} else if (Integer.parseInt(collectContent[getIndex][3].toString()) <= 0) {
				JOptionPane.showMessageDialog(null, "该书已全部被借走！");
				return;
			}
			collectContent[getIndex][3] = (Integer.parseInt(collectContent[getIndex][3].toString())) - 1;
			collectContent[getIndex][4] = (Integer.parseInt(collectContent[getIndex][4].toString())) + 1;
			Date date = new Date();
			passData.borrow_book(fundam.person.account_number, collectContent[getIndex][0].toString(),
					collectContent[getIndex][1].toString(), date);
			// TODO: 借书接口
			fundam.collectionTable.updateDisplay(collectContent);
			Object[][] newBorrowContent = new Object[borrowContent.length + 1][4];
			for (int i = 0; i < borrowContent.length; ++i) {
				newBorrowContent[i] = borrowContent[i].clone();
			}
			newBorrowContent[borrowContent.length][0] = collectContent[getIndex][0];
			newBorrowContent[borrowContent.length][1] = collectContent[getIndex][1];
			newBorrowContent[borrowContent.length][2] = sdf.format(new Date());
			newBorrowContent[borrowContent.length][3] = sdf
					.format(new Date().getTime() + ((long) (30)) * 24 * 60 * 60 * 1000);
			fundam.borrowTable.updateDisplay(newBorrowContent);

		} else if (instruction.contains("还书")) {
			int bookIndex = fundam.borrowTable.getSelectedRow();
			passData.return_book(fundam.person.account_number, borrowContent[bookIndex][0].toString());
			// TODO: 还书接口
			Object[][] newBorrowContent = new Object[borrowContent.length - 1][4];
			for (int i = 0; i < bookIndex; ++i) {
				newBorrowContent[i] = borrowContent[i].clone();
			}
			for (int i = bookIndex; i < borrowContent.length - 1; ++i) {
				newBorrowContent[i] = borrowContent[i + 1].clone();
			}
			fundam.borrowTable.updateDisplay(newBorrowContent);
		} else if (instruction.contains("续借")) {
			int bookIndex = fundam.borrowTable.getSelectedRow();
			Date date = new Date();
			passData.borrow_book(fundam.person.account_number, collectContent[bookIndex][0].toString(),
					collectContent[bookIndex][1].toString(), date);
			// TODO: 续借接口
			try {
				borrowContent[bookIndex][3] = sdf
						.format(new Date(sdf.parse((String) borrowContent[bookIndex][3]).getTime()
								+ ((long) (30)) * 24 * 60 * 60 * 1000));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			fundam.borrowTable.updateDisplay(borrowContent);
		}
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return button;
	}

}