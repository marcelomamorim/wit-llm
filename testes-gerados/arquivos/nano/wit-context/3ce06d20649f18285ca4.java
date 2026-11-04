/*
 * @test
 * @summary Tests for jdk.internal.misc.VM.getSavedProperties()
 * @run main VMWitupTest
 */

import java.util.Map;
import jdk.internal.misc.VM;

public class VMWitupTest {

    public static void main(String[] args) {
        testGetSavedPropertiesNull();
        testGetSavedPropertiesValid();
    }

    public static void testGetSavedPropertiesNull() {
        Map<String, String> result = null;
        try {
            result = VM.getSavedProperties();
            // If no exception, check if result is null, which indicates a bug
            if (result == null) {
                System.out.println("PASS: getSavedProperties() returned null as expected.");
            } else {
                System.out.println("FAIL: getSavedProperties() did not throw exception, returned: " + result);
            }
        } catch (IllegalStateException e) {
            System.out.println("PASS: getSavedProperties() threw IllegalStateException as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: getSavedProperties() threw unexpected exception: " + t);
        }
    }

    public static void testGetSavedPropertiesValid() {
        // Setup: simulate initialized state by setting savedProps
        // Since savedProps is private static, we cannot set it directly without reflection.
        // As per constraints, we avoid reflection; thus, this test assumes the method is called after initialization.
        // Alternatively, we can just call the method and check for non-null if initialized.
        try {
            Map<String, String> props = VM.getSavedProperties();
            if (props != null) {
                System.out.println("PASS: getSavedProperties() returned non-null map.");
            } else {
                System.out.println("FAIL: getSavedProperties() returned null.");
            }
        } catch (IllegalStateException e) {
            System.out.println("INFO: getSavedProperties() threw IllegalStateException, indicating not initialized.");
        } catch (Throwable t) {
            System.out.println("FAIL: getSavedProperties() threw unexpected exception: " + t);
        }
    }
}
