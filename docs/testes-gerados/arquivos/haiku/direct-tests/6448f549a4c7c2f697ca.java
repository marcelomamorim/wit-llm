/*
 * @test
 * @summary Verifies delete(String) accepts key_id case-insensitively, rejects other names with IOException, and throws NullPointerException for null.
 * @modules java.base/sun.security.x509
 * @run main SubjectKeyIdentifierExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.SubjectKeyIdentifierExtension;

public class SubjectKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] keyId = {1, 2, 3, 4, 5, 6, 7, 8};
        SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyId);

        ext.delete("key_id");
        ext.delete("KEY_ID");
        ext.delete("Key_Id");

        expectIOException(ext, "unknown");
        expectIOException(ext, "");
        expectIOException(ext, "key_id2");

        ext.delete("key_id");

        expectNullPointerException(ext);
    }

    static void expectIOException(SubjectKeyIdentifierExtension ext, String name) {
        try {
            ext.delete(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for name: " + name);
    }

    static void expectNullPointerException(SubjectKeyIdentifierExtension ext) {
        try {
            ext.delete(null);
        } catch (NullPointerException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("Expected NullPointerException, got IOException", e);
        }
        throw new RuntimeException("Expected NullPointerException for null name");
    }
}
