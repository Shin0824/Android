package com.example.myrouletteapp;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LinearLayout sectionsContainer;
    private Button spinButton;
    private int numSections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sectionsContainer = findViewById(R.id.sectionsContainer);
        spinButton = findViewById(R.id.spinButton);

        Button setupButton = findViewById(R.id.setupButton);
        setupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupSections();
            }
        });

        spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinRoulette();
            }
        });
    }

    private void setupSections() {
        EditText numSectionsInput = findViewById(R.id.numSections);
        if (numSectionsInput.getText().toString().isEmpty()) {
            Toast.makeText(this, "항목의 갯수를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        numSections = Integer.parseInt(numSectionsInput.getText().toString());
        sectionsContainer.removeAllViews();

        for (int i = 0; i < numSections; i++) {
            EditText sectionInput = new EditText(this);
            sectionInput.setHint("항목 " + (i + 1));
            sectionsContainer.addView(sectionInput);
        }

        spinButton.setVisibility(View.VISIBLE);
    }

    private void spinRoulette() {
        if (numSections == 0) {
            Toast.makeText(this, "먼저 항목의 갯수를 설정하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        Random random = new Random();
        int selectedSection = random.nextInt(numSections);

        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 3600 + (360 / numSections) * selectedSection,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setDuration(3000);
        rotateAnimation.setFillAfter(true);

        findViewById(R.id.title).startAnimation(rotateAnimation);

        rotateAnimation.setAnimationListener(new RotateAnimation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                EditText selectedInput = (EditText) sectionsContainer.getChildAt(selectedSection);
                String selectedText = selectedInput.getText().toString();
                Toast.makeText(MainActivity.this, "선택된 항목: " + selectedText, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
}
