package com.example.djurus.saiyan;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends ListActivity {
    private ArrayList<Quest> questlist= new ArrayList<Quest>();
    private MySimpleArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout questcontainer = (LinearLayout)findViewById(R.id.questcontainer);

        Quest quest1 = new Quest("Test Quest", "basketball");
//        Quest quest2 = new Quest("Quest name2", "golf");
//        Quest quest3 = new Quest("Quest name3", "weights");
//        Quest quest4 = new Quest("Quest name4", "basketball");
        questlist.add(quest1);
//        questlist.add(quest2);
//        questlist.add(quest3);
//        questlist.add(quest4);

//        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
//        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
//        Gson gson = new Gson();
//        String jsonQuestList = gson.toJson(questlist);
//        prefsEditor.putString("questlist", jsonQuestList);
//        prefsEditor.commit();
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        String jsonQuestList=appSharedPrefs.getString("questlist","");
        Gson gson=new Gson();
        Type type = new TypeToken<ArrayList<Quest>>(){}.getType();
        questlist = gson.fromJson(jsonQuestList, type);
        if (questlist==null){
            questlist= new ArrayList<Quest>();
            questlist.add(quest1);
        }

        adapter = new MySimpleArrayAdapter(this, questlist);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MainActivity.this, QuestDetailsActivity.class);
                myIntent.putExtra("questnumber", position);
                MainActivity.this.startActivity(myIntent);
            }
        });




    }

    public void newQuest(View view)
    {
        Intent intent = new Intent(MainActivity.this, NewQuestActivity.class);
        startActivity(intent);
    }
}
