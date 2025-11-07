package org.eternity.step04_conceptual_contour.loan;

import org.eternity.step04_conceptual_contour.shared.domain.DomainEntity;
import org.eternity.step04_conceptual_contour.shared.monetary.Money;

import java.util.Set;

public class Loan extends DomainEntity<Loan, Long> {
    private Long id;
    private SharePie sharePie;

    public Loan(Set<Company> companies) {
        this.sharePie = new SharePie(companies);
    }

    public Money amount() {
        return sharePie.sum();
    }

    public void increase(SharePie other) {
        this.sharePie = this.sharePie.plus(other);
    }

    public SharePie calculateRepayments(Money amount) {
        return sharePie.prorate(amount);
    }

    public void applyRepayments(SharePie repayments) {
        this.sharePie = this.sharePie.minus(repayments);
    }

    public Money investAmountOf(Company company) {
        return sharePie.amountOf(company);
    }

    @Override
    public Long getId() {
        return id;
    }
}
