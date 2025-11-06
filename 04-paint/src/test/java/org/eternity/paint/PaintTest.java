package org.eternity.paint;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PaintTest {
    @Test
    public void paint() {
        // 용량이 100인 순수한 노란색 페인트를 생성한다.
        Paint yellow = new Paint(100.0, 0, 50, 0);

        // 용량이 100인 순수한 파란색 페인트를 생성한다.
        Paint blue = new Paint(100.0, 0, 0, 50);

        // 노란색 페인트에 페인트를 혼합한다.
        yellow.paint(blue);

        assertThat(yellow.getV()).isEqualTo(200.0);
        assertThat(yellow.getB()).isEqualTo(25);
        assertThat(yellow.getY()).isEqualTo(25);
        assertThat(yellow.getR()).isEqualTo(0);
    }
}
