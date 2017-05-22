package com.djurus.djurus.saiyan;

import java.util.Date;

/**
 * Created by djurus on 7/8/16.
 */
public class Quest {
    private String questname;
    private String category;
    private int count;
    private int maxscore;
    private int score;
    private Date dateCompleted;
    public Quest(String questname,String category){
        this.questname=questname;
        this.category=category;
    }
    public String getQuestName(){
        return questname;
    }
    public String getCategory(){
        return category;
    }
    public int getCount(){
        return count;
    }
    public void incrementCount(){
        count++;
    }
    public void setDatecompleted(Date date){
        dateCompleted=date;
    }
    public Date getDateCompleted(){
        return dateCompleted;
    }
    public void setMaxscore(int score){
        maxscore=score;
    }
    public int getMaxscore(){
        return maxscore;
    }
    public void setScore(int questscore){
        score=questscore;
    }
    public int getScore(){
        return score;
    }
}
