package org.eternity.step01_start_loan.loan;

import org.eternity.step01_start_loan.shared.domain.DomainEntity;
import org.eternity.step01_start_loan.shared.monetary.Money;

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

    public Set<Share> distributePrincipalPayment(Money amount) {
        Set<Share> result = new HashSet<>();

        for(var owner : shares.keySet()) {
            Share paymentShare = shares.get(owner).prorate(amount, amount());
            result.add(paymentShare);
            shares.put(owner, shares.get(owner).minus(paymentShare));
        }

        return result;
    }

    public Money investAmountOf(Company company) {
        return shares.get(company).amount();
    }

    @Override
    public Long getId() {
        return id;
    }
}
