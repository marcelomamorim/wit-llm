/*
 * @test
 * @summary Test getSupportedExtensions() method for KeyChecker class.
 * @run main KeyCheckerWitupTest
 */

import java.util.*;
import sun.security.provider.certpath.KeyChecker;

public class KeyCheckerWitupTest {

    public static void main(String[] args) {
        testGetSupportedExtensions();
    }

    public static void testGetSupportedExtensions() {
        try {
            // Instantiate KeyChecker with dummy values
            KeyChecker checker = new KeyChecker(5, null);
            // Call the method
            Set<String> result = checker.getSupportedExtensions();
            // Verify the result is not null
            if (result == null) {
                throw new AssertionError("Result should not be null");
            }
            // Verify the set contains expected extensions
            List<String> expectedExtensions = Arrays.asList(
                "KeyUsage", "ExtendedKeyUsage", "SubjectAlternativeName"
            );
            for (String ext : expectedExtensions) {
                if (!result.contains(ext)) {
                    throw new AssertionError("Missing expected extension: " + ext);
                }
            }
            // Call again to test caching (idempotency)
            Set<String> result2 = checker.getSupportedExtensions();
            if (result != result2) {
                throw new AssertionError("Subsequent call should return same set instance");
            }
            System.out.println("testGetSupportedExtensions passed.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("Exception during test: " + e.getMessage());
        }
    }
}
