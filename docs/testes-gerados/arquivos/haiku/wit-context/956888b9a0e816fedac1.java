/*
 * @test
 * @summary Witup generated tests for SerialNumber(DerValue) constructor
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main SerialNumberWitupTest
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import sun.security.util.DerValue;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        testPositiveInteger();
        testNegativeInteger();
        testMultiByteInteger();
        testWrongTagThrowsIOException();
    }

    static DerValue derOf(int... bytes) throws IOException {
        byte[] b = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            b[i] = (byte) bytes[i];
        }
        return new DerValue(new ByteArrayInputStream(b));
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testPositiveInteger() throws Exception {
        DerValue v = derOf(0x02, 0x01, 0x05);
        SerialNumber sn = new SerialNumber(v);
        check(BigInteger.valueOf(5).equals(sn.getNumber()), "expected 5");
    }

    static void testNegativeInteger() throws Exception {
        DerValue v = derOf(0x02, 0x01, 0xFF);
        SerialNumber sn = new SerialNumber(v);
        check(BigInteger.valueOf(-1).equals(sn.getNumber()), "expected -1");
    }

    static void testMultiByteInteger() throws Exception {
        DerValue v = derOf(0x02, 0x02, 0x01, 0x00);
        SerialNumber sn = new SerialNumber(v);
        check(BigInteger.valueOf(256).equals(sn.getNumber()), "expected 256");
    }

    static void testWrongTagThrowsIOException() throws Exception {
        DerValue v = derOf(0x04, 0x01, 0x05);
        boolean thrown = false;
        try {
            new SerialNumber(v);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "expected IOException for non-INTEGER tag");
    }
}
