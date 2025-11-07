package org.eternity.step02_side_effect_free.loan;

import org.eternity.step02_side_effect_free.shared.domain.DomainEntity;
import org.eternity.step02_side_effect_free.shared.monetary.Money;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Loan extends DomainEntity<Loan, Long> {
    private Long id;
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
            shares.put(owner, shares.get(owner).plus(sharesToAdd.get(owner)));
        }
    }

    public Set<Share> calculateRepayments(Money amount) {
        Set<Share> result = new HashSet<>();

        for(var owner : shares.keySet()) {
            result.add(shares.get(owner).prorate(amount, amount()));
        }

        return result;
    }

    public void applyRepayments(Set<Share> paymentShares) {
        for(var paymentShare : paymentShares) {
            shares.put(paymentShare.company(),
            shares.get(paymentShare.company()).minus(paymentShare));
        }
    }

    public Money investAmountOf(Company company) {
        return shares.get(company).amount();
    }

    @Override
    public Long getId() {
        return id;
    }
}
