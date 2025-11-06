package org.eternity.step01.start.loan;

import org.eternity.shared.Money;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Facility {
    private Loan loan;
    private Map<Company, Share> shares = new HashMap<>();

    public static Facility crate(Map<Company, Share> investments) {
        return new Facility(investments);
    }

    private Facility( Map<Company, Share> shares) {
        if (!sum(shares.values()).isGreaterThan(Money.won(0))) {
            throw new IllegalArgumentException();
        }

        this.shares = shares;
        this.loan = new Loan(shares.keySet());
    }

    public void takeOutLoan(Money amount) {
        if (loan.amount().plus(amount).isGreaterThan(limit())) {
            throw new IllegalStateException();
        }

        Map<Company, Share> sharesToAdd = new HashMap<>();
        for(var owner : shares.keySet()) {
            Money amountToAdd = amount.times(shares.get(owner).amount()).divide(limit());
            sharesToAdd.put(owner, new Share(owner, amountToAdd));
        }

        loan.increase(sharesToAdd);
    }

    private Money limit() {
        return sum(shares.values());
    }

    private Money sum(Collection<Share> shares) {
        return shares.stream().map(Share::amount).reduce(Money.ZERO, Money::plus);
    }

    public Money investAmountOf(Company company) {
        return loan.investAmountOf(company);
    }

    // 현재 source 금액에서 몇 percent를 target으로 옮길 것인가
    public void transfer(Company source, Company destination, double percent) {
        Money amount = loan.investAmountOf(source).times(percent);

        loan.adjustShare(new Share(source, amount.negate()));
        loan.adjustShare(new Share(destination, amount));
    }

    public Map<Company, Share> distributePrincipalPayment(Money amount) {
        return loan.distributePrincipalPayment(amount);
    }
}

