/* @test
   @summary Tests the mod method of BigInteger for positive modulus.
   @run main BigIntegerWitupTest_P1f62ac237497
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_P1f62ac237497 {
    public static void main(String[] args) {
        BigInteger a = BigInteger.valueOf(123456789);
        BigInteger m = BigInteger.valueOf(10000);
        BigInteger result = a.mod(m);
        if (!result.equals(BigInteger.valueOf(6789))) {
            throw new AssertionError("Test failed: expected 6789, got " + result);
        }
        // Test with negative a, positive modulus
        BigInteger b = BigInteger.valueOf(-98765);
        BigInteger res2 = b.mod(m);
        BigInteger expected2 = b.remainder(m).signum() >= 0 ? b.remainder(m) : b.remainder(m).add(m);
        if (!res2.equals(expected2)) {
            throw new AssertionError("Test failed: expected " + expected2 + ", got " + res2);
        }
        // Test with a larger number
        BigInteger c = new BigInteger("987654321987654321");
        BigInteger m2 = new BigInteger("123456789");
        BigInteger res3 = c.mod(m2);
        BigInteger expected3 = c.remainder(m2).signum() >= 0 ? c.remainder(m2) : c.remainder(m2).add(m2);
        if (!res3.equals(expected3)) {
            throw new AssertionError("Test failed: expected " + expected3 + ", got " + res3);
        }
        // Test with modulus = 1 (edge case)
        BigInteger one = BigInteger.ONE;
        BigInteger res4 = a.mod(one);
        if (!res4.equals(BigInteger.ZERO)) {
            throw new AssertionError("Test failed: expected 0, got " + res4);
        }
        // Test with modulus = a (a > 0)
        BigInteger res5 = a.mod(a);
        if (!res5.equals(BigInteger.ZERO)) {
            throw new AssertionError("Test failed: expected 0, got " + res5);
        }
    }
}
