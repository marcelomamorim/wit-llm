/*
 * @test
 * @summary WITUP-derived checks for ExtendedKeyUsageExtension.delete(String)
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import java.io.IOException;
import java.util.Vector;

import sun.security.util.ObjectIdentifier;
import sun.security.x509.ExtendedKeyUsageExtension;

public class ExtendedKeyUsageExtensionWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ExtendedKeyUsageExtension newExtension() throws IOException {
        Vector<ObjectIdentifier> usages = new Vector<>();
        usages.add(new ObjectIdentifier("1.3.6.1.5.5.7.3.1"));
        return new ExtendedKeyUsageExtension(usages);
    }

    static void testDeleteUsagesDoesNotThrow() throws Exception {
        ExtendedKeyUsageExtension ext = newExtension();
        ext.delete(ExtendedKeyUsageExtension.USAGES);
    }

    static void testDeleteUsagesCaseInsensitive() throws Exception {
        ExtendedKeyUsageExtension ext = newExtension();
        ext.delete("Usages");
        ext.delete("USAGES");
    }

    static void testDeleteUnknownNameThrowsIOException() throws Exception {
        ExtendedKeyUsageExtension ext = newExtension();
        boolean thrown = false;
        try {
            ext.delete("unknown");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "expected IOException for unrecognized attribute name");
    }

    static void testDeleteEmptyNameThrowsIOException() throws Exception {
        ExtendedKeyUsageExtension ext = newExtension();
        boolean thrown = false;
        try {
            ext.delete("");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "expected IOException for empty attribute name");
    }

    public static void main(String[] args) throws Exception {
        testDeleteUsagesDoesNotThrow();
        testDeleteUsagesCaseInsensitive();
        testDeleteUnknownNameThrowsIOException();
        testDeleteEmptyNameThrowsIOException();
    }
}
