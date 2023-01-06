package cn.seu.domain.courses;

public class Score_report {
    private String account_number;
    private String courseID;
    private int score;//成绩
    public Score_report(String account_number,String courseID,int score)
    {
        this.account_number=account_number;
        this.courseID=courseID;
        this.score=score;
    }
    public String toString(){
        return account_number+"\n"+courseID+"\n"+Integer.toString(score);
    }
}
