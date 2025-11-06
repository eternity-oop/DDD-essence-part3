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

    private Facility(Money limit, Loan loan, Set<Investment> investments) {
        this.limit = limit;
        this.loan = loan;
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

    // 현재 source 금액에서 몇 percent를 target으로 옮길 것인가
    public void transfer(Company source, Company destination, double percent) {
        Money amount = loan.investAmountOf(source).times(percent);

        loan.adjustShare(source, amount.negate());
        loan.adjustShare(destination, amount);
    }

    @Override
    public Long getId() {
        return id;
    }
}

