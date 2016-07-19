package com.example.djurus.saiyan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NewQuestActivity extends AppCompatActivity {
    private ArrayList<Quest> questlist= new ArrayList<Quest>();
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private String category="basketball";
    private EditText questname;
    private EditText questmaxscore;
    private ImageView category0;
    private ImageView category1;
    private ImageView category2;
    private int maxscore=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newquest);
        ImageView backarrow= (ImageView)findViewById(R.id.backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        prefsEditor = appSharedPrefs.edit();
        String jsonQuestList=appSharedPrefs.getString("questlist","");
        Gson gson=new Gson();
        Type type = new TypeToken<ArrayList<Quest>>(){}.getType();
        questlist = gson.fromJson(jsonQuestList, type);

        if (questlist==null){
            questlist= new ArrayList<Quest>();
        }

        Button savequest= (Button)findViewById(R.id.savequest);
        questname   = (EditText)findViewById(R.id.questname);
        questmaxscore = (EditText)findViewById(R.id.questmaxscore);
        savequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questname.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"You can't go on an empty QUEST", Toast.LENGTH_SHORT).show();
                }
                else{
                    String maxscorestr="";
                    if (questmaxscore.getText().toString().equals("")){
                        maxscorestr+=maxscore;
                    }
                    else{
                        maxscorestr=questmaxscore.getText().toString();
                    }
                    Quest q = new Quest(questname.getText().toString(),category);
                    q.setMaxscore(Integer.valueOf(maxscorestr));
                    questlist.add(q);
                    String jsonQuestList = new Gson().toJson(questlist);
                    prefsEditor.putString("questlist", jsonQuestList);
                    prefsEditor.commit();
                    Intent myIntent = new Intent(NewQuestActivity.this, MainActivity.class);
                    NewQuestActivity.this.startActivity(myIntent);
                }
            }
        });
        category0 = (ImageView)findViewById(R.id.category0);
        category0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="basketball";
                category0.setBackgroundColor(Color.parseColor("#0892D0"));
                category1.setBackgroundResource(0);
                category2.setBackgroundResource(0);
            }
        });
        category1 = (ImageView)findViewById(R.id.category1);
        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="golf";
                category0.setBackgroundResource(0);
                category1.setBackgroundColor(Color.parseColor("#0892D0"));
                category2.setBackgroundResource(0);
            }
        });
        category2 = (ImageView)findViewById(R.id.category2);
        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="weights";
                category0.setBackgroundResource(0);
                category1.setBackgroundResource(0);
                category2.setBackgroundColor(Color.parseColor("#0892D0"));
            }
        });


    }
}
