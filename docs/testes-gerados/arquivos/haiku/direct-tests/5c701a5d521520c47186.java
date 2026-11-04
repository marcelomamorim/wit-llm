/*
 * @test
 * @summary AVA.derEncode writes a DER SEQUENCE containing the OID followed by the encoded value
 * @modules java.base/sun.security.x509
 *          java.base/sun.security.util
 * @run main AVAWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AVA;

public class AVAWitupTest {

    public static void main(String[] args) throws Exception {
        testCommonNameEncoding();
        testCountryEncoding();
        testEncodingIsDeterministic();
    }

    static void testCommonNameEncoding() throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.4.3");
        DerValue val = new DerValue(DerValue.tag_PrintableString, "abc".getBytes("US-ASCII"));
        AVA ava = new AVA(oid, val);

        byte[] expected = {
            0x30, 0x0A,
            0x06, 0x03, 0x55, 0x04, 0x03,
            0x13, 0x03, 0x61, 0x62, 0x63
        };
        check(expected, encode(ava), "CN AVA");
    }

    static void testCountryEncoding() throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.4.6");
        DerValue val = new DerValue(DerValue.tag_PrintableString, "US".getBytes("US-ASCII"));
        AVA ava = new AVA(oid, val);

        byte[] expected = {
            0x30, 0x09,
            0x06, 0x03, 0x55, 0x04, 0x06,
            0x13, 0x02, 0x55, 0x53
        };
        check(expected, encode(ava), "C AVA");
    }

    static void testEncodingIsDeterministic() throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.4.3");
        DerValue val = new DerValue(DerValue.tag_PrintableString, "abc".getBytes("US-ASCII"));
        AVA ava = new AVA(oid, val);

        byte[] first = encode(ava);
        byte[] second = encode(ava);
        check(first, second, "repeated derEncode");
    }

    static byte[] encode(AVA ava) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ava.derEncode(bos);
        return bos.toByteArray();
    }

    static void check(byte[] expected, byte[] actual, String what) {
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException(what + ": expected " + Arrays.toString(expected)
                    + " but got " + Arrays.toString(actual));
        }
    }
}
