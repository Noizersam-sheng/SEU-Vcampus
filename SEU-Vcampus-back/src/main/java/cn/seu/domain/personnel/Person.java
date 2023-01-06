package cn.seu.domain.personnel;
import java.io.*;
public class Person implements Serializable{
    public int way;
    public String account_number;//账号
    private String password;//密码
    private int secret=8;//加密
    private int jurisdiction;//权限

    public String getpassword() {
        return password;
    }

    public void setaccount_number(String account_number){
        this.account_number=account_number;
    }
    public void setpassword(String password){
        byte[] bt=password.getBytes();
        for(int i=0;i<bt.length;i++){
            bt[i]=(byte)(bt[i]^(int)secret); //通过异或运算进行加密
        }
        String newresult=new String(bt,0,bt.length);
        this.password=newresult;
    }
    /*public String getpassword(){
        return  password;
    }*/
    public void setjurisdiction(int jurisdiction){
        this.jurisdiction=jurisdiction;
    }
    public int getJurisdiction()
    {
        return jurisdiction;
    }


    /*public static void main(String[] args) {
        Person A=new Person();
        A.setpassword("123456");
        System.out.print(A.getpassword());
    }*/
}
