package cn.seu.domain.store;

import java.io.Serializable;

public class Commodity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private double price;
	private double value;
	private String id;
	private double discount;
	private String type;
	private String date;// 到什么时候过保质期
	private int num;
	private int market;
	private String poster;
	private String image;

	public Commodity(String id, String type, String name, double price, double value, double discount, String date,
			int num, int market, String poster, String image) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.value = value;
		this.discount = discount;
		this.date = date;
		this.type = type;
		this.num = num;
		this.market = market;
		this.poster = poster;
		this.image = image;
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

	public String getPoster() {
		return poster;
	}

	public String getImage() {
		return image;
	}

	@Override
	public String toString() {
		return id + "\n" + type + "\n" + name + "\n" + price + "\n" + value + "\n" + discount + "\n" + date + "\n" + num
				+ "\n" + market;
	}

}