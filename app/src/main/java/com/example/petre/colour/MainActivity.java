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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        square = (Square) findViewById(R.id.square_color);
        square.setColor(Color.BLUE);

        touchView = findViewById(R.id.full);

        touchView.setDrawingCacheEnabled(true);
        touchView.buildDrawingCache();


        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        touchView.setSystemUiVisibility(uiOptions);
        circle = (ImageView) findViewById(R.id.circle);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        circle.setY(.458f * height/2 - circle.getHeight()/2);
        circle.setX(.5f* width/2 - circle.getWidth()/2);

        ImageView img = (ImageView) findViewById(R.id.square_outline);
        System.out.println("X " + img.getX() + (float)img.getWidth()/2.0f + " Y " + img.getY() + (float) img.getHeight()/2.0f);

        setTouchListener();

        ImageView background = (ImageView) findViewById(R.id.background);

        background.setScaleType(ImageView.ScaleType.FIT_XY);

    }

    public void setTouchListener() {
        touchView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getY() > 10) { // TODO Aqui é a imagem da roda
                    bitmap = touchView.getDrawingCache();

                    circle.setY(event.getY() - circle.getHeight() / 2);
                    circle.setX(event.getX() - circle.getWidth() / 2);

                    int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());

                    int r = Color.red(pixel);
                    int g = Color.green(pixel);
                    int b = Color.blue(pixel);

                    square.setColor(Color.rgb(r, g, b));
                }
                else if (event.getX() > 10){ // TODO Aqui é a imagem do quadrado

                }
                return true;
            }

        });

    }
}
/*
Matrix matrix = new Matrix();
imageView.setScaleType(ImageView.ScaleType.MATRIX);   //required
matrix.postRotate((float) angle, pivotX, pivotY);
imageView.setImageMatrix(matrix);
 */