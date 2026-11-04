/*
 * @test
 * @summary Test java.lang.StringBuffer.setLength(int) for exception and normal behavior
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testSetLengthNegative();
        testSetLengthIncrease();
        testSetLengthDecrease();
    }

    private static void testSetLengthNegative() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.setLength(-1);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for negative length");
        } catch (StringIndexOutOfBoundsException expected) {
            // Expected exception
        }
    }

    private static void testSetLengthIncrease() {
        StringBuffer sb = new StringBuffer("abc");
        sb.setLength(5);
        if (sb.length() != 5) {
            throw new RuntimeException("Length should be 5 after increasing length");
        }
        // The new characters should be \u0000 (null chars)
        if (sb.charAt(3) != '\u0000' || sb.charAt(4) != '\u0000') {
            throw new RuntimeException("New characters after increasing length should be null chars");
        }
    }

    private static void testSetLengthDecrease() {
        StringBuffer sb = new StringBuffer("hello world");
        sb.setLength(5);
        if (sb.length() != 5) {
            throw new RuntimeException("Length should be 5 after decreasing length");
        }
        if (!sb.toString().equals("hello")) {
            throw new RuntimeException("Content should be truncated to 'hello'");
        }
    }
}
