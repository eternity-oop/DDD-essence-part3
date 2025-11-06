package org.eternity.step04.value_object.loan;

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
