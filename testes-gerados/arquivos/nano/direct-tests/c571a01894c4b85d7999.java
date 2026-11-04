/* @test    @summary Test Byte(String s) constructor with valid and invalid inputs    @run main ByteWitupTest */

import java.lang.Byte;

public class ByteWitupTest {

    public static void main(String[] args) {
        testValidByteString();
        testInvalidByteString();
    }

    private static void testValidByteString() {
        String validString = "127";
        Byte byteObj = new Byte(validString);
        assert byteObj != null : "Byte object should not be null for valid input";
        assert byteObj.byteValue() == 127 : "Byte value should be 127";
    }

    private static void testInvalidByteString() {
        String invalidString = "128";
        try {
            new Byte(invalidString);
            assert false : "Expected NumberFormatException for invalid byte string";
        } catch (NumberFormatException e) {
            // Expected exception
        }
        String nonNumericString = "abc";
        try {
            new Byte(nonNumericString);
            assert false : "Expected NumberFormatException for non-numeric string";
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }
}
