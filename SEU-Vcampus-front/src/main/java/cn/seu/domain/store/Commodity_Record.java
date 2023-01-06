package cn.seu.domain.store;

import java.io.Serializable;

public class Commodity_Record implements Serializable{
//存储商品的消费记录
//学生每进行一次购买，添加一条数据
//需要添加数据功能和
	private String stu_id;
	private String stu_name;
	private String comm_id;
	private String comm_name;
	private String type;
	private int num;
	private double cost;
	private String date;//记录购买的时间
	private int market;
	public Commodity_Record(String stu_id,String stu_name,String comm_id,String comm_name,String type,int num,double cost,String date
			,int market){
		this.stu_id=stu_id;
		this.stu_name=stu_name;
		this.comm_id=comm_id;
		this.comm_name=comm_name;
		this.type=type;
		this.num=num;
		this.cost=cost;
		this.date=date;
		this.market=market;
	}
	public String getStu_id(){return stu_id;}
	public String getStu_name(){return stu_name;}
	public String getComm_id(){return comm_id;}
	public String getComm_name(){return comm_name;}
	public  String getType(){return type;}
	public int getNum(){return num;}
	public  double getCost(){return cost;}
	public String getDate(){return date;}
	public int getMarket() {return market;}
	@Override
	 public String toString() {
	      return  stu_id+"\n"+stu_name+"\n"+comm_id+"\n"+comm_name+"\n"+type+"\n"+num+"\n"+cost+"\n"+date;
	 }
}