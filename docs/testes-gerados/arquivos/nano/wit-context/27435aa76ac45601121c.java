/*
 * @test
 * @summary Test for TimeZoneNameUtility.retrieveDisplayNames method
 * @run main TimeZoneNameUtilityWitupTest
 */

import sun.util.locale.provider.TimeZoneNameUtility;
import java.util.Locale;

public class TimeZoneNameUtilityWitupTest {

    public static void main(String[] args) {
        testRetrieveDisplayNames_NullId();
        testRetrieveDisplayNames_NullLocale();
    }

    private static void testRetrieveDisplayNames_NullId() {
        try {
            String[] result = TimeZoneNameUtility.retrieveDisplayNames(null, Locale.US);
            // Expecting NullPointerException, so if no exception, test fails
            System.err.println("Failed: Expected NullPointerException for null id");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Passed: NullPointerException thrown for null id");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for null id: " + t);
        }
    }

    private static void testRetrieveDisplayNames_NullLocale() {
        try {
            String[] result = TimeZoneNameUtility.retrieveDisplayNames("GMT", null);
            // Expecting NullPointerException, so if no exception, test fails
            System.err.println("Failed: Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Passed: NullPointerException thrown for null locale");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for null locale: " + t);
        }
    }
}
