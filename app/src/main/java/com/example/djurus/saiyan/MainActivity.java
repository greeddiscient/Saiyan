package com.example.djurus.saiyan;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends ListActivity {
    private ArrayList<Quest> questlist= new ArrayList<Quest>();
    private QuestAdapter adapter;
    private TextView startaquest;
    private SharedPreferences appSharedPrefs;
    private Typeface fontawesome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fontawesome = Typeface.createFromAsset( getApplicationContext().getAssets(), "fontawesome-webfont.ttf" );
        TextView history = (TextView)findViewById(R.id.history);
        history.setTypeface(fontawesome);
        TextView profile = (TextView)findViewById(R.id.profile);
        profile.setTypeface(fontawesome);

        ImageView newquest = (ImageView) findViewById(R.id.newquest);
        newquest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewQuestActivity.class);
                startActivity(intent);
            }
        });

        appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String jsonQuestList=appSharedPrefs.getString("questlist","");
        Gson gson=new Gson();
        Type type = new TypeToken<ArrayList<Quest>>(){}.getType();
        questlist = gson.fromJson(jsonQuestList, type);
        if (questlist==null){
            startaquest = (TextView)findViewById(R.id.startaquest);
            startaquest.setVisibility(View.VISIBLE);
        }
        else{
            adapter = new QuestAdapter(getApplicationContext(), questlist);
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
    }
    public class QuestAdapter extends ArrayAdapter<Quest>{
        private final ArrayList<Quest> values;
        private final Context context;
        public QuestAdapter(Context context, ArrayList<Quest> values) {
            super(context, R.layout.questview, values);
            this.values=values;
            this.context=context;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View questView = inflater.inflate(R.layout.questview, parent, false);

            TextView questName = (TextView) questView.findViewById(R.id.questname);
            questName.setText(values.get(position).getQuestName());

            TextView counticon = (TextView)questView.findViewById(R.id.counticon);
            counticon.setTypeface(fontawesome);

            TextView count = (TextView)questView.findViewById(R.id.count);
            String questcount= String.valueOf(values.get(position).getCount());
            count.setText(questcount);

            ImageView categoryIcon = (ImageView) questView.findViewById(R.id.category);
            String category = values.get(position).getCategory();
            if (category.equals("basketball")) {
                categoryIcon.setImageResource(R.drawable.basketball);
            } else if (category.equals("golf")) {
                categoryIcon.setImageResource(R.drawable.golfball);
            }
            else if (category.equals("weights")) {
                categoryIcon.setImageResource(R.drawable.weights);
            }

            TextView deletequest = (TextView)questView.findViewById(R.id.deletequest);
            deletequest.setTypeface(fontawesome);
            deletequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete this quest?");
                    adb.setNegativeButton("Cancel",null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                            values.remove(position);
                            notifyDataSetChanged();
                            String jsonQuestList = new Gson().toJson(values);
                            appSharedPrefs.edit().putString("questlist", jsonQuestList).commit();
                            if (values.size()==0){
                                startaquest = (TextView)findViewById(R.id.startaquest);
                                startaquest.setVisibility(View.VISIBLE);
                            }
                        }});
                    adb.show();
                }
            });
            return questView;
        }
    }
}
