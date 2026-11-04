/*
 * @test
 * @summary Test StringBuffer.substring(int) for valid and invalid indices
 * @run main java.lang.StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testSubstringValid();
        testSubstringInvalidNegativeStart();
        testSubstringInvalidStartGreaterThanLength();
    }

    private static void testSubstringValid() {
        java.lang.StringBuffer sb = new java.lang.StringBuffer("HelloWorld");
        String result = sb.substring(0);
        if (!"HelloWorld".equals(result)) {
            throw new AssertionError("Expected 'HelloWorld' but got '" + result + "'");
        }
        result = sb.substring(5);
        if (!"World".equals(result)) {
            throw new AssertionError("Expected 'World' but got '" + result + "'");
        }
    }

    private static void testSubstringInvalidNegativeStart() {
        java.lang.StringBuffer sb = new java.lang.StringBuffer("abc");
        try {
            sb.substring(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative start index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testSubstringInvalidStartGreaterThanLength() {
        java.lang.StringBuffer sb = new java.lang.StringBuffer("abc");
        try {
            sb.substring(4);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for start index greater than length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
