package com.example.djurus.saiyan;

/**
 * Created by djurus on 7/8/16.
 */
public class Quest {
    private String questname;
    private String category;
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
}
