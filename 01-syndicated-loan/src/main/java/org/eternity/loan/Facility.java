package org.eternity.loan;

import org.eternity.shared.domain.AggregateRoot;
import org.eternity.shared.monetary.Money;

import java.util.HashSet;
import java.util.Set;

public class Facility extends AggregateRoot<Facility, Long> {
    private Long id;
    private Money limit;
    private Loan loan;
    private Set<Investment> investments = new HashSet<>();

    public static Facility crate(Money limit, Set<Investment> investments) {
        return new Facility(limit, investments);
    }

    private Facility(Money limit, Set<Investment> investments) {
        if (!limit.isGreaterThan(Money.won(0))) {
            throw new IllegalArgumentException();
        }

        if (investments.stream().mapToDouble(Investment::percentage).sum() != 1) {
            throw new IllegalArgumentException();
        }

        this.loan = new Loan(investments);
        this.limit = limit;
        this.investments = investments;
    }

    public void takeOutLoan(Money amount) {
        if (loan.amount().plus(amount).isGreaterThan(limit)) {
            throw new IllegalStateException();
        }

        loan.increase(amount);
    }

    public Money investAmountOf(Company company) {
        return loan.investAmountOf(company);
    }

    @Override
    public Long getId() {
        return id;
    }
}
