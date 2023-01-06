package cn.seu.domain.personnel;

/**
 * 这是一个功能函数类，里面包含了整个项目的所有功能
 * @author 顾深远
 * @version 1.0
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import cn.seu.domain.consumption_record.Consumption_record;
import cn.seu.domain.courses.Course;
import cn.seu.domain.library.Book;
import cn.seu.domain.library.Book_borrowing_record;
import cn.seu.domain.store.Commodity;
import cn.seu.domain.store.Commodity_Car;
import cn.seu.domain.store.Commodity_Record;
import cn.seu.domain.store.Sales;
import cn.seu.dto.AlipayInfoInDTO;
import cn.seu.dto.AlipayInfoOutDTO;
import cn.seu.socket3.Client;
import cn.seu.socket3.Message;

public class PassData implements Serializable {
	public Client a;

	public PassData() {
		a = new Client();
	}

	// [start], login & register
	/**
	 * 登录
	 * 
	 * @param way            方式
	 * @param account_number 账号
	 * @param password       密码
	 * @return 加密后的账号信息
	 */
	public String sign_in(boolean way, String account_number, String password)// 登录
	{
		String ways;
		if (way) {
			ways = "id";
		} else {
			ways = "nickname";
		}
		int secret = 8;
		byte[] bt = password.getBytes();
		for (int i = 0; i < bt.length; i++) {
			bt[i] = (byte) (bt[i] ^ (int) secret); // 通过异或运算进行加密
		}
		String newresult = new String(bt, 0, bt.length);
		return ways + '\n' + account_number + '\n' + newresult;
	}

	/**
	 * 格式转换
	 * 
	 * @param A 账号对象
	 * @return 账号信息
	 */
	public Object r_object(Object A) {
		if (A == null)
			return null;
		// Transformation B=(Transformation) A;
		ArrayList<ArrayList<String>> B = (ArrayList<ArrayList<String>>) A;
		if (Integer.parseInt(B.get(0).get(4)) == 5) {
			String account_number = B.get(0).get(0);
			String password = B.get(0).get(2);
			int jurisdiction = Integer.parseInt(B.get(0).get(4));
			int gender = Integer.parseInt(B.get(0).get(6));
			String name = B.get(0).get(3);
			int age = Integer.parseInt(B.get(0).get(5));
			String data = B.get(0).get(7);
			String[] data3 = data.split("-");
			int year = Integer.parseInt(data3[0]);
			int month = Integer.parseInt(data3[1]);
			int day = Integer.parseInt(data3[2]);
			String nickname = B.get(0).get(1);
			String major = B.get(0).get(8);
			String college = B.get(0).get(9);
			double money = Double.parseDouble(B.get(0).get(10));
			String picture = B.get(0).get(11);
			double web_money = Double.parseDouble(B.get(0).get(12));
			Student C = new Student(account_number, nickname, password, name, jurisdiction, age, gender, year, month,
					day, major, college, money, picture, web_money);
			return C;
		}

		else if (Integer.parseInt(B.get(0).get(4)) == 4) {
			String account_number = B.get(0).get(0);
			String password = B.get(0).get(2);
			int jurisdiction = Integer.parseInt(B.get(0).get(4));
			String college = B.get(0).get(9);
			String nickname = B.get(0).get(1);
			String name = B.get(0).get(3);
			int age = Integer.parseInt(B.get(0).get(5));
			int gender = Integer.parseInt(B.get(0).get(6));
			double money = Double.parseDouble(B.get(0).get(10));
			Teacher C = new Teacher(account_number, nickname, password, name, jurisdiction, age, gender, college, 0);
			return C;
		} else if (Integer.parseInt(B.get(0).get(4)) == 3) {
			String account_number = B.get(0).get(0);
			String password = B.get(0).get(2);
			int jurisdiction = Integer.parseInt(B.get(0).get(4));
			Store_Manager C = new Store_Manager(account_number, password, jurisdiction);
			return C;
		} else if (Integer.parseInt(B.get(0).get(4)) == 2) {
			String account_number = B.get(0).get(0);
			String password = B.get(0).get(2);
			int jurisdiction = Integer.parseInt(B.get(0).get(4));
			Librarian C = new Librarian(account_number, password, jurisdiction);
			return C;
		} else if (Integer.parseInt(B.get(0).get(4)) == 1) {
			String account_number = B.get(0).get(0);
			String password = B.get(0).get(2);
			int jurisdiction = Integer.parseInt(B.get(0).get(4));
			Academic_affairs_teacher C = new Academic_affairs_teacher(account_number, password, jurisdiction);
			return C;
		}
		return null;
	}

	/**
	 * 登录
	 * 
	 * @param way            登录方式
	 * @param account_number 账号
	 * @param password       密码
	 * @return 账号对象
	 */
	public Object login(boolean way, String account_number, String password)// moni 调用的参数
	{
		Sign_in data = new Sign_in(account_number, way, password);
		return r_object(a.request(1, true, data.toString()));
	}

	/**
	 * 注册老师
	 * 
	 * @param account_number 账号
	 * @param nickname       昵称
	 * @param password       密码
	 * @param name           姓名
	 * @param jurisdiction   权限
	 * @param age            年龄
	 * @param gender         性别
	 * @param college        学院
	 * @return 教师对象
	 */
	public int register_t(String account_number, String nickname, String password, String name, int jurisdiction,
			int age, int gender, String college)// 注册老师
	{
		Teacher A = new Teacher(account_number, nickname, password, name, jurisdiction, age, gender, college, 0);
		return (int) a.request(11, true, A.toString());
	}

	/**
	 * 注册学生
	 * 
	 * @param account_number 账号
	 * @param nickname       昵称
	 * @param password       密码
	 * @param name           姓名
	 * @param jurisdiction   权限
	 * @param age            年龄
	 * @param gender         性别
	 * @param year           年
	 * @param month          月
	 * @param day            日
	 * @param major          专业
	 * @param college        学院
	 * @return 学生对象
	 */
	public int register_s(String account_number, String nickname, String password, String name, int jurisdiction,
			int age, int gender, int year, int month, int day, String major, String college)// 注册学生
	{
		Student A = new Student(account_number, nickname, password, name, jurisdiction, age, gender, year, month, day,
				major, college, 0, "null", 0);
		return (int) a.request(11, true, A.toString());
	}

	/**
	 * 注销学生
	 * 
	 * @param account_number 传输内容
	 * @return 是否操作成功
	 */
	public String cancel_s(String account_number)// 注销学生
	{
		return account_number;
	}

	// [end]

	// [start], library
	/**
	 * 添加书籍
	 * 
	 * @param book_nu  索书号
	 * @param bookname 书名
	 * @param whole_nu 馆藏数量
	 * @return
	 */
	public int add_book(String book_nu, String bookname, String whole_nu)// 加书
	{
		Book book = new Book(book_nu, bookname, Integer.parseInt(whole_nu), Integer.parseInt(whole_nu), 0);
		return (int) a.request(111, true, book.toString());
	}

	/**
	 * 查找书本
	 * 
	 * @param ways     方式（索书号/书名）
	 * @param bookname 内容
	 * @return 数据库搜索后对象
	 */
	public Object search_b(String ways, String bookname)// 查找书本
	{
		return a.request(141, true, ways + "\n" + bookname);
	}

	/**
	 * 查找学生
	 * 
	 * @param ways           方式（账号/昵称）
	 * @param account_number 内容
	 * @return 数据库搜索对象
	 */
	public Object search_s(String ways, String account_number)// 查找学生
	{
		return a.request(41, true, ways + "\n" + account_number);
	}

	/**
	 * 查找学生借书目录
	 * 
	 * @param accounr_number 账号
	 * @return 数据库搜索对象
	 */
	public Object search_b_s(String accounr_number)// 查找学生借书目录
	{
		return a.request(181, true, accounr_number);
	}

	/**
	 * 排名
	 * 
	 * @param ways     检索方式（索书号/书名）
	 * @param bookname 内容
	 * @param sorts    1：馆藏排序 2：借阅次数排序 3：可借阅两排序
	 * @return 数据库搜索对象
	 */
	public Object sort(String ways, String bookname, String sorts) {
		return a.request(145, true, ways + "\n" + bookname + "\n" + sorts);
	}

	/**
	 * 显示书本
	 * 
	 * @param A 书本对象
	 * @return 方便UI显示的二维数组
	 */
	public Object[][] show_b(Object A)// 显示书本
	{
		Object[][] O = new Object[0][0];
		if (A == null) {
			return O;
		}
		ArrayList<ArrayList<String>> B = (ArrayList<ArrayList<String>>) A;
		Object[][] C = new Object[B.size()][5];
		for (int i = 0; i < B.size(); i++) {
			C[i][0] = B.get(i).get(0);
			C[i][1] = B.get(i).get(1);
			C[i][2] = B.get(i).get(2);
			C[i][3] = B.get(i).get(3);
			C[i][4] = B.get(i).get(4);
		}
		return C;
	}

	/**
	 * 显示所有书
	 * 
	 * @return 方便UI显示的二维数组
	 */
	public Object show_all_b()// 显示所有书
	{
		return a.request(141, true, "null\n" + "null");
	}

	/**
	 * 减少馆藏
	 * 
	 * @param book_number  书号
	 * @param book_name    书名
	 * @param whole_nu     馆藏
	 * @param number       可借阅数目
	 * @param borrow_times 借阅次数
	 * @return 是否操作成功
	 */
	public int sub_b(String book_number, String book_name, String whole_nu, String number, String borrow_times)// 减少馆藏
	{
		return (int) a.request(131, true,
				book_number + "\n" + book_name + "\n" + whole_nu + "\n" + number + "\n" + borrow_times);
	}

	/**
	 * 减少馆藏all
	 * 
	 * @param ways      方式（索书号/书名）
	 * @param book_name 内容
	 * @return 是否操作成功
	 */
	public int delete_b(String ways, String book_name)// 减少馆藏all
	{
		return (int) a.request(121, true, ways + "\n" + book_name);
	}

	/**
	 * 显示学生借阅图书
	 * 
	 * @param A 书籍对象
	 * @return 方便UI显示的二维数组
	 */
	public Object[][] show_s(Object A)// 显示学生借阅图书
	{
		ArrayList<ArrayList<String>> B = (ArrayList<ArrayList<String>>) A;
		Object[][] C = new Object[B.size()][4];
		for (int i = 0; i < B.size(); i++) {
			Date date = new Date();
			long l = Long.parseLong(B.get(i).get(4));
			date.setTime(l);
			long l_l = l + (long) (1000) * 60 * 60 * 24 * 30;
			Date date_l = new Date(l_l);
			C[i][0] = B.get(i).get(1);
			C[i][1] = B.get(i).get(2);
			C[i][2] = date;
			C[i][3] = date_l;
		}
		return C;
	}

	/**
	 * 借书
	 * 
	 * @param account_number 账号
	 * @param book_nu        书号
	 * @param bookname       书名
	 * @param date           日期
	 * @return 是否操作成功
	 */
	public int borrow_book(String account_number, String book_nu, String bookname, Date date)// 借书
	{
		Book_borrowing_record C = new Book_borrowing_record(account_number, book_nu, bookname, date);
		return (int) a.request(161, true, C.tostring());
	}

	/**
	 * 还书
	 * 
	 * @param account_number 账号
	 * @param book_nu        书号
	 * @return 是否操作成功
	 */
	public int return_book(String account_number, String book_nu)// 还书
	{
		return (int) a.request(171, true, account_number + "\n" + book_nu);
	}
	// [end]

	// [start], 得到学籍信息
	/**
	 * 添加图片
	 * 
	 * @param account_number 账号
	 * @param pic            图片
	 * @return 是否操作成功
	 */
	public int addpic(String account_number, String pic) {
		return (int) a.request(81, true, account_number + "\n" + pic);
	}

	/**
	 * 学籍信息
	 * 
	 * @param A 学生对象
	 * @return 方便UI显示的一维数组
	 */
	public Object[] status_information(Object A)// 学籍信息
	{
		ArrayList<ArrayList<String>> B = (ArrayList<ArrayList<String>>) A;
		Object[] C = new Object[12];
		C[0] = B.get(0).get(0);
		C[1] = B.get(0).get(1);
		C[2] = B.get(0).get(3);
		C[3] = B.get(0).get(4);
		C[4] = B.get(0).get(5);
		C[5] = B.get(0).get(6);
		C[6] = B.get(0).get(7);
		C[7] = B.get(0).get(8);
		C[8] = B.get(0).get(9);
		if (B.get(0).get(11) == null)
			C[9] = "无";
		else
			C[9] = B.get(0).get(11);
		C[10] = B.get(0).get(10);
		C[11] = B.get(0).get(12);
		return C;
	}

	/**
	 * 教师信息
	 * 
	 * @param A 教师对象
	 * @return 方便UI显示的一维数组
	 */
	public Object[] teacher_information(Object A)//
	{
		ArrayList<ArrayList<String>> B = (ArrayList<ArrayList<String>>) A;
		Object[] C = new Object[B.size()];
		for (int i = 0; i < B.size(); i++) {
			C[0] = B.get(0).get(0);
			C[1] = B.get(0).get(1);
			C[2] = B.get(0).get(2);
			C[3] = B.get(0).get(3);
			C[4] = B.get(0).get(4);
			C[5] = B.get(0).get(5);
			C[6] = B.get(0).get(6);
			C[7] = B.get(0).get(7);
			C[8] = B.get(0).get(8);
			C[9] = B.get(0).get(9);
		}
		return C;
	}
	// [end]

	// [start], bank
	/**
	 * 金额刷新
	 * 
	 * @param account_number 账号
	 * @param addmoney       现有金额
	 * @return 是否操作成功
	 */
	public int recharge(String account_number, double addmoney) {
		String addMoney = String.valueOf(addmoney);
		return (int) a.request(61, true, account_number + "\n" + addMoney);
	}

	/**
	 * 网费刷新
	 * 
	 * @param account_number 账号
	 * @param addmoney       现有网费
	 * @return 是否操作成功
	 */
	public int web_recharge(String account_number, double addmoney) {
		String addMoney = String.valueOf(addmoney);
		return (int) a.request(71, true, account_number + "\n" + addMoney);
	}

	/**
	 * 生成消费记录
	 * 
	 * @param date           日期
	 * @param account_number 账号
	 * @param ways           消费形式
	 * @param cost           花费
	 * @param surplus        余额
	 * @return 是否操作成功
	 */
	public int c_record(String date, String account_number, int ways, double cost, double surplus) {
		Consumption_record A = new Consumption_record(date, account_number, ways, cost, surplus);
		return (int) a.request(411, true, A.toString());
	}

	/**
	 * 检索消费记录
	 * 
	 * @param ways           方式
	 * @param account_number 账号
	 * @return 消费记录对象
	 */
	public Object search_r(String ways, String account_number) {
		return a.request(412, true, ways + "\n" + account_number);
	}

	/**
	 * 显示消费记录
	 * 
	 * @param A 消费记录对象
	 * @return 方便UI显示的二维数组
	 */
	public Object[][] show_c_r(Object A)// 显示消费记录
	{
		Object[][] O = new Object[0][0];
		if (A == null) {
			return O;
		}
		ArrayList<ArrayList<String>> B = (ArrayList<ArrayList<String>>) A;
		Object[][] C = new Object[B.size()][4];
		for (int i = 0; i < B.size(); i++) {
			C[i][0] = B.get(i).get(4);
			int I = Integer.parseInt(B.get(i).get(1));
			switch (I) {
			case 1:
				C[i][1] = "卡片充值";
				break;
			case 2:
				C[i][1] = "校园商店";
				break;
			case 3:
				C[i][1] = "缴网费";
				break;
			case 5:
				C[i][1] = "二手商品收入";
				break;
			}
			switch (I) {
			case 1:
				C[i][2] = B.get(i).get(2);
				break;
			case 2:
				C[i][2] = "-" + B.get(i).get(2);
				break;
			case 3:
				C[i][2] = "-" + B.get(i).get(2);
				break;
			case 5:
				C[i][2] = B.get(i).get(2);
				break;
			}
			C[i][2] = String.format("%.2f", Double.parseDouble((String) C[i][2]));
			C[i][3] = String.format("%.2f", Double.parseDouble(B.get(i).get(3)));
		}
		return C;
	}
	// [end]

	// [start], 虚拟商店
	public ArrayList<Commodity> choose15(int type, ArrayList<Commodity> goods) {
		if (goods.size() <= 15)
			return goods;
		ArrayList<Commodity> ac = new ArrayList<Commodity>();
		// 对goods
		switch (type) {
		case 1:
			goods.sort(new Comparator<Commodity>() {
				@Override
				public int compare(Commodity arg0, Commodity arg1) {
					if (arg0.getPrice() == 0 || arg1.getPrice() == 0)
						return 0;
					if (arg1.getPrice() * arg1.getDiscount() > arg0.getPrice() * arg0.getDiscount())
						return 1;
					if (arg1.getPrice() * arg1.getDiscount() < arg0.getPrice() * arg0.getDiscount())
						return -1;
					return 0;
				}
			});
			break;
		case 2:
			goods.sort(new Comparator<Commodity>() {
				@Override
				public int compare(Commodity arg0, Commodity arg1) {
					if (arg0.getDate() == null || arg1.getDate() == null)
						return 0;
					return arg1.getDate().compareTo(arg0.getDate()); // 这是顺序
				}
			});
			break;
		}

		for (int i = 0; i < 15; i++)
			ac.add(goods.get(i));
		return ac;
	}

	public Object[] spendReport(String stu_id) {
		String type[] = new String[] { "全部", "衣物饰品", "食品饮料", "学习用品", "学习资料", "体育器械", "娱乐相关", "其它" };
		double num[] = new double[8];
		Object ob[] = new Object[4];
		ArrayList<Commodity_Record> goods = searchCommodityRecord(stu_id);
		double cost = 0;
		double hand1 = 0, hand2 = 0;
		for (int i = 0; i < goods.size(); i++) {
			cost += goods.get(i).getCost();
			if (goods.get(i).getMarket() == 1)
				hand1 += goods.get(i).getCost();
			else
				hand2 += goods.get(i).getCost();
			for (int j = 1; j <= 7; j++) {
				if (goods.get(i).getType().equals(type[j])) {
					num[j] += goods.get(i).getCost();
					break;
				}
			}
		}
		ob[3] = cost;
		if (hand1 > hand2)
			ob[2] = 1;
		else
			ob[2] = 2;
		double max = goods.get(0).getCost(), min = goods.get(0).getCost();
		int maxi = 1, mini = 1;
		for (int i = 0; i < 8; i++) {
			if (max < num[i]) {
				maxi = i;
				max = num[i];
			}
			if (min > num[i]) {
				mini = i;
				min = num[i];
			}
		}
		if (maxi == 0)
			maxi = 2;
		if (mini == 0)
			mini = 1;
		ob[0] = type[maxi];
		ob[1] = type[mini];
		return ob;
	}

	// 针对商品类的操作
	public ArrayList<Commodity> processArrayCommodity(Object A) {
		if (A == null)
			return null;
		ArrayList<ArrayList<String>> B = (ArrayList<ArrayList<String>>) A;
		ArrayList<Commodity> goods = new ArrayList<Commodity>();
		for (int i = 0; i < B.size(); i++) {
			String id = B.get(i).get(0);
			String type = B.get(i).get(1);
			String name = B.get(i).get(2);
			double price = Double.parseDouble(B.get(i).get(3));
			double value = Double.parseDouble(B.get(i).get(4));
			double discount = Double.parseDouble(B.get(i).get(5));
			String date = B.get(i).get(6);
			int num = Integer.parseInt(B.get(i).get(7));
			int market = Integer.parseInt(B.get(i).get(8));
			String poster = B.get(i).get(9);
			String image = B.get(i).get(10);
			Commodity tmpCo = new Commodity(id, type, name, price, value, discount, date, num, market, poster, image);
			goods.add(tmpCo);
		}
		return goods;
	}

	// 增加一条商品信息
	public int addCommodity(Commodity comm) {

		// System.out.print(comm.toString());

		// comm.setNum(num+comm.getNum());
		// Object ob=a.request(232, true, comm);
		// System.out.print(ob);
		// int tag=(int)ob;
		// library库的原因，不能从Object cast to int
		int tag = (int) a.request(211, true,
				comm.getID() + "\n" + comm.getType() + "\n" + comm.getName() + "\n" + comm.getPrice() + "\n"
						+ comm.getValue() + "\n" + comm.getDiscount() + "\n" + comm.getDate() + "\n" + comm.getNum()
						+ "\n" + comm.getMarket() + "\n" + comm.getPoster() + "\n" + comm.getImage());
		return tag;
	}

	public int addCommodity(String id, String type, String name, double price, double value, double discount,
			String date, int num, int market, String poster, String image) {
		int tag = (int) a.request(211, true, id + "\n" + type + "\n" + name + "\n" + price + "\n" + value + "\n"
				+ discount + "\n" + date + "\n" + num + "\n" + market + "\n" + poster + "\n" + image);
		return tag;
	}

	public int changeCommodity(Commodity commodity) {
		int num = commodity.getNum();
		String id = commodity.getID();
		Commodity comm = processArrayCommodity(a.request(241, true, "commodity_id" + "\n" + id)).get(0);
//    	int tag=(int)a.request(231, true,comm.getID()+"\n"+comm.getType()+"\n"+comm.getName()+"\n"+comm.getPrice()+"\n"
//    			+comm.getValue()+"\n"+comm.getDiscount()+"\n"+comm.getDate()+"\n"+(comm.getNum()-num));
		if ((commodity.getNum() + comm.getNum()) == 0) {
			// 删除该商品
			a.request(221, true, "commodity_id" + "\n" + comm.getID());
			return 0;
		} else if ((commodity.getNum() + comm.getNum()) > 0) {
			a.request(231, true,
					comm.getID() + "\n" + comm.getType() + "\n" + comm.getName() + "\n" + comm.getPrice() + "\n"
							+ comm.getValue() + "\n" + comm.getDiscount() + "\n" + comm.getDate() + "\n"
							+ (commodity.getNum() + comm.getNum()) + "\n" + comm.getMarket() + "\n" + comm.getPoster()
							+ "\n" + comm.getImage());
			System.out.println("购买过后的件数 = " + (commodity.getNum() + comm.getNum())  );
			return 1;
		}
		/*
		241 搜索 221 删除 231 修改件数
		 */
		return -1;

	}

	public int makeDiscount(Commodity commodity) {
		double discount = commodity.getDiscount();
		String id = commodity.getID();

		Commodity comm = processArrayCommodity(a.request(241, true, "commodity_id" + "\n" + id)).get(0);
		comm.setDiscount(discount);
		int tag = (int) a.request(231, true,
				comm.getID() + "\n" + comm.getType() + "\n" + comm.getName() + "\n" + comm.getPrice() + "\n"
						+ comm.getValue() + "\n" + discount + "\n" + comm.getDate() + "\n" + comm.getNum() + "\n"
						+ comm.getMarket() + "\n" + comm.getPoster() + "\n" + comm.getImage());

		// int tag=(int)a.request(232, true, comm);

		// 同时在购物车里寻找该商品并修改
//		ArrayList<Commodity_Car> goods = processArrayCommodityCar(a.request(341, true, "commodity_id" + "\n" + id));
//		ArrayList<Commodity> goods = CommoToCommoCar(processArrayCommodity(a.request(341, true, "commodity_id" + "\n" + id));
//		for (int i = 0; i < goods.size(); i++) {
//			String id_ = goods.get(i).getID();
//			String stu_id = goods.get(i).getStu_id();
//			String stu_name = goods.get(i).getStu_name();
//			String name = goods.get(i).getName();
//			String type = goods.get(i).getType();
//			String date = goods.get(i).getDate();
//			double discount_ = goods.get(i).getDiscount();
//			double price = goods.get(i).getPrice();
//			double value = goods.get(i).getValue();
//			int market = goods.get(i).getMarket();
//			int num = goods.get(i).getNum();
//			String image = goods.get(i).getImage();
//			a.request(331, true, stu_id + "\n" + stu_name + "\n" + id_ + "\n" + type + "\n" + name + "\n" + price + "\n"
//					+ value + "\n" + discount_ + "\n" + date + "\n" + num + "\n" + market + "\n" + " " + "\n" + image);
//		}
		return tag;
	}

	public ArrayList<Commodity> searchCommodityH(int t, String market, String low, String up, String name,
			String type) {
		ArrayList<Commodity> goods = processArrayCommodity(
				a.request(245, true, low + "\n" + up + "\n" + name + "\n" + type + "\n" + market));
		if (t == 0)
			return goods;
		int num[] = new int[goods.size()];
		for (int i = 0; i < goods.size(); i++)
			num[i] = 0;
		ArrayList<Sales> sales = new ArrayList<Sales>();
		for (int i = 0; i < goods.size(); i++) {
			ArrayList<Commodity_Record> cr = processArrayCommodityRecord(
					a.request(281, true, "commodity_id" + "\n" + goods.get(i).getID()));
			for (int j = 0; j < cr.size(); j++)
				num[i] += cr.get(i).getNum();
			Sales sa = new Sales(goods.get(i), num[i]);
			sales.add(sa);
		}

		sales.sort(new Comparator<Sales>() {
			@Override
			public int compare(Sales arg0, Sales arg1) {
				if (arg1.getNum() > arg0.getNum())
					return 1;
				if (arg1.getNum() < arg0.getNum())
					return -1;
				return 0;
			}
		});
		ArrayList<Commodity> goods2 = new ArrayList<Commodity>();
		for (int i = 0; i < sales.size(); i++) // warning: goods改成sales，20210728 19：48
			goods2.add(sales.get(i).getCommodity());
		return goods2;
	}

	public ArrayList<Commodity> searchCommodity(int ty, String condition) {
		ArrayList<Commodity> goods;
		switch (ty) {
		case 1:
			goods = processArrayCommodity(a.request(241, true, "commodity_id" + "\n" + condition));
			break;
		case 2:
			goods = processArrayCommodity(a.request(241, true, "type" + "\n" + condition));
			break;
		case 3:
			goods = processArrayCommodity(a.request(241, true, "commodity_name" + "\n" + condition));
			break;
		case 4:
			goods = processArrayCommodity(a.request(241, true, "price" + "\n" + condition));
			break;
		case 5:
			goods = processArrayCommodity(a.request(241, true, "value" + "\n" + condition));
			break;
		case 6:
			goods = processArrayCommodity(a.request(241, true, "discount" + "\n" + condition));
			break;
		case 7:
			goods = processArrayCommodity(a.request(241, true, "time" + "\n" + condition));
			break;
		case 8:
			goods = processArrayCommodity(a.request(241, true, "number" + "\n" + condition));
			break;
		case 9:
			goods = processArrayCommodity(a.request(241, true, "hand" + "\n" + condition));
			break;
		case 10:
			goods = processArrayCommodity(a.request(241, true, "poster" + "\n" + condition));
			break;
		case 11:
			goods = processArrayCommodity(a.request(241, true, "image" + "\n" + condition));
			break;
		default:
			return null;
		}
		return goods;
	}

	public int deleteCommodity(String id) {
		return (int) a.request(221, true, "commodity_id" + "\n" + id);

	}

	public ArrayList<Commodity> allCommodity() {
		ArrayList<Commodity> goods;
		goods = processArrayCommodity(a.request(241, true, "null" + "\n" + "null"));
		return goods;
	}

	// 针对商品消费记录的操作
	// 消费记录的增加和查询功能,在此不多改，ui根据逻辑判断
	public ArrayList<Commodity_Record> processArrayCommodityRecord(Object A) {
		if (A == null)
			return null;
		ArrayList<ArrayList<String>> B = (ArrayList<ArrayList<String>>) A;
		ArrayList<Commodity_Record> records = new ArrayList<Commodity_Record>();
		for (int i = 0; i < B.size(); i++) {
			String stu_id = B.get(i).get(0);
			String stu_name = B.get(i).get(1);
			String comm_id = B.get(i).get(2);
			String comm_name = B.get(i).get(3);
			String type = B.get(i).get(4);
			int num = Integer.parseInt(B.get(i).get(5));
			double cost = Double.parseDouble(B.get(i).get(6));
			String date = B.get(i).get(7);
			int market = Integer.parseInt(B.get(i).get(8));
			Commodity_Record tmpCo = new Commodity_Record(stu_id, stu_name, comm_id, comm_name, type, num, cost, date,
					market);
			records.add(tmpCo);
		}
		return records;
	}

	public int addCommodityRecord(Commodity_Record cr) {
		return (int) a.request(261, true, cr);
	}

	public int addCommodityRecord(String stu_id, String stu_name, String comm_id, String comm_name, String type,
			int num, double cost, String date, int market) {

		return (int) a.request(261, true, stu_id + "\n" + stu_name + "\n" + comm_id + "\n" + comm_name + "\n" + type
				+ "\n" + num + "\n" + cost + "\n" + date + "\n" + market);
	}

	public ArrayList<Commodity_Record> searchCommodityRecord(String stu_id) {
		ArrayList<Commodity_Record> cr = processArrayCommodityRecord(a.request(271, true, stu_id));
		cr.sort(new Comparator<Commodity_Record>() {
			@Override
			public int compare(Commodity_Record arg0, Commodity_Record arg1) {
				if (arg0.getDate() == null || arg1.getDate() == null)
					return 0;
				return arg1.getDate().compareTo(arg0.getDate()); // 这是顺序
			}
		});
		return cr;
	}

	public ArrayList<Commodity_Record> allCommodityRecord() {
		ArrayList<Commodity_Record> records;
		records = processArrayCommodityRecord(a.request(291, true, "null"));
		return records;
	}

	// 针对购物车的记录
	// 增删改查

	// 处理二维数组并组织成为 购物车对象数组
	public ArrayList<Commodity_Car> processArrayCommodityCar(Object A) {
		if (A == null)
			return null;
		ArrayList<ArrayList<String>> B = (ArrayList<ArrayList<String>>) A;
		ArrayList<Commodity_Car> goods = new ArrayList<Commodity_Car>();
		for (int i = 0; i < B.size(); i++) {
			String stu_id = B.get(i).get(0);
			String stu_name = B.get(i).get(1);
			String id = B.get(i).get(2);
			String type = B.get(i).get(3);
			String name = B.get(i).get(4);
			double price = Double.parseDouble(B.get(i).get(5));
			double value = Double.parseDouble(B.get(i).get(6));
			double discount = Double.parseDouble(B.get(i).get(7));
			String date = B.get(i).get(8);
			int num = Integer.parseInt(B.get(i).get(9));
			int market = Integer.parseInt(B.get(i).get(10));
			String image = B.get(i).get(11);
			Commodity_Car tmpCC = new Commodity_Car(stu_id, stu_name, id, type, name, price, value, discount, date, num,
					market, image);
			goods.add(tmpCC);
		}
		return goods;
	}

	public ArrayList<Commodity_Car> CommoToCommoCar(String stu_id, ArrayList<Commodity> B){
		ArrayList<Commodity_Car> goods = new ArrayList<Commodity_Car>();
		for(int i = 0; i < B.size(); i++){
			goods.add(new Commodity_Car(stu_id, B.get(i)));
		}
		return goods;
	}

	// 先搜商品，再添加购物车
	public int addCommodityCar(String stu_id, String stu_name, Commodity commodity) {
		int num = commodity.getNum();
		String id = commodity.getID();

		// 确定商品存在否则不操作
		Commodity comm = processArrayCommodity(a.request(241, true, "commodity_id" + "\n" + id)).get(0);
		if (comm == null) {
			a.request(321, true, stu_id + "\n" + id);// 删除购物车中的
			return -1;
		}
		// System.out.print(comm.toString());

		// comm.setNum(num+comm.getNum());
		// Object ob=a.request(232, true, comm);
		// System.out.print(ob);
		// int tag=(int)ob;
		// library库的原因，不能从Object cast to int
		int tag = (int) a.request(311, true,
				stu_id + "\n" + stu_name + "\n" + comm.getID() + "\n" + comm.getType() + "\n" + comm.getName() + "\n"
						+ comm.getPrice() + "\n" + comm.getValue() + "\n" + comm.getDiscount() + "\n" + comm.getDate()
						+ "\n" + (comm.getNum() + num) + "\n" + comm.getMarket() + "\n" + comm.getImage());
		return tag;
	}

	// 先搜商品，再添加购物车
	public int minusCommodityCar(String stu_id, String stu_name, Commodity commodity) {
		int num = commodity.getNum();
		String id = commodity.getID();

		Commodity comm = processArrayCommodity(a.request(241, true, "commodity_id" + "\n" + id)).get(0);
		// System.out.print(comm.toString());

		// comm.setNum(num+comm.getNum());
		// Object ob=a.request(232, true, comm);
		// System.out.print(ob);
//   	int tag=(int)ob;
		// library库的原因，不能从Object cast to int
		int tag;
		if (commodity.getNum() <= comm.getNum()) {
			tag = (int) a.request(331, true,
					stu_id + "\n" + stu_name + "\n" + comm.getID() + "\n" + comm.getType() + "\n" + comm.getName()
							+ "\n" + comm.getPrice() + "\n" + comm.getValue() + "\n" + comm.getDiscount() + "\n"
							+ comm.getDate() + "\n" + (comm.getNum() + num) + "\n" + comm.getMarket() + "\n"
							+ comm.getImage());
		} else
			return -1;

		// 处理商品
		if ((commodity.getNum() + comm.getNum()) == 0) {
			// 删除商品
			a.request(321, true, stu_id + "\n" + id);
			return 0;
		} else
			return 1;// 购买商品后仍有
	}

	// 删除某一行数据
	public int deleteCommodityCar(String stu_id, String comm_id) {
		return (int) a.request(321, true, stu_id + "\n" + comm_id);
	}

	// 搜索某一学生的加购记录
	public ArrayList<Commodity_Car> searchCommodityCar(String stu_id) {
		ArrayList<Commodity_Car> goods;
//		goods = processArrayCommodityCar(a.request(341, true, "id" + "\n" + stu_id));
		goods = CommoToCommoCar(stu_id, processArrayCommodity(a.request(341, true, "id" + "\n" + stu_id)));
		return goods;
	}

	// 把所有购物车的信息返回
	public ArrayList<Commodity_Car> allCommodityCar() {
		ArrayList<Commodity_Car> goods;
		goods = processArrayCommodityCar(a.request(341, true, "null" + "\n" + "null"));
//		processArrayCommodity
		return goods;
	}

	// [end]

	// [start], 教务系统
	/**
	 * 添加课程
	 * 
	 * @param A
	 */
	public int add_course(Course A) {
		// 取到课程名 ID 教师ID 周几 开始时间 结束时间 必修选修 最大选课人数 已选人数
		String c_ID = A.getCourseID();
		String name = A.getName();
		String teacher = A.getTeacher();
		String date = A.getDate() + "";
		String start = A.getStart_t() + "";
		String end = A.getEnd_t() + "";
		String date2 = A.getDate_2() + "";
		String start2 = A.getStart_t2() + "";
		String end2 = A.getEnd_t2() + "";
		String maxnum = A.getMax_num() + "";
		String count = A.getCount() + "";
		String status = A.getStatus() + "";
		String classroom = A.getClassroom();
		// 周节次
		int num = 0;
		if (A.getDate_2() != 0) {
			num = 2;
		} else {
			num = 1;
		}
		String n = num + "";

		// String
		// 为当前课程分配教室（假设我们拥有一定数量的教室，已经在数据库中添加完毕）
		// 拿到当前所有教室的列表

		// 遍历列表 检查时间是否冲突 若不冲突 则安排-》修改开始时间 结束时间 周几

		// 遍历完毕 还未找到合适的教室-》新开一间教室安排当前课程

		// 添加完毕 更改当前教师相关信息
		if (num == 2)
			a.request(511, true, c_ID + "\n" + name + "\n" + teacher + "\n" + date2 + "\n" + start2 + "\n" + end2 + "\n"
					+ status + "\n" + count + "\n" + maxnum + "\n" + classroom + "\n" + n);

		a.request(525, true, teacher + "\n" + c_ID);
		return (int) a.request(511, true, c_ID + "\n" + name + "\n" + teacher + "\n" + date + "\n" + start + "\n" + end
				+ "\n" + status + "\n" + count + "\n" + maxnum + "\n" + classroom + "\n" + n);

	}

	/**
	 * 删除课程
	 * 
	 * @param ways   按课程号 或者 课程名称
	 * @param course 具体参数
	 */
	public int delete_course(String ways, String course) {
		a.request(526, true, course);
		return (int) a.request(512, true, ways + "\n" + course);

	}

	/**
	 * 登记分数
	 * 
	 * @param
	 * @param
	 */
	public int registration_score(String s_id, String c_id, double score) {
		String sscore = "" + score;
		return (int) a.request(611, true, s_id + "\n" + c_id + "\n" + sscore);
	}

	/**
	 * 选择课程
	 * 
	 * @param c_id 课程号
	 * @param s_id 学生一卡通号
	 */
	public int choose_course(String c_id, String s_id) {
		// 根据学生ID 拿到课程id
		ArrayList<ArrayList<String>> B = (ArrayList<ArrayList<String>>) a.request(523, true, "id" + "\n" + s_id);
		// ArrayList<ArrayList<String>> C=new ArrayList<ArrayList<String>>();
		String temp = "";
		String[] C = new String[B.size()];
		for (int i = 0; i < B.size(); i++) {
			C[i] = B.get(i).get(1);
			if (i < B.size() - 1) {
				temp = temp + C[i] + "\n";
			} else {
				temp = temp + C[i];
			}
		}
		// 根据课程id 拿到学生课程表-》周几 开始时间 结束时间
		B = (ArrayList<ArrayList<String>>) a.request(522, true, temp);
		System.out.println(B);
		ArrayList<ArrayList<String>> D = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < B.size(); i++) {
			ArrayList<String> t = new ArrayList<String>();
			String date = B.get(i).get(3);
			String start = B.get(i).get(4);
			String end = B.get(i).get(5);
			t.add(date);
			t.add(start);
			t.add(end);
			D.add(t);
		}
		// 根据输入的课程ID 拿到当前欲选课程的周几 开始时间 结束时间 已选人数 课程最大容量
		B = (ArrayList<ArrayList<String>>) a.request(521, true, "course_id" + "\n" + c_id);
		ArrayList<ArrayList<String>> E = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < B.size(); i++) {
			ArrayList<String> t = new ArrayList<String>();
			String date = B.get(i).get(3);
			String start = B.get(i).get(4);
			String end = B.get(i).get(5);
			// String now_number=B.get(i).get(7);
			// String max_num=B.get(i).get(8);
			t.add(date);
			t.add(start);
			t.add(end);
			// t.add(now_number);
			// t.add(max_num);
			E.add(t);
		}

		// 判断时间是否冲突以及当前课程是否选满 若不冲突 可以选课 修改课表以及当前课容量
		boolean flag = true;
		for (int i = 0; i < E.size(); i++) {
			for (int j = 0; j < D.size(); j++) {
				if (E.get(i).get(0) == D.get(j).get(0)) {
					int a = Integer.parseInt(E.get(i).get(1));// 预选课程开始时间
					int b = Integer.parseInt(E.get(i).get(2));// 预选课程结束时间
					int c = Integer.parseInt(D.get(j).get(1));// 学生已选课程开始时间
					int d = Integer.parseInt(D.get(j).get(2));// 学生已选课程结束时间
					if ((a > c && a < d) || (b < d && b > c)) {
						flag = false;
					}
				}
			}
		}
		if (flag == true) {

			return (int) a.request(514, true, s_id + "\n" + c_id);
		}
		// 若冲突 返回错误信息
		else {
			return -1;
		}

	}

	/**
	 * 查询学生成绩
	 * 
	 * @param s_id 学生ID
	 * @param c_id 课程ID
	 * @return 当前课程的成绩
	 */
	public String search_score(String s_id, String c_id) {// 学生成绩查询

		String score = a.request(615, true, s_id + "\n" + c_id).toString();
		System.out.println(score);
		return score;

	}

	/**
	 * 按课程名称搜索
	 * 
	 * @param name
	 * @param state    必修选修 0无约束 1必修 2选修
	 * @param full     是否已满 0无约束 1未满 2已满
	 * @param conflict 是否冲突 0无约束 1不冲 2冲突
	 * @return
	 */
	public ArrayList<Course> search_course(String name, String s_id, int state, int full, int conflict) {
		ArrayList<Course> C = new ArrayList<Course>();
		ArrayList<ArrayList<String>> B = new ArrayList<ArrayList<String>>();

		String s = state + "";
		String f = full + "";
		B = (ArrayList<ArrayList<String>>) a.request(524, true, name + "\n" + s + "\n" + f);
		System.out.println(B);
		String id = "";
		String temp_name;
		String teacher;
		String classroom;
		int num = 0;
		int date = 0, date2 = 0, start = 0, start2 = 0, end = 0, end2 = 0;
		int now_num;
		int max_num;
		int status;

		for (int i = 0; i < B.size(); i++) {

			id = B.get(i).get(0);
			num = Integer.parseInt(B.get(i).get(10));
			teacher = B.get(i).get(2);
			temp_name = B.get(i).get(1);
			// date,date2,start1,start2,end1,end2;

			date = Integer.parseInt(B.get(i).get(3));
			start = Integer.parseInt(B.get(i).get(4));
			end = Integer.parseInt(B.get(i).get(5));
			if (num == 2) {
				System.out.println(temp_name + "有第二节课");
				date2 = Integer.parseInt(B.get(++i).get(3));
				start2 = Integer.parseInt(B.get(i).get(4));
				end2 = Integer.parseInt(B.get(i).get(5));
			} else {
				System.out.println(temp_name + "没有第二节课");
				date2 = 0;
				start2 = 0;
				end2 = 0;
			}
			status = Integer.parseInt(B.get(i).get(6));
			now_num = Integer.parseInt(B.get(i).get(7));
			max_num = Integer.parseInt(B.get(i).get(8));
			classroom = B.get(i).get(9);

			Course course = new Course(temp_name, id, teacher, date, start, end, date2, start2, end2, status, max_num,
					now_num, classroom);
			C.add(course);

		}
		// 根据学生ID 拿到课程id
		ArrayList<ArrayList<String>> X = (ArrayList<ArrayList<String>>) a.request(523, true, "id" + "\n" + s_id);
		String temp = "";
		String[] Y = new String[X.size()];
		for (int i = 0; i < X.size(); i++) {
			Y[i] = X.get(i).get(1);
			if (i < X.size() - 1) {
				temp = temp + Y[i] + "\n";
			} else {
				temp = temp + Y[i];
			}
		}
		// 根据课程id 拿到学生课程表-》周几 开始时间 结束时间
		X = (ArrayList<ArrayList<String>>) a.request(522, true, temp);
		ArrayList<ArrayList<String>> D = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < X.size(); i++) {
			ArrayList<String> t = new ArrayList<String>();
			String d = X.get(i).get(3);// 周几
			String st = X.get(i).get(4);// 开始时间
			String et = X.get(i).get(5);// 结束时间
			t.add(d);
			t.add(st);
			t.add(et);
			D.add(t);
		}

		// 根据拿到的课程列表 拿到当前即将展示课程的周几 开始时间 结束时间 已选人数 课程最大容量
		// B = (ArrayList<ArrayList<String>>) a.request(521, true, "course_id" + "\n" +
		// c_id);
		ArrayList<ArrayList<String>> E = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < B.size(); i++) {
			ArrayList<String> t = new ArrayList<String>();
			String da = B.get(i).get(3);
			String st = B.get(i).get(4);
			String en = B.get(i).get(5);
			String na = B.get(i).get(0);
			// String now_number=B.get(i).get(7);
			// String max_num=B.get(i).get(8);
			t.add(da);
			t.add(st);
			t.add(en);
			t.add(na);
			// t.add(now_number);
			// t.add(max_num);
			E.add(t);
		}

		if (conflict == 1) {
			// 判断时间是否冲突以及当前课程是否选满 若不冲突 可以显示
			for (int i = 0; i < E.size(); i++) {
				for (int j = 0; j < D.size(); j++) {
					if (E.get(i).get(0) == D.get(j).get(0)) {
						int a = Integer.parseInt(E.get(i).get(1));// 预选课程开始时间
						int b = Integer.parseInt(E.get(i).get(2));// 预选课程结束时间
						int c = Integer.parseInt(D.get(j).get(1));// 学生已选课程开始时间
						int d = Integer.parseInt(D.get(j).get(2));// 学生已选课程结束时间
						// 冲突 则删除
						if ((a > c && a < d) || (b < d && b > c)) {
							for (int k = 0; k < C.size(); k++) {
								if (C.get(i).getCourseID() == E.get(i).get(3)) {
									C.remove(i);
								}
							}
						}
					}
				}
			}
		} else if (conflict == 2) {
			for (int i = 0; i < E.size(); i++) {
				for (int j = 0; j < D.size(); j++) {
					if (E.get(i).get(0) == D.get(j).get(0)) {
						int a = Integer.parseInt(E.get(i).get(1));// 预选课程开始时间
						int b = Integer.parseInt(E.get(i).get(2));// 预选课程结束时间
						int c = Integer.parseInt(D.get(j).get(1));// 学生已选课程开始时间
						int d = Integer.parseInt(D.get(j).get(2));// 学生已选课程结束时间
						// 不冲突 则删除
						if (!((a > c && a < d) || (b < d && b > c))) {
							for (int k = 0; k < C.size(); k++) {
								if (C.get(i).getCourseID() == E.get(i).get(3)) {
									C.remove(i);
								}
							}
						}
					}
				}
			}

		}

		return C;
	}

	/**
	 * 查看当前学生的所选课程
	 * 
	 * @param s_id 学生id
	 * @return 当前学生所选课程的列表
	 */
	public ArrayList<Course> all_course(String s_id) {
		//
		ArrayList<ArrayList<String>> B = (ArrayList<ArrayList<String>>) a.request(523, true, "id" + "\n" + s_id);
		System.out.println(B);
		String course_list = "";
		for (int i = 0; i < B.size(); i++) {
			if (i < B.size() - 1) {
				course_list = course_list + B.get(i).get(1) + "\n";
			} else {
				course_list = course_list + B.get(i).get(1);
			}
		}
		System.out.println(course_list);
		B = (ArrayList<ArrayList<String>>) a.request(522, true, course_list);
		System.out.println(B);

		ArrayList<Course> C = new ArrayList<Course>();
		String id = "";
		String temp_name = "";
		String teacher;
		String classroom;
		int num = 0;
		int date = 0, date2 = 0, start = 0, start2 = 0, end = 0, end2 = 0;
		int now_num;
		int max_num;
		int status;
		for (int i = 0; i < B.size(); i++) {
			String temp = id;
			id = B.get(i).get(0);
			num = Integer.parseInt(B.get(i).get(10));
			teacher = B.get(i).get(2);
			temp_name = B.get(i).get(1);
			// date,date2,start1,start2,end1,end2;

			date = Integer.parseInt(B.get(i).get(3));
			start = Integer.parseInt(B.get(i).get(4));
			end = Integer.parseInt(B.get(i).get(5));
			if (num == 2) {
				date2 = Integer.parseInt(B.get(++i).get(3));
				start2 = Integer.parseInt(B.get(i).get(4));
				end2 = Integer.parseInt(B.get(i).get(5));
			}
			status = Integer.parseInt(B.get(i).get(6));
			now_num = Integer.parseInt(B.get(i).get(7));
			max_num = Integer.parseInt(B.get(i).get(8));
			classroom = B.get(i).get(9);

			Course course = new Course(temp_name, id, teacher, date, start, end, date2, start2, end2, status, max_num,
					now_num, classroom);
			C.add(course);

		}
		return C;
	}

	/**
	 * 查看选择当前课程的人名单
	 *
	 * @param c_id
	 * @return
	 */
	public Object[][] show_list(String c_id) {

		// 根据课程号拿到一卡通号
		ArrayList<ArrayList<String>> B = (ArrayList<ArrayList<String>>) a.request(523, true, "course_id" + "\n" + c_id);
		System.out.println(B);
		Object[][] C = new Object[B.size()-1][2];

		// 所有人的 id
		ArrayList<String> D = new ArrayList<String>();
		for (int i = 0; i < B.size(); i++) {
			String id = B.get(i).get(0);
			D.add(id);
		}

		// 根据ID拿名字
		ArrayList<String> E = new ArrayList<String>();
		for (int i = 0; i < B.size(); i++) {
			ArrayList<ArrayList<String>> F = (ArrayList<ArrayList<String>>) a.request(41, true, "id" + "\n" + B.get(i).get(0));
			System.out.println("选课学生姓名：" + F.get(0).get(3));
			if (F.get(0).get(4).equals("4")) {
				D.remove(F.get(0).get(0));
			} else {
				String name = F.get(0).get(3);
				System.out.println("选课学生姓名：" + name);
				E.add(name);
			}
		}

		B = (ArrayList<ArrayList<String>>) a.request(523, true, "course_id" + "\n" + c_id);
		for (int i = 0; i < B.size() - 1; i++) {
			C[i][0] = D.get(i);
			C[i][1] = E.get(i);
		}

		return C;
	}


	public AlipayInfoOutDTO create_alipay_order(AlipayInfoInDTO aii){
		AlipayInfoOutDTO aio= (AlipayInfoOutDTO) a.request(710, true, aii);
		return aio;
	}

	// [end]
	public static void main(String[] args) {
		PassData pd = new PassData();
		pd.borrow_book("213191246", "A11", "天空之城", new Date());
		System.out.println("ok");
	}

	/**
	 * 学生退课
	 *
	 * @param account_number
	 * @param courseID
	 * @return
	 */
	public int quit_course(String account_number, String courseID) {
		return Integer.parseInt(a.request(527, true, account_number + "\n" + courseID).toString());
	}
}
