/*
 * @test
 * @summary Test sun.security.x509.PrivateKeyUsageExtension.encode(OutputStream) behavior
 * @run main PrivateKeyUsageExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import sun.security.x509.PrivateKeyUsageExtension;

public class PrivateKeyUsageExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithNullDates();
        testEncodeWithNotBeforeOnly();
        testEncodeWithNotAfterOnly();
        testEncodeWithBothDates();
    }

    private static void testEncodeWithNullDates() throws IOException {
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(null, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for null dates");
        }
    }

    private static void testEncodeWithNotBeforeOnly() throws IOException {
        Date notBefore = new Date(1000000000000L); // arbitrary fixed date
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(notBefore, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for notBefore only");
        }
    }

    private static void testEncodeWithNotAfterOnly() throws IOException {
        Date notAfter = new Date(2000000000000L); // arbitrary fixed date
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(null, notAfter);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for notAfter only");
        }
    }

    private static void testEncodeWithBothDates() throws IOException {
        Date notBefore = new Date(1000000000000L);
        Date notAfter = new Date(2000000000000L);
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(notBefore, notAfter);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for both dates");
        }
    }
}
