package com.example.petre.colour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View touchView = findViewById(R.id.full);

        img = (ImageView) findViewById(R.id.ball);

        touchView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println(getSupportActionBar().getHeight() );
                img.setY(event.getRawY() - getSupportActionBar().getHeight() - img.getHeight()/2 - getStatusBarHeight());
                img.setX(event.getRawX() - img.getWidth()/2);
                return true;
            }
        });

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
