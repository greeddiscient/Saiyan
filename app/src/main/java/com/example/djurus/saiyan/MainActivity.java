package com.example.djurus.saiyan;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ListActivity {
    private ArrayList<Quest> questlist= new ArrayList<Quest>();
    private MySimpleArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout questcontainer = (LinearLayout)findViewById(R.id.questcontainer);

        Quest quest1 = new Quest("Quest name1", "basketball");
        Quest quest2 = new Quest("Quest name2", "golf");
        Quest quest3 = new Quest("Quest name3", "weights");
        Quest quest4 = new Quest("Quest name4", "basketball");
        questlist.add(quest1);
        questlist.add(quest2);
        questlist.add(quest3);
        questlist.add(quest4);

        adapter = new MySimpleArrayAdapter(this, questlist);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MainActivity.this, QuestDetailsActivity.class);
                myIntent.putExtra("questnumber", position);
                MainActivity.this.startActivity(myIntent);
                Quest obj = (Quest)adapter.getItem(position);
                Toast.makeText(getApplicationContext(),obj.getQuestName(), Toast.LENGTH_LONG).show();
            }
        });




    }

    public void newQuest(View view)
    {
        Intent intent = new Intent(MainActivity.this, QuestActivity.class);
        startActivity(intent);
    }
}
