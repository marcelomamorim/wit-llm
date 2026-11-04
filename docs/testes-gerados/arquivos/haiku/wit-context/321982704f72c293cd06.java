/*
 * @test
 * @summary DateTimeFormatter.withResolverStyle rejects null and keeps observable resolver behavior
 * @run main DateTimeFormatterWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testNullResolverStyleThrowsNPE();
        testSameResolverStyleReturnsSameInstance();
        testStrictRejectsInvalidDayOfMonth();
        testSmartClampsInvalidDayOfMonth();
    }

    static void testNullResolverStyleThrowsNPE() {
        DateTimeFormatter base = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        try {
            base.withResolverStyle(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null resolverStyle");
    }

    static void testSameResolverStyleReturnsSameInstance() {
        DateTimeFormatter base = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        DateTimeFormatter same = base.withResolverStyle(ResolverStyle.SMART);
        if (same != base) {
            throw new RuntimeException("Expected same instance when resolver style is unchanged");
        }
    }

    static void testStrictRejectsInvalidDayOfMonth() {
        DateTimeFormatter strict = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse("2011-02-30", strict);
        } catch (DateTimeParseException expected) {
            return;
        }
        throw new RuntimeException("Expected DateTimeParseException under STRICT resolver");
    }

    static void testSmartClampsInvalidDayOfMonth() {
        DateTimeFormatter smart = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                .withResolverStyle(ResolverStyle.SMART);
        LocalDate date = LocalDate.parse("2011-02-30", smart);
        if (!date.equals(LocalDate.of(2011, 2, 28))) {
            throw new RuntimeException("Expected 2011-02-28 under SMART resolver but got " + date);
        }
    }
}
