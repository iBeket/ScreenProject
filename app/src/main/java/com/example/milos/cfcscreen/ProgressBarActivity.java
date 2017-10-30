package com.example.milos.cfcscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Milos on 27-Oct-17.
 */

public class ProgressBarActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView progressInfo, goalInfo;
    ProgressBarAnimation progressBarAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        int goal = 800;
        int progress = 184;
        int percentage = 100;
        int finalPercentage;

        progressBar = (ProgressBar) findViewById(R.id.circularProgressbar);
        progressInfo = (TextView) findViewById(R.id.progress_info);
        goalInfo = (TextView) findViewById(R.id.text_goal);

        finalPercentage = (percentage * progress) / goal;

        progressBarAnimation = new ProgressBarAnimation(progressBar,0,finalPercentage);
        progressBarAnimation.setDuration(2500);
        progressBar.startAnimation(progressBarAnimation);


    }
}
