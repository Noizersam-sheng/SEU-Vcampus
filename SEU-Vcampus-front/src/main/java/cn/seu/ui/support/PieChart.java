package cn.seu.ui.support;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static cn.seu.ui.support.MyType.beautFont;

public class PieChart extends JPanel {
    Font font;
    int basex, basey, sizex, sizey, isinit;
    double pro1, pro2, pro3, pro4;
    String info1, info2, info3, info4;
    public JLabel labelInfo1, labelInfo2, labelInfo3, labelInfo4;

    public PieChart(int basexx, int baseyy, int sizexx, int sizeyy,
            double proportion1, String info1,
            double proportion2, String info2,
            double proportion3, String info3,
            double proportion4, String info4){
        super(null);
//        this.basex = basexx;
//        this.basey = baseyy;
        this.basex = 0;
        this.basey = -20;
        this.sizex = sizexx;
        this.sizey = sizeyy;
        this.setBounds(0, 0, sizex, sizey);
        this.pro1 = proportion1;
        this.pro2 = proportion2;
        this.pro3 = proportion3;
        this.pro4 = proportion4;
        this.info1 = info1;
        this.info2 = info2;
        this.info3 = info3;
        this.info4 = info4;
        this.font = new Font("微软雅黑", Font.PLAIN, 14);
        this.setBackground(new Color(255,255,255, 0));

        labelInfo1 = new JLabel();
        labelInfo2 = new JLabel();
        labelInfo3 = new JLabel();
        labelInfo4 = new JLabel();
        this.isinit = 0;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

//        super.remove(labelInfo1);
//        super.remove(labelInfo2);
//        super.remove(labelInfo3);
//        super.remove(labelInfo4);

        int radius = (int) (Math.min(getWidth(), getHeight() * 0.4));
        g.setColor(new Color(201, 202, 250));
        g.fillArc(getWidth() / 2 - radius, getHeight() / 2 - radius, radius * 2, radius * 2,
                0, (int) (360 * this.pro1));

        g.setColor(new Color(217, 250, 190));
        g.fillArc(getWidth() / 2 - radius, getHeight() / 2 - radius, radius * 2, radius * 2,
                (int) (360 * this.pro1), (int) (360 * this.pro2));

        g.setColor(new Color(254, 218, 173));
        g.fillArc(getWidth() / 2 - radius, getHeight() / 2 - radius, radius * 2, radius * 2,
                (int) (360 * (this.pro1 + this.pro2)), (int) (360 * this.pro3));

        g.setColor(new Color(253, 202, 216));
        g.fillArc(getWidth() / 2 - radius, getHeight() / 2 - radius, radius * 2, radius * 2,
                (int) (360 * (this.pro1 + this.pro2 + this.pro3)), (int) (360 * this.pro4));


        if(this.isinit == 0){
            this.isinit = 1;
            System.out.println("初始化 pie chart info！");

            radius = (int) (Math.min(super.getWidth(), super.getHeight() * 0.4));
            String str;
            String divider = " ";
            int leftshift = -20;

            str = this.info1 + divider + (int) (100 * this.pro1) + "%";
            labelInfo1 = new JLabel(str);
            labelInfo1.setFont(this.font);
            labelInfo1.setBounds(
                    basex + (int) (3*leftshift + super.getWidth() / 2 + radius * Math.cos(2*Math.PI * (this.pro1/2))),
                    basey + (int) (super.getHeight() / 2 - 0.6*radius * Math.sin(2*Math.PI * (this.pro1/2))),
                    200, 60
            );
            labelInfo1.setVisible(true);

            str = this.info2 + divider + (int) (100 * this.pro2) + "%";
            labelInfo2 = new JLabel(str);
            labelInfo2.setFont(this.font);
            labelInfo2.setBounds(
                    basex + (int) (leftshift + super.getWidth() / 2 + radius * Math.cos(2*Math.PI * (this.pro1 + this.pro2/2))),
                    -15+basey + (int) (super.getHeight() / 2 - 0.6*radius * Math.sin(2*Math.PI * (this.pro1 + this.pro2/2))),
                    200, 60
            );

            str = this.pro3 > 0.02? this.info3 + divider + (int) (100 * this.pro3) + "%" : "";
            labelInfo3 = new JLabel(str);
            labelInfo3.setFont(this.font);
            labelInfo3.setBounds(
                    basex + (int) (leftshift + super.getWidth() / 2 + radius * Math.cos(2*Math.PI * (this.pro1 + this.pro2 + this.pro3/2))),
                    15+basey + (int) (super.getHeight() / 2 - 0.6*radius * Math.sin(2*Math.PI * (this.pro1 + this.pro2 + this.pro3/2))),
                    200, 60
            );

            str = this.pro4 > 0.02? this.info4 + divider + (int) (100 * this.pro4) + "%" : "";
            labelInfo4 = new JLabel(str);
            labelInfo4.setFont(this.font);
            labelInfo4.setBounds(
                    basex + (int) (3*leftshift + super.getWidth() / 2 + radius * Math.cos(2*Math.PI * (this.pro1 + this.pro2 + this.pro3 + this.pro4/2))),
                    basey + (int) (super.getHeight() / 2 - 0.6*radius * Math.sin(2*Math.PI * (this.pro1 + this.pro2 + this.pro3 + this.pro4/2))),
                    200, 60
            );
            System.out.println("饼状图文字：" + str);
        }


        super.add(labelInfo1);
        super.add(labelInfo2);
        super.add(labelInfo3);
        super.add(labelInfo4);

        this.setOpaque(false);
    }

}


// 原文链接：https://blog.csdn.net/ximen250/article/details/103827093