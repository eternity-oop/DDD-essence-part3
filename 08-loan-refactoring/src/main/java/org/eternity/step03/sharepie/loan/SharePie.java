package org.eternity.step03.sharepie.loan;

import org.eternity.shared.Money;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SharePie {
    private Map<Company, Share> shares = new HashMap<>();

    public SharePie(Set<Company> companies) {
        for (var each : companies) {
            shares.put(each, new Share(each, Money.ZERO));
        }
    }

    public SharePie(Map<Company, Share> shares) {
        this.shares = shares;
    }

    public void increase(Map<Company, Share> paymentShares) {
        for (var owner : paymentShares.keySet()) {
            Share paymentShare = paymentShares.get(owner);
            Money newLoanShareAmount = shares.get(owner).amount().plus(paymentShare.amount());
            shares.put(owner, new Share(owner, newLoanShareAmount));
        }
    }

    public void decrease(Map<Company, Share> paymentShares) {
        for (var owner : paymentShares.keySet()) {
            Share paymentShare = paymentShares.get(owner);
            Money newLoanShareAmount = shares.get(owner).amount().minus(paymentShare.amount());
            shares.put(owner, new Share(owner, newLoanShareAmount));
        }
    }

    public Map<Company, Share> prorated(Money amount) {
        Map<Company, Share> paymentShares = new HashMap<>();
        Money total = amount();

        for (var owner : shares.keySet()) {
            Money initialShareAmount = shares.get(owner).amount();
            Money paymentShareAmount = amount.times(initialShareAmount).divide(total);
            paymentShares.put(owner, new Share(owner, paymentShareAmount));
        }

        return paymentShares;
    }

    public Money amount() {
        return shares.values().stream()
                .map(Share::amount)
                .reduce(Money.ZERO, Money::plus);
    }

    public Money amountOf(Company company) {
        return shares.get(company).amount();
    }

    public Set<Company> owners() {
        return shares.keySet();
    }
}
