package com.example.djurus.saiyan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class QuestDetailsActivity extends AppCompatActivity {
    private ArrayList<Quest> questlist= new ArrayList<Quest>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_details);

        ImageView backarrow= (ImageView)findViewById(R.id.backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        int questnumber = intent.getIntExtra("questnumber",0);

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        String jsonQuestList=appSharedPrefs.getString("questlist","");
        Gson gson=new Gson();
        Type type = new TypeToken<ArrayList<Quest>>(){}.getType();
        questlist = gson.fromJson(jsonQuestList, type);



        Quest q = questlist.get(questnumber);
        TextView questname = (TextView)findViewById(R.id.questname);
        questname.setText(q.getQuestName());
        ImageView catimage1 = (ImageView)findViewById(R.id.catimage1);
        ImageView catimage2 = (ImageView)findViewById(R.id.catimage2);
        String cat = q.getCategory();
        if (cat.equals("basketball")){
            catimage1.setImageDrawable(getResources().getDrawable(R.drawable.basketball));
            catimage2.setImageDrawable(getResources().getDrawable(R.drawable.basketball));
        }
        else if (cat.equals("golf")){
            catimage1.setImageDrawable(getResources().getDrawable(R.drawable.golfball));
            catimage2.setImageDrawable(getResources().getDrawable(R.drawable.golfball));
        }
        else if (cat.equals("weights")){
            catimage1.setImageDrawable(getResources().getDrawable(R.drawable.weights));
            catimage2.setImageDrawable(getResources().getDrawable(R.drawable.weights));
        }
    }
}
