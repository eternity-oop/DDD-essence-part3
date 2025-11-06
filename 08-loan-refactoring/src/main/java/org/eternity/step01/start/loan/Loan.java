package org.eternity.step01.start.loan;

import org.eternity.shared.Money;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Loan {
    private Map<Company, Share> shares = new HashMap<>();

    public Loan(Set<Company> companies) {
        for(var each : companies) {
            shares.put(each, new Share(each, Money.ZERO));
        }
    }

    public Money amount() {
        return shares.values().stream()
                .map(share -> share.amount())
                .reduce(Money.ZERO, Money::plus);
    }

    public void increase(Map<Company, Share> sharesToAdd) {
        for(var owner : sharesToAdd.keySet()) {
            Money amountToAdd = sharesToAdd.get(owner).amount().plus(shares.get(owner).amount());
            shares.put(owner, new Share(owner, amountToAdd));
        }
    }

    public void adjustShare(Share base) {
        Money currentAmount = shares.get(base.company()).amount();
        Money paymentShare = currentAmount.plus(base.amount());
        shares.put(base.company(), new Share(base.company(), paymentShare));
    }

    public Money investAmountOf(Company company) {
        return shares.get(company).amount();
    }

    public Map<Company, Share> distributePrincipalPayment(Money amount) {
        Map<Company, Share> paymentShares = new HashMap<>();
        Money total = amount();

        for(var owner : shares.keySet()) {
            Money initialShareAmount = shares.get(owner).amount();
            Money paymentShareAmount = amount.times(initialShareAmount).divide(total);
            paymentShares.put(owner, new Share(owner, paymentShareAmount));

            Money newLoanShareAmount = initialShareAmount.minus(paymentShareAmount);
            shares.put(owner, new Share(owner, newLoanShareAmount));
        }

        return paymentShares;
    }
}
