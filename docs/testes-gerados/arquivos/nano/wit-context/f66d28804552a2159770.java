/* @test    @summary Test delete method of StringBuffer for valid and invalid ranges    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testDeleteValidRange();
        testDeleteStartEqualsEnd();
        testDeleteStartGreaterThanEnd();
        testDeleteNegativeStart();
        testDeleteEndExceedsLength();
        testDeleteStartEqualsEndAtZero();
    }

    private static void testDeleteValidRange() {
        StringBuffer sb = new StringBuffer("Hello, World!");
        sb.delete(7, 12); // removes "World"
        if (!"Hello, !".equals(sb.toString())) {
            throw new AssertionError("Failed valid range delete: " + sb.toString());
        }
    }

    private static void testDeleteStartEqualsEnd() {
        StringBuffer sb = new StringBuffer("Test");
        sb.delete(2, 2); // no change
        if (!"Test".equals(sb.toString())) {
            throw new AssertionError("Failed delete with start == end: " + sb.toString());
        }
    }

    private static void testDeleteStartGreaterThanEnd() {
        StringBuffer sb = new StringBuffer("Sample");
        sb.delete(4, 2); // no change, as start > end
        if (!"Sample".equals(sb.toString())) {
            throw new AssertionError("Failed delete with start > end: " + sb.toString());
        }
    }

    private static void testDeleteNegativeStart() {
        StringBuffer sb = new StringBuffer("Negative");
        try {
            sb.delete(-1, 3);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative start");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testDeleteEndExceedsLength() {
        StringBuffer sb = new StringBuffer("Exceeds");
        try {
            sb.delete(2, 20);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for end > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testDeleteStartEqualsEndAtZero() {
        StringBuffer sb = new StringBuffer("Zero");
        sb.delete(0, 0); // no change
        if (!"Zero".equals(sb.toString())) {
            throw new AssertionError("Failed delete with start == end at zero: " + sb.toString());
        }
    }
}
