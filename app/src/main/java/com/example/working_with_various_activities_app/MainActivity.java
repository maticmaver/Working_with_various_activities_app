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

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_COUNT = "com.example.working_with_various_activities_app.extra.COUNT";

    private int mCount = 0;
    private TextView mShowCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        mShowCount = findViewById(R.id.textView7);
        TextView btnCount = findViewById(R.id.textView6);
        Button btnGoToActivity2 = findViewById(R.id.buttonGoToActivity2);
        Button btnGoToActivity3 = findViewById(R.id.buttonGoToActivity3);

        if (btnCount != null) {
            btnCount.setOnClickListener(v -> {
                mCount++;
                if (mShowCount != null) {
                    mShowCount.setText(String.valueOf(mCount));
                }
            });
        }

        if (btnGoToActivity2 != null) {
            btnGoToActivity2.setOnClickListener(v -> goToActivity2());
        }

        if (btnGoToActivity3 != null) {
            btnGoToActivity3.setOnClickListener(v -> goToActivity3());
        }

        // Swipe listener for Activity 1 (MainActivity):
        // Swipe Left (z desne proti levi) -> HelloActivity (Activity 2)
        // Swipe Right (z leve proti desni) -> ThirdActivity (Activity 3)
        OnSwipeTouchListener swipeListener = new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                goToActivity2();
            }

            @Override
            public void onSwipeRight() {
                goToActivity3();
            }
        };

        if (mainView != null) {
            mainView.setOnTouchListener(swipeListener);
        }
    }

    private void goToActivity2() {
        Intent intent = new Intent(MainActivity.this, HelloActivity.class);
        intent.putExtra(EXTRA_COUNT, mCount);
        intent.putExtra("count", mCount);
        startActivity(intent);
    }

    private void goToActivity3() {
        Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
        intent.putExtra(EXTRA_COUNT, mCount);
        intent.putExtra("count", mCount);
        startActivity(intent);
    }
}
