/*
 * @test
 * @summary WITUP-derived behavioral checks for ExtendedKeyUsageExtension.get(String)
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import java.util.Vector;
import sun.security.x509.ExtendedKeyUsageExtension;
import sun.security.util.ObjectIdentifier;

public class ExtendedKeyUsageExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testUsagesReturnsKeyUsages();
        testUsagesIsCaseInsensitive();
        testUnknownNameThrowsIOException();
        testNullNameThrowsNullPointerException();
    }

    static void testUsagesReturnsKeyUsages() throws Exception {
        Vector<ObjectIdentifier> usages = new Vector<ObjectIdentifier>();
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);
        Vector<ObjectIdentifier> result = ext.get(ExtendedKeyUsageExtension.USAGES);
        if (result == null) {
            throw new RuntimeException("get(usages) returned null");
        }
        if (!result.isEmpty()) {
            throw new RuntimeException("expected empty usages, got size " + result.size());
        }
    }

    static void testUsagesIsCaseInsensitive() throws Exception {
        Vector<ObjectIdentifier> usages = new Vector<ObjectIdentifier>();
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);
        Vector<ObjectIdentifier> result = ext.get("USAGES");
        if (result == null || !result.isEmpty()) {
            throw new RuntimeException("case-insensitive get(USAGES) failed");
        }
    }

    static void testUnknownNameThrowsIOException() throws Exception {
        Vector<ObjectIdentifier> usages = new Vector<ObjectIdentifier>();
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);
        try {
            ext.get("unknownAttribute");
            throw new RuntimeException("expected IOException for unknown name");
        } catch (java.io.IOException expected) {
            // expected
        }
    }

    static void testNullNameThrowsNullPointerException() throws Exception {
        Vector<ObjectIdentifier> usages = new Vector<ObjectIdentifier>();
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);
        try {
            ext.get(null);
            throw new RuntimeException("expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
