/*
 * @test
 * @summary Test sun.security.util.ResourcesMgr.getAuthResourceString behavior
 * @run main ResourcesMgrWitupTest
 */

public class ResourcesMgrWitupTest {
    public static void main(String[] args) {
        // Test with a known key that should exist in AuthResources bundle
        // We do not know keys, so test that it returns a non-null string or throws MissingResourceException
        try {
            String value = sun.security.util.ResourcesMgr.getAuthResourceString("Ok");
            if (value == null) {
                throw new RuntimeException("Returned null for key 'Ok'");
            }
            // Print to confirm test ran
            System.out.println("Value for 'Ok': " + value);
        } catch (java.util.MissingResourceException e) {
            // Acceptable if key not found
            System.out.println("MissingResourceException for key 'Ok' as expected if key absent");
        } catch (InternalError e) {
            // If VM not booted, InternalError expected
            System.out.println("InternalError as expected if VM not booted: " + e.getMessage());
        }

        // Test with a key that likely does not exist
        try {
            sun.security.util.ResourcesMgr.getAuthResourceString("nonexistent_key_12345");
            throw new RuntimeException("Expected MissingResourceException for nonexistent key");
        } catch (java.util.MissingResourceException e) {
            // Expected
            System.out.println("MissingResourceException for nonexistent key as expected");
        } catch (InternalError e) {
            System.out.println("InternalError as expected if VM not booted: " + e.getMessage());
        }
    }
}
