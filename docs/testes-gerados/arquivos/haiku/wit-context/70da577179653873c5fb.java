/*
 * @test
 * @summary Witup-generated checks for Year.atMonth(int) public behavior
 * @run main YearWitupTest
 */

import java.time.DateTimeException;
import java.time.Year;
import java.time.YearMonth;

public class YearWitupTest {

    public static void main(String[] args) {
        check(Year.of(2000).atMonth(1), "2000-01");
        check(Year.of(2000).atMonth(12), "2000-12");
        check(Year.of(2024).atMonth(2), "2024-02");
        expectInvalidMonth(0);
        expectInvalidMonth(13);
        expectInvalidMonth(-1);
    }

    private static void check(YearMonth actual, String expected) {
        if (!actual.toString().equals(expected)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }

    private static void expectInvalidMonth(int month) {
        try {
            Year.of(2000).atMonth(month);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("No DateTimeException for month " + month);
    }
}
