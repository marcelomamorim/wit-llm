/*
 * @test
 * @summary Tests for ResourceBundle.getTimeToLive method.
 * @run main ResourceBundleWitupTest
 */

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        testGetTimeToLiveWithNullBaseName();
        testGetTimeToLiveWithNullLocale();
        testGetTimeToLiveWithValidInputs();
    }

    public static void testGetTimeToLiveWithNullBaseName() {
        try {
            ResourceBundle rb = null;
            long result = ResourceBundle.getTimeToLive(null, Locale.getDefault());
            // Expecting NullPointerException, so if we reach here, test fails
            System.err.println("Failed: Expected NullPointerException for null baseName");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Passed: NullPointerException thrown for null baseName");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for null baseName: " + t);
        }
    }

    public static void testGetTimeToLiveWithNullLocale() {
        try {
            long result = ResourceBundle.getTimeToLive("baseName", null);
            System.err.println("Failed: Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Passed: NullPointerException thrown for null locale");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for null locale: " + t);
        }
    }

    public static void testGetTimeToLiveWithValidInputs() {
        try {
            long result = ResourceBundle.getTimeToLive("baseName", Locale.getDefault());
            if (result == Long.MAX_VALUE || result == 0 || result == -1) {
                System.out.println("Passed: getTimeToLive returned a value: " + result);
            } else {
                System.out.println("Note: getTimeToLive returned value: " + result);
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for valid inputs: " + t);
        }
    }
}
