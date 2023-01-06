package cn.seu.socket3;

import java.net.*;
import java.io.*;

public class Client {

	private static Socket socket;

//	public static void main(String[] args) {
//		connect();
//		// request(31,true,"id"+'\n'+213191246);
//		Client a = new Client();
//		Object ob = a.request(1, true, "id" + "\n" + "213191246" + "\n" + "123456");
//		System.out.println(ob);
//		System.out.print("get connected");
//	}

	private static void connect() {
		try {
			socket = new Socket("120.46.215.180", 9999);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object request(int type, boolean state, Object data) {
		connect();
		Client_send a = new Client_send(socket, type, true, data);
		Thread thread = new Thread(a);
		thread.start();
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a.getData();
	}

	public static void disconnect() {
		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class Client_send implements Runnable {
	private Socket socket;
	private int type;
	private boolean state;
	private Object object;
	private Object data;

	Client_send(Socket socket, int type, boolean state, Object object) {
		this.socket = socket;
		this.type = type;
		this.state = state;
		this.object = object;
	}

	public Object getData() {
		return data;
	}

	private void setData(Object data) {
		this.data = data;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			if (state) {
				Message message = new Message(type, state, object);
				oos.writeObject(message);
				oos.flush();

				Client_listen b = new Client_listen(socket);
				Thread thread = new Thread(b);
				thread.start();
				try {
					thread.join();
				} catch (Exception e) {
					e.printStackTrace();
				}
				setData(b.getData());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class Client_listen implements Runnable {
	private Socket socket;
	private Object data;

	Client_listen(Socket socket) {
		this.socket = socket;
	}

	public Object getData() {
		return data;
	}

	private void setData(Object data) {
		this.data = data;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			Message ans = (Message) ois.readObject();
			setData(ans.getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}