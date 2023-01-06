package cn.seu.domain.personnel;

public class Sign_in {
    boolean way;
    String account_number;
    String password;
    public Sign_in(String account_number,boolean way,String password){
        this.account_number=account_number;
        this.way=way;
        this.password=password;
    }
    public String tostring(){
        String ways;
        if(this.way){
            ways="id";
        }
        else{
            ways="nickname";
        }
        int secret=8;
        byte[] bt=password.getBytes();
        for(int i=0;i<bt.length;i++){
            bt[i]=(byte)(bt[i]^(int)secret); //通过异或运算进行加密
        }
        String newresult=new String(bt,0,bt.length);
        return ways+'\n'+account_number+'\n'+newresult;
    }
}
