/*
 * @test
 * @summary Test java.lang.String.subSequence(int, int) for bounds checking and correct substring behavior
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testValidSubSequence();
        testBeginIndexNegative();
        testEndIndexGreaterThanLength();
        testBeginIndexGreaterThanEndIndex();
    }

    private static void testValidSubSequence() {
        String s = "Hello, world!";
        CharSequence cs = s.subSequence(0, 5);
        if (!"Hello".contentEquals(cs)) {
            throw new AssertionError("Expected 'Hello' but got '" + cs + "'");
        }
        cs = s.subSequence(7, 12);
        if (!"world".contentEquals(cs)) {
            throw new AssertionError("Expected 'world' but got '" + cs + "'");
        }
    }

    private static void testBeginIndexNegative() {
        String s = "abc";
        try {
            s.subSequence(-1, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative beginIndex");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testEndIndexGreaterThanLength() {
        String s = "abc";
        try {
            s.subSequence(1, 5);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for endIndex > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testBeginIndexGreaterThanEndIndex() {
        String s = "abc";
        try {
            s.subSequence(2, 1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for beginIndex > endIndex");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
