package cn.seu.domain.courses;
public class Course {
    private String courseID;//课程号
    private String name;//课程名
    private String teacher;//授课老师
    private int date;//周几
    private int start_t,end_t;//开始时间和结束时间
    private int status;//0 必修；1 选修
    private int now_num;//已选人数
    private int max_num;//最大选课人数
    public Course(String courseID,String name,String teacher,int date,int start,int end,int status,int now_num,int max_num) {
        this.name=name;
        this.courseID=courseID;
        this.teacher=teacher;
        this.date=date;
        this.start_t=start;
        this.end_t=end;
        this.status=status;
        this.now_num=now_num;
        this.max_num=max_num;
    }
    public void setname(String name){
        this.name=name;
    }
    public void setteacher(String teacher){
        this.teacher=teacher;
    }
    public String toString()
    {
        return courseID+"\n"+name+"\n"+teacher+"\n"+Integer.toString(date)+"\n"+Integer.toString(start_t)+"\n"+Integer.toString(end_t)
                +'\n'+Integer.toString(status)+'\n'+Integer.toString(now_num)+'\n'+Integer.toString(max_num);
    }
}
