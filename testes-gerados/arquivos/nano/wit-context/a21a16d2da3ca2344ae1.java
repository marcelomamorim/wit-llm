/* @test    @summary Test for StringUTF16.codePointAtSB handling of index bounds    @run main StringUTF16WitupTest */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testCodePointAtSBWithInvalidIndex();
        testCodePointAtSBWithValidIndex();
    }

    private static void testCodePointAtSBWithInvalidIndex() {
        byte[] validBytes = {0, 65, 0, 66}; // Represents 'AB' in UTF16
        int end = validBytes.length;
        int invalidIndexNegative = -1;
        int invalidIndexTooLarge = validBytes.length;
        boolean exceptionThrownNegative = false;
        boolean exceptionThrownTooLarge = false;
        try {
            StringUTF16.codePointAtSB(validBytes, invalidIndexNegative, end);
        } catch (StringIndexOutOfBoundsException e) {
            exceptionThrownNegative = true;
        }
        try {
            StringUTF16.codePointAtSB(validBytes, invalidIndexTooLarge, end);
        } catch (StringIndexOutOfBoundsException e) {
            exceptionThrownTooLarge = true;
        }
        if (!exceptionThrownNegative) {
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        }
        if (!exceptionThrownTooLarge) {
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index >= length");
        }
    }

    private static void testCodePointAtSBWithValidIndex() {
        byte[] bytes = {0, 65, 0, 66}; // 'A' and 'B' in UTF16
        int end = bytes.length;
        int result = StringUTF16.codePointAtSB(bytes, 0, end);
        int expected = (char)0x0041; // 'A'
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
        result = StringUTF16.codePointAtSB(bytes, 2, end);
        expected = (char)0x0042; // 'B'
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }
}
