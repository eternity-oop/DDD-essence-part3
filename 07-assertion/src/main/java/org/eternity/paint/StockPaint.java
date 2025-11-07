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
}
