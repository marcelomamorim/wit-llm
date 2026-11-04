/*
 * @test
 * @summary Checks the DER output of CRLNumberExtension.encode(OutputStream)
 * @modules java.base/sun.security.x509
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Arrays;

import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    // DER encoding of OID 2.5.29.20 (id-ce-cRLNumber)
    private static final byte[] CRL_NUMBER_OID =
        {0x06, 0x03, 0x55, 0x1D, 0x14};

    static byte[] encodeToBytes(CRLNumberExtension ext) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ext.encode(bos);
        return bos.toByteArray();
    }

    static boolean contains(byte[] haystack, byte[] needle) {
        outer:
        for (int i = 0; i + needle.length <= haystack.length; i++) {
            for (int j = 0; j < needle.length; j++) {
                if (haystack[i + j] != needle[j]) {
                    continue outer;
                }
            }
            return true;
        }
        return false;
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testIntEncodingStartsWithSequence() throws Exception {
        byte[] out = encodeToBytes(new CRLNumberExtension(5));
        check(out.length > 0, "encoding must not be empty");
        check(out[0] == 0x30, "encoding must start with DER SEQUENCE tag");
    }

    static void testContainsCrlNumberOid() throws Exception {
        byte[] out = encodeToBytes(new CRLNumberExtension(5));
        check(contains(out, CRL_NUMBER_OID), "encoding must contain CRLNumber OID");
    }

    static void testContainsIntegerValue() throws Exception {
        byte[] out = encodeToBytes(new CRLNumberExtension(5));
        byte[] intValue = {0x02, 0x01, 0x05};
        check(contains(out, intValue), "encoding must contain INTEGER 5");
    }

    static void testDeterministic() throws Exception {
        byte[] a = encodeToBytes(new CRLNumberExtension(42));
        byte[] b = encodeToBytes(new CRLNumberExtension(42));
        check(Arrays.equals(a, b), "same value must encode identically");
    }

    static void testBigIntegerPositiveNeedsLeadingZero() throws Exception {
        // 128 requires a 0x00 padding byte in DER to remain positive
        byte[] out = encodeToBytes(new CRLNumberExtension(new BigInteger("128")));
        byte[] intValue = {0x02, 0x02, 0x00, (byte) 0x80};
        check(contains(out, intValue), "encoding must contain padded INTEGER 128");
    }

    static void testDifferentValuesEncodeDifferently() throws Exception {
        byte[] a = encodeToBytes(new CRLNumberExtension(1));
        byte[] b = encodeToBytes(new CRLNumberExtension(2));
        check(!Arrays.equals(a, b), "different CRL numbers must encode differently");
    }

    public static void main(String[] args) throws Exception {
        testIntEncodingStartsWithSequence();
        testContainsCrlNumberOid();
        testContainsIntegerValue();
        testDeterministic();
        testBigIntegerPositiveNeedsLeadingZero();
        testDifferentValuesEncodeDifferently();
    }
}
