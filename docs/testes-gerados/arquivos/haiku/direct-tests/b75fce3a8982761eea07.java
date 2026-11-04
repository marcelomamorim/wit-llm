/*
 * @test
 * @summary Verifies InvalidityDateExtension.delete(String) accepts the supported name case-insensitively, rejects unsupported names with IOException, and tolerates repeated deletes.
 * @modules java.base/sun.security.x509
 * @run main InvalidityDateExtensionWitupTest
 */

import java.io.IOException;
import java.util.Date;

import sun.security.x509.InvalidityDateExtension;

public class InvalidityDateExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        InvalidityDateExtension ext = new InvalidityDateExtension(false, new Date(1000000L));

        ext.delete("date");
        ext.delete("DATE");
        ext.delete("Date");

        expectIOException(new InvalidityDateExtension(false, new Date(1000000L)), "foo");
        expectIOException(new InvalidityDateExtension(false, new Date(1000000L)), "InvalidityDate");
        expectIOException(new InvalidityDateExtension(false, null), "");

        new InvalidityDateExtension(false, null).delete("Date");
    }

    private static void expectIOException(InvalidityDateExtension ext, String name) throws Exception {
        boolean thrown = false;
        try {
            ext.delete(name);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for name: " + name);
        }
    }
}
