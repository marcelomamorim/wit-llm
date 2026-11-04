/* @test    @summary Test subSequence method of AbstractStringBuilder for valid and invalid inputs    @run main AbstractStringBuilderWitupTest */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        testValidSubSequence();
        testInvalidSubSequence();
    }

    private static void testValidSubSequence() {
        StringBuilder sb = new StringBuilder("Hello, World!");
        CharSequence cs = sb.subSequence(0, 5);
        assert "Hello".contentEquals(cs) : "Expected 'Hello', but got '" + cs + "'";
        cs = sb.subSequence(7, 12);
        assert "World".contentEquals(cs) : "Expected 'World', but got '" + cs + "'";
    }

    private static void testInvalidSubSequence() {
        StringBuilder sb = new StringBuilder("Test");
        try {
            sb.subSequence(-1, 2);
            assert false : "Expected IndexOutOfBoundsException for negative start";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.subSequence(1, 10);
            assert false : "Expected IndexOutOfBoundsException for end > length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.subSequence(3, 2);
            assert false : "Expected IndexOutOfBoundsException for start > end";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
