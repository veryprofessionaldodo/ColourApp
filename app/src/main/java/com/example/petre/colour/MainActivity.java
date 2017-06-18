package com.example.petre.colour;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private View touchView;
    private ImageView circle, colorBar;
    private Bitmap bitmap;
    private Square square;
    private boolean hasClickedOnRing = false;
    private boolean hasClickedOnSquare = false;
    private int pixelWidth, pixelHeight;

    private float SCALE;
    int widthCircleMax;
    int heightCircleMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setVariables();

        circle.setY(.458f * pixelHeight / 2 - dpsToPixels(Constants.CIRCLE_DPS/2));
        circle.setX(.5f * pixelWidth / 2 - dpsToPixels(Constants.CIRCLE_DPS/2));

        //ImageView img = (ImageView) findViewById(R.id.square_outline);
        //System.out.println("X " + img.getX() + (float)img.getWidth()/2.0f + " Y " + img.getY() + (float) img.getHeight()/2.0f);

        setTouchListener();
    }

    public void setVariables() {
        ImageView background = (ImageView) findViewById(R.id.background);
        background.setScaleType(ImageView.ScaleType.FIT_XY);

        square = (Square) findViewById(R.id.square_color);
        square.setColor(Color.BLUE);

        touchView = findViewById(R.id.full);

        touchView.setDrawingCacheEnabled(true);
        touchView.buildDrawingCache();

        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        touchView.setSystemUiVisibility(uiOptions);
        circle = (ImageView) findViewById(R.id.circle);
        colorBar = (ImageView) findViewById(R.id.color_bar);

        SCALE = touchView.getContext().getResources().getDisplayMetrics().density;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        pixelWidth = size.x;
        pixelHeight = size.y;

        widthCircleMax = dpsToPixels(Constants.WHEEL_DPS);
        heightCircleMax = dpsToPixels(Constants.WHEEL_DPS);

    }

    public void setTouchListener() {
        touchView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float deltaX = event.getX() - pixelWidth / 2.0f;
                float deltaY = event.getY() - .458f * pixelHeight;
                float angle = (float) Math.atan2(deltaY, deltaX);

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    hasClickedOnRing = false;
                    hasClickedOnSquare = false;

                } else {
                    if (((Math.abs(deltaX) < Math.abs(dpsToPixels(Constants.WHEEL_DPS/2) * Math.cos(angle)) &&  (Math.abs(deltaX) > Math.abs(dpsToPixels((Constants.WHEEL_DPS-Constants.COLOR_BAR_DPS*1.75f)/2) * Math.cos(angle))))
                            || hasClickedOnRing) && !hasClickedOnSquare) {
                        handleChangeColor(event);

                        float x = pixelWidth / 2.0f + dpsToPixels((Constants.WHEEL_DPS-5)/2.0f) * (float) Math.cos(angle)
                                - Math.abs(colorBar.getWidth()/2 + colorBar.getWidth()/2 * (float)Math.cos(angle));
                        float y = .458f * pixelHeight +  dpsToPixels((Constants.WHEEL_DPS-5)/2.0f) * (float) Math.sin(angle)
                                - Math.abs(colorBar.getHeight()/2 + colorBar.getWidth()/2 * (float)Math.sin(angle));

                        colorBar.setX(x);
                        colorBar.setY(y);
                        colorBar.setRotation(angle * 180 / (float) Math.PI);

                        hasClickedOnRing = true;
                        hasClickedOnSquare = false;
                    } else if ((Math.abs(deltaX) < dpsToPixels(Constants.SQUARE_DPS/2) && Math.abs(deltaY) < dpsToPixels(Constants.SQUARE_DPS/2)) || hasClickedOnSquare ) {
                        float tmpX = event.getX() - dpsToPixels(Constants.CIRCLE_DPS/2);
                        float tmpY = event.getY() - dpsToPixels(Constants.CIRCLE_DPS/2);

                        if (deltaX < -dpsToPixels(Constants.SQUARE_DPS/2))
                            tmpX = pixelWidth / 2.0f - dpsToPixels(Constants.SQUARE_DPS/2 + Constants.CIRCLE_DPS/2) ;
                        else if (deltaX > dpsToPixels(Constants.SQUARE_DPS/2))
                            tmpX = pixelWidth / 2.0f + dpsToPixels(Constants.SQUARE_DPS/2 - Constants.CIRCLE_DPS/2) ;

                        if (deltaY < -dpsToPixels(Constants.SQUARE_DPS/2))
                            tmpY = .458f * pixelHeight - dpsToPixels(Constants.SQUARE_DPS/2 + Constants.CIRCLE_DPS/2) ;
                        else if (deltaY > dpsToPixels(Constants.SQUARE_DPS/2))
                            tmpY = .458f * pixelHeight + dpsToPixels(Constants.SQUARE_DPS/2 - Constants.CIRCLE_DPS/2) ;

                        circle.setX(tmpX);
                        circle.setY(tmpY);

                        hasClickedOnSquare = true;
                        hasClickedOnRing = false;
                    }

                }

                return true;
            }


        });

    }

    public void handleChangeColor(MotionEvent event) {
        bitmap = touchView.getDrawingCache();



        int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());

        int r = Color.red(pixel);
        int g = Color.green(pixel);
        int b = Color.blue(pixel);

        square.setColor(Color.rgb(r, g, b));
    }

    public int dpsToPixels(float dps) {
        return (int) (dps * SCALE + 0.5f);
    }
}
/*

 */

/*
 TODO LIST :
    TODO ALTERAR SPRITE DE QUADRADO PARA ALGO EM QUE POSSA TER MAIS PRECISÃO
    TODO PROBLEMA NO NEXUS 6, PROVAVELMENTE XXXHDPI DEMASIADO PEQUENO
 */