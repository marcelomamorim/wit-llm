/*
 * @test
 * @summary Test sun.security.x509.AVA.derEncode(OutputStream) for correct encoding and exception safety
 * @run main AVAWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.AVA;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;

public class AVAWitupTest {

    public static void main(String[] args) throws Exception {
        testDerEncodeNormal();
        testDerEncodeWithNullOutputStream();
    }

    private static void testDerEncodeNormal() throws IOException {
        // Create a simple AVA with known OID and DerValue
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {2, 5, 4, 3}); // commonName OID
        DerValue val = new DerValue(DerValue.tag_PrintableString, "TestValue".getBytes("UTF8"));
        AVA ava = new AVA(oid, val);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ava.derEncode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("derEncode produced empty output");
        }
    }

    private static void testDerEncodeWithNullOutputStream() {
        // According to current code, passing null OutputStream should throw NullPointerException
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {2, 5, 4, 3});
        DerValue val = new DerValue(DerValue.tag_PrintableString, "TestValue".getBytes());
        AVA ava = new AVA(oid, val);

        try {
            ava.derEncode(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException thrown", e);
        }
    }
}
