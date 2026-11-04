/*
 * @test
 * @summary Testa o método format de YearMonth para comportamento normal e exceções.
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testFormatWithValidFormatter();
        testFormatWithNullFormatter();
    }

    static void testFormatWithValidFormatter() {
        YearMonth ym = YearMonth.of(2023, 3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String result = ym.format(formatter);
        if (result == null || !result.equals("2023-03")) {
            throw new AssertionError("Expected '2023-03', got: " + result);
        }
    }

    static void testFormatWithNullFormatter() {
        YearMonth ym = YearMonth.of(2023, 3);
        try {
            ym.format(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            if (e.getMessage() == null || !e.getMessage().equals("formatter")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
