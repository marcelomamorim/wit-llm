/*
 * @test
 * @summary Test BasicConstraintsExtension.encode(OutputStream) behavior
 * @run main BasicConstraintsExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.BasicConstraintsExtension;
import sun.security.x509.PKIXExtensions;

public class BasicConstraintsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithCaTrueAndPathLen();
        testEncodeWithCaFalse();
        testEncodeWithNullExtensionValue();
    }

    private static void testEncodeWithCaTrueAndPathLen() throws IOException {
        // ca = true, pathLen = 3
        BasicConstraintsExtension ext = new BasicConstraintsExtension(Boolean.TRUE, true, 3);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for ca=true, pathLen=3");
        }
        // extensionId should be PKIXExtensions.BasicConstraints_Id
        if (!ext.extensionId.equals(PKIXExtensions.BasicConstraints_Id)) {
            throw new RuntimeException("extensionId mismatch");
        }
        // critical should be true when ca is true
        if (!ext.critical) {
            throw new RuntimeException("critical flag should be true when ca is true");
        }
    }

    private static void testEncodeWithCaFalse() throws IOException {
        // ca = false, pathLen = -1 (default)
        BasicConstraintsExtension ext = new BasicConstraintsExtension(Boolean.FALSE, false, -1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for ca=false");
        }
        // extensionId should be PKIXExtensions.BasicConstraints_Id
        if (!ext.extensionId.equals(PKIXExtensions.BasicConstraints_Id)) {
            throw new RuntimeException("extensionId mismatch");
        }
        // critical should be false when ca is false
        if (ext.critical) {
            throw new RuntimeException("critical flag should be false when ca is false");
        }
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        // Create extension with ca=true, pathLen=0, then nullify extensionValue to force encodeThis call
        BasicConstraintsExtension ext = new BasicConstraintsExtension(Boolean.TRUE, true, 0);
        ext.extensionValue = null; // force encodeThis in encode
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty when extensionValue is null");
        }
        // critical should be true when ca is true
        if (!ext.critical) {
            throw new RuntimeException("critical flag should be true when ca is true and extensionValue null");
        }
    }
}