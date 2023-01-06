package cn.seu.socket3;

import java.net.*;
import java.io.*;
//引入数据库
import cn.seu.Impl.*;
import cn.seu.domain.personnel.*;
import cn.seu.domain.library.*;
import cn.seu.domain.store.*;

//创建服务器
public class Server implements Serializable {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		try {
//			System.out.println("Socket is running");
//			ServerSocket serverSocket = new ServerSocket(9999);
//			// 处理多个Client端
//			while (true) {
//				Socket socket = serverSocket.accept();// 接收客户的请求
//				new Thread(new Server_listen(socket)).start();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}

// 监听Client的请求(get message)
class Server_listen implements Runnable, Serializable {
	private Socket socket;

	Server_listen(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			// ObjectInputStream 反序列化流，将之前使用 ObjectOutputStream 序列化的原始数据恢复为对象，以流的方式读取对象。
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			while (true) {
				Object ob = ois.readObject();
				Message message = (Message) ob;
				int type = message.getType();
				boolean state = message.getState();
				Object data = message.getData();

				Server_process a = new Server_process(socket, type, state, data);
				Thread thread = new Thread(a);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {// 若Client socket断开，则服务器关闭连接
			try {
				socket.close();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
	}
}

class Server_process implements Runnable {
	private Socket socket;
	private int type;
	public Object data;
	private boolean state;
	public Message message;

	Server_process(Socket socket, int type, boolean state, Object data) {
		this.socket = socket;
		this.type = type;
		this.data = data;
		this.state = state;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			// 引用数据库
			LogInImpl ll = new LogInImpl();
			BooksImpl bl = new BooksImpl();
			StoreImpl sl = new StoreImpl();
			ShoppingCarImpl scl = new ShoppingCarImpl();
			RecordImpl rl = new RecordImpl();
			CourseImpl cl = new CourseImpl();
			GradeImpl gl = new GradeImpl();

			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			Object object = null;
			Message ans;
			Student stu;
			Teacher tea;
			Librarian lib;
			Store_Manager st;
			Academic_affairs_teacher ac;
			Book_borrowing_record br;
			Book book;
			if (state) {
				switch (type) {
					// 针对每个case 调用不同的函数 并且作出不同的回应
					case 1:// 登录
						object = ll.isLogIn(data.toString().split("\n")[0], data.toString().split("\n")[1],
								data.toString().split("\n")[2]);
						break;
					case 11:// 加
						object = ll.addUser(data.toString().split("\n"));
						break;
					case 12:
						if (data instanceof Student) {
							stu = (Student) data;
							object = ll.addUser(stu.toString().split("\n"));
						}
						break;
					case 13:
						if (data instanceof Teacher) {
							tea = (Teacher) data;
							object = ll.addUser(tea.toString().split("\n"));
						}
						break;
					case 14:
						if (data instanceof Librarian) {
							lib = (Librarian) data;
							object = ll.addUser(lib.toString().split("\n"));
						}
						break;
					case 15:
						if (data instanceof Store_Manager) {
							st = (Store_Manager) data;
							object = ll.addUser(st.toString().split("\n"));
						}
						break;
					case 16:
						if (data instanceof Academic_affairs_teacher) {
							ac = (Academic_affairs_teacher) data;
							object = ll.addUser(ac.toString().split("\n"));
						}
						break;
					case 21:
						object = ll.deleteUser(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;

					case 31:// 加
						object = ll.updateUser(data.toString().split("\n"));
						break;
					case 32:
						if (data instanceof Student) {
							stu = (Student) data;
							object = ll.updateUser(stu.toString().split("\n"));
						}
						break;
					case 33:
						if (data instanceof Teacher) {
							tea = (Teacher) data;
							object = ll.updateUser(tea.toString().split("\n"));
						}
						break;
					case 34:
						if (data instanceof Librarian) {
							lib = (Librarian) data;
							object = ll.updateUser(lib.toString().split("\n"));
						}
						break;
					case 35:
						if (data instanceof Store_Manager) {
							st = (Store_Manager) data;
							object = ll.updateUser(st.toString().split("\n"));
						}
						break;
					case 36:
						if (data instanceof Academic_affairs_teacher) {
							ac = (Academic_affairs_teacher) data;
							object = ll.updateUser(ac.toString().split("\n"));
						}
						break;
					case 41:
						object = ll.searchOneUser(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 51:
						object = ll.searchAllUser(data.toString());
						break;
					case 61:
						object = ll.updateMoney(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 71:
						object = ll.updateNetFee(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 81:
						object = ll.updatePhoto(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 111:
						object = bl.addUser(data.toString().split("\n"));
						break;
					case 112:
						if (data instanceof Book) {
							book = (Book) data;
							object = bl.addUser(book.toString().split("\n"));
						}
						break;
					case 121:
						object = bl.deleteUser(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 131:
						object = bl.updateUser(data.toString().split("\n"));
						break;
					case 132:
						if (data instanceof Book) {
							book = (Book) data;
							object = bl.updateUser(book.toString().split("\n"));
						}
						break;
					case 141:
						object = bl.searchOneUser(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 145:
						object = bl.searchOneUser(data.toString().split("\n")[0], data.toString().split("\n")[1],
								data.toString().split("\n")[2]);
						break;
					case 151:
						object = bl.searchAllUser(data.toString());
						break;
					case 161:
						object = bl.borrowBook(data.toString().split("\n"));
						break;
					case 162:
						if (data instanceof Book_borrowing_record) {
							br = (Book_borrowing_record) data;
							object = bl.borrowBook(br.toString().split("\n"));
						}
						break;
					case 171:
						object = bl.returnBook(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 181:
						object = bl.borrowRecord(Integer.parseInt(data.toString()));
						break;
					case 191:
						object = bl.bookList_notNull(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 211:
						object = sl.addUser(data.toString().split("\n"));
						break;
					case 212:
						// if (data instanceof Commodity) {
						// commodity = (Commodity) data;
						// object = sl.addUser(commodity.toString().split("\n"));
						// }
						break;
					case 221:
						object = sl.deleteUser(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 231:
						object = sl.updateUser(data.toString().split("\n"));
						break;
					case 232:
						// if (data instanceof Commodity) {
						// commodity = (Commodity) data;
						// object = sl.updateUser(commodity.toString().split("\n"));
						// }
						break;
					case 241:
						object = sl.searchOneUser(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 245:
						object = sl.searchOneUser(data.toString().split("\n")[0], data.toString().split("\n")[1],
								data.toString().split("\n")[2], data.toString().split("\n")[3], data.toString().split("\n")[4]);
						break;
					case 251:
						object = sl.searchAllUser(data.toString());
						break;
					case 261:
						object = sl.addConsumption(data.toString().split("\n"));
						break;
					case 262:
						// if (data instanceof Commodity_Record) {
						// cr = (Commodity_Record) data;
						// object = sl.updateUser(cr.toString().split("\n"));
						// }
						break;
					case 271:
						object = sl.searchAllRecord(data.toString());
						break;
					case 281:
						object = sl.searchCommodity(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 291:
						break;
					case 311:
						object = scl.addUser(data.toString().split("\n"));
						break;
					case 321:// 根据一卡通号删商品
						object = scl.deleteUser(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 331:
						object = scl.updateUser(data.toString().split("\n"));
						break;
					case 341:
						object = scl.searchOneUser(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 345:// 多条件搜索
						object = scl.searchOneUser(data.toString().split("\n")[0], data.toString().split("\n")[1],
								data.toString().split("\n")[2], data.toString().split("\n")[3], data.toString().split("\n")[4]);
						break;
					case 411:
						object = rl.addUser(data.toString().split("\n"));
						break;
					case 412:
						object = rl.searchOneUser(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 511:
						object = cl.addUser(data.toString().split("\n"));
						break;
					case 512:
						object = cl.deleteUser(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 513:
						object = cl.updateUser(data.toString().split("\n"));
						break;
					case 514:
						object = cl.addCourse(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 515:
						object = cl.deleteCourse(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 521:
						object = cl.searchOneUser(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 522:
						object = cl.searchCourse(data.toString().split("\n"));
						break;
					case 523:
						object = cl.searchPrivateCourse(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 524:
						object = cl.searchOneUser(data.toString().split("\n")[0], data.toString().split("\n")[1],
								data.toString().split("\n")[2]);
						break;
					case 525:
						object = cl.addTeacherCourse(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 526:
						object = cl.deleteTeacherCourse(data.toString());
						break;
					case 611:
						object = gl.addUser(data.toString().split("\n"));
						break;
					case 612:
						object = gl.deleteUser(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 613:
						object = gl.updateUser(data.toString().split("\n"));
						break;
					case 614:
						object = gl.searchOneUser(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					case 615:
						object = gl.searchGrade(data.toString().split("\n")[0], data.toString().split("\n")[1]);
						break;
					default:
						break;
				}
				ans = new Message(type, true, object);// 若是正确信息，传true否则false
				oos.writeObject(ans);
				oos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}