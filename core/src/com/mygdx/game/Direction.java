package com.mygdx.game;

public enum Direction {
    STAND(0), RIGHT(1), LEFT(-1);
    private int value;
    private Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}