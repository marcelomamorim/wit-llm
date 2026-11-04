/* @test    @summary Test StringBuffer.substring method for various valid and invalid inputs    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testSubstringValidRange();
        testSubstringStartEqualsEnd();
        testSubstringStartNegative();
        testSubstringEndExceedsLength();
        testSubstringStartGreaterThanEnd();
    }

    static void testSubstringValidRange() {
        StringBuffer sb = new StringBuffer("Hello, World!");
        String result = sb.substring(0, 5);
        if (!"Hello".equals(result)) {
            throw new AssertionError("Expected 'Hello', but got: " + result);
        }
    }

    static void testSubstringStartEqualsEnd() {
        StringBuffer sb = new StringBuffer("Test");
        String result = sb.substring(2, 2);
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string, but got: " + result);
        }
    }

    static void testSubstringStartNegative() {
        StringBuffer sb = new StringBuffer("Negative");
        try {
            sb.substring(-1, 4);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative start");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSubstringEndExceedsLength() {
        StringBuffer sb = new StringBuffer("Exceed");
        try {
            sb.substring(0, 100);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for end > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSubstringStartGreaterThanEnd() {
        StringBuffer sb = new StringBuffer("Order");
        try {
            sb.substring(4, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for start > end");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
