/*
 * @test
 * @summary Verifies NameConstraintsExtension.delete(String) rejects unrecognized names and null names
 * @modules java.base/sun.security.x509
 * @run main NameConstraintsExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.GeneralSubtrees;
import sun.security.x509.NameConstraintsExtension;

public class NameConstraintsExtensionWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    private static NameConstraintsExtension newExtension() throws IOException {
        return new NameConstraintsExtension((GeneralSubtrees) null, (GeneralSubtrees) null);
    }

    private static void testUnrecognizedNameThrowsIOException() throws IOException {
        NameConstraintsExtension ext = newExtension();
        boolean thrown = false;
        try {
            ext.delete("unknown_attribute");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "delete with unrecognized name must throw IOException");
    }

    private static void testEmptyNameThrowsIOException() throws IOException {
        NameConstraintsExtension ext = newExtension();
        boolean thrown = false;
        try {
            ext.delete("");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "delete with empty name must throw IOException");
    }

    private static void testNullNameThrowsNullPointerException() throws IOException {
        NameConstraintsExtension ext = newExtension();
        boolean thrown = false;
        try {
            ext.delete(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "delete with null name must throw NullPointerException");
    }

    public static void main(String[] args) throws Exception {
        testUnrecognizedNameThrowsIOException();
        testEmptyNameThrowsIOException();
        testNullNameThrowsNullPointerException();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
