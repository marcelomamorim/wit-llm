/*
 * @test
 * @summary YearMonth.now(Clock) must reject a null clock with NullPointerException
 * @run main YearMonthWitupTest
 */

import java.time.Clock;
import java.time.YearMonth;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        boolean thrown = false;
        try {
            YearMonth.now((Clock) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null clock");
        }
    }
}
