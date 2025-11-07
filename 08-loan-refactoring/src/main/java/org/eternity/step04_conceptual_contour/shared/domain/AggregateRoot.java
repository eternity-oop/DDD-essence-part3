package org.eternity.step04_conceptual_contour.shared.domain;

public abstract class AggregateRoot<T extends DomainEntity<T, TID>, TID> extends DomainEntity<T, TID> {
}
