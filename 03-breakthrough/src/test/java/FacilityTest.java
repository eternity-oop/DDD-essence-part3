import org.eternity.loan.Company;
import org.eternity.loan.Facility;
import org.eternity.loan.Share;
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
                Map.of(companyA, new Share(companyA, Money.won(500)),
                        companyB, new Share(companyB, Money.won(200)),
                        companyC, new Share(companyC, Money.won(300))));

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
                Map.of(companyA, new Share(companyA, Money.won(500)),
                        companyB, new Share(companyB, Money.won(200)),
                        companyC, new Share(companyC, Money.won(300))));

        facility.takeOutLoan(Money.won(100));
        facility.takeOutLoan(Money.won(200));   // A: 150, B: 60, C: 90

        facility.transfer(companyB, companyC, 2.0 / 3.0);

        assertThat(facility.investAmountOf(companyA)).isEqualTo(Money.won(150));
        assertThat(facility.investAmountOf(companyB)).isEqualTo(Money.won(20));
        assertThat(facility.investAmountOf(companyC)).isEqualTo(Money.won(130));
    }
}
