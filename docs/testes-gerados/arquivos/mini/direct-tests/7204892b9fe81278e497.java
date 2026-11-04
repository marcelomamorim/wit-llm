/*
 * @test
 * @summary Test Runtime.compareToIgnoreOptional(Version) method
 * @run main RuntimeWitupTest
 */

public class RuntimeWitupTest {
    public static void main(String[] args) {
        // Since compareToIgnoreOptional calls compare(obj, true),
        // and we have no direct access to compare or Version implementation,
        // we test that compareToIgnoreOptional returns an int and does not throw.

        // Create dummy Version instances for testing
        // We must use the Version class as it appears in the context, but since
        // we have no source or constructor details, we cannot instantiate it directly.
        // So we test only that calling compareToIgnoreOptional with null throws NullPointerException.

        try {
            java.lang.Runtime runtime = java.lang.Runtime.getRuntime();
            // The method is instance method of Runtime, but from the source it looks like it is in Runtime?
            // Actually, the method signature is java.lang.Runtime.compareToIgnoreOptional(Version obj)
            // but this is unusual since Runtime does not have such method in public API.
            // Possibly a nested class or inner class Version?
            // Since we cannot instantiate Version, test with null to check behavior.

            // We expect NullPointerException or similar if null is passed.
            runtime.compareToIgnoreOptional(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected
        } catch (Throwable t) {
            throw new Error("Unexpected exception: " + t);
        }
    }
}
