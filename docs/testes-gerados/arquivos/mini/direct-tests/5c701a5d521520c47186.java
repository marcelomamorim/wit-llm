/*
 * @test
 * @summary Test sun.security.x509.AVA.derEncode(OutputStream) method
 * @run main AVAWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.AVA;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;

public class AVAWitupTest {

    public static void main(String[] args) throws Exception {
        testDerEncodeProducesExpectedSequence();
    }

    private static void testDerEncodeProducesExpectedSequence() throws IOException {
        // Create a simple OID and DerValue for testing
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 3, 4});
        // Use a printable string DerValue
        DerValue val = new DerValue(DerValue.tag_PrintableString, "test".getBytes("UTF8"));

        AVA ava = new AVA(oid, val);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ava.derEncode(baos);
        byte[] encoded = baos.toByteArray();

        // Basic sanity checks on the encoded output
        // It should start with the SEQUENCE tag (0x30)
        if (encoded.length < 2) {
            throw new AssertionError("Encoded output too short");
        }
        if (encoded[0] != 0x30) {
            throw new AssertionError("Encoded output does not start with SEQUENCE tag");
        }

        // The encoded output should contain the OID bytes
        byte[] oidBytes = oid.toByteArray();
        boolean foundOid = false;
        for (int i = 0; i <= encoded.length - oidBytes.length; i++) {
            boolean match = true;
            for (int j = 0; j < oidBytes.length; j++) {
                if (encoded[i + j] != oidBytes[j]) {
                    match = false;
                    break;
                }
            }
            if (match) {
                foundOid = true;
                break;
            }
        }
        if (!foundOid) {
            throw new AssertionError("Encoded output does not contain OID bytes");
        }

        // The encoded output should contain the value bytes
        byte[] valBytes = val.toByteArray();
        boolean foundVal = false;
        for (int i = 0; i <= encoded.length - valBytes.length; i++) {
            boolean match = true;
            for (int j = 0; j < valBytes.length; j++) {
                if (encoded[i + j] != valBytes[j]) {
                    match = false;
                    break;
                }
            }
            if (match) {
                foundVal = true;
                break;
            }
        }
        if (!foundVal) {
            throw new AssertionError("Encoded output does not contain value bytes");
        }
    }
}
