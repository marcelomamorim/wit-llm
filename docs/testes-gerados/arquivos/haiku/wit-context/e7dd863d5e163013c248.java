/*
 * @test
 * @summary Behavioral checks for YearMonth.isValidDay boundaries and month-length dependence
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        YearMonth feb2023 = YearMonth.of(2023, 2);
        check(!feb2023.isValidDay(0), "day 0 must be invalid");
        check(feb2023.isValidDay(1), "day 1 must be valid");
        check(feb2023.isValidDay(28), "day 28 must be valid in Feb 2023");
        check(!feb2023.isValidDay(29), "day 29 must be invalid in Feb 2023");
        check(!feb2023.isValidDay(-1), "negative day must be invalid");
        check(!feb2023.isValidDay(Integer.MIN_VALUE), "MIN_VALUE must be invalid");
        check(!feb2023.isValidDay(Integer.MAX_VALUE), "MAX_VALUE must be invalid");

        YearMonth feb2024 = YearMonth.of(2024, 2);
        check(feb2024.isValidDay(29), "day 29 must be valid in leap Feb 2024");
        check(!feb2024.isValidDay(30), "day 30 must be invalid in leap Feb 2024");

        YearMonth apr = YearMonth.of(2023, 4);
        check(apr.isValidDay(30), "day 30 must be valid in April");
        check(!apr.isValidDay(31), "day 31 must be invalid in April");

        YearMonth jan = YearMonth.of(2023, 1);
        check(jan.isValidDay(31), "day 31 must be valid in January");
        check(!jan.isValidDay(32), "day 32 must be invalid in January");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
