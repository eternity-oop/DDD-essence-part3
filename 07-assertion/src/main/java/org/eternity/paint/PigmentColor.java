package org.eternity.paint;

import java.util.Objects;

public class PigmentColor {
    private int red;
    private int yellow;
    private int blue;

    public PigmentColor(int red, int yellow, int blue) {
        this.red = red;
        this.yellow = yellow;
        this.blue = blue;
    }

    public PigmentColor mixedWith(PigmentColor other, double ratio) {
        // 생상 혼합 로직

        int red = (int)Math.round((this.red + other.red) * ratio);
        int yellow = (int)Math.round((this.yellow + other.yellow) * ratio);
        int blue = (int)Math.round((this.blue + other.blue) * ratio);

        return new PigmentColor(red, yellow, blue);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PigmentColor that = (PigmentColor) o;
        return red == that.red && yellow == that.yellow && blue == that.blue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(red, yellow, blue);
    }

    @Override
    public String toString() {
        return "PigmentColor{" +
                "red=" + red +
                ", yellow=" + yellow +
                ", blue=" + blue +
                '}';
    }
}
