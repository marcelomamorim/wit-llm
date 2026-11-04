/*
 * @test
 * @summary Test sun.security.x509.AVA.encode(DerOutputStream) for correct encoding and exception safety
 * @run main AVAWitupTest
 */

import sun.security.x509.AVA;
import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;
import sun.security.util.DerValue;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AVAWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeValid();
        testEncodeNullOutputStream();
    }

    private static void testEncodeValid() throws IOException {
        // Construct a valid AVA with a known OID and DerValue
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {2, 5, 4, 3}); // commonName OID
        DerValue val = new DerValue(DerValue.tag_PrintableString, "TestValue");
        AVA ava = new AVA(oid, val);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream dos = new DerOutputStream(baos);

        ava.encode(dos);
        dos.flush();

        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }
    }

    private static void testEncodeNullOutputStream() {
        // Test that passing null to encode throws NullPointerException
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {2, 5, 4, 3});
        DerValue val = new DerValue(DerValue.tag_PrintableString, "TestValue");
        AVA ava = new AVA(oid, val);

        try {
            ava.encode(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException thrown", e);
        }
    }
}
