/*
 * @test
 * @summary Test ClassLoader.getSystemResource(String) for null and valid input
 * @run main ClassLoaderWitupTest
 */

import java.lang.ClassLoader;
import java.net.URL;

public class ClassLoaderWitupTest {
    public static void main(String[] args) {
        testGetSystemResourceWithNull();
        testGetSystemResourceWithValidName();
    }

    private static void testGetSystemResourceWithNull() {
        try {
            ClassLoader.getSystemResource(null);
            throw new Error("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGetSystemResourceWithValidName() {
        // Use a resource name that is very likely not to exist to test non-null input
        String resourceName = "this_resource_should_not_exist_12345.txt";
        URL url = ClassLoader.getSystemResource(resourceName);
        // The method returns null if resource not found, so check for null or non-null
        if (url != null) {
            // If found, verify the URL ends with the resource name
            if (!url.toString().endsWith(resourceName)) {
                throw new Error("Returned URL does not end with resource name");
            }
        }
        // If null, acceptable as resource may not exist
    }
}
