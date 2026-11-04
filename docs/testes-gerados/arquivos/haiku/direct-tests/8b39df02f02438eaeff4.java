/*
 * @test
 * @summary Verifies ECFieldFp constructor validation and observable state for positive, zero, negative and null primes
 * @run main ECFieldFpWitupTest
 */

import java.math.BigInteger;
import java.security.spec.ECFieldFp;

public class ECFieldFpWitupTest {

    public static void main(String[] args) {
        ECFieldFp field = new ECFieldFp(BigInteger.valueOf(23));
        if (!field.getP().equals(BigInteger.valueOf(23))) {
            throw new RuntimeException("getP() did not return the prime passed to the constructor");
        }
        if (field.getFieldSize() != 5) {
            throw new RuntimeException("unexpected field size for p=23: " + field.getFieldSize());
        }

        ECFieldFp large = new ECFieldFp(new BigInteger("340282366920938463463374607431768211297"));
        if (large.getFieldSize() != 128) {
            throw new RuntimeException("unexpected field size for 128-bit prime: " + large.getFieldSize());
        }

        expectIllegalArgument(BigInteger.ZERO);
        expectIllegalArgument(BigInteger.valueOf(-7));
        expectNullPointer(null);
    }

    private static void expectIllegalArgument(BigInteger p) {
        try {
            new ECFieldFp(p);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for p=" + p);
    }

    private static void expectNullPointer(BigInteger p) {
        try {
            new ECFieldFp(p);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null p");
    }
}
