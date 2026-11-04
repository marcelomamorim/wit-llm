/*
 * @test
 * @summary Test getDisplayName with null Locale to verify NullPointerException
 * @run main IsoFieldsWitupTest
 */

import java.time.temporal.IsoFields;
import java.util.Locale;

public class IsoFieldsWitupTest {

    public static void main(String[] args) {
        testGetDisplayNameNullLocale();
    }

    public static void testGetDisplayNameNullLocale() {
        try {
            IsoFields.getDisplayName(null);
            System.err.println("Expected NullPointerException was not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes
            System.out.println("NullPointerException caught as expected");
        }
    }
}
