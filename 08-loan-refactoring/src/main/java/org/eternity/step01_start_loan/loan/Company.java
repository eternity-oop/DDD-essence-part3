package org.eternity.step01_start_loan.loan;

import org.eternity.step01_start_loan.shared.domain.DomainEntity;

public class Company extends DomainEntity<Company, Long> {
    private Long id;
    private final String name;

    public Company(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }
}
