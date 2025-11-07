import org.eternity.loan.Company;
import org.eternity.loan.Facility;
import org.eternity.loan.Investment;
import org.eternity.shared.monetary.Money;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class FacilityTest {
    @Test
    public void 회사별_퍼실러티_대출_금액() {
        Company companyA = new Company(1L, "A");
        Company companyB = new Company(2L, "B");
        Company companyC = new Company(3L, "C");

        // 대출 한도와 지분 설정
        Facility facility = Facility.crate(
                Money.won(1000),    // 대출 한도 1,000원
                Set.of(
                    new Investment(companyA, 0.5),    // 50%
                    new Investment(companyB, 0.2),    // 20%
                    new Investment(companyC, 0.3)));  // 30%

        // 대출 한도 1000원 중에서 총 200원 인출
        facility.takeOutLoan(Money.won(100));
        facility.takeOutLoan(Money.won(200));

        // 회사별 금액
        assertThat(facility.investAmountOf(companyA)).isEqualTo(Money.won(150)); // 150원
        assertThat(facility.investAmountOf(companyB)).isEqualTo(Money.won(60));  // 60원
        assertThat(facility.investAmountOf(companyC)).isEqualTo(Money.won(90));  // 90원
    }
}
