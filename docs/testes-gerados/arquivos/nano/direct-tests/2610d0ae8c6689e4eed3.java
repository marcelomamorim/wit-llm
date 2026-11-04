/* @test    @summary Test constructor BigInteger(String val) with various inputs    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testValidDecimalString();
        testValidHexString();
        testInvalidString();
        testEmptyString();
        testNullString();
    }

    static void testValidDecimalString() {
        BigInteger bi = new BigInteger("1234567890");
        assert bi.toString().equals("1234567890") : "Failed decimal string test";
    }

    static void testValidHexString() {
        BigInteger bi = new BigInteger("1a", 16);
        assert bi.toString().equals("26") : "Failed hex string test";
    }

    static void testInvalidString() {
        try {
            new BigInteger("notanumber");
            assert false : "Expected NumberFormatException";
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testEmptyString() {
        try {
            new BigInteger("");
            assert false : "Expected NumberFormatException for empty string";
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testNullString() {
        try {
            new BigInteger((String) null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
