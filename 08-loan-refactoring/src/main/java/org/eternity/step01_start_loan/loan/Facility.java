package org.eternity.step01_start_loan.loan;

import org.eternity.step01_start_loan.shared.domain.AggregateRoot;
import org.eternity.step01_start_loan.shared.monetary.Money;

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

    public Set<Share> repay(Money amount) {
        return loan.distributePrincipalPayment(amount);
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

