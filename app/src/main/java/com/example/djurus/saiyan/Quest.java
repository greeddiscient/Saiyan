package com.example.djurus.saiyan;

import java.util.Date;

/**
 * Created by djurus on 7/8/16.
 */
public class Quest {
    private String questname;
    private String category;
    private int count;
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
}
