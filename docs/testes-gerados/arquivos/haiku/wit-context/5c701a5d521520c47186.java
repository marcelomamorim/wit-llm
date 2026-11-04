/*
 * @test
 * @summary Verify that AVA.derEncode emits a DER SEQUENCE containing the attribute OID and the value encoding
 * @modules java.base/sun.security.util java.base/sun.security.x509
 * @run main AVAWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AVA;

public class AVAWitupTest {

    public static void main(String[] args) throws IOException {
        testCommonName();
        testCountryName();
        testDeterministicOutput();
    }

    static void testCommonName() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.4.3");
        DerValue val = new DerValue(new byte[] {0x13, 0x03, 0x61, 0x62, 0x63});
        AVA ava = new AVA(oid, val);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ava.derEncode(out);
        byte[] expected = {0x30, 0x0A, 0x06, 0x03, 0x55, 0x04, 0x03,
                           0x13, 0x03, 0x61, 0x62, 0x63};
        check(Arrays.equals(expected, out.toByteArray()), "CN encoding mismatch");
    }

    static void testCountryName() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.4.6");
        DerValue val = new DerValue(new byte[] {0x13, 0x02, 0x55, 0x53});
        AVA ava = new AVA(oid, val);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ava.derEncode(out);
        byte[] expected = {0x30, 0x09, 0x06, 0x03, 0x55, 0x04, 0x06,
                           0x13, 0x02, 0x55, 0x53};
        check(Arrays.equals(expected, out.toByteArray()), "C encoding mismatch");
    }

    static void testDeterministicOutput() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.4.3");
        DerValue val = new DerValue(new byte[] {0x13, 0x03, 0x61, 0x62, 0x63});
        AVA ava = new AVA(oid, val);
        ByteArrayOutputStream first = new ByteArrayOutputStream();
        ByteArrayOutputStream second = new ByteArrayOutputStream();
        ava.derEncode(first);
        ava.derEncode(second);
        check(Arrays.equals(first.toByteArray(), second.toByteArray()),
              "repeated encoding is not stable");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
