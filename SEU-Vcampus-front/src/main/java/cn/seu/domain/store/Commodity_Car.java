package cn.seu.domain.store;

import java.io.Serializable;

public class Commodity_Car implements Serializable {
	// 谁加的购物车
	// 购物车中是什么商品
	//
	private String stu_id;
	private String stu_name;
	private String name;
	private double price;
	private double value;
	private String id;
	private double discount;
	private String type;
	private String date;// 到什么时候过保质期
	private int num;
	private int market;
	private String image;

	public Commodity_Car(String stu_id, String stu_name, String id, String type, String name, double price,
			double value, double discount, String date, int num, int market, String image) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.value = value;
		this.discount = discount;
		this.date = date;
		this.type = type;
		this.num = num;
		this.market = market;
		this.stu_id = stu_id;
		this.stu_name = stu_name;
		this.image = image;
	}

	public Commodity_Car(String stu_id, Commodity commo){
		this.stu_id = stu_id;
		this.stu_name = "null_name";
		this.id = commo.getID();
		this.type = commo.getType();
		this.name = commo.getName();
		this.price = commo.getPrice();
		this.value = commo.getValue();
		this.discount = commo.getDiscount();
		this.date = commo.getDate();
		this.num = commo.getNum();
		this.market = commo.getMarket();
		this.image = commo.getImage();
	}

	public void setStu_id() {
		this.stu_id = stu_id;
	}

	public void setStu_name() {
		this.stu_name = stu_name;
	}

	public void setID(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getDate() {
		return date;
	}

	public double getDiscount() {
		return discount;
	}

	public String getID() {
		return id;
	}

	public int getNum() {
		return num;
	}

	public String getType() {
		return type;
	}

	public double getValue() {
		return value;
	}

	public int getMarket() {
		return market;
	}

	public String getStu_id() {
		return stu_id;
	}

	public String getStu_name() {
		return stu_name;
	}

	public String getImage() {
		return image;
	}

	@Override
	public String toString() {
		return stu_id + "\n" + stu_name + "\n" + id + "\n" + type + "\n" + name + "\n" + price + "\n" + value + "\n"
				+ discount + "\n" + date + "\n" + num + "\n" + market;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
