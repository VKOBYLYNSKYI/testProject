package com.example.testandroid;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.testandroid.presenter.IDataPresenter;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sPref;
    final String SAVED_RESULT = "saved_result";
    static public Boolean res = null;
    View parentLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        parentLayout = findViewById(android.R.id.content);
        setSupportActionBar(toolbar);
        Log.w("MainActivity/onCreate", "Success");

    }

    @SuppressLint("LongLogTag")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.w("MainActivity/onCreateOptionsMenu", "Success");
        return true;
    }


    @SuppressLint("LongLogTag")
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("link", loadData());
        res = loadData();
        Log.w("MainActivity/onSaveInstanceState", "Success");
    }

    Boolean loadData() {
        sPref = getPreferences(MODE_PRIVATE);
        Boolean res = sPref.getBoolean(SAVED_RESULT, false);
        return res;
    }


    @SuppressLint("LongLogTag")
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.w("MainActivity/onRestoreInstanceState", "Success");
        res = savedInstanceState.getBoolean("link");
    }

    @SuppressLint("LongLogTag")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.w("MainActivity/onOptionsItemSelected", "Success");


        switch (item.getItemId()) {
            case R.id.refresh:
                SharedPreferences preferences = getSharedPreferences(SAVED_RESULT, 0);
                preferences.edit().remove(SAVED_RESULT).commit();
                Snackbar.make(parentLayout, "Remove", Snackbar.LENGTH_LONG).show();
                return true;
            // TODO
            default:  break;
        }




       return super.onOptionsItemSelected(item);
    }

}