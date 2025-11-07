package org.eternity.step03_sharepie.loan;

import lombok.ToString;
import org.eternity.step03_sharepie.shared.domain.ValueObject;
import org.eternity.step03_sharepie.shared.monetary.Money;

@ToString
public class Share extends ValueObject<Share> {
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

    public Share prorate(Money amount, Money total) {
        return new Share(company, amount.times(this.amount).divide(total));
    }

    public Share plus(Share other) {
        return new Share(company, this.amount.plus(other.amount));
    }

    public Share minus(Share other) {
        return new Share(company, this.amount.minus(other.amount));
    }
}
