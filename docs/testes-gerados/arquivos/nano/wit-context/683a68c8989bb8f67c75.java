/*
 * @test
 * @summary Testa o método format de ZonedDateTime para comportamento normal e exceções.
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testFormatWithValidFormatter();
        testFormatWithNullFormatter();
    }

    static void testFormatWithValidFormatter() {
        ZonedDateTime zdt = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        String result = zdt.format(formatter);
        if (result == null || result.isEmpty()) {
            throw new AssertionError("Expected non-empty result for valid formatter");
        }
    }

    static void testFormatWithNullFormatter() {
        ZonedDateTime zdt = ZonedDateTime.now();
        try {
            ZonedDateTime.format(zdt, null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            if (!"formatter".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message in NullPointerException: " + e.getMessage());
            }
        }
    }
}
