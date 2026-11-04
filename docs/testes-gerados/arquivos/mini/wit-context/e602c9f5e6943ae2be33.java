/*
 * @test
 * @summary Test SubjectAlternativeNameExtension.encode(OutputStream) for correct encoding and exception safety
 * @run main SubjectAlternativeNameExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.GeneralNames;

public class SubjectAlternativeNameExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithValidGeneralNames();
        testEncodeWithNullExtensionValue();
    }

    private static void testEncodeWithValidGeneralNames() throws IOException {
        // Create a minimal GeneralNames instance (empty) to test encoding
        GeneralNames emptyNames = new GeneralNames();
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension(emptyNames);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output should not be empty");
        }
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        // Create an instance with null extensionValue by using default constructor
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();

        // extensionValue is null initially, encode() should set extensionId and critical and encodeThis without throwing
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output should not be empty after encode()");
        }
    }
}
