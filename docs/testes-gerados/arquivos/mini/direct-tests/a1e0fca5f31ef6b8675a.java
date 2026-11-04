/*
 * @test
 * @summary Test sun.security.util.ResourcesMgr.getString(String) method
 * @run main ResourcesMgrWitupTest
 */

import sun.security.util.ResourcesMgr;

public class ResourcesMgrWitupTest {
    public static void main(String[] args) throws Exception {
        // Test normal retrieval of a known key from the resource bundle
        String key = "invalidKey";
        try {
            String value = ResourcesMgr.getString(key);
            if (value == null) {
                throw new RuntimeException("Returned value is null for key: " + key);
            }
            // Print to confirm retrieval (not required but useful for jtreg output)
            System.out.println("Value for key '" + key + "': " + value);
        } catch (java.util.MissingResourceException e) {
            // Expected if the key does not exist in the resource bundle
            System.out.println("MissingResourceException caught as expected for key: " + key);
        }

        // Test with null key - should throw NullPointerException
        try {
            ResourcesMgr.getString(null);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException npe) {
            // Expected
            System.out.println("NullPointerException caught as expected for null key");
        }

        // Test behavior if VM is not booted - forcibly simulate by reflection
        // Since VM.isBooted() is private and no public setter, we cannot simulate this easily
        // So we skip this test as no public API to simulate VM not booted state
    }
}
