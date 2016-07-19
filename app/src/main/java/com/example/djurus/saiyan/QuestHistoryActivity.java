package com.example.djurus.saiyan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class QuestHistoryActivity extends AppCompatActivity {
    Typeface fontawesome;
    SharedPreferences appSharedPrefs;
    ArrayList<Quest> questHistoryList=new ArrayList<Quest>();
    QuestHistoryAdapter adapter;
    TextView noQuestCompleted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_history);
        fontawesome = Typeface.createFromAsset( getApplicationContext().getAssets(), "fontawesome-webfont.ttf" );

        ImageView backbutton = (ImageView) findViewById(R.id.backarrow);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (appSharedPrefs.contains("questHistoryList")){
            String jsonQuestList=appSharedPrefs.getString("questHistoryList","");
            Gson gson=new Gson();
            Type type = new TypeToken<ArrayList<Quest>>(){}.getType();
            questHistoryList = gson.fromJson(jsonQuestList, type);
        }
        if (questHistoryList.size()==0){
            noQuestCompleted = (TextView)findViewById(R.id.noquestcompleted);
            noQuestCompleted.setVisibility(View.VISIBLE);
        }
        else{
            adapter = new QuestHistoryAdapter(getApplicationContext(), questHistoryList);
            ListView list = (ListView) findViewById(android.R.id.list);
            list.setAdapter(adapter);
        }

    }

    public class QuestHistoryAdapter extends ArrayAdapter<Quest> {
        private final ArrayList<Quest> values;
        private final Context context;
        public QuestHistoryAdapter(Context context, ArrayList<Quest> values) {
            super(context, R.layout.questview, values);
            this.values=values;
            this.context=context;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View questHistoryView = inflater.inflate(R.layout.questhistoryview, parent, false);

            Quest currentQuest = values.get(values.size()-1-position);

            TextView questName = (TextView) questHistoryView.findViewById(R.id.questname);
            questName.setText(currentQuest.getQuestName());

            TextView questCompletedIcon = (TextView)questHistoryView.findViewById(R.id.questcompletedicon);
            questCompletedIcon.setTypeface(fontawesome);

            String date = new SimpleDateFormat("MM-dd-yyyy").format(currentQuest.getDateCompleted());
            TextView dateCompleted = (TextView)questHistoryView.findViewById(R.id.datecompleted);
            dateCompleted.setText(date);

            TextView questScore = (TextView)questHistoryView.findViewById(R.id.questscore);
            int score = currentQuest.getScore();
            int maxScore = currentQuest.getMaxscore();
            questScore.setText(score+" / "+maxScore);

            ImageView categoryIcon = (ImageView) questHistoryView.findViewById(R.id.category);
            String category = currentQuest.getCategory();
            if (category.equals("basketball")) {
                categoryIcon.setImageResource(R.drawable.basketball);
            } else if (category.equals("golf")) {
                categoryIcon.setImageResource(R.drawable.golfball);
            }
            else if (category.equals("weights")) {
                categoryIcon.setImageResource(R.drawable.weights);
            }


            return questHistoryView;
        }
    }
}
