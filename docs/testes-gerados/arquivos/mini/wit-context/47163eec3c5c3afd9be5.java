/*
 * @test
 * @summary Test Runtime.Version.compareTo(Version obj) for null argument
 * @run main RuntimeWitupTest
 */

public class RuntimeWitupTest {
    public static void main(String[] args) {
        // Create a dummy Version instance using a minimal subclass to test compareTo
        Runtime.Version v = new Runtime.Version("1.0");

        // Test that compareTo throws NullPointerException when argument is null
        try {
            v.compareTo(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }

        // Test compareTo with a non-null Version to check basic behavior
        Runtime.Version v2 = new Runtime.Version("1.0");
        int result = v.compareTo(v2);
        // Since both versions are equal, result should be 0
        if (result != 0) {
            throw new Error("compareTo returned " + result + " for equal versions");
        }
    }
}
