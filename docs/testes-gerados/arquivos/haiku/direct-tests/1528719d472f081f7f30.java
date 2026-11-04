/*
 * @test
 * @summary ChronoField.getDisplayName rejects null locale and returns stable non-empty names
 * @run main ChronoFieldWitupTest
 */

import java.time.temporal.ChronoField;
import java.util.Locale;

public class ChronoFieldWitupTest {

    public static void main(String[] args) {
        testNullLocaleRejected();
        testNonEmptyForAllFields();
        testDeterministicForSameLocale();
    }

    static void testNullLocaleRejected() {
        boolean thrown = false;
        try {
            ChronoField.DAY_OF_WEEK.getDisplayName(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null locale");
        }
    }

    static void testNonEmptyForAllFields() {
        for (ChronoField field : ChronoField.values()) {
            String name = field.getDisplayName(Locale.US);
            if (name == null || name.isEmpty()) {
                throw new RuntimeException("empty display name for " + field);
            }
        }
    }

    static void testDeterministicForSameLocale() {
        for (ChronoField field : ChronoField.values()) {
            String first = field.getDisplayName(Locale.US);
            String second = field.getDisplayName(Locale.US);
            if (!first.equals(second)) {
                throw new RuntimeException("non-deterministic display name for " + field);
            }
        }
    }
}
