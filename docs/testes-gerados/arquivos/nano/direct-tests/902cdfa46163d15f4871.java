/* @test    @summary Test StringBuffer.substring(int start) method for various start indices.    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testSubstringWithValidStart();
        testSubstringWithStartZero();
        testSubstringWithStartEqualToLength();
        testSubstringWithStartGreaterThanLength();
    }

    private static void testSubstringWithValidStart() {
        StringBuffer sb = new StringBuffer("Hello, World!");
        String result = sb.substring(7);
        if (!"World!".equals(result)) {
            throw new AssertionError("Expected 'World!', but got: " + result);
        }
    }

    private static void testSubstringWithStartZero() {
        StringBuffer sb = new StringBuffer("Test");
        String result = sb.substring(0);
        if (!"Test".equals(result)) {
            throw new AssertionError("Expected 'Test', but got: " + result);
        }
    }

    private static void testSubstringWithStartEqualToLength() {
        StringBuffer sb = new StringBuffer("Sample");
        String result = sb.substring(sb.length());
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string, but got: " + result);
        }
    }

    private static void testSubstringWithStartGreaterThanLength() {
        StringBuffer sb = new StringBuffer("ErrorTest");
        try {
            sb.substring(sb.length() + 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for start > length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
