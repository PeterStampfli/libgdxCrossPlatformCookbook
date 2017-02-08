package com.mygdx.game.utilities;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by peter on 1/19/17.
 */

public class Constants {

    public static final int WORLD_WIDTH=960;
    public static final int WORLD_HEIGHT=544;
    public static final float METER=1.0f/32f;     // conversion factor: meters per world unit

    public static final float MAX_STRENGHT=15;
    public static final float MAX_DISTANCE=100;
    public static final float UPPER_ANGLE=3* MathUtils.PI*0.5f;
    public static final float LOWER_ANGLE=MathUtils.PI*0.5f;


}
