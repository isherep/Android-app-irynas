package com.irynas.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_items);
        displayMovieList(5);



    }

    public void displayMovieList(View view){

        //ListView simpleList;
       // simpleList = (ListView)findViewById(R.id.list_view_items);

        //Intent startNewActivity  = new Intent(this, DisplayMessageAcrivity.class)
        //ArrayAdapter(Context context, int resource, int textViewResourceId, T[] objects)
        //context - the reference of current class
        //resource id - used to set the layout(xml file) for list items in which you have a text view.
        //example
        MainActivity m = new MainActivity();
        String[][] movies = m.getMovies();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, R.layout.list_view_items, R.id.textView, movies);
        ListView listView = (ListView) findViewById(R.layout.list_view_items);
        listView.setAdapter(arrayAdapter);
        //somelist.setAdapter


    }
}
