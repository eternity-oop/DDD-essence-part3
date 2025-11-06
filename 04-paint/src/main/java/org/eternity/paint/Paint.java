package org.eternity.paint;

public class Paint {
    private double v;
    private int r;
    private int y;
    private int b;

    public Paint(double v, int r, int y, int b) {
        this.v = v;
        this.r = r;
        this.y = y;
        this.b = b;
    }

    public double getV() {
        return v;
    }

    public int getR() {
        return r;
    }

    public int getY() {
        return y;
    }

    public int getB() {
        return b;
    }

    public void paint(Paint paint) {
        double totalV = v + paint.getV();

        // 생상 혼합 로직
        r = (int) Math.round((r * v + paint.getR() * paint.getV()) / totalV);
        y = (int) Math.round((y * v + paint.getY() * paint.getV()) / totalV);
        b = (int) Math.round((b * v + paint.getB() * paint.getV()) / totalV);

        v = totalV;
    }
}
