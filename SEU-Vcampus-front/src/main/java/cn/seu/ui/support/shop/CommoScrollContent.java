package cn.seu.ui.support.shop;

/**
 * @author 牟倪
 * @version 1.1
 * 用来放商品内容的panel，要放在JScrollPane里使用
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.seu.domain.personnel.PassData;
import cn.seu.domain.personnel.Person;
import cn.seu.domain.store.Commodity;
import cn.seu.ui.support.MyType;
import cn.seu.ui.support.RoundBorder;

public class CommoScrollContent extends JPanel {
	static int X = 220, Y = 330;
	public static final int SHOP = 0;
	public static final int CART = 1;
	public static final int SECOND_HAND = 2;
	public static final int MANAGER = 3;

	PassData passdata = new PassData();

	Commodity[] commoList = null;
	int state;
	Person person = null;

	public CommoScrollContent(Commodity[] commoListPara, int statePara, Person personPara) {
		super(null);
		this.commoList = commoListPara;
		this.state = statePara;
		this.person = personPara;
		this.setPreferredSize(new Dimension(1100, (commoList.length + 4) / 5 * Y + 50));

		render();
	}

	public void appendCommoList(Commodity addOne) {
		Commodity[] commoListNew = new Commodity[commoList.length + 1];
		for (int i = 0; i < commoList.length; ++i) {
			commoListNew[i] = commoList[i];
		}
		commoListNew[commoList.length] = addOne;
		setCommoList(commoListNew);
	}

	/**
	 * 这是一个用来set新商品列表的函数
	 * 
	 * @param commoListNew 新的商品列表
	 */
	public void setCommoList(Commodity[] commoListNew) {
		this.commoList = commoListNew;
		render();
	}

	/**
	 * 这是一个用来渲染的函数
	 */
	public void render() {
		this.removeAll(); // 首先移除所有
		this.repaint(); // 然后刷新面板

		for (int i = 0; i < commoList.length; ++i) {
			int i0 = i;
			JPanel info = new JPanel(null);

			info.setBounds((i % 5) * X, (i / 5) * Y, X, Y);
			info.setBackground(Color.WHITE);
			info.setBorder(new RoundBorder(0, Color.WHITE, 1));
			info.setOpaque(true);

			// [start], elements
			JLabel image = new JLabel(new ImageIcon(commoList[i].getImage()));// commoList[i].image
			JLabel description = new JLabel();
			description.setFont(new Font("微软雅黑", Font.PLAIN, 12));
			description.setSize(200, 0);
			JlabelSetText(description, commoList[i].getName());

			JLabel type = new JLabel(commoList[i].getType());
			JLabel origin = new JLabel(commoList[i].getMarket() == 2 ? "二手市场" : "正规商家");
			JLabel priceOld = new JLabel("￥" + String.format("%.2f", commoList[i].getPrice()));
			JLabel priceNew = new JLabel(
					"￥" + String.format("%.2f", commoList[i].getPrice() * commoList[i].getDiscount()));
			JLabel number = new JLabel("余" + commoList[i].getNum() + "件");
			// [end]

			// [start], size & location & format
			image.setBounds(10, 10, 200, 200);
			image.setHorizontalAlignment(JLabel.CENTER);
			description.setBounds(10, 217, 200, 40);
//			description.setFont(new Font("微软雅黑", Font.PLAIN, 12));

			type.setBorder(new RoundBorder(0, Color.GRAY, 1));
			origin.setBorder(new RoundBorder(0, Color.GRAY, 1));
			type.setFont(new Font("微软雅黑", Font.PLAIN, 12));
			origin.setFont(new Font("微软雅黑", Font.PLAIN, 12));
			type.setBounds(10, 268, 100, 25);
			origin.setBounds(125, 268, 80, 25);
			type.setHorizontalAlignment(JLabel.CENTER);
			origin.setHorizontalAlignment(JLabel.CENTER);

			priceOld.setForeground(Color.GRAY);
			priceOld.setFont(new Font("微软雅黑", Font.PLAIN, 12));
			priceNew.setForeground(MyType.redColor);
			priceNew.setFont(new Font("微软雅黑", Font.BOLD, 18));
			priceNew.setBounds(10, 295, 85, 30);
			priceOld.setBounds(95, 300, 85, 25);

//			number.setFont(new Font("微软雅黑", Font.PLAIN, 11));
			number.setBounds(160, 300, 60, 25);
			// [end]

			// [start], function
			info.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					if (state == SHOP) {
						Object[] options = new Object[] { " 直接购买 ", " 加入购物车 ", "取消" };
						int optionSelected = JOptionPane.showOptionDialog(null, "直接购买 / 加入购物车？", "购买选项",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

						if (optionSelected == 0) { // 直接购买
							int numToBuy = Integer.parseInt(JOptionPane.showInputDialog(null, "请输入购买的件数"));
							System.out.println("购买件数 = " + numToBuy);
							if (numToBuy == 0) {
								return;
							} else if (numToBuy > commoList[i0].getNum()) {
								JOptionPane.showMessageDialog(null, "输入数量大于商品数量" + commoList[i0].getNum() + "，错误！");
								return;
							}

							Object[] personalInfo = passdata
									.status_information(passdata.search_s("id", person.account_number));
							if (Double.parseDouble(personalInfo[10].toString())
									- commoList[i0].getPrice() * commoList[i0].getDiscount() * numToBuy < 0) {
								JOptionPane.showMessageDialog(null, "一卡通余额不足！");
								return;
							}

							int buyState = passdata.changeCommodity(new Commodity(commoList[i0].getID(),
									commoList[i0].getType(), commoList[i0].getName(), commoList[i0].getPrice(),
									commoList[i0].getPrice(), commoList[i0].getDiscount(), sdf.format(new Date()),
									-numToBuy, commoList[i0].getMarket(), commoList[i0].getPoster(),
									commoList[i0].getImage()));
							// TODO: 接口，学生购买物品

							if (buyState >= 0) {
								JOptionPane.showMessageDialog(null, "购买成功！");
								commoList[i0].setNum(commoList[i0].getNum() - numToBuy);
								number.setText("余" + Integer.toString(commoList[i0].getNum()) + "件");
								// 添加购买记录
								passdata.addCommodityRecord(
										person.account_number, "", commoList[i0].getID(),
										commoList[i0].getName(), commoList[i0].getType(), numToBuy,
										numToBuy * commoList[i0].getPrice() * commoList[i0].getDiscount(),
										sdf.format(new Date()), commoList[i0].getMarket()
								);
								// student没有name，因为person没有

								double money = Double.parseDouble(personalInfo[10].toString())
										- commoList[i0].getPrice() * commoList[i0].getDiscount() * numToBuy;
								passdata.c_record(sdf.format(new Date()), person.account_number, 2,
										commoList[i0].getPrice() * numToBuy * commoList[i0].getDiscount(), money);
								passdata.recharge(person.account_number, money);

								// 二手商品：给商品拥有者充钱
								if(commoList[i0].getMarket() == 2) {
									Object[] poster_personal_Info = passdata.status_information(passdata.search_s("id", commoList[i0].getPoster()));
									double poster_money = Double.parseDouble(poster_personal_Info[10].toString());
									double poster_updated_money = poster_money + commoList[i0].getPrice() * commoList[i0].getDiscount() * numToBuy;
									passdata.recharge(commoList[i0].getPoster(), poster_updated_money);
									passdata.c_record(sdf.format(new Date()), commoList[i0].getPoster(), 5,
										commoList[i0].getPrice() * numToBuy * commoList[i0].getDiscount(), poster_updated_money);
								}
							} else
								JOptionPane.showMessageDialog(null, "购买失败！");

						} else if (optionSelected == 1) { // 加入购物车
							passdata.addCommodityCar(person.account_number, "", commoList[i0]);
							// TODO: 接口，添加到购物车
							JOptionPane.showMessageDialog(null, "添加成功！");
						}

					} else if (state == CART) {
						Object[] options = new Object[] { " 购买 ", " 删除 ", "取消" };
						int optionSelected = JOptionPane.showOptionDialog(null, "购买 / 从购物车中删除商品？", "购物车选项",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

						if (optionSelected == 0) { // 直接购买
							int numToBuy = Integer.parseInt(JOptionPane.showInputDialog(null, "请输入购买的件数"));
							if (numToBuy == 0) {
								return;
							} else if (numToBuy > commoList[i0].getNum()) {
								JOptionPane.showMessageDialog(null, "输入数量大于商品数量" + commoList[i0].getNum() + "，错误！");
								return;
							}

							Object[] personalInfo = passdata
									.status_information(passdata.search_s("id", person.account_number));
							if (Double.parseDouble(personalInfo[10].toString())
									- commoList[i0].getPrice() * commoList[i0].getDiscount() * numToBuy < 0) {
								JOptionPane.showMessageDialog(null, "一卡通余额不足！");
								return;
							}

							int buyState = passdata.changeCommodity(new Commodity(commoList[i0].getID(),
									commoList[i0].getType(), commoList[i0].getName(), commoList[i0].getPrice(),
									commoList[i0].getPrice(), commoList[i0].getDiscount(), sdf.format(new Date()),
									-numToBuy, commoList[i0].getMarket(), commoList[i0].getPoster(),
									commoList[i0].getImage()));
							// 接口，学生购买物品
//							int buyState = 1;
							System.out.println("从购物车购买商品，buystate = " + Integer.toString(buyState));
							if (buyState >= 0) {
								JOptionPane.showMessageDialog(null, "购买成功！");

								// 添加购买记录
								passdata.addCommodityRecord(
										person.account_number, "", commoList[i0].getID(),
										commoList[i0].getName(), commoList[i0].getType(), numToBuy,
										numToBuy * commoList[i0].getPrice() * commoList[i0].getDiscount(),
										sdf.format(new Date()), commoList[i0].getMarket()
								);

//								commoList[i0].setNum(commoList[i0].getNum() - numToBuy);
//								number.setText("余" + Integer.toString(commoList[i0].getNum()) + "件");
								// 本来是更改商品数量，但应该把商品从购物车删去

								// 从购物车中删去这一商品
								passdata.deleteCommodityCar(person.account_number, commoList[i0].getID());

								// 银行那边
								double money = Double.parseDouble(personalInfo[10].toString())
										- commoList[i0].getPrice() * commoList[i0].getDiscount() * numToBuy;
								passdata.c_record(sdf.format(new Date()), person.account_number, 2,
										commoList[i0].getPrice() * commoList[i0].getDiscount() * numToBuy, money);
								passdata.recharge(person.account_number, money);

								// 二手商品：给商品拥有者充钱
								if(commoList[i0].getMarket() == 2) {
									Object[] poster_personal_Info = passdata.status_information(passdata.search_s("id", commoList[i0].getPoster()));
									double poster_money = Double.parseDouble(poster_personal_Info[10].toString());
									double poster_updated_money = poster_money + commoList[i0].getPrice() * commoList[i0].getDiscount() * numToBuy;
									passdata.recharge(commoList[i0].getPoster(), poster_updated_money);
									passdata.c_record(sdf.format(new Date()), commoList[i0].getPoster(), 5,
											commoList[i0].getPrice() * numToBuy * commoList[i0].getDiscount(), poster_updated_money);
								}

								// 界面的删除操作
								Commodity[] listNew = new Commodity[commoList.length - 1];
								for (int i = 0; i < i0; ++i)
									listNew[i] = commoList[i];
								for (int i = i0 + 1; i < commoList.length; ++i)
									listNew[i - 1] = commoList[i];
								commoList = listNew.clone();
								render();
							} else
								JOptionPane.showMessageDialog(null, "购买失败！");

						} else if (optionSelected == 1) { // 从购物车中删除商品
							// 从购物车中删去这一商品
							passdata.deleteCommodityCar(person.account_number, commoList[i0].getID());
							JOptionPane.showMessageDialog(null, "删除成功！");
							// 界面的删除操作
							Commodity[] listNew = new Commodity[commoList.length - 1];
							for (int i = 0; i < i0; ++i)
								listNew[i] = commoList[i];
							for (int i = i0 + 1; i < commoList.length; ++i)
								listNew[i - 1] = commoList[i];
							commoList = listNew.clone();
							render();
						}
					} else if (state == SECOND_HAND) {
						Object[] options = new Object[] { "修改折扣", " 删除 ", "取消" };
						int optionSelected = JOptionPane.showOptionDialog(null, "请选择对二手商品的操作", "二手商品管理",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
						if (optionSelected == 0) { // 修改自己二手商品的折扣
							String disString = JOptionPane.showInputDialog("请输入新的折扣：\n", commoList[i0].getNum());
							double newDis = Double.parseDouble(disString);
							if (newDis > 0 && newDis < 1) {
								passdata.makeDiscount(commoList[i0]);
								// TODO: 接口，修改折扣
								// 修改界面
								commoList[i0].setDiscount(newDis);
								priceNew.setText("￥" + String.format("%.2f",
										commoList[i0].getPrice() * commoList[i0].getDiscount()));
							}
						} else if (optionSelected == 1) { // 删除自己的二手商品
							passdata.deleteCommodity(commoList[i0].getID());
							// TODO: 接口，删除自己的二手商品
							JOptionPane.showMessageDialog(null, "删除成功！");
							// 从二手商品界面中把该商品删去
							Commodity[] listNew = new Commodity[commoList.length - 1];
							for (int i = 0; i < i0; ++i)
								listNew[i] = commoList[i];
							for (int i = i0 + 1; i < commoList.length; ++i)
								listNew[i - 1] = commoList[i];
							commoList = listNew.clone();
							render();
						}
					} else if (state == MANAGER) {
						Object[] options = new Object[] { "修改库存", "修改折扣", "删除商品", "取消" };
						int optionSelected = JOptionPane.showOptionDialog(null, "请选择需要进行的操作", "商品管理",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

						if (optionSelected == 0) { // 修改库存
							String numString = JOptionPane.showInputDialog("请输入库存数量：\n", commoList[i0].getNum());
							int oldNum = commoList[i0].getNum();
							int newNum = Integer.parseInt(numString);

							passdata.changeCommodity(new Commodity(commoList[i0].getID(), commoList[i0].getType(),
									commoList[i0].getName(), commoList[i0].getPrice(), commoList[i0].getValue(),
									commoList[i0].getDiscount(), sdf.format(new Date()), newNum - oldNum,
									commoList[i0].getMarket(), commoList[i0].getPoster(), commoList[i0].getImage()));
							// TODO: 接口，改变库存数量
							// 修改界面
							commoList[i0].setNum(newNum);
							number.setText("余" + commoList[i0].getNum() + "件");
							if (newNum == 0) {
								JOptionPane.showMessageDialog(null, "删除成功！");
								// 从商品界面中把该商品删去
								Commodity[] listNew = new Commodity[commoList.length - 1];
								for (int i = 0; i < i0; ++i)
									listNew[i] = commoList[i];
								for (int i = i0 + 1; i < commoList.length; ++i)
									listNew[i - 1] = commoList[i];
								commoList = listNew.clone();
								render();
							}
						} else if (optionSelected == 1) { // 修改折扣
							String disString = JOptionPane.showInputDialog("请输入新的折扣：\n", commoList[i0].getDiscount());
							double newDis = Double.parseDouble(disString);
							if (newDis > 0 && newDis < 1) {
								passdata.makeDiscount(commoList[i0]);
								// TODO: 接口，修改折扣
								// 修改界面
								commoList[i0].setDiscount(newDis);
								priceNew.setText("￥" + String.format("%.2f",
										commoList[i0].getPrice() * commoList[i0].getDiscount()));
							}
						} else if (optionSelected == 2) {
							passdata.deleteCommodity(commoList[i0].getID());
							// TODO: 接口，删除商品
							JOptionPane.showMessageDialog(null, "删除成功！");
							// 从商品界面中把该商品删去
							Commodity[] listNew = new Commodity[commoList.length - 1];
							for (int i = 0; i < i0; ++i)
								listNew[i] = commoList[i];
							for (int i = i0 + 1; i < commoList.length; ++i)
								listNew[i - 1] = commoList[i];
							commoList = listNew.clone();
							render();
						}
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
					info.setBorder(new RoundBorder(0, MyType.redColor, 1));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					info.setBorder(new RoundBorder(0, Color.WHITE, 1));
				}

			});
			// [end]

			// [start], add elements
			info.add(image);
			info.add(description);
			info.add(type);
			info.add(origin);
			info.add(priceOld);
			info.add(priceNew);
			info.add(number);
			// [end]

			this.add(info);
		}
	}

	/**
	 * 用html标签实现自动换行setText
	 * 
	 * @param jLabel     被setText的JLabel
	 * @param longString 要添加的长字符串
	 * @return 是否添加成功
	 */

	public static boolean JlabelSetText(JLabel jLabel, String longString) {
		// throws InterruptedException
		boolean ans = false;
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
					ans = true;
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
		return ans;
	}
}
