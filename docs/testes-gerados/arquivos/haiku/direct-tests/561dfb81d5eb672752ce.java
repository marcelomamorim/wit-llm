/*
 * @test
 * @summary Checks SubjectKeyIdentifierExtension.set accepts KeyIdentifier values for key_id and rejects invalid names or value types
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main SubjectKeyIdentifierExtensionWitupTest
 */

import java.io.IOException;

import sun.security.util.KeyIdentifier;
import sun.security.x509.SubjectKeyIdentifierExtension;

public class SubjectKeyIdentifierExtensionWitupTest {

    private static final byte[] KEY_BYTES = new byte[] {
        0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
        0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10,
        0x11, 0x12, 0x13, 0x14
    };

    public static void main(String[] args) throws Exception {
        testSetKeyIdentifierExactName();
        testSetKeyIdentifierCaseInsensitiveName();
        testSetRejectsNonKeyIdentifierValue();
        testSetRejectsUnknownName();
        testSetNullValueRejected();
    }

    private static void testSetKeyIdentifierExactName() throws Exception {
        SubjectKeyIdentifierExtension ext =
            new SubjectKeyIdentifierExtension(KEY_BYTES);
        KeyIdentifier kid = new KeyIdentifier(KEY_BYTES);
        ext.set(SubjectKeyIdentifierExtension.KEY_ID, kid);
    }

    private static void testSetKeyIdentifierCaseInsensitiveName() throws Exception {
        SubjectKeyIdentifierExtension ext =
            new SubjectKeyIdentifierExtension(KEY_BYTES);
        KeyIdentifier kid = new KeyIdentifier(KEY_BYTES);
        ext.set("KEY_ID", kid);
        ext.set("Key_Id", kid);
    }

    private static void testSetRejectsNonKeyIdentifierValue() throws Exception {
        SubjectKeyIdentifierExtension ext =
            new SubjectKeyIdentifierExtension(KEY_BYTES);
        expectIOException(() -> ext.set(SubjectKeyIdentifierExtension.KEY_ID,
                                        "not a KeyIdentifier"));
        expectIOException(() -> ext.set(SubjectKeyIdentifierExtension.KEY_ID,
                                        KEY_BYTES));
    }

    private static void testSetRejectsUnknownName() throws Exception {
        SubjectKeyIdentifierExtension ext =
            new SubjectKeyIdentifierExtension(KEY_BYTES);
        KeyIdentifier kid = new KeyIdentifier(KEY_BYTES);
        expectIOException(() -> ext.set("unknown_name", kid));
    }

    private static void testSetNullValueRejected() throws Exception {
        SubjectKeyIdentifierExtension ext =
            new SubjectKeyIdentifierExtension(KEY_BYTES);
        expectIOException(() -> ext.set(SubjectKeyIdentifierExtension.KEY_ID,
                                        null));
    }

    @FunctionalInterface
    private interface IOAction {
        void run() throws IOException;
    }

    private static void expectIOException(IOAction action) {
        try {
            action.run();
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("Expected IOException was not thrown");
    }
}
