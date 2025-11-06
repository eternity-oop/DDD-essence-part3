package org.eternity.shared;

import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@ToString
public class Money implements Comparable<Money> {
    public static final Money ZERO = Money.won(0);

    public static Money won(long amount) {
        return won(BigDecimal.valueOf(amount));
    }

    public static Money won(double amount) {
        return won(BigDecimal.valueOf(amount));
    }

    public static Money won(BigDecimal amount) {
        return new Money(amount);
    }

    private final BigDecimal amount;

    private Money(BigDecimal amount) {
        this.amount = amount;
    }

    public Money plus(Money other) {
        return new Money(amount.add(other.amount));
    }

    public Money minus(Money other) {
        return new Money(amount.subtract(other.amount));
    }

    public Money divide(Money other) {
        return new Money(amount.divide(other.amount, RoundingMode.HALF_EVEN));
    }

    public Money divide(double divisor) {
        return new Money(amount.divide(BigDecimal.valueOf(divisor), RoundingMode.HALF_EVEN));
    }

    public Money times(Money times) {
        return new Money(amount.multiply(times.value()).setScale(0, RoundingMode.HALF_EVEN));
    }

    public Money times(double times) {
        return new Money(amount.multiply(BigDecimal.valueOf(times)).setScale(0, RoundingMode.HALF_EVEN));
    }

    public boolean isGreaterThan(Money other) {
        return compareTo(other) > 0;
    }

    @Override
    public int compareTo(Money other) {
        return this.amount.compareTo(other.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return compareTo(money) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }

    public Money negate() {
        return new Money(amount.negate());
    }

    public double doubleValue() {
        return amount.doubleValue();
    }

    public BigDecimal value() {
        return amount;
    }
}