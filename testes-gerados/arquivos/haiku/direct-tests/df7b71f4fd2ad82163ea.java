/*
 * @test
 * @summary Verify DER encoding of SerialNumber.encode for small, boundary and large values
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main SerialNumberWitupTest
 */

import java.math.BigInteger;
import java.util.Arrays;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        check(new SerialNumber(0), new byte[] {0x02, 0x01, 0x00});
        check(new SerialNumber(127), new byte[] {0x02, 0x01, 0x7f});
        check(new SerialNumber(128), new byte[] {0x02, 0x02, 0x00, (byte) 0x80});
        check(new SerialNumber(-1), new byte[] {0x02, 0x01, (byte) 0xff});

        BigInteger big = new BigInteger("123456789012345678901234567890");
        DerOutputStream out = new DerOutputStream();
        new SerialNumber(big).encode(out);
        byte[] encoded = out.toByteArray();

        DerValue val = new DerValue(encoded);
        if (!val.getBigInteger().equals(big)) {
            throw new RuntimeException("DerValue decode mismatch for large value");
        }
        SerialNumber decoded = new SerialNumber(new DerInputStream(encoded));
        if (!decoded.getNumber().equals(big)) {
            throw new RuntimeException("SerialNumber round trip mismatch");
        }
    }

    private static void check(SerialNumber sn, byte[] expected) throws Exception {
        DerOutputStream out = new DerOutputStream();
        sn.encode(out);
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(actual, expected)) {
            throw new RuntimeException("Unexpected encoding: " + Arrays.toString(actual)
                    + " expected: " + Arrays.toString(expected));
        }
    }
}
