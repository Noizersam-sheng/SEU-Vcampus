package cn.seu.ui.login_panel;

/**
 * @author 牟倪
 * 登录窗口
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cn.seu.domain.personnel.PassData;
import cn.seu.domain.personnel.Person;
import cn.seu.ui.general.GeneralUI;
import cn.seu.ui.support.BackgroundFrame;
import cn.seu.ui.support.GridBagPanel;
import cn.seu.ui.support.MyType;

public class LoginUI {

	static ImageIcon background = new ImageIcon(MyType.figurePath + "seubg1-cast-500-353.jpg");
	static int X = 500, Y = 353;
	static ImageIcon logoIcon = new ImageIcon(MyType.iconPath + "vlogo.png");

	// pass data & person
	PassData passdata = new PassData();
	private GeneralUI generalUI;

	// frame & content panel
	private BackgroundFrame jf = new BackgroundFrame(background, X, Y);
	private GridBagPanel gbpanel = new GridBagPanel(X, Y);

	// element
	JLabel topSide = new JLabel(" 登录 - Login");
	JComboBox<String> typeCombo = new JComboBox<String>();
	{
		typeCombo.addItem("一卡通号");
		typeCombo.addItem("别名");
	}
	JLabel zhanghaoString = new JLabel("    账号");
	JLabel mimaString = new JLabel("    密码");
	JTextField username = new JTextField(14);
	JPasswordField password = new JPasswordField(14);
	JButton ok = new JButton("确定");
	JButton cancel = new JButton("取消");
	{
		ok.addActionListener(new LoginActionListener());
		cancel.addActionListener(new CancelActionListener());
	}

	// 两个事件响应函数
	class LoginActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			StringBuffer pwbuffer = new StringBuffer();
			pwbuffer.append(password.getPassword());
			if (username.getText().length() != 0 && pwbuffer.toString().length() != 0) {
				Object per = passdata.login(typeCombo.getSelectedItem().toString() == "一卡通号" ? true : false,
						username.getText(), pwbuffer.toString());
				if (per == null) {
					JOptionPane.showMessageDialog(gbpanel.panel, "账号或密码错误！");
				} else {
					generalUI.person=(Person)per;
					System.out.println(per);
					jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
				}
//				JOptionPane.showMessageDialog(gbpanel.panel, "密码是" + pwbuffer.toString());
			}
		}
	}

	class CancelActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
		}
	}

	// 初始化
	public LoginUI(GeneralUI g) {
		// General UI pointer
		this.generalUI=g;
		
		// title & font & style
		jf.setTitle("登录 - Login");
		jf.setIconImage(logoIcon.getImage());
		this.topSide.setFont(MyType.h1Font);

		// add element to grid bag panel
		gbpanel.gridAddComponent(topSide, 1, 0, 4, 1);
		gbpanel.gridAddComponent(new JLabel(" "), 1, 1, 4, 1);
		gbpanel.gridAddComponent(typeCombo, 0, 1 + 1, 1, 1);
		gbpanel.gridAddComponent(zhanghaoString, 1, 1 + 1, 1, 1);
		gbpanel.gridAddComponent(mimaString, 1, 2 + 1, 1, 1);
		gbpanel.gridAddComponent(username, 2, 1 + 1, 2, 1);
		gbpanel.gridAddComponent(password, 2, 2 + 1, 2, 1);
		gbpanel.gridAddComponent(ok, 1, 3 + 1, 1, 1);
		gbpanel.gridAddComponent(cancel, 2, 3 + 1, 1, 1);

		// add content panel to frame
		jf.addContentPanel(gbpanel.panel);
	}

	public void display() {
		jf.setResizable(false);
		jf.display();
	}

	public static void main(String args[]) {
		System.gc();
		MyType.initGlobalFont();
		LoginUI ui = new LoginUI(null);
		ui.display();
	}
}
