package com.threedots.progress.dotprogress;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private DotProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        ImageButton show = (ImageButton)findViewById(R.id.enter);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTask task = new ShowTask();
                task.execute();
            }
        });

        ImageButton hide = (ImageButton)findViewById(R.id.hide);
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTask task = new ShowTask();
                task.onPostExecute(true);
            }
        });

        progressBar = (DotProgressBar)findViewById(R.id.dotsProgressBar);
        progressBar.setDotCount(4);

        ShowTask task = new ShowTask();
        task.execute();
    }




    class ShowTask extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.start();
        }

        @Override
        protected Boolean doInBackground(Void... arg) {

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {

                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                progressBar.stop();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }
}
