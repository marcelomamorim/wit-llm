/*
 * @test
 * @summary Verifies ExtendedKeyUsageExtension.get(String) returns the usages for the "usages" name (case-insensitive) and throws IOException for unrecognized names
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import java.io.IOException;
import java.util.Vector;

import sun.security.util.ObjectIdentifier;
import sun.security.x509.ExtendedKeyUsageExtension;

public class ExtendedKeyUsageExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        Vector<ObjectIdentifier> usages = new Vector<>();
        usages.add(new ObjectIdentifier("1.3.6.1.5.5.7.3.1"));
        usages.add(new ObjectIdentifier("1.3.6.1.5.5.7.3.2"));

        ExtendedKeyUsageExtension ext =
                new ExtendedKeyUsageExtension(Boolean.FALSE, usages);

        Vector<ObjectIdentifier> got = ext.get("usages");
        check(got != null, "get(\"usages\") returned null");
        check(got.equals(usages), "get(\"usages\") returned unexpected content");

        Vector<ObjectIdentifier> viaConstant =
                ext.get(ExtendedKeyUsageExtension.USAGES);
        check(viaConstant.equals(usages),
                "get(USAGES) returned unexpected content");

        Vector<ObjectIdentifier> upper = ext.get("USAGES");
        check(upper.equals(usages), "get(\"USAGES\") is not case-insensitive");

        Vector<ObjectIdentifier> mixed = ext.get("UsAgEs");
        check(mixed.equals(usages), "get(\"UsAgEs\") is not case-insensitive");

        expectIOException(ext, "bogus");
        expectIOException(ext, "");
        expectIOException(ext, "usage");
    }

    private static void expectIOException(ExtendedKeyUsageExtension ext,
                                          String name) {
        try {
            ext.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for name: [" + name + "]");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
