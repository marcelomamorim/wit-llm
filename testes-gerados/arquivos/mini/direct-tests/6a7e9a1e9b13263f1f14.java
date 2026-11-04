/*
 * @test
 * @summary Test com.sun.java.util.jar.pack.Package addBytes(byte[] bytes) method
 * @run main PackageWitupTest
 */

public class PackageWitupTest {
    public static void main(String[] args) {
        Package pkg = new Package();

        // Test with empty byte array
        byte[] empty = new byte[0];
        pkg.addBytes(empty);

        // Test with non-empty byte array
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        pkg.addBytes(data);

        // Test with single byte array
        byte[] single = new byte[] {42};
        pkg.addBytes(single);

        // Test with null - no exception expected as per given code (no null check)
        // but since code calls addBytes(bytes, 0, bytes.length), this will throw NullPointerException
        // So we test that explicitly
        boolean npeThrown = false;
        try {
            pkg.addBytes(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when passing null to addBytes");
        }
    }
}
