/*
 * @test
 * @summary Test Runtime.load(String) for null filename input and normal call
 * @run main RuntimeWitupTest
 */

public class RuntimeWitupTest {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        // Test 1: Call load with a valid non-null string (cannot verify side effects, just no exception)
        try {
            runtime.load("java.lang.String"); // typical valid input
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception for valid filename", t);
        }

        // Test 2: Call load with null filename, expect NullPointerException as per WIT expaths
        try {
            runtime.load(null);
            throw new RuntimeException("Expected NullPointerException not thrown for null filename");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception type for null filename", t);
        }
    }
}
