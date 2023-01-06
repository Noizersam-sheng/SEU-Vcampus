package cn.seu.ui.support.jwc;

/**
 * 放置于成绩表末列的添加成绩按钮，渲染器 & 编辑器。
 */

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import cn.seu.domain.personnel.PassData;

// 渲染器 编辑器
class EnterScoreButtonRender extends AbstractCellEditor implements TableCellRenderer, ActionListener, TableCellEditor {
	public PassData passData = new PassData();
	public PassData passdata = new PassData();

	private static final long serialVersionUID = 1L;
	private JButton button = null;
	public JEnterScoreTable table;

	public EnterScoreButtonRender(String ins, JEnterScoreTable f) {
		table = f;
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
		int getIndex = table.getSelectedRow();
		if (table.getValueAt(getIndex, 2).toString().length() == 0)
			return;
		if (Double.parseDouble(table.getValueAt(getIndex, 2).toString()) < 0
				|| Double.parseDouble(table.getValueAt(getIndex, 2).toString()) > 100) {
			JOptionPane.showMessageDialog(null, "成绩必须介于0到100之间！", "错误", JOptionPane.ERROR_MESSAGE);
			table.setValueAt(60, getIndex, 2);
			return;
		}
		passdata.registration_score(table.getValueAt(getIndex, 0).toString(), table.courseID,
				Double.parseDouble(table.getValueAt(getIndex, 2).toString()));
		// 接口，发送成绩，参数分别是一卡通号、课程号、成绩
		JOptionPane.showMessageDialog(null, "登记成功！");
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return button;
	}
}