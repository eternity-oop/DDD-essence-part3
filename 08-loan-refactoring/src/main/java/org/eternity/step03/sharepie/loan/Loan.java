package org.eternity.step03.sharepie.loan;

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

    public void increase(Map<Company, Share> sharesToAdd) {
        shares.increase(sharesToAdd);
    }

    public void adjustShare(Share base) {
        shares.increase(Map.of(base.company(), base));
    }

    public Money investAmountOf(Company company) {
        return shares.amountOf(company);
    }

    public void applyPrinciplePaymentShares(Map<Company, Share> paymentShares) {
        shares.decrease(paymentShares);
    }

    public Map<Company, Share> calculatePrinciplePaymentShares(Money amount) {
        return shares.prorated(amount);
    }
}
