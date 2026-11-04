/*
 * @test
 * @summary Verify SubjectKeyIdentifierExtension.set rejects unknown attribute names and wrong value types with IOException
 * @modules java.base/sun.security.x509
 * @run main SubjectKeyIdentifierExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.SubjectKeyIdentifierExtension;

public class SubjectKeyIdentifierExtensionWitupTest {

    interface SetAction {
        void run() throws IOException;
    }

    static void expectIOException(String label, SetAction action) {
        try {
            action.run();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException(label + ": expected IOException was not thrown");
    }

    public static void main(String[] args) throws Exception {
        SubjectKeyIdentifierExtension ext =
                new SubjectKeyIdentifierExtension(new byte[] {1, 2, 3, 4});

        expectIOException("unknown name", () ->
                ext.set("unknown_attribute", "value"));

        expectIOException("empty name", () ->
                ext.set("", "value"));

        expectIOException("key_id with String value", () ->
                ext.set(SubjectKeyIdentifierExtension.KEY_ID, "not-a-key-identifier"));

        expectIOException("KEY_ID uppercase with String value", () ->
                ext.set("KEY_ID", "not-a-key-identifier"));

        expectIOException("key_id with null value", () ->
                ext.set(SubjectKeyIdentifierExtension.KEY_ID, null));

        expectIOException("key_id with Integer value", () ->
                ext.set(SubjectKeyIdentifierExtension.KEY_ID, Integer.valueOf(7)));
    }
}
