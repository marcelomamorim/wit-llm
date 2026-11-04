/*
 * @test
 * @summary SerialNumber(InputStream) decodes a DER INTEGER and rejects invalid tags or truncated input
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
        testDecodePositiveInteger();
        testDecodeNegativeInteger();
        testTrailingBytesNotConsumed();
        testWrongTagRejected();
        testTruncatedInputRejected();
    }

    static void testDecodePositiveInteger() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[] {0x02, 0x01, 0x05});
        SerialNumber sn = new SerialNumber(in);
        if (!sn.getNumber().equals(BigInteger.valueOf(5))) {
            throw new RuntimeException("Expected 5 but got " + sn.getNumber());
        }
    }

    static void testDecodeNegativeInteger() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[] {0x02, 0x01, (byte) 0xFF});
        SerialNumber sn = new SerialNumber(in);
        if (!sn.getNumber().equals(BigInteger.valueOf(-1))) {
            throw new RuntimeException("Expected -1 but got " + sn.getNumber());
        }
    }

    static void testTrailingBytesNotConsumed() throws Exception {
        ByteArrayInputStream in =
                new ByteArrayInputStream(new byte[] {0x02, 0x01, 0x05, (byte) 0xFF});
        SerialNumber sn = new SerialNumber(in);
        if (!sn.getNumber().equals(BigInteger.valueOf(5))) {
            throw new RuntimeException("Expected 5 but got " + sn.getNumber());
        }
        if (in.available() != 1) {
            throw new RuntimeException("Expected 1 unread byte but got " + in.available());
        }
    }

    static void testWrongTagRejected() {
        InputStream in = new ByteArrayInputStream(new byte[] {0x04, 0x01, 0x05});
        try {
            new SerialNumber(in);
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("Expected IOException for non-INTEGER tag");
    }

    static void testTruncatedInputRejected() {
        InputStream in = new ByteArrayInputStream(new byte[] {0x02, 0x05, 0x01});
        try {
            new SerialNumber(in);
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("Expected IOException for truncated input");
    }
}
