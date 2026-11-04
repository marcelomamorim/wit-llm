/* @test    @summary Test for String.subSequence to cover index bounds and exceptions    @run main StringWitupTest */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testValidSubSequence();
        testBeginIndexGreaterThanEndIndex();
        testBeginIndexNegative();
        testEndIndexExceedsLength();
    }

    private static void testValidSubSequence() {
        String s = "hello";
        CharSequence result = s.subSequence(1, 4);
        if (!result.equals("ell")) {
            throw new AssertionError("Expected 'ell', got: " + result);
        }
    }

    private static void testBeginIndexGreaterThanEndIndex() {
        String s = "world";
        try {
            s.subSequence(3, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for beginIndex > endIndex");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testBeginIndexNegative() {
        String s = "test";
        try {
            s.subSequence(-1, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative beginIndex");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testEndIndexExceedsLength() {
        String s = "java";
        try {
            s.subSequence(1, 10);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for endIndex > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
