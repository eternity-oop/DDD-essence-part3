package org.eternity.paint;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PaintTest {
    @Test
    public void paint() {
        PigmentColor yellow = new PigmentColor(0, 50, 0);
        PigmentColor blue = new PigmentColor(0, 0, 50);

        StockPaint paint1 = new StockPaint(100.0, yellow);
        StockPaint paint2 = new StockPaint(100.0, blue);
        MixedPaint mix = new MixedPaint();

        mix.mixIn(paint1);
        mix.mixIn(paint2);

        assertThat(mix.getVolume()).isEqualTo(200.0);
        assertThat(mix.getColor()).isEqualTo(new PigmentColor(0, 25, 25));

        assertThat(paint1.getVolume()).isEqualTo(100);
        assertThat(paint2.getVolume()).isEqualTo(100);
    }
}
