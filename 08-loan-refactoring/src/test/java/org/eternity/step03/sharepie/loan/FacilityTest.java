package org.eternity.step03.sharepie.loan;

import org.eternity.shared.Money;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class FacilityTest {
    @Test
    public void 회사별_퍼실러티_대출_금액_확인() {
        Company companyA = new Company("A");
        Company companyB = new Company("B");
        Company companyC = new Company("C");

        Facility facility = Facility.crate(
                new SharePie(Map.of(
                        companyA, new Share(companyA, 500),
                        companyB, new Share(companyB, 200),
                        companyC, new Share(companyC, 300))));

        facility.takeOutLoan(Money.won(100));

        assertThat(facility.investAmountOf(companyA)).isEqualTo(Money.won(50));
        assertThat(facility.investAmountOf(companyB)).isEqualTo(Money.won(20));
        assertThat(facility.investAmountOf(companyC)).isEqualTo(Money.won(30));
    }

    @Test
    public void 회사별_대출_금액_조정() {
        Company companyA = new Company("A");
        Company companyB = new Company("B");
        Company companyC = new Company("C");

        Facility facility = Facility.crate(
                new SharePie(Map.of(
                        companyA, new Share(companyA, 500),
                        companyB, new Share(companyB, 200),
                        companyC, new Share(companyC, 300))));

        facility.takeOutLoan(Money.won(100));
        facility.takeOutLoan(Money.won(200));   // A: 150, B: 60, C: 90

        facility.transfer(companyB, companyC, 2.0 / 3.0);

        assertThat(facility.investAmountOf(companyA)).isEqualTo(Money.won(150));
        assertThat(facility.investAmountOf(companyB)).isEqualTo(Money.won(20));
        assertThat(facility.investAmountOf(companyC)).isEqualTo(Money.won(130));
    }

    @Test
    public void 원금_상환액_배분() {
        Company companyA = new Company("A");
        Company companyB = new Company("B");
        Company companyC = new Company("C");

        Facility facility = Facility.crate(
                new SharePie(Map.of(
                        companyA, new Share(companyA, 500),
                        companyB, new Share(companyB, 200),
                        companyC, new Share(companyC, 300))));

        facility.takeOutLoan(Money.won(100));
        facility.takeOutLoan(Money.won(200));

        facility.transfer(companyB, companyC, 2.0 / 3.0); // 전체 대출 300 중에 A: 150, B: 20, C: 130

        Map<Company, Share> distributions = facility.distributePrincipalPayment(Money.won(150));

        assertThat(distributions.get(companyA).amount()).isEqualTo(Money.won(75));
        assertThat(distributions.get(companyB).amount()).isEqualTo(Money.won(10));
        assertThat(distributions.get(companyC).amount()).isEqualTo(Money.won(65));
    }
}
