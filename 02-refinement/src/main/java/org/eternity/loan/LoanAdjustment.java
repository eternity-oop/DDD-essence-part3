package org.eternity.loan;

import org.eternity.shared.monetary.Money;

public class LoanAdjustment extends LoanInvestment {
    private LoanInvestment original;

    public LoanAdjustment(LoanInvestment loanInvestment, Money amount) {
        super(loanInvestment.investment(), loanInvestment.amount().plus(amount));
        this.original = loanInvestment;
    }
}
