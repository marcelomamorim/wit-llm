/*
 * @test
 * @summary Test for String.toLowerCase(Locale) method, focusing on null locale handling.
 * @run main StringWitupTest
 */

import java.lang.String;
import java.util.Locale;

public class StringWitupTest {

    public static void main(String[] args) {
        testToLowerCaseWithNullLocale();
    }

    public static void testToLowerCaseWithNullLocale() {
        String str = "TestString";
        try {
            String result = str.toLowerCase(null);
            // Expecting a NullPointerException, so if no exception, the test fails.
            System.err.println("FAIL: Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Test passes if NullPointerException is caught.
            System.out.println("PASS: NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
