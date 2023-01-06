package cn.seu.domain.personnel;
import java.io.*;
public class Teacher extends Person implements Serializable{
    public Teacher(String account_number,String nickname,String password,String name,int jurisdiction,int age,int gender,String college){
        this.account_number=account_number;
        this.setpassword(password);
        this.nickname=nickname;
        this.setjurisdiction(4);
        this.college=college;
        this.name=name;
        this.gender=gender;
        this.age=age;
    }
    private int age;
    private int gender;
    private String college;//学院
    private String name;
    private String nickname;
    public void setnickname(String nickname) {
        this.nickname = nickname;
    }


    public void setcollege(String college){
        this.college=college;
    }
    public void setname(String name){
        this.name=name;
    }
    public String toString()
    {
        return account_number+"\n"+nickname+"\n"+getpassword()+"\n"+name+"\n"+Integer.toString(getJurisdiction())+"\n"+Integer.toString(age)+"\n"+Integer.toString(gender)+"\n"+"null\n"+"null\n"+college;
    }

}
