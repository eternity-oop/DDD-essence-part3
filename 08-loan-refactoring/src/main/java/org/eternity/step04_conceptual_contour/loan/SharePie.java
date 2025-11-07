package org.eternity.step04_conceptual_contour.loan;

import org.eternity.step04_conceptual_contour.shared.monetary.Money;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SharePie {
    private Map<Company, Share> shares = new HashMap<>();

    public SharePie(Set<Company> companies) {
        for (var each : companies) {
            shares.put(each, new Share(each, Money.ZERO));
        }
    }

    public SharePie(Share... shares) {
        this(Arrays.stream(shares).collect(Collectors.toMap(Share::company, Function.identity())));
    }

    public SharePie(Map<Company, Share> shares) {
        this.shares = shares;
    }

    public SharePie prorate(Money amount) {
        Map<Company, Share> result = new HashMap<>();
        for (var owner : shares.keySet()) {
            result.put(owner, shares.get(owner).prorate(amount, sum()));
        }
        return new SharePie(result);
    }

    public SharePie plus(SharePie other) {
        Map<Company, Share> result = new HashMap<>();
        for(Share share : this.shares.values()) {
            result.put(share.company(), share.plus(other.shareOf(share.company())));
        }
        return new SharePie(result);
    }

    public SharePie minus(SharePie other) {
        Map<Company, Share> result = new HashMap<>();
        for (Share share : this.shares.values()) {
            result.put(share.company(), share.minus(other.shareOf(share.company())));
        }
        return new SharePie(result);
    }

    private Share shareOf(Company company) {
        return this.shares.getOrDefault(company, new Share(company, Money.ZERO));
    }

    public Money sum() {
        return shares.values().stream()
                .map(Share::amount)
                .reduce(Money.ZERO, Money::plus);
    }

    public Money amountOf(Company company) {
        return shares.get(company).amount();
    }

    public Set<Company> owners() {
        return shares.keySet();
    }
}
