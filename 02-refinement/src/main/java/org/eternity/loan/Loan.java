package org.eternity.loan;

import org.eternity.shared.domain.DomainEntity;
import org.eternity.shared.monetary.Money;

import java.util.HashSet;
import java.util.Optional;
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

    public void adjustShare(Company company, Money amount) {
        if (loanInvestmentOf(company).isEmpty()) {
            throw new IllegalArgumentException();
        }

        LoanInvestment loanInvestment = loanInvestmentOf(company).get();
        LoanAdjustment loanAdjustment = new LoanAdjustment(loanInvestment, amount);

        loanInvestments.remove(loanInvestment);
        loanInvestments.add(loanAdjustment);
    }

    public Money investAmountOf(Company company) {
        return loanInvestmentOf(company)
                    .map(LoanInvestment::amount)
                    .orElse(Money.ZERO);
    }

    private Optional<LoanInvestment> loanInvestmentOf(Company company) {
        return loanInvestments.stream()
                .filter(each -> each.loanOf(company))
                .findAny();
    }

    @Override
    public Long getId() {
        return id;
    }
}
