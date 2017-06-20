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
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Console;

public class MainActivity extends AppCompatActivity {

    private View touchView;
    private ImageView circle, colorBar, outerWheel, shading;
    private Bitmap bitmap;
    private Square square, squareCenter, squareComplementaryLeft, squareComplementaryRight, squareComplementaryBottom;
    private boolean hasClickedOnRing = false;
    private boolean hasClickedOnSquare = false;
    private int pixelWidth, pixelHeight;
    private float angle = 0;

    private float innerSquareMargin = 2.03f;


    private int currComplementary = 0;

    private float SCALE;
    float CENTER_WHEEL_X, CENTER_WHEEL_Y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVariables();
        setTouchListener();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        CENTER_WHEEL_X = pixelWidth / 2.0f;
        CENTER_WHEEL_Y = outerWheel.getTop() + dpsToPixels(Constants.WHEEL_DPS/2.0f);
        circle.setX(CENTER_WHEEL_X - Constants.CIRCLE_DPS/2.0f);
        circle.setY(CENTER_WHEEL_Y - Constants.CIRCLE_DPS/2.0f);
        squareCenter = (Square) findViewById(R.id.complementary_color_center);
        handleChangeColor(angle);
    }

    public void setVariables() {
        Constants constants =  new Constants(getResources());

        touchView = findViewById(R.id.full);
        touchView.setDrawingCacheEnabled(true);
        touchView.buildDrawingCache();

        circle = (ImageView) findViewById(R.id.circle);
        colorBar = (ImageView) findViewById(R.id.color_bar);
        square = (Square) findViewById(R.id.square_color);
        outerWheel = (ImageView) findViewById(R.id.outer_wheel);


        ImageView background = (ImageView) findViewById(R.id.background);
        background.setScaleType(ImageView.ScaleType.FIT_XY);

        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        touchView.setSystemUiVisibility(uiOptions);

        SCALE = touchView.getContext().getResources().getDisplayMetrics().density;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        pixelWidth = size.x;
        pixelHeight = size.y;

       //

        /*LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.acitivity_main, null);

        ConstraintLayout item = (ConstraintLayout) view.findViewById(R.id.full);*/
    }

    public void setTouchListener() {
        touchView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float deltaX = event.getX() - CENTER_WHEEL_X;
                float deltaY = event.getY() - CENTER_WHEEL_Y;
                angle = (float) Math.atan2(deltaY, deltaX);

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    hasClickedOnRing = false;
                    hasClickedOnSquare = false;
                } else {
                    if (((Math.abs(deltaX) < Math.abs(dpsToPixels(Constants.WHEEL_DPS/2) * Math.cos(angle)) &&  (Math.abs(deltaX) > Math.abs(dpsToPixels((Constants.WHEEL_DPS-Constants.COLOR_BAR_DPS*1.75f)/2) * Math.cos(angle))))
                            || hasClickedOnRing) && !hasClickedOnSquare) {
                        handleOuterWheel(angle);
                    } else if ((Math.abs(deltaX) < dpsToPixels(Constants.SQUARE_DPS/2) && Math.abs(deltaY) < dpsToPixels(Constants.SQUARE_DPS/2)) || hasClickedOnSquare ) {
                        handleSquare(event, deltaX, deltaY);
                    }
                }
                return true;
            }
        });
    }

    private void handleOuterWheel(float angle) {
        float x = CENTER_WHEEL_X + dpsToPixels((Constants.WHEEL_DPS)/2.0f - Constants.COLOR_BAR_DPS/2.0f) * (float) Math.cos(angle)
                - dpsToPixels((Constants.COLOR_BAR_DPS)/2.0f);
        float y = CENTER_WHEEL_Y + dpsToPixels((Constants.WHEEL_DPS)/2.0f- Constants.COLOR_BAR_DPS/2.0f) * (float) Math.sin(angle)
                - dpsToPixels(Constants.COLOR_BAR_DPS/2.0f);

        colorBar.setX(x);
        colorBar.setY(y);
        colorBar.setRotation(angle * 180 / (float) Math.PI);

        handleChangeColor(angle);

        hasClickedOnRing = true;
        hasClickedOnSquare = false;
    }

    private void handleSquare(MotionEvent event, float deltaX, float deltaY) {
        float tmpX = event.getX() - dpsToPixels(Constants.CIRCLE_DPS/2);
        float tmpY = event.getY() - dpsToPixels(Constants.CIRCLE_DPS/2);

        if (deltaX < -dpsToPixels(Constants.INNER_SQUARE_DPS/innerSquareMargin))
            tmpX = CENTER_WHEEL_X - dpsToPixels(Constants.INNER_SQUARE_DPS/innerSquareMargin + Constants.CIRCLE_DPS/2) ;
        else if (deltaX > dpsToPixels(Constants.INNER_SQUARE_DPS/innerSquareMargin))
            tmpX = CENTER_WHEEL_X + dpsToPixels(Constants.INNER_SQUARE_DPS/innerSquareMargin - Constants.CIRCLE_DPS/2) ;

        if (deltaY < -dpsToPixels(Constants.INNER_SQUARE_DPS/innerSquareMargin))
            tmpY = CENTER_WHEEL_Y - dpsToPixels(Constants.INNER_SQUARE_DPS/innerSquareMargin + Constants.CIRCLE_DPS/2) ;
        else if (deltaY > dpsToPixels(Constants.INNER_SQUARE_DPS/innerSquareMargin))
            tmpY = CENTER_WHEEL_Y + dpsToPixels(Constants.INNER_SQUARE_DPS/innerSquareMargin - Constants.CIRCLE_DPS/2);


        circle.setX(tmpX);
        circle.setY(tmpY);

        squareCenter.setColor(getPreciseColor());

        hasClickedOnSquare = true;
        hasClickedOnRing = false;
    }

    public void handleChangeColor(float angle) {
        square.setColor(getColorAngle(angle));
        squareCenter.setColor(getPreciseColor());
    }

    private int getColorAngle(float angle) {
        bitmap = touchView.getDrawingCache();
        float x = CENTER_WHEEL_X + dpsToPixels((Constants.WHEEL_DPS)/2.0f - Constants.COLOR_BAR_DPS/2.0f) * (float) Math.cos(angle);
        float y = CENTER_WHEEL_Y + dpsToPixels((Constants.WHEEL_DPS)/2.0f- Constants.COLOR_BAR_DPS/2.0f) * (float) Math.sin(angle);
        int pixel = bitmap.getPixel((int) x,(int) y);

        int r = Color.red(pixel);
        int g = Color.green(pixel);
        int b = Color.blue(pixel);

        int color = Color.rgb(r,g,b);
        return color;
    }

    private int getPreciseColor() {
        bitmap = touchView.getDrawingCache();
        float x = circle.getX()+ dpsToPixels(Constants.CIRCLE_DPS/2.0f);
        float y = circle.getY()+ dpsToPixels(Constants.CIRCLE_DPS/2.0f);
        int pixel = bitmap.getPixel((int) x,(int) y);

        int r = Color.red(pixel);
        int g = Color.green(pixel);
        int b = Color.blue(pixel);

        int color = Color.rgb(r,g,b);
        return color;
    }

    public int dpsToPixels(float dps) {
        return (int) (dps * SCALE + 0.5f);
    }

    public void handleComplementaryColors() {

    }

}
/*
 TODO LIST :
    TODO Mudar Imagem do circulo para algo mais legível, e pouco controlo. Relembrar ideia de color picker que se mexe com o angulo
    TODO Ângulo muda quando se põe em modo Landscape
    TODO Fazer modo Landscape
    TODO Apresentar esquemas complementares
    TODO Fazer design de esquemas complementares
 */

/*
  BUGS CONHECIDOS :
    XLARGE MDPI Problemas nas cores


 */