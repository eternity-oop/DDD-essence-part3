package org.eternity.loan;

import org.eternity.shared.monetary.Money;

public class LoanInvestment {
    private Investment investment;
    private Money amount;

    public LoanInvestment(Investment investment) {
        this(investment, Money.ZERO);
    }

    public LoanInvestment(Investment investment, Money amount) {
        this.investment = investment;
        this.amount = amount;
    }

    public LoanInvestment distribute(Money total) {
        return new LoanInvestment(investment, this.amount.plus(investment.calculateShare(total)));
    }

    public boolean loanOf(Company company) {
        return this.investment.investmentOf(company);
    }

    public Money amount() {
        return amount;
    }

    public Investment investment() {
        return investment;
    }
}
