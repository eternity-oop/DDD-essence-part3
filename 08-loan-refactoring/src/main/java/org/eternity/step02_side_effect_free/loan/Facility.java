package org.eternity.step02_side_effect_free.loan;

import org.eternity.step02_side_effect_free.shared.domain.AggregateRoot;
import org.eternity.step02_side_effect_free.shared.monetary.Money;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Facility extends AggregateRoot<Facility, Long> {
    private Long id;
    private Loan loan;
    private Map<Company, Share> shares = new HashMap<>();

    public static Facility crate(Share... shares) {
        return new Facility(Arrays.stream(shares).collect(Collectors.toSet()));
    }

    private Facility(Set<Share> shares) {
        if (!sum(shares).isGreaterThan(Money.won(0))) {
            throw new IllegalArgumentException();
        }

        this.shares = shares.stream().collect(Collectors.toMap(Share::company, Function.identity()));
        this.loan = new Loan(this.shares.keySet());
    }

    public void takeOutLoan(Money amount) {
        if (loan.amount().plus(amount).isGreaterThan(limit())) {
            throw new IllegalStateException();
        }

        Map<Company, Share> sharesToAdd = new HashMap<>();
        for(var owner : shares.keySet()) {
            sharesToAdd.put(owner, shares.get(owner).prorate(amount, limit()));
        }

        loan.increase(sharesToAdd);
    }

    public void takeOutLoan(Share... shares) {
        if (loan.amount().plus(sum(Arrays.stream(shares).toList())).isGreaterThan(limit())) {
            throw new IllegalStateException();
        }

        loan.increase(Arrays.stream(shares).collect(Collectors.toMap(Share::company, Function.identity())));
    }

    public Set<Share> estimateRepayments(Money amount) {
        return loan.calculateRepayments(amount);
    }

    public void repay(Money amount) {
        loan.applyRepayments(loan.calculateRepayments(amount));
    }

    private Money limit() {
        return sum(shares.values());
    }

    private Money sum(Collection<Share> shares) {
        return shares.stream().map(Share::amount).reduce(Money.ZERO, Money::plus);
    }

    public Money investAmountOf(Company company) {
        return loan.investAmountOf(company);
    }

    @Override
    public Long getId() {
        return id;
    }
}

