/*
 * @test
 * @summary Verifies DER encoding produced by OIDName.encode(DerOutputStream)
 * @modules java.base/sun.security.util java.base/sun.security.x509
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
        testEncodeKnownOid();
        testEncodeMatchesObjectIdentifierConstructor();
        testEncodeAppendsToStream();
        testEncodeRoundTripThroughDerValue();
    }

    static void testEncodeKnownOid() throws IOException {
        DerOutputStream out = new DerOutputStream();
        new OIDName("1.2.3").encode(out);
        byte[] expected = new byte[] {0x06, 0x02, 0x2A, 0x03};
        check(Arrays.equals(expected, out.toByteArray()),
              "encoding of 1.2.3 mismatch: " + Arrays.toString(out.toByteArray()));
    }

    static void testEncodeMatchesObjectIdentifierConstructor() throws IOException {
        DerOutputStream fromString = new DerOutputStream();
        new OIDName("1.2.840.113549").encode(fromString);

        DerOutputStream fromOid = new DerOutputStream();
        new OIDName(new ObjectIdentifier("1.2.840.113549")).encode(fromOid);

        check(Arrays.equals(fromString.toByteArray(), fromOid.toByteArray()),
              "encoding differs between String and ObjectIdentifier constructors");
    }

    static void testEncodeAppendsToStream() throws IOException {
        DerOutputStream out = new DerOutputStream();
        OIDName name = new OIDName("1.2.3");
        name.encode(out);
        name.encode(out);
        byte[] expected = new byte[] {0x06, 0x02, 0x2A, 0x03, 0x06, 0x02, 0x2A, 0x03};
        check(Arrays.equals(expected, out.toByteArray()),
              "encode did not append two encodings: " + Arrays.toString(out.toByteArray()));
    }

    static void testEncodeRoundTripThroughDerValue() throws IOException {
        DerOutputStream out = new DerOutputStream();
        new OIDName("1.2.3").encode(out);
        DerValue value = new DerValue(out.toByteArray());
        ObjectIdentifier decoded = value.getOID();
        check(decoded.equals(new ObjectIdentifier("1.2.3")),
              "round-trip OID mismatch: " + decoded);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
