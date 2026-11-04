/*
 * @test
 * @summary Verifies that AVA.encode(DerOutputStream) emits a deterministic DER SEQUENCE containing the OID and the value.
 * @modules java.base/sun.security.x509
 *          java.base/sun.security.util
 * @run main AVAWitupTest
 */

import java.util.Arrays;

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AVA;

public class AVAWitupTest {

    public static void main(String[] args) throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.4.3");
        DerValue val = new DerValue((byte) 0x13, new byte[] {'x'});
        AVA ava = new AVA(oid, val);

        DerOutputStream out = new DerOutputStream();
        ava.encode(out);
        byte[] enc = out.toByteArray();

        byte[] expected = {0x30, 0x08, 0x06, 0x03, 0x55, 0x04, 0x03, 0x13, 0x01, 0x78};
        if (!Arrays.equals(expected, enc)) {
            throw new RuntimeException("Unexpected DER encoding: " + Arrays.toString(enc));
        }

        DerOutputStream out2 = new DerOutputStream();
        ava.encode(out2);
        if (!Arrays.equals(enc, out2.toByteArray())) {
            throw new RuntimeException("Encoding is not deterministic across calls");
        }
    }
}
