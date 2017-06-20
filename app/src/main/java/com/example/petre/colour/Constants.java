package com.example.petre.colour;

import android.content.res.Resources;

public class Constants {
    public static float CIRCLE_DPS;
    public static float WHEEL_DPS;
    public static float SQUARE_DPS;
    public static float INNER_SQUARE_DPS;
    public static float COLOR_BAR_DPS;

    public static final float COMPLEMENTARY = 1;
    public static final float ANALOGOUS = 2;
    public static final float TRIADIC = 3;
    public static final float SPLIT_COMPLEMENTARY = 4;
    public static final float TETRADIC = 5;

    public Constants(Resources r) {
        CIRCLE_DPS = r.getDimension(R.dimen.circle)/r.getDisplayMetrics().density;
        WHEEL_DPS = r.getDimension(R.dimen.part_wheel)/r.getDisplayMetrics().density;
        SQUARE_DPS = r.getDimension(R.dimen.square)/r.getDisplayMetrics().density;
        COLOR_BAR_DPS = r.getDimension(R.dimen.color)/r.getDisplayMetrics().density;
        INNER_SQUARE_DPS = r.getDimension(R.dimen.inner_square)/r.getDisplayMetrics().density;

    }
}
