/*
 * @test
 * @summary Test addBytes(byte[] bytes) method for Package class.
 * @run main PackageWitupTest
 */

import java.util.Arrays;
import com.sun.java.util.jar.pack.Package;

public class PackageWitupTest {

    public static void main(String[] args) {
        testAddBytesWithEmptyArray();
        testAddBytesWithNonEmptyArray();
    }

    static void testAddBytesWithEmptyArray() {
        Package pkg = new Package();
        byte[] emptyBytes = new byte[0];
        try {
            pkg.addBytes(emptyBytes);
            System.out.println("testAddBytesWithEmptyArray passed");
        } catch (Exception e) {
            System.err.println("testAddBytesWithEmptyArray failed: " + e);
        }
    }

    static void testAddBytesWithNonEmptyArray() {
        Package pkg = new Package();
        byte[] data = {1, 2, 3, 4, 5};
        try {
            pkg.addBytes(data);
            System.out.println("testAddBytesWithNonEmptyArray passed");
        } catch (Exception e) {
            System.err.println("testAddBytesWithNonEmptyArray failed: " + e);
        }
    }
}
