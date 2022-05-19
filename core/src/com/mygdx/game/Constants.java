package com.mygdx.game;

public enum Constants {

    PPM(120f),
    EPSILON((float)Math.pow(10,-6)),
    VIEWPORT_WIDTH(1080),
    VIEWPORT_HEIGHT(1920);

    private float value;

    private Constants(float value){
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
