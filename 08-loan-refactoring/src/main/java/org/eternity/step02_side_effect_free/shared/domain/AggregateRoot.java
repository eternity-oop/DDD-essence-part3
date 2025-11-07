package org.eternity.step02_side_effect_free.shared.domain;

public abstract class AggregateRoot<T extends DomainEntity<T, TID>, TID> extends DomainEntity<T, TID> {
}
