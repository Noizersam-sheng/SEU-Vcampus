package cn.seu.domain.personnel;

import java.util.ArrayList;

public class Student extends Person {
    public Student(String account_number,String nickname,String password,String name,int jurisdiction,int age,int gender,int year,int month,int day,String major,String college,int money){
        this.account_number=account_number;
        this.setpassword(password);
        this.setjurisdiction(5);
        this.name=name;
        this.age=age;
        this.year=year;
        this.month=month;
        this.day=day;
        this.nickname=nickname;
        this.major=major;
        this.college=college;
        this.money=money;
    }
    private int gender;//性别：女2男1
    private String name;//姓名
    private int age;//年龄
    private int year,month,day;//日期
    public String toDate(int year,int month,int day){
        String y,m,d;
        y=Integer.toString(year);
        m=Integer.toString(month);
        d=Integer.toString(day);
        return y+"-"+m+"-"+d;
    }
    public String nickname;//昵称
    private String major;//专业
    private String college;//学院
    private int money;//金额
    public ArrayList<ArrayList> books=new ArrayList<ArrayList>();
    public void setCollege(String college) {
        this.college = college;
    }
    public String getCollege() {
        return college;
    }
    public void setgender(int gender){
        this.gender=gender;
    }
    public int getgender(){return gender;}
    public void setname(String name){
        this.name=name;
    }
    public String getname(){return name;}
    public void setage(int age){
        this.age=age;
    }
    public int getage(){return age;}
    public void settime(int year,int month,int day){
        this.year=year;
        this.month=month;
        this.day=day;
    }
    public int getyear(){return year;}
    public int getmonth(){return month;}
    public int getday(){return day;}
    public void setnickname(String nickname){
        this.nickname=nickname;
    }
    public String getnickname(){return nickname;}
    public void setmajor(String major){
        this.major=major;
    }
    public String getmajor(){return major;}

    public int getmoney() {
        return money;
    }

    public String toString()
    {
        return account_number+"\n"+nickname+"\n"+getpassword()+"\n"+name+"\n"+Integer.toString(getJurisdiction())+"\n"+Integer.toString(age)+"\n"
                +Integer.toString(gender)+"\n"+toDate(year,month,day)+"\n"+major+"\n"+college+"\n"+Integer.toString(getmoney());
    }



}
