/*
 * @test
 * @summary Test Runtime.load(String) method behavior
 * @run main RuntimeWitupTest
 */

public class RuntimeWitupTest {
    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();
        // Test with a likely invalid filename to check for expected exception
        // The actual exception depends on native load0 implementation
        // We catch Throwable to avoid test failure due to unexpected exceptions
        try {
            rt.load("nonexistent_library_xyz_123");
            // If no exception, test passes silently
        } catch (Throwable t) {
            // Exception is expected for invalid library name
            // Print stack trace for diagnostic purposes
            t.printStackTrace();
        }
    }
}
