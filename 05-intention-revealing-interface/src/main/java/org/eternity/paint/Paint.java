package org.eternity.paint;

public class Paint {
    private double volume;
    private int red;
    private int yellow;
    private int blue;

    public Paint(double volume, int red, int yellow, int blue) {
        this.volume = volume;
        this.red = red;
        this.yellow = yellow;
        this.blue = blue;
    }

    public double getVolume() {
        return volume;
    }

    public int getRed() {
        return red;
    }

    public int getYellow() {
        return yellow;
    }

    public int getBlue() {
        return blue;
    }

    public void mixin(Paint paint) {
        double totalV = volume + paint.getVolume();

        // 생상 혼합 로직
        red = (int) Math.round((red * volume + paint.getRed() * paint.getVolume()) / totalV);
        yellow = (int) Math.round((yellow * volume + paint.getYellow() * paint.getVolume()) / totalV);
        blue = (int) Math.round((blue * volume + paint.getBlue() * paint.getVolume()) / totalV);

        volume = totalV;
    }
}
