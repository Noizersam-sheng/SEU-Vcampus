package cn.seu.domain.consumption_record;

/**
 * 这是一个消费记录类 负责记录一卡通的各种消费
 * 
 * @author 顾深远
 * @version 1.0
 */

public class Consumption_record {
	private String date;// 日期
	private String account_number;
	private int ways;// 方式
	private double cost;// 花费
	private double surplus;// 余额

	/**
	 *
	 * @param date           日期
	 * @param account_number 账号
	 * @param ways           消费类型
	 * @param cost           花费
	 * @param surplus        余额
	 */
	public Consumption_record(String date, String account_number, int ways, double cost, double surplus) {
		this.account_number = account_number;
		this.date = date;
		this.cost = cost;
		this.surplus = surplus;
		this.ways = ways;
	}

	public String toString() {
		return account_number + "\n" + Integer.toString(ways) + "\n" + Double.toString(cost) + "\n"
				+ Double.toString(surplus) + "\n" + date;
	}
}
