/*
 * @test
 * @summary Verifies ExtendedKeyUsageExtension.set accepts the usages attribute with a Vector and rejects other names or value types with IOException
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import java.io.IOException;
import java.util.Vector;

import sun.security.util.ObjectIdentifier;
import sun.security.x509.ExtendedKeyUsageExtension;

public class ExtendedKeyUsageExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        ExtendedKeyUsageExtension ext =
                new ExtendedKeyUsageExtension(new Vector<ObjectIdentifier>());

        ext.set("usages", new Vector<ObjectIdentifier>());
        ext.set("USAGES", new Vector<ObjectIdentifier>());
        ext.set("Usages", new Vector<ObjectIdentifier>());

        expectIOException(ext, "usages", "not-a-vector");
        expectIOException(ext, "usages", Integer.valueOf(1));
        expectIOException(ext, "unknown", new Vector<ObjectIdentifier>());
        expectIOException(ext, "", new Vector<ObjectIdentifier>());
    }

    private static void expectIOException(ExtendedKeyUsageExtension ext,
                                          String name, Object value) {
        try {
            ext.set(name, value);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for name=" + name
                + " value=" + value);
    }
}
