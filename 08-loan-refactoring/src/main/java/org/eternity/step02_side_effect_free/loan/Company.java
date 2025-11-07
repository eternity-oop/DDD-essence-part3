package org.eternity.step02_side_effect_free.loan;

import org.eternity.step02_side_effect_free.shared.domain.DomainEntity;

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
