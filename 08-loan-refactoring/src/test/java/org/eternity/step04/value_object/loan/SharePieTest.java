package org.eternity.step04.value_object.loan;

import org.eternity.shared.Money;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SharePieTest {
    @Test
    public void 지분_더하기() {
        Company companyA = new Company("A");
        Company companyB = new Company("B");

        SharePie shareA = new SharePie(Map.of(
                                companyA, new Share(companyA, 100),
                                companyB, new Share(companyA, 200)));
        SharePie shareB = new SharePie(Map.of(
                                companyA, new Share(companyA, 50),
                                companyB, new Share(companyA, 50)));

        SharePie sum = shareA.plus(shareB);

        assertThat(sum.amountOf(companyA)).isEqualByComparingTo(Money.won(150));
        assertThat(sum.amountOf(companyB)).isEqualByComparingTo(Money.won(250));

        assertThat(shareA.amountOf(companyA)).isEqualByComparingTo(Money.won(100));
        assertThat(shareB.amountOf(companyA)).isEqualByComparingTo(Money.won(50));
    }

    @Test
    public void 지분_빼기() {
        Company companyA = new Company("A");
        Company companyB = new Company("B");

        SharePie shareA = new SharePie(Map.of(
                                companyA, new Share(companyA, 100),
                                companyB, new Share(companyA, 200)));
        SharePie shareB = new SharePie(Map.of(
                                companyA, new Share(companyA, 50)));

        SharePie sum = shareA.minus(shareB);

        assertThat(sum.amountOf(companyA)).isEqualByComparingTo(Money.won(50));
        assertThat(sum.amountOf(companyB)).isEqualByComparingTo(Money.won(200));

        assertThat(shareA.amountOf(companyA)).isEqualByComparingTo(Money.won(100));
        assertThat(shareB.amountOf(companyA)).isEqualByComparingTo(Money.won(50));
    }

    @Test
    public void 지분_기준으로_금액_분배() {
        Company companyA = new Company("A");
        Company companyB = new Company("B");

        SharePie shares = new SharePie(Map.of(
                companyA, new Share(companyA, 100),
                companyB, new Share(companyA, 200)));

        SharePie prorated = shares.prorated(Money.won(3000));

        assertThat(prorated.amountOf(companyA)).isEqualByComparingTo(Money.won(1000));
        assertThat(prorated.amountOf(companyB)).isEqualByComparingTo(Money.won(2000));
    }
}
