package org.eternity.step03_sharepie.loan;

import org.eternity.step03_sharepie.shared.domain.AggregateRoot;
import org.eternity.step03_sharepie.shared.monetary.Money;

public class Facility extends AggregateRoot<Facility, Long> {
    private Long id;
    private Loan loan;
    private SharePie sharePie;

    public static Facility crate(SharePie sharePie) {
        return new Facility(sharePie);
    }

    private Facility(SharePie sharePie) {
        if (!sharePie.sum().isGreaterThan(Money.ZERO)) {
            throw new IllegalArgumentException();
        }

        this.sharePie = sharePie;
        this.loan = new Loan(sharePie.owners());
    }

    public void takeOutLoan(Money amount) {
        if (loan.amount().plus(amount).isGreaterThan(sharePie.sum())) {
            throw new IllegalStateException();
        }

        loan.increase(this.sharePie.prorate(amount));
    }

    public void takeOutLoan(SharePie sharePie) {
        if (loan.amount().plus(sharePie.sum()).isGreaterThan(this.sharePie.sum())) {
            throw new IllegalStateException();
        }

        loan.increase(sharePie);
    }

    public SharePie estimateRepayments(Money amount) {
        return loan.calculateRepayments(amount);
    }

    public void repay(Money amount) {
        loan.applyRepayments(loan.calculateRepayments(amount));
    }

    public Money investAmountOf(Company company) {
        return loan.investAmountOf(company);
    }

    @Override
    public Long getId() {
        return id;
    }
}

