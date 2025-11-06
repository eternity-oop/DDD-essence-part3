package org.eternity.paint;

import java.util.ArrayList;
import java.util.List;

public class MixedPaint implements Paint {
    private List<StockPaint> paints = new ArrayList<>();

    @Override
    public double getVolume() {
        if (paints.isEmpty()) {
            throw new IllegalStateException();
        }

        return paints.stream().mapToDouble(StockPaint::getVolume).sum();
    }

    @Override
    public PigmentColor getColor() {
        if (paints.isEmpty()) {
            throw new IllegalStateException();
        }

        PigmentColor result = paints.get(0).getColor();
        for(var paint : paints.subList(1, paints.size())) {
            result = result.mixedWith(paint.getColor(), paint.getVolume() / getVolume());
        }
        return result;
    }

    public void mixIn(StockPaint stockPaint) {
        paints.add(stockPaint);
    }
}
