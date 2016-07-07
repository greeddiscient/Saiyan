package com.example.djurus.saiyan;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<Quest> questlist= new ArrayList<Quest>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout questcontainer = (LinearLayout)findViewById(R.id.questcontainer);

        Quest quest1 = new Quest("Quest name1", "bball");
        Quest quest2 = new Quest("Quest name2", "bball");
        Quest quest3 = new Quest("Quest name3", "bball");
        Quest quest4 = new Quest("Quest name4", "bball");
        questlist.add(quest1);
        questlist.add(quest2);
        questlist.add(quest3);
        questlist.add(quest4);

        for (int i=0;i<questlist.size();i++){
            View child = getLayoutInflater().inflate(R.layout.questview, null);
            TextView text = (TextView) child.findViewById(R.id.questname);
            text.setText(questlist.get(i).getQuestName());
            questcontainer.addView(child);
        }


    }

    public void newQuest(View view)
    {
        Intent intent = new Intent(MainActivity.this, QuestActivity.class);
        startActivity(intent);
    }
}
