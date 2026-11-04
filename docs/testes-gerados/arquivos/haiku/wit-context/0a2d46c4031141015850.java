/*
 * @test
 * @summary Verify SerialNumber(DerInputStream) decodes DER integers and rejects malformed input
 * @modules java.base/sun.security.util java.base/sun.security.x509
 * @run main SerialNumberWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.util.DerInputStream;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        checkValue(new byte[]{0x02, 0x01, 0x05}, BigInteger.valueOf(5));
        checkValue(new byte[]{0x02, 0x01, (byte) 0xFF}, BigInteger.valueOf(-1));
        checkValue(new byte[]{0x02, 0x02, 0x00, (byte) 0x80}, BigInteger.valueOf(128));

        expectIOException(new byte[0]);
        expectIOException(new byte[]{0x04, 0x01, 0x05});
        expectIOException(new byte[]{0x02, 0x05, 0x01});
    }

    private static void checkValue(byte[] encoded, BigInteger expected) throws IOException {
        DerInputStream din = new DerInputStream(encoded);
        SerialNumber sn = new SerialNumber(din);
        if (!expected.equals(sn.getNumber())) {
            throw new RuntimeException("Unexpected serial number: " + sn.getNumber()
                    + ", expected: " + expected);
        }
    }

    private static void expectIOException(byte[] encoded) {
        try {
            DerInputStream din = new DerInputStream(encoded);
            new SerialNumber(din);
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("Expected IOException for malformed input");
    }
}
