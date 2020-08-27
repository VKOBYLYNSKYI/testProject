package com.example.testandroid.model;

import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.testandroid.http.Data;
import com.example.testandroid.http.HttpRequest;
import com.example.testandroid.http.onTaskCompleted;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class DataModel implements IDataModel,onTaskCompleted {

    private String resultDataModel = null;


    @Override
    public Boolean receiveAllProducts() throws Exception {
        Data mData = new Data();

        HttpRequest httpRequest = new HttpRequest(mData, this);
        httpRequest.execute("http://testproject11.zzz.com.ua/result", ".result", "DataModel");

        try {
            Thread.sleep(3000);
        } catch (Exception e){
            throw new Exception("A message that describes the error.");
        }


        return mData.getData();
    }


    @Override
    public void onTaskCompleted(boolean value) { }
}
