package org.eternity.loan;

import org.eternity.shared.domain.ValueObject;
import org.eternity.shared.monetary.Money;

public class Investment extends ValueObject<Investment> {
    private Company company;
    private double percentage;

    public Investment(Company company, double percentage) {
        this.company = company;
        this.percentage = percentage;
    }

    public double percentage() {
        return percentage;
    }

    public Money calculateShare(Money amount) {
        return amount.times(percentage);
    }

    public boolean investmentOf(Company company) {
        return this.company.equals(company);
    }
}
