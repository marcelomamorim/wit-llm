/*
 * @test
 * @summary Tests observable behavior of BigInteger(int signum, byte[] magnitude), including signum range validation
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        if (!new BigInteger(1, new byte[]{5}).toString().equals("5")) {
            fail("positive signum with magnitude {5} should give 5");
        }
        if (!new BigInteger(-1, new byte[]{5}).toString().equals("-5")) {
            fail("negative signum with magnitude {5} should give -5");
        }
        if (new BigInteger(0, new byte[]{0, 0}).signum() != 0) {
            fail("zero signum with zero magnitude should give zero");
        }
        if (!BigInteger.ZERO.equals(new BigInteger(0, new byte[]{0, 0}))) {
            fail("zero signum with zero magnitude should equal ZERO");
        }
        if (!new BigInteger(1, new byte[]{0, 0, 7}).equals(BigInteger.valueOf(7))) {
            fail("leading zero bytes should be stripped");
        }
        byte[] m = {5};
        BigInteger b = new BigInteger(1, m);
        m[0] = 9;
        if (!b.toString().equals("5")) {
            fail("constructor should not be affected by later changes to the magnitude array");
        }
        expectNumberFormatException(2, new byte[]{1}, "signum 2");
        expectNumberFormatException(-2, new byte[]{1}, "signum -2");
        expectNumberFormatException(0, new byte[]{1}, "signum 0 with nonzero magnitude");
    }

    private static void expectNumberFormatException(int signum, byte[] magnitude, String label) {
        try {
            new BigInteger(signum, magnitude);
        } catch (NumberFormatException expected) {
            return;
        }
        fail(label + " should throw NumberFormatException");
    }

    private static void fail(String message) {
        throw new RuntimeException(message);
    }
}
