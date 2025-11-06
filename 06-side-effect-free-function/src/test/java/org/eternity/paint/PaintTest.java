package org.eternity.paint;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PaintTest {
    @Test
    public void paint() {
        // 용량이 100인 순수한 노란색 페인트로 시작한다.
        Paint outPaint = new Paint(100.0, new PigmentColor(0, 50, 0));

        // 용량이 100인 순수한 파란색 페인트를 생성한다.
        Paint blue = new Paint(100.0, new PigmentColor(0, 0, 50));

        // 노란색 페인트에 페인트를 혼합한다.
        outPaint.mixin(blue);

        assertThat(outPaint.getVolume()).isEqualTo(200.0);
        assertThat(outPaint.getColor()).isEqualTo(new PigmentColor(0, 25, 25));
    }
}
