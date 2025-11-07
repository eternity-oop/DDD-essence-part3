package org.eternity.step01_start_loan.shared.domain;

public abstract class AggregateRoot<T extends DomainEntity<T, TID>, TID> extends DomainEntity<T, TID> {
}
