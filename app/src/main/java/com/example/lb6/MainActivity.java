package com.example.lb6;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

    MyTask mt;

    static  TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("qwe", "create Main Activity: " + this.hashCode());

        tv = (TextView) findViewById(R.id.tv);

        mt = (MyTask) getLastNonConfigurationInstance();
        if (mt == null){
            mt = new MyTask();
            mt.execute();
        }

        mt.link(this);
        Log.d("qwe", "create MyTask: " + mt.hashCode());
    }
    public Object onRetainNonConfigurationInstance() {
        mt.unLink();
        return mt;
    }
    static class MyTask extends AsyncTask<String, Integer, Void> {
        MainActivity activity;
        void link(MainActivity act){
            activity  = act;
        }

        void unLink(){
            activity = null;
        }
        @Override
        protected Void doInBackground(String...params){
            try{
                for (int i=1; i<=10; i++){
                    TimeUnit.SECONDS.sleep(1);
                    publishProgress(i);
                    Log.d("qwe", "i = " + i + ", MyTask: " + this.hashCode());
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tv.setText("i = " + values[0]);
        }
    }
}