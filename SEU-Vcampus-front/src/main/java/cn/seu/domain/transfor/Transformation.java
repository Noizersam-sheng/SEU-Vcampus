package cn.seu.domain.transfor;

import java.util.ArrayList;

public class Transformation {
    private ArrayList<ArrayList<String>> data;
    private int tag;
    public Transformation(ArrayList<ArrayList<String>> data,int tag)
    {
        this.data=data;
        this.tag=tag;
    }
    public int gettag(){return tag;}
    public ArrayList<ArrayList<String>> getdata(){return  data;}
}
