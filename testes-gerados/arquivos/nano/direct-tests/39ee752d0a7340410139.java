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
        sb.deleteCharAt(1); // should remove 'e'
        assert sb.toString().equals("hllo") : "Expected 'hllo', but got '" + sb.toString() + "'";
    }

    private static void testDeleteCharAtNegativeIndex() {
        StringBuilder sb = new StringBuilder("world");
        try {
            sb.deleteCharAt(-1);
            assert false : "Expected IndexOutOfBoundsException for negative index";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testDeleteCharAtIndexEqualLength() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.deleteCharAt(sb.length()); // should throw exception
            assert false : "Expected IndexOutOfBoundsException for index equal to length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testDeleteCharAtIndexGreaterThanLength() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.deleteCharAt(10); // should throw exception
            assert false : "Expected IndexOutOfBoundsException for index greater than length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
