package com.example.petre.colour;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

/**
 * Created by petre on 17/06/2017.
 */

public class Constants {

    public static float CIRCLE_DPS;
    public static float WHEEL_DPS;
    public static float SQUARE_DPS;
    public static float COLOR_BAR_DPS;


    public Constants(Resources r) {
        CIRCLE_DPS = r.getDimension(R.dimen.circle);
        WHEEL_DPS = r.getDimension(R.dimen.part_wheel);
        SQUARE_DPS = r.getDimension(R.dimen.square);
        COLOR_BAR_DPS = r.getDimension(R.dimen.color);
        System.out.println(CIRCLE_DPS + " " +  WHEEL_DPS + " " + SQUARE_DPS + " " + COLOR_BAR_DPS);
    }
}
