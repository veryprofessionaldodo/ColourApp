package com.example.petre.colour;

import android.content.res.Resources;

public class Constants {
    public static float CIRCLE_DPS;
    public static float WHEEL_DPS;
    public static float SQUARE_DPS;
    public static float COLOR_BAR_DPS;

    public Constants(Resources r) {
        CIRCLE_DPS = r.getDimension(R.dimen.circle)/r.getDisplayMetrics().density;
        WHEEL_DPS = r.getDimension(R.dimen.part_wheel)/r.getDisplayMetrics().density;
        SQUARE_DPS = r.getDimension(R.dimen.square)/r.getDisplayMetrics().density;
        COLOR_BAR_DPS = r.getDimension(R.dimen.color)/r.getDisplayMetrics().density;
    }
}
