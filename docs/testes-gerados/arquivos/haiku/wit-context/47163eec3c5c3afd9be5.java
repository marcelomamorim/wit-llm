/*
 * @test
 * @summary Verifies that Runtime.Version.compareTo(null) throws NullPointerException and that a version compares equal to itself
 * @run main RuntimeWitupTest
 */

public class RuntimeWitupTest {
    public static void main(String[] args) {
        Runtime.Version v = Runtime.getRuntime().version();

        boolean thrown = false;
        try {
            v.compareTo(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException from compareTo(null)");
        }

        if (v.compareTo(v) != 0) {
            throw new RuntimeException("Version must compare equal to itself");
        }
    }
}
