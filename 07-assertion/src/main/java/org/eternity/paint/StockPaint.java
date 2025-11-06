package org.eternity.paint;

public class StockPaint implements Paint {
    private double volume;
    private PigmentColor color;

    public StockPaint(double volume, PigmentColor color) {
        this.volume = volume;
        this.color = color;
    }

    @Override
    public double getVolume() {
        return volume;
    }

    @Override
    public PigmentColor getColor() {
        return color;
    }

    public void mixin(Paint other) {
        volume = volume + other.getVolume();
        double ratio = other.getVolume() / volume;
        color = color.mixedWith(other.getColor(), ratio);
    }
}
