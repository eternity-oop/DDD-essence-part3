package org.eternity.step03_sharepie.shared.domain;

public abstract class AggregateRoot<T extends DomainEntity<T, TID>, TID> extends DomainEntity<T, TID> {
}
