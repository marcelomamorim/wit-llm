/*
 * @test
 * @summary Tests for java.time.temporal.ChronoField.getDisplayName(Locale)
 * @run main ChronoFieldWitupTest
 */

import java.time.temporal.ChronoField;
import java.util.Locale;

public class ChronoFieldWitupTest {

    public static void main(String[] args) {
        testGetDisplayNameWithNullLocale();
        testGetDisplayNameWithValidLocale();
        testGetDisplayNameWithDefaultLocale();
    }

    private static void testGetDisplayNameWithNullLocale() {
        try {
            ChronoField cf = ChronoField.DAY_OF_WEEK;
            cf.getDisplayName(null);
            System.err.println("Failed: Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null locale");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null locale: " + e);
        }
    }

    private static void testGetDisplayNameWithValidLocale() {
        try {
            ChronoField cf = ChronoField.DAY_OF_WEEK;
            String name = cf.getDisplayName(Locale.US);
            if (name != null && !name.isEmpty()) {
                System.out.println("Passed: getDisplayName with Locale.US returned: " + name);
            } else {
                System.err.println("Failed: getDisplayName with Locale.US returned null or empty");
            }
        } catch (Exception e) {
            System.err.println("Failed: Exception in getDisplayName with Locale.US: " + e);
        }
    }

    private static void testGetDisplayNameWithDefaultLocale() {
        try {
            ChronoField cf = ChronoField.DAY_OF_WEEK;
            String name = cf.getDisplayName(Locale.getDefault());
            if (name != null && !name.isEmpty()) {
                System.out.println("Passed: getDisplayName with default Locale returned: " + name);
            } else {
                System.err.println("Failed: getDisplayName with default Locale returned null or empty");
            }
        } catch (Exception e) {
            System.err.println("Failed: Exception in getDisplayName with default Locale: " + e);
        }
    }
}
