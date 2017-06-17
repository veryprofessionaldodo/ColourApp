package com.example.petre.colour;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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
    private ImageView circle;
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

                float delta_x = event.getX() - pixelWidth / 2.0f;
                float delta_y = event.getY() - .458f * pixelHeight;
                System.out.println(delta_x + " " + delta_y);
                System.out.println(pixelWidth / 2.0f + " " + .458f * pixelHeight);
                float angle = (float) Math.atan2(delta_y, delta_x);

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    hasClickedOnRing = false;
                    hasClickedOnSquare = false;

                } else {
                    if (event.getY() > 10 || hasClickedOnRing) { // TODO Aqui é a imagem da roda
                        handleChangeColor(event);
                        hasClickedOnRing = true;
                    } else if (event.getX() > 10) { // TODO Aqui é a imagem do quadrado
                        hasClickedOnSquare = true;
                    }
                }
                return true;
            }


        });

    }

    public void handleChangeColor(MotionEvent event) {
        bitmap = touchView.getDrawingCache();

        circle.setY(event.getY() - dpsToPixels(Constants.CIRCLE_DPS/2));
        circle.setX(event.getX() - dpsToPixels(Constants.CIRCLE_DPS/2));

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
Matrix matrix = new Matrix();
imageView.setScaleType(ImageView.ScaleType.MATRIX);   //required
matrix.postRotate((float) angle, pivotX, pivotY);
imageView.setImageMatrix(matrix);
 */

/*
 TODO LIST :
    TODO COORDENADAS NA RODA
    TODO COORDENADAS NO QUADRADO
    TODO PROBLEMA NO NEXUS 6, PROVAVELMENTE XXXHDPI DEMASIADO PEQUENO
 */