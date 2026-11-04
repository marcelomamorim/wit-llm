/*
 * @test
 * @summary Test Package.addBytes(byte[]) method for valid and edge cases
 * @run main PackageWitupTest
 */

import java.util.Arrays;

public class PackageWitupTest {

    public static void main(String[] args) {
        Package pkg = new Package();

        // Test with a normal byte array
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        try {
            pkg.addBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid input", e);
        }

        // Test with empty byte array
        byte[] empty = new byte[0];
        try {
            pkg.addBytes(empty);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for empty array", e);
        }

        // Test with null input - expecting NullPointerException
        boolean npeThrown = false;
        try {
            pkg.addBytes(null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type for null input", e);
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null input");
        }

        // Test with large byte array to check no exception thrown
        byte[] large = new byte[1024];
        Arrays.fill(large, (byte) 0xFF);
        try {
            pkg.addBytes(large);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for large input", e);
        }

        System.out.println("All Package.addBytes tests passed.");
    }
}
