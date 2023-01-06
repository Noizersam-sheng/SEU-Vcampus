package cn.seu.domain.personnel;

/**
 * 这是一个没啥用的登录类
 * <br>哦
 * <br>他负责加密
 * @author 顾深远
 * @version 1.0
 */
import java.io.Serializable;

public class Sign_in implements Serializable {

//	private static final long serialVersionUID = 110L;

	boolean way;
	String account_number;
	String password;

	public Sign_in(String account_number, boolean way, String password) {
		this.account_number = account_number;
		this.way = way;
		this.password = password;
	}

	@Override
	public String toString() {
		String ways;
		if (this.way) {
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
		return ways + "\n" + account_number + "\n" + newresult;
	}
}
