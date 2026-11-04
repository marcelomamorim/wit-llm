/*
 * @test
 * @summary Verifies SerialNumber(DerInputStream) decodes the first DER INTEGER from the stream
 * @modules java.base/sun.security.x509
 * @modules java.base/sun.security.util
 * @run main SerialNumberWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        testRoundTrip(BigInteger.ZERO);
        testRoundTrip(BigInteger.valueOf(42));
        testRoundTrip(BigInteger.valueOf(-1));
        testRoundTrip(new BigInteger("-1234567890123456789"));
        testRoundTrip(new BigInteger("123456789012345678901234567890"));
        testFirstValueOnlyIsDecoded();
    }

    private static byte[] encodeInteger(BigInteger value) throws IOException {
        DerOutputStream out = new DerOutputStream();
        out.putInteger(value);
        return out.toByteArray();
    }

    private static void testRoundTrip(BigInteger expected) throws IOException {
        byte[] encoded = encodeInteger(expected);
        SerialNumber sn = new SerialNumber(new DerInputStream(encoded));
        if (!expected.equals(sn.getNumber())) {
            throw new RuntimeException("Expected " + expected + " but got " + sn.getNumber());
        }
    }

    private static void testFirstValueOnlyIsDecoded() throws IOException {
        DerOutputStream out = new DerOutputStream();
        out.putInteger(BigInteger.valueOf(1));
        out.putInteger(BigInteger.valueOf(2));
        byte[] encoded = out.toByteArray();
        SerialNumber sn = new SerialNumber(new DerInputStream(encoded));
        if (!BigInteger.ONE.equals(sn.getNumber())) {
            throw new RuntimeException("Expected first value 1 but got " + sn.getNumber());
        }
    }
}
