package cn.seu.ui.support;

/**
 * @author 牟倪
 * “你可能想知道”的条目，问答内容采用硬编码形式记录
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * 
 * @author 牟倪
 * 
 *         这是“你可能想知道”的条目封装成的类，在类里添加鼠标事件
 *
 */
public class WannaKnowItem extends JLabel {
	public WannaKnowItem(String s) {
		super(s);
		this.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		this.setBorder(new RoundBorder(30, Color.GRAY, 1));
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (s.contains("选不上实践课")) {
					JOptionPane.showMessageDialog(null,
							"计软智的同学们可以去教务老师办公室进行登记。\n" 
									+ "计科人智：洪老师，计算机楼120；软件：黄老师，计算机楼124。");
				} else if (s.contains("订购教材")) {
					JOptionPane.showMessageDialog(null, 
							"一般来说，学校提供通识课教材（如工数、英语）的统一订购途径，但专业课教材需要自行购买。\n"
									+ "也有部分专业课教材（如数字图像处理）可以统一订购。具体情况和书单请询问各班班长。");
				} else if (s.contains("查询SRTP学分")) {
					JOptionPane.showMessageDialog(null,
							"教务处首页-办事平台-东南大学课外研学学分管理系统（新），登录后即可查询。\n" 
									+ "注意，由于网页格式问题，手机端部分内容不显示，因此请使用电脑查询。");
				} else if (s.contains("学生酬金")) {
					JOptionPane.showMessageDialog(null,
							"1. 导师登录财务处总和信息门户；\n" 
							+ "2. 网上预约申报系统；\n" 
									+ "3. 选择报销管理中的酬金申报；\n"
							+ "4. 录入发放项目号、项目负责人、人员类型、酬金性质、酬金摘要等相关信息；\n"
									+ "5. 选择发放学生，填写发放金额，提交并打印酬金申报预约报销；\n"
							+ "6. 送交财务处（四牌楼校区财务科酬金窗口或九龙湖校区财务科118室）；\n"
									+ "7. 收费与信息管理科负责月底前完成批量发放到卡工作；\n");
				} else if (s.contains("学生如何缴费")) {
					JOptionPane.showMessageDialog(null,
							"登录东南大学财务处缴费平台（caiwuchu.seu.edu.cn）缴费，支持支付宝、微信、银联扫码支付。\n" 
									+ "用户名是学生本人的一卡通号，初始密码为身份证号后6位。\n" 
											+ "如初始密码无法登录，请致电校园与网络中心：83790808。\n");
				} else if (s.contains("国际会议报销综合定额")) {
					JOptionPane.showMessageDialog(null,"1000元。");
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
				setForeground(MyType.focusColor);
				setBorder(new RoundBorder(30, MyType.focusColor, 1));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setForeground(Color.BLACK);
				setBorder(new RoundBorder(30, Color.GRAY, 1));
			}
		});
	}

}
