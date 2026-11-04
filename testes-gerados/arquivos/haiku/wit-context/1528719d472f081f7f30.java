/*
 * @test
 * @summary Verifies ChronoField.getDisplayName(Locale) null handling and basic display name behavior
 * @run main ChronoFieldWitupTest
 */

import java.time.temporal.ChronoField;
import java.util.Locale;

public class ChronoFieldWitupTest {

    public static void main(String[] args) {
        testNullLocaleThrowsNpe();
        testDisplayNameNotNullForEnglish();
        testDisplayNameNotEmptyForAllFields();
    }

    static void testNullLocaleThrowsNpe() {
        boolean thrown = false;
        try {
            ChronoField.DAY_OF_MONTH.getDisplayName(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null locale");
        }
    }

    static void testDisplayNameNotNullForEnglish() {
        String name = ChronoField.DAY_OF_MONTH.getDisplayName(Locale.ENGLISH);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("Display name must be non-empty: " + name);
        }
    }

    static void testDisplayNameNotEmptyForAllFields() {
        for (ChronoField field : ChronoField.values()) {
            String name = field.getDisplayName(Locale.US);
            if (name == null || name.isEmpty()) {
                throw new AssertionError("Empty display name for " + field);
            }
        }
    }
}
