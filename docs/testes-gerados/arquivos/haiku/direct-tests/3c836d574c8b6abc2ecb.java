/*
 * @test
 * @summary OIDName.encode writes the DER OBJECT IDENTIFIER encoding of the wrapped OID
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main OIDNameWitupTest
 */

import java.io.IOException;
import java.util.Arrays;

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.OIDName;

public class OIDNameWitupTest {

    public static void main(String[] args) throws IOException {
        checkEncoding("1.2.3.4",
                new byte[]{0x06, 0x03, 0x2A, 0x03, 0x04});
        checkEncoding("1.2.840.113549",
                new byte[]{0x06, 0x06, 0x2A, (byte) 0x86, 0x48,
                           (byte) 0x86, (byte) 0xF7, 0x0D});
        checkEncodingFromObjectIdentifier("2.5.4.3",
                new byte[]{0x06, 0x03, 0x55, 0x04, 0x03});
        checkRoundTrip("1.2.3.4");
        checkRoundTrip("2.5.4.3");
        checkRoundTrip("1.2.840.113549");
    }

    static void checkEncoding(String name, byte[] expected) throws IOException {
        DerOutputStream out = new DerOutputStream();
        new OIDName(name).encode(out);
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("Unexpected encoding for " + name
                    + ": " + Arrays.toString(actual));
        }
    }

    static void checkEncodingFromObjectIdentifier(String name, byte[] expected)
            throws IOException {
        DerOutputStream out = new DerOutputStream();
        new OIDName(new ObjectIdentifier(name)).encode(out);
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("Unexpected encoding for OID " + name
                    + ": " + Arrays.toString(actual));
        }
    }

    static void checkRoundTrip(String name) throws IOException {
        DerOutputStream out = new DerOutputStream();
        new OIDName(name).encode(out);
        DerValue dv = new DerValue(out.toByteArray());
        ObjectIdentifier decoded = new OIDName(dv).getOID();
        if (!decoded.equals(new ObjectIdentifier(name))) {
            throw new RuntimeException("Round trip failed for " + name
                    + ": got " + decoded);
        }
    }
}
