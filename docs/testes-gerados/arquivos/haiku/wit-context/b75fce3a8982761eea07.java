/*
 * @test
 * @summary Tests InvalidityDateExtension.delete(String) for supported and unsupported names
 * @modules java.base/sun.security.x509
 * @run main InvalidityDateExtensionWitupTest
 */

import java.io.IOException;
import java.util.Date;
import sun.security.x509.InvalidityDateExtension;

public class InvalidityDateExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteDateLowerCase();
        testDeleteDateUpperCase();
        testDeleteUnsupportedNameThrowsIOException();
        testDeleteUnsupportedEmptyNameThrowsIOException();
        testDeleteTwiceDate();
        testDeleteWithNullDateExtension();
    }

    static void testDeleteDateLowerCase() throws IOException {
        InvalidityDateExtension ext =
            new InvalidityDateExtension(false, new Date(0L));
        ext.delete("date");
    }

    static void testDeleteDateUpperCase() throws IOException {
        InvalidityDateExtension ext =
            new InvalidityDateExtension(true, new Date(0L));
        ext.delete("DATE");
    }

    static void testDeleteUnsupportedNameThrowsIOException() throws IOException {
        InvalidityDateExtension ext =
            new InvalidityDateExtension(false, new Date(0L));
        try {
            ext.delete("other");
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for unsupported name");
    }

    static void testDeleteUnsupportedEmptyNameThrowsIOException() throws IOException {
        InvalidityDateExtension ext =
            new InvalidityDateExtension(false, new Date(0L));
        try {
            ext.delete("");
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for empty name");
    }

    static void testDeleteTwiceDate() throws IOException {
        InvalidityDateExtension ext =
            new InvalidityDateExtension(false, new Date(0L));
        ext.delete("date");
        ext.delete("date");
    }

    static void testDeleteWithNullDateExtension() throws IOException {
        InvalidityDateExtension ext = new InvalidityDateExtension(false, null);
        ext.delete("date");
    }
}
