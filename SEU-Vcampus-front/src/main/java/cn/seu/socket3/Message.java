package cn.seu.socket3;

import java.io.Serializable;

public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type;
	private boolean state;
	private Object data;
	
	Message(int type,boolean state,Object data){
		this.type=type;
		this.state=state;
		this.data=data;
	}
	
	//
	@Override
	 public String toString() {
	      return  Integer.toString(type)+'\n'+String.valueOf(state)+'\n'+data.toString();
	  }
	public int getType() {
		return type;
	}
	public boolean getState() {
		return state;
	}
	public Object getData() {
		return data;
	}
}
