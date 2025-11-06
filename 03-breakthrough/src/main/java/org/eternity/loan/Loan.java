package org.eternity.loan;

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
}
