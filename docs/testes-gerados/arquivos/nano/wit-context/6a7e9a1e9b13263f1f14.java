/*
 * @test
 * @summary Teste do método addBytes(byte[])
 * @run main PackageWitupTest
 */

import com.sun.java.util.jar.pack.Package;
import java.util.Arrays;

public class PackageWitupTest {

    public static void main(String[] args) {
        testAddBytesNormal();
        testAddBytesEmpty();
        testAddBytesNull();
    }

    static void testAddBytesNormal() {
        Package pkg = new Package();
        byte[] data = {1, 2, 3, 4, 5};
        try {
            pkg.addBytes(data);
            System.out.println("testAddBytesNormal passed");
        } catch (Exception e) {
            System.err.println("testAddBytesNormal failed: " + e);
        }
    }

    static void testAddBytesEmpty() {
        Package pkg = new Package();
        byte[] data = {};
        try {
            pkg.addBytes(data);
            System.out.println("testAddBytesEmpty passed");
        } catch (Exception e) {
            System.err.println("testAddBytesEmpty failed: " + e);
        }
    }

    static void testAddBytesNull() {
        Package pkg = new Package();
        try {
            pkg.addBytes(null);
            System.err.println("testAddBytesNull failed: no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testAddBytesNull passed");
        } catch (Exception e) {
            System.err.println("testAddBytesNull failed: " + e);
        }
    }
}
