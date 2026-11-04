/*
 * @test
 * @summary Checks public InputStream constructor of SerialNumber for valid DER INTEGER input and IOException on invalid or truncated input
 * @modules java.base/sun.security.x509
 * @run main SerialNumberWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        testPositiveSingleByte();
        testPositiveMultiByte();
        testNegativeValue();
        testWrongTagThrowsIOException();
        testTruncatedValueThrowsIOException();
        testEmptyStreamThrowsIOException();
    }

    static InputStream der(int... bytes) {
        byte[] b = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            b[i] = (byte) bytes[i];
        }
        return new ByteArrayInputStream(b);
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testPositiveSingleByte() throws Exception {
        SerialNumber sn = new SerialNumber(der(0x02, 0x01, 0x05));
        check(BigInteger.valueOf(5).equals(sn.getNumber()), "single byte INTEGER 5");
    }

    static void testPositiveMultiByte() throws Exception {
        SerialNumber sn = new SerialNumber(der(0x02, 0x02, 0x01, 0x00));
        check(BigInteger.valueOf(256).equals(sn.getNumber()), "two byte INTEGER 256");
    }

    static void testNegativeValue() throws Exception {
        SerialNumber sn = new SerialNumber(der(0x02, 0x01, 0xFF));
        check(BigInteger.valueOf(-1).equals(sn.getNumber()), "negative INTEGER -1");
    }

    static void testWrongTagThrowsIOException() {
        expectIOException(0x04, 0x01, 0x05);
    }

    static void testTruncatedValueThrowsIOException() {
        expectIOException(0x02, 0x05, 0x01);
    }

    static void testEmptyStreamThrowsIOException() {
        expectIOException();
    }

    static void expectIOException(int... bytes) {
        try {
            new SerialNumber(der(bytes));
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException was not thrown");
    }
}
