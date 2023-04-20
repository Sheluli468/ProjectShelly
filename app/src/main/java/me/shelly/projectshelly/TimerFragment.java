package me.shelly.projectshelly;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class TimerFragment extends Fragment {

    private static final long START_TIME_IN_MILLISECONDS = 25*60*1000;
    private TextView textView;
    private Button btnStart;
    private Button btnReset;
    private Button btnPause;
    private boolean timerRunning = false;
    private long timeLeftInMillis = START_TIME_IN_MILLISECONDS;
    private CountDownTimer countDownTimer;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        textView = view.findViewById(R.id.minTime);
        btnStart = view.findViewById(R.id.btnStart);
        btnReset = view.findViewById(R.id.btnReset2);
        btnPause = view.findViewById(R.id.btnPause);
        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setProgress(0, true);

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerRunning) {
                    pauseTimer();
                }
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!timerRunning) {
                    startTimer();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        updateCountDownText();

        super.onViewCreated(view, savedInstanceState);
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                Log.e("error", timeLeftInMillis + " ");
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
            }
        }.start();

        timerRunning = true;

    }

    private void updateCountDownText() {
        int min = (int) (timeLeftInMillis / 1000) / 60;
        int sec = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeft = String.format(Locale.getDefault(),"%02d:%02d", min, sec);
        textView.setText(timeLeft);
        progressBar.setProgress((int) (START_TIME_IN_MILLISECONDS - timeLeftInMillis) / 1000, true);
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    private void resetTimer() {
        timeLeftInMillis = START_TIME_IN_MILLISECONDS;
        updateCountDownText();
    }

}