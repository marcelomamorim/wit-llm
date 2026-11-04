/*
 * @test
 * @summary Verify ExtendedKeyUsageExtension.set accepts the usages attribute case-insensitively and rejects invalid names and value types
 * @modules java.base/sun.security.x509
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import java.io.IOException;
import java.util.Vector;

import sun.security.x509.ExtendedKeyUsageExtension;

public class ExtendedKeyUsageExtensionWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static boolean setThrowsIOException(ExtendedKeyUsageExtension ext, String name, Object value) {
        try {
            ext.set(name, value);
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    public static void main(String[] args) throws Exception {
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(new Vector<>());

        // Valid usages attribute, exact name
        ext.set("usages", new Vector<>());

        // Name matching is case-insensitive
        ext.set("USAGES", new Vector<>());
        ext.set("Usages", new Vector<>());

        // Non-Vector value must be rejected
        check(setThrowsIOException(ext, "usages", "not a vector"),
              "String value for usages should throw IOException");
        check(setThrowsIOException(ext, "usages", null),
              "null value for usages should throw IOException");

        // Unknown attribute name must be rejected
        check(setThrowsIOException(ext, "bogus", new Vector<>()),
              "unknown name should throw IOException");
        check(setThrowsIOException(ext, "", new Vector<>()),
              "empty name should throw IOException");

        // Rejected sets must not break a subsequent valid set
        ext.set("usages", new Vector<>());

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
