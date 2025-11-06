package org.eternity.paint;

public class Paint {
    private double volume;
    private PigmentColor color;

    public Paint(double volume, PigmentColor color) {
        this.volume = volume;
        this.color = color;
    }

    public double getVolume() {
        return volume;
    }

    public PigmentColor getColor() {
        return color;
    }

    public void mixin(Paint other) {
        volume = volume + other.getVolume();
        double ratio = other.getVolume() / volume;
        color = color.mixedWith(other.getColor(), ratio);
    }
}
