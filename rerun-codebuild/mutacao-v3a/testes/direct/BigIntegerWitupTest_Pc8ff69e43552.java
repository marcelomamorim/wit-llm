/* @test    @summary Test for BigInteger.byteValueExact() method    @run main BigIntegerWitupTest_Pc8ff69e43552 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pc8ff69e43552 {

    public static void main(String[] args) {
        testWithinByteRange();
        testOutOfByteRange();
    }

    static void testWithinByteRange() {
        // Test case where BigInteger value is within byte range (-128 to 127)
        BigInteger bi = BigInteger.valueOf(100); // within byte range
        byte result = bi.byteValueExact();
        assert result == 100 : "Expected 100, but got " + result;
    }

    static void testOutOfByteRange() {
        // Test case where BigInteger value exceeds byte range (e.g., 200)
        BigInteger bi = BigInteger.valueOf(200); // outside byte range
        try {
            bi.byteValueExact();
            assert false : "Expected ArithmeticException for out of byte range";
        } catch (ArithmeticException e) {
            // expected
            assert e.getMessage().equals("BigInteger out of byte range") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
