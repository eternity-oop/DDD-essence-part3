package org.eternity.step04_conceptual_contour;

import org.eternity.step04_conceptual_contour.loan.Company;
import org.eternity.step04_conceptual_contour.loan.Share;
import org.eternity.step04_conceptual_contour.loan.SharePie;
import org.eternity.step04_conceptual_contour.shared.monetary.Money;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SharePieTest {
    @Test
    public void 지분_더하기() {
        Company companyA = new Company(1L, "A");
        Company companyB = new Company(2L, "B");

        SharePie shareA = new SharePie(
                            new Share(companyA, 100),
                            new Share(companyB, 200));
        SharePie shareB = new SharePie(
                            new Share(companyA, 50),
                            new Share(companyB, 50));

        SharePie sum = shareA.plus(shareB);

        assertThat(sum.amountOf(companyA)).isEqualByComparingTo(Money.won(150));
        assertThat(sum.amountOf(companyB)).isEqualByComparingTo(Money.won(250));

        assertThat(shareA.amountOf(companyA)).isEqualByComparingTo(Money.won(100));
        assertThat(shareA.amountOf(companyB)).isEqualByComparingTo(Money.won(200));
        assertThat(shareB.amountOf(companyA)).isEqualByComparingTo(Money.won(50));
        assertThat(shareB.amountOf(companyB)).isEqualByComparingTo(Money.won(50));
    }

    @Test
    public void 지분_빼기() {
        Company companyA = new Company(1L, "A");
        Company companyB = new Company(2L, "B");

        SharePie shareA = new SharePie(
                new Share(companyA, 100),
                new Share(companyB, 200));
        SharePie shareB = new SharePie(
                new Share(companyA, 50),
                new Share(companyB, 50));

        SharePie diff = shareA.minus(shareB);

        assertThat(diff.amountOf(companyA)).isEqualByComparingTo(Money.won(50));
        assertThat(diff.amountOf(companyB)).isEqualByComparingTo(Money.won(150));
        assertThat(shareA.amountOf(companyA)).isEqualByComparingTo(Money.won(100));
        assertThat(shareA.amountOf(companyB)).isEqualByComparingTo(Money.won(200));
        assertThat(shareB.amountOf(companyA)).isEqualByComparingTo(Money.won(50));
        assertThat(shareB.amountOf(companyB)).isEqualByComparingTo(Money.won(50));
    }

    @Test
    public void 지분_기준으로_금액_분배() {
        Company companyA = new Company(1L, "A");
        Company companyB = new Company(2L, "B");

        SharePie shares = new SharePie(
                new Share(companyA, 100),
                new Share(companyB, 200));

        SharePie prorated = shares.prorate(Money.won(3000));

        assertThat(prorated.amountOf(companyA)).isEqualTo(Money.won(1000));
        assertThat(prorated.amountOf(companyB)).isEqualTo(Money.won(2000));
    }
}
