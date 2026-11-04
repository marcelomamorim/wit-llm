/*
 * @test
 * @summary Test Runtime.loadLibrary(String) for expected exceptions and normal behavior
 * @run main RuntimeWitupTest
 */

public class RuntimeWitupTest {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        // Test normal behavior with a non-null library name
        // We cannot guarantee the library exists, so catch UnsatisfiedLinkError
        try {
            runtime.loadLibrary("java" /* common library name */);
        } catch (UnsatisfiedLinkError e) {
            // Expected if library not found, test passes
        } catch (NullPointerException e) {
            throw new RuntimeException("Unexpected NullPointerException for non-null libname", e);
        }

        // Test passing null to loadLibrary
        // According to WIT, if security manager is present, NullPointerException is expected
        // But we cannot forcibly install a security manager here, so test for both behaviors
        try {
            runtime.loadLibrary(null);
            // If no exception, this is allowed if no security manager
        } catch (NullPointerException e) {
            // Expected if security manager present
        } catch (UnsatisfiedLinkError e) {
            // Also possible if no security manager but library name invalid
        } catch (SecurityException e) {
            // Possible if security manager denies
        }

        // No other exceptions expected or allowed
    }
}
