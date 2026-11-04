/*
 * @test
 * @summary Witup generated tests for SubjectKeyIdentifierExtension.delete(String)
 * @modules java.base/sun.security.x509
 * @run main SubjectKeyIdentifierExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.SubjectKeyIdentifierExtension;

public class SubjectKeyIdentifierExtensionWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    private static SubjectKeyIdentifierExtension newExtension() throws IOException {
        return new SubjectKeyIdentifierExtension(new byte[] {1, 2, 3, 4});
    }

    private static void testDeleteKeyIdAccepted() throws IOException {
        SubjectKeyIdentifierExtension ext = newExtension();
        ext.delete(SubjectKeyIdentifierExtension.KEY_ID);
    }

    private static void testDeleteKeyIdCaseInsensitive() throws IOException {
        SubjectKeyIdentifierExtension ext = newExtension();
        ext.delete("KEY_ID");
        ext.delete("Key_Id");
    }

    private static void testDeleteTwiceAccepted() throws IOException {
        SubjectKeyIdentifierExtension ext = newExtension();
        ext.delete("key_id");
        ext.delete("key_id");
    }

    private static void testDeleteUnknownNameThrowsIOException() throws IOException {
        SubjectKeyIdentifierExtension ext = newExtension();
        boolean thrown = false;
        try {
            ext.delete("unknown");
        } catch (IOException e) {
            thrown = true;
            check(e.getMessage() != null
                    && e.getMessage().contains("Attribute name not recognized"),
                    "unexpected IOException message: " + e.getMessage());
        }
        check(thrown, "delete(\"unknown\") should throw IOException");
    }

    private static void testDeleteEmptyNameThrowsIOException() throws IOException {
        SubjectKeyIdentifierExtension ext = newExtension();
        boolean thrown = false;
        try {
            ext.delete("");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "delete(\"\") should throw IOException");
    }

    private static void testDeleteNullNameThrowsNPE() throws IOException {
        SubjectKeyIdentifierExtension ext = newExtension();
        boolean thrown = false;
        try {
            ext.delete(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "delete(null) should throw NullPointerException");
    }

    public static void main(String[] args) throws Exception {
        testDeleteKeyIdAccepted();
        testDeleteKeyIdCaseInsensitive();
        testDeleteTwiceAccepted();
        testDeleteUnknownNameThrowsIOException();
        testDeleteEmptyNameThrowsIOException();
        testDeleteNullNameThrowsNPE();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
