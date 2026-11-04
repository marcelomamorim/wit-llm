/* @test    @summary Test encoding of BasicConstraintsExtension with CA true and path length set    @run main BasicConstraintsExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.BasicConstraintsExtension;

public class BasicConstraintsExtensionWitupTest {

    public static void main(String[] args) throws IOException {
        testEncodeWithCaAndPathLen();
        testEncodeWithCaAndDefaultPathLen();
        testEncodeWithNonCa();
    }

    static void testEncodeWithCaAndPathLen() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 3);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        // Basic check: encoded should contain the sequence tag (0x30)
        assert encoded.length > 0 : "Encoded byte array is empty";
        assert encoded[0] == 0x30 : "Expected sequence tag 0x30";
    }

    static void testEncodeWithCaAndDefaultPathLen() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, -1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        assert encoded.length > 0 : "Encoded byte array is empty";
        assert encoded[0] == 0x30 : "Expected sequence tag 0x30";
    }

    static void testEncodeWithNonCa() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, -1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        assert encoded.length > 0 : "Encoded byte array is empty";
        assert encoded[0] == 0x30 : "Expected sequence tag 0x30";
    }
}
