package me.shelly.projectshelly;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import me.shelly.projectshelly.utils.CountDownTimerWithPause;

public class TimerFragment extends Fragment {

    private TextView textView;
    private Button btnStart;
    private Button btnReset;
    private Button btnPause;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        textView = view.findViewById(R.id.textView);
        btnStart = view.findViewById(R.id.btnPause);
        btnReset = view.findViewById(R.id.btnReset2);
        btnPause = view.findViewById(R.id.btnPause);

        CountDownTimerWithPause timer;

        timer = new CountDownTimerWithPause(25*60*1000, 1000, false) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Takes remaining milliseconds and dividing by 1000 to get seconds
                int currentSec = (int) millisUntilFinished/1000;

                int currentMin = currentSec/60;
                textView.setText(String.format("%02d", currentMin) + ":" + String.format("%02d", currentSec - (currentMin*60)));
            }

            @Override
            public void onFinish() {

            }
        };

        btnStart.setOnClickListener(v -> {
            timer.create();
        });

        btnPause.setOnClickListener(v -> {
            if (timer.isRunning()) {
                timer.pause();
                btnPause.setText("Resume");
            } else if (timer.isPaused()) {
                timer.resume();
                btnPause.setText("Pause");
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}