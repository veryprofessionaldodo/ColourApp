package com.example.petre.colour;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by petre on 20/05/2017.
 */

public class Square extends View {


    int squareColor;


    public Square(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        squareColor = Color.GREEN;
    }

    @Override
    protected void onDraw(Canvas canvas) {
       View view = findViewById(R.id.square_color);
        Canvas c = canvas;
        Rect r = new Rect();
        r.set(0,0,view.getWidth(),view.getHeight());
        Paint squarePaint = new Paint();
        squarePaint.setColor(squareColor);
        squarePaint.setStyle(Paint.Style.FILL);
        squarePaint.setStrokeWidth(10);

        c.drawRect(r,squarePaint);
    }

    public void setColor(int color) {
        this.squareColor = color;
        invalidate();
    }
}
