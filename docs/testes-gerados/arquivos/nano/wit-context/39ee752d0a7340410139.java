/* @test    @summary Test deleteCharAt method of StringBuilder for valid and invalid indices    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testDeleteCharAtValidIndex();
        testDeleteCharAtNegativeIndex();
        testDeleteCharAtIndexEqualLength();
        testDeleteCharAtIndexGreaterThanLength();
    }

    private static void testDeleteCharAtValidIndex() {
        StringBuilder sb = new StringBuilder("hello");
        sb.deleteCharAt(1); // remove 'e'
        if (!"hllo".equals(sb.toString())) {
            throw new AssertionError("Expected 'hllo', but got: " + sb.toString());
        }
    }

    private static void testDeleteCharAtNegativeIndex() {
        StringBuilder sb = new StringBuilder("world");
        try {
            sb.deleteCharAt(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testDeleteCharAtIndexEqualLength() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.deleteCharAt(4); // index equal to length
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index equal to length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testDeleteCharAtIndexGreaterThanLength() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.deleteCharAt(10); // index greater than length
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index greater than length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
