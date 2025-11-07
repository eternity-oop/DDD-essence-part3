package org.eternity.step03_sharepie;

import org.eternity.step03_sharepie.loan.Company;
import org.eternity.step03_sharepie.loan.Facility;
import org.eternity.step03_sharepie.loan.Share;
import org.eternity.step03_sharepie.loan.SharePie;
import org.eternity.step03_sharepie.shared.monetary.Money;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FacilityTest {
    @Test
    public void 회사별_퍼실러티_대출_금액_확인() {
        Company companyA = new Company(1L, "A");
        Company companyB = new Company(2L, "B");
        Company companyC = new Company(3L, "C");

        Facility facility = Facility.crate(
                new SharePie(
                    new Share(companyA, Money.won(500)),
                    new Share(companyB, Money.won(200)),
                    new Share(companyC, Money.won(300))));

        facility.takeOutLoan(Money.won(100));

        assertThat(facility.investAmountOf(companyA)).isEqualTo(Money.won(50));
        assertThat(facility.investAmountOf(companyB)).isEqualTo(Money.won(20));
        assertThat(facility.investAmountOf(companyC)).isEqualTo(Money.won(30));
    }

    @Test
    public void 회사별_대출_금액_조정() {
        Company companyA = new Company(1L, "A");
        Company companyB = new Company(2L, "B");
        Company companyC = new Company(3L, "C");

        // 대출 한도와 지분을 금액으로 한 번에 설정
        Facility facility = Facility.crate(
                new SharePie(
                    new Share(companyA, Money.won(500)),
                    new Share(companyB, Money.won(200)),
                    new Share(companyC, Money.won(300))));

        // 퍼실리티의 지분에 따라 배당
        facility.takeOutLoan(Money.won(100));

        // 퍼실리티의 지분을 무시하고 명시적으로 지분 지정
        facility.takeOutLoan(
                new SharePie(
                    new Share(companyA, Money.won(100)),
                    new Share(companyC, Money.won(100))));

        assertThat(facility.investAmountOf(companyA)).isEqualTo(Money.won(150));
        assertThat(facility.investAmountOf(companyB)).isEqualTo(Money.won(20));
        assertThat(facility.investAmountOf(companyC)).isEqualTo(Money.won(130));
    }

    @Test
    public void 원금_상환액_배분() {
        Company companyA = new Company(1L, "A");
        Company companyB = new Company(2L, "B");
        Company companyC = new Company(3L, "C");

        Facility facility = Facility.crate(
                new SharePie(
                        new Share(companyA, Money.won(500)),
                        new Share(companyB, Money.won(200)),
                        new Share(companyC, Money.won(300))));

        // 퍼실리티의 지분을 무시하고 명시적으로 지분 지정
        facility.takeOutLoan(
                new SharePie(
                        new Share(companyA, Money.won(60)),
                        new Share(companyB, Money.won(30)),
                        new Share(companyC, Money.won(10))));

        // 10원 상환
        SharePie repay = facility.estimateRepayments(Money.won(10));

        // 대출 지분에 따라 상환금 배분    
        assertThat(repay.amountOf(companyA)).isEqualTo(Money.won(6));
        assertThat(repay.amountOf(companyB)).isEqualTo(Money.won(3));
        assertThat(repay.amountOf(companyC)).isEqualTo(Money.won(1));
    }
}
