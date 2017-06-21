package com.example.petre.colour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.view.View;

public class Complimentary extends Activity {

    private ConstraintLayout view;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complimentary_layout);
        view = (ConstraintLayout) findViewById(R.id.complimentary);
        view.setOnTouchListener(new View.OnTouchListener() {
      @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Tratar aqui do tratamento de input
                disable();
                return true;
            }
        });
    }

    public void disable() {
        Intent intent = new Intent(getApplicationContext(), PrecisionSquare.class);
        finishActivity(1);
        startActivityForResult(intent,2);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}

/*
 TODO LIST :
    TODO Fazer modo Landscape
    TODO Apresentar esquemas complementares
    TODO Fazer design de esquemas complementares
 */
