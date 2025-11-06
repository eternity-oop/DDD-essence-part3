package org.eternity.step05.conceptual_contour.loan;

import org.eternity.shared.Money;

import java.util.Map;

public class Loan {
    private SharePie shares;

    public Loan(SharePie shares) {
        this.shares = shares;
    }

    public Money amount() {
        return shares.amount();
    }

    public void increase(SharePie sharesToAdd) {
        this.shares = shares.plus(sharesToAdd);
    }

    public void adjustShare(Share base) {
        this.shares = shares.plus(new SharePie(Map.of(base.company(), base)));
    }

    public Money investAmountOf(Company company) {
        return shares.amountOf(company);
    }

    public void applyPrinciplePaymentShares(SharePie paymentShares) {
        this.shares = shares.minus(paymentShares);
    }

    public SharePie calculatePrinciplePaymentShares(Money amount) {
        return shares.prorated(amount);
    }
}
