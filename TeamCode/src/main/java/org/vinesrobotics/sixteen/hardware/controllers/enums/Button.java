package org.vinesrobotics.sixteen.hardware.controllers.enums;

/**
 * Created by Vines HS Robotics on 11/4/2016.
 */

/**
 * Button enum. Used to retrieve the pressed buttons on a controller. Can have a value.
 */
public enum Button {
    A(Side.NA,Type.BUTTON),
    B(Side.NA,Type.BUTTON),
    X(Side.NA,Type.BUTTON),
    Y(Side.NA,Type.BUTTON),
    RS(Side.RIGHT,Type.STICK),
    LS(Side.LEFT,Type.STICK),
    RB(Side.RIGHT,Type.BUMPER),
    LB(Side.LEFT,Type.BUMPER),
    RT(Side.RIGHT,Type.TRIGGER),
    LT(Side.LEFT,Type.TRIGGER),
    START(Side.NA,Type.BUTTON),
    BACK(Side.NA,Type.BUTTON),
    LEFT(Side.NA,Type.DPAD),
    RIGHT(Side.NA,Type.DPAD),
    UP(Side.NA,Type.DPAD),
    DOWN(Side.NA,Type.DPAD);

    private Side s;
    private Type t;
    public float value = 0;

    /**
     * Gets the side of the button, if applicable.
     * @return side
     */
    public Side side() {
        return s;
    }
    /**
     * Gets the type of button
     * @return type
     */
    public Type type() {
        return t;
    }

    Button(Side side, Type type) {
        s = side;
        t = type;
    }
}

enum Side {
    RIGHT,
    LEFT,
    NA
}
enum Type {
    DPAD,
    BUMPER,
    TRIGGER,
    STICK,
    BUTTON
}

