package org.eternity.loan;

import org.eternity.shared.domain.DomainEntity;
import org.eternity.shared.monetary.Money;
import org.w3c.dom.Entity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Loan extends DomainEntity<Loan, Long> {
    private Long id;
    private Money amount;
    private Set<LoanInvestment> loanInvestments = new HashSet<>();

    public Loan(Set<Investment> investments) {
        this.amount = Money.ZERO;
        for(var investment : investments) {
            var loanInvestment = new LoanInvestment(investment);
            loanInvestments.add(loanInvestment);
        }
    }

    public Money amount() {
        return amount;
    }

    public void increase(Money amount) {
        this.amount = this.amount.plus(amount);
        distribute(amount);
    }

    private void distribute(Money amount) {
        this.loanInvestments = loanInvestments.stream()
                                    .map(each -> each.distribute(amount))
                                    .collect(Collectors.toSet());
    }

    public Money investAmountOf(Company company) {
        return loanInvestments.stream()
                    .filter(each -> each.loanOf(company))
                    .map(LoanInvestment::amount)
                    .findFirst()
                    .orElse(Money.ZERO);
    }

    @Override
    public Long getId() {
        return id;
    }
}
