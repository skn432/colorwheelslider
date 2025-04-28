package com.example.exp12;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private float[] hsv = new float[]{0f, 1f, 1f};
    private int alpha = 255;

    private View previewColor;
    private SeekBar alphaSeekBar, valueSeekBar;
    private TextView alphaValueText, valueText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ColorWheelView colorWheel = findViewById(R.id.colorWheel);
        previewColor = findViewById(R.id.previewColor);
        alphaSeekBar = findViewById(R.id.alphaSeekBar);
        valueSeekBar = findViewById(R.id.valueSeekBar);
        alphaValueText = findViewById(R.id.alphaValueText);
        valueText = findViewById(R.id.valueValueText);

        // Set listener for ColorWheel
        colorWheel.setOnColorChangeListener((hue, saturation) -> {
            hsv[0] = hue;
            hsv[1] = saturation;
            updateColor();
        });

        // Set listener for Alpha SeekBar
        alphaSeekBar.setMax(255);
        alphaSeekBar.setProgress(alpha);
        alphaSeekBar.setOnSeekBarChangeListener(seekListener(progress -> {
            alpha = progress;
            alphaValueText.setText("Alpha: " + alpha);
            updateColor();
        }));

        // Set listener for Brightness/Value SeekBar
        valueSeekBar.setMax(100);
        valueSeekBar.setProgress(100);
        valueSeekBar.setOnSeekBarChangeListener(seekListener(progress -> {
            hsv[2] = progress / 100f;
            valueText.setText("Value: " + progress);
            updateColor();
        }));

        // Initial color update
        updateColor();
    }

    private void updateColor() {
        int color = Color.HSVToColor(alpha, hsv);
        previewColor.setBackgroundColor(color);
    }

    private SeekBar.OnSeekBarChangeListener seekListener(OnProgressChanged callback) {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                callback.onChange(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        };
    }

    interface OnProgressChanged {
        void onChange(int progress);
    }
}
