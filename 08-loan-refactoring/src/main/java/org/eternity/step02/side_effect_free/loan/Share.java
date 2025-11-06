package org.eternity.step02.side_effect_free.loan;

import org.eternity.shared.Money;

public class Share {
    private Company company;
    private Money amount;

    public Share(Company company, double amount) {
        this(company, Money.won(amount));
    }

    public Share(Company company, Money amount) {
        this.company = company;
        this.amount = amount;
    }

    public Company company() {
        return company;
    }

    public Money amount() {
        return amount;
    }
}
