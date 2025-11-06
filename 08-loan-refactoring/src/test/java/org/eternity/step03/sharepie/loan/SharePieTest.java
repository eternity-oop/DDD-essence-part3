package org.eternity.step03.sharepie.loan;

import org.eternity.shared.Money;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SharePieTest {
    @Test
    public void 지분_더하기() {
        Company companyA = new Company("A");
        Company companyB = new Company("B");

        SharePie shareA = new SharePie(new HashMap<>() {{
                put(companyA, new Share(companyA, 100));
                put(companyB, new Share(companyA, 200));
            }});

        Map<Company, Share> increase = Map.of(
                                companyA, new Share(companyA, 50),
                                companyB, new Share(companyA, 50));

        shareA.increase(increase);

        assertThat(shareA.amountOf(companyA)).isEqualByComparingTo(Money.won(150));
        assertThat(shareA.amountOf(companyB)).isEqualByComparingTo(Money.won(250));
    }

    @Test
    public void 지분_빼기() {
        Company companyA = new Company("A");
        Company companyB = new Company("B");

        SharePie shareA = new SharePie(new HashMap<>() {{
            put(companyA, new Share(companyA, 100));
            put(companyB, new Share(companyA, 200));
        }});

        Map<Company, Share> decrease = Map.of(
                companyA, new Share(companyA, 50),
                companyB, new Share(companyA, 50));

        shareA.decrease(decrease);

        assertThat(shareA.amountOf(companyA)).isEqualByComparingTo(Money.won(50));
        assertThat(shareA.amountOf(companyB)).isEqualByComparingTo(Money.won(150));
    }

    @Test
    public void 지분_기준으로_금액_분배() {
        Company companyA = new Company("A");
        Company companyB = new Company("B");

        SharePie shares = new SharePie(new HashMap<>() {{
            put(companyA, new Share(companyA, 100));
            put(companyB, new Share(companyA, 200));
        }});

        Map<Company, Share> prorated = shares.prorated(Money.won(3000));

        assertThat(prorated.get(companyA).amount()).isEqualByComparingTo(Money.won(1000));
        assertThat(prorated.get(companyB).amount()).isEqualByComparingTo(Money.won(2000));
    }
}
