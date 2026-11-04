/*
 * @test
 * @summary Verify SerialNumber(DerValue) decodes INTEGER values and rejects non-INTEGER tags
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
        check(new SerialNumber(parse(new byte[]{0x02, 0x01, 0x05})).getNumber()
                .equals(BigInteger.valueOf(5)), "positive single byte");

        check(new SerialNumber(parse(new byte[]{0x02, 0x01, 0x00})).getNumber()
                .equals(BigInteger.ZERO), "zero");

        check(new SerialNumber(parse(new byte[]{0x02, 0x01, (byte) 0xFF})).getNumber()
                .equals(BigInteger.valueOf(-1)), "negative one");

        check(new SerialNumber(parse(new byte[]{0x02, 0x02, 0x00, (byte) 0x80})).getNumber()
                .equals(BigInteger.valueOf(128)), "leading zero for high bit");

        boolean threw = false;
        try {
            new SerialNumber(parse(new byte[]{0x04, 0x01, 0x05}));
        } catch (IOException e) {
            threw = true;
        }
        check(threw, "non-INTEGER tag must be rejected");
    }

    static DerValue parse(byte[] der) throws IOException {
        return new DerValue(new ByteArrayInputStream(der));
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
