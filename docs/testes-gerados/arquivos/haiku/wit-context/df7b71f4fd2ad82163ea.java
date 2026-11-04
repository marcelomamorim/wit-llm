/*
 * @test
 * @summary Verifies DER encoding of SerialNumber.encode for representative values and round-trip decoding
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main SerialNumberWitupTest
 */

import java.math.BigInteger;
import java.util.Arrays;

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        check(new SerialNumber(1), new byte[]{0x02, 0x01, 0x01});
        check(new SerialNumber(128), new byte[]{0x02, 0x02, 0x00, (byte) 0x80});
        check(new SerialNumber(-1), new byte[]{0x02, 0x01, (byte) 0xFF});
        check(new SerialNumber(0), new byte[]{0x02, 0x01, 0x00});
        check(new SerialNumber(BigInteger.valueOf(127)), new byte[]{0x02, 0x01, 0x7F});
        check(new SerialNumber(BigInteger.valueOf(-128)), new byte[]{0x02, 0x01, (byte) 0x80});

        BigInteger big = BigInteger.ONE.shiftLeft(200);
        SerialNumber sn = new SerialNumber(big);
        DerOutputStream out = new DerOutputStream();
        sn.encode(out);
        DerValue decoded = new DerValue(out.toByteArray());
        if (!decoded.getBigInteger().equals(big)) {
            throw new RuntimeException("Round-trip mismatch for large serial number");
        }

        SerialNumber again = new SerialNumber(new DerValue(out.toByteArray()));
        if (!again.getNumber().equals(big)) {
            throw new RuntimeException("Decoded SerialNumber differs from original");
        }

        DerOutputStream first = new DerOutputStream();
        DerOutputStream second = new DerOutputStream();
        sn.encode(first);
        sn.encode(second);
        if (!Arrays.equals(first.toByteArray(), second.toByteArray())) {
            throw new RuntimeException("Repeated encode produced different output");
        }
    }

    private static void check(SerialNumber sn, byte[] expected) throws Exception {
        DerOutputStream out = new DerOutputStream();
        sn.encode(out);
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(actual, expected)) {
            throw new RuntimeException("Unexpected DER encoding: " + Arrays.toString(actual)
                    + " expected: " + Arrays.toString(expected));
        }
    }
}
