package com.example.djurus.saiyan;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface font = Typeface.createFromAsset( getApplicationContext().getAssets(), "fontawesome-webfont.ttf" );
        TextView history = (TextView)findViewById(R.id.history);
        history.setTypeface(font);
        TextView profile = (TextView)findViewById(R.id.profile);
        profile.setTypeface(font);

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
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
            View rowView = inflater.inflate(R.layout.questview, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.questname);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.category);
            textView.setText(values.get(position).getQuestName());
            Typeface font = Typeface.createFromAsset( getContext().getAssets(), "fontawesome-webfont.ttf" );
            TextView icon = (TextView)rowView.findViewById(R.id.icon);
            icon.setTypeface(font);
            TextView count = (TextView)rowView.findViewById(R.id.count);
            String questcount= String.valueOf(values.get(position).getCount());
            count.setText(questcount);
            String category = values.get(position).getCategory();
            if (category.equals("basketball")) {
                imageView.setImageResource(R.drawable.basketball);
            } else if (category.equals("golf")) {
                imageView.setImageResource(R.drawable.golfball);
            }
            else if (category.equals("weights")) {
                imageView.setImageResource(R.drawable.weights);
            }
            imageView.setOnClickListener(new View.OnClickListener() {
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
                            SharedPreferences appSharedPrefs = PreferenceManager
                                    .getDefaultSharedPreferences(context);
                            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                            String jsonQuestList = new Gson().toJson(values);
                            prefsEditor.putString("questlist", jsonQuestList);
                            prefsEditor.commit();
                            if (values.size()==0){
                                startaquest = (TextView)findViewById(R.id.startaquest);
                                startaquest.setVisibility(View.VISIBLE);
                            }
                        }});
                    adb.show();
                }
            });
            return rowView;
        }
    }

    public void newQuest(View view)
    {
        Intent intent = new Intent(MainActivity.this, NewQuestActivity.class);
        startActivity(intent);
    }
}
