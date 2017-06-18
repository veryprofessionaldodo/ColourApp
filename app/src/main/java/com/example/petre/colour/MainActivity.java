package com.example.petre.colour;

import android.app.ActionBar;
import android.content.Context;
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

import java.io.Console;

public class MainActivity extends AppCompatActivity {

    private View touchView;
    private ImageView circle, colorBar;
    private Bitmap bitmap;
    private Square square;
    private boolean hasClickedOnRing = false;
    private boolean hasClickedOnSquare = false;
    private int pixelWidth, pixelHeight;

    public static float CIRCLE_DPS;
    public static float WHEEL_DPS;
    public static float SQUARE_DPS;
    public static float COLOR_BAR_DPS;

    private float SCALE;
    int widthCircleMax;
    int heightCircleMax;
    float CENTER_WHEEL_X, CENTER_WHEEL_Y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setVariables();

        circle.setY(CENTER_WHEEL_Y / 2 - dpsToPixels(Constants.CIRCLE_DPS/2));
        circle.setX(.5f * pixelWidth / 2 - dpsToPixels(Constants.CIRCLE_DPS/2));

        //ImageView img = (ImageView) findViewById(R.id.square_outline);
        //System.out.println("X " + img.getX() + (float)img.getWidth()/2.0f + " Y " + img.getY() + (float) img.getHeight()/2.0f);

        setTouchListener();
    }

    public void setVariables() {
        System.out.println("merda");
        Constants constants =  new Constants(getResources());
        System.out.println("merda");

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

        CENTER_WHEEL_X = pixelWidth / 2.0f;
        CENTER_WHEEL_Y = .434f * pixelHeight;

    }

    public void setTouchListener() {
        touchView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float deltaX = event.getX() - CENTER_WHEEL_X;
                float deltaY = event.getY() - CENTER_WHEEL_Y;
                float angle = (float) Math.atan2(deltaY, deltaX);

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    hasClickedOnRing = false;
                    hasClickedOnSquare = false;

                } else {
                    if (((Math.abs(deltaX) < Math.abs(dpsToPixels(Constants.WHEEL_DPS/2) * Math.cos(angle)) &&  (Math.abs(deltaX) > Math.abs(dpsToPixels((Constants.WHEEL_DPS-Constants.COLOR_BAR_DPS*1.75f)/2) * Math.cos(angle))))
                            || hasClickedOnRing) && !hasClickedOnSquare) {

                        float x = CENTER_WHEEL_X + dpsToPixels((Constants.WHEEL_DPS)/2.0f) * (float) Math.cos(angle)
                                - Math.abs(colorBar.getWidth()/2 + colorBar.getWidth()/2 * (float)Math.cos(angle));
                        float y = CENTER_WHEEL_Y +  dpsToPixels((Constants.WHEEL_DPS)/2.0f) * (float) Math.sin(angle)
                                - Math.abs(colorBar.getHeight()/2 + colorBar.getWidth()/2 * (float)Math.sin(angle));

                        colorBar.setX(x);
                        colorBar.setY(y);
                        colorBar.setRotation(angle * 180 / (float) Math.PI);


                        ImageView precision = (ImageView) findViewById(R.id.precision);

                        handleChangeColor((int)CENTER_WHEEL_X + (int) (dpsToPixels(Constants.WHEEL_DPS/2 - 14.4f)* Math.cos(angle) - dpsToPixels(Constants.CIRCLE_DPS/2)),
                                (int)CENTER_WHEEL_Y + (int)(dpsToPixels(Constants.WHEEL_DPS/2 - 14.4f)* Math.sin(angle) - dpsToPixels(Constants.CIRCLE_DPS/2)));
                        //circle.setX(x + dpsToPixels(Constants.COLOR_BAR_DPS/2) * (float) Math.cos(angle) - dpsToPixels(Constants.CIRCLE_DPS/2));
                        //circle.setY(y + dpsToPixels(Constants.COLOR_BAR_DPS/2) * (float) Math.sin(angle) - dpsToPixels(Constants.CIRCLE_DPS/2));
                        //precision.setX((int)CENTER_WHEEL_X + (int) (dpsToPixels(Constants.WHEEL_DPS/2 - 14.4f)* Math.cos(angle) - dpsToPixels(Constants.CIRCLE_DPS/2)));
                        //precision.setY((int)CENTER_WHEEL_Y + (int)(dpsToPixels(Constants.WHEEL_DPS/2 - 14.4f)* Math.sin(angle) - dpsToPixels(Constants.CIRCLE_DPS/2)));

                        hasClickedOnRing = true;
                        hasClickedOnSquare = false;
                    } else if ((Math.abs(deltaX) < dpsToPixels(Constants.SQUARE_DPS/2) && Math.abs(deltaY) < dpsToPixels(Constants.SQUARE_DPS/2)) || hasClickedOnSquare ) {
                        float tmpX = event.getX() - dpsToPixels(Constants.CIRCLE_DPS/2);
                        float tmpY = event.getY() - dpsToPixels(Constants.CIRCLE_DPS/2);

                        if (deltaX < -dpsToPixels(Constants.SQUARE_DPS/2))
                            tmpX = CENTER_WHEEL_X - dpsToPixels(Constants.SQUARE_DPS/2 + Constants.CIRCLE_DPS/2) ;
                        else if (deltaX > dpsToPixels(Constants.SQUARE_DPS/2))
                            tmpX = CENTER_WHEEL_X + dpsToPixels(Constants.SQUARE_DPS/2 - Constants.CIRCLE_DPS/2) ;

                        if (deltaY < -dpsToPixels(Constants.SQUARE_DPS/2))
                            tmpY = CENTER_WHEEL_Y - dpsToPixels(Constants.SQUARE_DPS/2 + Constants.CIRCLE_DPS/2) ;
                        else if (deltaY > dpsToPixels(Constants.SQUARE_DPS/2))
                            tmpY = CENTER_WHEEL_Y + dpsToPixels(Constants.SQUARE_DPS/2 - Constants.CIRCLE_DPS/2) ;

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

    public void handleChangeColor(int x, int y) {
        bitmap = touchView.getDrawingCache();



        int pixel = bitmap.getPixel(x,y);

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