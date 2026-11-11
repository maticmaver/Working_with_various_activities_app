package com.example.working_with_various_activities_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HelloActivity extends AppCompatActivity {

    private int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hello);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(MainActivity.EXTRA_COUNT)) {
                mCount = intent.getIntExtra(MainActivity.EXTRA_COUNT, 0);
            } else if (intent.hasExtra("count")) {
                mCount = intent.getIntExtra("count", 0);
            }
        }

        TextView textViewCount = findViewById(R.id.textView7);
        if (textViewCount != null) {
            textViewCount.setText(String.valueOf(mCount));
        }

        Button btnGoToActivity1 = findViewById(R.id.buttonGoToActivity1);
        if (btnGoToActivity1 != null) {
            btnGoToActivity1.setOnClickListener(v -> goToActivity1());
        }

        // Swipe listener for Activity 2 (HelloActivity):
        // Swipe Left (z desne proti levi) -> ThirdActivity (Activity 3)
        // Swipe Right (z leve proti desni) -> MainActivity (Activity 1)
        OnSwipeTouchListener swipeListener = new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                goToActivity3();
            }

            @Override
            public void onSwipeRight() {
                goToActivity1();
            }
        };

        if (mainView != null) {
            mainView.setOnTouchListener(swipeListener);
        }
    }

    private void goToActivity1() {
        Intent mainIntent = new Intent(HelloActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(mainIntent);
        finish();
    }

    private void goToActivity3() {
        Intent thirdIntent = new Intent(HelloActivity.this, ThirdActivity.class);
        thirdIntent.putExtra(MainActivity.EXTRA_COUNT, mCount);
        thirdIntent.putExtra("count", mCount);
        startActivity(thirdIntent);
        finish();
    }
}
