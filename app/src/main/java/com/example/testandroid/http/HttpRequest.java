package com.example.testandroid.http;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpRequest extends AsyncTask<String, Void, Data> {

    private Data mData;
    private onTaskCompleted mOnTaskCompleted;

    public HttpRequest(Data mData,onTaskCompleted mOnTaskCompleted){
        this.mData = mData;
        this.mOnTaskCompleted = mOnTaskCompleted;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected Data doInBackground(String... params) {
        Log.w("HttpRequest/doInBackground", "Success");

        try {
            Document doc = Jsoup.connect(params[0]).get();
            if(doc.select(params[1]).text().equals("true")){
                mData.setData(Boolean.parseBoolean( doc.select(params[1]).text()));
                mOnTaskCompleted.onTaskCompleted(true);
                return null;
            }
            else if(doc.select(params[1]).text().equals("false")){
                mData.setData(Boolean.parseBoolean( doc.select(params[1]).text()));
                mOnTaskCompleted.onTaskCompleted(false);
                return mData;
            }
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }

    @SuppressLint("LongLogTag")
    protected void onPostExecute(Data mData) {
        Log.w("HttpRequest/onPostExecute", "Success");
    }



}
