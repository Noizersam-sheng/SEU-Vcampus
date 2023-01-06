package cn.seu.ui.support.library;

/**
 * @author 牟倪
 * @version 1.0
 * 放置于表格末列的按钮，渲染器/编辑器，支持增加/减少馆藏数量
 */

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import cn.seu.domain.personnel.PassData;
import cn.seu.ui.support.JCButton;

class LibraryManagerButtonRender extends AbstractCellEditor
		implements TableCellRenderer, ActionListener, TableCellEditor {
	public PassData passData = new PassData();

	static int MaxNumToBorrow = 30;

	private static final long serialVersionUID = 1L;
	private JButton button = null;
	private String instruction;
	public JCButton fundam;

	public LibraryManagerButtonRender(String ins, JCButton f) {
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
		Object[][] libraryManagerContent = fundam.libraryManagerTable.getContent().clone();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (instruction.contains("减少")) {
			int getIndex = fundam.libraryManagerTable.getSelectedRow();
			if (Integer.parseInt(libraryManagerContent[getIndex][2].toString()) > 0
					&& Integer.parseInt(libraryManagerContent[getIndex][3].toString()) <= 0) {
				JOptionPane.showMessageDialog(null, "可借阅数量已到达0本！");
				return;
			}
			libraryManagerContent[getIndex][2] = (Integer.parseInt(libraryManagerContent[getIndex][2].toString())) - 1;
			libraryManagerContent[getIndex][3] = (Integer.parseInt(libraryManagerContent[getIndex][3].toString())) - 1;
			passData.sub_b(libraryManagerContent[getIndex][0].toString(), libraryManagerContent[getIndex][1].toString(),
					libraryManagerContent[getIndex][2].toString(), libraryManagerContent[getIndex][3].toString(),
					libraryManagerContent[getIndex][4].toString());
			// TODO: 减少馆藏接口
			if (libraryManagerContent[getIndex][2].equals(0)) { // 这本书没了
				passData.delete_b("book_name", libraryManagerContent[getIndex][1].toString());
				// TODO: 彻底删除书籍接口
				Object[][] newLibraryManagerContent = new Object[libraryManagerContent.length - 1][5];
				for (int i = 0; i < getIndex; ++i)
					newLibraryManagerContent[i] = libraryManagerContent[i].clone();
				for (int i = getIndex + 1; i < libraryManagerContent.length; ++i)
					newLibraryManagerContent[i - 1] = libraryManagerContent[i].clone();
				fundam.libraryManagerTable.updateDisplay(newLibraryManagerContent);
				return;
			}
			fundam.libraryManagerTable.updateDisplay(libraryManagerContent);
		} else if (instruction.contains("增加")) {
			int getIndex = fundam.libraryManagerTable.getSelectedRow();
			libraryManagerContent[getIndex][2] = (Integer.parseInt(libraryManagerContent[getIndex][2].toString())) + 1;
			libraryManagerContent[getIndex][3] = (Integer.parseInt(libraryManagerContent[getIndex][3].toString())) + 1;
			passData.sub_b(libraryManagerContent[getIndex][0].toString(), libraryManagerContent[getIndex][1].toString(),
					libraryManagerContent[getIndex][2].toString(), libraryManagerContent[getIndex][3].toString(),
					libraryManagerContent[getIndex][4].toString());
			// TODO: 增加馆藏接口，貌似这个sub_b是可以直接update的
			fundam.libraryManagerTable.updateDisplay(libraryManagerContent);
		}
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return button;
	}
}