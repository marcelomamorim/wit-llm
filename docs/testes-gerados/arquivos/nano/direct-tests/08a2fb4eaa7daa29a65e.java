/* @test    @summary Test deleteCharAt method of StringBuffer for valid and invalid indices    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testDeleteCharAtValidIndex();
        testDeleteCharAtNegativeIndex();
        testDeleteCharAtIndexEqualToLength();
        testDeleteCharAtIndexGreaterThanLength();
    }

    private static void testDeleteCharAtValidIndex() {
        StringBuffer sb = new StringBuffer("hello");
        sb.deleteCharAt(1); // should remove 'e'
        assert sb.toString().equals("hllo") : "Expected 'hllo', but got '" + sb.toString() + "'";
    }

    private static void testDeleteCharAtNegativeIndex() {
        StringBuffer sb = new StringBuffer("world");
        try {
            sb.deleteCharAt(-1);
            assert false : "Expected StringIndexOutOfBoundsException for negative index";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testDeleteCharAtIndexEqualToLength() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.deleteCharAt(sb.length()); // should throw exception
            assert false : "Expected StringIndexOutOfBoundsException for index equal to length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testDeleteCharAtIndexGreaterThanLength() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.deleteCharAt(10); // should throw exception
            assert false : "Expected StringIndexOutOfBoundsException for index greater than length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
