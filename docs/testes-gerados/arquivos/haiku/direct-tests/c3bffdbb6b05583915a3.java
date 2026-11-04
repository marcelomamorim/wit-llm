/*
 * @test
 * @summary Verifies observable behavior of BigInteger(int signum, byte[] magnitude)
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        check(new BigInteger(1, new byte[]{0x01, 0x00}).equals(BigInteger.valueOf(256)),
              "positive signum with two-byte magnitude should be 256");

        check(new BigInteger(-1, new byte[]{0x01, 0x00}).equals(BigInteger.valueOf(-256)),
              "negative signum with two-byte magnitude should be -256");

        check(new BigInteger(1, new byte[]{0x00, 0x00, 0x05}).equals(BigInteger.valueOf(5)),
              "leading zero bytes in magnitude should not change the value");

        check(new BigInteger(0, new byte[]{0x00, 0x00}).equals(BigInteger.ZERO),
              "zero signum with zero magnitude should be ZERO");

        check(new BigInteger(0, new byte[]{0x00, 0x00}).signum() == 0,
              "zero magnitude should yield signum 0");

        check(new BigInteger(1, new byte[0]).signum() == 0,
              "empty magnitude should yield a zero value");

        check(new BigInteger(1, new byte[]{0x7F}).equals(BigInteger.valueOf(127)),
              "single byte magnitude 0x7F should be 127");

        byte[] mag = new byte[]{0x01, 0x00};
        BigInteger value = new BigInteger(1, mag);
        mag[1] = 0x02;
        mag[0] = 0x00;
        check(value.equals(BigInteger.valueOf(256)),
              "modifying the source array after construction must not change the value");

        try {
            new BigInteger(1, null);
            throw new RuntimeException("null magnitude should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
