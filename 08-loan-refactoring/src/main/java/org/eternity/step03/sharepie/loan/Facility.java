package org.eternity.step03.sharepie.loan;

import org.eternity.shared.Money;

import java.util.Map;

public class Facility {
    private Loan loan;
    private SharePie shares;

    public static Facility crate(SharePie shares) {
        return new Facility(shares);
    }

    private Facility(SharePie shares) {
        if (!shares.amount().isGreaterThan(Money.ZERO)) {
            throw new IllegalArgumentException();
        }

        this.shares = shares;
        this.loan = new Loan(new SharePie(shares.owners()));
    }
    
    public void takeOutLoan(Money amount) {
        if (loan.amount().plus(amount).isGreaterThan(shares.amount())) {
            throw new IllegalStateException();
        }

        loan.increase(shares.prorated(amount));
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
        Map<Company, Share> distribution = loan.calculatePrinciplePaymentShares(amount);

        loan.applyPrinciplePaymentShares(distribution);

        return distribution;
    }
}

