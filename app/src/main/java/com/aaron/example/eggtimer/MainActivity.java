package com.aaron.example.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int timeRemaining;
    TextView textView;
    SeekBar timeSeekBar;
    CountDownTimer timer;
    MediaPlayer mediaPlayer;
    Boolean isPlaying = false;
    Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.airhorn);

        // Create Seek Bar, set max, call display time method on seek bar change
        timeSeekBar = findViewById(R.id.seekBar);
        timeSeekBar.setMax(600);
        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView = findViewById(R.id.textView);
                textView.setText(convertTime(progress));
                timeRemaining = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
    // Go & cancel button onClick method to begin count down & modify UI
    public void beginCountDown(View v) {
        goButton = findViewById(R.id.goButton);
        if (isPlaying == false) {
            isPlaying = true;
            goButton.setText("Stop");
            timeSeekBar.setVisibility(View.INVISIBLE);
            timer = new CountDownTimer(timeRemaining * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    String displayTime = convertTime((int)(millisUntilFinished / 1000));
                    textView.setText(displayTime);
                }
                @Override
                public void onFinish() {
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        } else {
            resetTimer();
        }

    }
    // Method to reset the clocks default values upon cancel or count down finish
    public void resetTimer(){
        isPlaying = false;
        goButton.setText("Go");
        timeSeekBar.setVisibility(View.VISIBLE);
        timer.cancel();
        timeSeekBar.setProgress(0);
        textView.setText("0:00");
    }

    // Method to convert the seekBar value into time
    public String convertTime(int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        String timeString = String.format("%2d:%02d", minutes, seconds);
        return timeString;
    }

}